# OrderingApp
The application is part of my engineering work. It is used for ordering products.

Users cannot be registered, only the administrator can add them. I provide login details (but please do not change them).

User:
l: user@user.pl; pass: User123

Administrator:
l: admin@admin.pl; pass: Admin123

Employee:
l: worker@worker.pl; pass: Worker123

The application is aimed at a small group of people. Mainly for sick or disabled people who cannot do the shopping themselves, or those who are looked after by a given institution that provides basic food for their wards.

The application design assumed 3 types of users:
-customer
-employee
-administrator



Customer: a user who can view products sorted by category. It has the ability to add products to the basket and order selected products. The customer can also check the history of his completed orders.

Employee: a user who can check current orders and mark them as completed. In addition, the employee has the option of displaying the shopping list on a selected day. Another possibility for this user is to display a list of all the people for whom he provides products. So you can display basic user data. There are also 3 options with the option of writing an e-mail, calling and navigating to the user's address. Of course, if the user has provided this data (e-mail is mandatory). The employee can also change his data, password and e-mail.

Administrator: Has the ability to add, edit and delete users, change the time boundary for placing orders, add new categories and products.

MySql relational database diagram

![erd](https://user-images.githubusercontent.com/37914516/92331333-19548c80-f076-11ea-9b21-ac9f4a699d64.jpg)



