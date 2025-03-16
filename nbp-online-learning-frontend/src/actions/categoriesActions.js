import * as categoriesConstants from "../constants/categoriesConstants";
import categoriesServices from "../services/categoriesServices";

export const addCategory = async (dispatch, category, token) => {
  dispatch({ type: categoriesConstants.ADD_CATEGORY_REQUEST });
  const { data, isAdded, error } = await categoriesServices.addCategory(
      category,
      token
  );
  if (isAdded) {
    return dispatch({
      type: categoriesConstants.ADD_CATEGORY_SUCCESS,
      payload: data,
    });
  } else {
    return dispatch({
      type: categoriesConstants.ADD_CATEGORY_FAILURE,
      payload: error,
    });
  }
};
export const addAssignment = async (dispatch, assignment, token) => {
  dispatch({ type: categoriesConstants.ADD_ASSIGNMENT_REQUEST });
  const { data, isAdded, error } = await categoriesServices.addAssignment(
      assignment,
      token
  );
  if (isAdded) {
    return dispatch({
      type: categoriesConstants.ADD_ASSIGNMENT_SUCCESS,
      payload: data,
    });
  } else {
    return dispatch({
      type: categoriesConstants.ADD_ASSIGNMENT_FAILURE,
      payload: error,
    });
  }
};
export const addLesson = async (dispatch, lesson, token) => {
  dispatch({ type: categoriesConstants.ADD_LESSON_REQUEST });
  const { data, isAdded, error } = await categoriesServices.addLesson(
      lesson,
      token
  );
  if (isAdded) {
    return dispatch({
      type: categoriesConstants.ADD_LESSON_SUCCESS,
      payload: data,
    });
  } else {
    return dispatch({
      type: categoriesConstants.ADD_LESSON_FAILURE,
      payload: error,
    });
  }
};

export const fetchCategories = async (dispatch, token) => {
  dispatch({ type: categoriesConstants.FETCH_CATEGORIES_REQUEST });
  const data = await categoriesServices.fetchCategories(token);
  if (data) {
    return dispatch({
      type: categoriesConstants.FETCH_CATEGORIES_SUCCESS,
      payload: data,
    });
  } else {
    return dispatch({
      type: categoriesConstants.FETCH_CATEGORIES_FAILURE,
      payload: data,
    });
  }
};
export const fetchStudentCategories = async (dispatch, token) => {
  dispatch({ type: categoriesConstants.FETCH_CATEGORIES_REQUEST });
  const data = await categoriesServices.fetchStudentCategories(token);
  if (data) {
    return dispatch({
      type: categoriesConstants.FETCH_CATEGORIES_SUCCESS,
      payload: data,
    });
  } else {
    return dispatch({
      type: categoriesConstants.FETCH_CATEGORIES_FAILURE,
      payload: data,
    });
  }
};

export const fetchInstructorsCategories = async (dispatch, token) => {
  dispatch({ type: categoriesConstants.FETCH_CATEGORIES_REQUEST });
  const data = await categoriesServices.fetchInstructorsCategories(token);
  if (data) {
    return dispatch({
      type: categoriesConstants.FETCH_CATEGORIES_SUCCESS,
      payload: data,
    });
  } else {
    return dispatch({
      type: categoriesConstants.FETCH_CATEGORIES_FAILURE,
      payload: data,
    });
  }
};

export const updateCategory = async (dispatch, category, token) => {
  dispatch({ type: categoriesConstants.UPDATE_CATEGORY_REQUEST });
  const { data, isUpdated, error } = await categoriesServices.updateCategory(
      category,
      token
  );
  if (isUpdated) {
    return dispatch({
      type: categoriesConstants.UPDATE_CATEGORY_SUCCESS,
      payload: data,
    });
  } else {
    return dispatch({
      type: categoriesConstants.UPDATE_CATEGORY_FAILURE,
      payload: error,
    });
  }
};

export const deleteCategory = async (dispatch, catId, token) => {
  dispatch({ type: categoriesConstants.DELETE_CATEGORY_REQUEST });
  const { isDeleted, error } = await categoriesServices.deleteCategory(
      catId,
      token
  );
  if (isDeleted) {
    return dispatch({
      type: categoriesConstants.DELETE_CATEGORY_SUCCESS,
      payload: catId,
    });
  } else {
    return dispatch({
      type: categoriesConstants.DELETE_CATEGORY_FAILURE,
      payload: error,
    });
  }
};
// actions/coursesActions.js

// actions/coursesActions.js

export const fetchFreeCourses = async (dispatch, token) => {
  dispatch({ type: categoriesConstants.FETCH_FREE_COURSES_REQUEST });
  try {
    const data = await categoriesServices.fetchFreeCourses(token);
    dispatch({
      type: categoriesConstants.FETCH_FREE_COURSES_SUCCESS,
      payload: data,
    });
  } catch (error) {
    dispatch({
      type: categoriesConstants.FETCH_FREE_COURSES_FAILURE,
      payload: error.message,
    });
  }
};

export const enrollCourse = async (dispatch, courseId, token) => {
  dispatch({ type: categoriesConstants.ENROLL_COURSE_REQUEST });
  try {
    const { isEnrolled } = await categoriesServices.enrollCourse(courseId, token);
    if (isEnrolled) {
      dispatch({
        type: categoriesConstants.ENROLL_COURSE_SUCCESS,
        payload: courseId,
      });
    } else {
      throw new Error("Enroll failed");
    }
  } catch (error) {
    dispatch({
      type: categoriesConstants.ENROLL_COURSE_FAILURE,
      payload: error.message,
    });
  }
};