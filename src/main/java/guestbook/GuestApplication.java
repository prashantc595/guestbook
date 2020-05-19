package guestbook;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GuestApplication {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext("guestbook");
		System.out.println("Beans File loaded");

		GuestbookService guestbookService = context.getBean(GuestbookService.class);

	}
}
