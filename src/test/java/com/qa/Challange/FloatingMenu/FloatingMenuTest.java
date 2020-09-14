package com.qa.Challange.FloatingMenu;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FloatingMenuTest {

	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String floatingmenu = "//div[@id='menu']";

	public FloatingMenuTest() {
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
			driver.get(prop.getProperty("floatingMenuURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void selectHome() {
		try {
			scrollDwn();
			driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
			String url = driver.getCurrentUrl();
			System.out.println("### MY CURRENT URL IS: " + url);
			AssertJUnit.assertEquals(url, "http://localhost:7080/floating_menu#home");
			isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void selectNews() {
		try {
			scrollDwn();
			driver.findElement(By.xpath("//a[contains(text(),'News')]")).click();
			String url = driver.getCurrentUrl();
			System.out.println("### MY CURRENT URL IS: " + url);
			AssertJUnit.assertEquals(url, "http://localhost:7080/floating_menu#news");
			isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void selectContact() {
		try {
			scrollDwn();
			driver.findElement(By.xpath("//a[contains(text(),'Contact')]")).click();
			String url = driver.getCurrentUrl();
			System.out.println("### MY CURRENT URL IS: " + url);
			AssertJUnit.assertEquals(url, "http://localhost:7080/floating_menu#contact");
			isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void selectabout() {
		try {
			scrollDwn();
			driver.findElement(By.xpath("//a[contains(text(),'About')]")).click();
			String url = driver.getCurrentUrl();
			System.out.println("### MY CURRENT URL IS: " + url);
			AssertJUnit.assertEquals(url, "http://localhost:7080/floating_menu#about");
			isDisplayed();
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

	public void scrollDwn() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,1000)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void isDisplayed() {
		try {
			Boolean displayed = driver.findElement(By.xpath(floatingmenu)).isDisplayed();
			System.out.println("### IS THE FLOATING MENU DISPLAYED??? " + displayed);
			Assert.assertTrue(displayed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
