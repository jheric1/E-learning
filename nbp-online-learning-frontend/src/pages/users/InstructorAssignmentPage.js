import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import SidebarInstructor from "../../components/SidebarInstructor";
import "./InstructorAddLesson.css";
import { Table, Button, Form } from "react-bootstrap";

const InstructorAssignmentPage = () => {
    const { id } = useParams(); // ID zadatka iz URL-a
    const navigate = useNavigate();
    const [submissions, setSubmissions] = useState([]); // State for storing the student submissions
    const [points, setPoints] = useState([]); // State for storing points
    const [evaluated, setEvaluated] = useState([]); // State for tracking evaluated submissions
    const [touched, setTouched] = useState([]); // State for tracking if input fields have been touched
    const [hasPoints, setHasPoints] = useState([]);
    const [maxPoints, setMaxPoints] = useState([]); // State for storing max points for each submission

    // Fetch assignment submissions
    useEffect(() => {
        const fetchSubmissions = async () => {
            const token = JSON.parse(localStorage.getItem("jwtToken"));
            if (!token) {
                navigate("/");
                return;
            }

            try {
                const response = await fetch(`http://localhost:8008/api/homework-assignment/homework-results/${id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (!response.ok) {
                    throw new Error("Failed to fetch submissions");
                }

                const data = await response.json();
                const initialPoints = new Array(data.length).fill('');
                setSubmissions(data); // Set the submissions data
                setPoints(initialPoints); // Initialize points state
                setEvaluated(new Array(data.length).fill(false)); // Initialize evaluated state
                setTouched(new Array(data.length).fill(false)); // Initialize touched state

                const initialHasPoints = data.map(submission => submission.points > 0);
                setHasPoints(initialHasPoints);

                const initialMaxPoints = data.map(submission => submission.maxPoints);
                setMaxPoints(initialMaxPoints);

            } catch (error) {
                console.error("Error fetching submissions:", error);
            }

        };

        fetchSubmissions();
    }, [id, navigate]);

    // Check if the user is logged in
    useEffect(() => {
        if (!localStorage.getItem("jwtToken")) navigate("/");
    }, [navigate]);

    // Handler for points input change
    const handlePointsChange = (index, value, maxPoints) => {
        // Provjeri je li uneseni broj manji od maksimalnog broja bodova
        if (parseFloat(value) > maxPoints) {
            alert(`Maximum points allowed is ${maxPoints}`);
            console.log("maxx", maxPoints)
            return;
        }

        const newPoints = [...points];
        newPoints[index] = value;
        setPoints(newPoints);

        const newTouched = [...touched];
        newTouched[index] = true;
        setTouched(newTouched);

    };

    const handleEvaluate = async (index) => {
        const token = JSON.parse(localStorage.getItem("jwtToken"));
        if (!token) {
            navigate("/");
            return;
        }

        const submission = submissions[index];
        const point = points[index];

        // Check if the points have been entered
        if (!point.trim()) {
            alert("Please enter points before evaluating.");
            return;
        }

        const payload = {
            homeworkAssignmentId: submission.homeworkAssignmentId,
            points: point
        };

        try {
            const response = await fetch(`http://localhost:8008/api/homework-assignment/grade-homework`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(payload),
            });

            if (!response.ok) {
                throw new Error('Failed to evaluate submission');
            }

            // Update the submission's evaluated status
            const updatedEvaluated = [...evaluated];
            updatedEvaluated[index] = true;
            setEvaluated(updatedEvaluated);

            console.log(`Successfully evaluated submission for ${submission.email}`);
        } catch (error) {
            console.error('Error evaluating submission:', error);
        }
        window.location.reload();
    };

    const handleDeletePoints = async (index) => {
        const token = JSON.parse(localStorage.getItem("jwtToken"));
        if (!token) {
            navigate("/");
            return;
        }

        const submission = submissions[index];

        const payload = {
            homeworkAssignmentId: submission.homeworkAssignmentId,
            points: 0
        };

        try {
            const response = await fetch(`http://localhost:8008/api/homework-assignment/grade-homework`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(payload),
            });

            if (!response.ok) {
                throw new Error('Failed to delete points');
            }

            // Reset the points and update the state
            const updatedPoints = [...points];
            updatedPoints[index] = '';
            setPoints(updatedPoints);

            const updatedEvaluated = [...evaluated];
            updatedEvaluated[index] = false;
            setEvaluated(updatedEvaluated);

            const updatedHasPoints = [...hasPoints];
            updatedHasPoints[index] = false;
            setHasPoints(updatedHasPoints);

            console.log(`Successfully deleted points for ${submission.email}`);
        } catch (error) {
            console.error('Error deleting points:', error);
        }
        window.location.reload();
    };

    return (
        <div className="adminAddQuestionPage__container">
            <div className="adminAddQuestionPage__sidebar">
                <SidebarInstructor />
            </div>
            <div className="adminAddQuestionPage__content">
                <h3>Student Assignments</h3>
                <Table striped bordered hover className="student-assignments-table">
                    <thead>
                    <tr>
                        <th>Student Name</th>
                        <th>Email</th>
                        <th>Assignment Link</th>
                        <th>Points</th>
                    </tr>
                    </thead>
                    <tbody>
                    {submissions.length > 0 ? (
                        submissions.map((submission, index) => (
                            <tr key={submission.email}>
                                <td>{`${submission.firstName} ${submission.lastName}`}</td>
                                <td>{submission.email}</td>

                                <td style={{ width: '40%' }}>
                                    <a href={submission.homeworkDataUrl} target="_blank" rel="noopener noreferrer">
                                        View Assignment
                                    </a>
                                </td>
                                <td>
                                    {submission.points > 0 ? (
                                        <div className="d-flex align-items-center">
                                            <span style={{ marginRight: '5rem' }}>{submission.points}</span>
                                            <Button
                                                variant="danger"
                                                size="sm"
                                                onClick={() => handleDeletePoints(index)}
                                            >
                                                Delete Points
                                            </Button>
                                        </div>
                                    ) : (
                                        <div className="d-flex align-items-center">
                                            <Form.Control
                                                type="number"
                                                step="0.1"
                                                value={points[index]}
                                                onChange={(e) => handlePointsChange(index, e.target.value, maxPoints[index])}
                                                className="form-control form-control-sm"
                                                style={{ width: '80px', marginRight: '10px' }}
                                                disabled={evaluated[index] || hasPoints[index]}
                                                isInvalid={touched[index] && !points[index].trim()}
                                                max={maxPoints[index]} // postavi max atribut
                                            />
                                            {!evaluated[index] && !hasPoints[index] && (
                                                <Button
                                                    variant="success"
                                                    size="sm"
                                                    onClick={() => handleEvaluate(index)}
                                                >
                                                    Evaluate
                                                </Button>
                                            )}
                                        </div>
                                    )}
                                    <Form.Control.Feedback type="invalid">
                                        Points are required.
                                    </Form.Control.Feedback>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="4">Loading submissions...</td>
                        </tr>
                    )}
                    </tbody>
                </Table>
            </div>
        </div>
    );
};

export default InstructorAssignmentPage;
