package usi.poc.business.itf;



public interface IGame {

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
	
	public void login(LoginInformation loginInformation);
	
	public Question getQuestion(int n);
	
	public AnswerFeedback answerQuestion(String userId, int n, Answer answer);
	
	public UserRanking getRanking(String userId);
	
	public AdminUserRanking getUserRanking(AdminUserRequest request);
	
	public AdminUserAnswers getUserAnswers(AdminUserRequest request);

	public AdminUserAnswer getUserAnswer(AdminUserRequest request, int n);

}
