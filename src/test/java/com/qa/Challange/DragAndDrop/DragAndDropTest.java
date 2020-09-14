package com.qa.Challange.DragAndDrop;

import org.testng.annotations.Test;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DragAndDropTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String fromColumnA = "//body/div[@class='row']/div[@class='large-12 columns']/div[@class='example']/div[1]//div[@id='column-a']";
	String toColumnB = "//body/div[@class='row']/div[@class='large-12 columns']/div[@class='example']/div[1]//div[@id='column-b']";
	
	public DragAndDropTest() {
		try {
			prop = new Properties();
			FileInputStream fis;
			fis = new FileInputStream(System.getProperty("user.dir")+prop_Path);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void launch() {
		try {
			if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else if (prop.getProperty("browser").equalsIgnoreCase("ff")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.get(prop.getProperty("dragAndDropURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateDragAndDrop() {
		WebElement columnA = driver.findElement(By.xpath(fromColumnA));
		WebElement cloumnB = driver.findElement(By.xpath(toColumnB));
		Actions action = new Actions(driver);
		action.clickAndHold(columnA).moveToElement(cloumnB).release().build().perform();
	}
	
	@AfterMethod
	public void tearDwn() {
		try {
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
