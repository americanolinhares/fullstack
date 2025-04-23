import axios from "axios";

const API_URL = "http://localhost:8080/";

class AuthService {
  login(username: string, password: string) {
    return axios
      .post(API_URL + "login", {
        username,
        password
      })
      .then(response => {
        if (response.data.token) {
          localStorage.setItem("userAuth", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("userAuth");
  }

  register(username: string, password: string) {
    return axios.post(API_URL + "register", {
      username,
      password
    });
  }

  getCurrentUser() {
    const userStr = localStorage.getItem("userAuth");
    if (userStr) return JSON.parse(userStr);

    return null;
  }
}

export default new AuthService();
