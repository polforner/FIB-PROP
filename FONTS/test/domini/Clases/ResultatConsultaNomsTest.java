package domini.Clases;

import domini.Clases.ResultatConsultaNoms;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class ResultatConsultaNomsTest {

    @Test
    public void AlfabeticamentCorrecte() {
        Set<String> test = new HashSet<>();
        test.add("a");
        test.add("f");
        test.add("g");
        test.add("d");
        test.add("e");
        ResultatConsultaNoms rcm = new ResultatConsultaNoms(test,"test",Arrays.asList("e"));
        Set<String> resultatCorrecte = new TreeSet<String>(Arrays.asList("a","d","e","f","g"));
        rcm.ordenarAlfabeticament();
        assertEquals(resultatCorrecte,rcm.getResultat());
    }
    @Test
    public void InversamentCorrecte() {
        Set<String> test = new HashSet<String>(Arrays.asList("a","c","b","e","d"));
        ResultatConsultaNoms rcm = new ResultatConsultaNoms(test, "test",Arrays.asList("e") );
        rcm.ordenarInversament();
        Set<String> resultatCorrecte = new TreeSet<String>(Arrays.asList("e","d","c","b","a"));
        assertEquals(resultatCorrecte,rcm.getResultat());
    }

    @Test
    public void Testgetters() {
        Set<String> test = new HashSet<>();
        test.add("a");
        test.add("f");
        test.add("g");
        test.add("d");
        test.add("e");
        ResultatConsultaNoms rcm = new ResultatConsultaNoms(test,"test",Arrays.asList("e"));
        Set<String> res = rcm.getResultat();
        List<String> params = rcm.getParametres();
        String tipus = rcm.getTipusConsulta();
    }

}