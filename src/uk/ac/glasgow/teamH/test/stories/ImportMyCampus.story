Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors


Scenario: lecturer imports a course from MyCampus
Given a lecturer
When selecting mycampus course 157
Then mycampus course 157 is imported

Scenario: lecturer imports a course from MyCampus
Given a lecturer
When selecting mycampus course 13
Then mycampus course 13 is imported

Scenario: lecturer imports a course from MyCampus
Given a lecturer
When selecting mycampus course -1
Then mycampus course -1 is imported