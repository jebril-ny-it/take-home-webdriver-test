package com.qa.Challange.JSalerts;

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
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class JavaScriptAlertsTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String result = "//p[@id='result']";
	String jsAlertButton = "//button[contains(text(),'Click for JS Alert')]";
	String jsConfirmButton = "//button[contains(text(),'Click for JS Confirm')]";
	String jsPromptButton = "//button[contains(text(),'Click for JS Prompt')]";

	public JavaScriptAlertsTest() {
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
			driver.get(prop.getProperty("alertsJSURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifyJsAlertDismiss() {
		try {
			WebElement jsAlertText = driver.findElement(By.xpath(jsAlertButton));
			jsAlertText.click();
			Alert alert = driver.switchTo().alert();
			String alertmessage = alert.getText();
			System.out.println("###ALERT MESSAGE IS: " + alertmessage);
			AssertJUnit.assertEquals(alertmessage, "I am a JS Alert");
			alert.dismiss();
			String resultText = driver.findElement(By.xpath(result)).getText();
			System.out.println("###THE RESULT###: " + resultText);
			AssertJUnit.assertEquals(resultText, "You successfuly clicked an alert");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void verifyConformationOfJsAlert() {
		try {
			driver.findElement(By.xpath(jsConfirmButton)).click();
			Alert alert = driver.switchTo().alert();
			String alertmessage = alert.getText();
			System.out.println("###ALERT MESSAGE IS: " + alertmessage);
			AssertJUnit.assertEquals(alertmessage, "I am a JS Confirm");
			alert.accept();
			String resultText = driver.findElement(By.xpath(result)).getText();
			System.out.println("###THE RESULT###: " + resultText);
			AssertJUnit.assertEquals(resultText, "You clicked: Ok");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void validateJspromptField() {
		try {
			driver.findElement(By.xpath(jsPromptButton)).click();
			Alert alert = driver.switchTo().alert();
			String alertmessage = alert.getText();
			System.out.println("###ALERT MESSAGE IS: " + alertmessage);
			AssertJUnit.assertEquals(alertmessage, "I am a JS prompt");
			alert.sendKeys("### MY NAME IS JEBRIL, AND I WOULD LOVE TO JOIN YOUR COMPANY!");
			alert.accept();
			String resultText = driver.findElement(By.xpath(result)).getText();
			System.out.println("###THE RESULT###: " + resultText);
			AssertJUnit.assertEquals(resultText,
					"You entered: ### MY NAME IS JEBRIL, AND I WOULD LOVE TO JOIN YOUR COMPANY!");
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
