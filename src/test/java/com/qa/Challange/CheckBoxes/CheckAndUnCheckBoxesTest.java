package com.qa.Challange.CheckBoxes;

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

public class CheckAndUnCheckBoxesTest {
	WebDriver driver;
	Properties prop;
	
	String checkBox_1 = "//input[@type='checkbox'][1]";
	String checkBox_2 = "//input[@type='checkbox'][2]";
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	public CheckAndUnCheckBoxesTest() {
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
			driver.get(prop.getProperty("checkBoxesURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkBox() {
		try {
			Boolean selected = driver.findElement(By.xpath("//input[@type='checkbox'][1]")).isSelected();
			System.out.println("The Button is selected: " + selected);
			if (selected == false) {
				 System.out.println("###USER SELECTED THE CHECKBOX###");
				driver.findElement(By.xpath(checkBox_1)).click();	
				AssertJUnit.assertTrue(true);
			}else if (selected == true) {
				System.out.println("###Button is selected###");
				AssertJUnit.assertTrue(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkBox2() {
		try {
			Boolean selected = driver.findElement(By.xpath("//input[@type='checkbox'][2]")).isSelected();
			System.out.println("The Button is selected: " + selected);
			if (selected == true) {
				System.out.println("###BUTTON IS ALREADY SELECTED###");
				AssertJUnit.assertTrue(true);
			}else if(selected == false) {
				driver.findElement(By.xpath(checkBox_2)).click();
				System.out.println("### USER SELECTED THE CHECKBOX###");
				AssertJUnit.assertTrue(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void tearDwn() {
		driver.quit();
	}
}
