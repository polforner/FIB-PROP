package domini.Controladors;

import domini.Clases.*;
import domini.controladors.ControladorConsulta;
import domini.controladors.CtrlDocument;
import domini.controladors.CtrlExprBooleanes;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

public class TestControladorConsulta {
    @BeforeClass
    public static void setUp() throws Exception {
        CtrlDocument cd = CtrlDocument.getInstance();
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
        List<String> contingut7=Arrays.asList("que era hombre docto, graduado en Cigüenza","Con estas razones perdía el pobre caballero el juicio, y desvelábase por entenderlas");
        cd.nouDocument(autor,titol,contingut7);
        titol="8";
        autor="MontserratAbello";
        List<String> contingut8= Arrays.asList("En fe del buen acogimiento y honra que hace Vuestra Excelencia a toda suerte de libros","En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero");
        cd.nouDocument(autor,titol,contingut8);
        titol="10";
        autor="Prop";
        cd.nouDocument(autor,titol,contingut6);
        titol="11";
        autor="PAR";
        cd.nouDocument(autor,titol,contingut6);

    }

    /**
     * Objecte de la prova: Test del mètode ControladorConsulta() de la classe ControladorConsulta
     * Fitxers de dades necessaris: dades introduïdes manualment.
     * Valors estudiats: Estratègia caixa gris. Es crea un objecte ControladorConsulta.
     * Operativa: En aquest test es comprova que es crea correctament un objecte ControladorConsulta amb id_=1 i tots maps buits
     * Primer es crea un objecte ControladorConsulta, es crida els Getters i es comprova que el resultat és correcte
     */
    @Test
    public void TestConstructora() throws FileNotFoundException {
        System.out.println("TestConstructora");
        ControladorConsulta cc=new ControladorConsulta();
        assertEquals(1,cc.getId());
        Map<Integer,ResultatConsultaNoms> resultatConsultaNomsMap=cc.getResultatsConsultaNoms();
        Map<Integer, ResultatConsultaPairs> resultatConsultaPairsMap=cc.getResultatsConsultaPairs();
        Map<Integer, ResultatContingut> resultatConsultaContingutsMap=cc.getResultatsConsultaContingut();
        assertTrue(resultatConsultaContingutsMap.isEmpty());
        assertTrue(resultatConsultaPairsMap.isEmpty());
        assertTrue(resultatConsultaNomsMap.isEmpty());
    }


