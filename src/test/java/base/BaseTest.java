package base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import utils.ExtentManager;
import utils.ExtentTestManager;
import factory.WebDriverFactory;
import utils.ConfigReader;
import utils.LoggerUtils;


public class BaseTest {
	
	protected Logger log = LoggerUtils.getLogger(this.getClass());
    protected WebDriver driver;
    protected static ExtentReports extent = ExtentManager.getExtent();

    
    @BeforeMethod
    public void SetUp(Method method)
    {
    	 log.info("===== Test Execution Started =====");
         log.info("Launching browser");
         
         String browser = ConfigReader.getProperty("browser");
         driver = WebDriverFactory.createDriver(browser);
         driver.get(ConfigReader.getProperty("baseURL"));
         
         ExtentTest test = extent.createTest(method.getName());
         ExtentTestManager.setTest(test);
         
         test.info("Browser launched: " + browser);
         test.info("Navigated to URL");
    }
    
    protected String takeScreenshot(String testName) {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        String destination = System.getProperty("user.dir")
                + "/test-output/screenshots/" + testName + "_" + timestamp + ".png";

        try {
            FileUtils.copyFile(source, new File(destination));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }
    
    public WebElement waitFor(By locator, String conditionType, int timeoutSeconds, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        switch (conditionType.toLowerCase()) {
            case "visible":
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            case "clickable":
                return wait.until(ExpectedConditions.elementToBeClickable(locator));
            case "presence":
                return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            case "text":
                return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, value)) ? 
                        driver.findElement(locator) : null;
            case "attribute":
                return wait.until(ExpectedConditions.attributeContains(locator, value.split("=")[0], value.split("=")[1])) ? 
                        driver.findElement(locator) : null;
            default:
                throw new IllegalArgumentException("Unknown condition type: " + conditionType);
        }
    }
    
    public boolean waitForUrlContains(String urlFragment, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    
    @AfterMethod
    public void tearDown(ITestResult result)
    {
    	ExtentTest test = ExtentTestManager.getTest();
    	log.info("Closing browser");
        log.info("===== Test Execution Finished =====");
    	if (ITestResult.FAILURE == result.getStatus()) {
    		log.error("Test Failed: {}", result.getName());
            log.error("Failure Reason:", result.getThrowable());
            takeScreenshot(result.getName());
            
        }
    	else if (ITestResult.SUCCESS == result.getStatus()) {
            test.pass("Test Passed");
        }
        else if (ITestResult.SKIP == result.getStatus()) {
            test.skip("Test Skipped");
        }
    	
        if(driver!=null)
        {
            driver.quit();
        }
        ExtentTestManager.unload();
        extent.flush();
        
        log.info("===== Test Execution Finished =====");
        
    }
    
    
}