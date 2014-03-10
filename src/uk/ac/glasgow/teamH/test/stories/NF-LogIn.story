Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: Student logs in to the system
Given MyCampus authentication
When a student has successfully logged in
Then student will only have rights/privileges associated with their role

Scenario: Lecturer logs in to the system
Given MyCampus authentication
When a lecturer has successfully logged in
Then lecturer will only have rights/privileges associated with their role

Scenario: Admin logs in to the system
Given MyCampus authentication
When an admin has successfully logged in
Then the admin will only have rights/privileges associated with their role
