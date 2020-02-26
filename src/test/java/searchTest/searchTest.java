package searchTest;

import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import base.BaseTest;
import extentReport.ExtentTestManager;
import searchPage.searchPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

public class searchTest extends BaseTest {

	searchPage search = new searchPage();

	@Test(priority = 1)
	public void priceDescTest() throws InterruptedException {
		ExtentTestManager.getTest().log(Status.INFO, "Getting price of all cars");
		search.getPriceandYear();
		List<Float> prices = search.getValues().stream().map(f -> Float.valueOf(f)).collect(Collectors.toList());
		ArrayList<Float> sortedPrices = new ArrayList<>();
		sortedPrices.addAll(prices);
		Collections.sort(sortedPrices, Collections.reverseOrder());

		try {
			Assert.assertEquals(sortedPrices, prices);
		} catch (AssertionError e) {
			Assert.assertEquals(sortedPrices, prices, e.getMessage());
		}
	}

	@Test(priority = 2)
	public void yearTest() {
		ExtentTestManager.getTest().log(Status.INFO, "Checking year should be greater than 2015");
		search.getYears();
		for (Integer temp : search.getYears()) {
			Assert.assertTrue(temp >= 2015, "Years");
		}
	}

	@BeforeClass
	public void setUp() {
		initialization();
	}

	@AfterTest
	public void afterMethod() {
		driver.close();
	}
}
