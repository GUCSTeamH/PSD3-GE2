Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors


Scenario: Student books timeslot in session of a course
Given a student
When a student 12 selects a session 25 and a particular timeslot 15 of a course 2
Then student 12 is enrolled in the session 25 of the course 2

Scenario: Student books timeslot in session of a course
Given a student
When a student 11 selects a session 20 and a particular timeslot 11 of a course 3
Then student 11 is enrolled in the session 20 of the course 3

Scenario: Student books timeslot in session of a course
Given a student
When a student 9 selects a session 10 and a particular timeslot 5 of a course 2
Then student 9 is enrolled in the session 10 of the course 2

Scenario: Student books timeslot in session of a course
Given a student
When a student 3 selects a session 4 and a particular timeslot 5 of a course 6
Then student 3 is enrolled in the session 4 of the course 6

Scenario: Student books timeslot in session of a course
Given a student
When a student 50 selects a session 1 and a particular timeslot 5 of a course 7
Then student 50 is enrolled in the session 1 of the course 7

Scenario: Student books timeslot in session of a course
Given a student
And a booking system containing a course 5 with a session 10 and a timeslot 15
When a student 1 books a session 10 and a particular timeslot 15 of a course 5
Then student 1 is enrolled in the session 10 of the course 5