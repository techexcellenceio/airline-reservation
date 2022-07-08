# airline-reservation

## Domain description

- Make an airline reservation system.

- As a customer, I can make a ticket reservation to travel from Paris to New York (these are examples of two cities). We are assuming that there's a direct flight from one city to the other.

- The price of the ticket is the distance between the two cities multiplied by a coefficient. The coefficient is calculated as followed: If it's Monday, the coefficient is 50.7, if it's Tuesday the coefficient is the distance between cities minus 10, if it's Wednesday the coefficient is the sum of the length of the two city names, and for the other days the coefficient is just 1.

- I can add additional number of guest seats (max 3 guests), and in that case I get a 10% for each guest that I invited.

- As a customer, I can search for my ticket by the ticket reservation number, whereby I can view the following: Departure City, Destination City, Distance (km) between the 2 cities, and price.

- For any reserved ticket, the reservation microservice publishes a message to RabbitMQ. There's another microservice which consumes these messages and just logs to a file.

- BONUS: Creativity: Aside from the above, you can invent any other requirements.

- BONUS: Mutation Testing: Include pitest (if Java) or Stryker (if .NET)

- Evaluation criteria: When viewing your projects, I will evaluate the following: (1) Your solution design - clean design, clean code, (3) running SonarLint, (4) Running unit tests with code coverage and mutation testing

# Environment variables

${RABBIT_MQ_HOST}
${RABBIT_MQ_PORT}
${RABBIT_MQ_RESERVED_TICKET_QUEUE}
${POSTGRES_URL}
${POSTGRES_USER}
${POSTGRES_PASSWORD}

## Tools

### Pitest
Mutation testing tool. To run an analysis, the command is : 
`mvn test-compile org.pitest:pitest-maven:mutationCoverage`