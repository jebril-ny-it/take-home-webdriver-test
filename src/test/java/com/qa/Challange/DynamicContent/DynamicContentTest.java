package com.qa.Challange.DynamicContent;

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

public class DynamicContentTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	public DynamicContentTest() {
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
			driver.get(prop.getProperty("dynamicContentURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(invocationCount = 20)
	public void verifyDynamicContent() {
		
		try {
			WebElement avatar = driver
					.findElement(By.xpath("//div[@class='large-10 columns large-centered']//div[1]//div[1]//img[1]"));
			String avatarAttribute = avatar.getAttribute("src");
			System.out.println(avatarAttribute);
			String[] avatarsplit = avatarAttribute.split("-Profile");
			for (String name : avatarsplit) {
				System.out.println("###THE AVATAR IS: " + name);

				if (name.contains("-Avatar-3")) {
					System.out.println("### CONTACT IS DYNAMIC");
					AssertJUnit.assertEquals(name, "-Avatar-3.jpg");

				} else if (name.contains("-Avatar-7")) {
					System.out.println("### CONTACT IS DYNAMIC");
					AssertJUnit.assertEquals(name, "-Avatar-7.jpg");

				} else if (name.contains("-Avatar-2")) {
					System.out.println("### CONTACT IS DYNAMIC");
					AssertJUnit.assertEquals(name, "-Avatar-2.jpg");

				} else if (name.contains("-Avatar-6")) {
					System.out.println("### CONTACT IS DYNAMIC");
					AssertJUnit.assertEquals(name, "-Avatar-6.jpg");

				} else if (name.contains("-Avatar-5")) {
					System.out.println("### CONTACT IS DYNAMIC");
					AssertJUnit.assertEquals(name, "-Avatar-5.jpg");

				}
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
