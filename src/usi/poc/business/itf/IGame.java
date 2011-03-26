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

	public Question getQuestion(User user, int n);
	
	public AnswerFeedback answerQuestion(User user, int n, Answer answer);
	
	public UserRanking getRanking(User user);
	
	public AdminUserRanking getUserRanking(AdminUserRequest request);
	
	public AdminUserAnswers getUserAnswers(AdminUserRequest request);

	public AdminUserAnswer getUserAnswer(AdminUserRequest request, int n);
	
	public void login(User user);
	
	public void beginLoginTimeout();
	public void beginQuestionTimeFrame(int n);
	public void beginSynchroTime(int n);
}
