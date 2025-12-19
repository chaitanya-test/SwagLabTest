package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BaseTest;



public class CheckoutCompletePage extends BaseTest {

    private final By completeHeader = By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver; // inherited from BaseTest
    }

    /**
     * Get the text of the complete header
     */
    public String getCompleteHeaderText() {
        // Use the generic wait from BaseTest
        return waitFor(completeHeader, "visible", 10, null).getText();
    }

    /**
     * Optional: verify if on the checkout complete page by URL
     */
    public boolean isOnCheckoutCompletePage() {
        return waitForUrlContains("checkout-complete", 10);
    }
}