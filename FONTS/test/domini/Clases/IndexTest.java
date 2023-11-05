package domini.Clases;

import static org.junit.Assert.*;
import org.junit.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndexTest {
    private static Index index_;
    private static List<String> contingutDocument_;
    private static List<String> contingutDocument2_;

    @Before
    public void setIndex_() throws FileNotFoundException {
        index_ = new Index();
        contingutDocument_ = new ArrayList<>();
        contingutDocument_.add("Hola soc la primera frase, frase, frase.");
        contingutDocument_.add("HOLA soc la segona frase, frase, frase.");
        contingutDocument_.add("Hola soc la tercera frase, frase, frase.");
        contingutDocument_.add("Hola soc la tercera frase, frase, frase.");
        contingutDocument_.add("Hola soc la tercera frase, frase, frase.");
        contingutDocument_.add("Hola soc la tercera frase, frase, frase.");
        contingutDocument_.add("Hola soc la tercera frase, frase, frase.");
        contingutDocument_.add("Hola soc la tercera frase, frase, frase.");
        contingutDocument2_ = new ArrayList<>();
        contingutDocument2_.add("?????Hola! Quin DIA mes bonic: fa,, a U.S.A?!!");
    }

    @Test
    public void testKeyWordsBenProcessadesDeContingutDocument() {
        var keyWords = index_.afegirDocument(contingutDocument_, 1);
        var keyWords2 = index_.afegirDocument(contingutDocument2_, 2);
        System.out.println("-------------------------Keywords ben processades a partir del contingut del document--------------------------");

        System.out.println("DOCUMENT 1");
        for (Map.Entry<String, List<Integer>> keyWord : keyWords.entrySet()) {
            System.out.println("Paraula: " + keyWord.getKey() + " // Aparacions: " + keyWord.getValue().get(0) + " // Documents amb paraula: " + keyWord.getValue().get(1));
        }

        System.out.println("DOCUMENT 2");
        for (Map.Entry<String, List<Integer>> keyWord : keyWords2.entrySet()) {
            System.out.println("Paraula: " + keyWord.getKey() + " // Aparacions: " + keyWord.getValue().get(0) + " // Documents amb paraula: " + keyWord.getValue().get(1));
        }

        assertFalse(keyWords.containsKey("HOLA"));
        assertFalse(keyWords.containsKey("Hola"));
        assertTrue(keyWords.containsKey("hola"));
        assertTrue(keyWords.containsKey("soc"));
        assertFalse(keyWords.containsKey("la"));
        assertFalse(keyWords.containsKey("primera"));
        assertFalse(keyWords.containsKey("segona"));
        assertTrue(keyWords.containsKey("tercera"));
        assertTrue(keyWords.containsKey("frase"));
        assertFalse(keyWords.containsKey("frase,"));
        assertFalse(keyWords.containsKey("frase."));

        assertTrue(keyWords2.containsKey("hola"));
        assertFalse(keyWords2.containsKey("quin"));
        assertTrue(keyWords2.containsKey("dia"));
        assertFalse(keyWords2.containsKey("mes"));
        assertTrue(keyWords2.containsKey("bonic"));
        assertTrue(keyWords2.containsKey("fa"));
        assertFalse(keyWords2.containsKey("a"));
        assertTrue(keyWords2.containsKey("usa"));

        var indexSistema = index_.getInvertedIndex_();

        for (Map.Entry<String, Map<Integer, Set<Integer>>> entradaIndex : indexSistema.entrySet()) {
            System.out.println("KEYWORD: " + entradaIndex.getKey());
            for (Integer docID : entradaIndex.getValue().keySet()) {
                System.out.println("---DOC " + docID);
                for (Integer fraseID : entradaIndex.getValue().get(docID)) {
                    System.out.println("------FRASE: " + fraseID);
                }
            }
        }

        assertFalse(indexSistema.containsKey("HOLA"));
        assertFalse(indexSistema.containsKey("Hola"));
        assertTrue(indexSistema.containsKey("hola"));
        assertTrue(indexSistema.containsKey("soc"));
        assertFalse(indexSistema.containsKey("la"));
        assertFalse(indexSistema.containsKey("primera"));
        assertFalse(indexSistema.containsKey("segona"));
        assertTrue(indexSistema.containsKey("tercera"));
        assertTrue(indexSistema.containsKey("frase"));
        assertFalse(indexSistema.containsKey("frase,"));
        assertFalse(indexSistema.containsKey("frase."));

        assertTrue(indexSistema.containsKey("hola"));
        assertFalse(indexSistema.containsKey("quin"));
        assertTrue(indexSistema.containsKey("dia"));
        assertFalse(indexSistema.containsKey("mes"));
        assertTrue(indexSistema.containsKey("bonic"));
        assertTrue(indexSistema.containsKey("fa"));
        assertFalse(indexSistema.containsKey("a"));
        assertTrue(indexSistema.containsKey("usa"));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testCalculCorrecteParaulesDocument() {
        System.out.println("----------------------Calcul correcte de nombre de paraules del contingut del document-----------------------------");
        assertEquals(56, (long)index_.nombreParaulesDocument(contingutDocument_));
        assertEquals(8, (long)index_.nombreParaulesDocument(contingutDocument2_));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testCalculCorrecteParaulesRestantsDocument() {
        var keyWords = index_.afegirDocument(contingutDocument_, 1);
        var keyWords2 = index_.afegirDocument(contingutDocument2_, 2);

        List<String> paraulesRestants = index_.getParaulesRestants(1);
        System.out.println("----------------------Calcul correcte de keywords no presents en un document-----------------------------");
        System.out.println("KEYWORDS QUE NO APAREIXEN AL DOC 1");
        for (String paraula : paraulesRestants) {
            System.out.println(paraula);
        }
        assertTrue(paraulesRestants.contains("bonic"));
        assertTrue(paraulesRestants.contains("usa"));
        assertTrue(paraulesRestants.contains("dia"));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testRetornCorrecteEntradaParaulaAlIndex() {
        var keyWords = index_.afegirDocument(contingutDocument_, 1);
        var keyWords2 = index_.afegirDocument(contingutDocument2_, 2);
        var entradaParaulaIndex = index_.getDocsFrasesParaula("hola");
        var entradaParaulaIndex2 = index_.getDocsFrasesParaula("papagayo");
        System.out.println("-----------------------Retorn correcte de la entrada del index d'una paraula----------------------------");
        System.out.println("ENTRADA DE PARAULA CORRECTA");
        assertTrue(entradaParaulaIndex2.isEmpty());
        assertTrue(entradaParaulaIndex.containsKey(1));
        assertTrue(entradaParaulaIndex.get(1).contains(0));
        assertTrue(entradaParaulaIndex.get(1).contains(1));
        assertTrue(entradaParaulaIndex.get(1).contains(2));
        assertTrue(entradaParaulaIndex.get(1).contains(3));
        assertTrue(entradaParaulaIndex.get(1).contains(4));
        assertTrue(entradaParaulaIndex.get(1).contains(5));
        assertTrue(entradaParaulaIndex.get(1).contains(6));
        assertTrue(entradaParaulaIndex.get(1).contains(7));
        assertTrue(entradaParaulaIndex.containsKey(2));
        assertTrue(entradaParaulaIndex.get(2).contains(0));
        assertFalse(entradaParaulaIndex.get(2).contains(1));
        assertFalse(entradaParaulaIndex.get(2).contains(2));
        assertFalse(entradaParaulaIndex.get(2).contains(3));
        assertFalse(entradaParaulaIndex.get(2).contains(4));
        assertFalse(entradaParaulaIndex.get(2).contains(5));
        assertFalse(entradaParaulaIndex.get(2).contains(6));
        assertFalse(entradaParaulaIndex.get(2).contains(7));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testRetornCorrecteDocumentsQueContenenParaula() {
        System.out.println("-----------------------Retorn correcte de documents que contenen una paraula----------------------------");
        var keyWords = index_.afegirDocument(contingutDocument_, 1);
        var keyWords2 = index_.afegirDocument(contingutDocument2_, 2);
        var docs = index_.getDocsParaula("hola");
        assertTrue(docs.contains(1));
        assertTrue(docs.contains(2));
        docs = index_.getDocsParaula("bonic");
        assertFalse(docs.contains(1));
        assertTrue(docs.contains(2));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testRetornCorrecteEntradesKeyWordsFrase() {
        System.out.println("-----------------------Retorn correcte de les entrades del index de les keywords de la frase----------------------------");
        var keyWords = index_.afegirDocument(contingutDocument_, 1);
        var keyWords2 = index_.afegirDocument(contingutDocument2_, 2);
        var entradesKWFrase = index_.getDocsFrasesFrase("hola com estas bonic?");
        assertTrue(entradesKWFrase.get("hola").containsKey(1));
        assertTrue(entradesKWFrase.get("hola").containsKey(2));
        assertFalse(entradesKWFrase.get("hola").containsKey(3));
        assertFalse(entradesKWFrase.containsKey("com"));
        assertTrue(entradesKWFrase.get("estas").isEmpty());
        assertFalse(entradesKWFrase.get("bonic").containsKey(1));
        assertTrue(entradesKWFrase.get("bonic").containsKey(2));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testKeyWordsCorrectamentEliminadesDelIndex() {
        System.out.println("-----------------------Key words correctament eliminades del index----------------------------");
        var keyWords = index_.afegirDocument(contingutDocument_, 1);
        var keyWords2 = index_.afegirDocument(contingutDocument2_, 2);
        var keyWordsEliminades = new ArrayList<String>();
        keyWordsEliminades.add("hola");
        keyWordsEliminades.add("bonic");
        assertTrue(index_.getInvertedIndex_().get("hola").containsKey(2));
        assertTrue(index_.getInvertedIndex_().get("bonic").containsKey(2));
        assertTrue(index_.getInvertedIndex_().get("dia").containsKey(2));
        assertTrue(index_.getInvertedIndex_().get("fa").containsKey(2));
        assertTrue(index_.getInvertedIndex_().get("usa").containsKey(2));
        index_.actualitzaIndex(keyWordsEliminades, 2);
        assertFalse(index_.getInvertedIndex_().get("hola").containsKey(2));
        assertFalse(index_.getInvertedIndex_().get("bonic").containsKey(2));
        assertTrue(index_.getInvertedIndex_().get("dia").containsKey(2));
        assertTrue(index_.getInvertedIndex_().get("fa").containsKey(2));
        assertTrue(index_.getInvertedIndex_().get("usa").containsKey(2));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }
}