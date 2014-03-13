Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: Student logs in to the system
Given a mycampus
And a student
Then student 123456f with password 123 logs into the system

Scenario: Lecturer logs in to the system
Given a mycampus
And a lecturer
Then lecturer tim with password 123 logs into the system

Scenario: Admin logs in to the system
Given a mycampus
And an admin
Then admin 15487 with password 2654 logs into the system
