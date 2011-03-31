package usi.poc.business.itf;

public class UserAnswer implements IQuizzObject {
	private static final long serialVersionUID = 1L;
	
	private String mail;
	private int question;
	private int answer;
	
	public UserAnswer() {
		
	}
	
	public UserAnswer(String mail, int question, int answer) {
		this.setMail(mail);
		this.setQuestion(question);
		this.setAnswer(answer);
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}

	public void setQuestion(int question) {
		this.question = question;
	}

	public int getQuestion() {
		return question;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getAnswer() {
		return answer;
	}
}
