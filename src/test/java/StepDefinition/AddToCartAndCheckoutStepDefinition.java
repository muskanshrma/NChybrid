package StepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;

public class AddToCartAndCheckoutStepDefinition extends BaseClass{

    @Given("User is on Homepage")
    public void website() throws IOException {
        setup();
    }

    @When("User navigates to computer section and clicks on computer category")
    public void MenuCategories() throws IOException, InterruptedException {
        pageFactory.getAddToCartAndCheckout().selectCategory();
    }

    @When("User sorts the products and apply filters")
    public void SortFilterOptions() throws IOException, InterruptedException {
        pageFactory.getAddToCartAndCheckout().sortAndFilter();
    }

    @When("User Adds item to cart and navigates to cart and checkout")
    public void cartAndCheckout() throws IOException, InterruptedException {
        pageFactory.getAddToCartAndCheckout().addProductToCart();
    }

    @When("User registers with valid details and checkout the product")
    public void userRegistration() throws IOException {

        pageFactory.getAddToCartAndCheckout().registerUser();
    }

    @Then("Order is placed successfully")
    public void productOrderPlaced() throws IOException {
        pageFactory.getAddToCartAndCheckout().verifyOrderPlaced();
        close();
    }
}