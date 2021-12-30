-- Geef aan hoeveel cursisten de cursus hebben behaald
SELECT Coursename, COUNT(DISTINCT t4.StudentId) FROM Course AS T1
 JOIN EnrollmentData AS T2 ON t1.CourseID = t2.CourseId
 JOIN Certificate AS T3 ON t3.EnrollmentId = t2.EnrollmentId
 JOIN Student AS T4 ON t4.StudentId = t2.StudentId
 GROUP BY Coursename

 -- Geef top 3 cursusen met meest behaalde certificates
SELECT TOP 3 Coursename, COUNT(T3.EnrollmentId) AS Certificates FROM Course AS T1
 JOIN EnrollmentData AS T2 ON t1.CourseID = t2.CourseId
 JOIN Certificate AS T3 ON t3.EnrollmentId = t2.EnrollmentId
 GROUP BY Coursename;

 -- Selecteer de 3 meeste bekeken webcast videos
 SELECT TOP 3 title, views FROM webcast
 ORDER BY Webcast.views DESC

 -- Laat zien welke certificaten een student heeft
 SELECT * FROM Student AS t1
 INNER JOIN EnrollmentData AS t2 ON t1.StudentId = t2.StudentId
 INNER JOIN Certificate AS t3 ON t3.EnrollmentId = t2.EnrollmentId

 -- Laat zien de gemiddelde voortgang van elke module in een cursus van een student
 SELECT t4.courseID, t3.ContentItemId, AVG(t1.Percentage) AS average_progress FROM progress AS t1
  JOIN ContentItem AS t2 ON t2.ContentItemId = t1.ContentItemID
JOIN module AS t3 ON t3.ContentItemId = t2.ContentItemId
JOIN course AS t4 ON t4.CourseID = t2.CourseId
GROUP BY t4.courseID, t3.ContentItemId