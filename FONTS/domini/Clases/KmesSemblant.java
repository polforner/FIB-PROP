package domini.Clases;

import domini.controladors.CtrlDocument;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map;


/**
 * Classe que defineix la consulta de k documents mes semblants
 * La consulta reb un titol i un autor d'un document i un nombre k retornan k documents mes semblants a document
 * @author Zheshuo.Lin
 */
public class KmesSemblant implements Consulta{

    private final String titol;
    private final String autor;

    private final int  k;

    /**
     * creacio d'una consulta KmesSemblant amb el identificador del document i el k
     * @param titol titol del document
     * @param autor autor del document
     * @param k nombre de documents que retornem
     */
    public KmesSemblant( String titol, String autor, int k)  {

        this.titol=titol;
        this.autor=autor;
        this.k=k;
    }

    /**
     *  Crea una instancia de ResultatConsulta amb els  autors i titols de k documents mes semblants a document identificat per titol i autor.
     * @return retorna la instancia de ResultatConsulta creada en executar-se la consulta
     */
    public ResultatConsulta executar() throws FileNotFoundException,RuntimeException {

        CtrlDocument cd=CtrlDocument.getInstance();
        Map<String, Map<String,Integer>> titolsautor=cd.getTitolsAutor();
        int id=titolsautor.get(autor).get(titol);
        Map<Integer,Document> identificadors=cd.getIdentificadors();
        if (this.k>(identificadors.size()-1)) throw new RuntimeException("Error: nombre de documents mes petit que k");
        // el document que volem busca k documents mes semblants a aquest
        List<Map.Entry<String,String>> resultat=new ArrayList<>();
        double minsim=-100;
        List<Double> sims=new ArrayList<>();
        int minpos=0;

        Map<Integer,Map<String,Double>> tfidfs=cd.getVectorizacions();
        Map<String,Double> tfidf1=tfidfs.get(id);

        for (int ID : identificadors.keySet()) {
            if (ID==id) continue;
            if (k==0) break;
            Map<String,Double> tfidf2=tfidfs.get(ID);

            double sim=calcularSimilitud(tfidf1,tfidf2);
            if (sim > minsim) {

                Document d=identificadors.get(ID);
                Map.Entry<String, String> res = new AbstractMap.SimpleEntry<>(d.getAutor(), d.getTitol());
                // cas de tenim menys de k documents.
                if (resultat.size() < (this.k - 1)) {
                    sims.add(sim);
                    resultat.add(res);
                }
                //cas de tenim k documents

                else if (resultat.size()==this.k) {
                    resultat.set(minpos,res);
                    for (int j=0; j<sims.size();++j) {
                        if (sims.get(minpos)>sims.get(j)) {
                            minpos=j;
                        }
                    }
                    minsim=sims.get(minpos);

                }
                //cas de despres de inserir aquest document tenim k documents
                else if (resultat.size()==(this.k-1)) {
                    sims.add(sim);
                    resultat.add(res);
                    for (int j=0; j<sims.size(); ++j) {
                        if (sims.get(minpos)>sims.get(j)) {
                            minpos=j;

                        }
                    }

                    minsim=sims.get(minpos);
                }



            }
        }

        Map<String,Set<String>> res=new HashMap<>();
        for(Map.Entry<String,String> pair : resultat) {
            if (res.containsKey(pair.getKey())) {
                res.get(pair.getKey()).add(pair.getValue());
            }
            else {
                Set<String> value=new HashSet<>();
                value.add(pair.getValue());
                res.put(pair.getKey(), value);
            }
        }
        List<String> parametre=new ArrayList<>();
        parametre.add(titol);
        parametre.add(autor);
        parametre.add(String.valueOf(k));
        return  (new ResultatConsultaPairs(res,"KmesSemblant",parametre));


    }

    /**
     * Calcula similitud de dos documents mitjançant  teorema de cosinus
     * @param tfidf1 tfidf de primer document
     * @param tfidf2 tfidf de segon document
     * @return el similitud de dos documents
     */
    private double calcularSimilitud(Map<String,Double> tfidf1, Map<String,Double> tfidf2) {
        double sim=0;
        double norma=calculaNorma(tfidf1)*calculaNorma(tfidf2);
        for (String s : tfidf1.keySet()) {
            if (tfidf2.containsKey(s)) {
                sim+=tfidf1.get(s)*tfidf2.get(s);
            }
        }
        sim/=norma;


        return sim;
    }

    /**
     * calcula la norma d'un vector
     * @param p el vector per calcular la norma
     * @return la norma del vector p
     */
    private Double calculaNorma(Map<String,Double> p) {
        double norma=0;
        for (Double v : p.values()) {
            norma+=v*v;
        }
        norma=Math.sqrt(norma);


        return norma;
    }

    public String getTitol() {
        return titol;
    }

    public String getAutor() {
        return autor;
    }

    public int getK() {
        return  k;
    }


}
