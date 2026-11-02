import { useState } from 'react'

const MAX_RENDIMENTOS = 3

const VAZIO = {
  login: '',
  senha: '',
  nome: '',
  rg: '',
  cpf: '',
  endereco: '',
  profissao: '',
  rendimentos: [],
}

/** Formulário compartilhado entre o cadastro de cliente e a edição do próprio cadastro. */
export function ClienteForm({ inicial, senhaObrigatoria, textoBotao, onSubmit }) {
  const [cliente, setCliente] = useState({ ...VAZIO, ...inicial, senha: '' })
  const [erro, setErro] = useState('')
  const [enviando, setEnviando] = useState(false)

  const alterar = (campo) => (e) => setCliente({ ...cliente, [campo]: e.target.value })

  function alterarRendimento(indice, campo, valor) {
    const rendimentos = cliente.rendimentos.map((r, i) => (i === indice ? { ...r, [campo]: valor } : r))
    setCliente({ ...cliente, rendimentos })
  }

  function adicionarRendimento() {
    setCliente({ ...cliente, rendimentos: [...cliente.rendimentos, { entidadeEmpregadora: '', valor: '' }] })
  }

  function removerRendimento(indice) {
    setCliente({ ...cliente, rendimentos: cliente.rendimentos.filter((_, i) => i !== indice) })
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setErro('')
    setEnviando(true)
    try {
      await onSubmit({
        ...cliente,
        rendimentos: cliente.rendimentos.map((r) => ({ ...r, valor: Number(r.valor) })),
      })
    } catch (err) {
      setErro(err.message)
    } finally {
      setEnviando(false)
    }
  }

  return (
    <form className="formulario" onSubmit={handleSubmit}>
      <fieldset>
        <legend>Acesso</legend>
        <div className="grade">
          <label>
            Usuário
            <input value={cliente.login} onChange={alterar('login')} required maxLength={60} />
          </label>
          <label>
            Senha {!senhaObrigatoria && <small>(deixe em branco para manter)</small>}
            <input type="password" value={cliente.senha} onChange={alterar('senha')} required={senhaObrigatoria} />
          </label>
        </div>
      </fieldset>

      <fieldset>
        <legend>Identificação</legend>
        <div className="grade">
          <label className="largo">
            Nome
            <input value={cliente.nome} onChange={alterar('nome')} required maxLength={120} />
          </label>
          <label>
            RG
            <input value={cliente.rg} onChange={alterar('rg')} required maxLength={20} />
          </label>
          <label>
            CPF
            <input value={cliente.cpf} onChange={alterar('cpf')} required maxLength={14} placeholder="000.000.000-00" />
          </label>
          <label className="largo">
            Endereço
            <input value={cliente.endereco} onChange={alterar('endereco')} required />
          </label>
          <label className="largo">
            Profissão
            <input value={cliente.profissao ?? ''} onChange={alterar('profissao')} maxLength={80} />
          </label>
        </div>
      </fieldset>

      <fieldset>
        <legend>
          Empregadores e rendimentos <small>(máximo {MAX_RENDIMENTOS})</small>
        </legend>
        {cliente.rendimentos.map((r, i) => (
          <div className="linha-rendimento" key={i}>
            <label>
              Entidade empregadora
              <input
                value={r.entidadeEmpregadora}
                onChange={(e) => alterarRendimento(i, 'entidadeEmpregadora', e.target.value)}
                required
              />
            </label>
            <label>
              Rendimento (R$)
              <input
                type="number"
                min="0"
                step="0.01"
                value={r.valor}
                onChange={(e) => alterarRendimento(i, 'valor', e.target.value)}
                required
              />
            </label>
            <button type="button" className="botao secundario" onClick={() => removerRendimento(i)}>
              Remover
            </button>
          </div>
        ))}
        {cliente.rendimentos.length < MAX_RENDIMENTOS && (
          <button type="button" className="botao secundario" onClick={adicionarRendimento}>
            + Adicionar rendimento
          </button>
        )}
      </fieldset>

      {erro && <p className="erro">{erro}</p>}
      <button type="submit" className="botao" disabled={enviando}>
        {enviando ? 'Salvando…' : textoBotao}
      </button>
    </form>
  )
}
