# RestaurantAutomationMainSystem
Software engineer project. This is the main system for management purposes such as seating guests, editing exinting orders,
managing reservation, floor state, employees clock in/out system and more. This application was developed using javaFx and SceneBuilder. IMPORTANT: you need java7 or higher in order to run the code properly. Some of the functionality used in this application is functional programing using labda expression. Hence JDK 7 or higher is requiered.

# To Run Application
To run this application you can download and upload folder directly to ecplise or any IDE of your choice. Run the main file, Main.java, do not run the CMain.java file this file is not doing anything for now. Once you run the main, you will be prompted to the main interface with four options. This four options are the subsystems. The floor subsystem, will take you to a layout with restaurant tables. In order to seat people in a table click the desire table and choose the option from the popup dialog box. Another important feature is the clock system. Very simple to follow, however the input must be chosen from the image libraries. This subsystem is not 100% finish yet. 

# Main System 
The main system devides and conqueres targetting three different type of audience: Mobile app for waiters, Webb app for customer's queries and Desktop app for all restaurant personal.

![alt text](https://github.com/leonardoARoman/RestaurantAutomationMainSystem/blob/master/images/main_system.png?raw=true)

# Desktop app
Desktop is divided into four subsytems: Clock system, Admin, Kitchen and Floor.

![alt text](https://github.com/leonardoARoman/RestaurantAutomationMainSystem/blob/master/images/desk_app.png?raw=true)

# Floor
Floor main functuanality is updating table states, seating new guest, searching/modify reservations and see the menu. Network communication via sockets using TCP is stablished between desktop-floor and mobile app. Actors whom stablish this communications are the hostess from desktop and waiters from tablet. This type of communication is needed in order to keep table state updated in both ends.

![alt text](https://github.com/leonardoARoman/RestaurantAutomationMainSystem/blob/master/images/table_state.png?raw=true)

# Kitchen
Kitchen main functuanality is to recieve orders from weiter's tablet and update orders when done. Network communication via sockets using TCP is stablished between desktop-kitchen and mobile app. Actors whom stablish this communications are the cooks from kitchen and waiters from tablet. This type of communication is needed in order to keep orders moving as soon as they are done. Order state is updated in both ends.

![alt text](https://github.com/leonardoARoman/RestaurantAutomationMainSystem/blob/master/images/orders_state.png?raw=true)

# Clock Device
This device is what makes our project from existing projects. It targets all personal as they clock in and out of work. As we can see in the picture, employee needs to enter ID number then press an option. Once ID is enter the device will triger a query to the DBMS in order to adquire biometric fingerprint stored in our database system. Then employee will be prompted to place finger on scanner device. Once the sample gets collected from the scanner, stored sample will be compared with scanner sample for verification. If transaction is successful, time for clock in/out will be stored and employee is in or out.

![alt text](https://github.com/leonardoARoman/RestaurantAutomationMainSystem/blob/master/images/clock.png?raw=true)
