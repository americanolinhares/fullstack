import { Component } from "react";
import { Formik, Field, Form } from "formik";

import AuthService from "../services/auth.service";
import { Navigate } from "react-router-dom";

type Props = {};

type State = {
  username: string,
  password: string,
  successful: boolean,
  message: string,
  redirect: boolean
};

export default class Register extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handleRegister = this.handleRegister.bind(this);

    this.state = {
      username: "",
      password: "",
      successful: false,
      message: "",
      redirect: false
    };
  }

  handleRegister(formValue: { username: string; password: string }) {
    const { username,  password } = formValue;

    this.setState({
      message: "",
      successful: false
    });

    AuthService.register(
      username,
      password
    ).then(
      response => {
        this.setState({
          message: response.data.message,
          successful: true,
          redirect: true
        });
      },
      error => {
        const resMessage =
          (error.response.data.message && error.response.data && error.response) ||
          error.message ||
          error.toString();

        this.setState({
          successful: false,
          message: resMessage
        });
      }
    );
  }

  render() {
    const { successful, message, redirect } = this.state;

    if (redirect) {
      return <Navigate to="/login" />;
    }

    const initialValues = {
      username: "",
      password: "",
    };

    return (
        <div>
          <header className="jumbotron">
            <h3 className="text-center">Register Page</h3>
          </header>
          <div className="col-md-12">
            <div className="card card-container">
              <img
                  src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                  alt="profile-img"
                  className="profile-img-card"
              />

              <Formik
                  initialValues={initialValues}
                  onSubmit={this.handleRegister}
              >
                <Form>
                  {!successful && (
                      <div>
                        <div className="form-group">
                          <label htmlFor="username"> Username </label>
                          <Field name="username" type="text" className="form-control"/>
                        </div>

                        <div className="form-group">
                          <label htmlFor="password"> Password </label>
                          <Field
                              name="password"
                              type="password"
                              className="form-control"
                          />
                        </div>

                        <div className="form-group">
                          <button type="submit" className="btn btn-primary btn-block">Register</button>
                        </div>
                      </div>
                  )}

                  {message && (
                      <div className="form-group">
                        <div
                            className={
                              successful ? "alert alert-success" : "alert alert-danger"
                            }
                            role="alert"
                        >
                          {message}
                        </div>
                      </div>
                  )}
                </Form>
              </Formik>
            </div>
          </div>
        </div>
    );
  }
}
