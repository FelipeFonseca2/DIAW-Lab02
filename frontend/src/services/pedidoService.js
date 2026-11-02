import { api } from './api'

export const pedidoService = {
  criar: (pedido) => api.post('/pedidos', pedido),
  listar: () => api.get('/pedidos'),
  buscar: (id) => api.get(`/pedidos/${id}`),
  modificar: (id, pedido) => api.put(`/pedidos/${id}`, pedido),
  cancelar: (id) => api.post(`/pedidos/${id}/cancelamento`),
  avaliar: (id, avaliacao) => api.post(`/pedidos/${id}/avaliacao`, avaliacao),
  executarContrato: (id, contrato) => api.post(`/pedidos/${id}/contrato`, contrato),
}
