
## Architecture & Tech Stack

*   **Frontend:** React (Vite) + Vanilla CSS (Custom Glassmorphism UI)
*   **Backend:** Java (Core/Spring Boot structure)
*   **Database:** SQLite (Local database, no cloud dependency)



Member 1: Authentication & Authorization 
*   **Frontend:** Sign Up & Sign In pages (React). Ensure robust validation.
*   **Backend:** Java APIs for `/register` and `/login`.
*   **Database:** `users` table in SQLite (Employee ID, Email, Password Hash, Role).

Member 2: Employee Profile & Dashboard
*   **Frontend:** Admin/HR Dashboard & Employee Dashboard. Profile View/Edit pages.
*   **Backend:** APIs for fetching user details and updating profiles. Role-based checks.
*   **Database:** `employee_details` table.

Member 3: Attendance & Leave Management 
*   **Frontend:** Check-in/out UI, daily/weekly attendance views. Leave request forms.
*   **Backend:** APIs for logging attendance, submitting leave, and Admin approvals.
*   **Database:** `attendance_records` and `leave_requests` tables.

Member 4: Payroll & Admin Controls
*   **Frontend:** Read-only salary view for employees. Payroll update UI for Admin.
*   **Backend:** APIs for calculating and updating salary structure.
*   **Database:** `payroll` table.

---

## How to Run This Project Locally

### 1. Database (SQLite)
The database will be created locally as a file (e.g., `dayflow.db`). Make sure to add `dayflow.db` to your `.gitignore` so you don't overwrite each other's local databases!

### 2. Backend (Java)
Navigate to the `backend` folder and run your Java application. If using Spring Boot with Maven:
```bash
cd backend
mvn spring-boot:run
```

### 3. Frontend (React)
Navigate to the `frontend` folder, install dependencies, and start the Vite dev server:
```bash
cd frontend
npm install
npm run dev
```
