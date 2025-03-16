import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import swal from "sweetalert";
import { Table, Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import SidebarUser from "../../components/SidebarUser";
import "./UserSubmitHomeworkPage.css";

const UserSubmitHomeworkPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const loginReducer = useSelector((state) => state.loginReducer);
  const user = loginReducer.user;
  const token = JSON.parse(localStorage.getItem("jwtToken"));
  const { id } = useParams(); // Extract the id from the route
  const [maxPoints, setMaxPoints] = useState(null);
  const [deadlineDate, setDeadlineDate] = useState(null);
  const [startDate, setStartDate] = useState([]);
  const [title, setTitle] = useState([]);
  const [url, setUrl] = useState('');
  const [description, setDescription] = useState([]);
  const [assignmentId, setAssignmentId] = useState(null);
  const [homeworkAssignmentStatus, setHomeworkAssignmentStatus] = useState(null);
  const [points, setPoints] = useState(null);
  const [submitDate, setSubmitDate] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isCompleted, setIsCompleted] = useState(false);

  useEffect(() => {
    const fetchHomework = async () => {
      try {
        const response = await fetch(`http://localhost:8008/api/homework/id/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });
        if (!response.ok) {
          throw new Error("Failed to fetch homework");
        }
        const homeworkData = await response.json();
        setMaxPoints(homeworkData.maxPoints);
        setDeadlineDate(homeworkData.deadlineDate);
        setStartDate(homeworkData.startDate);
        setTitle(homeworkData.title);
        setDescription(homeworkData.description);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching homework:", error);
        setLoading(false);
      }
    };

    fetchHomework();
  }, [id, token]);

  useEffect(() => {
    const fetchHomeworkAssignment = async () => {
      try {
        const response = await fetch(`http://localhost:8008/api/homework-assignment/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });
        if (!response.ok) {
          throw new Error("Failed to fetch homework assignment");
        }
        const homeworkData = await response.json();
        setAssignmentId(homeworkData.id);
        setHomeworkAssignmentStatus(homeworkData.homeworkAssignmentStatus);
        setPoints(homeworkData.points);
        setSubmitDate(homeworkData.submitDate);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching homework assignment:", error);
        setLoading(false);
      }
    };

    fetchHomeworkAssignment();
  }, [id, token]);

  useEffect(() => {
    if (homeworkAssignmentStatus === "COMPLETED") {
      setIsCompleted(true);
    } else {
      setIsCompleted(false);
    }
  }, [homeworkAssignmentStatus]);

  useEffect(() => {
    if (!localStorage.getItem("jwtToken")) navigate("/");
  }, []);

  const submitHomework = async () => {
    try {
      const response = await fetch(`http://localhost:8008/api/homework-assignment/submit`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: assignmentId,
          homeworkId: id,
          studentId: user.id,
          homeworkAssignmentStatus: homeworkAssignmentStatus,
          points: points,
          submitDate: submitDate,
          homeworkDataUrl: url,
        }),
      });
      if (!response.ok) {
        throw new Error("Failed to submit homework");
      } else {
        swal("Successfully!", `You submitted ${title} homework successfully`, "success");
        console.log(url);
        console.log(await response.json());
        navigate(`/profile`);

      }
    } catch (error) {
      console.error("Error submitting homework:", error);
    }
  };

  return (
      <div className="userProfilePage__container">
        <div className="userProfilePage__sidebar">
          <SidebarUser />
        </div>
        {user && (
            <div className="userProfilePage__content">
              <Table bordered className="userProfilePage__content--table">
                <tbody>
                <tr>
                  <td>Name</td>
                  <td>{`${user.firstName} ${user.lastName}`}</td>
                </tr>
                <tr>
                  <td>Homework</td>
                  <td>{title}</td>
                </tr>
                <tr>
                  <td>Description</td>
                  <td>{description}</td>
                </tr>
                <tr>
                  <td>Deadline</td>
                  <td>{deadlineDate} 00:00</td>
                </tr>
                <tr>
                  <td>Maximum points</td>
                  <td>{maxPoints}</td>
                </tr>
                </tbody>
              </Table>
              {!isCompleted ? (
                  <div>
                    <form>
                      <label htmlFor="name">Link to homework: </label>
                      <input
                          value={url}
                          onChange={(e) => setUrl(e.target.value)}
                          type="text"
                          id="name"
                      />
                    </form>
                    <Button onClick={submitHomework} className="btn2">
                      Submit Homework
                    </Button>
                  </div>
              ) : (
                  <p>COMPLETED</p>
              )}
            </div>
        )}
      </div>
  );
};

export default UserSubmitHomeworkPage;

