package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ConfigReader;

@Test
public class VerifyTitle extends BaseTest {
	
	public void checkTitle() throws InterruptedException {
	LoginPage login = new LoginPage(driver);
	
	login.enterUsername(ConfigReader.getProperty("username"));

	login.enterPassword(ConfigReader.getProperty("password"));
	login.clickLogin();
	
	System.out.println(driver.getTitle());
	Assert.assertEquals(driver.getTitle(), "Swag Labs");
	
	}
	

}
