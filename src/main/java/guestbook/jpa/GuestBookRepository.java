package guestbook.jpa;

import guestbook.domain.GuestbookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GuestBookRepository extends JpaRepository<GuestbookEntry, Long> {

}
