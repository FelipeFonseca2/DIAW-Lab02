import { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { automovelService } from '../services/automovelService'
import { pedidoService } from '../services/pedidoService'
import { descreverAutomovel } from '../utils/formatos'

/** Criação (/pedidos/novo) e modificação (/pedidos/:id/editar) de pedidos de aluguel. */
export function PedidoForm() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [automoveis, setAutomoveis] = useState([])
  const [pedido, setPedido] = useState({ automovelId: '', dataInicio: '', dataFim: '' })
  const [erro, setErro] = useState('')
  const [enviando, setEnviando] = useState(false)

  useEffect(() => {
    automovelService.listar().then(setAutomoveis).catch((err) => setErro(err.message))
    if (id) {
      pedidoService
        .buscar(id)
        .then((p) => setPedido({ automovelId: p.automovel.id, dataInicio: p.dataInicio, dataFim: p.dataFim }))
        .catch((err) => setErro(err.message))
    }
  }, [id])

  const alterar = (campo) => (e) => setPedido({ ...pedido, [campo]: e.target.value })

  async function handleSubmit(e) {
    e.preventDefault()
    setErro('')
    setEnviando(true)
    try {
      const dados = { ...pedido, automovelId: Number(pedido.automovelId) }
      const salvo = id ? await pedidoService.modificar(id, dados) : await pedidoService.criar(dados)
      navigate(`/pedidos/${salvo.id}`)
    } catch (err) {
      setErro(err.message)
    } finally {
      setEnviando(false)
    }
  }

  return (
    <section>
      <div className="cabecalho-pagina">
        <h1>{id ? `Modificar pedido #${id}` : 'Novo pedido de aluguel'}</h1>
      </div>
      <form className="cartao formulario estreito" onSubmit={handleSubmit}>
        <label>
          Automóvel
          <select value={pedido.automovelId} onChange={alterar('automovelId')} required>
            <option value="">Selecione…</option>
            {automoveis.map((a) => (
              <option key={a.id} value={a.id}>
                {descreverAutomovel(a)}
              </option>
            ))}
          </select>
        </label>
        <div className="grade">
          <label>
            Data de início
            <input type="date" value={pedido.dataInicio} onChange={alterar('dataInicio')} required />
          </label>
          <label>
            Data de fim
            <input
              type="date"
              value={pedido.dataFim}
              min={pedido.dataInicio || undefined}
              onChange={alterar('dataFim')}
              required
            />
          </label>
        </div>
        {erro && <p className="erro">{erro}</p>}
        <div className="acoes">
          <button type="submit" className="botao" disabled={enviando}>
            {enviando ? 'Salvando…' : id ? 'Salvar alterações' : 'Enviar pedido'}
          </button>
          <Link to={id ? `/pedidos/${id}` : '/pedidos'} className="botao secundario">
            Voltar
          </Link>
        </div>
      </form>
    </section>
  )
}
