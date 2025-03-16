import React, { useEffect, useState } from "react";
import SidebarUser from "../../components/SidebarUser";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { fetchQuizResult } from "../../actions/quizResultActions";
import * as quizResultConstants from "../../constants/quizResultConstants";
import Sidebar from "../../components/Sidebar";

const AdminQuizResultPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const quizResultReducer = useSelector((state) => state.quizResultReducer);
  const [quizResults, setQuizResults] = useState(null);
  const token = JSON.parse(localStorage.getItem("jwtToken"));
  const user = JSON.parse(localStorage.getItem("user"));
  const userId = user ? user.userId : null;

  useEffect(() => {
    if (quizResults == null)
      fetchQuizResult(dispatch, null, token).then((data) => {
        if (data.type === quizResultConstants.FETCH_QUIZ_RESULT_SUCCESS) {
          setQuizResults(data.payload);
        }
      });
  }, []);

  useEffect(() => {
    if (!localStorage.getItem("jwtToken")) navigate("/");
  }, []);

  const generatePdf = async () => {
    try {
      const response = await fetch(`http://localhost:8008/api/users/generate-pdf`, {
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
      link.setAttribute("download","user_activity"); // Use the desired file name
      document.body.appendChild(link);
      link.click();
      link.parentNode.removeChild(link);

    } catch (error) {
      console.error("Error downloading results:", error);
    }
  };

  const buttonStyle = {
    backgroundColor: "green",
    color: "white",
    border: "none",
    padding: "10px 20px",
    fontSize: "16px",
    cursor: "pointer",
    borderRadius: "5px",
    margin: "10px 0",
    transition: "background-color 0.3s ease",
  };

  const buttonHoverStyle = {
    backgroundColor: "darkgreen",
  };

  return (
      <div style={{ display: "flex" }}>
        <div>
          <Sidebar />
        </div>
        <div style={{ padding: "20px" }}>
          <button
              style={buttonStyle}
              onMouseOver={(e) => e.currentTarget.style.backgroundColor = buttonHoverStyle.backgroundColor}
              onMouseOut={(e) => e.currentTarget.style.backgroundColor = buttonStyle.backgroundColor}
              onClick={() => generatePdf()}
          >
            Print Users Activity
          </button>
          {/* You can include additional content or elements here */}
        </div>
      </div>
  );
};

export default AdminQuizResultPage;
