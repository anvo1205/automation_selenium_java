package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class Topic_10_UserInteraction {
	WebDriver driver;
	Actions action;
	WebDriverWait wait;
	
	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.gecko.driver", "C:\\An\\software\\geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(new FirefoxProfile());
		options.addPreference("dom.webnotifications.enable", false);
		driver = new FirefoxDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		wait = new WebDriverWait(driver, 10);
		action = new Actions(driver);
	}

	@Test
	public void TC_01_HiverToElement() {
		driver.get("https://www.myntra.com/");
		action.moveToElement(driver.findElement(By.xpath("//a[@data-group='discover']"))).perform();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@data-reactid='714']"))));
		
		driver.findElement(By.xpath("//a[@data-reactid='714']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='American Eagle']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'American Eagle')]")).isDisplayed());
		
	}

	@Test
	public void TC_02_ClickAndHoldElement_SelectMultipleItems() {
		driver.get("https://jqueryui.com/resources/demos/selectable/display-grid.html");
		
		List<WebElement> numbers = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		action.clickAndHold(numbers.get(0))
								  .moveToElement(numbers.get(3))
								  .release()
								  .perform();
		List<WebElement> selectedNums = driver.findElements(By.xpath("//li[contains(@class,'selected')]"));
		
		//Verify that only 4 numbers are selected
		Assert.assertEquals(selectedNums.size(), 4);
		
		//Verify that correct numbers are selected
		for (int i = 1; i < 5; i++)
		{
			String classAttribute = driver.findElement(By.xpath("//li[text()='" + i + "']")).getAttribute("class");
			Assert.assertTrue(classAttribute.contains("selected"));
		}
		
	}
	
	@Test
	public void TC_03_ClickAndHoldElement_SelectRandomItems() {
		driver.get("https://jqueryui.com/resources/demos/selectable/display-grid.html");
		
		List<WebElement> numbers = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		
		action.keyDown(Keys.CONTROL).perform();
		action.click(numbers.get(0))
		.click(numbers.get(2))
		.click(numbers.get(5))
		.click(numbers.get(10)).perform();
		
		action.keyUp(Keys.CONTROL).perform();
		
		List<WebElement> selectedNums = driver.findElements(By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']"));
		
		Assert.assertEquals(selectedNums.size(), 4);

			Assert.assertEquals(selectedNums.get(0).getText(), "1");
			Assert.assertEquals(selectedNums.get(1).getText(), "3");
			Assert.assertEquals(selectedNums.get(2).getText(), "6");
			Assert.assertEquals(selectedNums.get(3).getText(), "11");
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
