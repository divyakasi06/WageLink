# WageLink

**Turning daily work into a verified financial identity for wage earners.**

Submitted for Global Innovation Hackathon 2026 — "Build for a Better Future"

## Problem

Millions of daily-wage and gig workers — construction laborers, domestic help, electricians, painters — depend on informal networks or middlemen to find work. This leads to unpredictable income, underpayment, and unsafe conditions. Because their earnings are undocumented, these workers have no verifiable income history, making it nearly impossible to access loans, insurance, or government welfare schemes. Employers also struggle to find verified, reliable workers nearby.

## Solution

WageLink is a work and wage ledger platform that connects workers directly with employers, removing the middleman, while building a verifiable digital record of every completed job.

- **Worker registration** — workers list their skill and availability
- **Employer registration** — employers sign up with their name, business type, and phone number
- **Job posting** — employers post short-term work directly
- **Browse & apply** — workers browse all open jobs and apply directly to the ones they want
- **Employer picks from applicants** — the employer views who applied for a job and selects the worker who actually did it
- **Employer-confirmed payments** — only the employer can confirm a job is done and paid, which prevents workers from fabricating their own wage history and keeps the ledger trustworthy
- **Work history** — a running total of jobs completed and wages earned per worker
- **Mock credit-readiness score** — a simple score based on job count and earnings, with a mock "Apply for Loan" flow showing loan eligibility, illustrating how a real version would connect workers to microfinance lenders

## What Makes This Different

Most gig-worker apps stop at job matching. WageLink's differentiator is the **verified wage ledger** — it turns everyday informal labor into a trustworthy data trail that can unlock financial inclusion (loans, insurance, welfare access) for workers who are otherwise invisible to banks and lenders.

WageLink does not lend money directly (that requires an RBI-licensed NBFC/bank). Instead, it builds the proof of income a real lender would need, and would partner with existing microfinance institutions in a production version.

## Technology

- **Backend:** Plain Java (JDK 25), using `com.sun.net.httpserver.HttpServer` for a lightweight REST API — no external framework
- **Database:** MySQL, accessed via JDBC (MySQL Connector/J)
- **Frontend:** Plain HTML, CSS, and JavaScript (`fetch` calls to the backend API)

## User Flow

1. Worker registers (`index.html`)
2. Employer registers (`employer.html`)
3. Employer posts a job (`postJob.html`)
4. Worker browses open jobs and applies (`browseJobs.html`)
5. Employer opens the job, sees the list of applicants, and picks who actually did the work, confirming payment (`confirmJob.html`)
6. Worker checks their growing work history, credit score, and loan eligibility (`history.html`)

## Project Structure

WageLink/
src/ Java backend source files
web/ HTML/CSS/JS frontend pages
lib/ MySQL JDBC driver (mysql-connector-j)
schema.sql Database schema
out/ Compiled .class files (generated, not tracked in Git)


## How to Run Locally

1. **Set up the database**
   - Open MySQL Workbench and run the contents of `schema.sql` to create the `wagelink` database and its tables (`workers`, `jobs`, `wage_ledger`, `employers`, `job_applications`)
   - Create a MySQL user for the app (or update `src/DBConnection.java` with your own MySQL username/password)

2. **Compile the backend**

javac -cp "lib\mysql-connector-j-9.1.0.jar" -d out src*.java


3. **Run the server**

java -cp "out;lib\mysql-connector-j-9.1.0.jar" ApiServer


4. **Open the app**

   Go to `http://localhost:8080` in your browser.

## Known Limitations (MVP scope)

- No login/authentication yet — worker and employer IDs are entered directly rather than through a secure login. A production version would add phone-based OTP verification.
- The credit-readiness score is a simplified mock formula for demo purposes, not a real credit model.
- No real lending integration — the "Apply for Loan" button illustrates the concept; a production version would connect to a partner microfinance lender's API.

## Team

Built for Global Innovation Hackathon 2026.
