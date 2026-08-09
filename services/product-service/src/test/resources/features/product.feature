Feature: Product management

  Scenario: Create and list products
    Given no products exist
    When I create a product with sku "SKU-BDD-1" and name "BDD Product"
    Then the product list contains an item with sku "SKU-BDD-1"
