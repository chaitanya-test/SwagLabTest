package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import utils.LoggerUtils;



public class LoginPage {

	private Logger log = LoggerUtils.getLogger(LoginPage.class);

private WebDriver driver;

private By username = By.id("user-name");
private By password = By.id("password");
private By loginBtn = By.id("login-button");


public LoginPage(WebDriver driver) {
	this.driver = driver;
	
}

public void enterUsername(String user) {
	log.info("Enering username :"+user);
	driver.findElement(username).sendKeys(user);
}

public void enterPassword(String pass) {
	log.info("Enering password :"+pass);
	driver.findElement(password).sendKeys(pass);
}

public void clickLogin() {
	log.info("Clicking on login button");
	driver.findElement(loginBtn).click();
}

}
