Feature: Parallel Browser Launch
  Scenario: Launch Google
    Given I launch the URL "https://www.google.com"

  Scenario: Launch Bing
    Given I launch the URL "https://www.bing.com"
    And Enter "Ravi" and click on search