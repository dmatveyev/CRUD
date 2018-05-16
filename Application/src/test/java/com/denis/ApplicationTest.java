package com.denis;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ApplicationTest {

    public static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:/Denis Matveev/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.get("http://localhost:8080/login");
    }
    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() throws InterruptedException {
        WebElement loginButton =  driver.findElement(By.id("google"));
        loginButton.click();

        //login by Google OAuth
        //Click GMail login
        driver.findElement(By.id("identifierId")).sendKeys("denismatveyevaleksandrovich@gmail.com");
        driver.findElement(By.id("identifierNext")).click();


        driver.findElement(By.name("password")).sendKeys("123qwe!@#QWE)");


        /*WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext"))).click();*/
        WebElement myelement = driver.findElement(By.id("passwordNext"));
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].click();", myelement);

        WebElement header = driver.findElement(By.linkText("CRUD"));

        String adminUrl = driver.getCurrentUrl();
        assertEquals(adminUrl, "http://localhost:8080/admin#");
    }

}