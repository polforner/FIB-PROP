package domini.Clases;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TestOperacionResultaPair {

    @Test
    public void testInterseccio() {
        Map<String, Set<String>> resultat1=new HashMap<>();
        resultat1.put("Anna",Set.of("1","2","3"));
        resultat1.put("Rafat",Set.of("1","2","3"));
        resultat1.put("Rafael",Set.of("1,5"));
        Map<String, Set<String>> resultat2=new HashMap<>();
        Map<String,Set<String>> resultatOperacio=new HashMap<>();
        OperacionResultatPair op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.interseccio();
        assertTrue(resultatOperacio.isEmpty());
        resultat2.put("Rafat",Set.of("1","2"));
        op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.interseccio();
        assertEquals(resultatOperacio,resultat2);
        resultat2.put("Maria",Set.of("1","2","3"));
        Map<String,Set<String>> resultatEsperat=new HashMap<>();
        resultatEsperat.put("Rafat",Set.of("1","2"));
        op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.interseccio();
        assertEquals(resultatOperacio,resultatEsperat);
    }

    @Test
    public void TestUnio() {
        Map<String, Set<String>> resultat1=new HashMap<>();
        resultat1.put("Anna",Set.of("1","2","3"));
        resultat1.put("Rafat",Set.of("1","2","3"));
        resultat1.put("Rafael",Set.of("1,5"));
        Map<String, Set<String>> resultat2=new HashMap<>();
        Map<String,Set<String>> resultatOperacio=new HashMap<>();
        OperacionResultatPair op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.unio();
        assertEquals(resultatOperacio,resultat1);
        resultat2.put("Rafat",Set.of("1","2"));
        op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.unio();
        assertEquals(resultatOperacio,resultat1);
        resultat2.put("Maria",Set.of("1","2","3"));
        Map<String,Set<String>> resultatEsperat=new HashMap<>();
        resultatEsperat.put("Anna",Set.of("1","2","3"));
        resultatEsperat.put("Rafat",Set.of("1","2","3"));
        resultatEsperat.put("Rafael",Set.of("1,5"));
        resultatEsperat.put("Maria",Set.of("1","2","3"));
        op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.unio();
        assertEquals(resultatOperacio,resultatEsperat);
    }

    @Test
    public void TestDiferencia() {
        Map<String, Set<String>> resultat1=new HashMap<>();
        resultat1.put("Anna",Set.of("1","2","3"));
        resultat1.put("Rafat",Set.of("1","2","3"));
        resultat1.put("Rafael",Set.of("1,5"));
        Map<String, Set<String>> resultat2=new HashMap<>();
        Map<String,Set<String>> resultatOperacio=new HashMap<>();
        OperacionResultatPair op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.diferencia();
        assertEquals(resultatOperacio,resultat1);
        Map<String,Set<String>> resultatEsperat=new HashMap<>();
        resultatEsperat.put("Anna",Set.of("1","2","3"));
        resultatEsperat.put("Rafat",Set.of("3"));
        resultatEsperat.put("Rafael",Set.of("1,5"));
        resultat2.put("Rafat",Set.of("1","2"));
        op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.diferencia();
        assertEquals(resultatOperacio,resultatEsperat);
        resultat2.put("Anna",Set.of("1","2","3"));
        resultat2.put("Rafat",Set.of("1","2","3"));
        resultat2.put("Rafael",Set.of("1,5"));
        op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.diferencia();
        assertTrue(resultatOperacio.isEmpty());
    }

    @Test
    public void TestDiferenciaSimetirica() {
        Map<String, Set<String>> resultat1=new HashMap<>();
        resultat1.put("Anna",Set.of("1","2","3"));
        resultat1.put("Rafat",Set.of("1","2","3"));
        resultat1.put("Rafael",Set.of("1,5"));
        Map<String, Set<String>> resultat2=new HashMap<>();
        Map<String,Set<String>> resultatOperacio=new HashMap<>();
        OperacionResultatPair op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.diferenciaSimetrica();
        assertEquals(resultatOperacio,resultat1);
        Map<String,Set<String>> resultatEsperat=new HashMap<>();
        resultatEsperat.put("Anna",Set.of("1","2","3"));
        resultatEsperat.put("Rafat",Set.of("3"));
        resultatEsperat.put("Rafael",Set.of("1,5"));
        resultat2.put("Rafat",Set.of("1","2"));
        op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.diferenciaSimetrica();
        assertEquals(resultatOperacio,resultatEsperat);
        resultat2.put("Anna",Set.of("1","2","3"));
        resultat2.put("Rafat",Set.of("1","2","3"));
        resultat2.put("Rafael",Set.of("1,5"));
        op1=new OperacionResultatPair(resultat1,resultat2);
        resultatOperacio=op1.diferenciaSimetrica();
        assertTrue(resultatOperacio.isEmpty());
    }
}
