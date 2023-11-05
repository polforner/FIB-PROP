package domini.Clases;

import domini.controladors.CtrlDocument;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Classe que defineix una consulta booleana
 * La consulta rep una expressio booleana i retorna els documents tals que continguin alguna frase que satisfan la expressio
 * @author pol.forner
 */
public class Booleana implements Consulta{
//PART PUBLICA
    //funcions publiques
    /**
     * Constructora amb parametre
     * Crea una Consulta Boolena inicialitzantla utilitzant el parametetre expressio
     * @param expressio expressio booleana
     */
    public Booleana(ExprBooleana expressio) throws FileNotFoundException{
        expressio_ = expressio.getExpresio();
        arbreExpr_ = expressio.getArbreExpressio();
        ctrlDocument_ = CtrlDocument.getInstance();
    }

    /**
     * Obte un ResultatConsulta que conte els documents tals que contenen alguna frase que compleix l'expressio booleana
     * @return Retorna una instancia de ResultatConsulta que conte el resultat de evalua l'expressio booleana
     */
    public ResultatConsultaPairs executar() throws FileNotFoundException{
        Map<Integer, Set<Integer>> documentsFrase = this.executarRec();
        Map<String, Set<String>> autorTitol = new HashMap<>();
        for (Integer i : documentsFrase.keySet()) {
            Document doc = ctrlDocument_.getDocPerIndex(i);
            String autor = doc.getAutor();
            String titol = doc.getTitol();
            Set<String> titols = new HashSet<>();
            if (autorTitol.containsKey(autor)) {
                titols.add(titol);
                titols.addAll(autorTitol.get(autor));
            }
            else titols.add(titol);
            autorTitol.put(autor, titols);
        }
        List<String> expressio = new ArrayList<>();
        expressio.add(expressio_);
        ResultatConsultaPairs resultat = new ResultatConsultaPairs(autorTitol, "Booleana", expressio);
        return resultat;
        //ResultatConsulta resultat = new ResultatConsultaPairs(autorTitol, "Booleana", new List<String> (expressio_));
    }

//PART PRIVADA
    //atributs privats
    /**
     * Arbre Binary que conte l'expressio boolena
     */
    private BinTree arbreExpr_;
    /**
     * Expressio booleana en format text
     */
    private String expressio_;
    /**
     * Controlador de documents
     */
    private CtrlDocument ctrlDocument_;

    //Funcions privades
    /**
     * Construtora a partir de l'expressio en arbre binari
     * @param arbre BinTree que conte l'expressio booleana
    */
    private Booleana(BinTree arbre) throws FileNotFoundException{
        arbreExpr_ = arbre;
        ctrlDocument_ = CtrlDocument.getInstance();
    }

    /**
     * Funcio de executar recursiva
     * @return retorna el conjunt de documents que compreixen l'expressio booleana de this
     * @throws FileNotFoundException
     */
    public Map<Integer, Set<Integer>> executarRec() throws FileNotFoundException{
        Map<Integer, Set<Integer>> resultat = new HashMap<>();
        String valor = arbreExpr_.getValor();
        if (!(valor.equals("&") | valor.equals("|"))) {
            if (valor.charAt(0) == '\"') {
                String frase = valor.substring(1,valor.length() - 1);
                Map<String, Map<Integer, Set<Integer>>> aux = ctrlDocument_.getDocsFrasesFrase(frase);
                Map<Integer, Set<Integer>> aux2 = new HashMap<>();
                Boolean first = true;
                for (Map<Integer, Set<Integer>> map: aux.values()) {
                    if (first) {aux2.putAll(map);first = false;}
                    else aux2 = intersecarMaps(aux2, map);
                }
                for (Map.Entry<Integer, Set<Integer>> entry: aux2.entrySet()) {
                    int docId = entry.getKey();
                    for(Integer entry2 : entry.getValue()) {
                        if (!ctrlDocument_.getDocPerIndex(docId).conteFrase(frase, entry2)) entry.getValue().remove(entry2);
                    }
                    if (aux2.get(docId).isEmpty()) aux2.remove(docId);
                }
                resultat = aux2;
            }
            else {
                resultat = ctrlDocument_.getDocsFrasesParaula(valor);
            }
            if (arbreExpr_.esNegat()) resultat = ctrlDocument_.getComplement(resultat);
        }
        else {
            Booleana esquerra = new Booleana(arbreExpr_.getEsquerra());
            Map<Integer, Set<Integer>> resEsquerra = esquerra.executarRec();
            Booleana dret = new Booleana(arbreExpr_.getDret());
            Map<Integer, Set<Integer>> resDret = dret.executarRec();

            if (valor.equals("&")) resultat = intersecarMaps(resEsquerra, resDret);
            else resultat = unirMaps(resEsquerra, resDret);
        }
        return resultat;
    }

