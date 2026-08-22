import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import AuthLayout from '../components/AuthLayout.jsx'
import FormField from '../components/FormField.jsx'
import PasswordStrength from '../components/PasswordStrength.jsx'
import { signUp } from '../api/authApi.js'
import {
  validateEmployeeId,
  validateEmail,
  validateSignUpPassword
} from '../utils/validators.js'

const initialForm = { employeeId: '', email: '', password: '', role: 'EMPLOYEE' }

export default function SignUp() {
  const navigate = useNavigate()
  const [form, setForm] = useState(initialForm)
  const [errors, setErrors] = useState({})
  const [showPassword, setShowPassword] = useState(false)
  const [submitting, setSubmitting] = useState(false)
  const [serverError, setServerError] = useState('')
  const [successMessage, setSuccessMessage] = useState('')

  function update(field, value) {
    setForm((f) => ({ ...f, [field]: value }))
    setErrors((e) => ({ ...e, [field]: '' }))
  }

  function validateAll() {
    const next = {
      employeeId: validateEmployeeId(form.employeeId),
      email: validateEmail(form.email),
      password: validateSignUpPassword(form.password)
    }
    setErrors(next)
    return Object.values(next).every((v) => !v)
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setServerError('')
    setSuccessMessage('')
    if (!validateAll()) return

    setSubmitting(true)
    try {
      const res = await signUp(form)
      setSuccessMessage(res.message || 'Account created. Check your email to verify.')
      setForm(initialForm)
      setTimeout(() => navigate('/signin'), 2500)
    } catch (err) {
      if (err.fieldErrors) setErrors((e) => ({ ...e, ...err.fieldErrors }))
      setServerError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <AuthLayout>
      <p className="auth-form-eyebrow">Create account</p>
      <h2>Join your workspace</h2>
      <p className="auth-form-sub">
        Already have an account? <Link to="/signin">Sign in</Link>
      </p>

      {serverError && <div className="banner banner-error" style={{ marginTop: 16 }}>{serverError}</div>}
      {successMessage && <div className="banner banner-success" style={{ marginTop: 16 }}>{successMessage}</div>}

      <form className="auth-form" onSubmit={handleSubmit} noValidate>
        <FormField
          label="Employee ID"
          id="employeeId"
          error={errors.employeeId}
          hint="Assigned by HR, e.g. EMP-2049"
        >
          <input
            id="employeeId"
            name="employeeId"
            type="text"
            placeholder="EMP-2049"
            value={form.employeeId}
            onChange={(e) => update('employeeId', e.target.value)}
            autoComplete="off"
          />
        </FormField>

        <FormField label="Work email" id="email" error={errors.email}>
          <input
            id="email"
            name="email"
            type="email"
            placeholder="you@company.com"
            value={form.email}
            onChange={(e) => update('email', e.target.value)}
            autoComplete="email"
          />
        </FormField>

        <FormField
          label="Password"
          id="password"
          error={errors.password}
          hint={!errors.password ? '8+ characters, with uppercase, number & symbol' : undefined}
        >
          <div className="password-row">
            <input
              id="password"
              name="password"
              type={showPassword ? 'text' : 'password'}
              placeholder="••••••••"
              value={form.password}
              onChange={(e) => update('password', e.target.value)}
              autoComplete="new-password"
            />
            <button
              type="button"
              className="password-toggle"
              onClick={() => setShowPassword((s) => !s)}
            >
              {showPassword ? 'HIDE' : 'SHOW'}
            </button>
          </div>
          <PasswordStrength password={form.password} />
        </FormField>

        <FormField label="Role" id="role">
          <div className="role-toggle" role="radiogroup" aria-label="Role">
            {['EMPLOYEE', 'HR'].map((r) => (
              <button
                type="button"
                key={r}
                className={form.role === r ? 'active' : ''}
                role="radio"
                aria-checked={form.role === r}
                onClick={() => update('role', r)}
              >
                {r === 'HR' ? 'HR Officer' : 'Employee'}
              </button>
            ))}
          </div>
        </FormField>

        <button className="btn-primary" type="submit" disabled={submitting}>
          {submitting ? 'Creating account…' : 'Create account'}
        </button>
      </form>
    </AuthLayout>
  )
}
