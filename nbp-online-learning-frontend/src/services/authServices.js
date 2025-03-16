import axios from "axios";

axios.defaults.baseURL = "http://localhost:8008"
const register = async (user) => {
  try {
    const { data } = await axios.post("/api/users/create-user", user);
    if (data) {
      console.log(
          "authService:register() Success: ",
          user.email,
          " successfully registerd."
      );
      return { isRegistered: true, error: null };
    } else {
      console.error("authService:register() Error: ", data);
      return { isRegistered: false, error: data };
    }
  } catch (error) {
    console.error("authService:register() Error: ", error.response.statusText);
    return { isRegistered: false, error: error.response.statusText };
  }
};

const login = async (username, password) => {
  try {
    const { data } = await axios.post("/api/users/login", {
      email: username,
      password: password,
    });


    if (data && data.jwtToken.length) {
      console.log("DJe sam")
      localStorage.setItem("user", JSON.stringify(data.user));
      localStorage.setItem("jwtToken", JSON.stringify(data.jwtToken));
      return data;
    } else {
      console.error("authService:login() Error: ", data);
      return data;
    }
  } catch (error) {
    console.error("authService:login() Error: ", error.response.statusText);
    return error.response.statusText;
  }
};


const authServices = { register, login };
export default authServices;