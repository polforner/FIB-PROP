package domini.Clases;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

public class DocumentTest {

    private static Document doc_;

    private static String autor_;
    private static String autor2_;

    private static String titol_;
    private static String titol2_;

    private static List<String> contingutDoc_;
    private static List<String> contingutDoc2_;

    private static List<String> contingutDoc3_;

    @Before
    public void setDoc_() throws FileNotFoundException {
        autor_ = "Bernat Homs";
        autor2_ = "Bernat Gisbert";

        titol_ = "Es bastant tard per estar programant";
        titol2_ = "It's pretty late to be programming";

        contingutDoc_ = new ArrayList<>();
        contingutDoc2_ = new ArrayList<>();
        contingutDoc3_ = new ArrayList<>();

        contingutDoc_.add("Aquesta es la primera linia de contingut del document.");
        contingutDoc_.add("Aquesta es la segona linia de contingut del document.");
        contingutDoc_.add("Aquesta es la tercera linia de contingut del document.");

        contingutDoc2_.add("Hola bon dia.");
        contingutDoc2_.add("Hola bona tarda.");
        contingutDoc2_.add("Hola bona nit.");

        contingutDoc3_.add("Questo document.");
        contingutDoc3_.add("Questo document.");

        doc_ = new Document(autor_, titol_, contingutDoc_, 1);
    }

