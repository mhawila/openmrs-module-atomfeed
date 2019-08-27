package org.openmrs.module.atomfeed.api.utils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.PersonAddress;

public class FeedFilterUtilTest {

	private static final String FILTER = "Poland%Pomorskie%Gdansk%Tczew%80-456";
	private static final String FILTER_PROPERTY_1 = "Poland%*%*%*%*";
	private static final String FILTER_PROPERTY_2 = "*%*%*%*%80-456";
	private static final String FILTER_PROPERTY_3 = "USA%*%*%*%*";
	private static final String BAD_FILTER_PROPERTY_1 = "%*%*%*%*";
	private static final String BAD_FILTER_PROPERTY_2 = "*%*%*%*";
	private static PersonAddress personAddress;

	@BeforeClass
	public static void setup() {
		personAddress = new PersonAddress();
		personAddress.setCountry("Poland");
		personAddress.setStateProvince("Pomorskie");
		personAddress.setCountyDistrict("Gdansk");
		personAddress.setCityVillage("Tczew");
		personAddress.setPostalCode("80-456");
		personAddress.setAddress1("address one");
		personAddress.setAddress2("address two");
		personAddress.setAddress3("address three");
		personAddress.setAddress4("address 4");
		personAddress.setAddress5("address 5");
		personAddress.setAddress6("address six");
		personAddress.setLongitude("1.90000");
		personAddress.setLatitude("2.50000");
	}

	@Test
	public void testFilterValidation() {
		Assert.assertTrue(FeedFilterUtil.isFilterValid(FILTER, FILTER_PROPERTY_1));
		Assert.assertTrue(FeedFilterUtil.isFilterValid(FILTER, FILTER_PROPERTY_2));

		Assert.assertFalse(FeedFilterUtil.isFilterValid(FILTER, FILTER_PROPERTY_3));
	}

	@Test
	public void testBadFormattedFilters() {
		Assert.assertFalse(FeedFilterUtil.isFilterValid(FILTER, BAD_FILTER_PROPERTY_1));
		Assert.assertFalse(FeedFilterUtil.isFilterValid(FILTER, BAD_FILTER_PROPERTY_2));
	}

	@Test
	public void testCreatingDefaultFilter() {
		// Default
		String createdFilter = FeedFilterUtil.createLocationFilter(personAddress);

		Assert.assertEquals(FILTER, createdFilter);
	}

	@Test
	public void testCreatingCustomFilter() {

		// Custom configuration
		String fields = "address1%address2%address3%address4%address5%address6%longitude%latitude%city_village";
		String expectedFilter = "address one%address two%address three%address 4%address 5%address six%1.90000%2.50000%Tczew";

		String createdFilter = FeedFilterUtil.createLocationFilter(personAddress, fields);
		Assert.assertEquals(expectedFilter, createdFilter);
	}
}
