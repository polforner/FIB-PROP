package domini.Clases;

import java.util.HashSet;
import java.util.Set;

/**
 * La classe per fer operacions entre dos conjunts de Strings.
 * @author Zheshuo.lin
 */
public class OperacionResultatNoms implements OperacionResultat<Set<String>>{

    private final Set<String> resultat1;
    private final Set<String> resultat2;

    /**
     * Crea una instancia de OperacionResultatNoms amb dos resultats de conjunts de strings.
     * @param r1 La resultat de una consulta
     * @param r2 La resultat de una consulta
     */
    public OperacionResultatNoms(Set<String> r1,Set<String> r2) {
        resultat1=r1;
        resultat2=r2;
    }
    /**
     * Operacio per intersecar 2 resultats
     * @return
     */
    public Set<String> interseccio() {
        Set<String> resultatOperacio=new HashSet<>();
        for (String s : resultat1) {
            if (resultat2.contains(s)) {
                resultatOperacio.add(s);
            }
        }
        return resultatOperacio;
    }

    /**
     * Operacio per unir 2 resultats
     * @return
     */
    public  Set<String> unio() {
        Set<String> resultatOperacio=new HashSet<>();
        resultatOperacio.addAll(resultat1);
        resultatOperacio.addAll(resultat2);
        return resultatOperacio;
    }

    /**
     * Operacio per fer la diferencia entre el primer i el segon resultat
     * @return
     */
    public  Set<String> diferencia() {
        Set<String> resultatOperacio=new HashSet<>();
        for (String s : resultat1) {
            if (!resultat2.contains(s)) {
                resultatOperacio.add(s);
            }
        }
        return resultatOperacio;
    }

    /**
     * Operacio per fer la diferencia simetrica entre 2 resultats
     * @return
     */
    public  Set<String> diferenciaSimetrica() {
        Set<String> resultatOperacio=new HashSet<>();
        resultatOperacio.addAll(resultat2);
        resultatOperacio.addAll(resultat1);
        for (String s : resultat1) {
            if (resultat2.contains(s)) {
                resultatOperacio.remove(s);
            }
        }
        return resultatOperacio;
    }
}
