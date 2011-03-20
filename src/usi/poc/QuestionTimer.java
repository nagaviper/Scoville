package usi.poc;

import java.util.TimerTask;

public class QuestionTimer extends TimerTask {
	private ITimerThrower thrower;
	
	public void setThrower(ITimerThrower thrower) {
		this.thrower = thrower;
	}

	@Override
	public void run() {
		thrower.callback();
	}
}
