import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button, Form } from "react-bootstrap";
import "./InstructorAddLesson.css";
import { useDispatch } from "react-redux";
import swal from "sweetalert";
import SidebarInstructor from "../../components/SidebarInstructor";
import FormContainer from "../../components/FormContainer";
import * as categoriesConstants from "../../constants/categoriesConstants";
import { addLesson } from "../../actions/categoriesActions"; // Promijenite ovu akciju prema vašem kodu
import { useParams } from "react-router-dom";

const token = JSON.parse(localStorage.getItem("jwtToken"));
const InstructorAddLesson = () => {
  const { id } = useParams();
  const [courseName, setCourseName] = useState("");
  const [title, setTitle] = useState(""); 
  const [url, setURL] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [isActive, setIsActive] = useState(false);

  const onClickPublishedHandler = () => {
    setIsActive(!isActive);
  };

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
        setCourseName(courseData.title);
        
      } catch (error) {
        console.error("Error fetching course:", error);
      }
    };

    fetchCourse();
  }, [id, token]);

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const submitHandler = (e) => {
    e.preventDefault();
    try {
      const lesson = {
        tittle: title,
        startDate: startDate,
        endDate: endDate,
        courseId: id,
        active: isActive,
        lessonData: null,
        lessonType: "EXTERNAL",
        url: url // Dodano za URL lekcije
      };

      addLesson(dispatch, lesson, token).then((data) => { // Promijenite ovu akciju prema vašem kodu
        if (data.type === categoriesConstants.ADD_LESSON_SUCCESS) {
          swal("Lesson Added!", `${lesson.title} successfully added`, "success");
          navigate(`/instructor/course/${id}`);
        } else {
          swal("Lesson Not Added!", `${lesson.title} not added`, "error");
        }
      });
    } catch(e) {
      alert("Lesson Not Added!");
    }
  };

  useEffect(() => {
    if (!localStorage.getItem("jwtToken")) navigate("/");
  }, [navigate]);

  return (
    <div className="adminAddLessonPage__container">
      <div className="adminAddLessonPage__sidebar">
        <SidebarInstructor />
      </div>
      <div className="adminAddLessonPage__content">
        <FormContainer>
          <h1>{courseName}</h1>
          <h2>Add Lesson</h2>
          <Form onSubmit={submitHandler}>
            <Form.Group className="my-3" controlId="title">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Lesson Title"
                value={title}
                onChange={(e) => {
                  setTitle(e.target.value);
                }}
              ></Form.Control>
            </Form.Group>

            <Form.Group className="my-3" controlId="url">
              <Form.Label>Lesson URL</Form.Label>
              <Form.Control
                style={{ textAlign: "top" }}
                as="textarea"
                rows="3"
                type="text"
                placeholder="Enter Lesson URL"
                value={url}
                onChange={(e) => {
                  setURL(e.target.value);
                }}
              ></Form.Control>
            </Form.Group>



            <Form.Group className="my-3" controlId="startDate">
              <Form.Label>Start Date</Form.Label>
              <Form.Control
                type="date"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
              />
            </Form.Group>

            <Form.Group className="my-3" controlId="endDate">
              <Form.Label>End Date</Form.Label>
              <Form.Control
                type="date"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
              />
            </Form.Group>
            <Form.Check
              className="my-3"
              type="switch"
              id="publish-switch"
              label="Publish Lesson"
              onChange={onClickPublishedHandler}
              checked={isActive}
            />

            <Button
              className="my-5 adminAddLessonPage__content--button"
              type="submit"
              variant="primary"
            >
              Add
            </Button>
          </Form>
        </FormContainer>
      </div>
    </div>
  );
};

export default InstructorAddLesson;
