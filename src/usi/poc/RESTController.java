package usi.poc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import usi.poc.business.itf.IGame;

@Controller
public class RESTController {
	
	private static final String adminAuthenticationKey = "azerty";

	@Resource
	private IGame game;
	
	public void setGame(IGame game) {
		this.game = game;
	}


	@RequestMapping(value="/user",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createUser(@RequestBody String param, HttpServletResponse response) throws Exception {
		//System.out.println("RESTController.createUser()");
		game.createUser(param);
	}

	
	@RequestMapping(value="/game",method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)	
	public void createGame(@RequestBody String param, HttpServletResponse response) throws Exception {
		System.out.println("RESTController.createGame()");
		game.createGame(param);
	}

	
	@RequestMapping(value="/question/{n}",method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public String getQuestion(@PathVariable int n, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("RESTController.getQuestion()");
		return game.getQuestion(n);
	}
	
	
}
