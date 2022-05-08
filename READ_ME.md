# Reading is Good

An online book marketplace, which manages the state of books as the enter a warehouse and 
move through orders. 

## Running the App

This is a springboot application, to build the application you will need mvn and at least java SDK11.

### Containers

The application builds each service into a docker image and publishes the image to docker hub, in order 
to build the image add the following to your `~/.m2/setting.xml` config file.

```xml
<servers>
   <server>
        <id>registry.hub.docker.com</id>
        <username>readingisgood</username>
        <password>j##f$(5t6rsMg/+</password>
   </server>
</servers>
```

Once you have added the credentials needed you can run the following command to build the application locally

> mvn install -U

A container library called JIB will then publish the locally created docker images to Docker Hub. The images 
are published to a `readingisgood` account on Docker Hub, you can use the password from the configuration above to log in
and view the available images. 

## Architecture

The application is set using a microservice architecture with the following core services:

1. Bookstore Gateway
2. Keycloak Mock
3. Bookstore 
4. Orders 

### Bookstore Gateway 

A gateway into our application, this service is responsible for connecting with Keycloak and validating that 
requests made by user are authenticated. Adds the security layer needed by our application, should allow or 
deny access to specific endpoints depending on the user making the request

### Keycloak Mock 

A simple mock of the Keycloak third party library, this service manages the authentication of our users, their 
roles within the system. Ideally I would have probably used an actual Keycloak instance but to make things fast and 
easier for this assignment I am making a quick mock. 

### Bookstore 

The marketplace API for our online store, this service keep stock of books within the warehouse and the state of each 
book. Provides and API for the retrieval of books for the marketplace as well as the creation/entry of 
new books within the warehouse. 

### Orders 

A service to manage the state of Orders once created, will provide an API to track/monitor and update the Order as it moves 
from one state to another, emitting events as the order goes through.

