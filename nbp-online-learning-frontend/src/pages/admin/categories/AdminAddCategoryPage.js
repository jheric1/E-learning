import React, { useState, useEffect } from "react";
import "./AdminAddCategoryPage.css";
import { Button, Form } from "react-bootstrap";
import { useDispatch } from "react-redux";
import * as categoriesConstants from "../../../constants/categoriesConstants";
import FormContainer from "../../../components/FormContainer";
import Sidebar from "../../../components/Sidebar";
import {
  addCategory,
  fetchCategories,
} from "../../../actions/categoriesActions";
import swal from "sweetalert";
import { useNavigate } from "react-router-dom";

const AdminAddCategoryPage = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [selectedProfessor, setSelectedProfessor] = useState("");
  const [professors, setProfessors] = useState([]); 
 
  const token = JSON.parse(localStorage.getItem("jwtToken"));

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const submitHandler = (e) => {
    e.preventDefault();
    const category = { title: title, description: description, startDate: startDate, endDate: endDate, instructorId: selectedProfessor};
    addCategory(dispatch, category, token).then((data) => {
      if (data.type === categoriesConstants.ADD_CATEGORY_SUCCESS) {
        swal("Course Added!", `${title} succesfully added`, "success");
      } else {
        swal("Course Not Added!", `${title} not added`, "error");
      }
      // navigate("/adminCategories");
    });
  };
  useEffect(() => {
    const fetchProfessors = async () => {
      try{
        const response = await fetch("http://localhost:8008/api/users/instructors", {
          headers: {
            Authorization: `Bearer ${token}`, // Dodajte ovu liniju kako biste poslali token
            "Content-Type": "application/json", // Ovisno o potrebi, možda trebate dodati i Content-Type
          },
        });
        if (!response.ok) {
          throw new Error("Failed to fetch instructors");
        }
        const userData = await response.json();
        setProfessors(userData);
      }catch (error) {
        console.error("Error fetching instructors:", error);}

    };
  
    // Call the fetchProfessors function
    fetchProfessors();
  }, []);

  return (
    <div className="adminAddCategoryPage__container">
      <div className="adminAddCategoryPage__sidebar">
        <Sidebar />
      </div>
      <div className="adminAddCategoryPage__content">
        <FormContainer>
          <h2>Add Course</h2>
          <Form onSubmit={submitHandler}>
            <Form.Group className="my-3" controlId="title">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Course Title"
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
                rows="5"
                type="text"
                placeholder="Enter Course Description"
                value={description}
                onChange={(e) => {
                  setDescription(e.target.value);
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
            <Form.Group className="my-3" controlId="professor">
              <Form.Label>Professor</Form.Label>
              <Form.Select
                value={selectedProfessor}
                onChange={(e) => setSelectedProfessor(e.target.value)}
              >
                <option value="">Select Professor</option>
                {/* Map through professors and create options */}
                {professors.map((professor) => (
                  <option key={professor.id} value={professor.id}>
                    {professor.firstName }
                  </option>
                ))}
              </Form.Select>
            </Form.Group>

            <Button
              className="my-3 adminAddCategoryPage__content--button"
              type="submit"
              variant=""
            >
              Add
            </Button>
          </Form>
        </FormContainer>
      </div>
    </div>
  );
};

export default AdminAddCategoryPage;
