Feature: Authentication

  Scenario: Register then login
    Given a fresh auth DB
    When I register with username "user1" and password "pass"
    Then I can login with username "user1" and password "pass" and receive a token
