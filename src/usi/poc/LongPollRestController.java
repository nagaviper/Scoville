package usi.poc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;

import usi.SessionUtils;
import usi.poc.business.impl.GameImpl;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.Question;
import usi.poc.business.itf.User;

public class LongPollRestController extends HttpServlet implements CometProcessor {

	private static final long serialVersionUID = 1L;
	private static Map<User, CometEvent> connexions = new HashMap<User, CometEvent>();
	//private static QuestionTimer questionTimer;
	
	private IGame game;
	
	public void setGame(IGame game) {
		this.game = game;
	}

	public LongPollRestController() {
		this.game = GameImpl.getInstance();
	}

	@Override
	public void event(CometEvent event) throws IOException, ServletException {
		if (event.getEventType() == CometEvent.EventType.BEGIN) {
	        HttpServletRequest request = event.getHttpServletRequest();
	        
	        // Vérification de la validité de la requête
			int questionNumber = 0;
	        String pathInfo = request.getPathInfo();
	        if (pathInfo != null && pathInfo.length() > 1)
	        	questionNumber = Integer.parseInt(pathInfo.substring(1));
	        if (questionNumber != game.getPresentQuestionNumber()) {
	        	event.getHttpServletResponse().setStatus(400);
	        	event.close();
	        }

	        // Vérification de la validité de l'utilisateur
	        User user = SessionUtils.getLoggedUser(request, game);
	        if (user == null) {
	        	event.getHttpServletResponse().setStatus(401);
	        	event.close();
	        }
	        
	        // Mise à jour de la liste des clients
	        synchronized(connexions) {
	        	connexions.put(user, event);
	        }
		}
		else if (event.getEventType() == CometEvent.EventType.ERROR)
			event.close();
		else if (event.getEventType() == CometEvent.EventType.END)
			event.close();
	}
	
	public void callback() {
		// On a atteint la fin de synchrotime
		for (Map.Entry<User, CometEvent> entry : this.connexions.entrySet()) {
			User user = entry.getKey();
			Question q = game.getPresentQuestion(user);
			String score = "monscore"; // à retirer
			CometEvent event = entry.getValue();
			try {
				event.getHttpServletResponse().getWriter().println(score);
				event.getHttpServletResponse().getWriter().flush();
				event.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.connexions.clear();
		
		// On relance le minuteur
		/*try {
			questionTimer.wait(game.getGameData().getQuestiontimeframe()); // qui devrait déclencher synchrotime, etc
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}
