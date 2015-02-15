# Supforum
_____________________________

It was a school project.

I used Netbeans 7.4 with GlassFish 3.1.2.2, EJB 3.1, JSF 2.1

You need to use this name for the ressource pool: **jdbc/rmt**

Before signup on the website, you need the create an admin. account via SQL to create to categories and board.

Here is the SQL request :

	INSERT INTO `user` (`id`, `creationDate`, `password`, `role`, `username`) VALUES (1, '2014-04-07 23:17:04', '21232f297a57a5a743894a0e4a801fc3', 'administrator', 'admin');


username : admin

password : admin


##SpecificationsThe first version of the website will be composed of several functionalities listed below:* Register, Log in and out* Display categories, boards, topics and messages* Create/Edit/Delete categories, boards, topics and messages* Lock, Unlock, Move and Merge topics* Display profiles* Display statistics about the forum