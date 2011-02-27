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

import usi.poc.business.itf.AdminGame;
import usi.poc.business.itf.AdminUserAnswer;
import usi.poc.business.itf.AdminUserAnswers;
import usi.poc.business.itf.AdminUserRanking;
import usi.poc.business.itf.AdminUserRequest;
import usi.poc.business.itf.Answer;
import usi.poc.business.itf.AnswerFeedback;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.LoginInformation;
import usi.poc.business.itf.Question;
import usi.poc.business.itf.User;
import usi.poc.business.itf.UserRanking;

@Controller
public class RESTController {

	@Resource
	private IGame game;
	
	public void setGame(IGame game) {
		this.game = game;
	}

	public RESTController() {

	}

	@RequestMapping(value="/user", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void user(@RequestBody User user) throws Exception {
		game.createUser(user);
	}


	@RequestMapping(value="/game", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void game(@RequestBody AdminGame adminGame) throws Exception {
		game.createGame(adminGame);
	}


	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void login(@RequestBody LoginInformation loginInformation) throws Exception {
		game.login(loginInformation);
	}
	

	@RequestMapping(value="/question/{n}",method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Question question(@PathVariable int n) throws Exception {
		return game.getQuestion(n);
	}
	
	
	@RequestMapping(value="/answer/{n}", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public AnswerFeedback answer(@PathVariable int n, @RequestBody Answer answer, HttpServletResponse response) throws Exception {
		String userId = null; // TODO get user ID fron cookie in HTTP header
		return game.answerQuestion(userId, n, answer);
	}
	
	
	@RequestMapping(value="/ranking", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public UserRanking ranking(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = null; // TODO get user ID fron cookie in HTTP header
		return game.getRanking(userId);
	}
	
	
	@RequestMapping(value="/score",method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public AdminUserRanking question(AdminUserRequest userRequest) throws Exception {
		return game.getUserRanking(userRequest);
	}

	
	@RequestMapping(value="/audit",method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public AdminUserAnswers auditAnswers(AdminUserRequest userRequest) throws Exception {
		return game.getUserAnswers(userRequest);
	}

	
	@RequestMapping(value="/audit/{n}",method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public AdminUserAnswer auditAnswer(AdminUserRequest userRequest, @PathVariable int n) throws Exception {
		return game.getUserAnswer(userRequest, n);
	}

	
}
