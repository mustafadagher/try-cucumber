Feature: BeerCans
  Scenario Outline: consuming bear
    Given I have <openingBalance> beer cans
    And I have drunk <processed> beer cans
    When I go to my fridge
    Then I should have <inStock> beer cans

    Examples:
    |openingBalance | processed | inStock|
    | 123            | 50        | 73      |
    | 1              | 1         | 0       |