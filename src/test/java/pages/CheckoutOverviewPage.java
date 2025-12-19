package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutOverviewPage {

    private WebDriver driver;

    private By finishBtn = By.id("finish");
    // primary selector plus some fallbacks in case DOM changed
    private By totalLabel = By.cssSelector(".summary_total_label, .summary_info_label, .summary_subtotal_label");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalText() {
        // Wait up to 15s for any of the total selectors to be present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.presenceOfElementLocated(totalLabel));
        List<WebElement> els = driver.findElements(totalLabel);
        if (!els.isEmpty()) {
            for (WebElement e : els) {
                String txt = e.getText();
                if (txt != null && !txt.trim().isEmpty()) {
                    return txt;
                }
            }
        }
        // If we reach here, element was not found or empty — gather diagnostics
        String pageUrl = driver.getCurrentUrl();
        String title = driver.getTitle();
        String pageSnippet = "";
        try {
            String src = driver.getPageSource();
            pageSnippet = src.length() > 1000 ? src.substring(0, 1000) : src;
        } catch (Exception ignore) {}
        throw new RuntimeException("Total label not found on checkout overview. URL=" + pageUrl + " title=" + title + " pageSnippet=" + pageSnippet);
    }

    public void clickFinish() {
        driver.findElement(finishBtn).click();
    }
}
