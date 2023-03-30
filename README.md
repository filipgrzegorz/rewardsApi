# Awarding points REST-Api Application.

<h3>
Application was build using Spring boot
application is using h2 database to store data
</h3>
Application run on standard port:8080
Api expose 3 endpoints:
- /api/status/check
- /api/transactions
- /api/rewards
Detailed Api documentation were generated using Swagger library. It is an HTML document please check file API-Documentation.html
Api documentation is also available directly in browser once the application is running, please check http://localhost:8080/swagger-ui.html there you can also call the endpoints

How To Run

You can run the application in below ways:
1 Directly from a jar file:

- please copy the jar file from the repository in to local file system
- please open terminal in place where the jar file is located.
- execute command java -jar rewards-1.0.0.jar after a while Application should start.
- API should be accessible at localhost:8080 when you type in browser http://localhost:8080/api/status/check you should
  get a response with message "Api is UP"

2 Fom IDE

- please clone the application repository.
- please open a IDE and import new Project.
- run the project as a Spring boot application.

3 From IDE using maven.

- please clone the application repository.
- please open a IDE and import new project.
- please run mvn install on terminal/console
- please run in terminal/console mvn spring-boot:run OR java -jar target/rewards-1.0.0.jar
- after a while Application should start.
- API should be accessible at localhost:8080 when you type in browser http://localhost:8080/api/status/check  you should
  get a response with message "Api is UP"

# Example of adding transaction:

<h3>TransactionId is a string value and have to be unique</h3>
curl -X POST "http://localhost:8080/api/transactions" -H "accept: application/json" -H "Content-Type: application/json"
-d "{ \"amount\": 80, \"customerId\": 1, \"transactionDate\": \"2023-03-27\", \"transactionId\": \"test1\"}"

curl -X POST "http://localhost:8080/api/transactions" -H "accept: application/json" -H "Content-Type: application/json"
-d "{ \"amount\": 120, \"customerId\": 1, \"transactionDate\": \"2023-02-01\", \"transactionId\": \"test2\"}"

curl -X POST "http://localhost:8080/api/transactions" -H "accept: application/json" -H "Content-Type: application/json"
-d "{ \"amount\": 50, \"customerId\": 2, \"transactionDate\": \"2023-01-11\", \"transactionId\": \"test3\"}"

curl -X POST "http://localhost:8080/api/transactions" -H "accept: application/json" -H "Content-Type: application/json"
-d "{ \"amount\": 88, \"customerId\": 2, \"transactionDate\": \"2023-02-21\", \"transactionId\": \"test4\"}"

curl -X POST "http://localhost:8080/api/transactions" -H "accept: application/json" -H "Content-Type: application/json"
-d "{ \"amount\": 234, \"customerId\": 2, \"transactionDate\": \"2023-03-21\", \"transactionId\": \"test5\"}"

# Example of updating transaction:

curl -X PUT "http://localhost:8080/api/transactions/test2" -H "accept: application/json" -H "Content-Type:
application/json" -d "{ \"amount\": 98.60 }"

curl -X PUT "http://localhost:8080/api/transactions/test1" -H "accept: application/json" -H "Content-Type:
application/json" -d "{ \"amount\": 350.21 }"

# Example of getting customer rewards:

<h3>All customers rewards </h3>
curl -X GET "http://localhost:8080/api/rewards" -H "accept: */*"
<h3>All rewards for customer </h3>
curl -X GET "http://localhost:8080/api/rewards/2" -H "accept: */*"
