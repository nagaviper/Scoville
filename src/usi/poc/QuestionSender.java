package usi.poc;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.catalina.CometEvent;
import org.codehaus.jackson.map.ObjectMapper;

import usi.poc.business.impl.GameImpl;
import usi.poc.business.itf.Question;
import usi.poc.business.itf.User;

public class QuestionSender implements ITimerThrower {
	private static QuestionSender instance = new QuestionSender();
	private static ObjectMapper jsonMapper = new ObjectMapper();

	public static QuestionSender getInstance() {
		return instance;
	}

	@Override
	public synchronized void callback() {
		System.out.println("QuestionSender callback");
		QuestionTimer.getInstance().cancel();
		int n = GameImpl.getInstance().getPresentQuestionNumber();
		for (Map.Entry<User, CometEvent> entry : Connexions.getMap().entrySet()) {
			User user = entry.getKey();
			Question q = GameImpl.getInstance().getQuestion(user, n);
			CometEvent event = entry.getValue();
			try {
				jsonMapper.writeValue(event.getHttpServletResponse()
						.getWriter(), q);
				event.getHttpServletResponse().getWriter().flush();
				event.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Connexions.getMap().clear();
		GameImpl.getInstance().beginQuestionTimeFrame();
	}
}
