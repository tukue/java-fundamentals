-- SQL Exercises (MySQL)
-- Run this script with an account that has CREATE DATABASE privileges.

-- 1. DDL: Data Definition Language
-- Exercise 1.1: Create a database
CREATE DATABASE IF NOT EXISTS school_management;
USE school_management;

-- Exercise 1.2: Create the courses table
CREATE TABLE IF NOT EXISTS courses (
    id INT NOT NULL AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    credits INT NOT NULL,
    PRIMARY KEY (id)
);

-- 2. DML: Data Manipulation Language
-- Exercise 2.1: Insert at least three courses
INSERT INTO courses (course_name, credits)
VALUES
    ('Java Programming', 3),
    ('SQL Basics', 2),
    ('Web Development', 3);

-- Exercise 2.2: Update Java Programming to a higher number of credits
UPDATE courses
SET credits = 5
WHERE course_name = 'Java Programming';

-- Exercise 2.3: Delete a course by its ID (the sample SQL Basics row)
SET @sql_basics_id = (
    SELECT id
    FROM courses
    WHERE course_name = 'SQL Basics'
    ORDER BY id
    LIMIT 1
);

DELETE FROM courses
WHERE id = @sql_basics_id;

-- View the remaining course rows.
SELECT * FROM courses;

-- Sample lecture-style tables for the student and attendance exercises.
-- Dropping these two tables makes the worksheet's sample data repeatable.
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS student;

CREATE TABLE student (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    class_group VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE attendance (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status ENUM('Present', 'Absent') NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_attendance_student
        FOREIGN KEY (student_id) REFERENCES student (id)
);

INSERT INTO student (name, class_group)
VALUES
    ('Alex', 'A'),
    ('Alice', 'A'),
    ('Bob', 'B');

INSERT INTO attendance (student_id, attendance_date, status)
VALUES
    (1, '2025-01-06', 'Present'),
    (1, '2025-01-08', 'Present'),
    (2, '2025-01-06', 'Present'),
    (2, '2025-01-07', 'Present'),
    (3, '2025-01-06', 'Present');

-- 3. DQL: Data Query Language
-- Exercise 3.1: Select every student column
SELECT *
FROM student;

-- Exercise 3.2: Select students in a particular class_group
SELECT *
FROM student
WHERE class_group = 'A';

-- Exercise 3.3: Find names starting with J
SELECT *
FROM student
WHERE name LIKE 'J%';

-- 4. Joins and Relationships
-- Exercise 4.1: Students with attendance records (inner join)
SELECT s.name, a.attendance_date, a.status
FROM student AS s
INNER JOIN attendance AS a ON a.student_id = s.id
ORDER BY s.id, a.attendance_date;

-- Exercise 4.2: All students, including those without attendance records
SELECT s.name, a.attendance_date, a.status
FROM student AS s
LEFT JOIN attendance AS a ON a.student_id = s.id
ORDER BY s.id, a.attendance_date;

-- 5. Aggregation and Grouping
-- Exercise 5.1: Count all students
SELECT COUNT(*) AS total_students
FROM student;

-- Exercise 5.2: Count attendance records for each status
SELECT status, COUNT(*) AS record_count
FROM attendance
GROUP BY status;

-- Exercise 5.3: Count Present days per student, including students with zero
SELECT s.name, COUNT(a.id) AS present_days
FROM student AS s
LEFT JOIN attendance AS a
    ON a.student_id = s.id
   AND a.status = 'Present'
GROUP BY s.id, s.name
ORDER BY s.id;
