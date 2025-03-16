import React, { useState, useEffect } from "react";
import { Button, Form } from "react-bootstrap";
import swal from "sweetalert";
import { addQuestion } from "../../../actions/questionsActions";
import FormContainer from "../../../components/FormContainer";
import SidebarInstructor from "../../../components/SidebarInstructor";
import * as questionsConstants from "../../../constants/questionsConstants";
import "./AdminAddQuestionsPage.css";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useParams } from "react-router-dom";

const token = JSON.parse(localStorage.getItem("jwtToken"));
const InstructorAddQuestionsPage = () => {
  const { id } = useParams();
  const [courseName, setCourseName] = useState("");
  const [title, setTitle] = useState(""); 
  const [startDate, setStartDate] = useState();
  const [description, setDescription] = useState(""); 
  const [content, setContent] = useState("");
  const [option1, setOption1] = useState("");
  const [option2, setOption2] = useState("");
  const [option3, setOption3] = useState("");
  const [option4, setOption4] = useState("");
  const [answer, setAnswer] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [currentDate, setCurrentDate] = useState(new Date());

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const onSelectAnswerHandler = (e) => {
    setAnswer(e.target.value);
  };

  useEffect(() => {
    if (!localStorage.getItem("jwtToken")) navigate("/");
  }, [navigate]);

  const submitHandler = (e) => {
    e.preventDefault();
    if (answer !== null && answer !== "n/a") {
      const question = {
        content: content,
        option1: option1,
        option2: option2,
        option3: option3,
        option4: option4,
        answer: answer,
        quiz: {
          quizId: id,
        },
      };

      addQuestion(dispatch, question, token).then((data) => {
        if (data.type === questionsConstants.ADD_QUESTION_SUCCESS) {
          swal("Question Added!", `${content} successfully added`, "success");
          setShowForm(false);
          resetForm();
        } else {
          swal("Question Not Added!", `${content} not added`, "error");
        }
      });
    } else {
      alert("Select valid answer!");
    }
  };

  const resetForm = () => {
    setContent("");
    setOption1("");
    setOption2("");
    setOption3("");
    setOption4("");
    setAnswer(null);
  };

  useEffect(() => {
    const fetchQuiz = async () => {
      try {
        const response = await fetch(`http://localhost:8008/api/quizzes/id/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });
        if (!response.ok) {
          throw new Error("Failed to fetch quiz");
        }
        const quizData = await response.json();
        setTitle(quizData.title);
        setDescription(quizData.description);
        setStartDate(new Date(quizData.startDate));
      } catch (error) {
        console.error("Error fetching quiz:", error);
      }
    };

    fetchQuiz();
  }, [id, token]);

  const showResultsHandler = () => {
    navigate(`/quiz/result/${id}`);
  };

  const warningStyle = {
    color: "red",
    backgroundColor: "lightyellow",
    padding: "10px",
    borderRadius: "5px",
  };

  return (
    <div className="adminAddQuestionPage__container">
      <div className="adminAddQuestionPage__sidebar">
        <SidebarInstructor />
      </div>
      <div className="adminAddQuestionPage__content">
        <FormContainer>
          <h2>Manage Questions</h2>
          <h3>{title}</h3>
          <h5>{description}</h5>
          {currentDate > startDate ? (
            <>
              <p style={warningStyle}>
                The quiz has already started, you cannot add new questions.
              </p>
              <Button
                className="my-5 adminAddQuestionPage__content--button"
                onClick={showResultsHandler}
                variant="secondary"
              >
                Show Results
              </Button>
            </>
          ) : showForm ? (
            <Form onSubmit={submitHandler}>
              <Form.Group className="my-3" controlId="content">
                <Form.Label>Question</Form.Label>
                <Form.Control
                  style={{ textAlign: "top" }}
                  as="textarea"
                  rows="3"
                  type="text"
                  placeholder="Enter Question Content"
                  value={content}
                  onChange={(e) => {
                    setContent(e.target.value);
                  }}
                ></Form.Control>
              </Form.Group>

              <Form.Group className="my-3" controlId="option1">
                <Form.Label>Option 1</Form.Label>
                <Form.Control
                  style={{ textAlign: "top" }}
                  as="textarea"
                  rows="2"
                  type="text"
                  placeholder="Enter Option 1"
                  value={option1}
                  onChange={(e) => {
                    setOption1(e.target.value);
                  }}
                ></Form.Control>
              </Form.Group>

              <Form.Group className="my-3" controlId="option2">
                <Form.Label>Option 2</Form.Label>
                <Form.Control
                  style={{ textAlign: "top" }}
                  as="textarea"
                  rows="2"
                  type="text"
                  placeholder="Enter Option 2"
                  value={option2}
                  onChange={(e) => {
                    setOption2(e.target.value);
                  }}
                ></Form.Control>
              </Form.Group>

              <Form.Group className="my-3" controlId="option3">
                <Form.Label>Option 3</Form.Label>
                <Form.Control
                  style={{ textAlign: "top" }}
                  as="textarea"
                  rows="2"
                  type="text"
                  placeholder="Enter Option 3"
                  value={option3}
                  onChange={(e) => {
                    setOption3(e.target.value);
                  }}
                ></Form.Control>
              </Form.Group>

              <Form.Group className="my-3" controlId="option4">
                <Form.Label>Option 4</Form.Label>
                <Form.Control
                  style={{ textAlign: "top" }}
                  as="textarea"
                  rows="2"
                  type="text"
                  placeholder="Enter Option 4"
                  value={option4}
                  onChange={(e) => {
                    setOption4(e.target.value);
                  }}
                ></Form.Control>
              </Form.Group>

              <div className="my-3">
                <label htmlFor="answer-select">Choose Correct Option:</label>
                <Form.Select
                  aria-label="Choose Correct Option"
                  id="answer-select"
                  onChange={onSelectAnswerHandler}
                >
                  <option value="n/a">Choose Option</option>
                  <option value="option1">Option 1</option>
                  <option value="option2">Option 2</option>
                  <option value="option3">Option 3</option>
                  <option value="option4">Option 4</option>
                </Form.Select>
              </div>
              <Button
                className="my-5 adminAddQuestionPage__content--button"
                type="submit"
                variant="primary"
              >
                Add
              </Button>
            </Form>
          ) : (
            <Button
              className="my-5 adminAddQuestionPage__content--button"
              onClick={() => setShowForm(true)}
              variant="primary"
            >
              Add Question
            </Button>
          )}
        </FormContainer>
      </div>
    </div>
  );
};

export default InstructorAddQuestionsPage;
