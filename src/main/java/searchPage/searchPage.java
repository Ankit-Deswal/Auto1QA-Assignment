package searchPage;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseTest;

public class searchPage extends BaseTest {
	// All locators
	String yearSortXpath = "//div[@class='root___198Zr']//div[3]//span[@class='labelText___1_7Q2']";
	String yearDropdownName = "yearRange.min";
	String sortDropdownXpath = "//*[@name='sort' and @class= 'form-control select___1VVkB']";
	String getpriceClassName = "totalPrice___3yfNv";
	String getYearXpath = "//*[@class='specItem___2gMHn'][1]";
	String paginationNextButtn = "//ul[@class='pagination']/li[last()-1]/a/span";
	List<String> values = new ArrayList<String>();
	List<Integer> years = new ArrayList<Integer>();

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public void getPriceandYear() throws InterruptedException {
		driver.findElement(By.xpath(yearSortXpath)).click();
		WebElement testDropDown = driver.findElement(By.name(yearDropdownName));
		Select dropdown = new Select(testDropDown);
		dropdown.selectByVisibleText("2015");
		WebElement testDropDown1 = driver.findElement(By.xpath(sortDropdownXpath));
		Select dropdown1 = new Select(testDropDown1);
		dropdown1.selectByVisibleText("Höchster Preis");

		getCarsPrice();
		getCarsMakeYear();
		String nextButton = driver.findElement(By.xpath("//ul[@class='pagination']/li[last()-1]/a/span"))
				.getAttribute("class");

		while (!nextButton.contains("disabled")) {
			new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class).until(
					ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='pagination']/li[last()-1]/a/span")));
			driver.findElement(By.xpath("//ul[@class='pagination']/li[last()-1]/a/span")).click();
			new WebDriverWait(driver, 60).until(webDriver -> ((JavascriptExecutor) webDriver)
					.executeScript("return document.readyState").equals("complete"));
			getCarsPrice();
			getCarsMakeYear();
			new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class).until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//ul[@class='pagination']/li[last()-1]/a")));
			nextButton = driver.findElement(By.xpath("//ul[@class='pagination']/li[last()-1]/a")).getAttribute("class");
		}
	}

	private void getCarsPrice() throws InterruptedException {
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until((ExpectedCondition<Boolean>) driver -> driver
				.findElement(By.xpath("//*[@id=\"app\"]/div/main/div[3]/div/div[2]/div/div[1]/ul/li[1]/button"))
				.getText().contains("2015"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='link___2Maxt clearfix']")));
		List<WebElement> totalPricePerPage = driver.findElements(By.xpath("//*[@class='totalPrice___3yfNv'][1]"));

		for (WebElement element : totalPricePerPage) {
			int attemptsPrice = 0;
			while (attemptsPrice < 4) {
				try {
					values.add(element.getText().replace("€", "").trim().replace(".", ""));
				} catch (StaleElementReferenceException e) {
				}
				attemptsPrice++;
			}
		}
	}

	private void getCarsMakeYear(){

		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until((ExpectedCondition<Boolean>) driver -> driver
				.findElement(By.xpath("//*[@id=\"app\"]/div/main/div[3]/div/div[2]/div/div[1]/ul/li[1]/button"))
				.getText().contains("2015"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='specItem___2gMHn']")));
		List<WebElement> allYears = driver.findElements(By.xpath("//*[@class='specItem___2gMHn'][1]"));

		for (WebElement element : allYears) {
			int attemptsYear = 0;
			while (attemptsYear < 4) {
				try {
					String yearsonly = element.getText();
					String newString = yearsonly.substring(yearsonly.length() - 4).trim();
					years.add(Integer.parseInt(newString));

				} catch (StaleElementReferenceException e) {
				}
				attemptsYear++;
			}
		}
	}
}
