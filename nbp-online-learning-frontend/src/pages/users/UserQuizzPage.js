import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import SidebarUser from "../../components/SidebarUser";
import swal from "sweetalert";
import "./UserQuizzPage.css";
import { useNavigate } from "react-router-dom";

const UserQuizzPage = () => {
  const dispatch = useDispatch();
  const loginReducer = useSelector((state) => state.loginReducer);
  const user = loginReducer.user;
  const token = JSON.parse(localStorage.getItem("jwtToken"));
  const { id } = useParams(); // Extract the id from the route
  const [maxPoints, setMaxPoints] = useState(null);
  const [deadlineDate, setDeadlineDate] = useState(null);
  const [description, setDescription] = useState("");
  const [questions, setQuestions] = useState([]);
  const [quizzTitle, setQuizzTitle] = useState("");
  const [quizAssignmentStatus, setAssignmentStatus] = useState("");
  const [loading, setLoading] = useState(true);
  const [quizAssignmentId, setQuizAssignmentId] = useState(null);
  const [selectedAnswers, setSelectedAnswers] = useState({});
  const [courseId, setCourseId] = useState(null);
  const [totalPoints, setTotalPoints] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchQuizzAssignmentById = async () => {
      try {
        const response = await fetch(
          `http://localhost:8008/api/quizzes/quiz-assignment/${id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        if (!response.ok) {
          throw new Error("Failed to fetch quizz");
        }
        const quizz = await response.json();
        setQuizAssignmentId(parseInt(quizz.id));
        setAssignmentStatus(quizz.quizAssignmentStatus);
        setQuizzTitle(quizz.quiz.quiz.title);
        setDeadlineDate(quizz.quiz.quiz.deadlineDate);
        setDescription(quizz.quiz.quiz.description);
        setQuestions(quizz.quiz.questions);
        setCourseId(quizz.quiz.quiz.courseId);
        setTotalPoints(quizz.totalPoints);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching quizz:", error);
        setLoading(false);
      }
    };

    fetchQuizzAssignmentById();
  }, [id, token]);

  const handleAnswerChange = (questionIndex, answerIndex) => {
    setSelectedAnswers((prevAnswers) => {
      const currentAnswers = prevAnswers[questionIndex] || [];
      if (currentAnswers.includes(answerIndex)) {
        return {
          ...prevAnswers,
          [questionIndex]: currentAnswers.filter((index) => index !== answerIndex),
        };
      } else {
        return {
          ...prevAnswers,
          [questionIndex]: [...currentAnswers, answerIndex],
        };
      }
    });
  };

  const handleSubmit = async () => {
    if (quizAssignmentStatus === "Submitted") {
      swal("Quiz Submitted!", `${quizzTitle} successfully submitted`, "success");
      return;
    }

    const answerList = [];
    for (const questionIndex in selectedAnswers) {
      selectedAnswers[questionIndex].forEach((answerIndex) => {
        answerList.push({
          answerData: questions[questionIndex].answerOptions[answerIndex].answerData,
          questionId: questions[questionIndex].id,
        });
      });
    }

    try {
      const response = await fetch(
        `http://localhost:8008/api/quizzes/quiz-assignment/submit-quiz/${quizAssignmentId}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify(answerList),
        }
      );
      if (!response.ok) {
        throw new Error("Failed to submit answers");
      }
      console.log("Answers submitted successfully");
      swal("Quiz Submitted!", `${quizzTitle} successfully submitted`, "success");
      navigate(`/course/${courseId}`); // Replace with your desired redirect path
    } catch (error) {
      console.error("Error submitting answers:", error);
      swal("Quiz Not submitted!", `${quizzTitle} not submitted`, "error");
    }
  };

  return (
    <div className="userProfilePage__container">
      <div className="userProfilePage__sidebar">
        <SidebarUser />
      </div>
      <div className="quizz-container">
        <div className="descriptionQ">
          <div className="intro-quizz">
            <h1>{quizzTitle}</h1>
            <p>{description}</p>
            <p>Deadline Date: {deadlineDate}</p>
          </div>
        </div>
        {loading ? (
          <div>Loading...</div>
        ) : quizAssignmentStatus === "COMPLETED" ? (
          <div className="completed-message">
            <p>You already submitted this quiz.</p>
            <p>
              Points: {totalPoints} /{" "}
              {questions.reduce((sum, question) => sum + question.maxPoints, 0)}
            </p>
          </div>
        ) : (
          <>
            {questions.map((question, questionIndex) => (
              <div key={questionIndex} className="quizz-question">
                <p className="question-text">{question.text}</p>
                {question.answerOptions.map((option, answerIndex) => (
                  <div key={answerIndex} className="answer-option">
                    <input
                      type="checkbox"
                      name={`question-${questionIndex}`}
                      value={answerIndex}
                      checked={
                        selectedAnswers[questionIndex]
                          ? selectedAnswers[questionIndex].includes(answerIndex)
                          : false
                      }
                      onChange={() => handleAnswerChange(questionIndex, answerIndex)}
                    />
                    <label>{option.answerData}</label>
                  </div>
                ))}
                <p className="max-points">Max Points: {question.maxPoints}</p>
              </div>
            ))}
            <button className="submit-button" onClick={handleSubmit}>
              Submit Response
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default UserQuizzPage;
