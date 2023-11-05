package domini.Controladors;

import domini.controladors.ControladorConsulta;
import domini.controladors.CtrlDocument;
import domini.controladors.CtrlDomini;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;

import static org.junit.Assert.*;

public class CtrlDominiTest {

    private static CtrlDomini cdom_;

    @BeforeClass
    public static void init() throws Exception {
        cdom_ = new CtrlDomini();
        String titol = "Som-hi Barça!";
        String autor = "Prop";
        List<String> contingut = new ArrayList<>(Arrays.asList("I si tots animem guanyarem!"));
        cdom_.crearDocument(autor,titol,contingut);
        String titol1 = "Test2";
        String autor1 = "Prop";
        List<String> contingut1 = new ArrayList<>(Arrays.asList("I si tots animem guanyarem!"));
        cdom_.crearDocument(autor1,titol1,contingut1);
        String titol2 = "Test3";
        String autor2 = "Prop";
        List<String> contingut2 = new ArrayList<>(Arrays.asList("I si tots animem guanyarem!"));
        cdom_.crearDocument(autor2,titol2,contingut2);
    }

    @Test
    public void crearDocumentTest() throws Exception {
        CtrlDocument cd = cdom_.getCtrlDoc();
        Map<String,Map<String, Integer>> titolsautor = new HashMap<>();
        Map<String,Integer> titols = new HashMap<>();
        titols.put("Som-hi Barça!",1);
        titols.put("Test2", 2);
        titols.put("Test3", 3);
        titolsautor.put("Prop", titols);
        System.out.println(cd.getTitolsAutor());
        assertEquals(titolsautor, cd.getTitolsAutor());
    }

    @Test
    public void modificarTitol() throws Exception {
        String titol = "Som-hi Barça!";
        String autor = "PAR";
        CtrlDocument cd = cdom_.getCtrlDoc();
        cdom_.modificarTitol("Som-hi Barcelona!", titol, autor);
        String TitolModificat = cd.getDocument("Som-hi Barcelona!", "PAR").getTitol();
        assertEquals(TitolModificat, "Som-hi Barcelona!");
    }

    @Test
    public void modificarAutor() throws Exception {
        String titol = "Som-hi Barça!";
        String autor = "Prop";
        cdom_.modificarAutor(titol,"PAR", autor);
        CtrlDocument cd = cdom_.getCtrlDoc();
        String AutorModificat = cd.getDocument(titol, "PAR").getAutor();
        assertEquals(AutorModificat, "PAR");

    }

    @Test
    public void modificarContingut() throws Exception {
        String titol = "Test2";
        String autor = "Prop";
        cdom_.modificarContingut(titol,autor, Arrays.asList("Un dia de partit, al gol Nord vaig anar"));
        assertEquals(Arrays.asList("Un dia de partit, al gol Nord vaig anar"), cdom_.consultaContingutDocument(titol,autor));
    }

    @Test
    public void consultaTitolsAutor() throws Exception {
        Set<String> comprovacio = new HashSet<>(Arrays.asList("Test2"));
        assertEquals(comprovacio,cdom_.consultaTitolsAutor("Prop"));
    }

    @Test
    public void consultaPrefixAutor() throws Exception {
        Set<String> comprovacio = new HashSet<>(Arrays.asList("PAR"));
        assertEquals(comprovacio,cdom_.consultaPrefixAutor("P"));
    }

    @Test
    public void consultaContingutDocument() throws Exception {
        List<String> comprovacio = Arrays.asList("I si tots animem guanyarem!");
        assertEquals(comprovacio,cdom_.consultaContingutDocument("Som-hi Barcelona!", "PAR"));
        assertEquals(comprovacio,cdom_.consultaContingutDocument("Test2", "Prop"));
        assertEquals(comprovacio,cdom_.consultaContingutDocument("Test3", "Prop"));
    }

    @Test
    public void consultaKDocumentsSemblants() throws Exception {
        Map<String,Set<String>> comprovacio = new HashMap<>();
        Set<String> titols = new HashSet<>(Arrays.asList("Test2"));
        comprovacio.put("Prop",titols);
        assertEquals(comprovacio,cdom_.consultaKDocumentsSemblants("Test3", "Prop", 2));
    }

    @Test
    public void consultaBooleana() throws Exception {
        Map<String,Set<String>> comprovacio = new HashMap<>();
        Set<String> titols = new HashSet<>(Arrays.asList("Test2", "Test3"));
        Set<String> titols2 = new HashSet<>(Arrays.asList("Som-hi Barcelona!"));
        comprovacio.put("PAR", titols2);
        comprovacio.put("Prop", titols);
        //assertEquals(comprovacio,cdom_.consultaBooleana("a & i"));
        cdom_.consultaBooleana("a & i");
    }

    @Test
    public void ordenarConsulta() throws Exception {
       cdom_.ordenarConsulta(4,"Alfabeticament");

    }

    @Test
    public void afegirExpressioBooleana() throws Exception {
        cdom_.afegirExpressioBooleana("a & b");
    }

    @Test
    public void borrarExpressioBooleana() throws Exception {
        cdom_.borrarExpressioBooleana(0);
    }

    @Test
    public void operarConsulta() throws FileNotFoundException {
        CtrlDocument cd = cdom_.getCtrlDoc();
        cdom_.consultaTitolsAutor("Prop");
        cdom_.consultaTitolsAutor("PAR");
        cdom_.operarConsulta(2,3, "Interseccio" );
    }

    @Test
    public void modificarExprBooleana() throws Exception {
        cdom_.afegirExpressioBooleana("a & b");
        cdom_.modificarExprBooleana(1, "a & c");
    }

    @Test
    public void eliminarDocument() throws Exception {
        String titol = "Test3";
        String autor = "Prop";
        cdom_.eliminarDocument(titol,autor);
        CtrlDocument cd = cdom_.getCtrlDoc();
        Exception e = assertThrows(RuntimeException.class,
                () -> cd.getDocument(titol,autor));;
        assertEquals(e.getMessage(), "el Document identificat per Autor: " + autor + " i Titol: " + titol + " no existeix");

    }
}