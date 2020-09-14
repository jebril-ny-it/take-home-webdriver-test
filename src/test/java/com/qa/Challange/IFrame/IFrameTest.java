package com.qa.Challange.IFrame;

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

public class IFrameTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String iFrameId = "mce_0_ifr";
	String innerFrameFiledXpath = "//body[@class='mce-content-body ']";

	public IFrameTest() {
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
			driver.get(prop.getProperty("iFrameURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifyIFrame() {
		try {
			System.out.println("### BEFORE FRAME ######");
			driver.switchTo().frame(iFrameId);
			System.out.println("### INSIDE THE FRAME ####");
			WebElement innerFrameField = driver.findElement(By.xpath(innerFrameFiledXpath));
			innerFrameField.clear();
			innerFrameField.sendKeys("### THE USER IS NOW INSIDE THIS IFRAME! ###");
			String textEntered = driver.findElement(By.xpath(innerFrameFiledXpath)).getText();
			System.out.println("### THE TEXT I ENTERED IS: "+textEntered);
			AssertJUnit.assertEquals(textEntered, "### THE USER IS NOW INSIDE THIS IFRAME! ###");
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
