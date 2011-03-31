package usi.poc.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import usi.poc.QuestionSender;

import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;

public class DistributedLoginTimer extends FunctionAdapter {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(FunctionContext fc) {
		System.out.println("DistributedLoginTimer.execute()");
		Timer timer = new Timer();
		int timeout = (Integer) fc.getArguments() * 1000;
		Date date = new Date(System.currentTimeMillis() + timeout);
		timer.schedule(new TimerTask() {
			public void run() {
				QuestionSender.getInstance().send(1);
	        }
		}, date);
		
		// On n'a rien à renvoyer mais on évite une exception
		fc.getResultSender().lastResult(0);
	}

	@Override
	public String getId() {
		return "DistributedLoginTimer";
	}
}
