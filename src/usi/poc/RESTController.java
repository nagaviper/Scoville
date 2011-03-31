package usi.poc;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import usi.SessionUtils;
import usi.poc.business.itf.AdminGame;
import usi.poc.business.itf.AdminUserAnswer;
import usi.poc.business.itf.AdminUserAnswers;
import usi.poc.business.itf.Answer;
import usi.poc.business.itf.AnswerFeedback;
import usi.poc.business.itf.IGame;
import usi.poc.business.itf.LoginInformation;
import usi.poc.business.itf.Question;
import usi.poc.business.itf.User;
import usi.poc.business.itf.UserRanking;

@Controller
public class RESTController {

	private static final String AUTHENTICATION_KEY = "key";

	@Resource(name="game")
	private IGame game;
	
	public void setGame(IGame game) {
		this.game = game;
	}

	public RESTController() {

	}

	@RequestMapping(value="/user", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void user(@RequestBody User user) throws Exception {
		if (! game.createUser(user))
			throw new BadRequestException();
	}


	@RequestMapping(value="/game", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void game(@RequestBody AdminGame adminGame) throws Exception {
		if ( AUTHENTICATION_KEY.equals(adminGame.getAuthentication_key()) ) {
			if ( ! game.createGame(adminGame.getParameters()) ) {
				throw new BadRequestException("A game already exist");
			}
		}
		else {
			throw new UnauthorizedException();
		}
	}


	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void login(@RequestBody LoginInformation loginInformation,
		HttpServletResponse response) throws Exception {
		User user = game.getUser(loginInformation.getMail());
		if (user == null || ! user.getPassword().equals(loginInformation.getPassword()))
			throw new UnauthorizedException();
		else if (user.isLogged())
			throw new BadRequestException();
		else {
			response.addCookie(new Cookie(SessionUtils.SESSION_KEY, loginInformation.getMail()));
			game.login(user);
		}
	}
	

	@RequestMapping(value="/question/{n}",method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Question question(@PathVariable int n,
			HttpServletRequest request) throws Exception {
		User user = SessionUtils.getLoggedUser(request, game);
		if (user == null)
			throw new UnauthorizedException();
		return game.getQuestion(user, n);
	}
	
	
	@RequestMapping(value="/answer/{n}", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public AnswerFeedback answer(@PathVariable int n,
			@RequestBody Answer answer,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = SessionUtils.getLoggedUser(request, game);
		if (user == null)
			throw new UnauthorizedException();
		return game.answerQuestion(user, n, answer);
	}
	
	
	@RequestMapping(value="/ranking", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public UserRanking ranking(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = SessionUtils.getLoggedUser(request, game);
		if (user == null)
			throw new UnauthorizedException();
		if (! game.getGameData().isGameFinished())
			throw new BadRequestException();
		return game.getRanking(user, false);
	}
	
	
	@RequestMapping(value="/score",method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public UserRanking score(@RequestParam(value="user_mail") String userMail,
			@RequestParam(value="authentication_key") String key) throws Exception {
		if (! AUTHENTICATION_KEY.equals(key))
			throw new UnauthorizedException();
		User user = game.getUser(userMail);
		if (user == null)
			throw new BadRequestException("Utilisateur inconnu");
		return game.getRanking(user, true);
	}

	
	@RequestMapping(value="/audit",method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public AdminUserAnswers auditAnswers(@RequestParam(value="user_mail") String userMail,
			@RequestParam(value="authentication_key") String key) throws Exception {
		if (! AUTHENTICATION_KEY.equals(key))
			throw new UnauthorizedException();
		User user = game.getUser(userMail);
		if (user == null)
			throw new BadRequestException("Utilisateur inconnu");
		return game.getUserAnswers(user);
	}

	
	@RequestMapping(value="/audit/{n}",method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public AdminUserAnswer auditAnswer(@RequestParam(value="user_mail") String userMail,
			@RequestParam(value="authentication_key") String key,
			@PathVariable int n) throws Exception {
		if (! AUTHENTICATION_KEY.equals(key))
			throw new UnauthorizedException();
		User user = game.getUser(userMail);
		if (user == null)
			throw new BadRequestException("Utilisateur inconnu");
		if (n < 1 || n > 20)
			throw new BadRequestException();
		return game.getUserAnswer(user, n);
	}
}
