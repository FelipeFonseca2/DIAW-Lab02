export const STATUS_PEDIDO = {
  EM_ANALISE: 'Em análise',
  APROVADO: 'Aprovado',
  REPROVADO: 'Reprovado',
  CANCELADO: 'Cancelado',
  CONTRATADO: 'Contratado',
}

export const TIPO_PROPRIETARIO = {
  CLIENTE: 'Cliente',
  EMPRESA: 'Empresa',
  BANCO: 'Banco',
}

/** Converte "AAAA-MM-DD" para "DD/MM/AAAA" sem passar por Date (evita deslocamento de fuso). */
export function formatarData(iso) {
  if (!iso) return '—'
  const [ano, mes, dia] = iso.slice(0, 10).split('-')
  return `${dia}/${mes}/${ano}`
}

export function formatarMoeda(valor) {
  return Number(valor ?? 0).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
}

export function descreverAutomovel(automovel) {
  return `${automovel.marca} ${automovel.modelo} ${automovel.ano} · ${automovel.placa}`
}