    /**
     * Objecte de la prova: Test del mètode ConsultaKdocumentRellevant(query,k) de la classe ControladorConsulta
     * Fitxers de dades necessaris: dades introduïdes manualment.
     * Valors estudiats: Estratègia caixa gris.  Es passa per entrada una llista de paraules i un k.
     * Operativa: En aquest test es comprova que el ControladorConsulta pot executar correctament la ConsultaKdocumentRellevant
     * Primer es crea un objecte ControladorConsulta, es crida ConsultaKdocumentRellevant(query,k)
     * i es comprova que el resultat és correcte
     */
    @Test
    public void TestExecucioKmesRellevant() throws FileNotFoundException {
        System.out.println("TestExecucioKmesRellevant");
        ControladorConsulta cc=new ControladorConsulta();
        List<String> query=Arrays.asList("acogimiento","honra","excelentcia","buen");
        int k=3;
        Map<String,Set<String>> resultat=cc.ConsultaKdocumentRellevant(query,k);
        List<String> titolsEsperats=Arrays.asList("8","2","1");
        List<String> autorsEsperats=Arrays.asList("JoanAbellan","MontserratAbello");
        for (String autor : resultat.keySet()) {
            assertTrue(autorsEsperats.contains(autor));
            for (String titol : resultat.get(autor)) {
                assertTrue(titolsEsperats.contains(titol));
            }
        }
        assertEquals(2,cc.getId());
        Map<Integer,ResultatConsultaNoms> resultatConsultaNomsMap=cc.getResultatsConsultaNoms();
        Map<Integer, ResultatConsultaPairs> resultatConsultaPairsMap=cc.getResultatsConsultaPairs();
        Map<Integer, ResultatContingut> resultatConsultaContingutsMap=cc.getResultatsConsultaContingut();
        assertTrue(resultatConsultaContingutsMap.isEmpty());
        assertEquals(1,resultatConsultaPairsMap.size());
        assertTrue(resultatConsultaNomsMap.isEmpty());
    }
    /**
     * Objecte de la prova: Test del mètode ConsultaKdocumentSemblant(titol,autor,k) de la classe ControladorConsulta
     * Fitxers de dades necessaris: dades introduïdes manualment.
     * Valors estudiats: Estratègia caixa gris.  Es passa per entrada titol i autor d'un document existit i un k.
     * Operativa: En aquest test es comprova que el ControladorConsulta pot executar correctament la ConsultaKdocumentSemblant
     * Primer es crea un objecte ControladorConsulta, es crida ConsultaKdocumentSemblant(titol,autor,k)
     * i es comprova que el resultat és correcte
     */
    @Test
    public void TestExecucioKmesSemblant() throws FileNotFoundException {
        System.out.println("TestExecucioKmesSemblant");
        ControladorConsulta cc=new ControladorConsulta();

        String t="1";
        String a="JoanAbellan";
        int k=3;
        Map<String,Set<String>> resultat=cc.ConsultaKdocumentSemblant(t,a,k);
        List<String> titolsEsperats=Arrays.asList("2","8","3");
        List<String> autorsEsperats=Arrays.asList("MontserratAbello","JosepLluisAguilo");
        for (String autor : resultat.keySet()) {
            assertTrue(autorsEsperats.contains(autor));
            for (String titol : resultat.get(autor)) {
                assertTrue(titolsEsperats.contains(titol));
            }
        }
        assertEquals(2,cc.getId());
        Map<Integer,ResultatConsultaNoms> resultatConsultaNomsMap=cc.getResultatsConsultaNoms();
        Map<Integer, ResultatConsultaPairs> resultatConsultaPairsMap=cc.getResultatsConsultaPairs();
        Map<Integer, ResultatContingut> resultatConsultaContingutsMap=cc.getResultatsConsultaContingut();
        assertTrue(resultatConsultaContingutsMap.isEmpty());
        assertEquals(1,resultatConsultaPairsMap.size());
        assertTrue(resultatConsultaNomsMap.isEmpty());
    }

    /**
     * Objecte de la prova: Test del mètode ConsultaPrefixAutor(prefix) de la classe ControladorConsulta
     * Fitxers de dades necessaris: dades introduïdes manualment.
     * Valors estudiats: Estratègia caixa gris.  Es passa per entrada un prefix.
     * Operativa: En aquest test es comprova que el ControladorConsulta pot executar correctament la ConsultaPrefixAutor
     * Primer es crea un objecte ControladorConsulta, es crida ConsultaKdocumentSemblant(titol,autor,k)
     * i es comprova que el resultat és correcte
     */
    @Test
    public void TestExecucioPrefixAutor() throws FileNotFoundException {
        System.out.println("TestExecucioPrefixAutor");
        ControladorConsulta cc=new ControladorConsulta();
        String prefix="Jo";

        Set<String> resultat=cc.ConsultaPrefixAutor(prefix);

        List<String> resultatEsperat= Arrays.asList("JoanAbellan","JosepLluisAguilo","JoanAdellAlvarez","JoanAgut","JosepMariaAinaud");

        assertTrue(resultat.containsAll(resultatEsperat)&& resultatEsperat.containsAll(resultat));
        assertEquals(2,cc.getId());
        Map<Integer,ResultatConsultaNoms> resultatConsultaNomsMap=cc.getResultatsConsultaNoms();
        Map<Integer, ResultatConsultaPairs> resultatConsultaPairsMap=cc.getResultatsConsultaPairs();
        Map<Integer, ResultatContingut> resultatConsultaContingutsMap=cc.getResultatsConsultaContingut();
        assertTrue(resultatConsultaContingutsMap.isEmpty());
        assertEquals(1,resultatConsultaNomsMap.size());
        assertTrue(resultatConsultaPairsMap.isEmpty());
        resultat=cc.ConsultaPrefixAutor("P");
        assertEquals(Set.of("PAR","Prop"),resultat);
    }

