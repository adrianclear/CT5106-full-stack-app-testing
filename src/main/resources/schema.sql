-- Drop existing tables if recreating
DROP TABLE IF EXISTS enrolment CASCADE;
DROP TABLE IF EXISTS student CASCADE;

-- =========================================================
-- STUDENT TABLE
-- =========================================================
CREATE TABLE student (
     id UUID PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(255) UNIQUE NOT NULL
);

-- =========================================================
-- ENROLMENT TABLE
-- =========================================================
CREATE TABLE enrolment (
   id UUID PRIMARY KEY,
   student_id UUID NOT NULL REFERENCES student(id) ON DELETE CASCADE,
   course_id UUID NOT NULL,
   status VARCHAR(20) NOT NULL DEFAULT 'ENROLLED',
   enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   UNIQUE (student_id, course_id)  -- prevent duplicate enrollments
);
