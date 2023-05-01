# reading-is-good
Reading is good for Getir Case Study

## Reading Is Good Application

##### This app has been developed as Restful APIs with Spring Boot

Aim of Reading Is Good Applicaiton is to deliver books to the customers who are ordered.

### Contents

- [Welcome](#welcome)
- [Application Tech Stack](#application-tech-stack)
- [Important Maven Dependencies](#important-maven-dependencies)
- [Docker Build and Run](#docker-build-and-run)
- [Api Endpoints](#api-endpoints)
    - [Authenticate and Get Token](#authenticate-and-get-token)
    - [Add New Book](#add-new-book)
    - [Update Book Stock](#update-book-stock)
    - [Add New Customer](#add-new-customer)
    - [Add New Order](#add-new-order)
    - [Get Order By Id](#get-order-by-id)
    - [List Orders By Date](#list-orders-by-date)
    - [List Orders By Customer](#list-orders-by-customer)
    - [Get Customer Monthly Statistics](#get-customer-monthly-statistics)
- [Swagger ui](#swagger-ui)
- [Authentication and Important Notes](#authentication-and-important-notes)
- [Test Coverage Rate](#test-coverage-rate)



### Welcome

Welcome on reading is good board

The project details will be finding  bellow.

App will run on 9091 port.

Once the app is started to run on the local, database tables will be inserted with some initial records.
Details can be found in the CreateDefaultDataService.

### Application Tech Stack

- **Java 17**
- **Spring Boot**
- **Maven**
- **Docker**
- **Maven**
- **Swagger Open API Specification**
- **JWT**
- **JUnit5, Mockito Testing**
- **H2 In Memory Database**

### Important Maven Dependencies

- **spring-boot-data-jpa**
- **Lombok**
- **spring-boot-starter-validation**
- **spring-boot-starter-test, mockito and junit5**
- **spring-boot-starter-security**
- **springdoc-openapi-ui**

***Version 3.0.6 was used for all dependencies related to spring boot!***

### Docker build and run

`docker build --tag=reading-is-good:1.0 .`

`docker run -p 9091:9091 reading-is-good:1.0 .`


### Api Endpoints

Postman collection is here
[readingisgood.postman_collection.json](https://github.com/zekitel/reading-is-good/tree/master/src/main/resources/postman-collection/readingisgood.postman_collection.json)

Postman collections can be found under `resource/postman-collection`

***Not that: Getting token at the first is very important. Otherwise, the response  will be unauthorized error without token***

*If the file cannot be opened for any reason, please consider the requests below.*


#### Authenticate and Get Token

POST: http://localhost:9091/authenticate

Your request body should be like this

`{
"username": "user",
"password":"password"
}`


This will return a bearer token as =>

`{"jwtToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjgyODAxNjAzLCJleHAiOjE2ODI4MTk2MDN9.xPnJW2Sq7EW7DHwf31Drg6LL1TqOFozkj-NTxMDNLlc"}`

This token should be added to Athorization Header as a bearer token like this.

"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjgyODAxNjAzLCJleHAiOjE2ODI4MTk2MDN9.xPnJW2Sq7EW7DHwf31Drg6LL1TqOFozkj-NTxMDNLlc"

***Important!***

For the postman requests authorization should be considered, Otherwise Unauthorized error will be shown.

*Authorization type should be chosen Bearer Token and the bearer token should be pasted *

#### Add New Book

POST: http://localhost:9091/api/book/create

Body should be like;

`{
"title":"NewBookTitle",
"author":"NewBookAuthor",
"price":18.9,
"isbn":"35313456",
"stock":56
}`

#### Update Book Stock

PUT: http://localhost:9091/api/book/update-stock/5?stock=54


#### Add New Customer

POST: http://localhost:9091/api/customer/create

Body should be like;

`{
"firstName":"CustomerName",
"lastName":"CustomerLastName",
"email":"abgt.da@gmail.com"
}`

#### Add New Order

POST: http://localhost:9091/api/order/create

Request Body should be like;

`{
"customerId":3,
"bookId":5,
"bookCount":2
}`

* Racing Condition is handled by hibernate optimistic lock mechanism. To give more details, during  order creation,If the stock value of the book  is changed, At this moment, Hibernate will throw OptimisticLockException, then the exception will be caught and thrown as StockValueChangedException
* In this endpoint, when an order persisted, an OrderStatistic entity is also persisted to query easily, this is the basic implementation of [CQRS Pattern](https://microservices.io/patterns/data/cqrs.html) is used to store and
  query statistic data.



#### Get Order By Id

GET: http://localhost:9091/api/order/5

#### List Orders By Date

POST : http://localhost:9091/api/order/list-by-date-interval

`{
"pageSize":4,
"pageNumber":"0",
"startDateTime":"2022-05-01T13:30:00.000Z",
"endDateTime":"2023-05-01T23:30:00.000Z"
}`

#### List Orders By Customer

POST : http://localhost:9091/api/order/list-by-customer

`{
"pageSize":3,
"pageNumber":0,
"customerId":1
}`

#### Get Customer Monthly Statistics

GET: http://localhost:9091/api/statistic/monthly-order/2

* This endpoint is responsible to querying monthly statistics of books and orders.
* This is the usage of cqrs pattern



### Swagger ui

The swagger ui shows the api interface and requests. To execute that requests, authentication is needed.
The bearer token should be requested by authentication and should be put to header of the request. That's why swagger will not work.

GET :  http://localhost:9091/swagger-ui/index.html

### Authentication and Important Notes
The requests can not be executed  without bearer token, so the bearer token  should be taken at first.
For getting token >


POST: http://localhost:9091/authenticate

The request body should be like this

`{
"username": "user",
"password":"password"
}`

Then  Bearer Token authorization type should be chosen on postman and should be pasted the bearer token which is got from above url.

If the request without bearer token or with expired or with wrong bearer token is executed, The response will be unauthorized and forbidden error

### Test Coverage Rate

Total test coverage rate as is;

all classes	83% (39/47)	76,8% (162/211)	79,6% (355/446)

And details;

com.casestudy.readingisgood	100% (1/1)	50% (1/2)	50% (1/2)

com.casestudy.readingisgood.config	100% (1/1)	100% (5/5)	100% (14/14)

com.casestudy.readingisgood.controller	100% (5/5)	42,9% (6/14)	42,9% (6/14)

com.casestudy.readingisgood.dto	100% (11/11)	93,2% (55/59)	93,2% (55/59)

com.casestudy.readingisgood.entity	77,8% (7/9)	79,6% (43/54)	80% (44/55)

com.casestudy.readingisgood.enums	100% (1/1)	100% (2/2)	100% (2/2)

com.casestudy.readingisgood.exception	14,3% (1/7)	4% (1/25)	2,1% (1/48)

com.casestudy.readingisgood.security	100% (7/7)	96,6% (28/29)	90,4% (75/83)

com.casestudy.readingisgood.service	100% (1/1)	100% (2/2)	100% (69/69)

com.casestudy.readingisgood.service.impl	100% (4/4)	100% (19/19)	88% (88/100)