    @Test
    public void testRetornCorrecteDeIdentificadorNumericDeDocument() {
        System.out.println("-------------------------Retorn correcte de identificador numeric de document --------------------------");
        assertEquals(1, doc_.getDocID());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testRetornCorrecteDeAutorDeDocument() {
        System.out.println("-------------------------Retorn correcte d'autor de document --------------------------");
        assertEquals(autor_, doc_.getAutor());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testAssignacioCorrecteDeAutorDeDocument() {
        System.out.println("-------------------------Assignacio correcte d'autor de document --------------------------");
        assertEquals(autor_, doc_.getAutor());
        doc_.setAutor(autor2_);
        assertEquals(autor2_, doc_.getAutor());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testRetornCorrecteDeTitolDeDocument() {
        System.out.println("-------------------------Retorn correcte de titol de document --------------------------");
        assertEquals(titol_, doc_.getTitol());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testAssignacioCorrecteDeTitolDeDocument() {
        System.out.println("-------------------------Assignacio correcte de titol de document --------------------------");
        assertEquals(titol_, doc_.getTitol());
        doc_.setTitol(titol2_);
        assertEquals(titol2_, doc_.getTitol());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testRetornCorrecteDeContingutDeDocument() {
        System.out.println("-------------------------Retorn correcte de contingut de document --------------------------");
        assertEquals(contingutDoc_, doc_.getContingut());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testAssignacioCorrecteDeContingutDeDocument() {
        System.out.println("-------------------------Assignacio correcte de contingut de document --------------------------");
        doc_.setContingut(contingutDoc2_);
        assertEquals(contingutDoc2_, doc_.getContingut());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testRetornCorrecteKeyWordsDocument() throws FileNotFoundException {
        System.out.println("-------------------------Retorn correcte keywords de document --------------------------");
        Index index = new Index();
        var keyWords = index.afegirDocument(doc_.getContingut(), doc_.getDocID());
        doc_.setKeyWords(keyWords);
        assertEquals(keyWords, doc_.getKeyWords());
        assertTrue(doc_.getKeyWords().containsKey("document"));
        assertTrue(doc_.getKeyWords().containsKey("linia"));
        assertTrue(doc_.getKeyWords().containsKey("tercera"));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testAssignacioCorrecteDeKeyWordsDeDocument() throws FileNotFoundException {
        System.out.println("-------------------------Assignacio correcte de keywords de document --------------------------");
        Index index = new Index();
        var keyWords = index.afegirDocument(doc_.getContingut(), doc_.getDocID());
        doc_.setKeyWords(keyWords);
        assertTrue(doc_.getKeyWords().containsKey("document"));
        assertTrue(doc_.getKeyWords().containsKey("linia"));
        assertTrue(doc_.getKeyWords().containsKey("tercera"));
        doc_.setContingut(contingutDoc2_);
        keyWords = index.afegirDocument(doc_.getContingut(), doc_.getDocID());
        doc_.setKeyWords(keyWords);
        assertTrue(doc_.getKeyWords().containsKey("dia"));
        assertTrue(doc_.getKeyWords().containsKey("tarda"));
        assertTrue(doc_.getKeyWords().containsKey("nit"));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testCreacioCorrectaDeVectoritzacioDeDocument() {
        System.out.println("-------------------------Creacio correcta de vectoritzacio de document --------------------------");
        assertNotEquals(null, doc_.vectoritzarDocument());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testRetornCorrecteDeConsultaSobreFraseDeDocument() {
        System.out.println("-------------------------Retorn correcte de consulta sobre frase de document --------------------------");
        assertTrue(doc_.conteFrase("linia de contingut del document.", 0));
        assertTrue(doc_.conteFrase("contingut del document.", 1));
        assertTrue(doc_.conteFrase("segona linia de contingut", 1));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testFuncionamentCorrecteEsborramentDeVectoritzacioDeDocument() {
        System.out.println("-------------------------Funcionament correcte esborrament de vectoritzacio de document --------------------------");
        DocumentVectoritzat vec = doc_.vectoritzarDocument();
        doc_.esborraVectorDocument();
        assertNotEquals(new DocumentVectoritzat(), doc_.getVectorDocument());
        assertNull(doc_.getVectorDocument());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testFunciomanetCorrecteActualitzacioKeyWordsDeDocument() throws FileNotFoundException {
        System.out.println("-------------------------Funcionament correcte actualitzacio keywords de document --------------------------");
        Index index = new Index();
        var keyWords = index.afegirDocument(doc_.getContingut(), doc_.getDocID());
        doc_.setKeyWords(keyWords);
        keyWords = index.afegirDocument(contingutDoc3_, 2);
        assertEquals(1, (long)doc_.getKeyWords().get("document").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("linia").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("contingut").get(1));
        doc_.actualitzaKeyWords(keyWords, new HashMap<>());
        assertEquals(2, (long)doc_.getKeyWords().get("document").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("linia").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("contingut").get(1));
        doc_.actualitzaKeyWords(new HashMap<>(), keyWords);
        assertEquals(1, (long)doc_.getKeyWords().get("document").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("linia").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("contingut").get(1));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testFunciomanetCorrecteActualitzacioKeyWordsDeDocument2() throws FileNotFoundException {
        System.out.println("-------------------------Funcionament correcte actualitzacio keywords de document 2 --------------------------");
        Index index = new Index();
        var keyWords = index.afegirDocument(doc_.getContingut(), doc_.getDocID());
        doc_.setKeyWords(keyWords);
        var keyWords22 = index.afegirDocument(contingutDoc3_, 2);
        var keyWords2 = new ArrayList<String>();
        for (Map.Entry<String, List<Integer>> word : keyWords22.entrySet()) {
            keyWords2.add(word.getKey());
        }
        assertEquals(1, (long)doc_.getKeyWords().get("document").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("linia").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("contingut").get(1));
        doc_.actualitzaKeyWords(keyWords2, new ArrayList<>());
        assertEquals(2, (long)doc_.getKeyWords().get("document").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("linia").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("contingut").get(1));
        doc_.actualitzaKeyWords(new ArrayList<>(), keyWords2);
        assertEquals(1, (long)doc_.getKeyWords().get("document").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("linia").get(1));
        assertEquals(1, (long)doc_.getKeyWords().get("contingut").get(1));
        System.out.println("------------------------------------------------------------------------------------------------------");
    }
}