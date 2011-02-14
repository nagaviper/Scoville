package usi.poc.business.itf;


public class AdminUserRanking {

	private int score;
	private int ranking;

	public AdminUserRanking() {

	}

	public AdminUserRanking(int score, int ranking) {
		super();
		this.score = score;
		this.ranking = ranking;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	@Override
	public String toString() {
		return "AdminUserRanking [score=" + score + ", ranking=" + ranking + "]";
	}

}
