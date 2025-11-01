package attestations4.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object для страницы списка товаров (Products).
 * Содержит локаторы и действия:
 * - чтение названий товаров,
 * - проверка отображения товара по имени,
 * - добавление выбранных товаров в корзину,
 * - переход в корзину.
 */

public class ProductPage {
    // Экземпляр WebDriver для работы с браузером
    private final WebDriver driver;
    // Локатор названий товаров в списке
    private final By productNamesLocator = By.className("inventory_item_name");
    // Локатор контейнера со списком товаров
    private final By productContainerLocator = By.id("inventory_container");
    // Список товаров по умолчанию, которые планируется добавить в корзину
    private List<String> productsToAdd = List.of("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt", "Sauce Labs Onesie");

    /**
     * Создает объект страницы Products.
     *
     * @param driver активный WebDriver
     */
    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    //Возвращает преднастроенный список товаров для добавления в корзину.
    public List<String> getProductsToAdd() {
        return productsToAdd;
    }

    //Возвращает список названий всех отображаемых товаров.
    public List<String> getProductNames() {
        return driver.findElements(productNamesLocator)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    //Возвращает локатор контейнера списка товаров.
    public By getProductContainerLocator() {
        return productContainerLocator;
    }

    //Проверяет, отображается ли товар с указанным именем.
    public boolean isProductDisplayed(String name) {
        List<WebElement> productElements = driver.findElements(productNamesLocator);
        return productElements.stream().anyMatch(el -> el.getText().equals(name));
    }

    public void productsAddToCart(List<String> products) {
        for (String product : products) {
            if (!isProductDisplayed(product)) continue;
            //Добавляем товар в корзину
            By addToCartButtonLocator = By.id("add-to-cart-" + product.toLowerCase().replace(" ", "-"));
            driver.findElement(addToCartButtonLocator).click();
        }
    }

    public void goToCart() {
        //Переходим в корзину
        driver.findElement(By.xpath("//a[contains(@class, 'shopping_cart_link')]")).click();
    }
}