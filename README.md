![Build Deploy Workflow](https://github.com/anant-pawar/lease-management-service/actions/workflows/build-deploy.yaml/badge.svg)
![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=lease-management-service&metric=coverage)

# Lease Management Service
Service for managing Vehicle Leasing 

![Lease Management](https://media.giphy.com/media/mIMsLsQTJzAn6/giphy.gif)

# Simple Design
* Provides REST endpoints for 
  * Managing Vehicles.
  * Managing Customers.
  * Managing Contracts.

# Technology Stack
Few key language and frameworks
* Java 17
* Springboot
* H2 DB / MYSQL DB

# Local : Build and Run
* With Java (uses H2 DB)
  * **Prerequisite** : [Install JDK 17](https://access.redhat.com/documentation/en-us/openjdk/17)
  * **Build** :  `./gradlew clean build`
  * **Run** : `java -jar build\libs\lease-management-service-0.0.1-SNAPSHOT.jar`
* With Docker Compose (uses MYSQL DB)
  * **Prerequisite** : [Install Docker](https://docs.docker.com/engine/install)
  * **Build and Run** :  `docker-compose up`

  
**Note** : To save you from trouble of building and running this thing locally have deployed a running copy on Render :) : [Build and Deployment](#build-and-deployment) 

# Build and Deployment
* Integrated with GitHub Actions to build and deploy service
  * [Build and Deployment Workflow](https://github.com/anant-pawar/lease-management-service/actions)
  * Deployed on [Render](https://render.com/) and service accessible at : https://lease-management-service.onrender.com

# Service documentation
* Service documentation built and hosted using GitHub Actions and Pages : [Lease Management Service API](https://anant-pawar.github.io/lease-management-service)

# Quality and Coverage
* Integrated with SonarCloud using GitHub Actions to generate quality and coverage report : [Quality and Coverage Report](https://sonarcloud.io/project/overview?id=lease-management-service) 

