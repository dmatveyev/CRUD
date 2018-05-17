package com.denis;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class ApplicationTest {

    public static WebDriver driver;
    private final By UserListTab;
    //Create user tab
    private By createTab;

    private By submit;

    public ApplicationTest() {
        UserListTab = By.id("tab-1");
        //Create user tab
        createTab = By.id("tab-2");
        submit = By.name("submit");
    }

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:/Denis Matveev/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/login");
        WebElement loginButton = driver.findElement(By.id("google"));
        loginButton.click();

        //login by Google OAuth
        //Click GMail login
        driver.findElement(By.id("identifierId")).sendKeys("denismatveyevaleksandrovich@gmail.com");
        driver.findElement(By.id("identifierNext")).click();
        driver.findElement(By.name("password")).sendKeys("123qwe!@#QWE)");
        /*WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext"))).click();*/
        WebElement myelement = driver.findElement(By.id("passwordNext"));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click();", myelement);
        driver.findElement(By.linkText("CRUD"));
    }

    @Before
    public void beforeTest() {
        driver.findElement(By.linkText("CRUD"));
        assertEquals(driver.getCurrentUrl(), "http://localhost:8080/admin#");
    }

    @AfterClass
    public static void tearDown() {
        //driver.quit();
    }

    @Test
    public void firstTest() throws InterruptedException {

    }

    @Test
    public void creataUser() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        /*wait.until(ExpectedConditions.
                        visibilityOfElementLocated(By.linkText("Create new User")));*/
        WebElement createNewUser = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText("Create new User")));
        createNewUser.click();
        WebElement el = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.name("f1")));
        el.findElement(By.name("login")).sendKeys("dentest");

        el.findElement(By.name("pd")).sendKeys("password");

        el.findElement(By.name("email")).sendKeys("d@d.com");

        WebElement submit = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.name("submit")));
        submit.click();

        WebElement usersList = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText("Users list")));
        /*List<WebElement> us = usersList.findElements(By.name("username"));
        for (WebElement element: us) {
            String text =  element.getText();
            System.out.println(text);
            if (text.equals("dentest")){
                assertTrue(true);
            }else {
                assertTrue(false);
            }
        }
        //цикл поиска по таблице.*/

        List<WebElement> rows = driver.findElements(By.cssSelector("tr"));
        boolean found = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String username =  cells.get(1).getText();
            String password =  cells.get(2).getText();
            String email =  cells.get(3).getText();
            System.out.println(username);
            if (username.equals("dentest") && password.equals("password") && email.equals("d@d.com")) {
                // country found, check the document
                found = true;
            }
        }
        assertTrue(found);
    }
}