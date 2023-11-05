package domini.Clases;

import domini.controladors.CtrlDocument;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que defineix la consulta de contingut de document
 * La consulta rep el nom d'un autor i el titol del seu document i retorna el contingut del document
 * @author bernat.homs
 */
public class ContingutDocument implements Consulta{

    /**
     * autor del document que es vol consultar
     */
    private final String autor_;

    /**
     * titol del document de l'autor que es vol consultar
     */
    private final String titol_;

    /**
     * constructora de la consulta de contingut de document
     * @param autor autor del document que es vol consultar
     * @param titol titol del document que es vol consultar
     */
    public ContingutDocument(String titol, String autor) {
        autor_ = autor;
        titol_ = titol;
    }

    /**
     * executa la consulta i retorna el contingut del document que es vol consutlar, identificat per autor, titol
     * @return retorna el contingut del document consultat
     */
    public ResultatContingut executar() throws FileNotFoundException {
        CtrlDocument cd = CtrlDocument.getInstance();
        List<String> parametres = new ArrayList<>();
        parametres.add(autor_);
        parametres.add(titol_);
        return new ResultatContingut((cd.getDocument(titol_, autor_).getContingut()), "Contingut Document", parametres);
    }
}
