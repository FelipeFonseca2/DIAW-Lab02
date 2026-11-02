import { useCallback, useEffect, useMemo, useState } from 'react'
import { SESSAO_EXPIRADA, tokenStorage } from '../services/api'
import { authService } from '../services/authService'
import { AuthContext } from './auth'

export function AuthProvider({ children }) {
  const [usuario, setUsuario] = useState(null)
  const [carregando, setCarregando] = useState(() => Boolean(tokenStorage.get()))

  // Restaura a sessão a partir do token salvo
  useEffect(() => {
    if (!tokenStorage.get()) return
    authService
      .usuarioLogado()
      .then(setUsuario)
      .catch(() => tokenStorage.clear())
      .finally(() => setCarregando(false))
  }, [])

  useEffect(() => {
    const expirar = () => setUsuario(null)
    window.addEventListener(SESSAO_EXPIRADA, expirar)
    return () => window.removeEventListener(SESSAO_EXPIRADA, expirar)
  }, [])

  const entrar = useCallback(async (login, senha) => {
    const { token, usuario } = await authService.login(login, senha)
    tokenStorage.set(token)
    setUsuario(usuario)
    return usuario
  }, [])

  const sair = useCallback(() => {
    tokenStorage.clear()
    setUsuario(null)
  }, [])

  const valor = useMemo(
    () => ({ usuario, carregando, entrar, sair, setUsuario }),
    [usuario, carregando, entrar, sair],
  )

  return <AuthContext.Provider value={valor}>{children}</AuthContext.Provider>
}
