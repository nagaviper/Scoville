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
import usi.poc.business.itf.AdminUserRanking;
import usi.poc.business.itf.AdminUserRequest;
import usi.poc.business.itf.Answer;
import usi.poc.business.itf.AnswerFeedback;
import usi.poc.business.itf.GameData;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.Question;
import usi.poc.business.itf.User;
import usi.poc.business.itf.UserRanking;
import usi.poc.business.itf.UserRankingList;
import usi.poc.data.IGameDataDAO;
import usi.poc.data.IUserDAO;

public class GameImpl implements IGame {

	private static IGame instance = new GameImpl();
	
	public static IGame getInstance() {
		return instance;
	}
	
	private GameImpl() {
		
	}


	@Resource
	private IUserDAO userDao;

	@Resource	
	private IGameDataDAO gameDataDao;
	
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
		}
		ScoreCalculator.calculate(user, n, good);
		String goodAnswer = gameDataDao.getGame().getQuestion(n).getAnswer(goodChoice);
		return new AnswerFeedback(good, goodAnswer, user.getScore());
	}

	
	@Override
	public UserRanking getRanking(User user) {
		Collection<User> top = userDao.getHundredBestUsers();
		List<User> before = userDao.getFiftyBeforeUsers();
		List<User> after = userDao.getFiftyAfterUsers();
		
		UserRanking r = new UserRanking();
		r.setScore(user.getScore());
		r.setTop_scores(UserRankingListBuilder.build(top));
		r.setBefore(UserRankingListBuilder.build(before));
		r.setAfter(UserRankingListBuilder.build(after));
		return r;
	}

	
	@Override
	public AdminUserRanking getUserRanking(AdminUserRequest request) {
		System.out.println("GameImpl.getUserRanking()");
		System.out.println("Not yet implemented...");
		return new AdminUserRanking();
	}

	
	@Override
	public AdminUserAnswers getUserAnswers(AdminUserRequest request) {
		System.out.println("GameImpl.getUserAnswers()");
		System.out.println("Not yet implemented...");
		return new AdminUserAnswers();
	}

	
	@Override
	public AdminUserAnswer getUserAnswer(AdminUserRequest request, int n) {
		System.out.println("GameImpl.getUserAnswer()");
		System.out.println("Not yet implemented...");
		return new AdminUserAnswer();
	}

	@Override
	public void login(User user) {
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

		Timer timer = new Timer();
		int timeout = gameData.getLogintimeout() * 1000;
		Date date = new Date(System.currentTimeMillis() + timeout);
		timer.schedule(new TimerTask() {
			public void run() {
				QuestionSender.getInstance().send(1);
	        }
		}, date);
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
}
