Feature: List Auctions
  Anyone can check auctions related to a specific game

  Scenario Outline: The game had related auctions
    Given There are <auctions> related to game <game>
    And The web application is launched
    Then Related auctions should be displayed
    Examples:
      | game |

