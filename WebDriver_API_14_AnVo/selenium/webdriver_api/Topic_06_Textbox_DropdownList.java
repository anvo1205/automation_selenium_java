package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_06_Textbox_DropdownList {
	WebDriver driver;
	public static Wait<WebDriver> fluentWait;

	@BeforeClass
	public void beforeClass() {
//		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/driver/geckodriver.exe");
//		driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get("http://demo.guru99.com/v4");
	
	}

	@Test
	public void TC_01_Textbox_TextArea() throws InterruptedException {
		String username = "mngr241911";
		String password = "uhUdAhy";
		
		String customerName = "Selenium Online";
		String dob = "01022001";
		String addr = "123 Address";
		String city = "Ho Chi Minh";
		String state = "Thu Duc";
		String pin = "123456";
		String mobileNumber = "01239487";
		String email = "test" +  new SimpleDateFormat("MMddhhmmss").format(new Date()) + "@gmail.com";
		String pass = "123456";
		
		JavascriptExecutor js = (JavascriptExecutor)driver;

		//Login
		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
		
		//Verify homepage displays
		Assert.assertEquals(driver.getCurrentUrl(), "http://demo.guru99.com/v4/manager/Managerhomepage.php");
		
		//Click on new customer
		driver.findElement(By.xpath("//a[@href='addcustomerpage.php']")).click();
		
		//Fill in information
		driver.findElement(By.name("name")).sendKeys(customerName);
//		driver.findElement(By.id("dob")).click();
//		driver.findElement(By.id("dob")).click();
		driver.findElement(By.name("dob")).sendKeys(dob);
//		js.executeScript("arguments[0].setAttribute('value', '"+ dob + "')", driver.findElement(By.id("dob")));
		driver.findElement(By.name("addr")).sendKeys(addr);
		driver.findElement(By.name("city")).sendKeys(city);
		driver.findElement(By.name("state")).sendKeys(state);
		driver.findElement(By.name("pinno")).sendKeys(pin);
		driver.findElement(By.name("telephoneno")).sendKeys(mobileNumber);
		driver.findElement(By.name("emailid")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(pass);
		driver.findElement(By.name("sub")).click();
		
		//Verify new customer info
		Thread.sleep(1000);
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
