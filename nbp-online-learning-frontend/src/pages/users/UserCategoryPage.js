import React, { useEffect, useState } from "react";
import { Button, ListGroup } from "react-bootstrap";
import SidebarUser from "../../components/SidebarUser";
import Message from "../../components/Message";
import { useParams } from "react-router-dom";
import "./UserCategoryPage.css";
import Loader from "../../components/Loader";
import { useNavigate } from "react-router-dom";
import swal from "sweetalert";
import { Link } from "react-router-dom";

const UserCategoryPage = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [title, setTitle] = useState(null);
  const [description, setDescription] = useState(null);
  const [lessons, setLessons] = useState([]);
  const [quizzes, setQuizzes] = useState([]);
  const [assignments, setAssignments] = useState([]);
  const [loading, setLoading] = useState(true);
  const token = JSON.parse(localStorage.getItem("jwtToken"));

  useEffect(() => {
    const fetchCourse = async () => {
      try {
        const response = await fetch(`http://localhost:8008/api/courses/id/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
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

  const quitCourse = async () => {
    try {
      const response = await fetch(`http://localhost:8008/api/courses/quit-course/${id}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      if (!response.ok) {
        throw new Error("Failed to quit course");
      }
      else{
        swal("Successfully!", `Quit course ${title} successfully`, "success");
          navigate("/profile");
      }
      // Handle success, e.g., redirect to another page or show a message
      console.log("Successfully quit the course!");
    } catch (error) {
      console.error("Error quitting course:", error);
    }
  };

  if (loading) {
    return <Loader />;
  }

  return (
    <div className="userCategoryPage__container">
      <div className="userCategoryPage__sidebar">
        <SidebarUser />
      </div>
      <div className="userCategoryPage__content">
        <div className="userCategoryPage__header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h2 className="userCategoryPage__title">{title}</h2>
          <div className="buttons" style={{ display: 'flex', justifyContent: 'flex-end' }}>
            <Button onClick={quitCourse} className="btn btn-primary" style={{ marginRight: '10px' }}>Quit course</Button>
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
                  <div className="s">
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
                <ListGroup.Item style={{ borderWidth: "5px" }}
                key={index} className="userCategoryPage__item">
                  <div className="d-flex justify-content-between align-items-center">
                    <div>
                      <div className="fw-bold">{assignment.title}</div>
                      <div>{assignment.description}</div>
                      <div><span>Max points: </span>{assignment.maxPoints}</div>
                      <div><span className="fw-bold">Available:</span> {assignment.startDate} - {assignment.deadlineDate}</div>
                      <Link to={`/homework/${assignment.id}`} className="userCategoryPage__link">Open assignment</Link>
                    </div>
                  </div>
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
                  <div className="d-flex justify-content-between align-items-center">
                    <div>
                      <div className="fw-bold">{quiz.title}</div>
                      <div>{quiz.description}</div>
                      <div><span>Max points: </span>{quiz.maxPoints}</div>
                      <div><span className="fw-bold">Available:</span> {quiz.startDate} - {quiz.deadlineDate}</div>
                      <a href={`/quizz/${quiz.id}`} className="userCategoryPage__link">Open quiz</a>
                    </div>
                  </div>
                </ListGroup.Item>
              ))}
            </ListGroup>
          )}
        </>
      </div>
    </div>
  );
};

export default UserCategoryPage;
