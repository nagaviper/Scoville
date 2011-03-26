package usi.poc;

import usi.poc.business.impl.GameImpl;

public class QuestionFinisher implements ITimerThrower {
	private static QuestionFinisher instance = new QuestionFinisher();

	public static QuestionFinisher getInstance() {
		return instance;
	}

	@Override
	public void callback() {
		GameImpl.getInstance().incrPresentQuestionNumber();
		GameImpl.getInstance().beginSynchroTime();
	}
}
