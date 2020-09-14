package com.qa.Challang.NewWindow;

//
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NewWindowTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String clickHereLink = "//a[contains(text(),'Click Here')]";
	String newWindow = "//h3[contains(text(),'New Window')]";

	public NewWindowTest() {
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
			driver.get(prop.getProperty("newWindowURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifyNewWindow() {
		try {
			WebElement clickherelink = driver.findElement(By.xpath(clickHereLink));
			clickherelink.click();
			Set<String> windowhandler = driver.getWindowHandles();
			Iterator<String> it = windowhandler.iterator();
			String parentWindow = it.next();
			System.out.println("####THE PARENT WINDOW ID IS: " + parentWindow);
			String newChildWindow = it.next();
			System.out.println("####THE NEW CHILD WINDOW ID IS:" + newChildWindow);
			driver.switchTo().window(newChildWindow);
			String newWindowMessage = driver.findElement(By.xpath(newWindow)).getText();
			System.out.println("####THE NEW WINDOW MESSAGE IS: " + newWindowMessage);
			AssertJUnit.assertEquals(newWindowMessage, "New Window");
			driver.close();
			driver.switchTo().window(parentWindow);
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