    @Test
    public void TestExecucioTitolsAutor() throws FileNotFoundException {
        System.out.println("TestExecucioTitolsAutor");
        ControladorConsulta cc=new ControladorConsulta();
        String autor="JoanAbellan";

        Set<String> resultat=cc.Consultatitolsautor(autor);
        List<String> resultatEsperat= Arrays.asList("1");
        assertTrue(resultat.containsAll(resultatEsperat)&& resultatEsperat.containsAll(resultat));
        assertEquals(2,cc.getId());
        Map<Integer,ResultatConsultaNoms> resultatConsultaNomsMap=cc.getResultatsConsultaNoms();
        Map<Integer, ResultatConsultaPairs> resultatConsultaPairsMap=cc.getResultatsConsultaPairs();
        Map<Integer, ResultatContingut> resultatConsultaContingutsMap=cc.getResultatsConsultaContingut();
        assertTrue(resultatConsultaContingutsMap.isEmpty());
        assertEquals(1,resultatConsultaNomsMap.size());
        assertTrue(resultatConsultaPairsMap.isEmpty());
    }
    @Test
    public void TestContingut() throws FileNotFoundException {
        System.out.println("TestContingut");
        ControladorConsulta cc=new ControladorConsulta();
        List<String> contingut=cc.ConsultaContingutDocument("5","JoanAdellAlvarez");
        List<String> contingutEsperat=Arrays.asList("le parecían tan bien como los que compuso el famoso Feliciano de Silva");
        assertEquals(contingut,contingutEsperat);
        assertEquals(2,cc.getId());
        Map<Integer,ResultatConsultaNoms> resultatConsultaNomsMap=cc.getResultatsConsultaNoms();
        Map<Integer, ResultatConsultaPairs> resultatConsultaPairsMap=cc.getResultatsConsultaPairs();
        Map<Integer, ResultatContingut> resultatConsultaContingutsMap=cc.getResultatsConsultaContingut();
        assertTrue(resultatConsultaNomsMap.isEmpty());
        assertEquals(1,resultatConsultaContingutsMap.size());
        assertTrue(resultatConsultaPairsMap.isEmpty());
    }

