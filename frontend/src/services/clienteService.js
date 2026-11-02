import { api } from './api'

export const clienteService = {
  cadastrar: (cliente) => api.post('/clientes', cliente),
  listar: () => api.get('/clientes'),
  buscar: (id) => api.get(`/clientes/${id}`),
  atualizar: (id, cliente) => api.put(`/clientes/${id}`, cliente),
  excluir: (id) => api.delete(`/clientes/${id}`),
}
