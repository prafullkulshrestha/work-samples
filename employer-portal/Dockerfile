# Usage:
#
#    Build image:
#    docker build -t angular-starter .
#
#    Run image (on localhost:8080):
#    docker run --name angular-starter -p 8080:80 angular-starter
#


# Stage 1, based on Node.js, to build and compile Angular

FROM node:8.12.0-alpine as builder


COPY package.json ./

## Storing node modules on a separate layer will prevent unnecessary npm installs at each build
RUN npm i && mkdir /ng-app && mv ./node_modules ./ng-app

WORKDIR /ng-app

COPY . .

RUN npm run build-prod


# Stage 2, based on Nginx, to have only the compiled app, ready for production with Nginx

FROM nginx:1.13.9-alpine

COPY ./config/nginx-custom.conf /etc/nginx/conf.d/default.conf.tmp

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

## From ‘builder’ stage copy over the artifacts in dist folder to default nginx public folder
COPY --from=builder /ng-app/dist/employer-portal /usr/share/nginx/html

RUN chown -R nginx:nginx /usr/share/nginx/html

# Append "daemon off;" to the beginning of the configuration
RUN echo "daemon off;" >> /etc/nginx/nginx.conf

## move set_env script
COPY ./config/set_env.sh .

WORKDIR .

CMD ["./set_env.sh", "nginx"]
