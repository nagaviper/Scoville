package usi.poc;

import java.io.IOException;
import java.util.Map;

import org.apache.catalina.CometEvent;
import org.codehaus.jackson.map.ObjectMapper;

import usi.poc.business.impl.GameImpl;
import usi.poc.business.itf.Question;
import usi.poc.business.itf.User;

public class QuestionSender {
	private static QuestionSender instance = new QuestionSender();
	private static ObjectMapper jsonMapper = new ObjectMapper();
	private static int sent = 0;

	public static QuestionSender getInstance() {
		return instance;
	}

	public synchronized void send(int n) {
		// On se protège contre le double-envoi
		if (sent == n)
			return;
		sent = n;

		// On envoie
		System.out.println("QuestionSender callback");
		QuestionTimer.getInstance().cancel();
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
		
		// On s'apprête à recevoir les réponses
		GameImpl.getInstance().beginQuestionTimeFrame(n);
	}
}
