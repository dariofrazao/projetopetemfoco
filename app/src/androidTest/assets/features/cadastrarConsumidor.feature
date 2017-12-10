Feature: cadastrar
    Como consumidor, eu preciso me cadastrar para que eu possa ter acesso ao aplicativo.
  Scenario: Entre com as informações requisitadas e realize seu cadastro ou recebe uma mensagem de erro.
    Given Eu tenho que preencher os campos com as informaçoes:<nome>,<email>,<senha>,<senha2>,<result>
    When Eu clicar no botão "cadastrar"
    Then Eu devo receber uma mensagem informando o estado do procedimento

    Exemples:
       | nome  | email                  | senha     | senha2 | result |
       | Raul  | raulpedrouag@gmail.com | 123456    | 123456 |        |
       | 7    | 6    | –  | 1.0      |
       | 5    | 4    | x  | 20.0     |
       | 3    | 2    | /  | 1.5      |
       | 1    | 0    | /  | Infinity |

  Scenario: Agora devo infomar os dados referentes a meu endereço
    Given Eu tenho que preencher os campos com as informaçoes:<nome>,<email>,<senha>,<senha2>,<result>
    When  Eu clicar no botão "cadastrar"
    Then  Eu devo receber uma mensagem informando o estado do procedimento