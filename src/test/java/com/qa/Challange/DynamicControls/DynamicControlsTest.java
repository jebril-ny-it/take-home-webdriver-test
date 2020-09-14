package com.qa.Challange.DynamicControls;

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

public class DynamicControlsTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String removeButton = "//button[contains(text(),'Remove')]";
	String addButton = "//button[contains(text(),'Add')]";
	String remove_add_Message = "//p[@id='message']";
	
	String enableButton = "//button[contains(text(),'Enable')]";
	String disableButton = "//button[contains(text(),'Disable')]";
	String enable_Disable_Message = "//p[@id='message']";
	
	public DynamicControlsTest() {
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
			driver.get(prop.getProperty("dynamicControlsURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyDynamicControlsRemove_Add() {
		try {
			WebElement removeBTN = driver.findElement(By.xpath(removeButton));
			if (removeBTN.isDisplayed()) {
				removeBTN.click();
				dynamicWait(remove_add_Message);
				String message = driver.findElement(By.xpath(remove_add_Message)).getText();
				System.out.println("###THE DYNAMIC REMOVE MESSAGE IS: "+message);
				AssertJUnit.assertEquals(message, "It's gone!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			try {
				WebElement addBTN = driver.findElement(By.xpath(addButton));
				 if (addBTN.isDisplayed()) {
					addBTN.click();
					dynamicWait(remove_add_Message);
					String message = driver.findElement(By.xpath(remove_add_Message)).getText();
					System.out.println("###THE DYNAMIC ADD MESSAGE IS: "+message);
					AssertJUnit.assertEquals(message, "It's back!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	@Test
	public void verifyDynamicControlsEnable_Disable() {
		try {
			WebElement enableBTN = driver.findElement(By.xpath(enableButton));
			if (enableBTN.isDisplayed()) {
				enableBTN.click();
				dynamicWait(enable_Disable_Message);
				String enableMessage = driver.findElement(By.xpath(enable_Disable_Message)).getText();
				System.out.println("###THE DYNAMIC ENABLE MESSAGE IS: "+enableMessage);
				AssertJUnit.assertEquals(enableMessage, "It's enabled!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			WebElement disableBTN = driver.findElement(By.xpath(disableButton));
			if (disableBTN.isDisplayed()) {
				disableBTN.click();
				dynamicWait(enable_Disable_Message);
				String enableMessage = driver.findElement(By.xpath(enable_Disable_Message)).getText();
				System.out.println("###THE DYNAMIC DISABLED MESSAGE IS: "+enableMessage);
				AssertJUnit.assertEquals(enableMessage, "It's disabled!");
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
	
	public void dynamicWait(String locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
