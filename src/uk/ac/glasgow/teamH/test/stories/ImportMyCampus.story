Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors


Scenario: lecturer imports a course from MyCampus
Given a lecturer
And a mycampus system containing course 157
When selecting mycampus course 157
Then mycampus course 157 is imported

Scenario: lecturer imports a course from MyCampus
Given a lecturer
And a mycampus system containing course 15
When selecting mycampus course 15
Then mycampus course 15 is imported

Scenario: lecturer imports a course from MyCampus
Given a lecturer
And a mycampus system containing course -15
When selecting mycampus course -15
Then mycampus course -15 is imported