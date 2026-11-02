# Histórias de Usuário

| ID | História | Critérios de aceitação | Caso de uso |
| :-- | :-- | :-- | :-- |
| HU01 | Como **usuário**, quero fazer login com usuário e senha, para acessar o sistema. | Apenas usuários cadastrados entram; credenciais inválidas exibem erro; todas as páginas internas exigem login. | UC01 |
| HU02 | Como **cliente**, quero me cadastrar informando RG, CPF, nome, endereço, profissão e meus empregadores com rendimentos, para poder usar o sistema. | CPF e usuário são únicos; no máximo 3 empregadores/rendimentos; após o cadastro o cliente já entra no sistema. | UC02 |
| HU03 | Como **cliente**, quero consultar, atualizar e excluir meu cadastro, para manter meus dados corretos. | O cliente só acessa o próprio cadastro; senha em branco mantém a atual; clientes com contrato executado não podem ser excluídos. | UC03 |
| HU04 | Como **cliente**, quero introduzir um pedido de aluguel escolhendo o automóvel e o período, para solicitar um aluguel. | O pedido nasce com status "Em análise"; a data de fim não pode ser anterior à de início. | UC04 |
| HU05 | Como **usuário**, quero consultar os pedidos e o status de cada um, para acompanhar o andamento. | O cliente vê apenas os próprios pedidos; agentes veem todos; o detalhe mostra parecer e contrato, quando houver. | UC05 |
| HU06 | Como **cliente ou agente**, quero modificar um pedido, para corrigir automóvel ou período. | Só pedidos "Em análise" podem ser modificados; o cliente só modifica os próprios pedidos. | UC06 |
| HU07 | Como **cliente**, quero cancelar um pedido, para desistir do aluguel. | Só pedidos "Em análise" ou "Aprovado" podem ser cancelados. | UC07 |
| HU08 | Como **agente**, quero avaliar financeiramente um pedido e registrar um parecer, para aprová-lo ou reprová-lo. | Só pedidos "Em análise" podem ser avaliados; o parecer positivo leva a "Aprovado" e o negativo a "Reprovado". | UC08 |
| HU09 | Como **agente**, quero executar o contrato de um pedido aprovado, definindo de quem será a propriedade do automóvel. | Só pedidos "Aprovado"; o automóvel é registrado em nome do cliente ou do próprio agente (empresa ou banco); o pedido passa a "Contratado". | UC09 |
| HU10 | Como **banco**, quero associar um contrato de crédito ao aluguel, para financiar o cliente. | Somente bancos concedem crédito; o crédito registra valor, juros mensais e prazo. | UC10 |
| HU11 | Como **agente**, quero consultar os clientes e seus rendimentos, para apoiar a análise financeira. | Somente agentes acessam a lista; a renda total é exibida. | UC11 |
