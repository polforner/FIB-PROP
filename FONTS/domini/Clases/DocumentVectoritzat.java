package domini.Clases;

import java.util.*;

/**
 * Classe que defineix l'objecte DocumentVectoritzat.
 * Un document vectoritzat esta definit per un vector que el representa en l'espai vectorial del sistema
 * @author bernat.homs
 */
public class DocumentVectoritzat {
	
	/**
	 * vector del document
	 */
	private Map<String, Double> vectorDocument_;
	
	/**
	 * getter del vector del document
	 * @return vector del document
	 */
	public Map<String, Double> getVectorDocument() {
		if (vectorDocument_ != null) return vectorDocument_;
		else return new HashMap<>();
	}
	
	/**
	 * metode calculador del vector del document
	 * @param keyWords paraules clau del document amb el seu nombre d'aparicions dins del document i el nombre de documents que contenen la paraula
	 * @param nombreDocuments nombre total de documents dins del sistema
	 * @param nombreParaules nombre total de paraules dins del document
	 */
	public void calcularVector(Map<String, Integer> keyWords, Map<String, Integer> nDocs, List<String> missingWords, Integer nombreDocuments, Integer nombreParaules) {
        vectorDocument_ = new TreeMap<>();
		for (Map.Entry<String, Integer> paraula : keyWords.entrySet()) {
            double tf, idf, tfidf;
            tf = ((double)paraula.getValue()/((double)nombreParaules));
            idf = Math.log((double)nombreDocuments/((double)nDocs.get(paraula.getKey())));

            tfidf = tf * idf;
            vectorDocument_.put(paraula.getKey(), tfidf);
        }

		for (String missingWord : missingWords) {
			vectorDocument_.put(missingWord, 0.0);
		}
	}
}
