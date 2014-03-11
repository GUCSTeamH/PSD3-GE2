Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: lecturer adds a session to a course
Given a lecturer
And a booking system containing a course 30
When course 30 and session 200 are provided
Then session 200 is added to course 30

Scenario: lecturer adds a session to a course
Given a lecturer
When course 30 and session 210 are provided
Then session 210 is added to course 30

Scenario: lecturer adds a session to a course
Given a lecturer
When course 50 and session 210 are provided
Then session 210 is added to course 50