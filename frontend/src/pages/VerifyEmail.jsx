import { useEffect, useState } from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import { verifyEmail } from '../api/authApi.js'

export default function VerifyEmail() {
  const [params] = useSearchParams()
  const token = params.get('token')
  const [status, setStatus] = useState('loading') // loading | success | error
  const [message, setMessage] = useState('Verifying your email…')

  useEffect(() => {
    if (!token) {
      setStatus('error')
      setMessage('Missing verification token.')
      return
    }
    verifyEmail(token)
      .then((res) => {
        setStatus('success')
        setMessage(res.message || 'Email verified. You can now sign in.')
      })
      .catch((err) => {
        setStatus('error')
        setMessage(err.message)
      })
  }, [token])

  return (
    <div className="verify-shell">
      <div className="verify-card">
        <p className="auth-form-eyebrow">{status === 'success' ? 'Verified' : status === 'error' ? 'Verification failed' : 'One moment'}</p>
        <h2>{message}</h2>
        {status !== 'loading' && (
          <p>
            <Link to="/signin">Go to sign in →</Link>
          </p>
        )}
      </div>
    </div>
  )
}
