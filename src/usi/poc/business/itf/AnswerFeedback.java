package usi.poc.business.itf;

public class AnswerFeedback implements IQuizzObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1294786418646252569L;
	private boolean are_u_right;
	private String good_answer;
	private int score;
	
	public AnswerFeedback() {

	}
	
	public AnswerFeedback(boolean are_u_right, String good_answer, int score) {
		super();
		this.are_u_right = are_u_right;
		this.good_answer = good_answer;
		this.score = score;
	}

	public boolean isAre_u_right() {
		return are_u_right;
	}

	public void setAre_u_right(boolean are_u_right) {
		this.are_u_right = are_u_right;
	}

	public String getGood_answer() {
		return good_answer;
	}

	public void setGood_answer(String good_answer) {
		this.good_answer = good_answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "AnswerFeedback [are_u_right=" + are_u_right + ", good_answer="
				+ good_answer + ", score=" + score + "]";
	}
	
}
