export default function FormField({
  label,
  id,
  error,
  hint,
  children
}) {
  return (
    <div className={`field ${error ? 'has-error' : ''}`}>
      <label htmlFor={id}>{label}</label>
      {children}
      {error ? (
        <span className="field-error" role="alert">{error}</span>
      ) : hint ? (
        <span className="field-hint">{hint}</span>
      ) : null}
    </div>
  )
}
