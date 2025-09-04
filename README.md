# Courier Tracking API
A Spring Boot Rest API for tracking courier geolocation.

🚀 Features
- Create & retrieve courier, save locations, and calculate total distance traveled
- RESTful API endpoints
- In-memory database for simplicity
- Swagger UI for API documentation
- Unit tests for core functionality
- Integration Tests for end-to-end validation

🛠️ Tech Stack
- Java 17
- Spring Boot 3
- H2 Database
- Spring Data JPA
- Swagger

⚙️ Installation and Setup
1.  Clone the repository:

    `git clone https://github.com/polo-cpu/courier-tracking.git`


2. Build & Run

    `mvn clean install`
    
    `mvn spring-boot:run`

   App runs at 👉 http://localhost:8090

   Access Swagger UI for API documentation 👉 http://localhost:8090/swagger-ui/index.html

🔗 API Endpoints

| Method | Endpoint                        | Description                                   |
|--------|---------------------------------|-----------------------------------------------|
| POST   | `/api/courier/create`           | Register new courier                          |
| POST   | `/api/courier/save/location`    | Save current location of courier              |
| GET    | `/api/courier/{id}`             | Get courier by PK of MIG_COURIER table        |
| GET    | `/api/courier/totalDistance/{courierId}` | Calculate total travelled distance of courier |
| GET    | `/api/courier/events/{courierId}` | Get entrance events by courierId              |


📊 Future Improvements
- Add authentication and authorization for courier registration (Spring Security + JWT)
- Implement real-time tracking with Kafka or another messaging system
- Frontend application for better user experience

👨‍💻 Author

Atilla Berk Demir - Software Development Engineer
- 🔗[LinkedIn](https://www.linkedin.com/in/atilla-berk-demir/)
