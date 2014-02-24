Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: lecturer selects that a session is a one-off.
Given a lecturer
When a session is provided
And the lecturer marks it as one-off
Then the session is not repeated

