const BASE_URL = import.meta.env.VITE_API_URL ?? '/api'
const TOKEN_KEY = 'aluguel.token'

export const tokenStorage = {
  get: () => localStorage.getItem(TOKEN_KEY),
  set: (token) => localStorage.setItem(TOKEN_KEY, token),
  clear: () => localStorage.removeItem(TOKEN_KEY),
}

/** Evento disparado quando o token salvo deixa de ser aceito pelo back-end. */
export const SESSAO_EXPIRADA = 'aluguel:sessao-expirada'

async function request(method, path, body) {
  const token = tokenStorage.get()
  const response = await fetch(BASE_URL + path, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
    body: body !== undefined ? JSON.stringify(body) : undefined,
  })

  if (response.status === 401 && token) {
    tokenStorage.clear()
    window.dispatchEvent(new Event(SESSAO_EXPIRADA))
  }

  const texto = await response.text()
  const dados = texto ? JSON.parse(texto) : null
  if (!response.ok) {
    throw new Error(dados?.mensagem ?? `Erro ${response.status}`)
  }
  return dados
}

export const api = {
  get: (path) => request('GET', path),
  post: (path, body) => request('POST', path, body),
  put: (path, body) => request('PUT', path, body),
  delete: (path) => request('DELETE', path),
}
