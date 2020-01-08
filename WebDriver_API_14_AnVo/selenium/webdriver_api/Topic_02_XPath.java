package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_02_XPath {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", "C:\\An\\software\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}
	
	@BeforeMethod
	public void beforeMethod()
	{
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	}
	

	@Test
	public void TC_01_LoginWithEmptyEmailAndPassword() {
		String errorMsg = "This is a required field.";
		
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.id("send2")).click();
		
		String emailErrorMsg = driver.findElement(By.id("advice-required-entry-email")).getText();
		Assert.assertEquals(emailErrorMsg, errorMsg);
		
		String passErrorMesg = driver.findElement(By.id("advice-required-entry-pass")).getText();
		Assert.assertEquals(passErrorMesg, errorMsg);
	}

	@Test
	public void TC_02_LoginWithInvalidEmail() {
		String expectedInvalidEmailErrorMsg = "Please enter a valid email address. For example johndoe@domain.com.";
		String emptyFieldErrorMsg = "This is a required field.";
		
		driver.findElement(By.id("email")).sendKeys("invalidemail@1234");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.id("send2")).click();
		
		String emailErrorMsg = driver.findElement(By.id("advice-validate-email-email")).getText();
		Assert.assertEquals(emailErrorMsg, expectedInvalidEmailErrorMsg);
		
		String passErrorMesg = driver.findElement(By.id("advice-required-entry-pass")).getText();
		Assert.assertEquals(passErrorMesg, emptyFieldErrorMsg);
	}
	
	@Test
	public void TC_03_LoginWithPasswordLessThan6Characters() {
		String passLengthErrorMsg = "Please enter 6 or more characters without leading or trailing spaces.";
		
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.id("send2")).click();
		
		String actualErrorMsg = driver.findElement(By.id("advice-validate-password-pass")).getText();
		Assert.assertEquals(actualErrorMsg, passLengthErrorMsg);
	}
	
	@Test
	public void TC_04_LoginWithIncorrectPassword() {
		String incorrectPassErrorMsg = "Invalid login or password.";
		
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123123123");
		driver.findElement(By.id("send2")).click();
		
		String actualErrorMsg = driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText();
		Assert.assertEquals(actualErrorMsg, incorrectPassErrorMsg);
	}
	

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
