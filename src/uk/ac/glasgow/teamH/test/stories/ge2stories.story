Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: lecturer selects that a session is a one-off.
Given a lecturer
When session 10 is marked as one-off
Then session 10 is repeated 1 time

