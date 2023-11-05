package domini.Clases;

import org.junit.BeforeClass;
import org.junit.Test;

import domini.controladors.CtrlDocument;

import static org.junit.Assert.assertEquals;
import java.util.*;
public class TestBooleana {
    private static CtrlDocument cd;
    @BeforeClass
    public static void setUp() throws Exception {
        cd = new CtrlDocument();
        String autor="1";
        String titol="1";
        List<String> contingut1= Arrays.asList("manzana i pera. patata o zanahoria.");
        cd.nouDocument(autor,titol,contingut1);
        autor="1";
        titol="2";
        List<String> contingut2 = Arrays.asList("tomate, aceite.");
        cd.nouDocument(autor,titol,contingut2);
        autor="2";
        titol="1";
        List<String> contingut3 = Arrays.asList("manzana pera i tomate.");
        cd.nouDocument(autor,titol,contingut3);
        autor="2";
        titol="2";
        List<String> contingut4 = Arrays.asList("atun.");
        cd.nouDocument(autor,titol,contingut4);
    }
    @Test
    /**
     * Objectiu del test: Comprobar que l'execucio funciona bé per una paraula sola
     */
    public void testExecutara1() throws Exception{
        ExprBooleana expr = new ExprBooleana("manzana");
        Booleana boolena = new Booleana(expr);
        ResultatConsultaPairs res = boolena.executar();
        Map<String,Set<String>> resultat = new HashMap<>();
        Set<String> set= new HashSet<>();
        set.add("1");
        resultat.put("1",set);
        resultat.put("2",set);
        assertEquals(resultat, res.getResultat());
    }
    @Test
    /**
     * Objectiu del test: Comprobar que funciona l'execucio funciona bé amb una expressio boolena basica
     */
    public void testExecutara2() throws Exception{
        ExprBooleana expr = new ExprBooleana("manzana & pera");
        Booleana boolena = new Booleana(expr);
        ResultatConsultaPairs res = boolena.executar();
        Map<String,Set<String>> resultat = new HashMap<>();
        Set<String> set= new HashSet<>();
        set.add("1");
        resultat.put("1",set);
        resultat.put("2",set);
        assertEquals(resultat, res.getResultat());
    }
    @Test
    /**
     * Objectiu del test: Comprobar que funciona l'execucio amb el operant '|'
     */
    public void testExecutara3() throws Exception{
        ExprBooleana expr = new ExprBooleana("manzana | tomate | atun");
        Booleana boolena = new Booleana(expr);
        ResultatConsultaPairs res = boolena.executar();
        Map<String,Set<String>> resultat = new HashMap<>();
        Set<String> set= new HashSet<>();
        set.add("1");
        set.add("2");
        resultat.put("1",set);
        resultat.put("2",set);
        assertEquals(resultat, res.getResultat());
    }
    @Test
    /**
     * Objectiu del test: Comprobar que funciona l'execucio amb una frase
     */
    public void testExecutara4() throws Exception{
        ExprBooleana expr = new ExprBooleana("\"manzana i pera\"");
        Booleana boolena = new Booleana(expr);
        ResultatConsultaPairs res = boolena.executar();
        Map<String,Set<String>> resultat = new HashMap<>();
        Set<String> set= new HashSet<>();
        set.add("1");
        resultat.put("1",set);
        assertEquals(resultat, res.getResultat());
    }
    @Test
    /**
     * Objectiu del test: Comprobar que funciona l'execucio amb parentesis
     */
    public void testExecutara5() throws Exception{
        ExprBooleana expr = new ExprBooleana("(pera | manzana) & tomate");
        Booleana boolena = new Booleana(expr);
        ResultatConsultaPairs res = boolena.executar();
        Map<String,Set<String>> resultat = new HashMap<>();
        Set<String> set= new HashSet<>();
        set.add("1");
        resultat.put("2",set);
        assertEquals(resultat, res.getResultat());
    }
    @Test
    /**
     * Objectiu del test: Comprobar que funciona l'execucio amb parentesis
     */
    public void testExecutara6() throws Exception{
        ExprBooleana expr = new ExprBooleana("!manzana");
        Booleana boolena = new Booleana(expr);
        ResultatConsultaPairs res = boolena.executar();
        Map<String,Set<String>> resultat = new HashMap<>();
        Set<String> set= new HashSet<>();
        set.add("2");
        resultat.put("1",set);
        resultat.put("2",set);
        assertEquals(resultat, res.getResultat());
    }

}
