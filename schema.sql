CREATE DATABASE IF NOT EXISTS wagelink;
USE wagelink;

CREATE TABLE workers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    skill VARCHAR(100) NOT NULL,
    available_days VARCHAR(50)
);

CREATE TABLE jobs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    location VARCHAR(150),
    wage INT NOT NULL,
    job_date DATE,
    posted_by VARCHAR(100)
);

CREATE TABLE wage_ledger (
    id INT AUTO_INCREMENT PRIMARY KEY,
    worker_id INT NOT NULL,
    job_id INT NOT NULL,
    amount_paid INT NOT NULL,
    completed_date DATE,
    FOREIGN KEY (worker_id) REFERENCES workers(id),
    FOREIGN KEY (job_id) REFERENCES jobs(id)
);