    @Test
    public void TestBooleana() throws FileNotFoundException {
        System.out.println("TestBooleana");
        String Expressio="mazana";
        ControladorConsulta cc=new ControladorConsulta();

        Map<String,Set<String>> resultat = cc.ConsultaExpressioBooleana(Expressio);

        assertTrue(resultat.isEmpty());
    }
    @Test
    public void TestOrdenacio() throws FileNotFoundException {
        System.out.println("TestOrdenacio");
        ControladorConsulta cc=new ControladorConsulta();
        String prefix="Jo";

        Set<String> resultat=cc.ConsultaPrefixAutor(prefix);
        cc.ordenarConsulta(1,"Alfabeticament");
        Set<String> resultatEsperat= new TreeSet<>(Arrays.asList("JoanAbellan","JoanAdellAlvarez","JoanAgut","JosepLluisAguilo","JosepMariaAinaud"));

        Map<Integer,ResultatConsultaNoms> resultatConsultaNomsMap=cc.getResultatsConsultaNoms();
        resultat=resultatConsultaNomsMap.get(1).getResultat();
        Iterator<String> it1=resultat.iterator();
        Iterator<String> it2=resultatEsperat.iterator();

        assertEquals(resultatEsperat.size(),resultat.size());
        while (it1.hasNext() && it2.hasNext()) {
            assertEquals(it1.next(),it2.next());
        }
        resultatEsperat= new LinkedHashSet<>(Arrays.asList("JosepMariaAinaud","JosepLluisAguilo","JoanAgut","JoanAdellAlvarez","JoanAbellan"));

        cc.ordenarConsulta(1,"Inversament");
        resultatConsultaNomsMap=cc.getResultatsConsultaNoms();
        resultat=resultatConsultaNomsMap.get(1).getResultat();
        it1=resultat.iterator();
        it2=resultatEsperat.iterator();
        assertEquals(resultatEsperat.size(),resultat.size());
        while (it1.hasNext() && it2.hasNext()) {
            assertEquals(it1.next(),it2.next());
        }

        List<String> contingut=cc.ConsultaContingutDocument("7","JosepMariaAinaud");
        List<String> resultEsperat=Arrays.asList("que era hombre docto, graduado en Cigüenza","Con estas razones perdía el pobre caballero el juicio, y desvelábase por entenderlas");
        cc.ordenarConsulta(2,"Alfabeticament");
        Map<Integer, ResultatContingut> resultatConsultaContingutsMap=cc.getResultatsConsultaContingut();
        contingut= resultatConsultaContingutsMap.get(2).getResultat();
        it1=contingut.listIterator();
        it2=resultEsperat.listIterator();
        assertEquals(resultEsperat.size(),contingut.size());
        while (it1.hasNext() && it2.hasNext()) {
            assertEquals(it1.next(),it2.next());
        }
        cc.ordenarConsulta(2,"Inversament");
        contingut= resultatConsultaContingutsMap.get(2).getResultat();
        it1=contingut.listIterator();
        it2=resultEsperat.listIterator();
        assertEquals(resultEsperat.size(),contingut.size());
        while (it1.hasNext() && it2.hasNext()) {
            assertEquals(it1.next(),it2.next());
        }

        String titol="1";
        String autor="JoanAbellan";
        int k=3;
        Map<String,Set<String>> resultpair=cc.ConsultaKdocumentSemblant(titol,autor,k);
        Map<String,Set<String>> resultpairEsperat=new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null || o2==null) {
                    return 0;
                }
                return o1.compareTo(o2);
            }
        });
        resultpairEsperat.put("MontserratAbello",new TreeSet<>(Arrays.asList("8","2")));
        resultpairEsperat.put("JosepLluisAguilo",Set.of("3"));
        cc.ordenarConsulta(3,"Alfabeticament");
        resultpair=cc.getResultatsConsultaPairs().get(3).getResultat();
        it1=resultpair.keySet().iterator();
        it2=resultpairEsperat.keySet().iterator();
        assertEquals(resultpairEsperat.size(),resultpair.size());
        while (it1.hasNext() && it2.hasNext()) {
            String autor1=it1.next();
            String autor2=it2.next();
            assertEquals(autor1,autor2);
            Iterator it3 = resultpair.get(autor1).iterator();
            Iterator it4 = resultpairEsperat.get(autor2).iterator();
            assertEquals(resultpairEsperat.get(autor1).size(),resultpair.get(autor2).size());
            while (it3.hasNext() && it4.hasNext()) {
                assertEquals(it3.next(),it4.next());
            }

        }

        cc.ordenarConsulta(3,"Inversament");
         resultpairEsperat=new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1 == null || o2==null) {
                    return 0;
                }
                return o2.compareTo(o1);
            }
        });
         Set<String> value=new TreeSet<>(new Comparator<String>() {
             @Override
             public int compare(String o1, String o2) {
                 if (o1 == null || o2==null) {
                     return 0;
                 }
                 return o2.compareTo(o1);
             }
         });
         value.addAll(Set.of("8","2"));
        resultpairEsperat.put("MontserratAbello",value);
        resultpairEsperat.put("JosepLluisAguilo",Set.of("3"));
        resultpair=cc.getResultatsConsultaPairs().get(3).getResultat();
        it1=resultpair.keySet().iterator();
        it2=resultpairEsperat.keySet().iterator();
        assertEquals(resultpairEsperat.size(),resultpair.size());
        while (it1.hasNext() && it2.hasNext()) {
            String autor1=it1.next();
            String autor2=it2.next();
            assertEquals(autor1,autor2);
            Iterator it3 = resultpair.get(autor1).iterator();
            Iterator it4 = resultpairEsperat.get(autor2).iterator();
            assertEquals(resultpairEsperat.get(autor1).size(),resultpair.get(autor2).size());
            while (it3.hasNext() && it4.hasNext()) {
                assertEquals(it3.next(),it4.next());
            }

        }





    }

}