# Service discovery client example

* The project exposes an endpoint to test the v1/departments endpoint from the employer-api endpoint.
* Please note that this project is created as an example project, not a production ready service.
* The purpose of this project is to showcase the functionality as to how we can discover a service and that's about it.
* Because this is an example only, no test cases are available, please test using swagger only.



## Build

* Execute the command from the application root directory - 'mvn clean package' command to build the project.

## Docker image

* The application is composed in a docker image.
* Also the consul and vault run in a docker container.


## Start the container

* Execute the command from the following application root directory.
 'docker-compose up --build -d'. After running the application starts at the port 8889 and with the context root /discovery-client/api

## Service registry

* Once the service is up, browse the consul server at http://localhost:8500, go to the services tab and make sure the service is registered under the 'Services' tab with the name 'discovery-client-api' and the health check is also passing.

## End points to test

* GET /discovery-client/api/v1/departments (http://localhost:8889/discovery-client/api/v1/departments)

## Stop the application

* Execute the following command from the application root directory
 'docker-compose down'



## API testing using Swagger

* /employer/api/swagger-ui.html# to check and execute the end points available. (e.g. http://localhost:8889/discovery-client/api/swagger-ui.html#)

## Enhancements/Possible improvements

* Swagger configuration values can also be externalized.
* The 'employer-api' instance id should be externalized to the consul.