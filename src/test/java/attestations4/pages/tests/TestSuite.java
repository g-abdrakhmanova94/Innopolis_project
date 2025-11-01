import attestations4.pages.AuthPage;
import attestations4.pages.CartPage;
import attestations4.pages.ProductPage;
import attestations4.pages.SelectionPage;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestSuite - тестовый класс для автоматизации веб-приложения интернет-магазина
 * Содержит набор тестовых сценариев для проверки функциональности авторизации и оформления заказов
 */

public class TestSuite {
    private WebDriver driver;
    private AuthPage authPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private SelectionPage selectionPage;

    /**
     * Метод настройки перед каждым тестом
     * Инициализирует WebDriver и Page Objects
     */
    @BeforeEach
    public void setUp() {
        // Указываем правильный путь для сохранения результатов Allure
        System.setProperty("allure.results.directory", "src/allure-results");
        driver = new FirefoxDriver();
        authPage = new AuthPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        selectionPage = new SelectionPage(driver);
    }

    @DisplayName("Успешная авторизация под пользователем 'standard_user'")
    @Test
    @Description("Тест успешной авторизации с валидными учетными данными, проверяет возможность входа для стандартного пользователя")
    @Tags({@Tag("Positive"), @Tag("Smoke")})
    public void successfulAuthStandardUser() {
        step("Открыть страницу авторизации", () -> {
            authPage.click();
        });

        step("Заполнить имя пользователя 'standard_user'", () -> {
            authPage.enterLogin("standard_user");
        });

        step("Заполнить пароль 'secret_sauce'", () -> {
            authPage.enterPassword("secret_sauce");
        });

        step("Нажать кнопку авторизации", () -> {
            authPage.clickLogin();
        });

        step("Дождаться загрузки страницы товаров", () -> {
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(productPage.getProductContainerLocator()));
        });

        step("Проверить наличие списка товаров", () -> {
            assertThat(productPage.getProductNames()).isNotEmpty();
        });
    }

    @DisplayName("Авторизация под пользователем 'locked_out_user'")
    @Test
    @Description("Тест авторизации с заблокированным пользователем, проверяет обработку ошибки при попытке входа заблокированным пользователем")
    @Tags({@Tag("Negative"), @Tag("Smoke")})
    public void AuthLockedOutUser() {
        step("Открыть страницу авторизации", () -> {
            authPage.click();
        });

        step("Заполнить имя пользователя 'locked_out_user'", () -> {
            authPage.enterLogin("locked_out_user");
        });

        step("Заполнить пароль 'secret_sauce'", () -> {
            authPage.enterPassword("secret_sauce");
        });

        step("Нажать кнопку авторизации", () -> {
            authPage.clickLogin();
        });

        step("Дождаться появления сообщения об ошибке", () -> {
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(authPage.getErrorElement()));
        });

        step("Проверить текст сообщения об ошибке", () -> {
            String actualErrorMessage = authPage.getErrorMessage();
            assertThat(actualErrorMessage)
                    .isEqualTo("Epic sadface: Sorry, this user has been locked out.");
        });
    }

    @DisplayName("E2E-сценарий под пользователем 'standard_user'")
    @Test
    @Description("Полный E2E-сценарий для стандартного пользователя, проверяет весь процесс покупки от авторизации до завершения заказа")
    @Tags({@Tag("Positive"), @Tag("E2E")})
    public void e2EScenarioStandardUser() {
        step("Авторизоваться пользователем 'standard_user'", () -> {
            authPage.click();
            authPage.enterLogin("standard_user");
            authPage.enterPassword("secret_sauce");
            authPage.clickLogin();

            // Дождаться загрузки страницы товаров
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(productPage.getProductContainerLocator()));
        });

        step("Добавить три товара в корзину", () -> {
            productPage.productsAddToCart(productPage.getProductsToAdd()); // см. ниже
        });

        step("Перейти в корзину", () -> {
            productPage.goToCart();
            // Дождаться загрузки корзины
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("cart_list")));
        });

        step("Проверить, что в корзине 3 товара", () -> {
            int count = cartPage.countItemsInCart();
            assertThat(count).withFailMessage("Ожидалось 3 товара, но найдено: " + count)
                    .isEqualTo(3);
        });

        step("Перейти к оформлению заказа", () -> {
            cartPage.toCheckout();
        });

        step("Заполнить форму доставки", () -> {
            selectionPage.fillCheckoutForm("Alex", "Braun", "628601");
            selectionPage.clickContinue();
        });

        step("Проверить итоговую стоимость покупки ($58.29)", () -> {
            double actualTotal = selectionPage.checkTotalPrice();
            assertThat(actualTotal).isEqualTo(58.29);
        });

        step("Завершить заказ", () -> {
            selectionPage.finish();
        });
    }

    @DisplayName("E2E-сценарий под пользователем 'performance_glitch_user'")
    @Test
    @Description("Полный E2E-сценарий для пользователя с проблемами производительности, проверяет работу системы при медленном отклике")
    @Tags({@Tag("Positive"), @Tag("E2E")})
    public void e2EScenarioPerformanceGlitchUser() {
        step("Авторизоваться пользователем 'performance_glitch_user'", () -> {
            authPage.click();
            authPage.enterLogin("performance_glitch_user");
            authPage.enterPassword("secret_sauce");
            authPage.clickLogin();

            // Дождаться загрузки страницы товаров
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(productPage.getProductContainerLocator()));
        });

        step("Добавить три товара в корзину", () -> {
            productPage.productsAddToCart(productPage.getProductsToAdd()); // см. ниже
        });

        step("Перейти в корзину", () -> {
            productPage.goToCart();
            // Дождаться загрузки корзины
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("cart_list")));
        });

        step("Проверить, что в корзине 3 товара", () -> {
            int count = cartPage.countItemsInCart();
            assertThat(count).withFailMessage("Ожидалось 3 товара, но найдено: " + count)
                    .isEqualTo(3);
        });

        step("Перейти к оформлению заказа", () -> {
            cartPage.toCheckout();
        });

        step("Заполнить форму доставки", () -> {
            selectionPage.fillCheckoutForm("Alex", "Braun", "628601");
            selectionPage.clickContinue();
        });

        step("Проверить итоговую стоимость покупки ($58.29)", () -> {
            double actualTotal = selectionPage.checkTotalPrice();
            assertThat(actualTotal).isEqualTo(58.29);
        });

        step("Завершить заказ", () -> {
            selectionPage.finish();
        });
    }

    /**
     * Метод очистки после каждого теста
     * Закрывает браузер и освобождает ресурсы
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}