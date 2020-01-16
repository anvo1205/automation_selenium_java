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

public class Topic_04_WebBrowser_Commands {
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
	public void TC_01_VerifyUrl() {
		Assert.assertEquals(driver.getCurrentUrl(),"http://live.demoguru99.com/index.php/customer/account/login/");
		
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		Assert.assertEquals(driver.getCurrentUrl(),"http://live.demoguru99.com/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_VerifyTitle() {
		Assert.assertEquals(driver.getTitle(),"Customer Login");
		
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		Assert.assertEquals(driver.getTitle(),"Create New Customer Account");

	}
	
	@Test
	public void TC_03_Navigate_Function() {
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		Assert.assertEquals(driver.getCurrentUrl(),"http://live.demoguru99.com/index.php/customer/account/create/");
		
		driver.navigate().back();
		Assert.assertEquals(driver.getCurrentUrl(),"http://live.demoguru99.com/index.php/customer/account/login/");
		
		driver.navigate().forward();
		Assert.assertEquals(driver.getCurrentUrl(),"http://live.demoguru99.com/index.php/customer/account/create/");
	}
	
	@Test
	public void TC_04_getPageSource() {
		String pageSource = driver.getPageSource();
		Assert.assertTrue(pageSource.contains("Login or Create an Account"));
		
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		String pageSource_New = driver.getPageSource();
		Assert.assertTrue(pageSource_New.contains("Create an Account"));
	}
	
	

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
