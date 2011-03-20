package usi.poc.data;

import java.util.Map;

import javax.annotation.Resource;

import usi.poc.business.itf.User;

import com.gemstone.gemfire.cache.Region;

public class GemUserDAO extends AbstractGemDAO implements IUserDAO {
	@Resource
	Region<String, User> usersCache;
	
	@Override
	public Map<String, User> getImpl() {
		return usersCache;
	}
	
	@Override
	public void put(String key, User obj) {
		getImpl().put(key, obj);
	}

	@Override
	public boolean existUser(String sessionId) {
		return usersCache.containsKey(sessionId);
	}

	@Override
	public int getScore(User user) {
		return user.getScore();
	}
}
