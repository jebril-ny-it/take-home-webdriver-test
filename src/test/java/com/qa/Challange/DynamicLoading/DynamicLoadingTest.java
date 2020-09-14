package com.qa.Challange.DynamicLoading;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DynamicLoadingTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String startButton = "//button[contains(text(),'Start')]";
	String dynamicMessage = "//h4[contains(text(),'Hello World!')]";
	
	public DynamicLoadingTest() {
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
			driver.get(prop.getProperty("dynamicLoadingURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifyDynamicallyLoadedElement() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			driver.findElement(By.xpath(startButton)).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dynamicMessage)));
			WebElement dynamicElement = driver.findElement(By.xpath(dynamicMessage));
			String dynamicElementText = dynamicElement.getText();
			AssertJUnit.assertEquals(dynamicElementText, "Hello World!");
			System.out.println("###DYNAMIC ELEMENT TEXT IS: " + dynamicElementText);
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
