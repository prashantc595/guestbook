package guestbook;

public class Location {

	private String countryName;

	public String getCountry(String ipAddress) {
		if (ipAddress.charAt(4) == '1') {
			countryName = "Germany";
		} else if (ipAddress.charAt(4) == '2') {
			countryName = "USA";
		} else if (ipAddress.charAt(4) == '3') {
			countryName = "France";
		} else {
			countryName = "India";
		}

		return countryName;
	}
}
