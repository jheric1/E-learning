import React, { useEffect, useState } from "react";
import "./Sidebar.css";
import { FaBars, FaUserAlt } from "react-icons/fa";
import { MdQuiz } from "react-icons/md";
import { NavLink } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import {fetchCategories, fetchInstructorsCategories} from "../actions/categoriesActions";
import { TbLayoutGrid, TbReport } from "react-icons/tb";
import { useNavigate } from "react-router-dom";


const SidebarInstructor = ({ children }) => {
    const categoriesReducer = useSelector((state) => state.categoriesReducer);
    const [categories, setCategories] = useState(categoriesReducer.categories);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const token = JSON.parse(localStorage.getItem("jwtToken"));
    const [menuItems, setMenuItems] = useState([
        {
            path: "/instructorProfile",
            name: "Profile",
            icon: <FaUserAlt />,
        },

    ]);

    const [isOpen, setIsOpen] = useState(false);
    const toggle = () => setIsOpen(!isOpen);

    useEffect(() => {
        if (!localStorage.getItem("jwtToken")) navigate("/");
      }, []);

    useEffect(() => {
        fetchInstructorsCategories(dispatch, token).then((data) => {
            const tempCategories = data.payload;
            setCategories(tempCategories);

            const newMenuItems = tempCategories.map((c) => {
                return {
                    path: `/instructor/course/${c.id}`,
                    name: c.title,
                    icon: <TbLayoutGrid />,
                };
            });
            setMenuItems([...menuItems, ...newMenuItems]);
        });
    }, []);

    return (
        <div
            className="container"
            style={{ display: "flex", width: "auto", margin: "0px", padding: "0px" }}
        >
            <div style={{ width: isOpen ? "12em" : "3em" }} className="sidebar">
                <div className="top_section">
                    {/* <h1 style={{ display: isOpen ? "block" : "none" }} className="logo">
            Logo
          </h1> */}
                    <div style={{ marginLeft: isOpen ? "50px" : "0px" }} className="bars">
                        <FaBars onClick={toggle} />
                    </div>
                </div>
                {menuItems.map((item, index) => (
                    <NavLink
                        to={item.path}
                        key={index}
                        className="sidemenulink"
                        activeclassname="sidemenulink-active"
                    >
                        <div className="icon">{item.icon}</div>
                        <div
                            style={{ display: isOpen ? "block" : "none" }}
                            className="link_text"
                        >
                            {item.name}
                        </div>
                    </NavLink>
                ))}
            </div>
            <main>{children}</main>
        </div>
    );
};

export default SidebarInstructor;
