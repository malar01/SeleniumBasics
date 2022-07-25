package com.obs.seleniumbasics;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class SeleniumCommands {
    public WebDriver driver;
    public void testInitialize(String browser, String url) {
        if(browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
        }else if(browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver=new EdgeDriver();
        }else if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver=new FirefoxDriver();
        }else {
            try {
                throw new Exception("Browser not define");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        //driver.get(url);
        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }
    @BeforeMethod
    public void setup() {
        testInitialize("chrome","http://demowebshop.tricentis.com/");
    }
    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if(result.getStatus()==ITestResult.FAILURE) {
            //DateUtility dateUtility=new DateUtility();
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("./Screenshots/" + result.getName()+ ".png"));
        }
        driver.quit();
    }

    //Verify home page title- first testcase
    @Test
    public void verifyHomePageTitle() {
        driver.get("http://demowebshop.tricentis.com/");
        String expTitle="DemoWeb Shop";
        String actTitle=driver.getTitle();
        Assert.assertEquals(actTitle, expTitle,"ERROR:Invalid home page Title found");
    }
    @Test
    public void verifyWindowHandle() {
        driver.get("https://demo.guru99.com/popup.php");
        String parentWindow=driver.getWindowHandle();
        //System.out.println(parentWindow);
        driver.findElement(By.xpath("//a[text()='Click Here']")).click();
        Set<String> handleIds=driver.getWindowHandles();
        //System.out.println(handleIds);
        Iterator<String> itr=handleIds.iterator();
        while(itr.hasNext()) {
            String child=itr.next();
            if(!parentWindow.equals(child)) {
                driver.switchTo().window(child);
                driver.manage().window().maximize();
                driver.findElement(By.xpath("//input[@name='emailid']")).sendKeys("Test@gmail.com");
                driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
            }
            driver.switchTo().window(parentWindow);
        }
    }
    @Test (enabled=false)
    public void verifyFileUpload() {
        driver.get("https://demo.guru99.com/test/upload/");
        driver.findElement(By.xpath("//input[@id='uploadfile_0']")).sendKeys("F:\\Automation-Testing\\Sample_File.txt");
    }
    @Test(enabled=false)
    public void verifyLogin(){
        System.out.println("hi");
    }
    @Test
    public void verifyFileUploadRobotClass() throws AWTException, InterruptedException {
        driver.get("https://www.monsterindia.com/seeker/registration?");
        driver.manage().window().maximize();
        JavascriptExecutor j=(JavascriptExecutor)driver;
        j.executeScript("scroll(0,500)");
        StringSelection s=new StringSelection("F:\\Automation-Testing\\Sample_File.txt");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
        driver.findElement(By.xpath("//span[text()='Choose CV']")).click();
        Thread.sleep(1000);
        Robot r=new Robot();
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
        Thread.sleep(1000);
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
    }
    @Test(enabled=true)
    public void javascriptClickAndSendKeys(){
        driver.get("http://demowebshop.tricentis.com/");
        JavascriptExecutor js=(JavascriptExecutor) driver;
        String s1="document.getElementById('newsletter-email').value='test@gmail.com'";
        String s2="document.getElementById('newsletter-subscribe-button').click()";
        js.executeScript(s1);
        js.executeScript(s2);
    }
    @Test
    public void verifyValidLogin() throws IOException {
ExcelUtility excelUtility=new ExcelUtility();
        driver.get("http://demowebshop.tricentis.com/");
        driver.findElement(By.linkText("Log in")).click();
        WebElement username = driver.findElement(By.cssSelector("input[id='Email']"));
        String user = excelUtility.readStringData(1, 0, "Login");
        username.sendKeys(user);
        WebElement password = driver.findElement(By.cssSelector("input[id='Password']"));
        String pass = excelUtility.readStringData(1, 1, "Login");
        password.sendKeys(pass);
        driver.findElement(By.cssSelector("input[class^='button-1']")).click();
    }
@Test(dataProvider = "userlogindata")
    public void verifyLoginUsingDataProvider(String username,String password){
    driver.get("http://demowebshop.tricentis.com/");
    driver.findElement(By.linkText("Log in")).click();
    WebElement user= driver.findElement(By.cssSelector("input[id='Email']"));
    user.sendKeys((username));
    WebElement pass = driver.findElement(By.cssSelector("input[id='Password']"));
    pass.sendKeys(password);
    driver.findElement(By.cssSelector("input[class^='button-1']")).click();
}
@DataProvider(name="userlogindata")
    public Object[][] loginData(){
        Object[][] data=new Object[2][2];
        data[0][0]="priya.test@gmail.com";
        data[0][1]="Test@1234";

        data[1][0]="Test";
        data[1][1]="test@1234";
return data;
    }
    @DataProvider(name="loginExcelDataProvider")
    public Object[][] loginExcelData() throws IOException {
        ExcelUtility excelUtility=new ExcelUtility();
        String filepath = System.getProperty("user.dir")+ File.separator+"\\src\\main\\resources\\TestData.xlsx";
        Object[][] excelData=excelUtility.readDataFromExcel(file,"Login");
    }
}
