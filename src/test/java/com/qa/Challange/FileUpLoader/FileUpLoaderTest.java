package com.qa.Challange.FileUpLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileUpLoaderTest {
	
	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String chooseFile = "//input[@name='file']";
	String filePath = 	"/src/test/resources/Motivation.png";
	
	String uploadButton = "//input[@class='button']";
	String uploadConformationMessage = "//h3[contains(text(),'File Uploaded!')]";
	
	public FileUpLoaderTest() {
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
			driver.get(prop.getProperty("FileUpLoaderURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void verifyFileUpload() {
		try {
			driver.findElement(By.xpath(chooseFile)).sendKeys(System.getProperty("user.dir")+filePath);
			driver.findElement(By.xpath(uploadButton)).click();
			String fileUpladed = driver.findElement(By.xpath(uploadConformationMessage)).getText();
			System.out.println("### CONFORMATION MESSAGE IS: "+fileUpladed);
			AssertJUnit.assertEquals(fileUpladed, "File Uploaded!");
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
