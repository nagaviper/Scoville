package usi;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import usi.poc.business.itf.IGame;
import usi.poc.business.itf.User;

public class SessionUtils {
	public static final String SESSION_KEY = "session_key";
	
	public static User getLoggedUser(HttpServletRequest request, IGame game) {
		Cookie[] cookies = request.getCookies(); // return null if there is no cookie
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(SESSION_KEY)) {
				User user = game.getUser(cookie.getValue());
				if (user != null && user.isLogged())
					return user;
				else
					break;
			}
		}
		return null;
	}
}
