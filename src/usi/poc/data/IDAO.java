package usi.poc.data;

import usi.poc.business.itf.IQuizzObject;

public interface IDAO {
	boolean contains(String key);
	IQuizzObject get(String key);
	void reset();
}
