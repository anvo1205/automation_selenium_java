package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_11_Popup_IFrame {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", "C:\\An\\software\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	@Test
	public void TC_01_Popup() throws InterruptedException {
		String email = "test" +  new SimpleDateFormat("MMddhhmmss").format(new Date()) + "@gmail.com";
		String noThanksBtn = "//div[@id='ulp-layer-793']";
		
		driver.get("https://www.javacodegeeks.com/");
		
		Thread.sleep(10000);
		
		if(isElementDisplay(noThanksBtn))
		{
			Thread.sleep(2000);
			driver.findElement(By.xpath(noThanksBtn)).click();
		}

		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//div[@id='ulp-inline-layer-tgXfmyDevZDZosiD-392']/input")).sendKeys(email);
		driver.findElement(By.xpath("//div[@id='ulp-inline-layer-tgXfmyDevZDZosiD-384']//a[@data-label='Sign up']")).click();
		
	}

	@Test
	public void TC_02_iFrame() {
		//Step 1
		driver.get("https://kyna.vn/");
		
		//Step 2
		WebElement fb_Iframe = driver.findElement(By.xpath("//div[@class='fanpage']//iframe[contains(@src,'facebook.com')]"));
		Assert.assertTrue(fb_Iframe.isDisplayed());
		
		//Step 3
		driver.switchTo().frame(fb_Iframe);
		
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='_1drq']")).getText(), "170K likes");
		
		//Step 4
		driver.switchTo().defaultContent();
		
		WebElement chat_Iframe = driver.findElement(By.id("cs_chat_iframe"));
		Assert.assertTrue(chat_Iframe.isDisplayed());
		
		
		
		//Step 5
		driver.switchTo().frame(chat_Iframe);
		
		WebElement chatTxt = driver.findElement(By.xpath("//div[@class='textarea_wrapper']/textarea"));
		chatTxt.sendKeys("Selenium Testing!");
		chatTxt.sendKeys(Keys.ENTER);
		Assert.assertTrue(driver.findElement(By.xpath("//form[contains(@class,'meshim_widget_components_chatWindow_profileMenu_Edit')]")).isDisplayed());
		
		//Step 6
		driver.switchTo().defaultContent();
		
		WebElement searchTxt = driver.findElement(By.id("live-search-bar"));
		WebElement searchBtn = driver.findElement(By.xpath("//button[contains(@class,'search-button')]"));
		searchTxt.sendKeys("Java");
		searchBtn.click();
		
		Assert.assertEquals(driver.findElement(By.xpath("//h1")).getText(), "'Java'");
		
	}
	
	public boolean isElementDisplay(String xpath)
	{
		try
		{
			return driver.findElement(By.xpath(xpath)).isDisplayed();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
