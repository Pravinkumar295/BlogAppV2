A dynamic blog platform built with Spring Boot, featuring full CRUD operations, user authentication, role-based access control, and secure password recovery. This project demonstrates modern web application development using Spring MVC and MySQL.

🔧 Tech Stack
-> Backend: Spring Boot, Spring MVC

-> Security: Spring Security (Authentication & Authorization)

-> Database: MySQL

-> Build Tool: Maven

-> Java Version: Java 17+

🚀 Features
✅ Create, Read, Update, Delete (CRUD) blog posts

🔐 Secure user authentication using Spring Security

🛡️ Role-based access control (Admin / User)

📬 "Forgot Password" functionality with email-based password reset

💾 Data persistence using MySQL

📚 MVC architecture for clean separation of concerns

📷 Screenshots (Optional)
(Add screenshots here if available)

📁 Project Structure
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.blog.app
│   │   └── resources
│   │       ├── templates
│   │       └── application.properties
└── pom.xml
⚙️ Getting Started
Clone the repository

bash
Copy
Edit
git clone https://github.com/Pravinkumar295/BlogAppV2
Configure your application.properties with your MySQL credentials.

Run the application:

bash
Copy
Edit
./mvnw spring-boot:run
Visit http://localhost:8080

📬 Forgot Password Feature
Users can click on "Forgot Password"

A secure reset link is sent to the registered email

Password can be safely reset through a token-based validation
