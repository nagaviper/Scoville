package usi.poc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;

import usi.SessionUtils;
import usi.poc.business.impl.GameImpl;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.User;

public class LongPollRestController extends HttpServlet implements CometProcessor {

	private static final long serialVersionUID = 1L;
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
	        if (questionNumber != game.getPresentQuestionNumber()) {
	        	System.out.println("Fenêtre temporelle incorrecte : demande=" + questionNumber + " au lieu de " + game.getPresentQuestionNumber());
	        	event.getHttpServletResponse().setStatus(400);
	        	event.close();
	        	return;
	        }

	        // Vérification de la validité de l'utilisateur
	        User user = SessionUtils.getLoggedUser(request, game);
	        if (user == null) {
	        	System.out.println("L'utilisateur ne semble pas loggé");
	        	event.getHttpServletResponse().setStatus(401);
	        	event.close();
	        	return;
	        }
	        System.out.println("    from " + user.getMail());
	        
	        // Mise à jour de la liste des clients
	        Connexions.getMap().put(user, event);
	        
	        // Déclenchement du jeu si tous les clients sont connectés
	        if (game.getGameData().getNbusersthreshold() == Connexions.getMap().size())
	        	QuestionSender.getInstance().callback();
		}
		else if (event.getEventType() == CometEvent.EventType.ERROR)
			event.close();
		else if (event.getEventType() == CometEvent.EventType.END)
			event.close();
	}
}
