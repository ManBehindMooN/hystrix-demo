package ch.trivadis.demo.hystrix.example1;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloCommand extends HystrixCommand<String> {

	private final String name;

	public HelloCommand(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("groupkey"));
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		return "HelloCommand: " + name;
	}

	@Override
	protected String getFallback() {
		return "This is a fallback";
	}

}
