package usi.test;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import usi.SessionUtils;
import usi.poc.BadRequestException;
import usi.poc.UnauthorizedException;
import usi.poc.business.itf.GameData;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.User;
import usi.poc.business.itf.UserRanking;

@Controller
public class RESTControllerTest {

	@Resource
	private IGame game;

	@Resource
	private Map<String, User> usersCache;
	

	@RequestMapping(value="/test/user/{email}", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public User user(@PathVariable String email) throws Exception {
		System.out.println("RESTControllerTest.user()");
		System.out.println(usersCache);
		return usersCache.get(email);
	}

	@RequestMapping(value="/test/usercount", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public int userCount() throws Exception {
		System.out.println("RESTControllerTest.userCount()");
		return usersCache.size();
	}
	
	@RequestMapping(value="/test/game", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public GameData game() throws Exception {
		System.out.println("RESTControllerTest.game()");
		return game.getGameData();
	}

	@RequestMapping(value="/test/ranking/{email}", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public UserRanking ranking(@PathVariable String email,
			@PathVariable String req) throws Exception {
		System.out.println("RESTControllerTest.ranking()");
		User user = usersCache.get(email);
		if (user == null)
			throw new BadRequestException("No user found with email " + email);
		return game.getRanking(user);
	}
}
