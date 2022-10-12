Feature: Add Product to Cart and Checkout

  Scenario: Add product to cart and checkout successfully after registering with valid details
    Given User is on Homepage
    When User navigates to computer section and clicks on computer category
    And User sorts the products and apply filters
    And User Adds item to cart and navigates to cart and checkout
    And User registers with valid details and checkout the product
    Then Order is placed successfully