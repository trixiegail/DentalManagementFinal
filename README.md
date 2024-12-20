# Dental Management Backend

This project serves as the backend for the Dental Management system. It is developed using **Java Spring Boot** to provide REST API endpoints for managing dental operations and data storage. The backend interacts with a **MySQL** database.

---

## Tools/Tech Used
- **Java Spring Boot**
- **Java 17**
- **MySQL Workbench 8.0**

---

## Installation Instructions

### Prerequisites
1. **Java**: Ensure Java 17 is installed on your system. You can download and install it from [here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
2. **MySQL Database**: Ensure MySQL Workbench 8.0 is installed on your system. You can download and install it from [here](https://dev.mysql.com/downloads/workbench/).
3. **Spring Boot**: Ensure Spring Boot is set up and configured on your system. You can find installation instructions [here](https://spring.io/projects/spring-boot).

---

### Steps to Install and Run the Project

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/trixiegail/DentalManagementFinal.git
2. **Ensure MySQL Database and Schema Exist**:
   - Ensure MySQL is running.
   - Create a database schema named dbdentalmanagement in your MySQL server. If it doesn't exist, you can create it using MySQL Workbench or any other MySQL management tool.
3. **Configure Application Properties**:
   - Open the src/main/resources/application.properties file.
   - Set the username and password for your MySQL database to match your MySQL Workbench credentials:
   ```
   spring.datasource.username=<your-username>
   spring.datasource.password=<your-password>
   spring.datasource.url=jdbc:mysql://localhost:3306/dbdentalmanagement
4. **Run the Application**:
   - Run the DentalManagementApplication.java file located in
     ```
     src/main/java/com/yourpackage/DentalManagementApplication.java.

   
   
