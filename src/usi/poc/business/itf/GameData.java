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
	private boolean isGameStarted = false;
	private boolean isGameFinished = false;
	private int presentQuestionNumber = 0;
	private int presentAnswerNumber = 0;
	
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
		return question[i - 1];
	}
	
	public Question[] getQuestion() {
		return question;
	}
	
	public int getGoodChoice(int i) {
		return goodChoice[i - 1];
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

	public boolean isGameStarted() {
		return isGameStarted;
	}
	
	public void startGame() {
		this.isGameStarted = true;
		this.presentQuestionNumber = 1;
	}
	
	public boolean isGameFinished() {
		return isGameFinished;
	}
	
	public void endGame() {
		this.isGameFinished = true;
	}

	public void setPresentQuestionNumber(int presentQuestionNumber) {
		this.presentQuestionNumber = presentQuestionNumber;
	}

	public int getPresentQuestionNumber() {
		return presentQuestionNumber;
	}

	public void setPresentAnswerNumber(int presentAnswerNumber) {
		this.presentAnswerNumber = presentAnswerNumber;
	}

	public int getPresentAnswerNumber() {
		return presentAnswerNumber;
	}
}
