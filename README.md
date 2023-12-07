# My HR CRM Project

Welcome to the documentation for the "My HR CRM Project". This document provides a brief overview of the project and guides you through its setup and usage.

## Table of Contents

- [Introduction](#introduction)
- [Project Related Links](#project-related-links)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Features](#features)
- [Security](#security)
- [Installation](#installation)
- [Usage](#usage)
- [References](#references)

## Introduction

"My CRM Project" is a Customer Relationship Management (CRM) application designed to streamline and manage customer interactions, candidate data, vacancies, and more. It aims to provide a user-friendly interface for managing various aspects of customer relationships and recruitment processes.<br />
All project information you may find at [this link](https://drive.google.com/drive/folders/10BLBtSwlvJMaWUfJpxciwA4f1JdzcLAx).

## Project Related Links
- [Project Information](projectDocs)
- [HR CRM Application Java Docs](javadoc/index.html)

## Technologies:
`Spring Boot`: Used for application development and streamlined setup and deployment. 
<br />
`MySQL`: Utilized as the database for storing application data.
<br />
`Mockito and Junit`: Utilized for creating mock objects and performing unit and integration testing.
<br />
`Swagger`: Used for documenting the API and generating interactive documentation.
<br />
`AOP (Aspect-Oriented Programming)`: Implemented for cross-cutting concerns such as logging.
<br />
`Hibernate and JPA`: Utilized for database interaction and object-relational mapping.
<br />
`SLF4J`: Employed for logging and event registration within the application.
<br />
`Lombok`: Simplify using annotations.
<br />
`Liquibase`: Employed for managing database migrations.
<br />
`JaCoCo`: Utilized for measuring code coverage with unit tests.
<br />
`JWT (JSON Web Tokens)`: Utilized for secure authentication and authorization mechanisms.
<br />
`Hibernate and JPA`: Employed for seamless database interaction and effective object-relational mapping.



## Getting Started

Before you begin, ensure you have the necessary environment and dependencies set up. You can refer to the official Spring Boot documentation for detailed information on [setting up a Spring Boot project](https://docs.spring.io/spring-boot/docs/3.1.2/reference/html/getting-started.html#getting-started-system-requirements).

## Features

- **Candidate Management:** Manage candidate profiles, including personal information, contact details, and application status.
- **Vacancy Tracking:** Keep track of open vacancies, job titles, descriptions, salaries, and application deadlines.
- **Employee Management:** Maintain employee information, roles, and responsibilities.
- **Interaction Tracking:** Log customer interactions, interviews, meetings, and communications.

## Security

The application will implement security measures to restrict access to authorized users. This will include requiring an "admin" and "manager" roles for accessing sensitive operations like CRUD functionality and performing POST, DELETE, and PUT requests.

## Installation

1. Clone the repository from [GitHub](https://github.com/your-username/your-crm-project).
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
3. Configure the database connection in the `application.properties` file.
4. Build the project using Maven: `mvn clean install`.

## Usage

1. Run the application using your IDE or the command line: `java -jar your-crm-project.jar`.
2. Access the application through your web browser at `http://localhost:8080`.
3. Navigate through the user interface to manage candidates, vacancies, employees, and interactions.

## References

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/3.1.2/reference/html/)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

## Database stucture
![erd](https://github.com/dickchest/MyHrCrmProject/blob/master/projectDocs/ERD%20crm_hr.png)
