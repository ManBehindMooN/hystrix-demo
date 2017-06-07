package com.trivadis.demo.hystrix;

import com.netflix.hystrix.*;

public class LookupCommand extends HystrixCommand<Person> {

	private String userid;

	private Configuration config = new Configuration();

	protected LookupCommand(String userid, Configuration config) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("LookupCommandGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("LookupCommandKey")));
		// .andCommandPropertiesDefaults(HystrixPropertiesCommandDefault.Setter()
		// .withExecutionTimeoutInMilliseconds(2000)
		// .withCircuitBreakerErrorThresholdPercentage(20)));

		this.userid = userid;
		this.config = config;

	}

	@Override
	protected Person run() throws Exception {
		if (!config.serviceProperties.online) {
			throw new RuntimeException("Service down");
		}
		return UserClient.loadPerson(userid, config.serviceProperties.worktime);
	}

	@Override
	protected String getCacheKey() {
		return userid;
	}

	@Override
	protected Person getFallback() {
		return UserClient.loadFallback(userid);
	}

}
