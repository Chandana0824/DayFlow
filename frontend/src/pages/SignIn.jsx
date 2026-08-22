import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import AuthLayout from '../components/AuthLayout.jsx'
import FormField from '../components/FormField.jsx'
import { signIn } from '../api/authApi.js'
import { validateEmail, validateSignInPassword } from '../utils/validators.js'

const initialForm = { email: '', password: '' }

export default function SignIn() {
  const navigate = useNavigate()
  const [form, setForm] = useState(initialForm)
  const [errors, setErrors] = useState({})
  const [showPassword, setShowPassword] = useState(false)
  const [submitting, setSubmitting] = useState(false)
  const [serverError, setServerError] = useState('')

  function update(field, value) {
    setForm((f) => ({ ...f, [field]: value }))
    setErrors((e) => ({ ...e, [field]: '' }))
  }

  function validateAll() {
    const next = {
      email: validateEmail(form.email),
      password: validateSignInPassword(form.password)
    }
    setErrors(next)
    return Object.values(next).every((v) => !v)
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setServerError('')
    if (!validateAll()) return

    setSubmitting(true)
    try {
      const res = await signIn(form)
      // Store the JWT for subsequent authenticated requests (dashboard, etc.)
      localStorage.setItem('dayflow_token', res.token)
      localStorage.setItem('dayflow_role', res.role)
      localStorage.setItem('dayflow_employee_id', res.employeeId)

      // The Employee/Admin dashboards are separate modules built by teammates —
      // route by role once those routes exist. For now this is the integration seam.
      navigate(res.role === 'HR' ? '/admin/dashboard' : '/dashboard')
    } catch (err) {
      setServerError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <AuthLayout>
      <p className="auth-form-eyebrow">Welcome back</p>
      <h2>Sign in to Dayflow</h2>
      <p className="auth-form-sub">
        New here? <Link to="/signup">Create an account</Link>
      </p>

      {serverError && <div className="banner banner-error" style={{ marginTop: 16 }}>{serverError}</div>}

      <form className="auth-form" onSubmit={handleSubmit} noValidate>
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

        <FormField label="Password" id="password" error={errors.password}>
          <div className="password-row">
            <input
              id="password"
              name="password"
              type={showPassword ? 'text' : 'password'}
              placeholder="••••••••"
              value={form.password}
              onChange={(e) => update('password', e.target.value)}
              autoComplete="current-password"
            />
            <button
              type="button"
              className="password-toggle"
              onClick={() => setShowPassword((s) => !s)}
            >
              {showPassword ? 'HIDE' : 'SHOW'}
            </button>
          </div>
        </FormField>

        <button className="btn-primary" type="submit" disabled={submitting}>
          {submitting ? 'Signing in…' : 'Sign in'}
        </button>
      </form>
    </AuthLayout>
  )
}
