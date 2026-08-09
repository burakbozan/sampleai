Feature: Order management

  Scenario: Create and list orders
    Given no orders exist
    When I create an order for customer "CUST-1" with items "[{\"sku\":\"SKU1\",\"qty\":1}]"
    Then the order list contains an order for customer "CUST-1"
