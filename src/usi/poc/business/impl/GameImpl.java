package usi.poc.business.impl;

import java.io.StringReader;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import usi.poc.QuestionSender;
import usi.poc.ScoreCalculator;
import usi.poc.UserRankingListBuilder;
import usi.poc.business.impl.game.mapping.Parametertype;
import usi.poc.business.impl.game.mapping.Sessiontype;
import usi.poc.business.itf.AdminUserAnswer;
import usi.poc.business.itf.AdminUserAnswers;
import usi.poc.business.itf.Answer;
import usi.poc.business.itf.AnswerFeedback;
import usi.poc.business.itf.GameData;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.Question;
import usi.poc.business.itf.User;
import usi.poc.business.itf.UserAnswer;
import usi.poc.business.itf.UserRanking;
import usi.poc.data.IAnswerDAO;
import usi.poc.data.IGameDataDAO;
import usi.poc.data.IUserDAO;
import usi.poc.timer.DistributedFirstQuestionSender;
import usi.poc.timer.DistributedLoginTimer;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.execute.Execution;
import com.gemstone.gemfire.cache.execute.FunctionService;

public class GameImpl implements IGame {

	private static IGame instance = new GameImpl();
	
	public static IGame getInstance() {
		return instance;
	}
	
	private GameImpl() {
		FunctionService.registerFunction(distLoginTimer);
		FunctionService.registerFunction(distFirstQSender);
	}
	
	private DistributedLoginTimer distLoginTimer = new DistributedLoginTimer();
	private DistributedFirstQuestionSender distFirstQSender = new DistributedFirstQuestionSender();
	
	@Resource
	private Cache cache;

	@Resource
	private IUserDAO userDao;

	@Resource	
	private IGameDataDAO gameDataDao;
	
	@Resource	
	private IAnswerDAO answerDao;
	
