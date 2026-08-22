import DayTimeline from './DayTimeline.jsx'

export default function AuthLayout({ children }) {
  return (
    <div className="auth-shell">
      <aside className="auth-brand">
        <div className="auth-brand-mark">
          <span className="auth-brand-dot" />
          Dayflow HRMS
        </div>

        <div className="auth-brand-copy">
          <h1>Every workday, perfectly aligned.</h1>
          <p>
            One place for attendance, leave and payroll visibility —
            built for the people who run HR and the people they support.
          </p>
        </div>

        <div>
          <DayTimeline />
          <div className="auth-brand-footer">Attendance · Leave · Payroll · Approvals</div>
        </div>
      </aside>

      <main className="auth-form-panel">
        <div className="auth-form-card">{children}</div>
      </main>
    </div>
  )
}
