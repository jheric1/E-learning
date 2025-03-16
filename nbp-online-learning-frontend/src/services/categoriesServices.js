import axios from "axios";
axios.defaults.baseURL = "http://localhost:8008"
const fetchCategories = async (token) => {
  try {
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const { data } = await axios.get("/api/courses/all", config);
    console.log("categoryService:fetchCategories() Success: ", data);
    return data;
  } catch (error) {
    console.error(
        "categoryService:fetchCategories() Error: ",
        error.response.statusText
    );
    return error.response.statusText;
  }
};
const fetchStudentCategories = async (token) => {
  try {
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const { data } = await axios.get("/api/courses/my-courses", config);
    console.log("categoryService:fetchCategories() Success: ", data);
    return data;
  } catch (error) {
    console.error(
        "categoryService:fetchCategories() Error: ",
        error.response.statusText
    );
    return error.response.statusText;
  }
};

const fetchInstructorsCategories = async (token) => {
  try {
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const { data } = await axios.get(`/api/courses/instructor`, config);
    console.log("categoryService:fetchCategories() Success: ", data);

    return data;
  } catch (error) {
    console.error(
        "categoryService:fetchCategories() Error: ",
        error.response.statusText
    );
    return error.response.statusText;
  }
};

const addCategory = async (category, token) => {
  try {
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const { data } = await axios.post("/api/courses/create-course", category, config);
    console.log("categoryService:addCategory() Success: ", data);
    return { data: data, isAdded: true, error: null };
  } catch (error) {
    console.error(
        "categoryService:addCategory() Error: ",
        error.response.statusText
    );
    return { data: null, isAdded: false, error: error.response.statusText };
  }
};
const addAssignment = async (assignment, token) => {
  try {
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const { data } = await axios.post("/api/homework/create-homework", assignment, config);
    console.log("categoryService:addAssignment() Success: ", data);
    return { data: data, isAdded: true, error: null };
  } catch (error) {
    console.error(
        "categoryService:addAssignment() Error: ",
        error.response.statusText
    );
    return { data: null, isAdded: false, error: error.response.statusText };
  }
};
const addLesson = async (lesson, token) => {
  try {
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const { data } = await axios.post("/api/lesson/create-lesson", lesson, config);
    console.log("categoryService:addAssignment() Success: ", data);
    return { data: data, isAdded: true, error: null };
  } catch (error) {
    console.error(
        "categoryService:addAssignment() Error: ",
        error.response.statusText
    );
    return { data: null, isAdded: false, error: error.response.statusText };
  }
};

const deleteCategory = async (courseId, token) => {
  try {
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const { data } = await axios.delete(`api/courses/delete-course/${courseId}`, config);
    console.log("categoryService:deleteCategory()  Success: ", data);
    return {
      isDeleted: true,
      error: null,
    };
  } catch (error) {
    console.error(
        "categoryService:deleteCategory()  Error: ",
        error.response.statusText
    );
    return {
      isDeleted: false,
      error: error.response.statusText,
    };
  }
  
};


const updateCategory = async (category, token) => {
  try {
    const config = {
      headers: { Authorization: `Bearer ${token}` },
    };
    const { data } = await axios.put(
        `/api/category/${category.catId}/`,
        category,
        config
    );
    console.log("categoryService:updateCategory()  Success: ", data);
    return {
      data: data,
      isUpdated: true,
      error: null,
    };
  } catch (error) {
    console.error(
        "categoryService:updateCategory()  Error: ",
        error.response.statusText
    );
    return {
      data: null,
      isUpdated: false,
      error: error.response.statusText,
    };
  }
};
// actions/coursesActions.js


const fetchFreeCourses = async (token) => {
  const response = await fetch("/api/courses/free-courses", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  if (response.ok) {
    return await response.json();
  } else {
    throw new Error("Failed to fetch free courses");
  }
};

const enrollCourse = async (courseId, token) => {
  const response = await fetch(`api/courses/apply-to-course/${courseId}`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  if (response.ok) {
    return { isEnrolled: true };
  } else {
    throw new Error("Failed to enroll in course");
  }
};


const categoriesService = {
  addCategory,
  addAssignment,
  addLesson,
  fetchCategories,
  fetchStudentCategories,
  updateCategory,
  deleteCategory,
  fetchInstructorsCategories,
  fetchFreeCourses,
  enrollCourse
};
export default categoriesService;