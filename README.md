# ParkingPaymentService
Below are the several conditions listed below to calculate the payment for any parking. 
I want to pay so I can go home

Less than 10 min is free. Escape Time
Parked Time < 10min  -> 0$
10 min <= Parked Time < 1hr  -> 12$
1hr <= Parked Time -> 3$ for every 15 min
Day Maximum is 96$
Weekend Day ( Thu, Fri, Sat, Sun till 10 PM) only rate 12$
Weekend Night ( after 10 PM) 40$


 Examples: 
      | day_of_week | entry_time          | payment_time        | amount_to_pay | comment                         |
      | Monday      | 0001-01-01 10:00:00 | 0001-01-01 10:09:59 |             0 | escape time                     |
      | Monday      | 0001-01-01 10:00:00 | 0001-01-01 10:10:00 |            12 | after escape time pay full hour |
      | Monday      | 0001-01-01 10:00:00 | 0001-01-01 11:00:00 |            15 |                                 |
      | Monday      | 0001-01-01 10:00:00 | 0001-01-01 11:15:00 |            18 |                                 |
      | Monday      | 0001-01-01 08:00:00 | 0001-01-01 15:59:59 |            96 |                                 |
      | Monday      | 0001-01-01 08:00:00 | 0001-01-01 16:00:00 |            96 | max payment                     |
      | Monday      | 0001-01-01 06:00:00 | 0001-01-03 05:59:59 |           192 | max payment for 2 days          |
      | Thursday    | 0001-01-04 21:00:00 | 0001-01-04 21:59:59 |            12 | weekend day only                |
      | Thursday    | 0001-01-04 22:00:00 | 0001-01-04 22:10:00 |            40 | weekend night                   |
      | Friday      | 0001-01-05 22:00:00 | 0001-01-05 22:10:00 |            40 | weekend night                   |
      | Saturday    | 0001-01-06 22:00:00 | 0001-01-06 22:10:00 |            40 | weekend night                   |
      | Saturday    | 0001-01-06 21:59:59 | 0001-01-06 22:10:00 |            52 | one hour  + weekend night       |
      | Monday      | 0001-01-01 05:55:00 | 0001-01-01 06:05:00 |            24 | escape time across day boundary |
