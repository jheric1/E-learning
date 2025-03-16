import React, { useEffect, useState } from "react";
import { Table, Button } from "react-bootstrap";
import SidebarUser from "../../components/SidebarUser";
import "./FreeCoursesPage.css";

const FreeCoursesPage = () => {
  const token = JSON.parse(localStorage.getItem("jwtToken")); 
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const response = await fetch("http://localhost:8008/api/courses/free-courses", {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });
        if (!response.ok) {
          throw new Error("Failed to fetch courses");
        }
        const coursesData = await response.json();
        setCategories(coursesData);
      } catch (error) {
        console.error("Error fetching courses:", error);
      }
    };

    fetchCourses();
  }, [token]);

  const enrollCourse = async (courseId) => {
    try {
      const response = await fetch(`http://localhost:8008/api/courses/apply-to-course/${courseId}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      if (!response.ok) {
        throw new Error("Failed to enroll in course");
      }
      // Update local state to reflect the enrollment
      const updatedCategories = categories.map((course) => {
        if (course.id === courseId) {
          return {
            ...course,
            enrolled: true, // Example assuming you have an enrolled field in your course object
          };
        }
        return course;
      });
      setCategories(updatedCategories);
      console.log("Enrolled successfully!");
    } catch (error) {
      console.error("Error enrolling in course:", error);
    }
  };

  return (
    <div className="freeCoursesPage__container">
      <SidebarUser />

      <div className="freeCoursesPage__content">
        <h1>Free Courses</h1>
        <Table bordered responsive className="freeCoursesPage__content--table">
          <thead>
            <tr>
              <th>Course Name</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {categories.map((course) => (
              <tr key={course.id}>
                <td>{course.title}</td>
                <td>
                <Button
                    variant="primary"
                    className="enroll-button"
                    onClick={() => enrollCourse(course.id)}
                    disabled={course.enrolled} // Disable button if already enrolled
                  >
                    {course.enrolled ? "Enrolled" : "Enroll Course"}
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
};

export default FreeCoursesPage;
