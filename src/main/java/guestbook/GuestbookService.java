package guestbook;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.net.InetAddresses;

import guestbook.jdbc.GuestBookJPARepository;
import guestbook.jdbc.GuestBookRepository;
import guestbook.model.GuestBookEntry;

@Transactional
public class GuestbookService {
	private GuestBookJPARepository guestBookJPARepository;

	private SpamChecker spamChecker;
	private RateLimiter rateLimiter;
	// private JpaRepository jpaRepository;
	private GuestBookRepository guestBookRepository;
	private String tenantName;
	private String IpAddress;;

	@Autowired
	public GuestbookService(@Value("${computername}") String tenantName, SpamCheckerDecorator spamCheckerDecorator,
			RateLimiter rateLimiter, GuestBookJPARepository guestBookJPARepository) {
		this.tenantName = tenantName;
		this.spamChecker = spamCheckerDecorator;
		this.rateLimiter = rateLimiter;
		this.guestBookJPARepository = guestBookJPARepository;

	}

	public GuestbookService() {
	}

	public void create(GuestBookEntry guestbookEntry, String ipAddress) {

		/*
		 * if (spamChecker.isSpam(guestbookEntry.getContent())) { throw new
		 * RuntimeException("Spam words in content"); }
		 */

		if (rateLimiter.isRateLimited(ipAddress)) {
			throw new RuntimeException("Ip Address is rate limited");
		}

		guestBookJPARepository.save(guestbookEntry);

	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public boolean isIPValid(String IpAddress) {
		if (InetAddresses.isInetAddress(IpAddress)) {
			return true;
		} else
			return false;
	}

	public String getCountryName(String IpAddress) {
		Location location = new Location();
		if (isIPValid(IpAddress)) {
			return location.getCountry(IpAddress);
		} else
			return "IPAddress not Valid";

	}

	public List<String> viewUniqueNames(Stream<GuestbookEntryModel> guestbookEntry) {
		List<GuestbookEntryModel> list = guestbookEntry.collect(Collectors.toList());
		ArrayList<GuestbookEntryModel> guestList = new ArrayList<GuestbookEntryModel>(list);
		List<String> nameList = new ArrayList<>();
		for (GuestbookEntryModel names : guestList) {
			nameList.add(names.getName());
		}
		return nameList;
	}

	public List<String> postSpam(Map<Stream<GuestbookEntryModel>, String> map) {
		List<String> nameList = new ArrayList<>();
		for (Map.Entry<Stream<GuestbookEntryModel>, String> entry : map.entrySet()) {
			if (entry.getValue().contains("spam"))
				nameList.add(entry.getKey().findFirst().get().getName());
		}
		return nameList;
	}

	public int visitorCount(Map<Stream<GuestbookEntryModel>, String> map) {
		int count = 0;
		for (Map.Entry<Stream<GuestbookEntryModel>, String> entry : map.entrySet()) {
			if (entry.getValue() != null)
				count = map.size();
		}
		return count;
	}

	public int messageCount(Map<Stream<String>, String> map) {
		int count = 0;
		for (Map.Entry<Stream<String>, String> entry : map.entrySet()) {
			if (entry.getKey() != null)
				count = map.size();
		}
		return count;
	}

	public List<GuestBookEntry> find() {
		// TODO Auto-generated method stub
		return guestBookJPARepository.findAll();
	}

	public GuestBookEntry find(String name) {
		return guestBookJPARepository.findByName(name);
	}

	public List<String[]> groupByName() {
		return guestBookJPARepository.countTotalContentsByUser();
	}

}
