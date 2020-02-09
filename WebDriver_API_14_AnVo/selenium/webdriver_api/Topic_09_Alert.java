package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_09_Alert {
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	Alert alert;
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", "C:\\An\\software\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 10);
		
		driver.get("https://automationfc.github.io/basic-form/index.html");
	}

	@Test
	public void TC_01_AcceptAlert() throws InterruptedException {	
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(),"I am a JS Alert");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.id("result")).getText(),"You clicked an alert successfully");
		
	}

	@Test
	public void TC_02_ConfirmAlert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(),"I am a JS Confirm");
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You clicked: Cancel");
	}
	
	@Test
	public void TC_03_PromptAlert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(),"I am a JS prompt");
		alert.sendKeys("Test Alert Textbox");
		alert.accept();
		Assert.assertEquals(driver.findElement(By.id("result")).getText(), "You entered: Test Alert Textbox");
	}
	
	@Test
	public void TC_04_AuthenticationAlert1() {
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		String successMsg = "Congratulations! You must have the proper credentials.";
		Assert.assertTrue(driver.findElement(By.tagName("p")).getText().contains(successMsg));
	}
	
	@Test
	public void TC_05_AuthenticationAlert2() {
		
		String successMsg = "Congratulations! You must have the proper credentials.";
		String userNamePass = "admin";
		
		driver.get("http://the-internet.herokuapp.com");
		
		String lnk = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		byPassAuthenticationAlert(lnk, userNamePass, userNamePass);
		Assert.assertTrue(driver.findElement(By.tagName("p")).getText().contains(successMsg));
	}
	
	public void byPassAuthenticationAlert(String baseUrl, String userName, String pass)
	{
		String[] lnkSplit = baseUrl.split("//");
		String newLnk  = lnkSplit[0] + "//" + userName + ":" + pass
						+ "@" + lnkSplit[1];
		driver.get(newLnk);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
