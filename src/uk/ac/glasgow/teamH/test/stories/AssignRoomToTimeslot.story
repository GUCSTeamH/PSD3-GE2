Narrative:
In order to achieve full functionality
As a user
I want to use the system without any errors

Scenario: Admin assigns a room to a timetableSlot
Given an admin
When room BO504 and timeslot 1002 are selected
Then room BO504 is assigned to timeslot 1002

Scenario: Admin assigns a room to a timetableSlot
Given an admin
When room SAWB05 and timeslot 1 are selected
Then room SAWB05 is assigned to timeslot 1

Scenario: Admin assigns a room to a timetableSlot
Given an admin
When room BO13 and timeslot -100 are selected
Then room BO13 is assigned to timeslot -100