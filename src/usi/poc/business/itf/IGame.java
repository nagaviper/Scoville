package usi.poc.business.itf;


public interface IGame {

	public boolean createUser(User user);
	
	public void createGame(AdminGame game);
	
	public String login(LoginInformation loginInformation);
	
	public Question getQuestion(int n);
	
	public AnswerFeedback answerQuestion(String userId, int n, Answer answer);
	
	public UserRanking getRanking(String userId);
	
	public AdminUserRanking getUserRanking(AdminUserRequest request);
	
	public AdminUserAnswers getUserAnswers(AdminUserRequest request);

	public AdminUserAnswer getUserAnswer(AdminUserRequest request, int n);

}
