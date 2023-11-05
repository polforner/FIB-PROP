package domini.Clases;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * La classe per fer operacions entre dos conjunts de parelles de strings.
 * @author Zheshuo.lin
 */
public class OperacionResultatPair implements OperacionResultat<Map<String,Set<String>>>{

    /**
     * Resultat de la primera consulta
     */
    private final Map<String, Set<String>> resultat1;
    /**
     * Resultat de la segona consulta
     */
    private final Map<String,Set<String>> resultat2;

    /**
     * Crea una instancia de OperacionResultatNoms amb dos resultats de conjunts de strings.
     * @param r1 La resultat de una consulta
     * @param r2 La resultat de una consulta
     */
    public OperacionResultatPair(Map<String,Set<String>>r1,Map<String,Set<String>> r2) {
        resultat1=r1;
        resultat2=r2;
    }

    /**
     * Operacio per intersecar 2 resultats
     * @return
     */
    public Map<String,Set<String>> interseccio() {
        Map<String,Set<String>> resultatOperacion=new HashMap<>();
        for (String autor : resultat1.keySet()) {
            if (resultat2.containsKey(autor)) {
                Set<String> titolsiguals=new HashSet<>();
                for (String titol : resultat1.get(autor)) {
                    if (resultat2.get(autor).contains(titol)) {
                        titolsiguals.add(titol);
                    }
                }
                if (!titolsiguals.isEmpty()) {
                    resultatOperacion.put(autor,titolsiguals);
                }
            }
        }
        return resultatOperacion;
    }

    /**
     * Operacio per unir 2 resultats
     * @return
     */
    public Map<String,Set<String>> unio() {
        Map<String,Set<String>> resultatOperacion=new HashMap<>();
        Set<String> autors=new HashSet<>();
        Set<String> autors1=resultat1.keySet();
        Set<String> autors2=resultat2.keySet();
        autors.addAll(autors1);
        autors.addAll(autors2);
        for (String autor : autors) {
            Set<String> titols=new HashSet<>();
            if (resultat2.containsKey(autor) && resultat1.containsKey(autor)) {
                titols.addAll(resultat1.get(autor));
                titols.addAll(resultat2.get(autor));
            }

            else if (resultat1.containsKey(autor)) {
                titols.addAll(resultat1.get(autor));
            }
            else if (resultat2.containsKey(autor)) {
                titols.addAll(resultat2.get(autor));
            }
            resultatOperacion.put(autor,titols);

        }

        return resultatOperacion;
    }

    /**
     * Operacio per fer la diferencia entre el primer i el segon resultat
     * @return
     */
    public Map<String,Set<String>> diferencia() {
        Map<String,Set<String>> resultatOperacion=new HashMap<>();
        for (String autor : resultat1.keySet()) {
            Set<String> titols = new HashSet<>(resultat1.get(autor));
            if (resultat2.containsKey(autor)){
                titols.removeAll(resultat2.get(autor));
            }
            if (!titols.isEmpty()) {
                resultatOperacion.put(autor,titols);
            }



        }
        return resultatOperacion;
    }

    /**
     * Operacio per fer la diferencia simetrica entre 2 resultats
     * @return
     */
    public Map<String,Set<String>> diferenciaSimetrica() {
        Map<String,Set<String>> resultatOperacion=new HashMap<>();
        Set<String> autors=new HashSet<>();
        Set<String> autors1=resultat1.keySet();
        Set<String> autors2=resultat2.keySet();
        autors.addAll(autors1);
        autors.addAll(autors2);
        for (String autor : autors) {
            Set<String> titols=new HashSet<>();
            if (resultat2.containsKey(autor) && resultat1.containsKey(autor)) {
                titols.addAll(resultat1.get(autor));
                titols.addAll(resultat2.get(autor));
            }

            else if (resultat1.containsKey(autor)) {
                titols.addAll(resultat1.get(autor));
            }
            else if (resultat2.containsKey(autor)) {
                titols.addAll(resultat2.get(autor));
            }
            resultatOperacion.put(autor,titols);

        }
        for (String autor : resultat1.keySet()) {
            if (resultat2.containsKey(autor)) {
                for (String titol : resultat1.get(autor)) {
                    if (resultat2.get(autor).contains(titol)) {
                        resultatOperacion.get(autor).remove(titol);
                    }
                }
                if(resultatOperacion.get(autor).isEmpty()) resultatOperacion.remove(autor);
            }
        }
        return resultatOperacion;
    }
}
