# Ratpack example app with Vue.js + Webpack frontend

## Requirements
* Java 8 SDK for Ratpack backend
* For running browser functional tests either
    * Firefox v47.0.1
    * Chrome

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

To run the webpack dev server on localhost:8080, run
```
cd src/app
npm run dev
```
The Webpack dev server proxies API requests to the Ratpack server running on port 5050, so during development you can take advantage of the features of the Webpack dev server like live reloading.

## Tests
To run unit and API functional tests for the Ratpack app, run
```
gradlew test
```

Since the browser tests rely on the Webpack assets being built which can take a little while, I split those tests into their own Gradle task
```
gradlew testBrowser
```

By default, the browser functional tests will run in Firefox. To run the tests in Chrome, run
```
gradlew -Dgeb.env=chrome testBrowser
```

To run Javascript unit tests for the Vue frontend app, you can either use Gradle
```
gradlew testJs
```
or NPM/Webpack
```
cd src/app
npm run test
```

And finally to run all the different types of tests, run
```
gradlew check
```

## Packaging
The app uses the [Gradle Shadow](https://github.com/johnrengelman/shadow) plugin for packaging
```
gradlew shadowJar
```