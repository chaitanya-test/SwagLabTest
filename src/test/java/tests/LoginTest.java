package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {
	

	@Test
	public void validLoginShouldGoToProducts() throws InterruptedException
	{
		LoginPage login = new LoginPage(driver);
		login.enterUsername(ConfigReader.getProperty("username"));
		login.enterPassword(ConfigReader.getProperty("password"));
		login.clickLogin();
		ProductsPage products = new ProductsPage(driver);
		Assert.assertTrue(products.getProductCount() > 0, "Products not visible after login");
		
	}
	

}