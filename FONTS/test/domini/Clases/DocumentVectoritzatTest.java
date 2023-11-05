package domini.Clases;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DocumentVectoritzatTest {

    private static DocumentVectoritzat vector_;
    private static DocumentVectoritzat vector2_;

    private static Document doc_;
    private static Document doc2_;

    private static Index index_;


    @Before
    public void setVector_() throws FileNotFoundException {
        String autor_ = "Bernat Homs";

        String titol_ = "Es bastant tard per estar programant2";
        String titol2_ = "Es bastant tard per estar programant2";

        List<String> contingutDoc_ = new ArrayList<>();
        List<String> contingutDoc2_ = new ArrayList<>();

        contingutDoc_.add("Aquesta linia del document.");

        contingutDoc2_.add("Hola bon dia.");

        doc_ = new Document(autor_, titol_, contingutDoc_, 1);
        doc2_ = new Document(autor_, titol2_, contingutDoc2_, 2);
        index_ = new Index();
        vector_ = doc_.vectoritzarDocument();
        vector2_ = doc2_.vectoritzarDocument();
    }

    @Test
    public void testRetornCorrecteDeVectorDeDocument() {
        System.out.println("-------------------------Retorn correcte de vector de document --------------------------");
        var keyWords = index_.afegirDocument(doc_.getContingut(), doc_.getDocID());
        var keyWords2 = index_.afegirDocument(doc2_.getContingut(), doc2_.getDocID());
        doc_.setKeyWords(keyWords);
        doc2_.setKeyWords(keyWords2);
        doc_.actualitzaKeyWords(keyWords2, new HashMap<>());

        System.out.println("DOCUMENT 1");
        var paraulesRestants = index_.getParaulesRestants(doc_.getDocID());
        var nombreParaules = index_.nombreParaulesDocument(doc_.getContingut());
        assertTrue(vector_.getVectorDocument().isEmpty());
        vector_.calcularVector(keyWords, paraulesRestants, 2, nombreParaules);
        for (Map.Entry<String, Double> coordenada : vector_.getVectorDocument().entrySet()) {
            System.out.println("PARAULA: " + coordenada.getKey() + " VALOR COORDENADA: " + coordenada.getValue());
        }
        assertFalse(vector_.getVectorDocument().isEmpty());

        System.out.println("DOCUMENT 2");
        paraulesRestants = index_.getParaulesRestants(doc2_.getDocID());
        nombreParaules = index_.nombreParaulesDocument(doc2_.getContingut());
        assertTrue(vector2_.getVectorDocument().isEmpty());
        vector2_.calcularVector(keyWords2, paraulesRestants, 2, nombreParaules);
        for (Map.Entry<String, Double> coordenada : vector2_.getVectorDocument().entrySet()) {
            System.out.println("PARAULA: " + coordenada.getKey() + " VALOR COORDENADA: " + coordenada.getValue());
        }

        assertFalse(vector2_.getVectorDocument().isEmpty());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void testCalculCorrecteVectorDocument() {
        System.out.println("-------------------------Calcul correcte vector document --------------------------");
        var keyWords = index_.afegirDocument(doc_.getContingut(), doc_.getDocID());
        doc_.setKeyWords(keyWords);
        var paraulesRestants = index_.getParaulesRestants(doc_.getDocID());
        var nombreParaules = index_.nombreParaulesDocument(doc_.getContingut());
        vector_.calcularVector(keyWords, paraulesRestants, 2, nombreParaules);
        double tmp = vector_.getVectorDocument().get("document");
        assertEquals(0.17328679513998632, tmp, 0.00000001);
        System.out.println("------------------------------------------------------------------------------------------------------");
    }
}