package guestbook.jdbc;

import guestbook.GuestbookEntryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GuestBookRepository {

	@Autowired
	private JdbcTemplate jdbctemplate;

	public void save(GuestbookEntryModel guestbookEntry) {
		jdbctemplate.update("INSERT INTO GUESTBOOK VALUES(?,?)", guestbookEntry.getName(), guestbookEntry.getContent());
	}

	public List<GuestbookEntryModel> find() {
		RowMapper<GuestbookEntryModel> rowMapper = (rs, i) -> new GuestbookEntryModel(rs.getString("name"),
				rs.getString("content"));
		return jdbctemplate.query("SELECT * FROM GUESTBOOK", rowMapper);
	}

	public GuestbookEntryModel find(String name) {
		RowMapper<GuestbookEntryModel> rowMapper = (rs, i) -> new GuestbookEntryModel(rs.getString("name"),
				rs.getString("content"));
		String sql = "SELECT * FROM GUESTBOOK WHERE NAME=?";
		System.out.println(sql);
		return jdbctemplate.queryForObject(sql, new Object[] { name }, rowMapper);
	}

}
