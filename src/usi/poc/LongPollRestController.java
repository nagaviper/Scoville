package usi.poc;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;
import org.codehaus.jackson.map.ObjectMapper;

import usi.SessionUtils;
import usi.poc.business.impl.GameImpl;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.Question;
import usi.poc.business.itf.User;

public class LongPollRestController extends HttpServlet implements CometProcessor {

	private static final long serialVersionUID = 1L;
	private static ConcurrentHashMap<User, CometEvent> connexions = new ConcurrentHashMap<User, CometEvent>();
	private static ObjectMapper jsonMapper = new ObjectMapper();
	private static int n = 0;
	
	private IGame game;
	
	public void setGame(IGame game) {
		this.game = game;
	}

	public LongPollRestController() {
		this.game = GameImpl.getInstance();
	}

	@Override
	public void event(CometEvent event) throws IOException, ServletException {
		System.out.println("New question...");
		if (event.getEventType() == CometEvent.EventType.BEGIN) {
	        HttpServletRequest request = event.getHttpServletRequest();
	        
	        // Vérification de la validité de la requête
			int questionNumber = 0;
	        String pathInfo = request.getPathInfo();
	        if (pathInfo != null && pathInfo.length() > 1)
	        	questionNumber = Integer.parseInt(pathInfo.substring(1));
	        if (questionNumber == 0) {// != game.getPresentQuestionNumber()) {
	        	event.getHttpServletResponse().setStatus(400);
	        	event.close();
	        	return;
	        }

	        // Vérification de la validité de l'utilisateur
	        User user = SessionUtils.getLoggedUser(request, game);
	        if (user == null) {
	        	event.getHttpServletResponse().setStatus(401);
	        	event.close();
	        	return;
	        }
	        System.out.println("    from " + user.getMail());
	        
	        // Mise à jour de la liste des clients
	        synchronized(connexions) {
	        	connexions.put(user, event);
	        	System.out.println("    -> Clients number : " + connexions.size());
	        	if (connexions.size() == 2) {
	        		n = questionNumber;
	        		callback();
	        	}
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
			Question q = game.getQuestion(user, n);
			CometEvent event = entry.getValue();
			try {
				jsonMapper.writeValue(event.getHttpServletResponse().getWriter(), q);
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
