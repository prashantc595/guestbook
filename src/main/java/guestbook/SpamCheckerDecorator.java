package guestbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpamCheckerDecorator implements SpamChecker {

	@Autowired
	private FrenchSpamChecker frenchSpamChecker;
	@Autowired
	private EnglishSpamChecker englishSpamChecker;

	public SpamCheckerDecorator() {
	}

	public boolean isFrench(String content) {
		return true;
	}

	@Override
	public boolean isSpam(String content) {
		if (isFrench(content)) {
			return frenchSpamChecker.isSpam(content);
		}
		return englishSpamChecker.isSpam(content);
	}

	public FrenchSpamChecker getFrenchSpamChecker() {
		return frenchSpamChecker;
	}

	public void setFrenchSpamChecker(FrenchSpamChecker frenchSpamChecker) {
		this.frenchSpamChecker = frenchSpamChecker;
	}

	public EnglishSpamChecker getEnglishSpamChecker() {
		return englishSpamChecker;
	}

	public void setEnglishSpamChecker(EnglishSpamChecker englishSpamChecker) {
		this.englishSpamChecker = englishSpamChecker;
	}

}
