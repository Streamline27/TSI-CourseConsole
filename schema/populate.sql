USE course_work;

INSERT IGNORE INTO Student(studentId, firstName, lastName)
VALUES ("12345-234523", "Denis", "Kobelev"),
	   ("25323-533323", "Vladislav", "Yakushin"),
       ("24254-829382", "Mark", "Grono"),
       ("83434-232994", "Roman", "Komjakov"),
       ("23092-934934", "Dmitriy", "Andreev"),
       ("23905-230024", "Viktoriya", "Chernishenko"),
       ("24422-232939", "Artur", "Shtorhs"),
       ("23023-292429", "Vladimir", "Grigoriev"),
       ("23234-949558", "Shanit", "Korolova"),
       ("14796-876759", "Richard", "Dzenis");

INSERT IGNORE INTO Discipline(Title)
VALUES ("Java 1"),
	   ("Java 2"),
       ("Java 3"),
       ("Android Development"),
       ("Scala"),
       ("ASM"),
       ("Clojure"),
       ("Go"),
       ("C++");
       
INSERT IGNORE INTO Course(courseId, discipline, lessonNumber, price, isFinished)
VALUES ("AX-123", "Java 3", 10, 300.00, false),
	   ("BZ-234", "Java 1", 10, 200.00, false),
       ("QG-935", "Java 2", 10, 200.00, true),
       ("QE-237", "C++", 10, 150.00, false),
       ("FF-249", "ASM", 10, 250.00, true);
       
INSERT IGNORE INTO CourseSubscription(courseId, studentId, isPayed)
VALUES ("AX-123", "25323-533323", true),
	   ("AX-123", "23092-934934", true),
       ("AX-123", "83434-232994", true),
       ("BZ-234", "23234-949558", true),
       ("BZ-234", "24422-232939", true),
       ("QG-935", "23905-230024", true),
       ("QG-935", "23023-292429", true),
       ("QE-237", "25323-533323", true),
       ("QE-237", "23092-934934", true),
       ("FF-249", "14796-876759", true);
       
INSERT IGNORE INTO Lesson(room, lessonTime, courseId)
VALUES ("255", '2016-03-13 18:30:00', "AX-123"),
	   ("255", '2016-03-23 19:30:00', "AX-123"),
       ("255", '2016-03-30 18:30:00', "AX-123"),
       ("255", '2016-04-05 19:30:00', "AX-123");


SELECT * FROM Lesson;
SELECT * FROM Student;
SELECT * FROM Discipline;

USE course_work;

SELECT * FROM Course;
SELECT * FROM Lesson;