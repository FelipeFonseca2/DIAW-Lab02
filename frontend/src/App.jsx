import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { Layout } from './components/Layout'
import { ProtectedRoute } from './components/ProtectedRoute'
import { AuthProvider } from './context/AuthProvider'
import { Cadastro } from './pages/Cadastro'
import { Clientes } from './pages/Clientes'
import { Login } from './pages/Login'
import { PedidoDetalhe } from './pages/PedidoDetalhe'
import { PedidoForm } from './pages/PedidoForm'
import { Pedidos } from './pages/Pedidos'
import { Perfil } from './pages/Perfil'

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/cadastro" element={<Cadastro />} />
          <Route
            element={
              <ProtectedRoute>
                <Layout />
              </ProtectedRoute>
            }
          >
            <Route path="/pedidos" element={<Pedidos />} />
            <Route
              path="/pedidos/novo"
              element={
                <ProtectedRoute perfil="CLIENTE">
                  <PedidoForm />
                </ProtectedRoute>
              }
            />
            <Route path="/pedidos/:id" element={<PedidoDetalhe />} />
            <Route path="/pedidos/:id/editar" element={<PedidoForm />} />
            <Route
              path="/perfil"
              element={
                <ProtectedRoute perfil="CLIENTE">
                  <Perfil />
                </ProtectedRoute>
              }
            />
            <Route
              path="/clientes"
              element={
                <ProtectedRoute perfil="AGENTE">
                  <Clientes />
                </ProtectedRoute>
              }
            />
          </Route>
          <Route path="*" element={<Navigate to="/pedidos" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}
