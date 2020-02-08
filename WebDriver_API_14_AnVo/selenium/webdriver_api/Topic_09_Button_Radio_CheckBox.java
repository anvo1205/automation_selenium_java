package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_09_Button_Radio_CheckBox {
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", "C:\\An\\software\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	public void TC_01_Button_JS() throws InterruptedException {
		driver.get("http://live.guru99.com");
		
		By myAccountLnk = By.xpath("//div[@class='footer']//a[@href='http://live.demoguru99.com/index.php/customer/account/']");
		By createAnAccBtn = By.xpath("//a[@title='Create an Account']");
		By registerBtn = By.xpath("//button[@title='Register']");
		
		js.executeScript("arguments[0].click();", driver.findElement(myAccountLnk));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(createAnAccBtn)));
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/login/");
		
		js.executeScript("arguments[0].click();", driver.findElement(createAnAccBtn));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(registerBtn)));
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_Checkbox() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		
		By chk = By.id("eq5");
		
		driver.findElement(chk).click();
		Assert.assertTrue(driver.findElement(chk).isSelected());
		
		driver.findElement(chk).click();
		Assert.assertFalse(driver.findElement(chk).isSelected());
		
	}
	
	@Test
	public void TC_03_RadioButton() {
		driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");
		
		WebElement radioBtn = driver.findElement(By.id("engine3"));
		
		radioBtn.click();
		if (!radioBtn.isSelected())
		{
			radioBtn.click();
		}
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
