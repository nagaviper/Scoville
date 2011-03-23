package usi.poc;

import java.util.TimerTask;

public class QuestionTimer extends TimerTask {
	private ITimerThrower thrower;
	private static QuestionTimer instance = new QuestionTimer();
	private boolean iswaiting = false;
	
	public static QuestionTimer getInstance() {
		return instance;
	}
	
	public void setThrower(ITimerThrower thrower) {
		this.thrower = thrower;
	}

	@Override
	public void run() {
		thrower.callback();
	}
	
	public void conditionalWait(int logintimeout) {
		// Double-checked lock à revoir
		if (! iswaiting) {
			synchronized(instance) {
				if (! iswaiting) {
					try {
						iswaiting = true;
						this.wait(logintimeout);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
