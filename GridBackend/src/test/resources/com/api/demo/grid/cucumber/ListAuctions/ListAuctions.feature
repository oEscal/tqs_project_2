Feature: List Auctions
  Anyone can check auctions related to a specific game

  Scenario: The game had related auctions
    Given There are 20 auctions related to game 1
    And The web application is launched
    Then Related auctions should be 20
    And Related auctions table should be displayed

  Scenario: The game had no related auctions
    Given There are 0 auctions related to game 2
    And The web application is launched
    Then Related auctions should be 0
    And Related auctions table should not be displayed
    And Extra message should appear

