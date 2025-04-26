import { Component } from "react";
import { Navigate } from "react-router-dom";
import { Formik, Field, Form } from "formik";

import AuthService from "../services/auth.service";

type Props = {};

type State = {
  redirect: string | null,
  username: string,
  password: string,
  errorMessage?: string
};

export default class Login extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handleLogin = this.handleLogin.bind(this);

    this.state = {
      redirect: null,
      username: "",
      password: "",
      errorMessage: "",
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (currentUser) {
      this.setState({ redirect: "/dashboard" });
    }
  }

  componentWillUnmount() {
    window.location.reload();
  }


  handleLogin(formValue: { username: string; password: string }) {
    const { username, password } = formValue;

    AuthService.login(username, password).then(
      () => {
        this.setState({
          redirect: "/dashboard"
        });
      },
        (error) => {
          if (error.response && error.response.status === 401) {
            this.setState({ errorMessage: "Invalid credentials. Please try again." });
          } else {
          const errors = error.response?.data ?? [];
          const errorMessage = errors.map((err: { message: string }) => err.message).join(" ");
          this.setState({ errorMessage });
          }
        }
    );
  }

    clearErrorMessage = () => {
        this.setState({ errorMessage: "" });
    };

  render() {
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />
    }

    const initialValues = {
      username: "",
      password: "",
    };

    return (
        <div>
          <header className="jumbotron">
            <h3 className="text-center">Login Page</h3>
          </header>
          <div className="col-md-12">
            <div className="card card-container">
              <img
                  src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                  alt="profile-img"
                  className="profile-img-card"
              />
              {this.state.errorMessage && (
                  <div className="alert alert-danger alert-dismissible" role="alert">
                    {this.state.errorMessage}
                    <button
                        type="button"
                        className="btn-close"
                        aria-label="Close"
                        onClick={this.clearErrorMessage}
                    ></button>
                  </div>
              )}

              <Formik
                  initialValues={initialValues}
                  onSubmit={this.handleLogin}
              >
                <Form>
                  <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <Field name="username" type="text" className="form-control"/>
                  </div>

                  <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <Field name="password" type="password" className="form-control"/>
                  </div>

                  <div className="form-group">
                    <button type="submit" className="btn btn-primary btn-block">
                      <span>Login</span>
                    </button>
                  </div>
                </Form>
              </Formik>
            </div>
          </div>
        </div>
    );
  }
}
