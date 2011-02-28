package usi.poc.business.impl;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

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
import usi.poc.data.IUserDAO;

@Service
public class GameImpl implements IGame {

	@Resource
	private IUserDAO userDao;

	@Resource	
	private Map<String, GameData> gameCache;
	
	private static Unmarshaller gameUnmarshaller;
	
	static {
		try {
			gameUnmarshaller = JAXBContext.newInstance(Sessiontype.class.getPackage().getName()).createUnmarshaller();
		} catch (JAXBException e) {
			// TODO - Very big problem !!!
			e.printStackTrace();
		}
	}
		
	public GameImpl() {
		
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
		return gameCache.get(0);
	}
	
	@Override
	public boolean createGame(String xmlParameters) {
		System.out.println("GameImpl.createGame()");
		if ( gameCache.size() == 1 ) {
			return false;
		}
		try {
			@SuppressWarnings("unchecked")
			JAXBElement<Sessiontype> doc = (JAXBElement<Sessiontype>) gameUnmarshaller.unmarshal(new StringReader(xmlParameters));
			Sessiontype s = doc.getValue();
			Parametertype p = s.getParameters();
			int nbQuestions = s.getQuestions().getQuestion().size();
			Question [] questions = new Question [nbQuestions];

			int i = 0;
			for ( usi.poc.business.impl.game.mapping.Question q : s.getQuestions().getQuestion() ) {
				List<String> choices = q.getChoice();
				questions[i++] = new Question(q.getLabel(), choices.get(0), choices.get(0), choices.get(0), choices.get(0), 0);
			}			
			GameData gameData = new GameData(questions, p.getLongpollingduration(), p.getNbusersthreshold(),
												p.getQuestiontimeframe(), p.getNbquestions(), p.isFlushusertable());
			
			gameCache.put("game", gameData);
		}
		catch (JAXBException e) {
			// TODO - Very big problem !!!
			e.printStackTrace();
		}
		return true;
	}

	
	@Override
	public Question getQuestion(int n) {
		System.out.println("GameImpl.getQuestion()");
		System.out.println("Not yet implemented...");
		Question q = new Question();
		q.setQuestion("Pourquoi Marc n'aime t-il pas JAVA ?");
		q.setAnswer_1("Parce que trop de monde deteste le C");
		q.setAnswer_2("Parce que le logo n'est pas une tasse de thé");
		q.setAnswer_3("Parce que c'est trop facile");
		q.setAnswer_4("Parce que... Non, en fait il n'y a pas de raison");
		return q;
	}


	@Override
	public AnswerFeedback answerQuestion(User user, int n, Answer answer) {
		System.out.println("GameImpl.answerQuestion()");
		System.out.println("Not yet implemented...");
		System.out.println(answer);
		return new AnswerFeedback();
	}

	
	@Override
	public UserRanking getRanking(User user) {
		System.out.println("GameImpl.getRanking()");
		System.out.println("Not yet implemented...");
		UserRanking r = new UserRanking();
		r.setMy_score(12);
		r.setTop_scores(new UserRankingList());
		r.getTop_scores().setMail(new String [] { "az", "er" , "ty" });
		r.getTop_scores().setScores(new int [] { 21, 13, 32, 12, 4 });
		r.setBefore_me(new UserRankingList());
		r.getBefore_me().setMail(new String [] { "wx", "cv" , "bn" });
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
	
}

