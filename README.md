⚙️ Full-Stack Web Code Editor – Backend

This is the backend of a Full-Stack Web Code Editor built using Spring Boot and PostgreSQL.
It provides REST APIs for managing users, projects, and files, along with authentication and code execution support.

🚀 Features
🔐 JWT-based authentication
👤 User management
📁 Create, update, delete projects
📄 File management (HTML, CSS, JS)
▶️ Serve files for live preview
🔄 Update file content in real-time
🗑️ Delete projects and files
🌐 RESTful APIs

🛠️ Tech Stack
Spring Boot
Java
PostgreSQL
Spring Security (JWT)
Hibernate / JPA

⚙️ Getting Started

1. Clone the repository
git clone https://github.com/swapnar07122002/code-editor-backend.git
cd code-editor-backend

🔐 Environment Variables
Create a .env file in the root directory:

DB_URL=jdbc:postgresql://localhost:5432/your_db_name
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

JWT_SECRET=your_secret_key

MAIL_USERNAME=your_email@example.com
MAIL_PASSWORD=your_app_password

3. Run the application
Using IntelliJ:
->Open the project
->Run the main class

4. Backend runs on
http://localhost:8080

🔗 Frontend Repository
 https://github.com/swapnar07122002/code-editor-frontend
