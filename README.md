# vehicle-aggregator-problem
## Problem Statement
We have a city that runs its own buses & trains. They exposed their data via various microservices & some of these services are error-prone while some are reliable.

We need to build a reliable reactive aggregator service that aggregates all the data from dependent services (some of these are reactive & some are not. Refer to respective service & endpoints below for more info).

### To achieve this, we need to
Implement all end points of following services

* Vehicle Aggregator Service
* Dependent 3rd party service (we need to build appropriate mock data such that all scenarios
in Vehicle Aggregator Service can be tested) 
  * Bus Metadata Service
  * Bus Live Fare & Location Service
  * Bus Fallback Fare Service
  * Train Live Location Service
* Write integration tests for all endpoints in Vehicle Aggregator Service.
* Follow best practices at all levels: Java, Spring (both in Reactive & non-Reactive world), Low level design, API error handling, Build & others.

### Here are the responsibilities of each service
| Service                          | Responsibility                                                                                                                                                             |
|----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Vehicle Aggregator Service       | Generates & aggregates various data streams, handles & auto-recovers from upstream errors.                                                                                 |
| Bus Metadata Service             | Stores Metadata related to buses.                                                                                                                                          |
| Bus Live Fare & Location Service | Provides fast & accurate realtime fare of each bus amp; also provides a stream of all buses. But all endpoints in this service are not reliable i.e endpoints fail often.  |
| Bus Fallback Fare Service        | Provides outdated fare details for each bus. Reliable (doesnt fail often), Slow & Outdated older data.                                                                     |
| Train Live Location Service      | Provides realtime stream of all trains. Reliable, Fast & Accurate.                                                                                                         |

### Here is the outline of endpoints we need to implement (implementation constraints for each of the endpoint are mentioned in respective service section).

| Service                          | Endpoint                     | What its expected to                                                                                                                                                                                                                            |
|----------------------------------|------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Vehicle Aggregator Service       | GET /buses                   | Returns list of buses along with their fares, where fares are fetched from fallback service if fetching of this info from live service fails.                                                                                                   |
|                                  | GET /vehicle-location-stream | Exposes an infinite reliable stream that aggregates (1) Error-prone bus location stream (auto recovers i.e resubscribes incase of upstream errors out) with (2) Reliable train location stream.                                                 |
| Bus Metadata Service             | GET /metadata                | Fetches already stored metadata about all buses.                                                                                                                                                                                                |
| Bus Live Fare & Location Service | GET /buses/{id}/fare         | Given a bus id, we need to fetch latest fare for the vehicle in question. Fails 50% of Time.                                                                                                                                                    |
|                                  | GET /location-stream         | Exposes a location stream that contains events representing movement of all buses in the fleet. Each event will have location information along with bus id. Unreliable stream that emits 1 event/second & stream fails after emitting 5 items. |
| Bus Fallback Fare Service        | GET /buses/{id}/fare         | Given a bus id, we need to fetch outdated older version fare for the vehicle in question.                                                                                                                                                       |
| Train Live Location service      | GET /location-stream         | Exposes a location stream that contains events representing movement of all trains. Each event will have location information along with train id. Reliable infinite stream which emits 1 event/second that never fails.                        |
