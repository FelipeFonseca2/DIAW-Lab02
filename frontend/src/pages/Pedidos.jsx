import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { StatusBadge } from '../components/StatusBadge'
import { useAuth } from '../context/auth'
import { pedidoService } from '../services/pedidoService'
import { descreverAutomovel, formatarData } from '../utils/formatos'

export function Pedidos() {
  const { usuario } = useAuth()
  const ehCliente = usuario.perfil === 'CLIENTE'
  const [pedidos, setPedidos] = useState(null)
  const [erro, setErro] = useState('')

  useEffect(() => {
    pedidoService.listar().then(setPedidos).catch((err) => setErro(err.message))
  }, [])

  return (
    <section>
      <div className="cabecalho-pagina">
        <div>
          <h1>{ehCliente ? 'Meus pedidos de aluguel' : 'Pedidos de aluguel'}</h1>
          <p className="subtitulo">
            {ehCliente
              ? 'Acompanhe o status dos seus pedidos.'
              : 'Analise os pedidos e execute os contratos aprovados.'}
          </p>
        </div>
        {ehCliente && (
          <Link to="/pedidos/novo" className="botao">
            + Novo pedido
          </Link>
        )}
      </div>

      {erro && <p className="erro">{erro}</p>}
      {pedidos === null && !erro && <p className="carregando">Carregando…</p>}
      {pedidos?.length === 0 && <p className="vazio">Nenhum pedido encontrado.</p>}

      {pedidos?.length > 0 && (
        <div className="tabela-container">
          <table>
            <thead>
              <tr>
                <th>Nº</th>
                {!ehCliente && <th>Cliente</th>}
                <th>Automóvel</th>
                <th>Período</th>
                <th>Status</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {pedidos.map((p) => (
                <tr key={p.id}>
                  <td>#{p.id}</td>
                  {!ehCliente && <td>{p.clienteNome}</td>}
                  <td>{descreverAutomovel(p.automovel)}</td>
                  <td>
                    {formatarData(p.dataInicio)} a {formatarData(p.dataFim)}
                  </td>
                  <td>
                    <StatusBadge status={p.status} />
                  </td>
                  <td>
                    <Link to={`/pedidos/${p.id}`}>Detalhes</Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  )
}
