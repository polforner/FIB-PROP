package domini.Clases;

import java.util.*;

/**
 * Representa el resultat d’una consulta que nomes retorna parametres individuals (Strings),
 * com son les consultes TitolsAutor o PrefixosAutors. S’encarrega de les ordenacions dels resultats
 */
public class ResultatConsultaNoms implements ResultatConsulta {

    /**
     * Indica el tipus de consulta que ha generat la instancia de resultatConsulta
     */
    private String tipusConsulta_;

    /**
     * Parametres de la consulta
     */
    private List<String> parametres_;

    /**
     * Resultat de la consulta
     */
    private Set<String> resultat_;
    /**
     * Creadora de la classe
     * @param resultat resultat de la consulta 
     * @param tipusConsulta tipus de consulta ()
     * @param parametres parametres de la consulta
     */
    public ResultatConsultaNoms(Set<String> resultat, String tipusConsulta, List<String> parametres) {
        resultat_ = resultat;
        tipusConsulta_= tipusConsulta;
        parametres_ = parametres;
    }

    /**
     * Ordena el resultat de la consulta alfabeticament.
     */
    public void ordenarAlfabeticament () {
        resultat_ = new TreeSet<String>(resultat_);
    }

    /**
     * Ordena el resultat de la consulta en ordre inversament alfabetic (Z-A).
     */
    public void ordenarInversament() {
        TreeSet<String> resultatOrdenat = new TreeSet<String>(resultat_);
        List list = new ArrayList(resultatOrdenat);
        Collections.sort(list, Collections.reverseOrder());
        Set resultSet = new LinkedHashSet(list);
        resultat_=resultSet;
    }

    /**
     * getter del resultat de la consulta
     * @return resultat_
     */
    public Set<String> getResultat() {
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