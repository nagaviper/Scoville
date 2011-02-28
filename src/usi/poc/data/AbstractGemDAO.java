package usi.poc.data;

import java.util.Map;

import usi.poc.business.itf.IQuizzObject;

public abstract class AbstractGemDAO implements IDAO {
	
	abstract Map<String, ? extends IQuizzObject> getImpl();
	
	@Override
	public boolean contains(String key) {
		return getImpl().containsKey(key);
	}

	@Override
	public IQuizzObject get(String key) {
		return getImpl().get(key);
	}
}
