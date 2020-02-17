package webdriver_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class Topic_10_UserInteraction {
	WebDriver driver;
	Actions action;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.gecko.driver", "C:\\An\\software\\geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(new FirefoxProfile());
		options.addPreference("dom.webnotifications.enable", false);
		driver = new FirefoxDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		wait = new WebDriverWait(driver, 10);
		action = new Actions(driver);
	}

	@Test
	public void TC_01_HiverToElement() {
		driver.get("https://www.myntra.com/");
		action.moveToElement(driver.findElement(By.xpath("//a[@data-group='discover']"))).perform();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@data-reactid='714']"))));
		
		driver.findElement(By.xpath("//a[@data-reactid='714']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='American Eagle']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'American Eagle')]")).isDisplayed());
		
	}

	@Test
	public void TC_02_ClickAndHoldElement_SelectMultipleItems() {
		driver.get("https://jqueryui.com/resources/demos/selectable/display-grid.html");
		
		List<WebElement> numbers = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		action.clickAndHold(numbers.get(0))
								  .moveToElement(numbers.get(3))
								  .release()
								  .perform();
		List<WebElement> selectedNums = driver.findElements(By.xpath("//li[contains(@class,'selected')]"));
		
		//Verify that only 4 numbers are selected
		Assert.assertEquals(selectedNums.size(), 4);
		
		//Verify that correct numbers are selected
		for (int i = 1; i < 5; i++)
		{
			String classAttribute = driver.findElement(By.xpath("//li[text()='" + i + "']")).getAttribute("class");
			Assert.assertTrue(classAttribute.contains("selected"));
		}
		
	}
	
	@Test
	public void TC_03_ClickAndHoldElement_SelectRandomItems() {
		driver.get("https://jqueryui.com/resources/demos/selectable/display-grid.html");
		
		List<WebElement> numbers = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		
		action.keyDown(Keys.CONTROL).perform();
		action.click(numbers.get(0))
		.click(numbers.get(2))
		.click(numbers.get(5))
		.click(numbers.get(10)).perform();
		
		action.keyUp(Keys.CONTROL).perform();
		
		List<WebElement> selectedNums = driver.findElements(By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']"));
		
		Assert.assertEquals(selectedNums.size(), 4);

			Assert.assertEquals(selectedNums.get(0).getText(), "1");
			Assert.assertEquals(selectedNums.get(1).getText(), "3");
			Assert.assertEquals(selectedNums.get(2).getText(), "6");
			Assert.assertEquals(selectedNums.get(3).getText(), "11");
	}
	
	@Test
	public void TC_04_DoubleClick() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//button[text()='Double click me']")));
		action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
		Assert.assertEquals(driver.findElement(By.id("demo")).getText(), "Hello Automation Guys!");
	}
	
	@Test
	public void TC_05_RightClickToElement() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

		action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
		WebElement quitBtn = driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit')]"));
		action.moveToElement(quitBtn).perform();
		Assert.assertTrue(quitBtn.getAttribute("class").contains("context-menu-visible"));
		Assert.assertTrue(quitBtn.getAttribute("class").contains("context-menu-hover"));
		quitBtn.click();
	}
	
	@Test
	public void TC_06_DragAndDropElement() {
		driver.get("https://demos.telerik.com/kendo-ui/dragdrop/angular");

		action.dragAndDrop(driver.findElement(By.id("draggable")), driver.findElement(By.id("droptarget"))).perform();
		
		Assert.assertEquals(driver.findElement(By.id("droptarget")).getText(),"You did great!");
	}
	
	@Test
	public void TC_07_Drag_And_Drop_JS() throws InterruptedException, IOException {
		js = (JavascriptExecutor) driver;
		
		driver.get("http://the-internet.herokuapp.com/drag_and_drop");

		String sourceCss = "#column-a";
		String targetCss = "#column-b";

		String java_script = readFile(System.getProperty("user.dir") + "\\DragAndDrop\\drag_and_drop_helper.js");

		// Inject Jquery lib to site
		// String jqueryscript = readFile(jqueryPath);
		// javascriptExecutor.executeScript(jqueryscript);

		// A to B
		java_script = java_script + "$(\"" + sourceCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(sourceCss)));
		js.executeScript(java_script);
		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(By.id("column-a")).getText(), "B");
		Assert.assertEquals(driver.findElement(By.id("column-b")).getText(), "A");

		// B to A
		js.executeScript(java_script);
		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(By.id("column-a")).getText(), "A");
		Assert.assertEquals(driver.findElement(By.id("column-b")).getText(), "B");
	}
	
	
	@Test
	public void TC_08_DragAndDropHTML5() throws AWTException, InterruptedException {
		driver.get("http://the-internet.herokuapp.com/drag_and_drop");
		
		String sourceXpath = "//div[@id='column-a']";
		String targetXpath = "//div[@id='column-b']";
		
		drag_the_and_drop_html5_by_xpath(sourceXpath, targetXpath);
		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(By.id("column-a")).getText(), "B");
		Assert.assertEquals(driver.findElement(By.id("column-b")).getText(), "A");
		
		
		drag_the_and_drop_html5_by_xpath(sourceXpath, targetXpath);
		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(By.id("column-a")).getText(), "A");
		Assert.assertEquals(driver.findElement(By.id("column-b")).getText(), "B");
	}
	
	public void drag_the_and_drop_html5_by_xpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(1000);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public String readFile(String file) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(file);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
