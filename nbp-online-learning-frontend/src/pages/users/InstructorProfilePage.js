import React, { useEffect } from "react";
import { Table } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import Image from "react-bootstrap/Image";
import { fetchCategories } from "../../actions/categoriesActions";
import { fetchQuizzes } from "../../actions/quizzesActions";
import SidebarUser from "../../components/SidebarInstructor";
import "./UserProfilePage.css";
import SidebarInstructor from "../../components/SidebarInstructor";
const InstructorProfilePage = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const loginReducer = useSelector((state) => state.loginReducer);
    const user = loginReducer.user;
    const token = JSON.parse(localStorage.getItem("jwtToken"));

//useEffect(() => {
//  fetchCategories(dispatch, token);
//}, [dispatch]);
//
//useEffect(() => {
//  fetchQuizzes(dispatch, token);
//}, [dispatch]);
//
    useEffect(() => {
        if (!localStorage.getItem("jwtToken")) navigate("/");
    }, []);

    return (
        <div className="userProfilePage__container">
            <div className="userProfilePage__sidebar">
                <SidebarInstructor />
            </div>
            {user && (
                <div className="userProfilePage__content">
                    <Image
                        className="userProfilePage__content--profilePic"
                        width="20%"
                        height="20%"
                        roundedCircle
                        src="images/user.png"
                    />

                    <Table bordered style= {{background:"rgb(103, 147, 222)"}} className="userProfilePage__content--table">
                        <tbody>
                        <tr>
                            <td>Name</td>
                            <td>{`${user.firstName} ${user.lastName}`}</td>
                        </tr>
                        <tr>
                            <td>Username</td>
                            <td>{user.username}</td>
                        </tr>
                        <tr>
                            <td>Phone</td>
                            <td>{user.phoneNumber}</td>
                        </tr>
                        <tr>
                            <td>Role</td>
                            <td>{user.role}</td>
                        </tr>
                        <tr>
                            <td>Account Status</td>
                            <td>{`${user.enabled}`}</td>
                        </tr>
                        </tbody>
                    </Table>
                </div>
            )}
        </div>
    );
};

export default InstructorProfilePage;
