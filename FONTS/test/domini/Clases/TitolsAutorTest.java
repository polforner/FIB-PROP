package domini.Clases;

import domini.Clases.*;
import domini.controladors.CtrlDocument;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;


public class TitolsAutorTest {

    private static  CtrlDocument cd;

    @BeforeClass
    public static void inicialitza() throws Exception {
        cd = CtrlDocument.getInstance();
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

    @Test
    public void TitolsAutorTest() throws Exception {
        System.out.println("TestConstructora");
        String autor = "MontserratAbello";
        TitolsAutor test = new TitolsAutor(autor);
        assertEquals(autor, test.getAutor());
    }

    @Test
    public void executarTestExistint() throws Exception {
        System.out.println("Test TitolsAutor, amb autor existent");
        String autor = "MontserratAbello";
        TitolsAutor test = new TitolsAutor(autor);
        Set<String> Titols = new HashSet<>(Arrays.asList("2", "8"));
        ResultatConsulta rc = test.executar();
        assertEquals(Titols, ((ResultatConsultaNoms)rc).getResultat());

    }
    @Test
    public void executarTestNoExistent() throws FileNotFoundException {
        System.out.println("Test TitolsAutor, amb autor NO existent");
        String autor = "MontserratAbella";
        TitolsAutor test = new TitolsAutor(autor);
        Set<String> Titols = new HashSet<>(Arrays.asList("2", "8"));
        Exception e = assertThrows(RuntimeException.class,
                () -> test.executar());
        assertEquals(e.getMessage(), "No existeix l'autor introduit");

    }

}