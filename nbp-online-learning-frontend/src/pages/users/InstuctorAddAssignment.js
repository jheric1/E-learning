import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button, Form } from "react-bootstrap";
import "./InstructorAddAssignment.css";
import { useDispatch, useSelector } from "react-redux";
import swal from "sweetalert";
import SidebarInstructor from "../../components/SidebarInstructor";
import FormContainer from "../../components/FormContainer";
import * as categoriesConstants from "../../constants/categoriesConstants";
import { addAssignment } from "../../actions/categoriesActions";
import { useParams } from "react-router-dom";

const token = JSON.parse(localStorage.getItem("jwtToken"));
const InstructorAddAssignment = () => {
  const { id } = useParams();
  const [courseName, setCourseName] = useState("");
  const [title, setTitle] = useState(""); 
  const [description, setDescription] = useState("");
  const [startDate, setStartDate] = useState("");
  const [deadlineDate, setDeadlineDate] = useState("");
  const [maxPoints, setMaxPoints] = useState(1);


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
      const assignment = {
        title: title,
        description: description,
        startDate: startDate,
        deadlineDate: deadlineDate,
        courseId: id,
        maxPoints: parseInt(maxPoints)
      };

      addAssignment(dispatch, assignment, token).then((data) => {
        if (data.type === categoriesConstants.ADD_ASSIGNMENT_SUCCESS) {
          swal("Assignment Added!", `${assignment.title} successfully added`, "success");
          navigate(`/instructor/course/${id}`);
        } else {
          swal("Assignment Not Added!", `${assignment.title} not added`, "error");
        }
      });
    } catch(e) {
      alert("Assignment Not Added!");
    }
  };

  useEffect(() => {
    if (!localStorage.getItem("jwtToken")) navigate("/");
  }, [navigate]);

  return (
    <div className="adminAddQuizPage__container">
      <div className="adminAddQuizPage__sidebar">
        <SidebarInstructor />
      </div>
      <div className="adminAddQuizPage__content">
        <FormContainer>
          <h1>{courseName}</h1>
          <h2>Add assignment</h2>
          <Form onSubmit={submitHandler}>
            <Form.Group className="my-3" controlId="title">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Assignment Title"
                value={title}
                onChange={(e) => {
                  setTitle(e.target.value);
                }}
              ></Form.Control>
            </Form.Group>

            <Form.Group className="my-3" controlId="description">
              <Form.Label>Description</Form.Label>
              <Form.Control
                style={{ textAlign: "top" }}
                as="textarea"
                rows="3"
                type="text"
                placeholder="Enter Assignment Description"
                value={description}
                onChange={(e) => {
                  setDescription(e.target.value);
                }}
              ></Form.Control>
            </Form.Group>

            <Form.Group className="my-3" controlId="maxPoints">
              <Form.Label>Maximum Points</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Maximum Points"
                value={maxPoints}
                onChange={(e) => {
                  setMaxPoints(e.target.value);
                }}
              ></Form.Control>
            </Form.Group> 
            <div className="my-3">
              <Form.Group className="my-3" controlId="startDate">
                <Form.Label>Start Date</Form.Label>
                <Form.Control
                  type="date"
                  value={startDate}
                  onChange={(e) => setStartDate(e.target.value)}
                />
              </Form.Group>

              <Form.Group className="my-3" controlId="deadlineDate">
                <Form.Label>Deadline Date</Form.Label>
                <Form.Control
                  type="date"
                  value={deadlineDate}
                  onChange={(e) => setDeadlineDate(e.target.value)}
                />
              </Form.Group>

            </div>
            <Button
              className="my-5 adminAddQuizPage__content--button"
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

export default InstructorAddAssignment;
