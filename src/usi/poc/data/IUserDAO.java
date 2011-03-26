package usi.poc.data;

import java.util.Collection;
import java.util.List;

import usi.poc.business.itf.User;

public interface IUserDAO extends IDAO {
	
	boolean existUser(String sessionId);
	
	void put(String key, User obj);
	
	int getScore(User user);
	
	Collection<User> getHundredBestUsers();
	List<User> getFiftyBeforeUsers();
	List<User> getFiftyAfterUsers();
}
