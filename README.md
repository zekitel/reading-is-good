# reading-is-good
Reading is good for Getir Case Study

## Reading Is Good Application

##### This app has been developed as Restful APIs with Spring Boot 

Aim of Reading Is Good Applicaiton is to deliver books to the customers who are ordered.

### Contents 

- [Welcome](#welcome)
- [Application Tech Stack](#application-tech-stack)
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

You will be find details about project bellow.

App will run on 9090 port.

When you are running to started this app on your pc;
In the CreateDefaultDataService, I will put some code  to insert data into tables.

Initially, some example data will be created on tables.


### Application Tech Stack

The application developed with 
- **java 17**
- **Springboot**
- **H2 database**
- **spring-boot-data-jpa**
- **Lombok**
- **spring-boot-starter-validation**
- **spring-boot-starter-test, mockito and junit5**
- **spring-boot-starter-security** 
- **springdoc-openapi-ui** 

***Version 3.0.6 was used for all dependencies related to spring boot!***

### Docker build and run

`docker build --tag=reading-is-good:1.0 .`

`docker run -p 8090:8090 reading-is-good:1.0 .`


### Api Endpoints

Postman collection is here 
[readingisgood.postman_collection.json](https://github.com/zekitel/reading-is-good/tree/master/src/main/resources/postman-collection/readingisgood.postman_collection.json)

You will be found postman collection under `resource/postman-collection`

***Not that: Please be aware of getting token at the first. Otherwise, you will get unauthorized error without token*** 

*If the file does not open for any reason, please consider the requests below.*


#### Authenticate and Get Token

POST: http://localhost:9090/authenticate

Your request body should be like this

`{
"username": "user",
"password":"password"
}`


This will return you a bearer token as => 

{
  "jwtToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjgyODAxNjAzLCJleHAiOjE2ODI4MTk2MDN9.xPnJW2Sq7EW7DHwf31Drg6LL1TqOFozkj-NTxMDNLlc"
}

This token should be added to Athorization Header as a bearer token like this.

"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjgyODAxNjAzLCJleHAiOjE2ODI4MTk2MDN9.xPnJW2Sq7EW7DHwf31Drg6LL1TqOFozkj-NTxMDNLlc" 

***Important!***

For your postman requests please consider authorization, Unless you will get Unauthorized error.

*Authorization type should be chosen Bearer Token and paste your bearer token which you taken from getToken*

#### Add New Book

POST: http://localhost:9090/api/book/save

Body should be like;
{
    "title":"NewBookTitle",
    "author":"NewBookAuthor",
    "price":18.9,
    "isbn":"35313456",
    "stock":56
}

#### Update Book Stock

PUT: http://localhost:9090/api/book/updateBookStock?bookStock=54&bookId=1


#### Add New Customer

POST: http://localhost:9090/api/customer/save

Body should be like;
{
    "firstName":"CustomerName",
    "lastName":"CustomerLastName",
    "email":"abgt.da@gmail.com"
}

#### Add New Order

POST: http://localhost:8090/orders

Request Body should be like;
{
    "customerId":3,
    "bookId":5,
    "bookCount":2 
}


#### Get Order By Id

GET: http://localhost:9090/api/order/get?orderId=5

#### List Orders By Date

POST : http://localhost:9090/api/order/listOrdersByDateInterval

{
    "pageSize":4,
    "pageNumber":"0",
    "startTimeStamp":1,
    "endTimeStamp":12312315123123
}

#### List Orders By Customer

POST : http://localhost:9090/api/order/listOrderByCustomer
{
    "pageSize":3,
    "pageNumber":0,
    "customerId":1
}

#### Get Customer Monthly Statistics

GET: http://localhost:9090/api/statistic/monthlyOrderStatistics?customerId=2



### Swagger ui

The swagger ui shows the api interface but you need to authenticate and get token, and put that token to header of the request thats why swagger will not work.

GET :  http://localhost:9090/swagger-ui/index.html

### Authentication and Important Notes
You won't be able to request without bearer token, so please get bearer token at first.
For getting token >


POST: http://localhost:9090/authenticate

Your request body should be like this

{
"username": "user",
"password":"password"
}

Then please choose Bearer Token authorization type on postman and paste your bearer token which you got from above url.

If you request without bearer token or with expired or with wrong bearer token you will be Unauthorized and forbidden error

### Test Coverage Rate

Total test coverage rate as is;

all classes	84,4% (38/45)	77,2% (159/206)	79,4% (351/442)

And details;

com.casestudy.readingisgood.config	100% (1/1)	100% (5/5)	100% (14/14)

com.casestudy.readingisgood.controller	100% (5/5)	42,9% (6/14)	37,5% (6/16)

com.casestudy.readingisgood.dto	100% (11/11)	93,2% (55/59)	93,2% (55/59)

com.casestudy.readingisgood.entity	77,8% (7/9)	79,6% (43/54)	80% (44/55)

com.casestudy.readingisgood.enums	100% (1/1)	100% (2/2)	100% (2/2)

com.casestudy.readingisgood.exception	16,7% (1/6)	4,3% (1/23)	2,3% (1/44)

com.casestudy.readingisgood.security	100% (7/7)	96,6% (28/29)	90,4% (75/83)

com.casestudy.readingisgood.service	100% (1/1)	100% (2/2)	100% (69/69)

com.casestudy.readingisgood.service.impl	100% (3/3)	100% (16/16)	85,7% (84/98)
