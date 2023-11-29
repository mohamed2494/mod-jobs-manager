# mod-jobs-manager

## Introduction

The mod-jobs-manager module is one of two modules, alongside the mod-jobs-consumer [https://github.com/mohamed2494/mod-jobs-consumer.git], designed to manage and process job-related tasks within the FOLIO system. It's all about creating, monitoring, and processing jobs, with detailed tracking of their status. This module is built on the FOLIO Spring-based backend, ensuring it smoothly fits into the FOLIO system.

## Getting Started

### Prerequisites

Before running the mod-jobs-manager module, ensure you have the following prerequisites installed:

* Java JDK (Version 11 or higher)
* Apache Maven 3.3.x or higher

### Clone the Repository

Clone the mod-jobs-manager repository from GitHub.

git clone [https://github.com/mohamed2494/mod-jobs-manager.git]
cd mod-jobs-manager

### Configuration

Update the `application.yml` file with relevant configurations, such as Kafka broker details, database connection settings, etc.

If needed, customize the `Dockerfile` to match your environment specifications.

## Build and Run

### Build the module using Maven:
```bash
mvn clean package
```

### Run the module:
```bash
java -jar target/mod-jobs-manager-fat.jar
```

### Build the Docker image:
Build the Docker image:

```bash
docker build -t mod-jobs-manager:latest .

```
### Run the Docker container:
```bash
docker run -p 8081:8081 mod-jobs-manager:latest
```

## Database Configuration

The module uses Liquibase for database schema management. Ensure that your database is configured according to the Liquibase changes specified in the `src/main/resources/liquibase` directory.

## Kafka Integration

The module relies on Kafka for event-driven communication. Make sure your Kafka broker is accessible, and update the Kafka configuration in the `application.yml` file.

## Testing

Use the provided testing framework to run unit tests and ensure the module's functionality.

```bash
mvn test
```

## API Documentation

Explore the module's API documentation by navigating to `http://localhost:8081/api-docs` after the module is running.

## Customization

Feel free to customize the module to meet your specific requirements. Modify the codebase, configurations, or database schema as needed.

## License
Copyright (C) 2022-2023 The Open Library Foundation

This software is distributed under the terms of the Apache License,
Version 2.0. See the file "[LICENSE](LICENSE)" for more information.

This is an example of the FOLIO backend module built using folio-spring-base library.

The module has been created using the mod-spring-template.

Please find all the details regarding creating new folio Spring based modules using mod-spring-template at https://github.com/folio-org/mod-spring-template

