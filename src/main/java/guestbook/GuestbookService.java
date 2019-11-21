package guestbook;

import guestbook.jdbc.GuestBookRepository;

public class GuestbookService {

    private SpamChecker spamChecker;
    private RateLimiter rateLimiter;
    private GuestBookRepository jpaRepository;

    public GuestbookService() {
        this.spamChecker = new SpamChecker();
        this.rateLimiter = new RateLimiter();
    }

    public void create(GuestbookEntry guestbookEntry, String ipAddress) {
        if (spamChecker.isSpam(guestbookEntry.getContent())) {
            throw new RuntimeException("Spam words in content");
        }

        if (rateLimiter.isRateLimited(ipAddress)) {
            throw new RuntimeException("Ip Address is rate limited");
        }

        jpaRepository.save(guestbookEntry);
    }
}
