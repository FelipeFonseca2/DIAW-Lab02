import { STATUS_PEDIDO } from '../utils/formatos'

export function StatusBadge({ status }) {
  return <span className={`badge badge-${status.toLowerCase()}`}>{STATUS_PEDIDO[status] ?? status}</span>
}
