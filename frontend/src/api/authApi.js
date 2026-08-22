// Thin fetch wrapper around the Dayflow auth API.
// Uses the Vite dev proxy (see vite.config.js) so calls to /api/* reach
// the Spring Boot backend on :8080 without hardcoding a host — this keeps
// the same code working in dev and once it's deployed behind one origin.

const BASE = '/api/auth'

async function request(path, options) {
  const res = await fetch(`${BASE}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options
  })

  const data = await res.json().catch(() => ({}))

  if (!res.ok) {
    const message = data?.message || 'Something went wrong. Please try again.'
    const fieldErrors = data?.errors || null
    const err = new Error(message)
    err.fieldErrors = fieldErrors
    err.status = res.status
    throw err
  }

  return data
}

export function signUp(payload) {
  return request('/signup', { method: 'POST', body: JSON.stringify(payload) })
}

export function signIn(payload) {
  return request('/signin', { method: 'POST', body: JSON.stringify(payload) })
}

export function verifyEmail(token) {
  return request(`/verify?token=${encodeURIComponent(token)}`, { method: 'GET' })
}
