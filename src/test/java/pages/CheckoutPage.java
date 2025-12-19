package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class CheckoutPage {


private WebDriver driver;


private By firstName = By.id("first-name");
private By lastName = By.id("last-name");
private By postalCode = By.id("postal-code");
private By continueBtn = By.id("continue");


public CheckoutPage(WebDriver driver) {
	this.driver = driver;
}

public void enterFirstName(String fn) {
	driver.findElement(firstName).sendKeys(fn);
}

public void enterLastName(String ln) {
	driver.findElement(lastName).sendKeys(ln);
}

public void enterPostalCode(String code) {
	driver.findElement(postalCode).sendKeys(code);
}

public void clickContinue() {
	driver.findElement(continueBtn).click();
}
}