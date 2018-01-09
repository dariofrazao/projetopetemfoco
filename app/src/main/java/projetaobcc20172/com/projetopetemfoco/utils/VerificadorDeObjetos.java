package projetaobcc20172.com.projetopetemfoco.utils;

import projetaobcc20172.com.projetopetemfoco.excecoes.CampoObrAusenteException;
import projetaobcc20172.com.projetopetemfoco.model.Endereco;
import projetaobcc20172.com.projetopetemfoco.model.Pet;

/**
 * Created by raul on 10/12/17.
 * Essa classe é responsavel por validar os dados que são recebidos pelo controller
 */

public class VerificadorDeObjetos {


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

    public static void vDadosPet(Pet pet) throws CampoObrAusenteException  {
        if (pet.getNome().isEmpty()) {
            throw new CampoObrAusenteException ();
            }
        }


}
