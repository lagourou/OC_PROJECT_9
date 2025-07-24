# Étape 1 : Build Angular
FROM node:lts-alpine3.22 AS build-stage
WORKDIR /app
COPY . .
RUN npm cache clean --force
RUN npm install
RUN npx ng build --configuration=docker

# Étape 2 : Serve Angular avec Nginx
FROM nginx:stable-alpine3.21-slim

RUN apk add --no-cache curl

COPY --from=build-stage /app/dist/angular-diabetes-app /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
