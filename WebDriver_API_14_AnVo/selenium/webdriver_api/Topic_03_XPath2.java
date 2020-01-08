package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_03_XPath2 {
	WebDriver driver;
	public static Wait<WebDriver> fluentWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", "C:\\An\\software\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	}

	@Test
	public void TC_05_LoginWithValidEmailAndPassword() {
		String firstName = "Automation";
		String lastName = "Testing";
		String email = "automation_13@gmail.com";

		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("pass")).sendKeys("123123");
		driver.findElement(By.id("send2")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()= 'My Dashboard']")).isDisplayed());
		Assert.assertTrue(driver
				.findElement(
						By.xpath("//p[@class='hello']/strong[text()='Hello, " + firstName + " " + lastName + "!']"))
				.isDisplayed());
		Assert.assertTrue(driver
				.findElement(By
						.xpath("//div[@class='box-content']/p[contains(text(),'" + firstName + " " + lastName + "')]"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='box-content']/p[contains(.,'" + email + "')]"))
				.isDisplayed());
		driver.findElement(By.xpath("//a[@data-target-element='#header-account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();
	}

	@Test
	public void TC_06_CreateAnAccount() throws InterruptedException {
		String firstName = "Automation";
		String lastName = "Testing";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
		String dateAsString = simpleDateFormat.format(new Date());
		String email = "test" + dateAsString + "@gmail.com";
		String password = "123456";

		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		driver.findElement(By.xpath("//button[@title='Register']")).click();

		Assert.assertTrue(
				driver.findElement(By.xpath("//span[text()='Thank you for registering with Main Website Store.']"))
						.isDisplayed());

		driver.findElement(By.xpath("//a[@data-target-element='#header-account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();

		new WebDriverWait(driver, 10).until(
				ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h1[text()='You are now logged out']")));
		Assert.assertEquals(driver.getTitle(), "Home page");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
