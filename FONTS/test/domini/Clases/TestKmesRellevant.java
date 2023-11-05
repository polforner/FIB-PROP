package domini.Clases;

import domini.Clases.*;
import domini.controladors.CtrlDocument;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestKmesRellevant
{
    private static CtrlDocument cd;
    @BeforeClass
    public static void setUp() throws Exception {
        cd =CtrlDocument.getInstance();
        String titol="1";
        String autor="JoanAbellan";
        List<String> contingut1= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivia un hidalgo de los de lanza en astillero");
        cd.nouDocument(autor,titol,contingut1);
        titol="2";
        autor="MontserratAbello";
        List<String> contingut2 =Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros");
        cd.nouDocument(autor,titol,contingut2);
        titol="3";
        autor="JosepLluisAguilo";
        List<String> contingut3=Arrays.asList("En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que viv�a un hidalgo de los de lanza en astillero");
        cd.nouDocument(autor,titol,contingut3);
        titol="4";
        autor="RaimondAguiloBartolome";
        List<String> contingut4=Arrays.asList("Es, pues, de saber que este sobredicho hidalgo, los ratos que estaba ocioso -que eran los mas");
        cd.nouDocument(autor,titol,contingut4);
        titol="5";
        autor="JoanAdellAlvarez";
        List<String> contingut5=Arrays.asList("le parecian tan bien como los que compuso el famoso Feliciano de Silva");
        cd.nouDocument(autor,titol,contingut5);
        titol="6";
        autor="JoanAgut";
        List<String> contingut6=Arrays.asList("Con estas razones perdia el pobre caballero el juicio, y desvel�base por entenderlas");
        cd.nouDocument(autor,titol,contingut6);
        titol="7";
        autor="JosepMariaAinaud";
        List<String> contingut7=Arrays.asList("que era hombre docto, graduado en Ciguenza");
        cd.nouDocument(autor,titol,contingut7);
        titol="8";
        autor="MontserratAbello";
        List<String> contingut8= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivia un hidalgo de los de lanza en astillero");
        cd.nouDocument(autor,titol,contingut8);
    }

    /**
     * Objecte de la prova: Test del metode KmesRellevant(k, query) de la classe KmesRellevant
     * Fitxers de dades necessaris: dades introduiides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte KmesRellevant.
     * Operativa: En aquest test es comprova que es crea correctament un objecte KmesRellevant amb k=k i query=query
     * Primer es crea un objecte KmesRellevant, es crida els metodes getK() i getQuery i es comprova que el resultat es correcte
     */
    @Test
    public void TestConstructora() throws FileNotFoundException {
        System.out.println("TestConstructora");
        List<String> query=Arrays.asList("acogimiento","honra","excelentcia","buen");
        int k=3;
        KmesRellevant consulta=new KmesRellevant(k,query);
        assertEquals(query,consulta.getQuery());
        assertEquals(k,consulta.getK());

    }

    /**
     * Objecte de la prova: Test del metode executar de la classe KmesRellevant
     * Fitxers de dades necessaris: dades introduides manualment.
     * Valors estudiats: Estrategia caixa gris. Es passa per entrada una llista de paraules i un k.
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta
     * Primer es crea un objecte KmesRellevant, es crida els metodes executar() i es comprova que el resultat es correcte
     */
    @Test
    public void TestExecutar()  throws FileNotFoundException{
        System.out.println("TestExecutar");
        List<String> query=Arrays.asList("acogimiento","honra","excelencia","buen");
        int k=3;
        KmesRellevant consulta=new KmesRellevant(k,query);
        List<String> titols=Arrays.asList("8","2","1");
        List<String> autors=Arrays.asList("JoanAbellan","MontserratAbello");
        ResultatConsulta res= consulta.executar();
        Map<String, Set<String>> result=((ResultatConsultaPairs) res).getResultat();
        for (String t : result.keySet()) {
            assertTrue(autors.contains(t));
            Set<String> a=result.get(t);
            for (String ts : a) {
                assertTrue(titols.contains(ts));
            }
        }
    }
    /**
     * Objecte de la prova: Test del metode executar de la classe KmesRellevant
     * Fitxers de dades necessaris: dades introduides manualment.
     * Valors estudiats: Estrategia caixa gris. Es passa per entrada una llista de paraules i un k=0.
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta quan k sigui 0
     * Primer es crea un objecte KmesRellevant, es crida els metodes executar() i es comprova que el resultat es correcte
     */
    @Test
    public void Testkigualzero() throws FileNotFoundException {
        System.out.println("cas k = 0");

        List<String> query=Arrays.asList("acogimiento","honra","excelencia","buen");
        int k=0;
        KmesRellevant consulta=new KmesRellevant(k,query);

        ResultatConsulta res= consulta.executar();

        Map<String,Set<String>> result=((ResultatConsultaPairs) res).getResultat();
        assertEquals(0,result.size());


    }
    /**
     * Objecte de la prova: Test del metode executar de la classe KmesRellevant
     * Fitxers de dades necessaris: dades introduides manualment.
     * Valors estudiats: Estrategia caixa gris. Es passa per entrada una llista de paraules i un k.
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta quan totes paraules siguin irrellevants amb
     * els documents, la resultat hauria de ser buit encara que k > 0.
     * Primer es crea un objecte KmesRellevant, es crida els metodes executar() i es comprova que el resultat es correcte
     */
    @Test
    public void TestQueryIrrellevant() throws FileNotFoundException {
        System.out.println("QueryIrrellevant");

        List<String> query=Arrays.asList("ultim","now","next","dinner");
        int k=4;
        KmesRellevant consulta=new KmesRellevant(k,query);

        ResultatConsulta res= consulta.executar();

        Map<String,Set<String>> result=((ResultatConsultaPairs) res).getResultat();
        assertEquals(0,result.size());
    }

    /**
     * Objecte de la prova: Test del metode executar de la classe KmesRellevant
     * Fitxers de dades necessaris: dades introduides manualment.
     * Valors estudiats: Estrategia caixa gris. Es passa per entrada una llista de query i un k mes gran que nombre de documents
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta quan k sigui mes gran que nombre de documents, el resultat hauria de
     * ser tots documents que son rellevants amb query el que introduit primer es crea un objecte KmesRellevant, es crida els metodes executar() i es comprova que el resultat es correcte
     */
    @Test
    public void TestKMesGranQueNombreDeDocument() throws FileNotFoundException {
        System.out.println("cas k >= nombre de documents");
        List<String> query=Arrays.asList("acogimiento","honra","excelencia","buen");
        int k=9;
        KmesRellevant consulta=new KmesRellevant(k,query);
        List<String> titols=Arrays.asList("8","2","1");
        List<String> autors=Arrays.asList("JoanAbellan","MontserratAbello");
        ResultatConsulta res = consulta.executar();
        Map<String, Set<String>> result = ((ResultatConsultaPairs) res).getResultat();


        for (String t : result.keySet()) {
            assertTrue(autors.contains(t));
            Set<String> a = result.get(t);
            for (String ts : a) {
                assertTrue(titols.contains(ts));
            }
        }
    }
}