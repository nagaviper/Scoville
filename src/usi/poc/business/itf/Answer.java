package usi.poc.business.itf;

public class Answer implements IQuizzObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3309514582655456631L;
	private int answer;
	
	public Answer() {

	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Answer [answer=" + answer + "]";
	}

}
