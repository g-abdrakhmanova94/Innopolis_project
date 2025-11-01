package attestations4.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object для страницы корзины.
 * Инкапсулирует действия пользователя на корзине:
 * - подсчет количества товаров,
 * - переход к оформлению заказа (Checkout).
 * Используется в UI‑тестах Selenium по паттерну Page Object.
 */

public class CartPage {
    // Локатор кнопки перехода к оформлению заказа
    private final By checkoutButton = By.id("checkout");
    // Экземпляр WebDriver для взаимодействия с браузером
    private final WebDriver driver;

    /**
     * Создает объект страницы корзины.
     *
     * @param driver активный WebDriver
     */

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int countItemsInCart() {
        // Проверяем количество товаров в корзине
        List<WebElement> items = driver.findElements(By.xpath("//div[@class='cart_item'][@data-test='inventory-item']"));
        return items.size();
    }

    public void toCheckout() {
        // Переход к оформлению покупки
        WebElement checkout = driver.findElement(checkoutButton);
        checkout.click();
    }
}