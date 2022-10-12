package Pages;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class AddToCartAndCheckout {
    WebDriver driver;
    WebDriverWait wait;

    String MenuBar = "//a[contains(text(), '%s')]";
    String category = "//div[@class='item-grid']//a[contains(text(),'%s')]";
    String product = "//a[contains(text(),'%s')]";
    String userDetails = "//input[@name='%s']";
    By totalProducts = By.xpath("//h2//a");
    By productPrice = By.xpath("//span[contains(@class,'price actual-price')]");
    By sort = By.xpath("//select[@name='products-orderby']");
    By sortCategory = By.xpath("//option[@value='10']");
    By displayFilter = By.xpath("//select[@name='products-pagesize']");
    By displayFilterValue = By.xpath("//option[@value='9']");
    By addToCart = By.xpath("(//button[contains(@class,'add-to-cart-button')])[1]");
    By shopCart = By.xpath("//a[contains(text(), 'shopping cart')]");
    By orderQuantity = By.xpath("//input[@class='qty-input']");
    By terms = By.xpath("//input[@name='termsofservice']");
    By checkout = By.xpath("//button[@name='checkout']");
    By registerButton = By.xpath("//button[contains(@class,'register-button')]");
    By register = By.xpath("//button[@name='register-button']");
    By continueButton = By.xpath("//a[contains(@class,'register-continue-button')]");
    By country = By.xpath("//select[@name='BillingNewAddress.CountryId']");
    By stateProvince = By.xpath("//select[@name='BillingNewAddress.StateProvinceId']");
    By UsCountry = By.xpath("//option[@value='1'][contains(text(),'United States')]");
    By state = By.xpath("//option[@value='52'][contains(text(),'Alaska')]");
    String billingDetails = "//input[@name='BillingNewAddress.%s']";
    By cont = By.xpath("(//button[contains(text(), 'Continue')])[1]");
    By continueButton1 = By.xpath("//button[contains(@onclick,'Billing.save()')]");
    By continueButton2 = By.xpath("//button[contains(@class,'shipping-method-next-step-button')]");
    By continueButton3 = By.xpath("//button[contains(@class,'shipping-method-next-step-button')]");
    By continueButton4 = By.xpath("//button[contains(@class,'payment-method-next-step-button')]");
    By continueButton5 = By.xpath("//button[contains(@class,'payment-info-next-step-button')]");
    By confirm = By.xpath("//button[contains(@class,'confirm-order-next-step-button')]");
    By orderPlacedVerification = By.xpath("//h1[contains(text(),'Thank you')]");

    public AddToCartAndCheckout(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void selectCategory() throws InterruptedException {

        driver.findElement(By.xpath(String.format(MenuBar,"Computers "))).click();
        driver.findElement(By.xpath(String.format(category,"Notebooks "))).click();
    }

    public void sortAndFilter() throws InterruptedException {

        driver.findElement(sort).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(sortCategory));
        driver.findElement(sortCategory).click();
        Thread.sleep(3000); //Hard wait because pause is required
        wait.until(ExpectedConditions.visibilityOfElementLocated(displayFilter));
        driver.findElement(displayFilter).click();
        wait.until(ExpectedConditions.elementToBeClickable(displayFilterValue));
        driver.findElement(displayFilterValue).click();
        Thread.sleep(3000); //Hard wait because pause is required

        List<WebElement> allProducts = driver.findElements(totalProducts);
        int allProductsCount = allProducts.size();
        System.out.println("Total products: " + allProductsCount);
        for (WebElement allElements : allProducts) {
            String productName = allElements.getText();
            System.out.println(productName);
        }
        List<WebElement> allProductsPrice = driver.findElements(productPrice);
        for (WebElement allElementPrice : allProductsPrice) {
            String productPrice = allElementPrice.getText();
            System.out.println(productPrice);
        }
    }
    public void addProductToCart() throws InterruptedException{

        driver.findElement(By.xpath(String.format(product,"Apple MacBook Pro 13-inch"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCart));
        driver.findElement(addToCart).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(shopCart));
        boolean isResultDisplayed = driver.findElement(shopCart).isDisplayed();
        Assert.assertTrue(isResultDisplayed, "Order not added to cart");
        driver.findElement(shopCart).click();
        driver.findElement(orderQuantity).clear();
        driver.findElement(orderQuantity).sendKeys("4");
        wait.until(ExpectedConditions.elementToBeClickable(terms));
        driver.findElement(terms).click();
        driver.findElement(checkout).click();
    }
    public void registerUser()throws IOException {

        String path = System.getProperty("user.dir") + "//src//test//java//TestData//DemoNopRegister.xlsx";
        FileInputStream prop1 = null;
        try {
            prop1 = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook wb = new XSSFWorkbook(prop1);
        XSSFSheet sheet = wb.getSheet("Sheet1");
        String fName = sheet.getRow(1).getCell(0).getStringCellValue();
        String lName = sheet.getRow(1).getCell(1).getStringCellValue();
        String email = fName + lName + Math.random() + "@gmail.com";
        String pass = sheet.getRow(1).getCell(3).getStringCellValue();
        XSSFSheet sheet2 = wb.getSheet("Sheet2");
        String city = sheet2.getRow(1).getCell(0).getStringCellValue();
        String add = sheet2.getRow(1).getCell(1).getStringCellValue();
        String zipcode = sheet2.getRow(1).getCell(2).getStringCellValue();
        String num = sheet2.getRow(1).getCell(3).getStringCellValue();

        driver.findElement(registerButton).click();
        driver.findElement(By.xpath(String.format(userDetails, "FirstName"))).sendKeys(fName);
        driver.findElement(By.xpath(String.format(userDetails, "LastName"))).sendKeys(lName);
        driver.findElement(By.xpath(String.format(userDetails, "Email"))).sendKeys(email);
        driver.findElement(By.xpath(String.format(userDetails, "Password"))).sendKeys(pass);
        driver.findElement(By.xpath(String.format(userDetails, "ConfirmPassword"))).sendKeys(pass);
        driver.findElement(register).click();
        driver.findElement(continueButton).click();
        driver.findElement(terms).click();
        driver.findElement(checkout).click();
        driver.findElement(country).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(UsCountry));
        driver.findElement(UsCountry).click();
        driver.findElement(stateProvince).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(state));
        driver.findElement(state).click();
        driver.findElement(By.xpath(String.format(billingDetails, "City"))).sendKeys(city);
        driver.findElement(By.xpath(String.format(billingDetails, "Address1"))).sendKeys(add);
        driver.findElement(By.xpath(String.format(billingDetails, "ZipPostalCode"))).sendKeys(zipcode);
        driver.findElement(By.xpath(String.format(billingDetails, "PhoneNumber"))).sendKeys(num);
        driver.findElement(cont).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton1));
        driver.findElement(continueButton1).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton2));
        driver.findElement(continueButton2).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton3));
        driver.findElement(continueButton3).click();
        wait.until(ExpectedConditions.elementToBeClickable(continueButton4));
        driver.findElement(continueButton4).click();
        wait.until(ExpectedConditions.elementToBeClickable(continueButton5));
        driver.findElement(continueButton5).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirm));
        driver.findElement(confirm).click();
    }

    public void verifyOrderPlaced() throws IOException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderPlacedVerification));
        String actual = driver.findElement(orderPlacedVerification).getText();
        Assert.assertEquals(actual, "Thank you", "Expected result does not match with actual result");
    }
}