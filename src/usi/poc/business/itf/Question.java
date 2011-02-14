package usi.poc.business.itf;

public class Question {

	private String question;
	private String answer_1;
	private String answer_2;
	private String answer_3;
	private String answer_4;
	private String score;

	public Question() {

	}
	
	public Question(String question, String answer_1, String answer_2, String answer_3, String answer_4, String score) {
		super();
		this.question = question;
		this.answer_1 = answer_1;
		this.answer_2 = answer_2;
		this.answer_3 = answer_3;
		this.answer_4 = answer_4;
		this.score = score;
	}

	public String getAnswer_1() {
		return answer_1;
	}

	public void setAnswer_1(String answer_1) {
		this.answer_1 = answer_1;
	}

	public String getAnswer_2() {
		return answer_2;
	}

	public void setAnswer_2(String answer_2) {
		this.answer_2 = answer_2;
	}

	public String getAnswer_3() {
		return answer_3;
	}

	public void setAnswer_3(String answer_3) {
		this.answer_3 = answer_3;
	}

	public String getAnswer_4() {
		return answer_4;
	}

	public void setAnswer_4(String answer_4) {
		this.answer_4 = answer_4;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "Question [question=" + question + ", answer_1=" + answer_1 + ", answer_2=" + answer_2
		                   + ", answer_3=" + answer_3 + ", answer_4=" + answer_4 + ", score=" + score + "]";
	}
	
}
