package guestbook.integrationtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import guestbook.GuestBookConfig;
import guestbook.GuestbookEntryModel;
import guestbook.model.GuestBookEntry;

@ContextConfiguration(classes = TestConfiguration.class)
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class GuestBookControllerTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@BeforeEach
	public void setMockMvc() {

		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void shouldGiveGuestBookDetails() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("details"))
				.andExpect(model().attributeDoesNotExist("users"));
	}
	
	@Test
	public void postBlogDetails() throws Exception {
		GuestBookEntry guestbookEntry = new GuestBookEntry();
		guestbookEntry.setName("Prashant");
		guestbookEntry.setContent("Java");
		mockMvc.perform(post("/").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("name", "Prashant")
				.param("content", "Java"))
				.andExpect(view().name("redirect:/"));

	}

	@Test
	public void shouldGiveGuestBookEntryDetailsByName() {
		try {
			mockMvc.perform(get("/{name}", "Prashant")).andExpect(status().isOk()).andExpect(view().name("details"))
					.andExpect(model().attributeDoesNotExist("users"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}

@Configuration
@ImportResource(locations = "file:src/main/webapp/WEB-INF/guestBookServlet-servlet.xml")
@Import(GuestBookConfig.class)
class TestConfiguration {

}