package usi.poc.business.itf;

public class UserRanking implements IQuizzObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4944597132221460423L;
	private int my_score;
	private UserRankingList top_scores;
	private UserRankingList before_me;
	private UserRankingList after_me;
		
	public UserRanking() {

	}

	public int getMy_score() {
		return my_score;
	}

	public void setMy_score(int my_score) {
		this.my_score = my_score;
	}

	public UserRankingList getTop_scores() {
		return top_scores;
	}

	public void setTop_scores(UserRankingList top_scores) {
		this.top_scores = top_scores;
	}

	public UserRankingList getBefore_me() {
		return before_me;
	}

	public void setBefore_me(UserRankingList before_me) {
		this.before_me = before_me;
	}

	public UserRankingList getAfter_me() {
		return after_me;
	}

	public void setAfter_me(UserRankingList after_me) {
		this.after_me = after_me;
	}

	@Override
	public String toString() {
		return "Ranking [my_score=" + my_score + ", top_scores=" + top_scores
				+ ", before_me=" + before_me + ", after_me=" + after_me + "]";
	}

	
}
