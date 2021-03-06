Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: lecturer selects that a session is a one-off.
Given a lecturer
And a booking system containing session 10
When session 10 is marked as one-off
Then session 10 is a one-off session

Scenario: lecturer selects that a session is a weekly.
Given a lecturer
And a booking system containing session 15
When session 15 is marked as weekly
Then session 15 is a weekly session

Scenario: lecturer selects that a session is a fortnightly.
Given a lecturer
And a booking system containing session 14
When session 14 is marked as fortnightly
Then session 14 is a fortnightly session