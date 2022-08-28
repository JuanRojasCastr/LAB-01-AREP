# Searching stocks

This repository contains a small application to search stocks by equity names
through different API's

## Architecture

The app is a Client-Server architecture, the fronted was developed with JS and consume the backend api developed with Java and Spark. 

## API

### Base URL

```url
https://tranquil-fortress-94732.herokuapp.com/
```

### Endpoints

The following is the only endpoint:

```
/stock
```

### Requests

The request is sent as a GET, and the structure is the following:

```
/stock?name=$NAME&provider=$PROVIDER&time=$TIME
```

For example:

```
/stock?name=IBM&provider=0&time=INTRADAY
```

Let's see what these values mean:

- Name: The equity name to search
- Provider: The number of the provider which the backend will search the stocks value
- Time: For some providers you can choose the time series to search (Intraday, Daily, Weekly, Monthly)

 ### Responses

The type fo the response have to be a json and its structure will depend on the provider selected, anyways we have to look for the date, open, high, low, close and volume values.


The following is the response body using the provider 1 (Polygon):

```json
{
  "status": "OK",
  "from": "2022-08-26",
  "symbol": "IBM",
  "open": 134.1,
  "high": 134.18,
  "low": 130.34,
  "close": 130.38,
  "volume": 4185179,
  "afterHours": 130.25,
  "preMarket": 133.51
}
```

## Running the app locally


In order to run the project directly with java just have to clone this repository and go to the SparkWebApp class, then run the main class, other way is with the following command:

```
java -cp "target/classes;target/dependency/*" edu.escuelaing.arep.designprimer.SparkWebApp
```

## Adding new providers!

This app support the feature to add new providers, but we have to be carefully doing this:

1. First go to the index.html, here we have to add the provider to the select, in order to let the user use this provider, add a new value and name:
```
src/main/resources/public/index.html
```
![](https://cdn.discordapp.com/attachments/584593411567517710/1013531334939000902/unknown.png)

2. In this file go to the buildTable function and check the structure of the response, then add the case to the function to load the table with that provider. 

3. We have to add the api provider to our service class:

```
src/main/java/edu/escuelaing/arep/designprimer/ServiceConfig.java
```
![api-provider-service](https://media.discordapp.net/attachments/584593411567517710/1013529673071865996/unknown.png)

Check that this api doesn't need more params than the used by the others, if this isn't the case add them.

4. Now go to our SparkApp, we are about to explain more detailed the cache later but for now add the cases to load and save the response on the cache, and that's all, in a resume we add the select, the api and check if the structure of the json response.

## Cache

This app try to be efficient with te request, so we add a cache memory in our backend, after a request to the provider the save the response with a key created with these rules:

- If we use provider 0 the time will change for the same name, so the key is $NAME+$PROVIDER+$TIME
- If we use provider 1 only can change the name not the time, so the key is $NAME+$PROVIDER

We can verify this on browser network tool:

![network](https://media.discordapp.net/attachments/584593411567517710/1013547094864302172/unknown.png)

We see how the second request is faster than the first one.

We also add a thread that every 5 minutes clean the cache:

![network2](https://media.discordapp.net/attachments/584593411567517710/1013547698101698650/unknown.png)

![network3](https://media.discordapp.net/attachments/584593411567517710/1013547734520840222/unknown.png)

Look how after the cleaning the request is again slower than the last one.

## Concurrency test

To prove that our app can handle concurrent request's we crate a thread which make a request to the provider and save the date if the request is successfully, then we have a test that create multiple threads and start them, if all the threads created are successful the test is ok.

## Author

- **Juan Camilo Rojas Castro** 


