import React, { useEffect, useState } from "react";
import axios from "axios";
import "./UserCategoryPage.css";
import { ListGroup } from "react-bootstrap";
import SidebarInstructor from "../../components/SidebarInstructor"; // Adjust the import according to your project structure
import Loader from "../../components/Loader"; // Adjust the import according to your project structure
import Message from "../../components/Message"; // Adjust the import according to your project structure
import { generatePath, useParams } from "react-router-dom";
import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";



const InstructorCategoryPage = () => {
  const { id } = useParams(); // Extract the id from the route
  const [title, setTitle] = useState(null); // Initialize course state to null
  const [description, setDescription] = useState(null);
  const [lessons, setLessons] = useState([]);
  const [quizzes, setQuizzes] = useState([]);
  const [assignments, setAssignments] = useState([]);
  const [loading, setLoading] = useState(true);
  const token = JSON.parse(localStorage.getItem("jwtToken"));

  const generatePdf = async (id, title) => {
    try {
      const response = await fetch(`http://localhost:8008/api/quizzes/generate-pdf/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`, // Dodajte ovu liniju kako biste poslali token
          "Content-Type": "application/pdf", // Ovisno o potrebi, možda trebate dodati i Content-Type
        },
      });
      if (!response.ok) {
        throw new Error("Failed to download results");
      }

      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      title = title.replace(/ /g, "_");
      link.setAttribute('download', `${title}` + "_rezultati"); // Use the desired file name
      document.body.appendChild(link);
      link.click();
      link.parentNode.removeChild(link);

    } catch (error) {
      console.error("Error downloading results:", error);
    }
  };

  useEffect(() => {
    const fetchCourse = async () => {
      try {
        const response = await fetch(`http://localhost:8008/api/courses/id/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`, // Dodajte ovu liniju kako biste poslali token
            "Content-Type": "application/json", // Ovisno o potrebi, možda trebate dodati i Content-Type
          },
        });
        if (!response.ok) {
          throw new Error("Failed to fetch course");
        }
        const courseData = await response.json();
        setTitle(courseData.title);
        setDescription(courseData.description);
        setAssignments(courseData.homeworkList);
        setLessons(courseData.lessonList);
        setQuizzes(courseData.quizList);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching course:", error);
        setLoading(false);
      }
    };

    fetchCourse();
  }, [id, token]);



  if (loading) {
    return <Loader />;
  }

  return (
    <div className="userCategoryPage__container">
      <div className="userCategoryPage__sidebar">
        <SidebarInstructor />
      </div>
      <div className="userCategoryPage__content">
        <div className="userCategoryPage__header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h2 className="userCategoryPage__title">{title}</h2>
          <div className="buttons" style={{ display: 'flex', justifyContent: 'flex-end' }}>
            <Link to="/instructorAddQuiz" className="btn btn-primary" style={{ marginRight: '10px' }}>Add Quiz</Link>
            <Link to={`/instructor/add-assignment/${id}`} className="btn btn-primary" style={{ marginRight: '10px' }}>Add Assignment</Link>
            <Link to={`/instructor/add-lesson/${id}`} className="btn btn-primary">Add Lesson</Link>

          </div>
        </div>
        <p>{description}</p>
        <>
          <h3>Lessons</h3>
          {lessons.length === 0 ? (
            <Message>No lessons available.</Message>
          ) : (
            <ListGroup className="userCategoryPage__content--categoriesList">
              {lessons.map((lesson, index) => (
                <ListGroup.Item style={{ borderWidth: "5px" }} key={index} className="userCategoryPage__item">
                  <div className="ms-2 me-auto">
                    <div className="fw-bold">{lesson.tittle}</div>
                    {lesson.description}
                  </div>
                  <a href={`${lesson.url}`} className="userCategoryPage__link">Open lesson</a>

                </ListGroup.Item>
              ))}
            </ListGroup>
          )}

          <h3>Assignments</h3>
          {assignments.length === 0 ? (
            <Message>No assignments available.</Message>
          ) : (
            <ListGroup className="userCategoryPage__content--categoriesList">
              {assignments.map((assignment, index) => (
                <ListGroup.Item style={{ borderWidth: "5px" }} key={index} className="userCategoryPage__item">
                  <div className="ms-2 me-auto">
                    <div className="fw-bold">{assignment.title}</div>
                    {assignment.description}
                  </div>
                  <a href={`/instructor/assignment/${assignment.id}`} className="userCategoryPage__link">Open assignment</a>
                </ListGroup.Item>
              ))}
            </ListGroup>
          )}

          <h3>Quizzes</h3>
          {quizzes.length === 0 ? (
            <Message>No quizzes available.</Message>
          ) : (
            <ListGroup className="userCategoryPage__content--categoriesList">
              {quizzes.map((quiz, index) => (
                <ListGroup.Item style={{ borderWidth: "5px" }} key={index} className="userCategoryPage__item">
                  <div className="ms-2 me-auto">
                    
                    <div className="fw-bold">{quiz.title}
                    </div>
                    <div className="buttons" style={{ float: 'right' }}>
                    <Button onClick={() => generatePdf(quiz.id, quiz.title)}>Print results</Button></div>
                    {quiz.description}
                  </div>
                  
                  <Link to={`/quiz/result/${quiz.id}`} className="userCategoryPage__link">Open quiz</Link>

                </ListGroup.Item>
              ))}
            </ListGroup>
          )}
        </>
      </div>
    </div>
  );
};

export default InstructorCategoryPage;