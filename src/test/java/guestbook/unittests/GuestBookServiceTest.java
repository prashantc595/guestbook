package guestbook.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import java.util.LinkedHashMap;

import java.util.List;

import java.util.Map;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import org.springframework.context.ApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import guestbook.GuestbookEntryModel;
import guestbook.GuestbookService;

public class GuestBookServiceTest {

	@Test

	public void getCountryName() {

		ApplicationContext context = new AnnotationConfigApplicationContext("guestbook");

		String tenantName = "XebiaTenant";

		String ipAddress = "172.8.9.28";

		GuestbookService guestBookService = context.getBean(GuestbookService.class, tenantName);

		assertEquals("India", guestBookService.getCountryName(ipAddress));

	}

	@Test

	public void uniqueNamesList() {

		ApplicationContext context = new AnnotationConfigApplicationContext("guestbook");

		String tenantName = "XebiaTenant";

		GuestbookService guestBookService = context.getBean(GuestbookService.class, tenantName);

		List<String> actual = new ArrayList<>();

		actual.add("Prashant");

		actual.add("Abhay");

		Stream<GuestbookEntryModel> first_entry = Stream.of(new GuestbookEntryModel("Prashant", "English"));

		Stream<GuestbookEntryModel> second_entry = Stream.of(new GuestbookEntryModel("Abhay", "Spanish"));

		Stream<GuestbookEntryModel> result = Stream.concat(first_entry, second_entry);

		assertEquals(actual, guestBookService.viewUniqueNames(result));

	}

	@Test

	public void spamGuests() {

		ApplicationContext context = new AnnotationConfigApplicationContext("guestbook");

		String tenantName = "XebiaTenant";

		GuestbookService guestBookService = context.getBean(GuestbookService.class, tenantName);

		Stream<GuestbookEntryModel> first_entry = Stream.of(new GuestbookEntryModel("Prashant", "English"));

		Stream<GuestbookEntryModel> second_entry = Stream.of(new GuestbookEntryModel("Abhay", "English"));

		Stream<GuestbookEntryModel> third_entry = Stream.of(new GuestbookEntryModel("FDS", "English"));

		Stream<GuestbookEntryModel> fourth_entry = Stream.of(new GuestbookEntryModel("LKJ", "English"));

		Stream<GuestbookEntryModel> fifth_entry = Stream.of(new GuestbookEntryModel("hjjj", "English"));

		Stream<GuestbookEntryModel> result = Stream
				.of(first_entry, second_entry, third_entry, fourth_entry, fifth_entry)

				.flatMap(s -> s);

		List<String> actual = new ArrayList<>();

		actual.add("Prashant");

		actual.add("Abhay");

		actual.add("FDS");

		actual.add("LKJ");

		actual.add("hjjj");

		Map<Stream<GuestbookEntryModel>, String> map = new LinkedHashMap<>();

		map.put(first_entry, "spam");

		map.put(second_entry, "spam");

		map.put(third_entry, "spam");

		map.put(fourth_entry, "This is also also alsospam");

		map.put(fifth_entry, "This is a spam");

		assertEquals(actual, guestBookService.postSpam(map));

	}

	@Test

	public void visitorCount() {

		ApplicationContext context = new AnnotationConfigApplicationContext("guestbook");

		String tenantName = "XebiaTenant";

		GuestbookService guestBookService = context.getBean(GuestbookService.class, tenantName);

		Stream<GuestbookEntryModel> first_entry = Stream.of(new GuestbookEntryModel("Prashant", "English"));

		Stream<GuestbookEntryModel> second_entry = Stream.of(new GuestbookEntryModel("Abhay", "English"));

		Stream<GuestbookEntryModel> third_entry = Stream.of(new GuestbookEntryModel("FDS", "English"));

		Stream<GuestbookEntryModel> fourth_entry = Stream.of(new GuestbookEntryModel("LKJ", "English"));

		Stream<GuestbookEntryModel> fifth_entry = Stream.of(new GuestbookEntryModel("hjjj", "English"));

		Stream<GuestbookEntryModel> result = Stream
				.of(first_entry, second_entry, third_entry, fourth_entry, fifth_entry)

				.flatMap(s -> s);

		int actual = 5;

		Map<Stream<GuestbookEntryModel>, String> map = new LinkedHashMap<>();

		map.put(first_entry, "United States");

		map.put(second_entry, "Germany");

		map.put(third_entry, "France");

		map.put(fourth_entry, "United States");

		map.put(fifth_entry, "India");

		assertEquals(actual, guestBookService.visitorCount(map));

	}

	@Test

	public void messageCount() {

		ApplicationContext context = new AnnotationConfigApplicationContext("guestbook");

		String tenantName = "XebiaTenant";

		GuestbookService guestBookService = context.getBean(GuestbookService.class, tenantName);

		Stream<String> first_message = Stream.of("My name is Prashant");

		Stream<String> second_message = Stream.of("Ich heisse Prashant");

		Stream<String> third_message = Stream.of("Me llamo Prashant");

		Stream<String> fourth_message = Stream.of("mon nom est Prashant");

		Stream<String> result = Stream.of(first_message, second_message, third_message, fourth_message).flatMap(s -> s);

		int actual = 4;

		Map<Stream<String>, String> map = new LinkedHashMap<>();

		map.put(first_message, "United States");

		map.put(second_message, "Germany");

		map.put(third_message, "Spain");

		map.put(fourth_message, "France");

		assertEquals(actual, guestBookService.messageCount(map));

	}

}