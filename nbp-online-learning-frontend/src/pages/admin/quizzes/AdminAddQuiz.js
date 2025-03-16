import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button, Form, Col, Row } from "react-bootstrap";
import "./AdminAddQuiz.css";
import { useDispatch, useSelector } from "react-redux";
import swal from "sweetalert";
import SidebarInstructor from "../../../components/SidebarInstructor";
import FormContainer from "../../../components/FormContainer";
import * as quizzesConstants from "../../../constants/quizzesConstants";
import { addQuiz } from "../../../actions/quizzesActions";
import { fetchInstructorsCategories } from "../../../actions/categoriesActions";

const AdminAddQuiz = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [startDate, setStartDate] = useState("");
  const [deadlineDate, setDeadlineDate] = useState("");
  const [isActive, setIsActive] = useState(false);
  const [selectedCategoryId, setSelectedCategoryId] = useState(null);
  const [questions, setQuestions] = useState([]);

  const categoriesReducer = useSelector((state) => state.categoriesReducer);
  const [categories, setCategories] = useState(categoriesReducer.categories);

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const onClickPublishedHandler = () => {
    setIsActive(!isActive);
  };

  const onSelectCategoryHandler = (e) => {
    const selectedCategoryTitle = e.target.value;

    const selectedCategory = categories.find(
      (cat) => cat.title === selectedCategoryTitle
    );
    if (selectedCategory) {
      setSelectedCategoryId(selectedCategory.id);
    }
  };

  const token = JSON.parse(localStorage.getItem("jwtToken"));

  const submitHandler = (e) => {
    e.preventDefault();
    if (selectedCategoryId !== null && selectedCategoryId !== "n/a") {
      const quizDTO = {
        quiz:{
        title: title,
        description: description,
        startDate: startDate,
        active: isActive,
        deadlineDate: deadlineDate,
        courseId: selectedCategoryId},
        questions: questions,
      };

      addQuiz(dispatch, quizDTO, token).then((data) => {
        if (data.type === quizzesConstants.ADD_QUIZ_SUCCESS) {
          swal("Quiz Added!", `${quizDTO.quiz.title} successfully added`, "success");
        } else {
          swal("Quiz Not Added!", `${quizDTO.quiz.title} not added`, "error");
        }
      });
    } else {
      alert("Select valid category!");
    }
  };

  useEffect(() => {
    if (!localStorage.getItem("jwtToken")) navigate("/");
  }, [navigate]);

  useEffect(() => {
    if (categories.length === 0) {
      fetchInstructorsCategories(dispatch, token).then((data) => {
        setCategories(data.payload);
      });
    }
  }, [categories.length, dispatch, token]);

  const addQuestionHandler = () => {
    setQuestions([
      ...questions,
      { text: "", answerOptions: [{ answerData: "", isCorrect: false }] },
    ]);
  };

  const handleQuestionTextChange = (index, value) => {
    const updatedQuestions = questions.map((question, qIndex) => {
      if (qIndex === index) {
        return { ...question, text: value };
      }
      return question;
    });
    setQuestions(updatedQuestions);
  };

  const handleAnswerTextChange = (qIndex, aIndex, value) => {
    const updatedQuestions = questions.map((question, questionIndex) => {
      if (questionIndex === qIndex) {
        const updatedAnswerOptions = question.answerOptions.map((answer, answerIndex) => {
          if (answerIndex === aIndex) {
            return { ...answer, answerData: value };
          }
          return answer;
        });
        return { ...question, answerOptions: updatedAnswerOptions };
      }
      return question;
    });
    setQuestions(updatedQuestions);
  };

  const handleCorrectChange = (qIndex, aIndex) => {
    const updatedQuestions = questions.map((question, questionIndex) => {
      if (questionIndex === qIndex) {
        const updatedAnswerOptions = question.answerOptions.map((answer, answerIndex) => {
          return { ...answer, isCorrect: answerIndex === aIndex };
        });
        return { ...question, answerOptions: updatedAnswerOptions };
      }
      return question;
    });
    setQuestions(updatedQuestions);
  };

  const addAnswerHandler = (qIndex) => {
    const updatedQuestions = questions.map((question, questionIndex) => {
      if (questionIndex === qIndex) {
        if (question.answerOptions.length < 4) {
          return {
            ...question,
            answerOptions: [...question.answerOptions, { answerData: "", isCorrect: false }],
          };
        }
      }
      return question;
    });
    setQuestions(updatedQuestions);
  };

  return (
    <div className="adminAddQuizPage__container">
      <div className="adminAddQuizPage__sidebar">
        <SidebarInstructor />
      </div>
      <div className="adminAddQuizPage__content">
        <FormContainer>
          <h2>Add Quiz</h2>
          <Form onSubmit={submitHandler}>
            <Form.Group className="my-3" controlId="title">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Quiz Title"
                value={title || ""}
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
                placeholder="Enter Quiz Description"
                value={description || ""}
                onChange={(e) => {
                  setDescription(e.target.value);
                }}
              ></Form.Control>
            </Form.Group>

            <Form.Check
              className="my-3"
              type="switch"
              id="publish-switch"
              label="Publish Quiz"
              onChange={onClickPublishedHandler}
              checked={isActive}
            />

            <div className="my-3">
              <label htmlFor="category-select">Choose a Category:</label>
              <Form.Select
                aria-label="Choose Category"
                id="category-select"
                onChange={onSelectCategoryHandler}
              >
                <option value="n/a">Choose Category</option>
                {categories ? (
                  categories.map((cat, index) => (
                    <option key={index} value={cat.title}>
                      {cat.title}
                    </option>
                  ))
                ) : (
                  <option value="">Choose one from below</option>
                )}
              </Form.Select>

              <Form.Group className="my-3" controlId="startDate">
                <Form.Label>Start Date</Form.Label>
                <Form.Control
                  type="date"
                  value={startDate || ""}
                  onChange={(e) => setStartDate(e.target.value)}
                />
              </Form.Group>

              <Form.Group className="my-3" controlId="deadlineDate">
                <Form.Label>Deadline Date</Form.Label>
                <Form.Control
                  type="date"
                  value={deadlineDate || ""}
                  onChange={(e) => setDeadlineDate(e.target.value)}
                />
              </Form.Group>
            </div>

            {questions.map((question, qIndex) => (
              <div key={qIndex}>
                <Form.Group className="my-3">
                  <Form.Label>Question {qIndex + 1}</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Enter Question"
                    value={question.text || ""}
                    onChange={(e) =>
                      handleQuestionTextChange(qIndex, e.target.value)
                    }
                  />
                </Form.Group>
                {question.answerOptions.map((answer, aIndex) => (
                  <Row key={aIndex} className="align-items-center my-3">
                    <Col md={8}>
                      <Form.Control
                        type="text"
                        placeholder={`Enter Answer ${aIndex + 1}`}
                        value={answer.answerData || ""}
                        onChange={(e) =>
                          handleAnswerTextChange(qIndex, aIndex, e.target.value)
                        }
                      />
                    </Col>
                    <Col md={4}>
                      <Form.Check
                        type="radio"
                        label="Correct"
                        name={`correct-answer-${qIndex}`}
                        checked={answer.isCorrect}
                        onChange={() => handleCorrectChange(qIndex, aIndex)}
                      />
                    </Col>
                  </Row>
                ))}
                <Button
                  className="my-3"
                  onClick={() => addAnswerHandler(qIndex)}
                >
                  Add Answer
                </Button>
              </div>
            ))}
            <Button className="my-3" onClick={addQuestionHandler}>
              Add Question
            </Button>
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

export default AdminAddQuiz;
