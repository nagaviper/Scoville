package usi.poc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

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
		for (Entry<User, HttpServletResponse> entry : Connexions.getMap().entrySet()) {
			User user = entry.getKey();
			Question q = GameImpl.getInstance().getQuestion(user, n);
			HttpServletResponse response = entry.getValue();
			try {
				PrintWriter writer = response.getWriter();
				if (writer != null) {
					jsonMapper.writeValue(writer, q);
					//writer.flush();
					//event.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Connexions.getMap().clear();
		
		// On s'apprête à recevoir les réponses
		GameImpl.getInstance().beginQuestionTimeFrame(n);
	}
}
