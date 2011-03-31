package usi.test;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import usi.poc.business.itf.GameData;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.User;

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
	
	@RequestMapping(value="/test/timer", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void timer() throws Exception {
		System.out.println("RESTControllerTest.timer()");
		game.testTimer();
	}
	
	@RequestMapping(value="/test/reset", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void reset() throws Exception {
		System.out.println("RESTControllerTest.reset()");
		game.reset();
	}
}
