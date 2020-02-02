package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_06_DropdownList {
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
		driver.get("https://automationfc.github.io/basic-form/index.html");
	
	}

	@Test
	public void TC_02_DropdownList() {
		Select jobRole1_Ddl = new Select(driver.findElement(By.id("job1")));
		Assert.assertFalse(jobRole1_Ddl.isMultiple());
		jobRole1_Ddl.selectByVisibleText("Mobile Testing");
		Assert.assertEquals(jobRole1_Ddl.getFirstSelectedOption().getText(), "Mobile Testing");
		jobRole1_Ddl.selectByValue("manual");
		Assert.assertEquals(jobRole1_Ddl.getFirstSelectedOption().getText(), "Manual Testing");
		jobRole1_Ddl.selectByIndex(9);
		Assert.assertEquals(jobRole1_Ddl.getFirstSelectedOption().getText(), "Functional UI Testing");
		Assert.assertEquals(10, jobRole1_Ddl.getOptions().size());
		
		Select jobRole2_Ddl = new Select(driver.findElement(By.id("job2")));
		Assert.assertTrue(jobRole2_Ddl.isMultiple());

			jobRole2_Ddl.selectByVisibleText("Automation");
			jobRole2_Ddl.selectByVisibleText("Mobile");
			jobRole2_Ddl.selectByVisibleText("Desktop");
			
			List <WebElement> selectedElements = jobRole2_Ddl.getAllSelectedOptions();
			Assert.assertEquals(selectedElements.size(), 3);
			
			List <String> selectedOption = new ArrayList<String>();
			for (WebElement element:selectedElements)
			{
				selectedOption.add(element.getText());
			}
			
			Assert.assertTrue(selectedOption.contains("Automation"));
			Assert.assertTrue(selectedOption.contains("Mobile"));
			Assert.assertTrue(selectedOption.contains("Desktop"));
			
			jobRole2_Ddl.deselectAll();
			Assert.assertEquals(jobRole2_Ddl.getAllSelectedOptions().size(), 0);
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
