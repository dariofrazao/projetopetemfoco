Feature: cadastrar
    Como consumidor, eu preciso me cadastrar para que eu possa ter acesso ao aplicativo.
  Scenario: Entre com as informações requisitadas e realize seu cadastro ou recebe uma mensagem de erro.
    Given Eu tenho que preencher os campos com as informaçoes:<nome>,<email>,<senha>,<senha2>
    When Eu realizar a confirmação do meu cadastro
    Then Eu devo receber uma mensagem informando o estado do procedimento:<result>

    Examples:
       | nome  | email                  | senha     | senha2  |                         result                                  |
       | Raul  | novoEmailg@gmail.com   | 123456    | 123456  | Sucesso! Pronto para a próxima etapa                            |
       | Raul  | testesdakjdkas--*@ffss | 123456    | 123456  | Erro ao cadastrar usuário: Email indicado não é válido.         |
       | Raul  | novoEmailg@gmail.com   | 123456    | 1234567 | Erro ao cadastrar usuário: Os campos de senha devem ser iguais. |
       |       | novoEmailg@gmail.com   | 123456    | 123456  | Erro ao cadastrar usuário: Preencha todos os campos             |
       | Raul  |                        | 123456    | 123456  | Erro ao cadastrar usuário: Preencha todos os campos             |
       | Raul  | novoEmailg@gmail.com   |           | 123456  | Erro ao cadastrar usuário: Preencha todos os campos             |
       | Raul  | novoEmailg@gmail.com   | 123456    |         | Erro ao cadastrar usuário: Os campos de senha devem ser iguais. |
       |       |                        |           |         | Erro ao cadastrar usuário: Preencha todos os campos             |

  Scenario: Agora devo infomar os dados referentes a meu endereço
    Given Eu tenho que preencher os campos obrigatorios com as informaçoes:<logradouro>,<bairro>,<cidade>,<cep>,<uf>
    And Se eu desejar posso preencher os campos opcionais: <numero>,<complemento>
    When  Eu realizar a confirmação do meu cadastro
    Then  Eu devo receber uma mensagem informando o estado do procedimento:<result>

    Examples:
      | logradouro  | bairro |  cidade  | numero |        complemento     |cep       | uf |  result                                                 |
      | setor norte | gunga  |  Naboo   |   42   | prox. palacio de naboo |55299-510 | PE |  Sucesso ao cadastrar usuário                           |
      | setor norte | gunga  |  Naboo   |        | prox. palacio de naboo |55299-510 | PE |  Sucesso ao cadastrar usuário                           |
      | setor norte | gunga  |  Naboo   |   42   |                        |55299-510 | PE |  Sucesso ao cadastrar usuário                           |
      | setor norte | gunga  |  Naboo   |        |                        |55299-510 | PE |  Sucesso ao cadastrar usuário                           |
      |             | gunga  |  Naboo   |   42   | prox. palacio de naboo |55299-510 | PE |  Erro ao cadastrar endereço: Preencha todos os campos   |
      | setor norte |        |  Naboo   |   42   | prox. palacio de naboo |55299-510 | PE |  Erro ao cadastrar endereço: Preencha todos os campos   |
      | setor norte | gunga  |          |   42   | prox. palacio de naboo |55299-510 | PE |  Erro ao cadastrar endereço: Preencha todos os campos   |
      | setor norte | gunga  |  Naboo   |   42   | prox. palacio de naboo |          | PE |  Erro ao cadastrar endereço: Preencha todos os campos   |
      |             |        |          |   42   | prox. palacio de naboo |          | PE |  Erro ao cadastrar endereço: Preencha todos os campos   |
      |             |        |          |   42   |                        |          | PE |  Erro ao cadastrar endereço: Preencha todos os campos   |