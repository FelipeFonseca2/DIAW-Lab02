import { api } from './api'

export const authService = {
  login: (login, senha) => api.post('/auth/login', { login, senha }),
  usuarioLogado: () => api.get('/auth/me'),
}
