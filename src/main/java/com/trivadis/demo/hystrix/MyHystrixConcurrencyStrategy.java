package com.trivadis.demo.hystrix;

import javax.servlet.http.HttpServlet;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

public class MyHystrixConcurrencyStrategy extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public MyHystrixConcurrencyStrategy(){
		HystrixPlugins.getInstance().registerConcurrencyStrategy(HystrixConcurrencyStrategyDefault.getInstance());
	}

}

//https://github.com/WASdev/sample.netflixoss.wlp


//public class TestHystrixConcurrencyStrategyDefault extends HystrixConcurrencyStrategy {
//
//	private static final Logger LOG = getLogger(TestHystrixConcurrencyStrategyDefault.class);
//
//
//	@Override
//	public ThreadPoolExecutor getThreadPool(final HystrixThreadPoolKey threadPoolKey,
//																					com.netflix.hystrix.strategy.properties.HystrixProperty<Integer> corePoolSize, com.netflix.hystrix.strategy.properties.HystrixProperty<Integer> maximumPoolSize,
//																					com.netflix.hystrix.strategy.properties.HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
//
//		final ThreadFactory threadFactory = new WasThreadFactory();
//
//		return new ThreadPoolExecutor(corePoolSize.get(), maximumPoolSize.get(), keepAliveTime.get(), unit, workQueue,
//			threadFactory);
//	}
//
//	static class WasThreadFactory implements ThreadFactory {
//
//		@Override
//		public Thread newThread(Runnable r) {
//			Validate.notNull(r, "Runnable is not supposed to be null!");
//			return new WasThreadManagerThread(r);
//		}
//	}
//
//	static class WasThreadManagerThread extends Thread {
//
//		final Runnable runnable;
//
//		WasThreadManagerThread(Runnable runnable) {
//			this.runnable = runnable;
//		}
//
//		@Override
//		public synchronized void start() {
//			try {
//				InitialContext ctx = new InitialContext();
//				WorkManager workManager = (WorkManager) ctx.lookup("wm/hystrix");
//				LOG.debug("wm/hystrix Workmanager is being used.");
//				workManager.schedule(new WasWork(runnable));
//			} catch (Exception e) {
//				LOG.error("An error occurred with the wm/hystrix workmanager", e);
//			}
//		}
//	}
//
//	static class WasWork implements Work {
//		final Runnable runnable;
//
//		WasWork(Runnable runnable) {
//			this.runnable = runnable;
//		}
//
//		@Override
//		public void release() {
//			//This implementation is empty, since we expect the Runnable to terminate based on some specific shutdown signal.
//		}
//
//		@Override
//		public boolean isDaemon() {
//			return false;
//		}
//
//		@Override
//		public void run() {
//			runnable.run();
//		}
//	}
//}
//}
