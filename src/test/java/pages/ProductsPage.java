package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.List;



public class ProductsPage {


	private WebDriver driver;

	private By inventoryItem = By.className("inventory_item");
	private By addToCartBtn = By.cssSelector("button.btn_inventory");
	private By cartBadge = By.className("shopping_cart_badge");
	private By cartLink = By.className("shopping_cart_link");

	public ProductsPage(WebDriver driver) {
		this.driver = driver;
	}

	public int getProductCount() {
		return driver.findElements(inventoryItem).size();
	}

	public void addProductToCartByIndex(int index) {
		List<WebElement> items = driver.findElements(inventoryItem);
		if (index < 0 || index >= items.size())
			throw new IllegalArgumentException("Invalid index");
		WebElement item = items.get(index);
		item.findElement(addToCartBtn).click();
	}

	public void addProductToCartByName(String name) {
		List<WebElement> items = driver.findElements(inventoryItem);
		for (WebElement item : items) {
			String title = item.findElement(By.className("inventory_item_name")).getText();
			if (title.equalsIgnoreCase(name)) {
				item.findElement(addToCartBtn).click();
				return;
			}
		}
		throw new RuntimeException("Product not found: " + name);
	}

	public int getCartCount() {
		try {
			return Integer.parseInt(driver.findElement(cartBadge).getText());
		} catch (Exception e) {
			return 0;
		}
	}

	public void goToCart() {
		driver.findElement(cartLink).click();
	}
}