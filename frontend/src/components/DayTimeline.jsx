// The signature visual: a horizontal gradient bar moving from indigo
// ("morning") to amber ("afternoon"), labeled with the real workday
// milestones this HRMS tracks — check-in, tasks, leave, check-out.
// It's a literal, functional expression of the product name "Dayflow"
// rather than decoration.

const MILESTONES = ['09:00 Check-in', 'Tasks', 'Leave', '18:00 Check-out']

export default function DayTimeline() {
  return (
    <div className="day-timeline" aria-hidden="true">
      <div className="day-timeline-track">
        <div className="day-timeline-fill" />
      </div>
      <div className="day-timeline-labels">
        {MILESTONES.map((m) => (
          <span key={m}>{m}</span>
        ))}
      </div>
    </div>
  )
}
