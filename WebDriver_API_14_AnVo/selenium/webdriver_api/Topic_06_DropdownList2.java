package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_06_DropdownList2 {
	WebDriver driver;
	public static Wait<WebDriver> fluentWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/driver/geckodriver.exe");
		driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get("https://demo.nopcommerce.com/register");
	
	}

	@Test
	public void TC_03_DropdownList2() {
		String email = "test" +  new SimpleDateFormat("MMddhhmmss").format(new Date()) + "@gmail.com";
		System.out.println(email);
		String pass = "123456";
		
		driver.findElement(By.xpath("//a[@href='/register?returnUrl=%2Fregister']")).click();
		driver.findElement(By.id("gender-male")).click();
		driver.findElement(By.id("FirstName")).sendKeys("An");
		driver.findElement(By.id("LastName")).sendKeys("Vo");
		Select day = new Select(driver.findElement(By.name("DateOfBirthDay")));
		day.selectByValue("1");
		Assert.assertEquals(day.getOptions().size(), 32);
		Select month = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		month.selectByValue("5");
		Assert.assertEquals(month.getOptions().size(), 13);
		Select year = new Select(driver.findElement(By.name("DateOfBirthYear")));
		year.selectByValue("1980");
		Assert.assertEquals(year.getOptions().size(), 112);
		driver.findElement(By.id("Email")).sendKeys(email);
		driver.findElement(By.id("Password")).sendKeys(pass);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(pass);
		driver.findElement(By.id("register-button")).click();
		
		//Verify register successfully
		Assert.assertTrue(driver.findElement(By.xpath("//a[@href='/customer/info']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[@href='/logout']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Your registration completed']")).isDisplayed());
		
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
