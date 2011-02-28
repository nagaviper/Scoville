package usi.poc.business.itf;



public class GameData {
	
	private Question [] question;
	private int longpollingduration;
	private int nbusersthreshold;
	private int questiontimeframe;
	private int nbquestions;
	private boolean flushusertable;
	
	/**
	 * unused
	 */
	private String trackeduseridmail;

	public GameData(Question[] question, int longpollingduration,
			int nbusersthreshold, int questiontimeframe, int nbquestions,
			boolean flushusertable, String trackeduseridmail) {
		super();
		this.question = question;
		this.longpollingduration = longpollingduration;
		this.nbusersthreshold = nbusersthreshold;
		this.questiontimeframe = questiontimeframe;
		this.nbquestions = nbquestions;
		this.flushusertable = flushusertable;
		this.trackeduseridmail = trackeduseridmail;
	}

	public GameData(Question[] question, int longpollingduration,
			int nbusersthreshold, int questiontimeframe, int nbquestions,
			boolean flushusertable) {
		super();
		this.question = question;
		this.longpollingduration = longpollingduration;
		this.nbusersthreshold = nbusersthreshold;
		this.questiontimeframe = questiontimeframe;
		this.nbquestions = nbquestions;
		this.flushusertable = flushusertable;
	}

	public Question[] getQuestion() {
		return question;
	}

	public int getLongpollingduration() {
		return longpollingduration;
	}

	public int getNbusersthreshold() {
		return nbusersthreshold;
	}

	public int getQuestiontimeframe() {
		return questiontimeframe;
	}

	public int getNbquestions() {
		return nbquestions;
	}

	public boolean isFlushusertable() {
		return flushusertable;
	}

	public String getTrackeduseridmail() {
		return trackeduseridmail;
	}

}
