# Currency Exchange REST API

This is the source code for the currency exchange Spring Boot application developed with Spring Boot, Kotlin,
PostgreSQL and Spring JPA.

**See also https://github.com/spring-guides/tut-spring-boot-kotlin for a more complete Spring Boot + Kotlin + JPA example.**


## Steps for executing with Docker:

- Clone/Download the repository.


- Open the project in the IDE (Netbeans/Intellij Idea/Eclipse) and generate the executable .jar file for the application. The alternate method to generate the .jar file is through Gradle.
  
        ./gradlew clean bootJar

- Open the terminal and go to the directory where docker-compose.yml is located and run the below command and will build the PostgreSQL and Spring Boot Rest API Containers.

      docker compose up

- Run the below command to get the list of running containers :

      docker ps

After executing above steps without any errors and docker containers are up and running, open the browser and navigate to the next step.

## OpenApi - Swagger for API specification
- After run the application, you can access the api specification with following url:

      http://localhost:8080/swagger-ui/index.html#/

# REST API

The REST API to the fleet management app is described below.

## Get The Latest Exchange Rate by Currency

### Request

`Get /v1/currency`

### Query Parameter
| Parameter | Type     | Description               |
| --------- | -------- | ------------------------- |
| `currency`       | `string` | **Required** |

## Post Exchange Rate

### Request

`Post /v1/currency`

### Request Body
| Parameter | Type     | Description               |
| --------- | -------- | ------------------------- |
| `currencyName`       | `string` | **Required** |
| `date`       | `string` | **Required** |
| `exchangeRate`       | `integer` | **Required** |

## Get Exchange Rates by Period

### Request

`Get /v1/currency/period`

### Request Body
| Parameter | Type     | Description               |
| --------- | -------- | ------------------------- |
| `currencyName`       | `string` | **Required** |
| `fromDate`       | `string` | **Required** |
| `toDate`       | `string` | **Required** |

## Get Exchange Rates by Date

### Request

`Get /v1/currency/date`

### Query Parameter
| Parameter | Type     | Description               |
| --------- | -------- | ------------------------- |
| `date`       | `string` | **Required** |


#Schemas

## Currency Schema

| Field Name | Data Type |
|:---         |:----         |
| currencyName | String |


## Exchange Rate Schema

| Field Name | Data Type |
|:---         |:----|         
| currency_name | String |
| date | String | |
| exchange_rate | Integer |
