package usi.poc.business.itf;

public class User implements IQuizzObject {
	
	private static final long serialVersionUID = 3884899046258970447L;

	private String mail;
	private String firstname;
	private String lastname;
	private String password;
	private int score = 0;
	private int bonus = 0;
	private boolean is_logged = false;
	private int lastAnswer = 0;
	
	public User() {
		
	}
	
	public boolean isLogged() {
		return is_logged;
	}

	public void setLogged() {
		this.is_logged = true;
	}

	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore(int unitScore) {
		this.score += unitScore;
	}
	
	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	public void incrBonus() {
		this.bonus++;
	}
	
	public void resetBonus() {
		this.bonus = 0;
	}
	
	public boolean isLastRight() {
		if (bonus != 0)
			return true;
		return false;
	}
	
	public void setLastAnswer(int lastAnswer) {
		this.lastAnswer = lastAnswer;
	}

	public int getLastAnswer() {
		return lastAnswer;
	}

	@Override
	public String toString() {
		return "User [logged=" + is_logged + ", mail=" + mail + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", password=" + password + "]";
	}

}
