package factory;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
	
	public static WebDriver createDriver(String browser)
	{
		WebDriver driver;
		switch (browser.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			// Add your desired chrome options here
			options.addArguments("--start-maximized");
			options.addArguments("--disable-notifications");
			options.addArguments("--remote-allow-origins=*");
			options.setExperimentalOption("prefs", Map.of(
	                "credentials_enable_service", false,
	                "profile.password_manager_enabled", false
	        ));
			// options.addArguments("--headless=new"); // if needed

			driver = new ChromeDriver(options);
			break;

		case "firefox":
			driver = new FirefoxDriver();
			break;

		case "edge":
			driver = new FirefoxDriver();
			break;

		default:
			throw new RuntimeException("Unsupported browser: " + browser);
			
		}
		return driver;
		
		
		
		
	}

	public static WebDriver createdriver() {
		// TODO Auto-generated method stub
		return null;
	}

}
