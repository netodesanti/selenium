package testing;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import excel.ReadExcelFile;

import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import objectOperations.Keywords;

public class FirstTest {
	
	public WebDriver driver;
	public Keywords operations;
	
	@BeforeTest
	public void beforeTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		operations = new Keywords(driver);
	}
  
	@Test
	public void firstChrome() {
		driver.get("https://demoqa.com/text-box");
		
		ReadExcelFile xslFile = new ReadExcelFile();
		ArrayList<String> cells = xslFile.read();
		int index = 0;
		
		do {
			operations.perform(cells.get(index), cells.get(index + 1), cells.get(index + 2), cells.get(index + 3));
			index += 4;
		} while (index < cells.size());
	}

	@AfterTest
	public void afterTest() {
		operations.perform("CLOSEBROWSER", "", "", "");
	}

}
