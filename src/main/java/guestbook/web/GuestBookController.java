package guestbook.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.net.InetAddresses;

import guestbook.GuestbookEntryModel;
import guestbook.GuestbookService;
import guestbook.jdbc.GuestBookRepository;
import guestbook.model.GuestBookEntry;

@Controller
public class GuestBookController {

	@Autowired
	private GuestbookService guestbookService;
	@Autowired
	private GuestBookRepository guestBookRepository;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String get(Model guestBookModel) {
		guestBookModel.addAttribute("data", guestbookService.find().stream()
				.map(g -> new GuestbookEntryModel(g.getName(), g.getContent())).collect(Collectors.toList()));
		return "details";
	}

	@RequestMapping(path = "/groupBy", method = RequestMethod.GET)
	public String getCount(Model guestBookModel) {
		guestBookModel.addAttribute("users", guestbookService.groupByName());
		System.out.println("mmmmmmmmm" + guestbookService.groupByName());
		return "count";
	}

	// @ResponseBody
	@RequestMapping(path = "/{name}", method = RequestMethod.GET)
	public String getByName(@PathVariable String name, Model guestBookModel) {
		guestBookModel.addAttribute("data", guestbookService.find(name));
		return "details";

	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public String postDetails(String name, String content, HttpServletRequest httpServletRequest) throws Exception {

		Random random = new Random();
		String ipAddress = InetAddresses.fromInteger(random.nextInt()).getHostAddress();

		GuestBookEntry guestbookEntry = new GuestBookEntry();
		guestbookEntry.setName(name);
		guestbookEntry.setContent(content);
		guestbookService.create(guestbookEntry, ipAddress);
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(path = "/repoData", method = RequestMethod.GET)
	public String getRepoData() {
		return guestBookRepository.find().toString();
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {
		ModelAndView model = new ModelAndView("thymeleaf/error");
		model.addObject("errMsg", "Customized Exception");
		return model;

	}

}