	private static Unmarshaller gameUnmarshaller;
	static {
		try {
			gameUnmarshaller = JAXBContext.newInstance(Sessiontype.class.getPackage().getName()).createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public User getUser(String key) {
		return (User)userDao.get(key);
	}
	
	@Override
	public boolean existsUser(String sessionId) {
		return userDao.existUser(sessionId);
	}

	@Override
	public boolean createUser(User user) {
		String key = user.getMail();
		if (userDao.contains(key))
			return false;

		userDao.put(user.getMail(), user);
		return true;
	}
	
	@Override
	public GameData getGameData() {
		return gameDataDao.getGame();
	}
	
	@Override
	public boolean createGame(String xmlParameters) {
		if ( gameDataDao.isGameExists() ) {
			return false;
		}
		try {
			@SuppressWarnings("unchecked")
			JAXBElement<Sessiontype> doc = (JAXBElement<Sessiontype>) gameUnmarshaller.unmarshal(new StringReader(xmlParameters));
			Sessiontype s = doc.getValue();
			Parametertype p = s.getParameters();
			int nbQuestions = s.getQuestions().getQuestion().size();
			Question [] questions = new Question [nbQuestions];
			int[] goodChoices = new int[nbQuestions];

			int i = 0;
			for ( usi.poc.business.impl.game.mapping.Question q : s.getQuestions().getQuestion() ) {
				List<String> choices = q.getChoice();
				questions[i] = new Question(q.getLabel(), choices.get(0), choices.get(1), choices.get(2), choices.get(3), 0);
				goodChoices[i++] = q.getGoodchoice();
			}			
			GameData gameData = new GameData(questions, goodChoices, p.getLogintimeout(), p.getSynchrotime(), p.getNbusersthreshold(),
				p.getQuestiontimeframe(), p.getNbquestions(), p.isFlushusertable());
			
			gameDataDao.createGame(gameData);
		}
		catch (JAXBException e) {
			e.printStackTrace();
		}
		return true;
	}

	
	@Override
	public Question getQuestion(User user, int n) {
		Question question = gameDataDao.getGame().getQuestion(n).clone();
		int score = userDao.getScore(user);
		question.setScore(score);		
		return question;
	}


	@Override
	public AnswerFeedback answerQuestion(User user, int n, Answer answer) {
		GameData gameData = gameDataDao.getGame();
		int goodChoice = gameData.getGoodChoice(n);
		boolean good;
		if (n != gameData.getPresentAnswerNumber())
			good = false;
		else {
			int userChoice = answer.getAnswer();
			good = (userChoice == goodChoice);
			
			// Persistance de la réponse seulement si elle est valide
			answerDao.put(user.getMail() + n, new UserAnswer(user.getMail(), n, userChoice));
		}
		ScoreCalculator.calculate(user, n, good);
		user.setLastAnswer(n);

		System.out.println("Nouveau score de " + user.getMail() + " : " + user.getScore());
		String goodAnswer = gameDataDao.getGame().getQuestion(n).getAnswer(goodChoice);
		return new AnswerFeedback(good, goodAnswer, user.getScore());
	}

	
	@Override
	public UserRanking getRanking(User user, boolean prepareScoreTable) {
		if (prepareScoreTable)
			// TODO : attention, on calcule toutes les tables alors que seul l'utilisateur courant nous intéresse...
			userDao.prepareScoreTable();
		Collection<User> top = userDao.getHundredBestUsers();
		Collection<User> before = userDao.getFiftyBeforeUsers(user.getScore());
		Collection<User> after = userDao.getFiftyAfterUsers(user.getScore());
		
		UserRanking r = new UserRanking();
		r.setScore(user.getScore());
		r.setTop_scores(UserRankingListBuilder.build(top));
		r.setBefore(UserRankingListBuilder.build(before));
		r.setAfter(UserRankingListBuilder.build(after));
		return r;
	}

	
	@Override
	public AdminUserAnswers getUserAnswers(User user) {
		int [] user_answers = new int[20];
		int [] good_answers = new int[20];

		String mail = user.getMail();
		for (int n = 1; n <= 20; n++) {
			UserAnswer userAnswer = (UserAnswer) answerDao.get(mail + n);
			if (userAnswer == null)
				user_answers[n - 1] = 0;
			else
				user_answers[n - 1] = userAnswer.getAnswer();
			good_answers[n - 1] = gameDataDao.getGame().getGoodChoice(n);
		}
		return new AdminUserAnswers(user_answers, good_answers);
	}


	@Override
	public AdminUserAnswer getUserAnswer(User user, int n) {
		// L'utilisateur a ou pas bien répondu à cette question, et dans la fenêtre temporelle autorisée
		UserAnswer userAnswer = (UserAnswer) answerDao.get(user.getMail() + n);
		int user_answer;
		if (userAnswer == null)
			user_answer = 0;
		else
			user_answer = userAnswer.getAnswer();

		// Question posée et réponse attendue
		Question question = gameDataDao.getGame().getQuestion(n);
		int goodChoice = gameDataDao.getGame().getGoodChoice(n);
		
		return new AdminUserAnswer(user_answer, goodChoice, question.getQuestion());
	}

	@Override
	public void login(User user) {
		System.out.println(new Date().toString() + ": login de " + user.getMail());
		user.setLogged();
		// TODO : verrou distribué pour éviter de lancer plusieurs minuteurs
		GameData gameData = gameDataDao.getGame();
		synchronized(gameData) {
			if (gameDataDao.getGame().isGameStarted() == false)
				this.beginLoginTimeout();
		}
	}

	@Override
	public void beginLoginTimeout() {
		System.out.println(new Date().toString() + ": beginLoginTimeout");
		
		final GameData gameData = gameDataDao.getGame();
		gameData.startGame();
		int timeout = gameData.getLogintimeout();
		
		Execution execution = FunctionService.onMembers(cache.getDistributedSystem()).withArgs(timeout);
		execution.execute(distLoginTimer);
	}

	@Override
	public void beginQuestionTimeFrame(final int n) {
		System.out.println(new Date().toString() + ": beginQuestionTimeFrame for question " + n);
		final GameData gameData = gameDataDao.getGame();
		gameData.setPresentAnswerNumber(n);

		if (n > gameData.getNbquestions())
			gameData.setPresentQuestionNumber(0);
		else
			gameData.setPresentQuestionNumber(n+1);

		Timer timer = new Timer();
		int timeout = gameData.getQuestiontimeframe() * 1000;
		Date date = new Date(System.currentTimeMillis() + timeout);
		timer.schedule(new TimerTask() {
			public void run() {
				GameImpl.getInstance().beginSynchroTime(n+1);
	        }
		}, date);
	}

	@Override
	public void beginSynchroTime(final int n) {
		System.out.println(new Date().toString() + ": beginSynchroTime for question " + n);
		final GameData gameData = gameDataDao.getGame();
		gameData.setPresentAnswerNumber(0);
		if (n  > gameData.getNbquestions())
			userDao.prepareScoreTable();

		Timer timer = new Timer();
		int timeout = gameData.getQuestiontimeframe() * 1000;
		Date date = new Date(System.currentTimeMillis() + timeout);
		timer.schedule(new TimerTask() {
			public void run() {
				if (n  > gameData.getNbquestions()) {
					gameData.endGame();
					System.out.println(new Date().toString() + ": end of the game, waiting now for ranking requests");
				}
				else
					QuestionSender.getInstance().send(n);
	        }
		}, date);
	}

	@Override
	public void testTimer() {
		Execution execution = FunctionService.onMembers(cache.getDistributedSystem()).withArgs(4);
		execution.execute(distLoginTimer);
	}

	@Override
	public void sendQuestionsToAll() {
		Execution execution = FunctionService.onMembers(cache.getDistributedSystem());
		execution.execute(distFirstQSender);
	}
}
