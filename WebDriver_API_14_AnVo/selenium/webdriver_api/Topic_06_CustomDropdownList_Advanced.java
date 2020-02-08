package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_06_CustomDropdownList_Advanced {
	WebDriver driver;
	public static WebDriverWait wait;
	JavascriptExecutor js;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/driver/geckodriver.exe");
		driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		js = (JavascriptExecutor) driver;
	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get("http://multiple-select.wenzhixin.net.cn/examples#basic.html");
	}
	
	@Test
	public void TC_04_CustomDropdownList() throws InterruptedException {
		
		String[] months = {"January","February","March"};
//		String monthsDropdown = "//div[@class='form-group row'][2]//div[@class='ms-parent multiple-select']";
		String monthsDropdown = "//div[@class='form-group row'][4]//div[@class='ms-parent multiple-select']";
		String monthElement = "//div[@class='form-group row'][2]//input[@data-name='selectItem']";
		
		Thread.sleep(2000);
		selectMultipleItemsFromCustomDropdown(monthsDropdown, monthElement, months);
		Assert.assertTrue(verifyElementsAreSelected(monthsDropdown, months));
		
	}

	public boolean verifyElementsAreSelected(String parentXpath, String[] selectedItems) {
		String actualResult = driver.findElement(By.xpath(parentXpath)).getText();
		if (selectedItems.length > 0 && selectedItems.length <= 3)
		{
			String expectedResult = String.join(",", selectedItems);
			if (expectedResult.equalsIgnoreCase(actualResult))
			{
				return true;
			}
			else {
				System.out.println("Selected Items are not correct. The actual result: " + actualResult);
				return false;
			}
		}
		else if (selectedItems.length > 3 && selectedItems.length <= 12)
		{
			return actualResult.equals(selectedItems.length + " of 12 selected");
		}
		else
		{
			return actualResult.equals("All selected");
		}
	}

	
	public void selectMultipleItemsFromCustomDropdown(String parentsXpath, String childrenXpath, String[] selectedItems)
	{
//		driver.findElement(By.xpath(parentsXpath)).click();
		js.executeScript("arguments[0].click();",driver.findElement(By.xpath(parentsXpath)));
		List<WebElement> allItems = driver.findElements(By.xpath(childrenXpath));
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childrenXpath)));
		for (String item: selectedItems)
		{
			for (WebElement element: allItems)
			{
					if (element.getText().equals(item))
					{
						js.executeScript("arguments[0].scrollIntoView(true);",element);
						js.executeScript("arguments[0].click();",element);
						break;
					}
			}
			List<WebElement> selectedElements = driver.findElements(By.xpath("//*[@class='selected']"));
			if (selectedElements.size() == selectedItems.length)
			{
				break;
			}
		}
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
