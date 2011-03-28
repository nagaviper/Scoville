package usi.poc.data;

import usi.poc.business.itf.UserAnswer;

public interface IAnswerDAO extends IDAO {
	void put(String key, UserAnswer obj);
}
