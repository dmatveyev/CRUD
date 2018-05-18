package com.denis;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.Arrays;
import java.util.Collection;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ApplicationTest {

    private static WebDriver driver;

    private String username;
    private String password;
    private String email;

    public ApplicationTest(final String username, final String password, final String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[] [] data = {
                {"dentest", "password", "email"},
                {"dentest12", "password12", "email12"}

        };
        return Arrays.asList(data);
    }

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:/Denis Matveev/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/login");
        final WebElement loginButton = driver.findElement(By.id("google"));
        loginButton.click();

        //login by Google OAuth
        //Click GMail login
        driver.findElement(By.id("identifierId")).sendKeys("denismatveyevaleksandrovich@gmail.com");
        driver.findElement(By.id("identifierNext")).click();
        driver.findElement(By.name("password")).sendKeys("123qwe!@#QWE)");
        /*WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.elementToBeClickable(By.id("passwordNext"))).click();*/
        final WebElement myelement = driver.findElement(By.id("passwordNext"));
        final JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click();", myelement);
        driver.findElement(By.linkText("CRUD"));
    }

    @Before
    public void beforeTest() {
        driver.findElement(By.linkText("CRUD"));

    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void createUserTest() throws InterruptedException {
        final WebDriverWait wait = new WebDriverWait(driver, 15);
        createUser(wait, username, password, email);
        final WebElement usersList = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText("Users list")));
        //цикл поиска по таблице.*/
        final WebElement targetRow = findUser(driver, username, password, email);
        assertNotNull(targetRow);
    }

    @Test
    public void editUser() {
        final WebDriverWait wait = new WebDriverWait(driver, 15);
        final WebElement targetRow = findUser(driver, username, password, email);
        final List<WebElement> cells = targetRow.findElements(By.tagName("td"));
        cells.get(cells.size()-2).click();
        final WebElement modal = targetRow.findElement(By.className("modal-dialog"));
        final WebElement formGroup = modal.findElement(By.className("form-group"));
        final List<WebElement> formControls = formGroup.findElements(By.className("form-control"));
        for (final WebElement element:formControls) {
            if (element.getAttribute("name").equals("roles")) {
                element.clear();
                element.sendKeys("ROLE_ADMIN");
            }
        }
        final WebElement footer =  modal.findElement(By.className("modal-footer"));
        final WebElement buttonSubmin = footer.findElement(By.id("submit"));
        buttonSubmin.click();
        final WebElement userRow = findUser(driver, username, password, email);
        final WebElement roles = userRow.findElement(By.name("roles"));
        assertEquals("ROLE_ADMIN", roles.getAttribute("value"));
    }

    @Test
    public void deleteUserTest() {
        final WebDriverWait wait = new WebDriverWait(driver, 15);
        final WebElement usersList = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText("Users list")));
        //цикл поиска по таблице.*/
        final WebElement targetRow = findUser(driver, username, password, email);
        final List<WebElement> cells = targetRow.findElements(By.tagName("td"));
        cells.get(cells.size()-1).click();
        assertNull(findUser(driver, username, password, email));
    }

    private static void createUser(final WebDriverWait wait, final String username, final String password, final String email) {
        final WebElement createNewUser = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.linkText("Create new User")));
        createNewUser.click();
        final WebElement el = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.name("f1")));
        el.findElement(By.name("login")).sendKeys(username);
        el.findElement(By.name("pd")).sendKeys(password);
        el.findElement(By.name("email")).sendKeys(email);
        final WebElement submitButton = wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.name("submit")));
        submitButton.click();
    }

    private static WebElement findUser(final WebDriver webDriver, final String... args) {
        final List<WebElement> rows = webDriver.findElements(By.cssSelector("tr"));
        for (final WebElement row : rows) {
            final List<WebElement> cells = row.findElements(By.tagName("td"));
            final String username = cells.get(1).getText();
            final String password = cells.get(2).getText();
            final String email = cells.get(3).getText();
            if (username.equals(args[0]) && password.equals(args[1]) && email.equals(args[2])) {
                // country found, check the document
                return row;
            }
        }
        return null;
    }

}