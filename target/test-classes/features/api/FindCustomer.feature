@API @FindCustomer
Feature: Find Customer End To End Flow

  Scenario: Validate Find Customer End To End Flow
    Given User prepares Find Customer request with below data
      | customerName | Satya Mohan |
      | mobile       | 9876543210  |
      | email        | satya@example.com |
    When User submits Find Customer API request
    Then API response should be processed successfully
    And API response should contain generated UCIC
    And API response should contain match count
    And User stores generated UCIC from response
    When User validates customer request details in database
    Then Database request status should be processed
    And Database match count should match API response
    And Database generated UCIC should match API response
