# Enterprise Employer Data Management API

* The project exposes the APIs for managing the master data for employers for example their employees etc.
* The project can be built in a docker container and can be deployed to various environments
* When the container starts the postgres database is created, under the employer_api database, employer schema is created.
* The application comes up with the YAML configuration file for configuring a few values in it.


## Database Tables
* The project uses postgres 9.6 version running at 5432 port, but you don't need to install it.
* Table 'employees' keeps the employee data
* Table 'departments' keeps the department data.
* The employees and departments tables are created under the employer schema, both the tables are seeded with some seed data in them.

## Assumptions
* One department is associated with more than one employees
* One employee is associated with one department only

## Prerequisite
* Install Docker, I used version = 18.03.1-ce
* Download STS to import and do further development on it
* Install Maven, I used version = 3.5.3
* Build and run project on MAC or Unix based system 

## Import

* Unzip the project source code zip file.
* In spring source tool IDE, click - File -> New -> "Java Project".
* Refer to the root directory of you project to import it as a new project
* Right click on the project in the IDE, go to "Configure" -> "Convert to maven project"
* Right click on the project in the IDE, go to "Maven" to update the project

## Testcases

* The test cases are the pure unit test cases.
* There are controller class unit test cases
* There are service class unit test cases.

## Build

* Execute the command from the application root directory - 'mvn clean package' command to build the project.

## Docker image

* The application and the database are composed in a docker image.

## Start the application

* Execute the command from the following application root directory.
 'docker-compose up --build --force-recreate -d'. After running the application starts at the port 5555 and with the context root /employer/api

## End points to test

* GET /employer/api/v1/employees/list (http://localhost:5555/employer/api/v1/employees/list)
* POST /employer/api/v1/employees (http://localhost:5555/employer/api/v1/employees)
* GET /employer/api/v1/departments (http://localhost:5555/employer/api/v1/departments)

## Stop the application

* Execute the following command from the application root directory
 'docker-compose down'

## Exception

* The exception handling mechanism has been set up
* NoDataFoundException is a custom runtime exception created this returns 404 if the data is not present in the tables.


## API testing using Swagger

* /employer/api/swagger-ui.html# to check and execute the end points available. (e.g. http://localhost:5555/employer/api/swagger-ui.html#)

## Sonar code analysis (Optional)

* Build the project first
* Start the sonar server (I used version 7.8)
* Command for running sonar reports

 mvn sonar:sonar \
  -Dsonar.projectKey=emdm \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.sources=src/main/java \
  -Dsonar.tests=src/test/java \
  -Dsonar.java.binaries=target/classes \
  -Dsonar.java.test.binaries=target/test-classes \
  -Dsonar.java.libraries=target \
  -Dsonar.login=6aa87237be17a252198d84b6225605951b2e9dc1
  
  You would need to use your sonar project key and token

## Enhancements/Possible improvements

* The application configuration should be externalized either in configuration server or consul and vault.
* Swagger configuration values should be externalized.