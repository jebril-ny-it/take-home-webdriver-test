package com.qa.Challange.Login;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String userNameTab = "username";
	String passWordTab = "password";
	String loginButton = "//button[@class='radius']";
	String login_Message = "//div[@class='flash success']";
	String error_Message = "//div[@class='flash error']";

	public LoginTest() {
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
			driver.get(prop.getProperty("loginURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void validateHappyPathLogin() {
		try {
			driver.findElement(By.id(userNameTab)).sendKeys(prop.getProperty("Happy_Path_User_Name"));
			driver.findElement(By.id(passWordTab)).sendKeys(prop.getProperty("Happy_Path_Password"));
			driver.findElement(By.xpath(loginButton)).click();
			String loginMessage = driver.findElement(By.xpath(login_Message)).getText();
			System.out.println("###SUCCESSFULL LOGIN NOTIFICATION MESSAGE IS: " + loginMessage);
			AssertJUnit.assertEquals(loginMessage, "You logged into a secure area!\n" + "×");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void validateUnHappyPathLogin() {
		try {
			driver.findElement(By.id(userNameTab)).sendKeys(prop.getProperty("UnHappy_Path_User_Name"));
			driver.findElement(By.id(passWordTab)).sendKeys(prop.getProperty("UnHappy_Path_Password"));
			driver.findElement(By.xpath(loginButton)).click();
			String login_Erroe_Message = driver.findElement(By.xpath(error_Message)).getText();
			System.out.println("###ERROR NOTIFICATION MESSAGE IS: " + login_Erroe_Message);
			AssertJUnit.assertEquals(login_Erroe_Message, "Your username is invalid!\n" + "×");
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
