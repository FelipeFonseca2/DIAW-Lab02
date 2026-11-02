import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { StatusBadge } from '../components/StatusBadge'
import { useAuth } from '../context/auth'
import { pedidoService } from '../services/pedidoService'
import { descreverAutomovel, formatarData, formatarMoeda, TIPO_PROPRIETARIO } from '../utils/formatos'

export function PedidoDetalhe() {
  const { id } = useParams()
  const { usuario } = useAuth()
  const ehCliente = usuario.perfil === 'CLIENTE'
  const [pedido, setPedido] = useState(null)
  const [erro, setErro] = useState('')

  useEffect(() => {
    pedidoService.buscar(id).then(setPedido).catch((err) => setErro(err.message))
  }, [id])

  async function executar(acao) {
    setErro('')
    try {
      setPedido(await acao())
    } catch (err) {
      setErro(err.message)
    }
  }

  function cancelar() {
    if (window.confirm('Deseja cancelar este pedido?')) {
      executar(() => pedidoService.cancelar(id))
    }
  }

  if (!pedido) return erro ? <p className="erro">{erro}</p> : <p className="carregando">Carregando…</p>

  const emAnalise = pedido.status === 'EM_ANALISE'
  const podeCancelar = ehCliente && (emAnalise || pedido.status === 'APROVADO')

  return (
    <section>
      <div className="cabecalho-pagina">
        <div>
          <Link to="/pedidos">← Pedidos</Link>
          <h1>
            Pedido #{pedido.id} <StatusBadge status={pedido.status} />
          </h1>
        </div>
        <div className="acoes">
          {emAnalise && (
            <Link to={`/pedidos/${pedido.id}/editar`} className="botao secundario">
              Modificar
            </Link>
          )}
          {podeCancelar && (
            <button type="button" className="botao perigo" onClick={cancelar}>
              Cancelar pedido
            </button>
          )}
        </div>
      </div>

      {erro && <p className="erro">{erro}</p>}

      <div className="cartao">
        <dl className="detalhes">
          <dt>Cliente</dt>
          <dd>{pedido.clienteNome}</dd>
          <dt>Automóvel</dt>
          <dd>{descreverAutomovel(pedido.automovel)}</dd>
          <dt>Matrícula</dt>
          <dd>{pedido.automovel.matricula}</dd>
          <dt>Período</dt>
          <dd>
            {formatarData(pedido.dataInicio)} a {formatarData(pedido.dataFim)}
          </dd>
          <dt>Criado em</dt>
          <dd>{formatarData(pedido.dataCriacao)}</dd>
          {pedido.agenteAvaliador && (
            <>
              <dt>Avaliado por</dt>
              <dd>{pedido.agenteAvaliador}</dd>
              <dt>Parecer</dt>
              <dd>{pedido.parecer || '—'}</dd>
            </>
          )}
        </dl>
      </div>

      {pedido.contrato && <Contrato contrato={pedido.contrato} proprietario={pedido.automovel.proprietario} />}

      {!ehCliente && emAnalise && (
        <Avaliacao onAvaliar={(avaliacao) => executar(() => pedidoService.avaliar(id, avaliacao))} />
      )}

      {!ehCliente && pedido.status === 'APROVADO' && (
        <ExecucaoContrato
          tipoAgente={usuario.tipoAgente}
          onExecutar={(contrato) => executar(() => pedidoService.executarContrato(id, contrato))}
        />
      )}
    </section>
  )
}

function Contrato({ contrato, proprietario }) {
  return (
    <div className="cartao">
      <h2>Contrato #{contrato.id}</h2>
      <dl className="detalhes">
        <dt>Assinado em</dt>
        <dd>{formatarData(contrato.dataAssinatura)}</dd>
        <dt>Agente</dt>
        <dd>{contrato.agente}</dd>
        <dt>Propriedade do automóvel</dt>
        <dd>
          {TIPO_PROPRIETARIO[contrato.tipoProprietario]} ({proprietario})
        </dd>
        {contrato.credito && (
          <>
            <dt>Crédito</dt>
            <dd>
              {formatarMoeda(contrato.credito.valor)} em {contrato.credito.prazoMeses} meses a{' '}
              {contrato.credito.taxaJurosMensal}% a.m. · {contrato.credito.banco}
            </dd>
          </>
        )}
      </dl>
    </div>
  )
}

function Avaliacao({ onAvaliar }) {
  const [parecer, setParecer] = useState('')

  return (
    <div className="cartao formulario">
      <h2>Avaliação financeira</h2>
      <label>
        Parecer
        <textarea value={parecer} onChange={(e) => setParecer(e.target.value)} maxLength={500} rows={3} />
      </label>
      <div className="acoes">
        <button type="button" className="botao" onClick={() => onAvaliar({ aprovado: true, parecer })}>
          Parecer positivo
        </button>
        <button type="button" className="botao perigo" onClick={() => onAvaliar({ aprovado: false, parecer })}>
          Parecer negativo
        </button>
      </div>
    </div>
  )
}

function ExecucaoContrato({ tipoAgente, onExecutar }) {
  const ehBanco = tipoAgente === 'BANCO'
  const [tipoProprietario, setTipoProprietario] = useState('CLIENTE')
  const [comCredito, setComCredito] = useState(false)
  const [credito, setCredito] = useState({ valor: '', taxaJurosMensal: '', prazoMeses: '' })

  const alterar = (campo) => (e) => setCredito({ ...credito, [campo]: e.target.value })

  function handleSubmit(e) {
    e.preventDefault()
    onExecutar({
      tipoProprietario,
      credito: comCredito
        ? {
            valor: Number(credito.valor),
            taxaJurosMensal: Number(credito.taxaJurosMensal),
            prazoMeses: Number(credito.prazoMeses),
          }
        : null,
    })
  }

  return (
    <form className="cartao formulario" onSubmit={handleSubmit}>
      <h2>Execução do contrato</h2>
      <label>
        Registrar o automóvel como propriedade de
        <select value={tipoProprietario} onChange={(e) => setTipoProprietario(e.target.value)}>
          <option value="CLIENTE">Cliente</option>
          <option value={tipoAgente}>{TIPO_PROPRIETARIO[tipoAgente]} (este agente)</option>
        </select>
      </label>
      {ehBanco && (
        <label className="checkbox">
          <input type="checkbox" checked={comCredito} onChange={(e) => setComCredito(e.target.checked)} />
          Associar contrato de crédito
        </label>
      )}
      {comCredito && (
        <div className="grade tres">
          <label>
            Valor (R$)
            <input type="number" min="0.01" step="0.01" value={credito.valor} onChange={alterar('valor')} required />
          </label>
          <label>
            Juros (% a.m.)
            <input
              type="number"
              min="0"
              step="0.01"
              value={credito.taxaJurosMensal}
              onChange={alterar('taxaJurosMensal')}
              required
            />
          </label>
          <label>
            Prazo (meses)
            <input type="number" min="1" value={credito.prazoMeses} onChange={alterar('prazoMeses')} required />
          </label>
        </div>
      )}
      <button type="submit" className="botao">
        Executar contrato
      </button>
    </form>
  )
}
