package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_06_Textbox {
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
		driver.get("http://demo.guru99.com/v4");
	
	}

	@Test
	public void TC_01_Textbox_TextArea() throws InterruptedException, ParseException {
		String username = "mngr241911";
		String password = "uhUdAhy";
		
		String customerName = "Selenium Online";
		String gender = "male";
		String dob = "01-02-2001";
		String addr = "123 Address";
		String city = "Ho Chi Minh";
		String state = "Thu Duc";
		String pin = "123456";
		String mobileNumber = "01239487";
		String email = "test" +  new SimpleDateFormat("MMddhhmmss").format(new Date()) + "@gmail.com";
		System.out.println("Original email: " + email);
		String pass = "123456";
		
		//New info
		String newAddr = "234 Edited Address";
		String newCity = "Toronto";
		String newState = "ON";
		String newPin = "978654";
		String newMobileNumber = "416123478";
		String newEmail = "new" +  new SimpleDateFormat("MMddhhmmss").format(new Date()) + "@gmail.com";
		System.out.println("New email: " + newEmail);
		
		JavascriptExecutor js = (JavascriptExecutor)driver;

		//Login
		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
		
		//Verify homepage displays
		Assert.assertEquals(driver.getCurrentUrl(), "http://demo.guru99.com/v4/manager/Managerhomepage.php");
		
		//Click on new customer
		driver.findElement(By.xpath("//a[@href='addcustomerpage.php']")).click();
		
		//New customer info elements
		WebElement customerNameTxt = driver.findElement(By.name("name"));
		WebElement dobTxt = driver.findElement(By.id("dob"));
		WebElement addrTxt = driver.findElement(By.name("addr"));
		WebElement cityTxt = driver.findElement(By.name("city"));
		WebElement stateTxt = driver.findElement(By.name("state"));
		WebElement pinTxt = driver.findElement(By.name("pinno"));
		WebElement phoneNumTxt = driver.findElement(By.name("telephoneno"));
		WebElement emailTxt = driver.findElement(By.name("emailid"));
		WebElement passTxt = driver.findElement(By.name("password"));
		WebElement submitBtn = driver.findElement(By.name("sub"));	
		
		//Fill in information
		customerNameTxt.sendKeys(customerName);
		js.executeScript("document.getElementById(\"dob\").removeAttribute(\"type\")");
		dobTxt.sendKeys(dob);
		addrTxt.sendKeys(addr);
		cityTxt.sendKeys(city);
		stateTxt.sendKeys(state);
		pinTxt.sendKeys(pin);
		phoneNumTxt.sendKeys(mobileNumber);
		emailTxt.sendKeys(email);
		passTxt.sendKeys(pass);
		submitBtn.click();
		
		//Get customer ID
		String customerId = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		System.out.println(customerId);
		
		//Registered info elements
		WebElement customNameLbl = driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td"));
		WebElement genderLbl = driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td"));
		WebElement birthdayLbl = driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td"));
		WebElement addrLbl = driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td"));
		WebElement cityLbl = driver.findElement(By.xpath("//td[text()='City']/following-sibling::td"));
		WebElement stateLbl = driver.findElement(By.xpath("//td[text()='State']/following-sibling::td"));
		WebElement pinLnl = driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td"));
		WebElement mobileNumLbl = driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td"));
		WebElement emailLbl = driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td"));
		
		//Verify new customer info		
		Assert.assertEquals(customNameLbl.getText(), customerName);
		Assert.assertEquals(genderLbl.getText(), gender);
		Date d = new SimpleDateFormat("MM-dd-yyyy").parse(dob);
		Assert.assertEquals(birthdayLbl.getText(), new SimpleDateFormat("yyyy-dd-MM").format(d));
		Assert.assertEquals(addrLbl.getText(), addr);
		Assert.assertEquals(cityLbl.getText(), city);
		Assert.assertEquals(stateLbl.getText(), state);
		Assert.assertEquals(pinLnl.getText(), pin);
		Assert.assertEquals(mobileNumLbl.getText(), mobileNumber);
		Assert.assertEquals(emailLbl.getText(), email);
		
		//Go to Edit Customer page
		driver.findElement(By.xpath("//a[@href='EditCustomer.php']")).click();
		driver.findElement(By.name("cusid")).sendKeys(customerId);
		driver.findElement(By.name("AccSubmit")).click();
		
		//Verify name and address are correct
		Assert.assertEquals(driver.findElement(By.name("name")).getAttribute("value"), customerName);
		Assert.assertEquals(driver.findElement(By.name("addr")).getText(), addr);
		
		//Edit customer info
		driver.findElement(By.name("addr")).clear();
		driver.findElement(By.name("addr")).sendKeys(newAddr);
		driver.findElement(By.name("city")).clear();
		driver.findElement(By.name("city")).sendKeys(newCity);
		driver.findElement(By.name("state")).clear();
		driver.findElement(By.name("state")).sendKeys(newState);
		driver.findElement(By.name("pinno")).clear();
		driver.findElement(By.name("pinno")).sendKeys(newPin);
		driver.findElement(By.name("telephoneno")).clear();
		driver.findElement(By.name("telephoneno")).sendKeys(newMobileNumber);
		driver.findElement(By.name("emailid")).clear();
		driver.findElement(By.name("emailid")).sendKeys(newEmail);
		driver.findElement(By.name("sub")).click();
		
		//Verify updated info
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), newAddr);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), newCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), newState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), newPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), newMobileNumber);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), newEmail);
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
