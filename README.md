# Ratpack example app with Vue.js + Webpack frontend

## Ratpack backend
The Ratpack backend app uses an in-memory H2 database, so it doesn't require any other database to be installed on your machine.

To run the backend in development mode, run
```
./gradlew run
```

To enable Gradle's continuous mode to watch for server changes and reload them, add ```-t```
```
./gradlew run -t
```

## Vue.js Frontend
The Vue.js frontend app is located in the src/app folder.

To install the node dependencies, run
```
cd src/app
npm install
```

To run the webpack dev server, run
```
cd src/app
npm run dev
```