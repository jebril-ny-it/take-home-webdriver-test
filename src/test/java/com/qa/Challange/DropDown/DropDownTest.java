package com.qa.Challange.DropDown;

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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DropDownTest {
	
	WebDriver driver;
	Properties prop;
	String prop_Path = "/src/main/java/com/qa/Challange/Properties/prop.properties";

	String dropDWN = "dropdown";
	String dropDwnValue1 = "//select[@id='dropdown']//option[@value='1']";
	String dropDwnValue2 = "//select[@id='dropdown']//option[@value='2']";

	
	public DropDownTest() {
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
			driver.get(prop.getProperty("dropDownURL"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void handleDropDwnOption1() {
		try {
			WebElement dropdwn = driver.findElement(By.id(dropDWN));
			dropdwn.click();
			selectFromDropDown("1", dropdwn);
			dropdwn.click();
			String optionSelected = driver.findElement(By.xpath(dropDwnValue1)).getText();
			System.out.println("### THE OPTION SELECTED IS: "+optionSelected);
			AssertJUnit.assertEquals(optionSelected, "Option 1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void handleDropDwnOption2() {
		try {
			WebElement dropdwn = driver.findElement(By.id(dropDWN));
			dropdwn.click();
			selectFromDropDown("2", dropdwn);
			dropdwn.click();
			String optionSelected = driver.findElement(By.xpath(dropDwnValue2)).getText();
			System.out.println("### THE OPTION SELECTED IS: "+optionSelected);
			AssertJUnit.assertEquals(optionSelected, "Option 2");
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
	
	public void selectFromDropDown(String value, WebElement element) {
		try {
			Select select = new Select(element);
			select.selectByValue(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
