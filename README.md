# Dayflow - Human Resource Management System

## Hackathon Team Collaboration Guide & Git Workflow

**CRITICAL RULES FOR THE HACKATHON:**
1. **Repository Submission:** The team leader MUST create the repository and submit the link before **10:00 AM**.
2. **Commit Frequency:** Every team member MUST push their code to the repository at least once **every 1 hour**. This ensures progress tracking.
3. **Main Branch:** Your latest working code should always be merged into the `main` branch.
4. **Individual Commits:** Every team member MUST commit their *own* code. Individual commits are used to assign points. Do not commit on behalf of someone else.
5. **Commit Messages:** Use proper commit messages explaining what was done (e.g., `feat: added employee login UI`, `fix: sqlite database connection`).

---

## Architecture & Tech Stack

*   **Frontend:** React (Vite) + Vanilla CSS (Custom Glassmorphism UI)
*   **Backend:** Java (Core/Spring Boot structure)
*   **Database:** SQLite (Local database, no cloud dependency)

## Module Breakdown for Team Members

You can divide the work among your friends as follows:

### Member 1: Authentication & Authorization (Section 3.1)
*   **Frontend:** Sign Up & Sign In pages (React). Ensure robust validation.
*   **Backend:** Java APIs for `/register` and `/login`.
*   **Database:** `users` table in SQLite (Employee ID, Email, Password Hash, Role).

### Member 2: Employee Profile & Dashboard (Section 3.2 & 3.3)
*   **Frontend:** Admin/HR Dashboard & Employee Dashboard. Profile View/Edit pages.
*   **Backend:** APIs for fetching user details and updating profiles. Role-based checks.
*   **Database:** `employee_details` table.

### Member 3: Attendance & Leave Management (Section 3.4 & 3.5)
*   **Frontend:** Check-in/out UI, daily/weekly attendance views. Leave request forms.
*   **Backend:** APIs for logging attendance, submitting leave, and Admin approvals.
*   **Database:** `attendance_records` and `leave_requests` tables.

### Member 4: Payroll & Admin Controls (Section 3.6)
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
