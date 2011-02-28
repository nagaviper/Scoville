package usi.poc.business.itf;



public interface IGame {

	public User getUser(String key);

	public boolean existsUser(String id);

	/**
	 * User creation
	 * @param user
	 * @return true if user had been created, false if it already exists
	 */
	public boolean createUser(User user);
	
	/**
	 * Game creation
	 * @param xmlParameters xml string matching gamesession.xsd schema
	 * @return true if game had been created, false if it already exists
	 */
	public boolean createGame(String xmlParameters);
	
	/**
	 * Get the game
	 * @return the game reference if it exists, null otherwise
	 */
	public GameData getGameData();
	
	public String login(LoginInformation loginInformation);
	
	public Question getQuestion(String userId, int n);
	
	public AnswerFeedback answerQuestion(String userId, int n, Answer answer);
	
	public UserRanking getRanking(String userId);
	
	public AdminUserRanking getUserRanking(AdminUserRequest request);
	
	public AdminUserAnswers getUserAnswers(AdminUserRequest request);

	public AdminUserAnswer getUserAnswer(AdminUserRequest request, int n);

}
