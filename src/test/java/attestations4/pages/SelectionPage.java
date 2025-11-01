package attestations4.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Класс Page Object для страницы оформления заказа/проверки
 * Предоставляет методы для взаимодействия с элементами страницы завершения покупки
 */

public class SelectionPage {
    // Локатор поля имени пользователя
    private final By firstNameInput = By.id("first-name");
    // Локатор поля фамилии пользователя
    private final By lastNameInput = By.id("last-name");
    // Локатор поля почтового кода
    private final By postalCodeInput = By.id("postal-code");
    // Локатор кнопки "Продолжить"
    private final By continueButton = By.id("continue");
    // Локатор кнопки "Завершить"
    private final By finishButton = By.id("finish");
    // Локатор поля "Общей суммы заказа"
    private final By totalPriceLabel = By.className("summary_total_label");
    private final WebDriver driver;

    /**
     * Конструктор класса
     *
     * @param driver экземпляр WebDriver для управления браузером
     */
    public SelectionPage(WebDriver driver) {
        this.driver = driver;
    }

    //Заполнить форму доставки
    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(postalCodeInput).sendKeys(postalCode);
    }

    //Продолжить оформление заказа
    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    // Проверить итоговую сумму покупки
    public double checkTotalPrice() {
        String text = driver.findElement(totalPriceLabel).getText();
        // Оставить только цифры и точку
        String clean = text.replaceAll("[^\\d.]", "");
        if (clean.isEmpty()) {
            throw new IllegalStateException("Не найдено числовое значение в: " + text);
        }
        return Double.parseDouble(clean);
    }

    //Нажать кнопку "Завершить" для окончательного оформления заказа
    public void finish() {
        driver.findElement(finishButton).click();
    }
}

