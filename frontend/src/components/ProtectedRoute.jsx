import { Navigate } from 'react-router-dom'
import { useAuth } from '../context/auth'

/** Restringe a rota a usuários autenticados e, opcionalmente, a um perfil (CLIENTE ou AGENTE). */
export function ProtectedRoute({ perfil, children }) {
  const { usuario, carregando } = useAuth()

  if (carregando) return <p className="carregando">Carregando…</p>
  if (!usuario) return <Navigate to="/login" replace />
  if (perfil && usuario.perfil !== perfil) return <Navigate to="/pedidos" replace />
  return children
}
