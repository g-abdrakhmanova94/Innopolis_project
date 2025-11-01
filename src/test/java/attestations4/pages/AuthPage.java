package attestations4.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object для страницы авторизации https://www.saucedemo.com/.
 * Инкапсулирует элементы страницы и действия над ними: открытие страницы,
 * ввод логина/пароля, нажатие кнопки входа и чтение текста ошибки.
 * Используется в UI‑тестах Selenium по паттерну Page Object.
 */

public class AuthPage {
    private final WebDriver driver;

    // Локатор поля имени пользователя
    private final By usernameField = By.id("user-name");

    // Локатор поля пароля
    private final By passwordField = By.id("password");

    // Локатор кнопки авторизации
    private final By loginButton = By.id("login-button");

    // Локатор контейнера сообщения об ошибке авторизации
    private final By errorContainer = By.xpath("//div[contains(@class, 'error-message-container error')]");

    public void click() {
        //Открываем страницу авторизации
        driver.get("https://www.saucedemo.com/");
    }

    /**
     * Создает объект страницы авторизации.
     *
     * @param driver экземпляр WebDriver, управляющий браузером
     */
    public AuthPage(WebDriver driver) {
        this.driver = driver;
    }

    //Заполнить имя пользователя
    public void enterLogin(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    //Заполнить пароль
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    //Нажать на кнопку авторизации
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    //Возвращает элемент контейнера сообщения об ошибке авторизации.
    public WebElement getErrorElement() {
        return driver.findElement(errorContainer);
    }

    //Возвращает текст сообщения об ошибке
    public String getErrorMessage() {
        return getErrorElement().getText().trim();
    }
}

