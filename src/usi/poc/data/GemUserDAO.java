package usi.poc.data;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import usi.poc.business.itf.User;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
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
		try {
			return usersCache.existsValue("id = '" + sessionId + "'");
		} catch (FunctionDomainException e) {
			e.printStackTrace();
		} catch (TypeMismatchException e) {
			e.printStackTrace();
		} catch (NameResolutionException e) {
			e.printStackTrace();
		} catch (QueryInvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
}
