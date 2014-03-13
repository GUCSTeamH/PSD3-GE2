
Narrative:
In order to not fail a course
As a student
I want to view all compulsory sessions
					 
Scenario: Student checks if signed up for compulsory session in a course
Given a student
And a booking system containing course 16 with a session 14 also student 12
Then student 12 checks if signed up for compulsory session 14 of course 16

Scenario: Student checks if signed up for compulsory session in a course
Given a student
And a booking system containing course 15 with a session 10 also student 12
Then student 10 checks if signed up for compulsory session 10 of course 15

Scenario: Student checks if signed up for compulsory session in a course
Given a student
And a booking system containing course 15 with a session 11 also student 17
Then student 17 checks if signed up for compulsory session 11 of course 15



