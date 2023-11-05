package domini.Clases;

import java.util.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Representa el resultat d’una consulta que retorna parells de parametres (Strings),
 * com son les consultes kmesSemblant, kmesRellevant i booleana. S’encarrega de les ordenacions dels resultats
 */
public class ResultatConsultaPairs implements ResultatConsulta {
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
    private Map<String,Set<String>> resultat_;

    /**
     *  creadora que inicialitza els atributs de la classe amb la informacio de la consulta que genera la instancia.
     * @param resultat resultat de la consulta
     * @param tipusConsulta tipus de consulta
     * @param parametres parametres que de la consulta que s'ha fet
     */
    public ResultatConsultaPairs(Map<String,Set<String>> resultat, String tipusConsulta, List<String> parametres) {
        resultat_ = resultat;
        tipusConsulta_ = tipusConsulta;
        parametres_ = parametres;
    }

    /**
     * Ordena el resultat de la consulta alfabeticament per autor.
     */
    public void ordenarAlfabeticament (){
        TreeMap<String,Set<String>> resultatOrdenat = new TreeMap<String,Set<String>>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null || o2==null) {
                    return 0;
                }
                return o1.compareTo(o2);
            }
        });
        for (Map.Entry<String,Set<String>> e: resultat_.entrySet()) {
            Set<String> setOrdenat = new TreeSet<String>(e.getValue());
            resultatOrdenat.put(e.getKey(), setOrdenat);
        }
        System.out.println(resultatOrdenat);
        resultat_=resultatOrdenat;
    }

    /**
     * Ordena el resultat de la consulta en ordre inversament alfabetic (Z-A), per autor.
     */
    public void ordenarInversament() {
        TreeMap<String,Set<String>> resultatOrdenat = new TreeMap<String,Set<String>>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null || o2==null) {
                    return 0;
                }
                return o2.compareTo(o1);
            }
        });
        for (Map.Entry<String,Set<String>> e: resultat_.entrySet()) {

                Set<String> Titols = new TreeSet<String>(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        if (o1 == null || o2==null) {
                            return 0;
                        }
                        return o2.compareTo(o1);
                    }
                });
                Titols.addAll(e.getValue());

                resultatOrdenat.put(e.getKey(),Titols);

        }
        System.out.println(resultatOrdenat);

        resultat_=resultatOrdenat;
    }
    /**
     * getter del resultat de la consulta
     * @return resultat_
     */
    public Map<String,Set<String>> getResultat() {
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

