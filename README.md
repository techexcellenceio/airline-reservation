# airline-reservation

This project illustrates TDD & Hexagonal Architecture implementation in Java, using the Use Case Driven Development Approach.

This Kata has been invented by https://github.com/valentinacupac

## Domain description

Goal : Make an airline reservation system.

- DONE : As a customer, I can make a ticket reservation to travel from Paris to New York (these are examples of two cities). 
We are assuming that there's a direct flight from one city to the other.

- DONE : The price of the ticket is the distance between the two cities multiplied by a coefficient. 
The coefficient is calculated as followed: 
If it's Monday, the coefficient is 50.7, 
if it's Tuesday the coefficient is the distance between cities minus 10, 
if it's Wednesday the coefficient is the sum of the length of the two city names, 
and for the other days the coefficient is just 1.

- DONE : I can add additional number of guest seats (max 3 guests), and in that case I get a 10% for each guest that I invited.

- DONE : As a customer, I can search for my ticket by the ticket reservation number, whereby I can view the following: 
Departure City, Destination City, Distance (km) between the 2 cities, and price.

- DONE : For any reserved ticket, the reservation microservice publishes a message to RabbitMQ. 
- NOT DONE : There's another microservice which consumes these messages and just logs to a file.

- BONUS : Some service provider interfaces are implemented in JPA

- DONE: Mutation Testing: Include pitest (if Java) or Stryker (if .NET)

## Prerequisites
- OpenJDK 17

Even if the project is dependent on rabbit MQ and an SQL database, it can be launched with embedded ampq for the broker and H2.

## Runtime environments 
Different runtime environments are enabled via spring profiles
Configuration properties for these environments can be found in infra-application/src/main/resources

Any new environment can be set by creating a new application-${profile}.yml to fine-tune configuration.
You can run a spring application via maven command `mvn spring-boot:run -Dspring-boot.run.profiles=envName`

## local

This is the default development environment. It includes : 

- an embedded qpid broker on localhost
- in memory database H2 (with ddl generated)
- inserted test data for cities
- automatic usage of SPI stubs when there is no JPA implementation

## staging

Environment set to run on PostgreSQL. This environment includes :

- automatic usage of SPI stubs when there is no JPA implementation

Environment variables are :
- RABBIT_MQ_HOST
- RABBIT_MQ_PORT
- RABBIT_MQ_RESERVED_TICKET_QUEUE
- POSTGRES_URL
- POSTGRES_USER
- POSTGRES_PASSWORD

## prod
Environment set to run on PostgreSQL. This environment cannot be launched because all SPI are not implemented yet.  
This environment does not include SPI stubs.

Environment variables are :
- RABBIT_MQ_HOST
- RABBIT_MQ_PORT
- RABBIT_MQ_RESERVED_TICKET_QUEUE
- POSTGRES_URL
- POSTGRES_USER
- POSTGRES_PASSWORD


## Testing

### Domain tests
Domain tests are located in the domain module. They are plain JUnit tests which does not require specific configuration.

### Integration tests environments
You can run integration tests with different configurations. These configurations are driven by test profiles.
To run a test via maven with a profile, you can use this command:
`mvn test -Dspring.profiles.active=test-profile`

Integration test profiles are :
- test-stub-only : fastest integration tests, application part is loaded but datasource, jpa, and database repositories are disabled
- test-jpa-and-stub : default profile. Tests are run with a database. When there is no implementation of an SPI, a stub is automatically created
- test-jpa-only: all stubs are discarded. This test environment doesn't work yet, as all SPI doesn't have concrete implementation

### Mutation testing
We use Pitest, a mutation testing tool. To run an analysis, the command is : 
`mvn test-compile org.pitest:pitest-maven:mutationCoverage`

### Coverage report
JaCoCo is used for coverage report during maven test goal. The reports are found under each submodule target/site/jacoco directory

## Architecture

### Multi-module project
To avoid leaking from infrastructure classes to the domain, the project is divided into 3 modules :
- domain : contains all the domain APIs, model, domain services, service provider interfaces (SPIs). Contains all the BDD tests of use cases.
- infra-application : spring configuration, controllers and rabbit MQ configuration. Contains integration tests.
- infra-persistence : JPA/Spring implementation of SPIs

The root project contains the parent pom.xml which :

- has spring-boot-starter-parent as a parent to allow automatic spring dependencies configuration
- has two transitive dependencies for its children : JUnit 5 and assertJ
- contains configuration for plugins : maven-compiler-plugin, spring-boot-maven-plugin, pitest-maven, maven-surefire-plugin, jacoco-maven-plugin  

Dependencies of the modules are the following :
- infra-application has a dependency to domain and to infra-persistence. It drives the configuration.
- infra-persistence has a dependency to domain
- domain module is independent of the infra(s) modules. It has only two transitive dependencies from parent pom, which are Junit 5 and assertJ

Therefore, domain module can be used by any framework to set up a new application (swing, android, etc)

### domain module
Root package is `com.fsk.airline.reservation.domain`

Sub-packages are : 
- api : contains the exposed use case interfaces
- command : when applicable contains requests used by API
- model : domain model
- service : implementations of API use cases
- spi : contains service provider interfaces and its in-memory stubs
- hexarch : utility annotation which allows spring to scan automatically domain services without explicitly importing them

The behavior is only tested via its use cases.

NB : services can implement multiple use cases. They are annotated with `com.fsk.airline.reservation.domain.hexarch.DomainService`.
In the infra-application module, these domain services are scanned via this annotation. Only the use cases are referenced in the application services.
The domain service can evolve : implementing more or less use cases will not break the application part.

### infra-application module
Root package is `com.fsk.airline.reservation.app`

Sub-packages are :
- configuration : configuration for domain services scan via annotation. Rabbit configuration. Embedded broker which can be launched via property. SPIs stubs which can be deactivated via property and which are conditional on a real SPI implementation
- controller : rest APIs interfaces, error handling and DTOs
- service : application service (mainly for JPA transaction handling) and messaging service

Class `com.fsk.airline.reservation.app.AirlineApplication` is the main spring boot class.
It is configured to scan packages configured in the property `application.base-package.scan`.
It allows to not include persistence by default and to run application on stubs only if necessary. 

### infra-persistence module
Root package is `com.fsk.airline.reservation.persistence`

Sub-packages are :
- dao : contains implementations of SPIs from the domain module
- repository : spring jpa repositories
- entity : jpa entities
- embedded : spring application runner which allow to insert data. Can be deactivated via a property. Is used by the integration tests with jpa and can be used for demos or when application runs locally.

On the root package, the class `com.fsk.airline.reservation.persistence.PersistenceConfiguration` configures spring jpa repositories and jpa entities.
Note that persistence package is on the same level as the application one :
- com.fsk.airline.reservation.app : for application module
- com.fsk.airline.reservation.persistence : for persistence

It allows to include jpa implementations of the service provider interfaces via configuration.
The property `application.base-package.scan` is the driver of this configuration. 
For example : 
- test profile "test-stub-only" scans only `com.fsk.airline.reservation.app`
- test profile "test-jpa-and-stub" scans two packages : `com.fsk.airline.reservation.app,com.fsk.airline.reservation.persistence`

That means that jpa services could be deactivated and replaced by another technology storage.

## Contributors

- [Stanislas Klukowski](stanKluk)
