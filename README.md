# Hospital

'@author Dzianis Berastsen'

This is multi-module project with **Maven** which has 9 modules:<br/>
* model
* test-db 
* dao-api  
* dao
* service-api
* service
* service-rest
* web-app
* rest-app

## About 

This app can be used by a doctor in a hospital to keep track of their patients and staff attached to them.
More detailed description is provided [here](/documentation/srs/Hospital.md). 

## Requirements

1. Java **JDK 15** 
2. Maven
3. Spring Boot
     + Data JDBC
     + Test
     + Web
     + Thymeleaf
4. JUnit
5. Bootstrap
6. H2 Database

## Installing 

```hgignore
 $ git clone https://github.com/Brest-Java-Course-2021-2/Dzianis-Berastsen-Hospital.git
```

## Run application

To run the application,first of all we need to build it, for this we need to run given command in the terminal:

` $ mvn clean install`

Application consists 2 particular modules (web-application & rest-app) that are dependent on each other.<br/>
At first run rest-app module which is set up on http://localhost:8088 (can run it using spring devtools or RestApplication.class).
Further run web-app module which is set up on http://localhost:8080 and is listening to :8088 so rest-app must be started already (can run it using spring devtools or HospitalApplication.class)
