package domini.Clases;

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;


public class ResultatConsultaPairsTest {
    /**
     * Objecte de la prova: Test del metode ordenaAlfabeticament de la classe ResultatConsultaPairTest
     * Fitxers de dades necessaris: dades introduiides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte ResultatConsultaPairTest.
     * Operativa: En aquest test comprovem que la funcio opera correctament
     */
    @Test
    public void ordenarAlfabeticament() {
        Map<String,Set<String>> test = new HashMap<>();
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String e = "e";
        Set<String> testString = new HashSet<>(Arrays.asList("bb","cc","aa","ww","ii"));
        test.put(a,testString);
        test.put(e,testString);
        test.put(d,testString);
        test.put(b,testString);
        test.put(c,testString);
        ResultatConsultaPairs rcp = new ResultatConsultaPairs(test, "test", Arrays.asList("a"));
        Set<String> StringCorrecte = new TreeSet<>(testString);
        Map<String,Set<String>> Correcte = new TreeMap<>();
        Correcte.put(a,StringCorrecte);
        Correcte.put(e,StringCorrecte);
        Correcte.put(d,StringCorrecte);
        Correcte.put(b,StringCorrecte);
        Correcte.put(c,StringCorrecte);
        rcp.ordenarAlfabeticament();
        assertEquals(test,Correcte);
    }
    /**
     * Objecte de la prova: Test del metode ordenarInversament de la classe ResultatConsultaPairTest
     * Fitxers de dades necessaris: dades introduiides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte ResultatConsultaPairTest.
     * Operativa: En aquest test comprovem que la funcio opera correctament
     */
    @Test
    public void ordenarInversament() {
        Map<String,Set<String>> test = new HashMap<>();
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String e = "e";
        Set<String> testString = new HashSet<>(Arrays.asList("bb","cc","aa","ww","ii"));
        test.put(a,testString);
        test.put(e,testString);
        test.put(d,testString);
        test.put(b,testString);
        test.put(c,testString);
        ResultatConsultaPairs rcp = new ResultatConsultaPairs(test, "test", Arrays.asList("a"));
        Set<String> StringCorrecte = new TreeSet<>(testString);
        Map<String,Set<String>> Correcte = new TreeMap<>();
        Correcte.put(a,StringCorrecte);
        Correcte.put(e,StringCorrecte);
        Correcte.put(d,StringCorrecte);
        Correcte.put(b,StringCorrecte);
        Correcte.put(c,StringCorrecte);
        rcp.ordenarInversament();
        assertEquals(test,Correcte);
    }
    /**
     * Objecte de la prova: Test del getters de la classe ResultatConsultaPairTest
     * Fitxers de dades necessaris: dades introduiides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte ResultatConsultaPairTest.
     * Operativa: En aquest test comprovem que els diferents getters funcionen correctament.
     */
    @Test
    public void Testgetters() {
        Map<String,Set<String>> resultat = new HashMap<>();
        Set<String> test = new HashSet<>();
        test.add("a");
        test.add("f");
        test.add("g");
        test.add("d");
        test.add("e");
        resultat.put("test1", test);
        resultat.put("test2", test);
        resultat.put("test3", test);
        ResultatConsultaPairs rcp = new ResultatConsultaPairs(resultat,"test",Arrays.asList("e"));
        Map<String,Set<String>> res = rcp.getResultat();
        List<String> params = rcp.getParametres();
        String tipus = rcp.getTipusConsulta();
    }
}