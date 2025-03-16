import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import SidebarInstructor from "../../components/SidebarInstructor";
import "./InstructorAddLesson.css";
import { Table } from "react-bootstrap";

const InstructorQuizResultPage = () => {
    const token = JSON.parse(localStorage.getItem("jwtToken"));
    const { id } = useParams(); // ID zadatka iz URL-a
    const navigate = useNavigate();
    const [quiz, setQuiz] = useState(null); // State for quiz data
    const [title, setTitle] = useState(""); 
    const [startDate, setStartDate] = useState();
    const [description, setDescription] = useState(""); 
    useEffect(() => {
        const fetchQuiz = async () => {
            const token = JSON.parse(localStorage.getItem("jwtToken"));
            if (!token) {
                navigate("/");
                return;
            }

            try {
                const response = await fetch(`http://localhost:8008/api/quizzes/quiz-results/${id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch quiz");
                }
                const data = await response.json();
                setQuiz(data);
            } catch (error) {
                console.error("Error fetching quiz:", error);
            }
        };

        fetchQuiz();
    }, [id, navigate]);

    // Provjera je li korisnik prijavljen
    useEffect(() => {
        if (!localStorage.getItem("jwtToken")) navigate("/");
    }, [navigate]);
    useEffect(() => {
        const fetchQuizInfo = async () => {
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
    
        fetchQuizInfo();
      }, [id, token]);
    return (
        <div className="adminAddQuestionPage__container">
            <div className="adminAddQuestionPage__sidebar">
                <SidebarInstructor />
            </div>
            <div className="adminAddQuestionPage__content">
                <h3>Quizz: {title} </h3>
                <p> Info: {description}</p>
                {quiz ? (
                    <div>
                        <h3>Student quizz achievements</h3>
                        <Table striped bordered hover className="student-assignments-table">
                            <thead>
                                <tr>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Email</th>
                                    <th>Total Points</th>
                                </tr>
                            </thead>
                            <tbody>
                                {quiz.map((student, index) => (
                                    <tr key={index}>
                                        <td>{student.firstName}</td>
                                        <td>{student.lastName}</td>
                                        <td>{student.email}</td>
                                        <td>{student.totalPoints}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </Table>
                    </div>
                ) : (
                    <p>Loading quiz...</p>
                )}
            </div>
        </div>
    );
};

export default InstructorQuizResultPage;
