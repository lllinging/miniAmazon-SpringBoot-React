# miniAmazon-SpringBoot-React

## Introduction
This project implements modern development practices using Spring MVC for the back-end and React for the front-end. It features secure user authentication, dynamic data management, and robust application performance by leveraging **Spring Boot, Spring Security, Spring Data JPA, MySQL, Redis, and Redux Toolkit**. The application supports functionalities like user **registration, login, search, sorting, pagination, filtering, and order management**.

## Key Features
**User Registration and Login:** Implements secure user authentication and authorization using Spring Security and JWT.
**Product Search and Filtering:** Allows users to search and filter products based on different criteria like categories, prices, and more.
**Sorting and Pagination:** Efficiently manages large data sets with support for sorting and pagination.
Order Management: Handles placing, viewing, and tracking orders with a robust back-end implementation.
**Secure Data Storage:** Utilizes MySQL with Spring Data JPA for reliable and secure data management.
**Shopping Basket Management:** Integrates Redis for caching and managing basket items, improving application performance.
**Front-End with React and Redux:** Uses React and Redux Toolkit to efficiently manage application states and asynchronous actions.
Responsive User Interface: Styled with Material UI for a modern and user-friendly design.  

## Technologies Used  
### Back-end:  
Spring Boot with Spring MVC  
Spring Security for authentication and JWT  
Spring Data JPA for database interactions  
MySQL for relational data storage  
Redis for caching and shopping basket management  

### Front-end:  
React  
Redux Toolkit for state management  
Thunk API for handling asynchronous actions  
Material UI for styling  

## Running the Application(Docker is needed)  
1. Navigate to the docker folder and run:  
```docker-compose up -d```  
This will start the MySQL and Redis containers, with data initialization   through mounted volumes.  

2. Build the Project:  
In the project directory, build the application using Maven:  
```mvn clean install```  
Start the Spring Boot Application  

3. Once built, start the application:  
```mvn spring-boot:run```  

4. Access the Application:  
The front-end will be accessible at http://localhost:8081.  

