package guestbook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class EnglishSpamChecker implements SpamChecker {

	private Set<String> spamWords;

	public EnglishSpamChecker() {
		this.spamWords = new HashSet<>();
		this.spamWords.addAll(Arrays.asList("acne", "adult", ""));
	}

	@Override
	public boolean isSpam(String content) {
		Set<String> words = new HashSet<>(Arrays.asList(content.split("\\s")));
		if (!Collections.disjoint(spamWords, words)) {
			return true;
		}
		return false;
	}
}
