package guestbook.jdbc;

import guestbook.GuestbookEntry;
import guestbook.config.JdbcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JdbcConfig.class)
public class GuestBookRepositoryTest {

    @Autowired
    GuestBookRepository guestBookRepository;


    @Test
    public void fetchData() {
        assertNotNull(guestBookRepository);
        assertNotNull(guestBookRepository.count());
    }

    @Test
    public void saveData() {
        assertNotNull(guestBookRepository);
        assertEquals(0L, guestBookRepository.count().longValue());
        guestBookRepository.save(new GuestbookEntry("Rahul", "1st post"));
        assertEquals(1L, guestBookRepository.count().longValue());
    }

    @Test
    public void saveAllData() {
        assertNotNull(guestBookRepository);
        assertEquals(0L, guestBookRepository.count().longValue());
        guestBookRepository.save(Arrays.asList(new GuestbookEntry("Rahul", "1st post"),
                new GuestbookEntry("Rahul", "2nd post")));
        assertEquals(2L, guestBookRepository.count().longValue());
    }

}