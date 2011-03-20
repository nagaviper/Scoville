package usi.poc.business.itf;



public class GameData implements IQuizzObject {
	
	private static final long serialVersionUID = -691230606112571557L;
	
	private Question [] question;
	private int[] goodChoice;
	private int logintimeout;
	private int synchrotime;
	private int nbusersthreshold;
	private int questiontimeframe;
	private int nbquestions;
	private boolean flushusertable;
	
	/**
	 * unused
	 */
	private String trackeduseridmail;
	
	public GameData(Question[] question, int[] goodChoice, int logintimeout, int synchrotime,
			int nbusersthreshold, int questiontimeframe, int nbquestions,
			boolean flushusertable, String trackeduseridmail) {
		super();
		this.question = question;
		this.goodChoice = goodChoice;
		this.logintimeout = logintimeout;
		this.synchrotime = synchrotime;
		this.nbusersthreshold = nbusersthreshold;
		this.questiontimeframe = questiontimeframe;
		this.nbquestions = nbquestions;
		this.flushusertable = flushusertable;
		this.trackeduseridmail = trackeduseridmail;
	}

	public GameData(Question[] question, int[] goodChoice, int logintimeout, int synchrotime,
			int nbusersthreshold, int questiontimeframe, int nbquestions,
			boolean flushusertable) {
		super();
		this.question = question;
		this.goodChoice = goodChoice;
		this.logintimeout = logintimeout;
		this.synchrotime = synchrotime;
		this.nbusersthreshold = nbusersthreshold;
		this.questiontimeframe = questiontimeframe;
		this.nbquestions = nbquestions;
		this.flushusertable = flushusertable;
	}

	public Question getQuestion(int i) {
		return question[i];
	}
	
	public Question[] getQuestion() {
		return question;
	}
	
	public int getGoodChoice(int i) {
		return goodChoice[i];
	}

	public int getLogintimeout() {
		return logintimeout;
	}

	public int getSynchrotime() {
		return synchrotime;
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
