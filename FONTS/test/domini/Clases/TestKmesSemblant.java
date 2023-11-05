package domini.Clases;

import domini.Clases.KmesSemblant;
import domini.Clases.ResultatConsulta;
import domini.Clases.ResultatConsultaPairs;
import domini.controladors.CtrlDocument;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

public class TestKmesSemblant
{
    private static CtrlDocument cd;
    @BeforeClass
    public static void setUp() throws Exception {
        cd =CtrlDocument.getInstance();
        String titol="1";
        String autor="JoanAbellan";
        List<String> contingut1= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd.nouDocument(autor,titol,contingut1);
        titol="2";
        autor="MontserratAbello";
        List<String> contingut2 =Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros");
        cd.nouDocument(autor,titol,contingut2);
        titol="3";
        autor="JosepLluisAguilo";
        List<String> contingut3=Arrays.asList("En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd.nouDocument(autor,titol,contingut3);
        titol="4";
        autor="RaimondAguiloBartolome";
        List<String> contingut4=Arrays.asList("Es, pues, de saber que este sobredicho hidalgo, los ratos que estaba ocioso -que eran los más");
        cd.nouDocument(autor,titol,contingut4);
        titol="5";
        autor="JoanAdellAlvarez";
        List<String> contingut5=Arrays.asList("le parecían tan bien como los que compuso el famoso Feliciano de Silva");
        cd.nouDocument(autor,titol,contingut5);
        titol="6";
        autor="JoanAgut";
        List<String> contingut6=Arrays.asList("Con estas razones perdía el pobre caballero el juicio, y desvelábase por entenderlas");
        cd.nouDocument(autor,titol,contingut6);
        titol="7";
        autor="JosepMariaAinaud";
        List<String> contingut7=Arrays.asList("que era hombre docto, graduado en Cigüenza");
        cd.nouDocument(autor,titol,contingut7);
        titol="8";
        autor="MontserratAbello";
        List<String> contingut8= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd.nouDocument(autor,titol,contingut8);

    }

    /**
     * Objecte de la prova: Test del metode KmesSemblant(titol autor k) de la classe KmesSemblant
     * Fitxers de dades necessaris: dades introduides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte KmesSemblant.
     * Operativa: En aquest test es comprova que es crea correctament un objecte KmesSemblant amb titol=titol, k=k, autor=autor
     * Primer es crea un objecte KmesSemblant, es crida els metodes getTitol(), getAutor() i getK, i es comprova que el resultat es correcte
     */
    @Test
    public void TestConstructora() throws FileNotFoundException {
        System.out.println("TestConstructora");
        String titol="1";
        String autor="JoanAbellan";
        int k=3;
        KmesSemblant consulta=new KmesSemblant(titol,autor,k);
        assertEquals(titol,consulta.getTitol());
        assertEquals(autor,consulta.getAutor());
        assertEquals(k,consulta.getK());

    }
    /**
     * Objecte de la prova: Test del metode executar de la classe KmesSemblant
     * Fitxers de dades necessaris: dades introduides manualment.
     * Valors estudiats: Estrategia caixa gris. Es passa per entrada titol i autor d'un document existit i un k.
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta
     * Primer es crea un objecte KmesSemblant, es crida els metodes executar() i es comprova que el resultat es correcte
     */
    @Test
    public void TestExecutar() throws FileNotFoundException {
        System.out.println("TestExecutar");
        String titol="1";
        String autor="JoanAbellan";
        int k=3;
        KmesSemblant consulta=new KmesSemblant(titol,autor,k);
        List<String> titols=Arrays.asList("2","8","3");
        List<String> autors=Arrays.asList("MontserratAbello","JosepLluisAguilo");
        ResultatConsulta res= consulta.executar();

        Map<String,Set<String>> result=((ResultatConsultaPairs) res).getResultat();
        for (String t : result.keySet()) {
            assertTrue(autors.contains(t));
            Set<String> a=result.get(t);
            for (String ts : a) {
                assertTrue(titols.contains(ts));
            }
        }
    }

    /**
     * Objecte de la prova: Test del metode executar de la classe KmesSemblant
     * Fitxers de dades necessaris: dades introduides manualment.
     *Valors estudiats: Estrategia caixa gris. Es passa per entrada titol i autor d'un document existit i un k=0
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta quan k sigui 0, el resultat hauria de
     * ser buit primer es crea un objecte KmesSemblant, es crida els metodes executar() i es comprova que el resultat es correcte
     */
    @Test
    public void Testkigualzero() throws FileNotFoundException {
        System.out.println("cas k = 0");
        String titol="1";
        String autor="JoanAbellan";
        int k=0;
        KmesSemblant consulta=new KmesSemblant(titol,autor,k);

        ResultatConsulta res= consulta.executar();

        Map<String,Set<String>> result=((ResultatConsultaPairs) res).getResultat();
        assertEquals(0,result.size());


    }

    /**
     * Objecte de la prova: Test del metode executar de la classe KmesSemblant
     * Fitxers de dades necessaris: dades introduides manualment.
     * Valors estudiats: Estrategia caixa gris. Es passa per entrada titol i autor d'un document existit i un k mes gran que nombre de documents
     * Operativa: En aquest test es comprova que funciona correctament aquesta consulta quan k sigui mes gran que nombre de documents, el resultat hauria de
     * ser tots documents menys el que introduit primer es crea un objecte KmesSemblant, es crida els metodes executar() i es comprova que el resultat es correcte
     */
    @Test
    public void TestKMesGranQueNombreDeDocument() throws FileNotFoundException {
        System.out.println("cas k >= nombre de documents");
        String titol = "1";
        String autor = "JoanAbellan";
        int k = 9;
        KmesSemblant consulta = new KmesSemblant(titol, autor, k);
        List<String> titols = Arrays.asList("2", "8", "3","4","5","6","7");
        List<String> autors = Arrays.asList("MontserratAbello", "JosepLluisAguilo","RaimondAguiloBartolome","JoanAdellAlvarez","JoanAgut","JosepMariaAinaud");
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