    /**
     * Fa la unió de 2 Map<Integer, Set<Integer>>. Retorna un Map<Integer, Set<Integer>> amb keys tal que o bé esta a nomes un dels maps
     * o esta a tots dos maps, on es fa la unio dels seus value.
     * @param x Primer map
     * @param y Segon map
     * @return Retorna la unio del primer i del segon map
     */
    private static Map<Integer, Set<Integer>> unirMaps(Map<Integer, Set<Integer>> x, Map<Integer, Set<Integer>> y) {
        Map<Integer, Set<Integer>> resultat = new HashMap<>();
        resultat.putAll(x);
        for (Map.Entry<Integer, Set<Integer>> entrada : y.entrySet()) {
            Integer key = entrada.getKey();
            if (!resultat.containsKey(key)) resultat.put(key, entrada.getValue());
            else {
                Set<Integer> union = unirSets(resultat.get(key), y.get(key));
                resultat.put(key, union);
            }
        }
        return resultat;
    }

    /**
     * Fa la unió de 2 Set<Integer>. Retorna un Set<Integer> amb tots els valors de tots dos Sets.
     * @param x Primer set
     * @param y Segon set
     * @return Retorna la unio del primer i del segon set
     */
    private static Set<Integer> unirSets (Set<Integer> x, Set<Integer> y) {
        Set<Integer> resultat = new HashSet<>();
        resultat.addAll(x);
        resultat.addAll(y);
        return resultat;
    }

    /**
     * Fa l'intersecció de 2 Map<Integer, Set<Integer>>. Retorna un Map<Integer, Set<Integer>> amb keys tal que es trobin als dos map
     * i la interseccio dels seus valors no es buida.
     * @param x Primer map
     * @param y Segon map
     * @return Retorna l'intersecció del primer i del segon map
     */
    private static Map<Integer, Set<Integer>> intersecarMaps(Map<Integer, Set<Integer>> x, Map<Integer, Set<Integer>> y) {
        Map<Integer, Set<Integer>> resultat = new HashMap<>();
        resultat.putAll(x);
        for (Map.Entry<Integer, Set<Integer>> entrada : x.entrySet()) {
            Integer key = entrada.getKey();
            if (!y.containsKey(key)) resultat.remove(key);
            else {
                Set<Integer> interseccio = intersecarSets(resultat.get(key), y.get(key));
                if(interseccio.isEmpty()) resultat.remove(key);
                else resultat.put(key, interseccio);
            }
        }
        return resultat;
    }

    /**
     * Fa l'intersecció de 2 Set<Integer>. Retorna un Set<Integer> amb els valors que surten a tots dos Sets.
     * @param x Primer set
     * @param y Segon set
     * @return Retorna l'intersecció del primer i del segon map
     */
    private static Set<Integer> intersecarSets (Set<Integer> x, Set<Integer> y) {
        Set<Integer> resultat = new HashSet<>();
        resultat.addAll(x);
        resultat.retainAll(y);
        return resultat;
    }
}
