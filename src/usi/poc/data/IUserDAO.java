package usi.poc.data;

import usi.poc.business.itf.User;

public interface IUserDAO extends IDAO {
	boolean existUser(String sessionId);
	void put(String key, User obj);
}
