package usi.poc.business.itf;

public class AdminUserAnswer {

	private int user_answer;
	private int good_answer;
	private String question;

	public AdminUserAnswer() {

	}
	
	public AdminUserAnswer(int user_answer, int good_answer, String question) {
		super();
		this.user_answer = user_answer;
		this.good_answer = good_answer;
		this.question = question;
	}

	public int getUser_answer() {
		return user_answer;
	}

	public void setUser_answer(int user_answer) {
		this.user_answer = user_answer;
	}

	public int getGood_answer() {
		return good_answer;
	}

	public void setGood_answer(int good_answer) {
		this.good_answer = good_answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "AdminUserAnswer [user_answer=" + user_answer + ", good_answer="
				+ good_answer + ", question=" + question + "]";
	}
	
}
