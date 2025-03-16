import React from "react";
import { Navbar, Nav, Container } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux"; // Dodaj useDispatch
import { LinkContainer } from "react-router-bootstrap";
import { useNavigate } from "react-router-dom";

const Header = () => {
  const navigate = useNavigate();
  const isLoggedIn = useSelector(state => state.loginReducer.loggedIn); // Promijenjeno
  const user = useSelector(state => state.loginReducer.user); // Dodano
  const dispatch = useDispatch(); // Dodano

  const logoutHandler = () => {
    localStorage.clear();
    navigate("/login");
    window.location.reload();
  };

  return (
      <header>
        <Navbar bg="dark" variant="dark" expand="lg" collapseOnSelect>
          <Container>
            <Navbar.Brand>Exam-Portal</Navbar.Brand>
            <Navbar.Toggle aria-controls="responsive-navbar-nav" />
            <Navbar.Collapse id="responsive-navbar-nav">
              <Nav className="justify-content-end flex-grow-1 pe-3">
                {isLoggedIn ? (
                    <Nav.Link>{user.firstName}</Nav.Link> // Prikazi ime korisnika
                ) : (
                    <LinkContainer to="/">
                      <Nav.Link>Login</Nav.Link>
                    </LinkContainer>
                )}
                {isLoggedIn ? (
                    <LinkContainer to="/">
                      <Nav.Link onClick={logoutHandler}>Logout</Nav.Link>
                    </LinkContainer>
                ) : (
                    <LinkContainer to="/register">
                      <Nav.Link>Register</Nav.Link>
                    </LinkContainer>
                )}
              </Nav>
            </Navbar.Collapse>
          </Container>
        </Navbar>
      </header>
  );
};

export default Header;
