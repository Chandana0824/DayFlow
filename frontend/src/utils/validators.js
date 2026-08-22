// Client-side validation mirrors the backend's Bean Validation rules
// (see SignUpRequest.java / SignInRequest.java) so users get instant
// feedback, while the backend remains the source of truth.

export const EMPLOYEE_ID_REGEX = /^[A-Za-z0-9-]{3,20}$/
export const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
export const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!_-]).*$/

export function validateEmployeeId(value) {
  if (!value.trim()) return 'Employee ID is required'
  if (!EMPLOYEE_ID_REGEX.test(value)) return '3-20 letters, digits or hyphens'
  return ''
}

export function validateEmail(value) {
  if (!value.trim()) return 'Email is required'
  if (!EMAIL_REGEX.test(value)) return 'Enter a valid email address'
  return ''
}

export function validateSignUpPassword(value) {
  if (!value) return 'Password is required'
  if (value.length < 8) return 'At least 8 characters'
  if (!PASSWORD_REGEX.test(value)) return 'Add uppercase, lowercase, a number and a symbol'
  return ''
}

export function validateSignInPassword(value) {
  if (!value) return 'Password is required'
  return ''
}

export function passwordStrength(value) {
  let score = 0
  if (value.length >= 8) score++
  if (/[a-z]/.test(value) && /[A-Z]/.test(value)) score++
  if (/\d/.test(value)) score++
  if (/[@#$%^&+=!_-]/.test(value)) score++
  if (value.length >= 12) score++
  return Math.min(score, 4) // 0-4
}

export const STRENGTH_LABELS = ['Too weak', 'Weak', 'Fair', 'Good', 'Strong']
