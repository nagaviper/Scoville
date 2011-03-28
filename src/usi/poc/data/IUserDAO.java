package usi.poc.data;

import java.util.Collection;

import usi.poc.business.itf.User;

public interface IUserDAO extends IDAO {
	
	boolean existUser(String sessionId);
	
	void put(String key, User obj);
	
	int getScore(User user);
	
	void prepareScoreTable();

	Collection<User> getHundredBestUsers();
	Collection<User> getFiftyBeforeUsers(int score);
	Collection<User> getFiftyAfterUsers(int score);
}
