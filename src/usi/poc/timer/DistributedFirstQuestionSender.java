package usi.poc.timer;

import usi.poc.QuestionSender;

import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;

/**
 * @author nagaviper
 * Pour les questions suivantes, chaque machine s'occupe de son minuteur
 *
 */
public class DistributedFirstQuestionSender extends FunctionAdapter {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(FunctionContext fc) {
		System.out.println("DistributedFirstQuestionSender.execute()");
		QuestionSender.getInstance().send(1);
		
		// On n'a rien à renvoyer mais on évite une exception
		fc.getResultSender().lastResult(0);
	}

	@Override
	public String getId() {
		return "DistributedFirstQuestionSender";
	}
}
