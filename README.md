# 🌡️ Temperature Converter Application

A full-stack web application that converts temperatures between **Celsius** and **Fahrenheit**. The project follows a **microservices-based architecture**, incorporates modern **DevOps practices**, and uses an automated **CI/CD pipeline** for continuous integration and deployment.

---

## 🚀 Features

* **Backend:** RESTful API developed with Spring Boot.
* **Frontend:** Responsive web interface built using HTML5, CSS3, JavaScript, Node.js, and Express.
* **Microservices Architecture:** Backend and frontend are deployed as separate services.
* **Containerization:** Docker and Docker Compose for simplified deployment.
* **CI/CD Pipeline:** Automated build, dependency installation, and Docker image validation using GitHub Actions.

---

## 🛠️ Technology Stack

| Component | Technologies                              |
| --------- | ----------------------------------------- |
| Backend   | Java 17, Spring Boot, Maven               |
| Frontend  | Node.js, Express, HTML5, CSS3, JavaScript |
| DevOps    | Docker, Docker Compose, GitHub Actions    |

---

## 📁 Project Structure

```text
temperature-converter/
│
├── .github/
│   └── workflows/
│       └── ci-cd.yml              # GitHub Actions workflow
│
├── tempconv/                      # Spring Boot backend
│   ├── src/
│   ├── pom.xml
│   └── docker-compose.yml
│
└── temp-converter-web-client/     # Node.js frontend
    ├── package.json
    ├── server.js
    └── public/
```

---

## ⚙️ Prerequisites

Before running the project, ensure the following software is installed:

* Docker
* Docker Compose
* Java 17
* Maven
* Node.js (v18 or later)

---

## ▶️ Running the Application

### 1. Clone the Repository

```bash
git clone https://github.com/imashidilshani/temperature-converter.git
cd temperature-converter
```

### 2. Navigate to the Backend Directory

```bash
cd tempconv
```

### 3. Build and Start the Application

```bash
docker compose up --build
```

Docker Compose will build and start both the backend and frontend services.

---

## 🌐 Access the Application

| Service | Host URL | Container Port | Description |
| :--- | :--- | :--- | :--- |
| **Frontend Client** | [http://localhost:3001](http://localhost:3001) | `3000` | Web UI for Temperature Converter |
| **Backend API** | [http://localhost:9000](http://localhost:9000) | `8080` | Spring Boot REST Service |
| **MongoDB** | `mongodb://localhost:27019` | `27017` | Database Instance |

---

### 🔑 Authentication & Access Requirements

* **API Key Authorization:** Backend REST API endpoints require an active API Key passed via the `X-API-KEY` header.
* **MongoDB Compass Connection:** Connect using `mongodb://localhost:27019` to view or manage stored conversion logs and API key records.
---

## 🔌 API Endpoints Documentation

Base URL: `http://localhost:9000/api/temperatures`

### 1. Convert Temperature & Save to DB
Converts temperature values and saves the log into MongoDB.
* **Endpoint:** `/convert`
* **Method:** `POST`
* **Query Parameters:**
  * `value` (double, required): Temperature value to convert.
  * `unit` (String, required): Unit of temperature (e.g., `C` or `F`).
* **Response:** Returns the saved `TemperatureLog` object.

---

### 2. Get All Logs
Retrieves all temperature conversion logs stored in the database.
* **Endpoint:** `/logs`
* **Method:** `GET`
* **Response:** List of all `TemperatureLog` objects.

---

### 3. Get History
Retrieves full history of conversion logs.
* **Endpoint:** `/history`
* **Method:** `GET`
* **Response:** List of historical `TemperatureLog` objects.

---

### 4. Filter Logs by Unit
Filters temperature logs based on the specified unit.
* **Endpoint:** `/filter`
* **Method:** `GET`
* **Query Parameters:**
  * `unit` (String, required): Unit to filter logs by (e.g., `C` or `F`).
* **Response:** List of filtered `TemperatureLog` objects matching the unit.

---

### 5. Safety Check
Checks safety warnings/alerts based on temperature value and unit.
* **Endpoint:** `/safety-check`
* **Method:** `GET`
* **Query Parameters:**
  * `value` (double, required): Temperature value.
  * `unit` (String, required): Unit of temperature.
* **Response:** Plain text response message (e.g., `"High Temperature Warning!"`).

---

## 🔄 CI/CD Pipeline

The project includes an automated GitHub Actions workflow (`ci-cd.yml`) that runs whenever code is pushed to or a pull request is created for the **setup-cicd** branch.

The pipeline performs the following tasks:

* Builds the Spring Boot backend using Maven.
* Installs frontend dependencies with Node.js.
* Validates Docker image creation using Docker Compose.
* Ensures the project builds successfully before integration.

---

## 📌 Future Improvements

* Add unit and integration testing.
* Deploy the application to a cloud platform (AWS, Azure, or Render).
* Add Swagger/OpenAPI documentation.
* Implement user authentication.
* Improve UI/UX with modern frontend frameworks such as React.

---

## 👩‍💻 Author

**Imashi Dilshani**

Information Technology Undergraduate

GitHub: **https://github.com/imashidilshani**
