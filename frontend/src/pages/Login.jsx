import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Login = () => {
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!formData.email || !formData.password) {
      setError('Please fill in all fields.');
      return;
    }

    // TODO: Replace with actual backend API call
    console.log('Logging in with:', formData);
    
    // Mocking a login failure to demonstrate error handling
    if (formData.email !== 'admin@dayflow.com') {
       setError('Incorrect credentials. Please try again.');
       return;
    }

    // On success, redirect to dashboard
    // navigate('/dashboard');
    alert("Login successful! Redirecting to dashboard...");
  };

  return (
    <div className="auth-container">
      <div className="auth-header">
        <h1>Dayflow</h1>
        <p>Every workday, perfectly aligned.</p>
      </div>
      
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Email Address</label>
          <input
            type="email"
            name="email"
            className="form-control"
            placeholder="Enter your email"
            value={formData.email}
            onChange={handleChange}
          />
        </div>
        
        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            name="password"
            className="form-control"
            placeholder="Enter your password"
            value={formData.password}
            onChange={handleChange}
          />
        </div>

        {error && <div className="error-message">{error}</div>}

        <button type="submit" className="btn-primary">
          Sign In
        </button>
      </form>

      <div className="auth-footer">
        <p>Don't have an account? <Link to="/signup">Sign Up</Link></p>
      </div>
    </div>
  );
};

export default Login;
