package domini.Clases;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Classe que defineix l'objecte Document
 * Un document esta format per un autor, un titol, el seu contingut i tambe un identificador numeric que l'identifica
 * de la mateixa manera que ho fan l'autor i el titol.
 * @author bernat.homs
 */
public class Document implements Serializable {
    /**
     * Relacio d'assosiacio amb DocumentVectoritzat
     */
    private DocumentVectoritzat vector_;

    /**
     * Identificador numeric de document
     */
    private final int docID_;
    /**
     * Autor del document
     */
    private String autor_;
    /**
     * Titol del document
     */
    private String titol_;
    /**
     * Contingut del document
     */
    private List<String> contingut_;

    /**
     * Paraules clau del document amb el nombre d'aparicions de la paraula dins del document
     */
    private Map<String, Integer> keyWords_;

    /**
     * Cosntructora de Document amb autor, titol i contingut
     *
     * @param autor     autor autor del nou document
     * @param titol     titol del nou document
     * @param contingut contingut del nou document
     */
    public Document(String autor, String titol, List<String> contingut, Integer docID) {
        autor_ = autor;
        titol_ = titol;
        contingut_ = contingut;
        docID_ = docID;
    }

    /**
     * Getter del identificador numeric del document
     *
     * @return identificador numeric del document
     */
    public int getDocID() {
        return docID_;
    }

    /**
     * Getter de l'autor del document
     *
     * @return autor del document
     */
    public String getAutor() {
        return autor_;
    }

    /**
     * Setter de l'autor del document
     *
     * @param autor nou autor del document
     */
    public void setAutor(String autor) {
        autor_ = autor;
    }

    /**
     * Getter del titol del document
     *
     * @return titol del document
     */
    public String getTitol() {
        return titol_;
    }

    /**
     * Setter del titol del document
     *
     * @param titol nou titol del document
     */
    public void setTitol(String titol) {
        titol_ = titol;
    }

    /**
     * Getter del contingut del document
     *
     * @return contingut del document
     */
    public List<String> getContingut() {
        return contingut_;
    }

    /**
     * Setter del contingut del document
     *
     * @param contingut nou contingut del document
     */
    public void setContingut(List<String> contingut) {
        contingut_ = contingut;
    }

    /**
     * Getter de les paraules clau del document
     *
     * @return retorna les paraules clau del document
     */
    public Map<String, Integer> getKeyWords() {
        return keyWords_;
    }

    /**
     * Setter de les paraules clau del document
     *
     * @param keyWords paraules clau del document
     */
    public void setKeyWords(Map<String, Integer> keyWords) {
        keyWords_ = keyWords;
    }

    /**
     * retorna el vector del document
     * @return vector del document
     */
    public DocumentVectoritzat getVectorDocument() {
        return vector_;
    }

    /**
     * crea el vector del document i el retorna
     * @return vector del document
     */
    public DocumentVectoritzat vectoritzarDocument() {
        vector_ = new DocumentVectoritzat();
        return vector_;
    }

    /**
     * metode per comprovar si un document conte una frase dins d'una frase particular
     *
     * @param subFrase subfrase que es vol mirar si conte la frase
     * @param fraseID  identificador de la frase en la que es vol consultar
     * @return retorna el resultat de la comprovacio
     */
    public boolean conteFrase(String subFrase, Integer fraseID) {
        String temp = contingut_.get(fraseID).toLowerCase();
        subFrase = subFrase.toLowerCase();
        return temp.contains(subFrase);
    }

    /**
     * esborra la vectoritzacio del document
     */
    public void esborraVectorDocument() {
        vector_ = null;
    }
}
