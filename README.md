# Full Stack Tech Task for Job Interview

This project demonstrates my skills in Frontend with Angular and Typescript as well Backend with Java and Spring Boot.
It features a responsive Supermarket Checkout UI with Tailwind CSS and a real-time connection to the backend API.
It also includes Signal Store, eslint, prettier and tests. There are still todos in the code. I'm aware that there is potatial to make the whole feature more user friendly and better but I hope for the puprose of demosntrating my experience it is enough.

## 📂 Project Structure

Root directory containing backend and frontend projects:

```
    root/
    ├── backend/ # Spring Boot Maven Project
    │ └── src/main/java/com/...
    └── frontend/ # Angular Project
    │ └── src/app/...
```

## 🛠️ Prerequisites

Ensure you have the following installed on your machine:

- Java JDK 17 or higher
- Node.js (v18+) and npm
- Angular CLI (npm install -g @angular/cli)
- Maven (optional, if using the provided mvnw wrapper)

## ⚙️ Setup Instructions

Navigate to the frontend folder and install the necessary dependencies:

```bash
    npm install
```

## 🚀 Running the Application

You will need to run the backend and frontend in separate terminal windows.

### Terminal 1: Start the Backend

From the project root folder:

```bash
cd backend
./mvnw spring-boot:run
```

The backend will start on [http://localhost:8080](http://localhost:8080).

### Terminal 2: Start the Frontend

From the project root folder:

```bash
cd frontend
ng serve
```

The frontend will start on [http://localhost:4200](http://localhost:4200).

### 🦄 Frotend test

Run the unit test locally from root folder by executing the following script

```bash
cd frontend && ng test
```

### 👩‍💻 Frotend lint

Run eslint locally from root folder by executing the following script

```bash
cd frontend && ng lint
```

### 🦋 Frotend prettier

Run prettier locally from root folder by executing the following scripts.

For checking:

```bash
cd frontend && npm run prettier:check
```

For formatting:

```bash
cd frontend && npm run prettier:format
```

### ✍️ Author

Katrin Otto
Senior Full Stack Developer with focus on Frontend

Contact: [kateforhaiilo@gmail.com](mailto:kateforhaiilo@gmail.com)

GitHub: [github.com/kateforhaiilo](https://github.com/kateforhaiilo-commits)
