# Enterprise Employer API Postgres Databse Server and Seed Data

* The project provides a postgres database server.
* The project also puts some seed data in the database tables once it is up.

## Database Tables
* The project uses postgres 9.6 version running at 5432 port, but you don't need to install it.
* Table 'employees' keeps the employee data
* Table 'departments' keeps the department data.
* The employees and departments tables are created under the employer schema, both the tables are seeded with some seed data in them.

## Assumptions
* We don't need to implement any changes other than those specified in the requirements.
* One department is associated with more than one employees.
* One employee is associated with one department only.
* Multiple records with the same firstName and lastName are allowed.

## Start the container

run docker-compose up -d

## Stop the container
run docker-compose down