package ua.lorien.spring.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Elf
 *
 */
/**
 * @author Elf
 *
 */
public class Application {

	public static void main(String[] args) {
		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"context.xml");
	}
}
