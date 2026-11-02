import { useEffect, useState } from 'react'
import { clienteService } from '../services/clienteService'
import { formatarMoeda } from '../utils/formatos'

/** Consulta de clientes pelos agentes, apoiando a análise financeira dos pedidos. */
export function Clientes() {
  const [clientes, setClientes] = useState(null)
  const [erro, setErro] = useState('')

  useEffect(() => {
    clienteService.listar().then(setClientes).catch((err) => setErro(err.message))
  }, [])

  return (
    <section>
      <div className="cabecalho-pagina">
        <h1>Clientes</h1>
      </div>
      {erro && <p className="erro">{erro}</p>}
      {clientes === null && !erro && <p className="carregando">Carregando…</p>}
      {clientes?.length === 0 && <p className="vazio">Nenhum cliente cadastrado.</p>}
      {clientes?.length > 0 && (
        <div className="tabela-container">
          <table>
            <thead>
              <tr>
                <th>Nome</th>
                <th>CPF</th>
                <th>RG</th>
                <th>Profissão</th>
                <th>Empregadores</th>
                <th>Renda total</th>
              </tr>
            </thead>
            <tbody>
              {clientes.map((c) => (
                <tr key={c.id}>
                  <td>{c.nome}</td>
                  <td>{c.cpf}</td>
                  <td>{c.rg}</td>
                  <td>{c.profissao || '—'}</td>
                  <td>{c.rendimentos.map((r) => r.entidadeEmpregadora).join(', ') || '—'}</td>
                  <td>{formatarMoeda(c.rendimentos.reduce((total, r) => total + Number(r.valor), 0))}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  )
}
