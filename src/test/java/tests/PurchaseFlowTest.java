package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

public class PurchaseFlowTest extends BaseTest {

    @Test
    public void endToEndPurchase() throws InterruptedException {
        LoginPage login = new LoginPage(driver);
        login.enterUsername(ConfigReader.getProperty("username"));
        login.enterPassword(ConfigReader.getProperty("password"));
        login.clickLogin();

        ProductsPage products = new ProductsPage(driver);
        int initialCount = products.getCartCount();
        products.addProductToCartByIndex(0);
        Assert.assertTrue(products.getCartCount() > initialCount, "Product was not added to cart");

        products.goToCart();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.getCartItemsCount() > 0, "Cart is empty after adding product");

        cart.clickCheckout();
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.enterFirstName("John");
        checkout.enterLastName("Doe");
        checkout.enterPostalCode("12345");
        Thread.sleep(5000);
        checkout.clickContinue();
        System.out.println("Done adding to cart");

        Thread.sleep(15000);
        // wait for the overview page to load
        waitForUrlContains("checkout-step-two", 30);

        CheckoutOverviewPage overview = new CheckoutOverviewPage(driver);
        String total = overview.getTotalText();
        Assert.assertTrue(total.toLowerCase().contains("total"), "Total not shown on overview");
        overview.clickFinish();

        CheckoutCompletePage complete = new CheckoutCompletePage(driver);
        Assert.assertEquals(complete.getCompleteHeaderText(), "THANK YOU FOR YOUR ORDER",
                "Order not completed successfully");
    }
    
    
}
