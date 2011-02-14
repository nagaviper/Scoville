package usi.poc.business.itf;

import java.util.Arrays;

public class UserRankingList {
	
	private String [] mail;
	private int [] scores;
	private String [] firstname;
	private String [] lastname;
	
	public UserRankingList() {
		// TODO Auto-generated constructor stub
	}

	public String[] getMail() {
		return mail;
	}

	public void setMail(String[] mail) {
		this.mail = mail;
	}

	public int[] getScores() {
		return scores;
	}

	public void setScores(int[] scores) {
		this.scores = scores;
	}

	public String[] getFirstname() {
		return firstname;
	}

	public void setFirstname(String[] firstname) {
		this.firstname = firstname;
	}

	public String[] getLastname() {
		return lastname;
	}

	public void setLastname(String[] lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "RankingList [mail=" + Arrays.toString(mail) + ", scores="
				+ Arrays.toString(scores) + ", firstname="
				+ Arrays.toString(firstname) + ", lastname="
				+ Arrays.toString(lastname) + "]";
	}
	
}
