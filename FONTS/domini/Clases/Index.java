package domini.Clases;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;
import java.io.File;
import java.lang.String;

/**
 * Classe que defineix l'objecte Index
 * L'Index esta format per el index invers el sistema i conte tot el necessari per fer funcionar l'espai vectorial
 * dins del sistema
 * @author bernat.homs
 */
public class Index implements Serializable{

    /**
     * atribut de classe que representa les stopwords de l'index
     */
    private static List<String> stopWords_;

    /**
     * atribut de classe que representa simbols de puntuacio que volem descartar del index
     */
    private static List<Character> simbolsPuntuacio_;

    /**
     * index invertit del sistema estructurat de la seguent manera: paraula, documents amb paraula, frases del document amb paraula
     */
    private final Map<String, Map<Integer, Set<Integer>>> invertedIndex_;

    /**
     * constructora de la classe
     */
    public Index() throws FileNotFoundException {
        invertedIndex_ = new TreeMap<>();
        stopWords_ = new ArrayList<>();
        File fitxer = new File("./stopwords.txt");
        Scanner scan = new Scanner(fitxer);

        while (scan.hasNextLine()) {
            String stopWord = scan.nextLine();
            stopWords_.add(stopWord);
        }
        scan.close();
        simbolsPuntuacio_ = new ArrayList<>(Arrays.asList(',', '.', ';', ':', '?', '/', '!'));
    }

    /**
     * Getter del index invertit
     * @return retorna el index invertit de l'aplicacio
     */
    public Map<String, Map<Integer, Set<Integer>>> getInvertedIndex_() {
        return invertedIndex_;
    }

    /**
     * metode per afegir un document al index i retornar les seves keyWords
     * @param contingutDocument contingut del document que s'afegeix al index
     * @param docID             identificador numeric del document
     * @return retorna les keywords del document que s'ha afegit al index
     */
    public Map<String, Integer> afegirDocument(List<String> contingutDocument, Integer docID) {
        Map<String, Integer> keyWords = new TreeMap<>();
        for (int i = 0; i < contingutDocument.size(); ++i) {
            String frase = contingutDocument.get(i);
            List<String> keyWordsFrase = getKeyWordsFrase(frase);
            for (String s : keyWordsFrase) {
                actualitzaIndex(s, i, docID);
                if (!keyWords.containsKey(s)) {
                    borraFrasesParaulaDocument(s, docID);
                    actualitzaIndex(s, i, docID);
                    keyWords.put(s, 1);
                } else {
                    int freq = keyWords.get(s);
                    freq++;
                    keyWords.put(s, freq);
                }
            }
        }
        return keyWords;
    }

    /**
     * retorna el nombre de paraules que conte un document
     * @param contingutDocument contingut del document que es vol consultar
     * @return retorna el nombre de paraules que conte el document
     */
    public Integer nombreParaulesDocument(List<String> contingutDocument) {
        Integer nParaules = 0;
        for (String frase : contingutDocument) {
            while (!frase.isEmpty()) {
                int indexOfSpace = frase.indexOf(" ");
                ++nParaules;
                if (indexOfSpace != -1) {
                    frase = frase.substring(indexOfSpace);
                    frase = frase.stripLeading();
                } else frase = "";
            }
        }
        return nParaules;
    }

    /**
     * metode privat per actualitzar l'index amb les diferents keywords que vagin entrant al sistema
     * @param paraula paraula que entra al sistema
     * @param fraseID identificador de la frase que conte la paraula
     * @param docID   identificador numeric del document que conte la paraula
     */
    private void actualitzaIndex(String paraula, Integer fraseID, Integer docID) {
        if (invertedIndex_.containsKey(paraula)) {
            if (invertedIndex_.get(paraula).containsKey(docID)) {
                if (!invertedIndex_.get(paraula).get(docID).contains(fraseID)) invertedIndex_.get(paraula).get(docID).add(fraseID);
            } else {
                Set<Integer> fraseIDs = new TreeSet<>();
                fraseIDs.add(fraseID);
                invertedIndex_.get(paraula).put(docID, fraseIDs);
            }
        } else {
            Set<Integer> fraseIDs = new TreeSet<>();
            fraseIDs.add(fraseID);
            Map<Integer, Set<Integer>> docIDs_amb_fraseIDs = new TreeMap<>();
            docIDs_amb_fraseIDs.put(docID, fraseIDs);
            invertedIndex_.put(paraula, docIDs_amb_fraseIDs);
        }
    }

    /**
     * metode que retorna les paraules del corpus que no apareixen en un document
     *
     * @param docID identificador numeric del document que es vol consultar
     * @return retorna el conjunt de paraules que el document no conte pero estan al corpus
     */
    public List<String> getParaulesRestants(Integer docID) {
        List<String> paraulesRestants = new ArrayList<>();
        for (Map.Entry<String, Map<Integer, Set<Integer>>> paraula : invertedIndex_.entrySet()) {
            if (!paraula.getValue().containsKey(docID)) paraulesRestants.add(paraula.getKey());
        }

        return paraulesRestants;
    }

