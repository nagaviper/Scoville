package usi.poc.business.itf;

public class UserRanking implements IQuizzObject {
	
	private static final long serialVersionUID = 4944597132221460423L;

	private int score;
	private UserRankingList top_scores;
	private UserRankingList before;
	private UserRankingList after;
		
	public UserRanking() {

	}

	public UserRankingList getTop_scores() {
		return top_scores;
	}

	public void setTop_scores(UserRankingList top_scores) {
		this.top_scores = top_scores;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public UserRankingList getBefore() {
		return before;
	}

	public void setBefore(UserRankingList before) {
		this.before = before;
	}

	public UserRankingList getAfter() {
		return after;
	}

	public void setAfter(UserRankingList after) {
		this.after = after;
	}

	@Override
	public String toString() {
		return "Ranking [score=" + score + ", top_scores=" + top_scores
				+ ", before=" + before + ", after=" + after + "]";
	}

	
}
