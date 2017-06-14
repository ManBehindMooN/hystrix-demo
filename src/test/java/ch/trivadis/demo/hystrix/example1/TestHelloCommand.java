package ch.trivadis.demo.hystrix.example1;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Future;

import org.junit.Test;

import rx.Observable;

public class TestHelloCommand {

	//Synchronous execution
	@Test
	public void testCommand() {
		String result = new HelloCommand("Hello World!").execute();
		assertEquals("HelloCommand: Hello World!", result);
	}

	//Asynchronous execution
	@Test
	public void testAsynch() {
		Future<String> f = new HelloCommand("Hello World!").queue();

		String s = null;
		try {
			s = f.get();
		} catch (Exception e) {
			// handle future get() exception here
		}
		assertEquals("HelloCommand: Hello World!", s);
	}
	
	//Reactive execution (simple)
	@Test
	public void testReactiveSimple(){
		Observable<String> o = new HelloCommand("Hello World!").observe();
		String s = o.toBlocking().single();
		assertEquals("HelloCommand: Hello World!", s);
	}
	

}
