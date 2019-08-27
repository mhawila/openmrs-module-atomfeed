package org.openmrs.module.atomfeed.api.utils;

import org.openmrs.PersonAddress;
import org.springframework.util.StringUtils;

public final class FeedFilterUtil {

	private static final String FILTER_DELIMITER = "%";
	private static final String SKIP_VALUE = "*";

	private FeedFilterUtil() { }

	public static boolean isFilterValid(String filter, String filterProperty) {
		String[] filterValues = filter.split(FILTER_DELIMITER);
		String[] filterPropertyValues = filterProperty.split(FILTER_DELIMITER);

		if (filterValues.length != filterPropertyValues.length) {
			return false;
		}

		for (int i = 0; i < filterValues.length; i++) {
			if (filterValueDoesNotMatch(filterPropertyValues[i], filterValues[i])) {
				return false;
			}
		}

		return true;
	}

	public static String createLocationFilter(PersonAddress personAddress, String addressFields) {
		if(!StringUtils.hasLength(addressFields)) {
			return personAddress.getCountry()
				+ FILTER_DELIMITER
				+ personAddress.getStateProvince()
				+ FILTER_DELIMITER
				+ personAddress.getCountyDistrict()
				+ FILTER_DELIMITER
				+ personAddress.getCityVillage()
				+ FILTER_DELIMITER
				+ personAddress.getPostalCode();
		}
		String[] fields = addressFields.split(FILTER_DELIMITER);
		StringBuilder sb = new StringBuilder();
		for(String addressField: fields) {
			switch (addressField) {
				case "address1": sb.append(personAddress.getAddress1()).append(FILTER_DELIMITER); break;
				case "address2": sb.append(personAddress.getAddress2()).append(FILTER_DELIMITER); break;
				case "address3": sb.append(personAddress.getAddress3()).append(FILTER_DELIMITER); break;
				case "address4": sb.append(personAddress.getAddress4()).append(FILTER_DELIMITER); break;
				case "address5": sb.append(personAddress.getAddress5()).append(FILTER_DELIMITER); break;
				case "address6": sb.append(personAddress.getAddress6()).append(FILTER_DELIMITER); break;
				case "country": sb.append(personAddress.getCountry()).append(FILTER_DELIMITER); break;
				case "state_province": sb.append(personAddress.getStateProvince()).append(FILTER_DELIMITER); break;
				case "county_district": sb.append(personAddress.getCountyDistrict()).append(FILTER_DELIMITER); break;
				case "city_village": sb.append(personAddress.getCityVillage()).append(FILTER_DELIMITER); break;
				case "postal_code": sb.append(personAddress.getPostalCode()).append(FILTER_DELIMITER); break;
				case "longitude": sb.append(personAddress.getLongitude()).append(FILTER_DELIMITER); break;
				case "latitude": sb.append(personAddress.getLatitude()).append(FILTER_DELIMITER); break;
				default: sb.append("");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String createLocationFilter(PersonAddress personAddress) {
		return createLocationFilter(personAddress, null);
	}

	private static boolean filterValueDoesNotMatch(String filterPropertyValue, String filterValue) {
		return !SKIP_VALUE.equals(filterPropertyValue)
				&& !filterPropertyValue.equalsIgnoreCase(filterValue);
	}
}
