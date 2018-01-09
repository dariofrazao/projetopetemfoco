package projetaobcc20172.com.projetopetemfoco.utils;

import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.excecoes.SenhasDiferentesException;
import projetaobcc20172.com.projetopetemfoco.model.Avaliacao;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Pet;
import projetaobcc20172.com.projetopetemfoco.model.Usuario;
/**
 * Created by raul on 10/12/17.
 * Essa classe é responsavel por validar os dados que são recebidos pelo controller
 */

public class VerificadorDeObjetos {

    //Verifica se as informações do usuário estão corretas
    public static void vDadosUsuario(Usuario user) throws  CampoObrAusenteException, SenhasDiferentesException {
        if(user.getNome().isEmpty()|| user.getSenha().isEmpty()
                || user.getEmail().isEmpty()){
            throw new CampoObrAusenteException();
        }
        else if(!user.getSenha().equals(user.getSenha2())){
            throw new SenhasDiferentesException();
        }

    }

    //Método responsável por avaliar se um obj endereco possui todas os atributos obrigatorios
    public static void vDadosObrEndereco(Endereco end) throws CampoObrAusenteException {
        if (        end.getLogradouro().isEmpty()
                ||
                end.getBairro().isEmpty()
                ||
                end.getLocalidade().isEmpty()
                ||
                end.getUf().isEmpty()
                || end.getCep().isEmpty()
                ) {
            throw new CampoObrAusenteException();
        }
    }

    //Método responsável por avaliar se um obj avaliacao possui todas os atributos obrigatorios
    public static void vDadosObjAvaliacao(Avaliacao avaliacao) throws CampoObrAusenteException {
        if (avaliacao.getIdUsuario().isEmpty()) {
            throw new CampoObrAusenteException();
        }
    }

    public static void vDadosPet(Pet pet) throws CampoObrAusenteException  {
        if (pet.getNome().isEmpty()) {
            throw new CampoObrAusenteException ();
            }
        }


}
