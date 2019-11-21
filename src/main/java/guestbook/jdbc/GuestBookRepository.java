package guestbook.jdbc;

import guestbook.GuestbookEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class GuestBookRepository {
    @Autowired
    JdbcTemplate template;

    public void save(GuestbookEntry guestbookEntry) {
        this.template.update("insert into GUEST_BOOK (NAME, CONTENT) values (?, ?)",guestbookEntry.getName(),guestbookEntry.getContent());
    }

    public Integer count() {
       return this.template.queryForObject("select count(*) from GUEST_BOOK",Integer.class);
    }

    public Integer count(String name) {
        return 0;
    }


}
