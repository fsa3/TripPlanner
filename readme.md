# BookMaster
*Cluster 6 - integrated product*

![](/src/img/logo.png)

**HBV401G Þróun hugbúnaðar - spring 2021**

## Group members in team T
* Ágúst Heiðar Sveinbjörnsson - [ahs33@hi.is](mailto:ahs33@hi.is)
    * Github: [`agustheidar`](https://github.com/agustheidar)
* Björn Borgar Magnússon - [bbm5@hi.is](mailto:bbm5@hi.is)
    * Github: [`BearPays`](https://github.com/BearPays)
* Fannar Steinn Aðalsteinsson - [fsa3@hi.is](mailto:fsa3@hi.is)
    * Github: [`fsa3`](https://github.com/fsa3)

This repository contains the source code for the integrated Trip Planner called Bookmaster created by cluster 6 in
Þróun Hugbúnaðar 2021.

* The [`src/flightSystem`](src/flightSystem) package contains the code for the flight system implemented by the F team
    * [Link to original source code](https://github.com/jongvnnar/hopurF)
    
* The [`src/hotelSystem`](src/hotelSystem) package contains the code for the hotel system implemented by the H team
    * [Link to original source code](https://github.com/tfj2/ThrounH)
    
* The [`src/dayTripSystem`](src/dayTripSystem) package contains the code for the day trip system implemented by the D team
    * [Link to original source code](https://github.com/ragnadua/HBV401G)
    
All other code is written by the T team.

## Libraries
* sqlite-jdbc-3.32.3.2 
* javafx-sdk-11.0.2
* JUnit4 (for testing)

## Functionality
When running the programs users are able to log in or create an account and search for flights, hotel rooms and daytrips.
Upon search users will see standardized trip packages containing flights, hotels and daytrips along with the complete search results.
Users are able to book the standardized packages or book a custom package created from the search results. Finally users are able to view their bookings and cancel them.

Please notice that there is only data for the period **1.5.2021 - 31.5.2021**, searching for anything else will result in an empty search results.

Available departure locations and destinations are:
* Reykjavík
* Akureyri
* Ísafjörður
* Egilsstaðir

To run the program please run the main function in the class [`Main.java`](src/main/Main.java) in the package [`src/main`](src/main).