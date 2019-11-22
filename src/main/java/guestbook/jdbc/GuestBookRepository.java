package guestbook.jdbc;

import guestbook.GuestbookEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GuestBookRepository {
    @Autowired
    JdbcTemplate template;

    @Transactional(readOnly = true)
    public void save(GuestbookEntry guestbookEntry) {
        this.template.update("insert into GUEST_BOOK (NAME, CONTENT) values (?, ?)",guestbookEntry.getName(),guestbookEntry.getContent());
    }

    @Transactional(readOnly = true)
    public int[] save(List<GuestbookEntry> guestbookEntry){
        List<Object[]> value= guestbookEntry.stream()
                .map(a->new Object[]{a.getName(),a.getContent()})
                .collect(Collectors.toList());
        return this.template.batchUpdate(
                "insert into guest_book (name, content) values(?,?)",
                value);
    }

    public Integer count() {
       return this.template.queryForObject("select count(*) from GUEST_BOOK",Integer.class);
    }

    public Integer count(String name) {
        return 0;
    }

    public List<String> getAllContentForGuest(String name) {
        return template.queryForList("select content from GUEST_BOOK where name=?", new Object[]{name},String.class);
    }

    public List<GuestbookEntry> getAll() {
        return null;
    }
}
