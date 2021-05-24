package objectOperations;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Keywords {
	
	WebDriver driver;
	
	public Keywords(WebDriver wd) {
		this.driver = wd;
	}
	
	public void perform(String operation, String type, String name, String data) {
		switch (operation.toUpperCase()) {
			case "CLICK":
				driver.findElement(this.getElement(type, name)).click();
				break;
			case "ENTER":
				driver.findElement(this.getElement(type, name)).sendKeys(Keys.ENTER);
				break;
			case "SPACECLICK":
				driver.findElement(this.getElement(type, name)).sendKeys(Keys.SPACE);
				break;
			case "SENDKEYS":
				driver.findElement(this.getElement(type, name)).sendKeys(data);
				break;
			case "SELECTBYINDEX":
				Select select = new Select(driver.findElement(this.getElement(type, name)));
				select.selectByIndex(Integer.parseInt(data));
				break;
			case "SELECTBYVALUE":
				Select selectValue = new Select(driver.findElement(this.getElement(type, name)));
				selectValue.selectByValue(data);
				break;
			case "HOVEROVER":
				Actions action = new Actions(driver);
				action.moveToElement(driver.findElement(this.getElement(type, name))).perform();
				break;
			case "OPENCHROME":
			case "OPENFF":
				driver.get(data);
				break;
			case "CLICKANDHOLD":
				Actions actionCH = new Actions(driver);
				actionCH.clickAndHold(driver.findElement(this.getElement(type, name))).perform();
				break;
			case "MAXIMIZE":
				driver.manage().window().maximize();
				break;
			case "SETWINDOWSIZE":
				Dimension dimension = new Dimension(Integer.parseInt(data.split(",")[0]), Integer.parseInt(data.split(",")[1]));
				driver.manage().window().setSize(dimension);
				break;
			case "SWITCHWINDOW":
				for(String curWindow : driver.getWindowHandles()){
				    driver.switchTo().window(curWindow);
				}
				break;
			case "DRAG":
				Actions drag = new Actions(driver);
				WebElement myElement = driver.findElement(this.getElement(type, name));
				WebElement parent = myElement.findElement(By.xpath("./.."));
			    drag.dragAndDrop(myElement, parent).perform();
				break;
			case "CLOSEBROWSER":
				driver.close();
				break;
			case "IMPLICIT":
				driver.manage().timeouts().implicitlyWait(Long.parseLong(data), TimeUnit.SECONDS);
				break;
			case "EXPLICIT":
				WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(data));
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(this.getElement(type, name)));
				break;
			case "FLUENTWAIT":
				Wait<WebDriver> flWait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		
				final Keywords thus = this;
				final String objType = type;
				final String objName = name;
				
				flWait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver ) {
						return driver.findElement(thus.getElement(objType, objName));
					}
				});
				break;
			default:
				break;
		}
	}

	private By getElement(String type, String name) {
		if (type.equalsIgnoreCase("id")) {
			return By.id(name);
		} else {
			return By.xpath(name);
		}
	}
}
