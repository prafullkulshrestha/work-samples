# Enterprise Employer API Config And Service Registry

* The project externalizes the distrubuted configuration for the employer api.
* The project is also used as a service registry application
* Every microservice when started picks the db credentials and the application config from the vault and consul services
* Every microservice after starting up registers itself as a service to the consul server.
 

## Assumptions
* We don't need to implement any changes other than those specified in the requirements.
* One department is associated with more than one employees
* One employee is associated with one department only
* Multiple records with the same firstName and lastName are allowed

## Prerequisite
* Install Docker, I used version = 18.03.1-ce

## Install consul and vault

* brew install consul
* brew install vault

## Start the consul and vault servers

 * run cd config
 * run docker-compose up -d
 * browse http://localhost:8500/ to see the consul page comes up
 * browse http://localhost:8200/ to see the vault page comes up
 * run bash consul/consu-keys.sh
 * run bash valut/vault-keys.sh
 * Verify that the keys are present in consul and vault(token=myroot)

## Stop the application

* Execute the following command from the root directory
 'docker-compose down'