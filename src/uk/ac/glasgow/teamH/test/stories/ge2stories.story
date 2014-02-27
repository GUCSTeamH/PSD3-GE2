Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors


Scenario: lecturer adds a session to a course
Given a lecturer
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

Scenario: lecturer imports a course from MyCampus
Given a lecturer
When selecting mycampus course 157
Then mycampus course 157 is imported


Scenario: Student books timeslot in session of a course
Given a student
When a student 12 selects a session 25 and a particular timeslot 15 of a course 16
Then student 12 is enrolled in the session 25 of the course 16

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
When course 16 is selected from student 12
Then student 12 books all compulsory sessions of course 16

Scenario: Student views all compulsory sessions in a course
Given a student
When a student 12 selects a course 16
Then student 12 views all compulsory sessions of course 16

Scenario: Admin assigns a room to a timetableSlot
Given an admin
When room BO504 and timeslot 1002 are selected
Then room BO504 is assigned to timeslot 1002