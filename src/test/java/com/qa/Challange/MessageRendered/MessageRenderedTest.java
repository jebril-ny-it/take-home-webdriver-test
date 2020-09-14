package com.qa.Challange.MessageRendered;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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

public class MessageRenderedTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String message = "//div[@id='flash']";
	String clickHereLink = "//a[contains(text(),'Click here')]";

	public MessageRenderedTest() {
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
			driver.get(prop.getProperty("messageRenderedURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifyRenderedMessages() {
		try {
			WebElement clickHere = driver.findElement(By.xpath(clickHereLink));
			clickHere.click();
			String notificationMessage = driver.findElement(By.xpath(message)).getText();
			if (notificationMessage.contains("Action successful\n" + "×")) {
				AssertJUnit.assertEquals(notificationMessage, "Action successful\n" + "×");
				System.out.println("### THE NOTIFICATION MESSAGE IS: " + notificationMessage);
				System.out.println("### THIS IS THE MESSAGE RENDERED: " + notificationMessage);
			} else if (notificationMessage.contains("Action unsuccesful, please try again\n" + "×")) {
				AssertJUnit.assertEquals(notificationMessage, "Action unsuccesful, please try again\n" + "×");
				System.out.println("### THE NOTIFICATION MESSAGE IS: " + notificationMessage);
				System.out.println("### THIS IS THE MESSAGE RENDERED: " + notificationMessage);
			}
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
