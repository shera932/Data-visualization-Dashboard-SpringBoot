// Login.js
import React, { useState } from "react";
import { Link, useNavigate } from 'react-router-dom';
import axios from "axios";
import './Auth.css'; // Add this import for shared styles
import backgroundImage from './image.png'; // Import the background image

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  async function login(event) {
    event.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/login", {
        email: email,
        password: password,
      });

      const res = response.data;
      console.log(res);

      if (res.status === true) {
        // Login success
        navigate('/dashboard');
      } else if (res.message === "Email not exists") {
        // Email does not exist
        alert("Email does not exist");
      } else {
        // Incorrect email or password
        alert("Incorrect Email and Password");
      }
    } catch (err) {
      console.error(err);
      alert("An error occurred. Please try again.");
    }
  }

  return (
    <div className="auth-container" style={{ backgroundImage: `url(${backgroundImage})` }}>
      <div className="auth-card">
        <h2 className="auth-title">Login</h2>
        <form>
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              className="form-control"
              placeholder="Enter Email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
            />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter Password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
            />
          </div>
          <button type="submit" className="btn btn-primary" onClick={login}>
            Login
          </button>
          <p className="auth-link">
            New user? <Link to="/register">Sign up here</Link>
          </p>
        </form>
      </div>
    </div>
  );
}

export default Login;
