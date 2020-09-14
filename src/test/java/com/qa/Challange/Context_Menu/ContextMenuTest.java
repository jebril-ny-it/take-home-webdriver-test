package com.qa.Challange.Context_Menu;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ContextMenuTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String contextMenuBox = "//div[@class='example']//div";

	public ContextMenuTest() {
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
			driver.get(prop.getProperty("contextMenuURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void validateContextMenu() {
		try {
			Actions actions = new Actions(driver);
			WebElement menuElement = driver.findElement(By.xpath(contextMenuBox));
			actions.contextClick(menuElement).build().perform();
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			System.out.println("### ALERT TEXT IS: "+alertText);
			AssertJUnit.assertEquals(alertText, "You selected a context menu");
			alert.accept();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
