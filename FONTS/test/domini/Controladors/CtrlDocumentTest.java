package domini.Controladors;

import static org.junit.Assert.*;

import domini.Clases.Document;
import domini.controladors.CtrlDocument;
import org.junit.*;

import domini.Clases.Index;

import javax.print.DocFlavor;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class CtrlDocumentTest {

    private static CtrlDocument cd_;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void nouDocumentTest() throws Exception {
        CtrlDocument cd_ = new CtrlDocument();
        System.out.println("Test nouDocument amb Document no Existent");
        String titol="6";
        String autor="JoanAbellan";
        List<String> contingut1= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd_.nouDocument(autor,titol,contingut1);
        assertEquals(2, cd_.getId());
        titol="2";
        autor="MontserratAbello";
        List<String> contingut2 =Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros");
        cd_.nouDocument(autor,titol,contingut2);
        assertEquals(3, cd_.getId());
        titol="3";
        autor="JosepLluisAguilo";
        List<String> contingut3=Arrays.asList("En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd_.nouDocument(autor,titol,contingut3);
        assertEquals(4, cd_.getId());
        titol="4";
        autor="RaimondAguiloBartolome";
        List<String> contingut4=Arrays.asList("Es, pues, de saber que este sobredicho hidalgo, los ratos que estaba ocioso -que eran los más");
        cd_.nouDocument(autor,titol,contingut4);
        assertEquals(5, cd_.getId());
        titol="5";
        autor="JoanAdellAlvarez";
        List<String> contingut5=Arrays.asList("le parecían tan bien como los que compuso el famoso Feliciano de Silva");
        cd_.nouDocument(autor,titol,contingut5);
        assertEquals(6, cd_.getId());
        titol="1";
        autor="JoanAbellan";
        List<String> contingut6=Arrays.asList("Con estas razones perdía el pobre caballero el juicio, y desvelábase por entenderlas");
        cd_.nouDocument(autor,titol,contingut6);
        assertEquals(7, cd_.getId());
        titol="7";
        autor="JosepMariaAinaud";
        List<String> contingut7=Arrays.asList("que era hombre docto, graduado en Cigüenza","Con estas razones perdía el pobre caballero el juicio, y desvelábase por entenderlas");
        cd_.nouDocument(autor,titol,contingut7);
        assertEquals(8, cd_.getId());
        titol="8";
        autor="MontserratAbello";
        List<String> contingut8= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd_.nouDocument(autor,titol,contingut8);
        assertEquals(9, cd_.getId());
        System.setOut(standardOut);
    }

    @Test
    public void nouDocumentTestMalament() throws Exception {
        CtrlDocument cd_ = new CtrlDocument();
        System.out.println("Test nouDocument amb Document existent");
        String titol="6";
        String autor="JoanAbellana";
        List<String> contingut1= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd_.nouDocument(autor,titol,contingut1);
        assertEquals(2, cd_.getId());
        titol="6";
        autor="JoanAbellana";
        List<String> contingut2 =Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros");
        System.setOut(new PrintStream(outputStreamCaptor));
        cd_.nouDocument(autor,titol,contingut2);
        //assertEquals("Document existit",outputStreamCaptor.toString().trim());
        System.setOut(standardOut);
    }

    @Test
    public void AfegirDocumentTest() throws Exception {
        CtrlDocument cd_ = new CtrlDocument();
        System.out.println("Test AfegirDocument");
        String titol="6";
        String autor="JoanAbellan";
        List<String> contingut1= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd_.nouDocument(autor,titol,contingut1);
        assertEquals(2, cd_.getId());
        titol="2";
        autor="MontserratAbello";
        List<String> contingut2 =Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros");
        cd_.nouDocument(autor,titol,contingut2);
        assertEquals(3, cd_.getId());
        titol="3";
        autor="JosepLluisAguilo";
        List<String> contingut3=Arrays.asList("En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd_.nouDocument(autor,titol,contingut3);
        assertEquals(4, cd_.getId());
        titol="4";
        autor="RaimondAguiloBartolome";
        List<String> contingut4=Arrays.asList("Es, pues, de saber que este sobredicho hidalgo, los ratos que estaba ocioso -que eran los más");
        cd_.nouDocument(autor,titol,contingut4);
        assertEquals(5, cd_.getId());
        titol="5";
        autor="JoanAdellAlvarez";
        List<String> contingut5=Arrays.asList("le parecían tan bien como los que compuso el famoso Feliciano de Silva");
        cd_.nouDocument(autor,titol,contingut5);
        assertEquals(6, cd_.getId());
        titol="1";
        autor="JoanAbellan";
        List<String> contingut6=Arrays.asList("Con estas razones perdía el pobre caballero el juicio, y desvelábase por entenderlas");
        cd_.nouDocument(autor,titol,contingut6);
        assertEquals(7, cd_.getId());
        titol="7";
        autor="JosepMariaAinaud";
        List<String> contingut7=Arrays.asList("que era hombre docto, graduado en Cigüenza","Con estas razones perdía el pobre caballero el juicio, y desvelábase por entenderlas");
        cd_.nouDocument(autor,titol,contingut7);
        assertEquals(8, cd_.getId());
        titol="8";
        autor="MontserratAbello";
        List<String> contingut8= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd_.nouDocument(autor,titol,contingut8);
        Map<String,Map<String,Integer>> titolsAutor = new HashMap<String,Map<String,Integer>>();
        Map<String,Integer> titols1 = new HashMap<>();
        titols1.put("6",1);
        titols1.put("1",6);
        titolsAutor.put("JoanAbellan",titols1);
        Map<String,Integer> titols2 = new HashMap<>();
        titols2.put("2",2);
        titols2.put("8",8);
        titolsAutor.put("MontserratAbello", titols2);
        Map<String,Integer> titols3 = new HashMap<>();
        titols3.put("3",3);
        titolsAutor.put("JosepLluisAguilo", titols3);
        Map<String,Integer> titols4 = new HashMap<>();
        titols4.put("4",4);
        titolsAutor.put("RaimondAguiloBartolome", titols4);
        Map<String,Integer> titols5 = new HashMap<>();
        titols5.put("5",5);
        titolsAutor.put("JoanAdellAlvarez", titols5);
        Map<String,Integer> titols6 = new HashMap<>();
        titols6.put("7",7);
        titolsAutor.put("JosepMariaAinaud", titols6);
        System.out.println(titolsAutor);
        assertEquals(titolsAutor, cd_.getTitolsAutor());
        Map<Integer, Document> ids = new TreeMap<>();
        ids.put(1,cd_.getDocument("6","JoanAbellan"));
        ids.put(6,cd_.getDocument("1","JoanAbellan"));
        ids.put(2,cd_.getDocument( "2","MontserratAbello"));
        ids.put(8,cd_.getDocument("8" ,"MontserratAbello"));
        ids.put(3,cd_.getDocument("3" ,"JosepLluisAguilo"));
        ids.put(4,cd_.getDocument("4" ,"RaimondAguiloBartolome"));
        ids.put(5,cd_.getDocument("5","JoanAdellAlvarez"));
        ids.put(7,cd_.getDocument("7","JosepMariaAinaud"));
        assertEquals(ids, cd_.getIdentificadors());
    }

    @Test
    public void eliminarDocumentTest() throws Exception {
        CtrlDocument cd_ = new CtrlDocument();
        System.out.println("Test AfegirDocument");
        String titol = "6";
        String autor = "JoanAbellan";
        List<String> contingut1 = Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros", "En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd_.nouDocument(autor, titol, contingut1);
        titol = "2";
        autor = "MontserratAbello";
        List<String> contingut2 = Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros");
        cd_.nouDocument(autor, titol, contingut2);
        cd_.eliminarDocument("2", "MontserratAbello");
        Map<String, Map<String, Integer>> titolsAutor = new HashMap<String, Map<String, Integer>>();
        Map<String, Integer> titols1 = new HashMap<>();
        titols1.put("6", 1);
        titolsAutor.put("JoanAbellan", titols1);
        assertEquals(titolsAutor, cd_.getTitolsAutor());
    }
}