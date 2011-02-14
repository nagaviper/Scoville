package usi.poc.business.itf;

import java.util.Arrays;

public class AdminUserAnswers {
	
	private int [] user_answers;
	private int [] good_answers;
	
	public AdminUserAnswers() {

	}

	public AdminUserAnswers(int[] user_answers, int[] good_answers) {
		super();
		this.user_answers = user_answers;
		this.good_answers = good_answers;
	}

	public int[] getUser_answers() {
		return user_answers;
	}

	public void setUser_answers(int[] user_answers) {
		this.user_answers = user_answers;
	}

	public int[] getGood_answers() {
		return good_answers;
	}

	public void setGood_answers(int[] good_answers) {
		this.good_answers = good_answers;
	}

	@Override
	public String toString() {
		return "AdminUserAnswers [user_answers="
				+ Arrays.toString(user_answers) + ", good_answers="
				+ Arrays.toString(good_answers) + "]";
	}	

}
