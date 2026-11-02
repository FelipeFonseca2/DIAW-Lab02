import { Link, useNavigate } from 'react-router-dom'
import { ClienteForm } from '../components/ClienteForm'
import { useAuth } from '../context/auth'
import { clienteService } from '../services/clienteService'

export function Cadastro() {
  const { entrar } = useAuth()
  const navigate = useNavigate()

  async function cadastrar(cliente) {
    await clienteService.cadastrar(cliente)
    await entrar(cliente.login, cliente.senha)
    navigate('/pedidos')
  }

  return (
    <main className="pagina-acesso">
      <div className="cartao cadastro">
        <h1>Cadastro de cliente</h1>
        <p className="subtitulo">
          Já tem cadastro? <Link to="/login">Entrar</Link>
        </p>
        <ClienteForm senhaObrigatoria textoBotao="Cadastrar" onSubmit={cadastrar} />
      </div>
    </main>
  )
}
