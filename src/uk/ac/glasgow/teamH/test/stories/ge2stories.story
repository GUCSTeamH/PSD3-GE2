Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: lecturer selects that a session is a one-off.
Given a lecturer
When session 10 is marked as one-off
Then session 10 is a one-off session

Scenario: lecturer selects that a session is a weekly.
Given a lecturer
When session 10 is marked as weekly
Then session 10 is a weekly session

Scenario: lecturer selects that a session is a fortnightly.
Given a lecturer
When session 10 is marked as fortnightly
Then session 10 is a fortnightly session

Scenario: Student views all sessions in a course
Given a student
When course 16 is selected from student 11
Then student 12 books all compulsory sessions of course 16