### SonarCloud Quality Checks and Travis CI

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=5de154d3bdc4e7502cfd1b44d7d3443519b57739&metric=alert_status)](https://sonarcloud.io/dashboard?id=5de154d3bdc4e7502cfd1b44d7d3443519b57739)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=5de154d3bdc4e7502cfd1b44d7d3443519b57739&metric=security_rating)](https://sonarcloud.io/dashboard?id=5de154d3bdc4e7502cfd1b44d7d3443519b57739)
[![Build Status](https://travis-ci.com/VasilijeBursac/PharmacySystem.svg?branch=develop)](https://travis-ci.com/VasilijeBursac/PharmacySystem)


### Heroku Deployment

- Frontend Deployment: [![Heroku App Status](http://heroku-shields.herokuapp.com/team54-pharmacy-frontend)](https://team54-pharmacy-frontend.herokuapp.com) <br/>
https://team54-pharmacy-frontend.herokuapp.com/
- Backend Deployment:  [![Heroku App Status](http://heroku-shields.herokuapp.com/team54-pharmacy-backend)](https://team54-pharmacy-backend.herokuapp.com) <br/>
https://team54-pharmacy-backend.herokuapp.com/

<hr/>

### Emails and passwords for login (email, password)

- Patient: filip@gmail.com, filip
- Pharmacist: dimitrije@gmail.com, dimi
- Dermatologist: pera@gmail.com, pera
- Supplier: jovan@gmail.com, jovan
- Pharmacy Administrator: milena@gmail.com, milence
- System Administrator: dejan@gmail.com, deki
 
 

<hr/>


### Instructions for Running Project Locally
#### Backend:
The backend is a Maven SpringBoot application, and can be found in the ***Team54*** folder. <br/>
- It can be started normally as a Java project in Eclipse or IntelliJ (run *Team54/src/main/java/ISA/Team54/Main.java* as Java Application). 
- **Java 11 was used.**
- PostgreSQL is needed for the Backend. 
- Local database connection settings can be found in the ***application.properties*** file (*Team54/src/main/resources/application.properties*):
     - **Uncomment next 4 Lines after "#LOCAL POSTGRES CREDENTIALS" and comment next 4 Lines after "#HEROKU DEPLOYMENT POSTGRES CREDENTIALS"**
  
#### Frontend:
The frontend is a Vue.js application, and can be found in the ***Front/pharmacy*** folder. <br/>
- It can be started by running:
     - <code>npm install</code> and then 
     - <code>npm run serve</code> in the mentioned folder. <br/>
- Further installation details can be found in README.md in ***Front/pharmacy***. <br/>


<hr/>


# PharmacySystem

## About Project:
Projekat iz predmeta **Internet Softverske Arhitekture(ISA)**. <br/>

## ISA Team 54 - Our Members:

|     Team 54   |  Our members      |
| ------------- | ----------------- |
| Student 1     | Ognjen Kuzmanović |
| Student 2     | Vasilije Bursać   |
| Student 3     | Milena Kovačević  |
| Student 4     | Marko Đurišić     |

## Used Technologies:

- **Backend:** Java + Spring Boot
- **Frontend:** Vue.js + Axios
- **Relational Database Management System:** PostgreSQL
- **Continuous Integration Server:** TravisCI
- **Deployment Platform:** Heroku
- **Code Quality Analysis tool:** SonarCloud
- **Build Automation tool:** Maven


<hr/>

