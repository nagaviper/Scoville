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
		System.out.println("QuestionTimer run");
		if (thrower != null)
			thrower.callback();
	}
	
	@Override
	public boolean cancel() {
		iswaiting = false;
		return super.cancel();
	}

	public void conditionalWait(int timeout) {
		if (! iswaiting) {
			synchronized(instance) {
				if (! iswaiting) {
					try {
						iswaiting = true;
						this.wait(timeout);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
