package guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GuestbookController {
    private GuestbookEntryVO savedValue;

    @GetMapping({"/"})
    public String guestBook(Model model) {
        model.addAttribute("guestbookentry", new GuestbookEntryVO());
        model.addAttribute("latest",savedValue);
        return "guestbook";
    }

    @PostMapping({"/"})
    public String postGuestBook(GuestbookEntryVO entryVO, Model model) {
        System.out.println("Saving  :" + entryVO);
        savedValue = entryVO;
        return "redirect:/";
    }
}