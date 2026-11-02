import { useState } from 'react'
import { Link, Navigate, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/auth'

export function Login() {
  const { usuario, entrar } = useAuth()
  const navigate = useNavigate()
  const [login, setLogin] = useState('')
  const [senha, setSenha] = useState('')
  const [erro, setErro] = useState('')
  const [enviando, setEnviando] = useState(false)

  if (usuario) return <Navigate to="/pedidos" replace />

  async function handleSubmit(e) {
    e.preventDefault()
    setErro('')
    setEnviando(true)
    try {
      await entrar(login, senha)
      navigate('/pedidos')
    } catch (err) {
      setErro(err.message)
    } finally {
      setEnviando(false)
    }
  }

  return (
    <main className="pagina-acesso">
      <form className="cartao acesso" onSubmit={handleSubmit}>
        <img src="/favicon.svg" alt="" width="64" height="64" />
        <h1>Aluguel de Carros</h1>
        <p className="subtitulo">Entre com seu usuário e senha</p>
        <label>
          Usuário
          <input value={login} onChange={(e) => setLogin(e.target.value)} required autoFocus />
        </label>
        <label>
          Senha
          <input type="password" value={senha} onChange={(e) => setSenha(e.target.value)} required />
        </label>
        {erro && <p className="erro">{erro}</p>}
        <button type="submit" className="botao" disabled={enviando}>
          {enviando ? 'Entrando…' : 'Entrar'}
        </button>
        <p className="rodape-acesso">
          Ainda não tem cadastro? <Link to="/cadastro">Cadastre-se como cliente</Link>
        </p>
      </form>
    </main>
  )
}
