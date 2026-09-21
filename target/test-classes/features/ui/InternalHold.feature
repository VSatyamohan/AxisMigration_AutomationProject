@UI @InternalHold
Feature: Internal Hold record

  Background:
    Given User logs into the application

  Scenario: INTERNAL HOLD the INPUT record
    When the user clicks on the Imaging Eyeballing menu
    And the user clicks on the Img_Verification link
    And the user provides comments
    And the user clicks on the INTERNAL HOLD button
    And the user INTERNAL HOLD the record
    Then the record should be marked as IH in the REPORT_INPUT_OUTPUT table
    And the comments should be updated in the REPORT_INPUT_OUTPUT table
