package com.qa.Challange.Hovers;

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
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MouseHoversTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String userOneXpath = "//div[@class='example']//div[1]//img[1]";
	String viewProfile1 = "//div[@class='example']//div[1]//div[1]//a[1]";
	
	String userThreeXpath = "//div[@class='example']//div[3]//img[1]";
	String viewProfile3 = "//div[@class='example']//div[3]//div[1]//a[1]";

	
	public MouseHoversTest() {
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
			driver.get(prop.getProperty("mouseHoversURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void verifyUserOneProfile() {
		try {
			WebElement userOneImage = driver.findElement(By.xpath(userOneXpath));		
			actions(userOneImage);
			driver.findElement(By.xpath(viewProfile1)).click();
			String userOneURL = driver.getCurrentUrl();
			System.out.println("### AFTER SELECTING USER 1 PROFILE THE URL IS: "+userOneURL);
			AssertJUnit.assertEquals(userOneURL, "http://localhost:7080/users/1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyUserThreeProfile() {
		try {
			WebElement userThreeImage = driver.findElement(By.xpath(userThreeXpath));		
			actions(userThreeImage);
			driver.findElement(By.xpath(viewProfile3)).click();
			String userOneURL = driver.getCurrentUrl();
			System.out.println("### AFTER SELECTING USER 3 PROFILE THE URL IS: "+userOneURL);
			AssertJUnit.assertEquals(userOneURL, "http://localhost:7080/users/3");
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
	
	public void actions(WebElement element) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
