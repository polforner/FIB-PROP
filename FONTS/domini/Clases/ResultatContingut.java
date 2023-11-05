package domini.Clases;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Representa el resultat d’una consulta que retorna una llista,
 * com son la consulta ContingutDocuement. S’encarrega de les ordenacions dels resultats
 */

public class ResultatContingut implements ResultatConsulta{
    /**
     * Tipus de consulta que ha generat la instancia del ResultatConsultaNoms
     */
    private String tipusConsulta_;
    /**
     *  Llistat de parametres de la consulta que han generat la instancia de la classe.
     */
    private List<String> parametres_;
    /**
     *  Llistat del resultat de la consulta que ha creat l’instancia de la classe en el format de Map<Autor,Set<Títols>>.
     */
    private List<String> resultat_;
    /**
     * creadora que inicialitza els atributs de la classe amb la informacio de la consulta que genera la instancia.
     * @param resultat resultat de la consulta
     * @param tipusConsulta tipus de consulta
     * @param parametres parametres que de la consulta que s'ha fet
     */
    public ResultatContingut(List<String> resultat, String tipusConsulta, List<String> parametres) {
        resultat_=resultat;
        parametres_=parametres;
        tipusConsulta_=tipusConsulta;
    }


    public void ordenarAlfabeticament() {
        ;
    }

    @Override
    public void ordenarInversament() {
        ;
    }
    /**
     * getter del resultat de la consulta
     * @return resultat_
     */
    public List<String> getResultat(){
        return resultat_;
    }
    /**
     * getter dels parametres de la consulta
     * @return parametres_
     */
    public List<String> getParametres() {
        return parametres_;
    }
    /**
     * getter del tipus de consulta
     * @return tipusConsulta_
     */
    public String getTipusConsulta() {
        return tipusConsulta_;
    }
}
