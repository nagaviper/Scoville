package usi.poc.data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import usi.poc.business.itf.User;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.TypeMismatchException;

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

	@Override
	public Collection<User> getHundredBestUsers() {
		try {
			return usersCache.query("score >= 0");
			// GemFire ne supporte pas "order by"...
		} catch (FunctionDomainException e1) {
			e1.printStackTrace();
		} catch (TypeMismatchException e1) {
			e1.printStackTrace();
		} catch (NameResolutionException e1) {
			e1.printStackTrace();
		} catch (QueryInvocationTargetException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getFiftyBeforeUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getFiftyAfterUsers() {
		// TODO Auto-generated method stub
		return null;
	}
}
