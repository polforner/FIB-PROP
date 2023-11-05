package domini.Clases;



import domini.Clases.PrefixAutor;
import domini.Clases.ResultatConsulta;
import domini.Clases.ResultatConsultaNoms;
import domini.Clases.Trie;
import domini.controladors.CtrlDocument;
import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;


public class TestPrefixAutor {
    private static CtrlDocument cd;
    @BeforeClass
    public static void setUp() throws Exception {
        cd=CtrlDocument.getInstance();
        String titol="1";
        String autor="JoanAbellan";
        String c="pedra paper tijeras";
        String d ="papa mama";
        String[] s= (c.split("\\s"));

        List<String> contingut=new ArrayList<>();
        contingut.add(c);
        for(String ss : s) {
            contingut.add(ss);
        }
        contingut.add(d);
        cd.nouDocument(autor,titol,contingut);
        titol="2";
        autor="MontserratAbello";
        cd.nouDocument(autor,titol,contingut);
        titol="3";
        autor="JosepLluisAguilo";
        cd.nouDocument(autor,titol,contingut);
        titol="4";
        autor="RaimondAguiloBartolome";
        cd.nouDocument(autor,titol,contingut);
        titol="5";
        autor="JoanAdellAlvarez";
        cd.nouDocument(autor,titol,contingut);
        titol="6";
        autor="JoanAgut";
        cd.nouDocument(autor,titol,contingut);
        titol="7";
        autor="JosepMariaAinaud";
        cd.nouDocument(autor,titol,contingut);
        titol="8";
        autor="josepMariaAinaud";
        cd.nouDocument(autor,titol,contingut);
        titol="9";
        autor="joanAdellAlvarez";
        cd.nouDocument(autor,titol,contingut);
        titol="10";
        autor="Prop";
        cd.nouDocument(autor,titol,contingut);
        titol="11";
        autor="PAR";
        cd.nouDocument(autor,titol,contingut);
    }
    /**
     * Objecte de la prova: Test del mètode PrefixAutor(prefix) de la classe PrefixAutor
     * Fitxers de dades necessaris: Prefix introduït manualment.
     * Valors estudiats: Estratègia caixa gris. Es crea un objecte PrefixAutor.
     * Operativa: En aquest test es comprova que es crea correctament un objecte PrefixAutor amb prefix=prefix
     * Primer es crea un objecte PrefixAutor, es crida els mètodes getPrefix() i es comprova que el resultat és correcte
     */
    @Test
    public void TestConstructora() throws FileNotFoundException {
        System.out.println("TestConstructora");
        String prefix="jo";
        PrefixAutor consulta=new PrefixAutor(prefix);
        assertEquals(prefix,consulta.getPrefix());

    }

    /**
     * Objecte de la prova: Test del mètode executar de la classe PrefixAutor
     * Fitxers de dades necessaris: Prefix introduït manualment.
     * Valors estudiats: Estratègia caixa gris. Es passa per entrada un prefix.
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta quan alguns autors tenen el prefix entrat
     * és a dir dona un prefix després de execució obté tots autors que contenen aquest prefix.
     * Primer es crea un objecte PrefixAutor, es crida els mètodes executar() i es comprova que el resultat és correcte
     */
    @Test
    public void TestExecutar() throws FileNotFoundException {
        System.out.println("TestExecute");
        String prefix="Jo";
        PrefixAutor consulta=new PrefixAutor( prefix);
        List<String> resultat= Arrays.asList("JoanAbellan","JosepLluisAguilo","JoanAdellAlvarez","JoanAgut","JosepMariaAinaud");
        ResultatConsulta res= consulta.executar();
        Set<String> result= ((ResultatConsultaNoms) res).getResultat();
        for(String s : resultat) {

            assertTrue(result.contains(s));
        }
        prefix="P";
        Set<String> resu1ltat=Set.of("Prop","PAR");
        consulta=new PrefixAutor( prefix);
        res= consulta.executar();
        result= ((ResultatConsultaNoms) res).getResultat();
        assertEquals(result,resu1ltat);

    }

    /**
     * Objecte de la prova: Test del mètode executar de la classe PrefixAutor
     * Fitxers de dades necessaris: Prefix introduït manualment.
     * Valors estudiats: Estratègia caixa gris. Es passa per entrada un prefix que cap autor conté aquest prefix.
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta
     * Primer es crea un objecte PrefixAutor, es crida els mètodes executar() i es comprova que el resultat és correcte
     */
    @Test
    public void prefixCapAutorConte() throws FileNotFoundException {
        System.out.println("prefixCapAutorConte");
        String prefix="jo";
        PrefixAutor consulta=new PrefixAutor( prefix);
        Trie t=cd.getPrefixosAutors();
        List<String> resultat=Arrays.asList("josepMariaAinaud","joanAdellAlvarez");
        List<String> noconte = Arrays.asList("JoanAbellan","JosepLluisAguilo","JoanAdellAlvarez","JoanAgut","JosepMariaAinaud");
        ResultatConsulta res= consulta.executar();
        Set<String> result= ((ResultatConsultaNoms) res).getResultat();
        for(String s : resultat) {

            assertTrue(result.contains(s));
        }
        for (String s : noconte) {
            assertFalse(result.contains(s));
        }

    }

    /**
     * Objecte de la prova: Test del mètode executar de la classe PrefixAutor
     * Fitxers de dades necessaris: Prefix introduït manualment.
     * Valors estudiats: Estratègia caixa gris. Es passa per entrada un prefix minuscula.
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta quan té autors amb mateixos noms
     * però hi ha distinció entre majuscula i minuscula per exemple un autor és joan l'altre és Joan. Hem de assgurar quan
     * entra prefix "jo". La resultat no conté Joan i conté joan.
     * Primer es crea un objecte PrefixAutor, es crida els mètodes executar() i es comprova que el resultat és correcte
     */
    @Test
    public void distincioMajusculaMinuscula() throws FileNotFoundException {
        System.out.println("distincioMajusculaMinuscula");
        String prefix="Mat";
        PrefixAutor consulta=new PrefixAutor( prefix);
        Trie t=cd.getPrefixosAutors();
        List<String> resultat= Arrays.asList("JoanAbellan","JosepLluisAguilo","JoanAdellAlvarez","JoanAgut","JosepMariaAinaud");
        ResultatConsulta res= consulta.executar();
        Set<String> result= ((ResultatConsultaNoms) res).getResultat();
        assertEquals(0,result.size());

    }


}
