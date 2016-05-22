DROP SCHEMA course_work;
CREATE SCHEMA course_work;


DROP TABLE IF EXISTS Discipline CASCADE;
CREATE TABLE Discipline(
	title VARCHAR(255) PRIMARY KEY
);

DROP TABLE IF EXISTS Student CASCADE;
CREATE TABLE IF NOT EXISTS Student(
	studentId VARCHAR(12) PRIMARY KEY,
	firstName VARCHAR(255),
    lastName VARCHAR(255)
);

DROP TABLE IF EXISTS Course CASCADE;
CREATE TABLE IF NOT EXISTS Course(
	courseId VARCHAR(255) PRIMARY KEY,
    isFinished BOOLEAN,
    lessonNumber INTEGER,
	price NUMERIC(6, 2),
    discipline VARCHAR(255) REFERENCES Discipline
);

DROP TABLE IF EXISTS CourseSubscription CASCADE;
CREATE TABLE IF NOT EXISTS CourseSubscription(
	subscriptionId SERIAL PRIMARY KEY,
	studentId VARCHAR(13),
    courseId VARCHAR(255),
    isPayed BOOLEAN,
    UNIQUE(studentId, courseId),
    FOREIGN KEY (studentId) REFERENCES Student(studentId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (courseId)  REFERENCES Course(courseId)   ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Lesson CASCADE;
CREATE TABLE IF NOT EXISTS Lesson(
	lessonId SERIAL PRIMARY KEY,
	room VARCHAR(255),
    lessonTime TIMESTAMP,
    courseId VARCHAR(255),
    CONSTRAINT lessons_hosted_by_course
    FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE ON UPDATE CASCADE
);



INSERT INTO Student(studentId, firstName, lastName)
VALUES ('12345-234523', 'Denis', 'Kobelev'),
	   ('25323-533323', 'Vladislav', 'Yakushin'),
       ('24254-829382', 'Mark', 'Grono'),
       ('83434-232994', 'Roman', 'Komjakov'),
       ('23092-934934', 'Dmitriy', 'Andreev'),
       ('23905-230024', 'Viktoriya', 'Chernishenko'),
       ('24422-232939', 'Artur', 'Shtorhs'),
       ('23023-292429', 'Vladimir', 'Grigoriev'),
       ('23234-949558', 'Shanit', 'Korolova'),
       ('14796-876759', 'Richard', 'Dzenis');

INSERT INTO Discipline(Title)
VALUES ('Java 1'),
	   ('Java 2'),
       ('Java 3'),
       ('Android Development'),
       ('Scala'),
       ('ASM'),
       ('Clojure'),
       ('Go'),
       ('C++');

INSERT INTO Course(courseId, discipline, lessonNumber, price, isFinished)
VALUES ('AX-123', 'Java 3', 10, 300.00, false),
	   ('BZ-234', 'Java 1', 10, 200.00, false),
       ('QG-935', 'Java 2', 10, 200.00, true),
       ('QE-237', 'C++', 10, 150.00, false),
       ('FF-249', 'ASM', 10, 250.00, true);

INSERT INTO CourseSubscription(courseId, studentId, isPayed)
VALUES ('AX-123', '25323-533323', true),
	   ('AX-123', '23092-934934', true),
       ('AX-123', '83434-232994', true),
       ('BZ-234', '23234-949558', true),
       ('BZ-234', '24422-232939', true),
       ('QG-935', '23905-230024', true),
       ('QG-935', '23023-292429', true),
       ('QE-237', '25323-533323', true),
       ('QE-237', '23092-934934', true),
       ('FF-249', '14796-876759', true);

INSERT INTO Lesson(room, lessonTime, courseId)
VALUES ('255', '2016-03-13 18:30:00', 'AX-123'),
	   ('255', '2016-03-23 19:30:00', 'AX-123'),
       ('255', '2016-03-30 18:30:00', 'AX-123'),
       ('255', '2016-04-05 19:30:00', 'AX-123');