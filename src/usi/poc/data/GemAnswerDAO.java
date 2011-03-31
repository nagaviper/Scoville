package usi.poc.data;

import java.util.Map;

import javax.annotation.Resource;

import usi.poc.business.itf.UserAnswer;

import com.gemstone.gemfire.cache.Region;

public class GemAnswerDAO extends AbstractGemDAO implements IAnswerDAO {
	@Resource
	Region<String, UserAnswer> answersCache;

	@Override
	Map<String, UserAnswer> getImpl() {
		return answersCache;
	}
	
	@Override
	public void put(String key, UserAnswer obj) {
		getImpl().put(key, obj);
	}

	@Override
	public void reset() {
		answersCache.clear();		
	}
}
