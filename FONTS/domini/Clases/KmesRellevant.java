package domini.Clases;

import domini.controladors.CtrlDocument;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Classe que defineix la consulta de k documents mes rellevants
 * La consulta reb una llista de paraules i un nombre k retornan k documents mes rellevants amb les paraules
 * @author Zheshuo.Lin
 */
public class KmesRellevant implements Consulta {
    private final List<String> query;

    private final int k;

    /**
     * creacio d'una consulta KmesRellevant amb un query de paraules i el k
     * @param k nombre de documents que retornem
     * @param query les paraules que els documents retornats han de ser mes rellevants amb aquests.
     */
    public KmesRellevant(int k,  List<String> query)  {
        this.query=query;
        this.k=k;
    }

    /**
     *  Crea una instancia de ResultatConsulta amb els  autors i titols de k documents mes rellevant amb query
     * @return retorna la instancia de ResultatConsulta creada en executar-se la consulta
     */
    public ResultatConsulta executar() throws FileNotFoundException,RuntimeException {
        CtrlDocument cd =CtrlDocument.getInstance();
        Map<String,Set<String>>resultat = new HashMap<>();
        Map<Integer,Document> identificadors=cd.getIdentificadors();
        Map<Integer,Double> documentAmbParaules=new HashMap<>();
        Map<Integer,Map<String,Double>> tfidfs=cd.getVectorizacions();
        if (k>identificadors.size()) throw new RuntimeException("Error: nombre de documents mes petit que k");
        for (String s : this.query) {
                Set<Integer> docsP = cd.getDocsParaula(s);

                for (int id : docsP) {
                    if (tfidfs.get(id).containsKey(s))  {
                        if (documentAmbParaules.containsKey(id)) {
                            documentAmbParaules.replace(id, documentAmbParaules.get(id) + tfidfs.get(id).get(s));
                        } else {
                            documentAmbParaules.put(id, tfidfs.get(id).get(s));

                        }
                    }
                }


        }

        List<Map.Entry<Integer,Double>> res=new ArrayList<>(documentAmbParaules.entrySet());

        Collections.sort(res,new Comparator<>() {

            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Collections.reverse(res);
        if (res.size() > k) {
             res = res.subList(0, this.k);
        }
            for (Map.Entry<Integer, Double> doc : res) {
                Document d = identificadors.get(doc.getKey());
                if (resultat.containsKey(d.getAutor())) {
                    resultat.get(d.getAutor()).add(d.getTitol());
                } else {
                    Set<String> value = new HashSet<>();
                    value.add(d.getTitol());
                    resultat.put(d.getAutor(), value);
                }

            }


        List<String> parametre=new ArrayList<>();
        parametre.add(query.toString());
        parametre.add(String.valueOf(k));
        return new ResultatConsultaPairs(resultat,"KmesRellevant",parametre);
    }

    public List<String> getQuery() {
        return this.query;
    }

    public int getK() {
        return this.k;
    }
}
