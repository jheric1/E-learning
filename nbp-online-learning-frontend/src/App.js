import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import Header from "./components/Header";
import AdminAddCategoryPage from "./pages/admin/categories/AdminAddCategoryPage";
import AdminCategoriesPage from "./pages/admin/categories/AdminCategoriesPage";
import AdminUpdateCategoryPage from "./pages/admin/categories/AdminUpdateCategoryPage";
import AdminProfilePage from "./pages/admin/AdminProfilePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import AdminQuizzesPage from "./pages/admin/quizzes/AdminQuizzesPage";
import AdminAddQuiz from "./pages/admin/quizzes/AdminAddQuiz";
import AdminUpdateQuiz from "./pages/admin/quizzes/AdminUpdateQuiz";
import AdminQuestionsPage from "./pages/admin/questions/AdminQuestionsPage";
import InstructorAddQuestionsPage from "./pages/admin/questions/InstructorAddQuestionsPage";
import AdminUpdateQuestionPage from "./pages/admin/questions/AdminUpdateQuestionPage";
import UserProfilePage from "./pages/users/UserProfilePage";
import UserQuizzesPage from "./pages/users/UserQuizzesPage";
import UserQuizManualPage from "./pages/users/UserQuizManualPage";
import UserQuestionsPage from "./pages/users/UserQuestionsPage";
import UserQuizResultPage from "./pages/users/UserQuizResultPage";
import AdminQuizResultPage from "./pages/admin/AdminQuizResultPage";
import UserCategoryPage from "./pages/users/UserCategoryPage";
import InstructorCategoryPage from "./pages/users/InstructorCategoryPage";
import InstructorAddAssignment from "./pages/users/InstuctorAddAssignment";
import InstructorAddLesson from "./pages/users/InstructorAddLesson";
import InstructorQuizzesPage from "./pages/users/InstructorQuizzesPage";
import InstructorProfilePage from "./pages/users/InstructorProfilePage";
import FreeCoursesPage from "./pages/users/FreeCoursesPage";
import InstructorAssignmentPage from "./pages/users/InstructorAssignmentPage";
import UserSubmitHomeworkPage from "./pages/users/UserSubmitHomeworkPage";
import UserQuizzPage from "./pages/users/UserQuizzPage";
import InstructorQuizResultPage from "./pages/users/InstructorQuizResultPage";
const App = () => {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/adminProfile" element={<AdminProfilePage />} />
        <Route path="/adminCategories" element={<AdminCategoriesPage />} />
        <Route path="/adminAddCategory" element={<AdminAddCategoryPage />} />
        <Route
          path="/adminUpdateCategory/:catId"
          element={<AdminUpdateCategoryPage />}
        />
        <Route path="/adminQuizzes" element={<AdminQuizzesPage />} />

        <Route path="/adminUpdateQuiz/:quizId" element={<AdminUpdateQuiz />} />
        <Route path="/adminQuestions" element={<AdminQuestionsPage />} />
        <Route path="/manageQuiz/:id" element={<InstructorAddQuestionsPage />} />
        <Route path="/adminallResult" element={<AdminQuizResultPage />} />
        <Route path="/quiz/result/:id" element={<InstructorQuizResultPage/>}/>
        <Route
          path="/adminUpdateQuestion/:quesId"
          element={<AdminUpdateQuestionPage />}
        />
        <Route path="/profile" element={<UserProfilePage />} />
        <Route path="/quizzes" element={<UserQuizzesPage />} />
        <Route path="/quiz/*" element={<UserQuizzesPage />} />
        <Route path="/quizManual/" element={<UserQuizManualPage />} />
        <Route path="/questions/" element={<UserQuestionsPage />} />
        <Route path="/quizResults/" element={<UserQuizResultPage />} />
        <Route path="/course/:id" element={<UserCategoryPage/>}/>
        <Route path="/homework/:id" element={<UserSubmitHomeworkPage/>}/>
        <Route path="/instructorAddQuiz" element={<AdminAddQuiz />} />
        <Route path="/quizz/:id" element={<UserQuizzPage/>}/>

        <Route path="/instructor/course/:id" element={<InstructorCategoryPage/>}/>
        <Route path="/instructor/add-assignment/:id" element={<InstructorAddAssignment/>}/>
        <Route path="/instructor/add-lesson/:id" element={<InstructorAddLesson/>}/>
        <Route path="/instructor/quizzes" element={<InstructorQuizzesPage/>}/>
        <Route path="/instructorProfile" element={<InstructorProfilePage />} />
        <Route path="/enroll-course" element={<FreeCoursesPage />} />
        <Route path="/instructor/assignment/:id" element={<InstructorAssignmentPage />} />
      </Routes>
    </Router>
  );
};

export default App;
