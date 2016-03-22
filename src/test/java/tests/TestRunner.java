package tests;

import helpers.Screenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Анна on 22.03.2016.
 */
public class TestRunner {
    WebDriver driver = null;
    Screenshot screenshot;
    Connection con = null;
    Statement stmt = null;
    String testApplicationUrl = "http://192.168.0.183:5000/showSignUp";
    String testUserName = "myNewTestUser";
    String testUserEmail = "testUser@example.com";
    String testUserPassword = "12345";
    String databaseUrl = "jdbc:mysql://192.168.0.183:3306/BucketList";
    String pathToScreenshots = "D://sample/";

    @BeforeMethod
    public void setUp(){
        driver = new FirefoxDriver();
        screenshot = new Screenshot(driver, pathToScreenshots);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus()==ITestResult.FAILURE){
            System.out.println("Test is failed");
            screenshot.takeScreenShot();
        }
        driver.close();
    }

    @Test
    public void insertTestData() {

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(testApplicationUrl);
        WebElement loginInput = driver.findElement(By.xpath("/html/body/div/div[2]/form/input[1]"));  /// заполнить через  pageobject
        WebElement emailInput= driver.findElement(By.xpath("/html/body/div/div[2]/form/input[2]"));  /// заполнить через  pageobject
        WebElement passwordInput = driver.findElement(By.xpath("/html/body/div/div[2]/form/input[3]"));  /// заполнить через  pageobject
        WebElement submit = driver.findElement(By.xpath("/html/body/div/div[2]/form/button"));  /// заполнить через  pageobject
        loginInput.sendKeys(testUserName);
        emailInput.sendKeys(testUserEmail);
        passwordInput.sendKeys(testUserPassword);
        submit.click();
    }

    @Test
    public void dataBaseTest () throws ClassNotFoundException, SQLException {
        Class.forName ("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(databaseUrl, "root", "12345");
        stmt = con.createStatement();
        String query = "SELECT * from tbl_user;";
        String clearTestData1 = "DELETE from tbl_user WHERE user_id>='2';";
        ResultSet result = stmt.executeQuery(query);
        while (result.next()) {
            int id = result.getInt("user_id");
            String username = result.getString("user_name");
            String usermail = result.getString("user_username");
            System.out.println("username :" + username);
            System.out.println("usermail :" + usermail);
            if (id >1 )
                Assert.assertEquals(username, testUserName);        }
 stmt.executeUpdate(clearTestData1);
result.close();
    }

}
