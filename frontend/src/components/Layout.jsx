import { NavLink, Outlet, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/auth'
import { TIPO_PROPRIETARIO } from '../utils/formatos'

export function Layout() {
  const { usuario, sair } = useAuth()
  const navigate = useNavigate()
  const ehCliente = usuario.perfil === 'CLIENTE'

  function handleSair() {
    sair()
    navigate('/login')
  }

  return (
    <>
      <header className="topo">
        <div className="topo-conteudo">
          <NavLink to="/pedidos" className="marca">
            <img src="/favicon.svg" alt="" width="32" height="32" />
            Aluguel de Carros
          </NavLink>
          <nav>
            <NavLink to="/pedidos">Pedidos</NavLink>
            {ehCliente ? <NavLink to="/perfil">Meu cadastro</NavLink> : <NavLink to="/clientes">Clientes</NavLink>}
          </nav>
          <div className="usuario">
            <span>
              {usuario.nome}
              <small>{ehCliente ? 'Cliente' : `Agente · ${TIPO_PROPRIETARIO[usuario.tipoAgente]}`}</small>
            </span>
            <button type="button" className="botao secundario" onClick={handleSair}>
              Sair
            </button>
          </div>
        </div>
      </header>
      <main className="conteudo">
        <Outlet />
      </main>
    </>
  )
}
