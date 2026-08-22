import { passwordStrength, STRENGTH_LABELS } from '../utils/validators.js'

export default function PasswordStrength({ password }) {
  if (!password) return null
  const score = passwordStrength(password)
  const colors = ['#d64545', '#d98d1f', '#f2a93b', '#4f8f5b', '#1e9e6a']

  return (
    <div>
      <div className="strength-meter" aria-hidden="true">
        {[0, 1, 2, 3].map((i) => (
          <div
            key={i}
            className="strength-bar"
            style={{ background: i < score ? colors[score] : undefined }}
          />
        ))}
      </div>
      <div className="strength-label">{STRENGTH_LABELS[score]}</div>
    </div>
  )
}
