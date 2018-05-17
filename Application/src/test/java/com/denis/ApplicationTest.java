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

    }

    @AfterClass
    public static void tearDown() {
        //driver.quit();
    }

    @Test
    public void createUserTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        createUser(wait, "dentest", "password", "d@d.com");
        WebElement usersList = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText("Users list")));
        //цикл поиска по таблице.*/
        WebElement targetRow = findUser(driver, "dentest", "password", "d@d.com");
        assertNotNull(targetRow);
    }

    @Test
    public void deleteUserTest() {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement usersList = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText("Users list")));
        //цикл поиска по таблице.*/
        WebElement targetRow = findUser(driver, "dentest", "password", "d@d.com");
        List<WebElement> cells = targetRow.findElements(By.tagName("td"));
        cells.get(cells.size()-1).click();
        assertNull(findUser(driver, "dentest", "password", "d@d.com"));
    }

    private void createUser(final WebDriverWait wait, final String username, final String password, final String email) {
        WebElement createNewUser = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText("Create new User")));
        createNewUser.click();
        WebElement el = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.name("f1")));
        el.findElement(By.name("login")).sendKeys(username);
        el.findElement(By.name("pd")).sendKeys(password);
        el.findElement(By.name("email")).sendKeys(email);
        WebElement submit = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.name("submit")));
        submit.click();
    }

    private WebElement findUser(final WebDriver driver, final String ... args) {
        List<WebElement> rows = driver.findElements(By.cssSelector("tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String username = cells.get(1).getText();
            String password = cells.get(2).getText();
            String email = cells.get(3).getText();
            System.out.println(username);
            if (username.equals(args[0]) && password.equals(args[1]) && email.equals(args[2])) {
                // country found, check the document
                return row;
            }
        }
        return null;
    }

}