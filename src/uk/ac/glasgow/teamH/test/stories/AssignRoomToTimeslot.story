Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: Admin assigns a room to a timetableSlot
Given an admin
And a system containing timeslot 1002
When room BO504 and timeslot 1002 are selected
Then room BO504 is assigned to timeslot 1002

Scenario: Admin assigns a room to a timetableSlot
Given an admin
And a system containing timeslot 10
When room F121 and timeslot 10 are selected
Then room F121 is assigned to timeslot 10

Scenario: Admin assigns a room to a timetableSlot
Given an admin
And a system containing timeslot 200
When room F121 and timeslot 200 are selected
Then room F121 is assigned to timeslot 200