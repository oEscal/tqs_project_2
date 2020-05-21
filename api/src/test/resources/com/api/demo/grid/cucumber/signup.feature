Feature: Sign up
  Anyone can create an account on the platform

  Scenario Outline: The user doesn't exists on the platform
    Given no user <user> on the platform
    When I create a new account with user <user> and password <password>
    Then the account is created with success

    Examples:
      | user            | password              |
      | user1           | password1             |
      | user2           | password2             |
      | user3           | password3             |

  Scenario Outline: The user doesn't exists on the platform but the password yes
    Given no user <user> on the platform
    And there are a user with password <password>
    When I create a new account with user <user> and password <password>
    Then the account is created with success

    Examples:
      | user            | password              |
      | user1           | password1             |
      | user2           | password2             |
      | user3           | password3             |

  Scenario Outline: The user exists on the platform
    Given there are a user <user> on the platform
    When I create a new account with user <user> and password <password>
    Then the account is not created and I receive an error message

    Examples:
      | user            | password              |
      | user1           | password1             |
      | user2           | password2             |
      | user3           | password3             |
