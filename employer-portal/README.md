# EmployerPortal

* This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 6.1.1.
* The project can be developed in local environment and also can be containerized and deployed using docker
* the project can run under different profiles under the local and docker(`ng serve --prod` or change build-prod parameter in the package.json for runing on docker)
* `About` and `Logout` links are not functional on UI and can be implemented in future.

## Nginx server

* The application is deployed on the nginx web server in the Docker.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Build Local

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Buid and run on Docker

* Run `docker-compose up --build` to build and run the project on docker.
* Open `localhost:8080` in the browser to access the application.


## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
