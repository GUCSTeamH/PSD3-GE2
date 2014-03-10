Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: System needs to support over 20 timeslots per session
Given a system
When session 2 is selected and it has over 20 timeslots
Then if number of timeslots are over 20 then the system can support