    /**
     * doanada una paraula retorna el llistat de documents que contenen la paraula i per cada document les frases que la contenen
     * @param paraula paraula sobre la que es vol consultar
     * @return retorna el llistat de documents que contenen la paraula pasada per parametre
     */
    public Map<Integer, Set<Integer>> getDocsFrasesParaula(String paraula) {
        if (invertedIndex_.containsKey(paraula)) return invertedIndex_.get(paraula);
        else return new HashMap<>();
    }

    /**
     * retorna el llistat de documents que contenen una paraula
     * @param paraula paraula sobre la que es vol consultar els documents del sistema que la contenen
     * @return retorna el llistat de documents que contenen la paraula pasada per parametre
     */
    public Set<Integer> getDocsParaula(String paraula) {
        if (invertedIndex_.containsKey(paraula)) {
            return invertedIndex_.get(paraula).keySet();
        }
        else {
            return new HashSet<>();
        }
    }

    /**
     * retorna el llistat de keywords que conte una frase
     * @param frase frase sobre la que es volen obtenir les keywords
     * @return retorna el llistat de keywords de la frase
     */
    private List<String> getKeyWordsFrase(String frase) {
        List<String> paraules = new ArrayList<>();
        while (!frase.isEmpty()) {
            int indexOfSpace = frase.indexOf(" ");
            String paraula;
            if (indexOfSpace == -1) paraula = frase;
            else paraula = frase.substring(0, indexOfSpace);

            paraula = paraula.toLowerCase();
            for (Character character : simbolsPuntuacio_) {
                paraula = paraula.replace(character, ' ');
            }
            paraula = paraula.replaceAll("\\s", "");

            if (!stopWords_.contains(paraula)) paraules.add(paraula);
            if (indexOfSpace != -1) {
                frase = frase.substring(indexOfSpace);
                frase = frase.stripLeading();
            } else frase = "";
        }
        return paraules;
    }

    /**
     * retorna les keyWords passades com a parametre amb corresponent nombre de documents que la contenen dins del sistema
     * @param keyWords keyWords d'un document del sistema
     * @return keyWords amb el nombre de documents on apareixen cadascuna
     */
    public Map<String, Integer> getNdocsKeyWords(Map<String, Integer> keyWords) {
        Map<String, Integer> nDocs = new HashMap<>();
        for (Map.Entry<String, Integer> keyWord : keyWords.entrySet()) {
            nDocs.put(keyWord.getKey(), invertedIndex_.get(keyWord.getKey()).size());
        }
        return nDocs;
    }

    /**
     * retorna les entrades de l'index de les keywords que conte la frase, es a dir, per cada keyword, el llistat de documents que la conte i les frases dins del document que la contenen
     * @param frase frase sobre la que es vol consultar les keywords
     * @return retorna les keywords de la frase junt amb el llistat de documents que la contenen i les frases dins de cada document que la contenen
     */
    public Map<String, Map<Integer, Set<Integer>>> getDocsFrasesFrase(String frase) {
        List<String> keyWordsFrase = getKeyWordsFrase(frase);
        Map<String, Map<Integer, Set<Integer>>> entradesIndexKeyWords = new HashMap<>();
        for (String currentWord : keyWordsFrase) {
            if (invertedIndex_.containsKey(currentWord)) entradesIndexKeyWords.put(currentWord, invertedIndex_.get(currentWord));
            else {
                Map<Integer, Set<Integer>> emptyEntry = new HashMap<>();
                entradesIndexKeyWords.put(currentWord, emptyEntry);
            }
        }
        return entradesIndexKeyWords;
    }

    /**
     * actualitza l'index a partir d'una llista de keywords eliminades d'un document amb identificador numeric docID
     * @param keyWordsEliminades llista de keywords eliminades del document amb identificador numeric docID
     * @param docID identificador numeric del document sobre el qual s'han esborrat keywords
     */
    public void actualitzaIndex (List<String> keyWordsEliminades, Integer docID) {
        for (String currentWord : keyWordsEliminades) {
            invertedIndex_.get(currentWord).remove(docID);
        }
    }

    /**
     * esborra les frases en les que apareix la keyword paraula dins d'un document amb identificador numeric docID
     * @param paraula keyword sobre la que s'han d'eliminar les frases en les que apareix dins d'un document
     * @param docID docID en el que es troba la keyword paraula
     */
    private void borraFrasesParaulaDocument (String paraula, Integer docID) {
        if (invertedIndex_.containsKey(paraula)) {
            if (invertedIndex_.get(paraula).containsKey(docID)) invertedIndex_.get(paraula).get(docID).clear();
        }
    }
}
