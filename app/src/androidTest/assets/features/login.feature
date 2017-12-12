# new feature
# Tags: optional
    
Feature: Logar na aplicação
    Como consumidor, eu preciso informar meu e-mail e senha para que eu possa utilizar o aplicativo.
Scenario: Informar as informações requisitadas e logar na aplicação ou receber uma mensagem de erro
    Given eu informo meu <email> e <senha>
    When Eu finalizar a operação de login
    Then Eu recebo a mensagem: <mensagem>

    Examples:
       | email                       | senha          | mensagem                |
       | raulpedrouag@gmail.com      | 123456e        | Sucesso ao fazer login! |
       | joaoNaoCadastrado@gmail.com | senhaInvalida  | Erro ao fazer login!    |
       |                             |                | Erro ao fazer login!    |
       | raulpedrouag@gmail.com      |                | Erro ao fazer login!    |
       |                             | 123456e        | Erro ao fazer login!    |