package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_05_WebElement_Commands {
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
		driver.get("https://automationfc.github.io/basic-form/index.html");
	}
	

	@Test
	public void TC_01_VerifyElementDisplayed() {
		WebElement emailTxt = driver.findElement(By.id("mail"));
		WebElement educationTxt = driver.findElement(By.id("edu"));
		WebElement ageUnder18Cbb = driver.findElement(By.id("under_18"));
		boolean elementDisplayed = emailTxt.isDisplayed()
								&& educationTxt.isDisplayed()
								&& ageUnder18Cbb.isDisplayed();
		if (elementDisplayed)
		{
			emailTxt.sendKeys("Automation Testing");
			educationTxt.sendKeys("Automation Testing");
			ageUnder18Cbb.click();
		}
	}
	
	@Test
	public void TC_02_VerifyElementEnabledDisabled() {
		WebElement emailTxt = driver.findElement(By.id("mail"));
		WebElement educationTxt = driver.findElement(By.id("edu"));
		WebElement ageUnder18Cbb = driver.findElement(By.id("under_18"));
		WebElement jobRole1 = driver.findElement(By.id("job1"));
		WebElement interestDevelopment = driver.findElement(By.id("development"));
		WebElement slider1 = driver.findElement(By.id("slider-1"));
		
		WebElement password = driver.findElement(By.id("password"));
		WebElement ageRadioDisabled = driver.findElement(By.id("radio-disabled"));
		WebElement biography = driver.findElement(By.id("bio"));
		WebElement jobRole2 = driver.findElement(By.id("job2"));
		WebElement interestDisabled = driver.findElement(By.id("check-disbaled"));
		WebElement slider2 = driver.findElement(By.id("slider-2"));
		
		boolean elementEnabled = emailTxt.isEnabled()
								&& educationTxt.isEnabled()
								&& ageUnder18Cbb.isEnabled()
								&& jobRole1.isEnabled() 
								&& interestDevelopment.isEnabled()
								&& slider1.isEnabled();
		if (elementEnabled)
		{
			System.out.println("Elements are enabled!");
		}
		
		boolean elementDisabled = password.isEnabled()
							   && ageRadioDisabled.isEnabled()
							   && biography.isEnabled()
							   && jobRole2.isEnabled()
							   && interestDisabled.isEnabled()
							   && slider2.isEnabled();
		if (!elementDisabled)
		{
			System.out.println("Elements are disabled!");
		}
	}
	
	@Test
	public void TC_03_VerifyElementSelected() {
		WebElement ageUnder18Cbb = driver.findElement(By.id("under_18"));
		WebElement interestDevelopment = driver.findElement(By.id("development"));
	
		ageUnder18Cbb.click();
		interestDevelopment.click();
		
		Assert.assertTrue(ageUnder18Cbb.isSelected());
		Assert.assertTrue(interestDevelopment.isSelected());
		
		interestDevelopment.click();
		Assert.assertTrue(!interestDevelopment.isSelected());
		
	}


	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
