package domini.Clases;

import java.io.FileNotFoundException;

/**
 * Representa la interficie de una consulta
 * 
 */
public interface Consulta {

    /**
     * Executa la consulta retornant el resultat d'aquesta.
     * @return Retorna una instancia de ResultatConsulta
     */
    public ResultatConsulta executar() throws FileNotFoundException;
}
