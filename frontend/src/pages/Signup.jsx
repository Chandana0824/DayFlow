import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Signup = () => {
  const [formData, setFormData] = useState({ 
    employeeId: '', 
    email: '', 
    password: '', 
    role: 'EMPLOYEE' 
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!formData.employeeId || !formData.email || !formData.password) {
      setError('Please fill in all mandatory fields.');
      return;
    }

    // Basic password security rule validation
    if (formData.password.length < 8) {
      setError('Password must be at least 8 characters long.');
      return;
    }

    // TODO: Replace with actual backend API call to Spring Boot
    console.log('Registering user:', formData);
    
    alert("Registration successful! Please check your email for verification.");
    navigate('/login');
  };

  return (
    <div className="auth-container">
      <div className="auth-header">
        <h1>Join Dayflow</h1>
        <p>Your workday, perfectly aligned.</p>
      </div>
      
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Employee ID</label>
          <input
            type="text"
            name="employeeId"
            className="form-control"
            placeholder="e.g., EMP-1042"
            value={formData.employeeId}
            onChange={handleChange}
          />
        </div>

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
            placeholder="Create a strong password"
            value={formData.password}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Role</label>
          <select name="role" className="form-control form-select" value={formData.role} onChange={handleChange}>
            <option value="EMPLOYEE">Employee</option>
            <option value="HR">HR / Admin</option>
          </select>
        </div>

        {error && <div className="error-message">{error}</div>}

        <button type="submit" className="btn-primary">
          Sign Up
        </button>
      </form>

      <div className="auth-footer">
        <p>Already have an account? <Link to="/login">Sign In</Link></p>
      </div>
    </div>
  );
};

export default Signup;
