package guestbook.jdbc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import guestbook.model.GuestBookEntry;

@Repository
public interface GuestBookJPARepository extends JpaRepository<GuestBookEntry, Long> {

	GuestBookEntry findByName(String name);

	@Query(value = "SELECT name, COUNT(content) from GuestBookEntry GROUP BY name", nativeQuery = true)
	List<String[]> countTotalContentsByUser();

}
