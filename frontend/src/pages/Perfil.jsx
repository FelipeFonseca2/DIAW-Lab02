import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { ClienteForm } from '../components/ClienteForm'
import { useAuth } from '../context/auth'
import { clienteService } from '../services/clienteService'

/** Consulta, atualização e exclusão do cadastro do cliente logado. */
export function Perfil() {
  const { usuario, setUsuario, sair } = useAuth()
  const navigate = useNavigate()
  const [cliente, setCliente] = useState(null)
  const [mensagem, setMensagem] = useState('')
  const [erro, setErro] = useState('')

  useEffect(() => {
    clienteService.buscar(usuario.id).then(setCliente).catch((err) => setErro(err.message))
  }, [usuario.id])

  async function salvar(dados) {
    setMensagem('')
    const atualizado = await clienteService.atualizar(usuario.id, dados)
    setCliente(atualizado)
    setUsuario({ ...usuario, login: atualizado.login, nome: atualizado.nome })
    setMensagem('Cadastro atualizado.')
  }

  async function excluir() {
    if (!window.confirm('Excluir seu cadastro? Esta ação não pode ser desfeita.')) return
    setErro('')
    try {
      await clienteService.excluir(usuario.id)
      sair()
      navigate('/login')
    } catch (err) {
      setErro(err.message)
    }
  }

  if (!cliente) return erro ? <p className="erro">{erro}</p> : <p className="carregando">Carregando…</p>

  return (
    <section>
      <div className="cabecalho-pagina">
        <h1>Meu cadastro</h1>
        <button type="button" className="botao perigo" onClick={excluir}>
          Excluir cadastro
        </button>
      </div>
      {erro && <p className="erro">{erro}</p>}
      {mensagem && <p className="sucesso">{mensagem}</p>}
      <div className="cartao">
        <ClienteForm key={JSON.stringify(cliente)} inicial={cliente} textoBotao="Salvar alterações" onSubmit={salvar} />
      </div>
    </section>
  )
}
