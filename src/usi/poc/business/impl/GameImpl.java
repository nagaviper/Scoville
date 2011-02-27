package usi.poc.business.impl;

import java.io.StringReader;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import usi.poc.business.impl.game.mapping.Sessiontype;
import usi.poc.business.itf.AdminUserAnswer;
import usi.poc.business.itf.AdminUserAnswers;
import usi.poc.business.itf.AdminUserRanking;
import usi.poc.business.itf.AdminUserRequest;
import usi.poc.business.itf.Answer;
import usi.poc.business.itf.AnswerFeedback;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.LoginInformation;
import usi.poc.business.itf.Question;
import usi.poc.business.itf.User;
import usi.poc.business.itf.UserRanking;
import usi.poc.business.itf.UserRankingList;

@Service
public class GameImpl implements IGame {

	@Resource	
	private Map<String, User> usersCache;

	@Resource	
	private Map<String, Sessiontype> gameCache;
	
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
	public boolean createUser(User user) {
		String key = user.getMail();
		if ( usersCache.containsKey(key) ) {
			return false;
		}
		usersCache.put(user.getMail(), user);
		return true;
	}
	
	@Override
	public Sessiontype getGame() {
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
			gameCache.put("game", s);
		}
		catch (JAXBException e) {
			// TODO - Very big problem !!!
			e.printStackTrace();
		}
		return true;
	}

	
	@Override
	public void login(LoginInformation loginInformation) {
		System.out.println("GameImpl.login()");
		System.out.println("Not yet implemented...");
		System.out.println(loginInformation);
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
	public AnswerFeedback answerQuestion(String userId, int n, Answer answer) {
		System.out.println("GameImpl.answerQuestion()");
		System.out.println("Not yet implemented...");
		System.out.println(answer);
		return new AnswerFeedback();
	}

	
	@Override
	public UserRanking getRanking(String userId) {
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

