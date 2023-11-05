package domini.Clases;

import domini.controladors.CtrlDocument;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ContingutDocumentTest {

    @Test
    public void testExecucioDeContingutDocumentCorrecte() throws Exception {
        System.out.println("-----------------------Execucio de contingut document correcte ----------------------------");
        String autor = "Bernat Homs";
        String titol = "La rateta que escombrava l'escaleta";
        var contingutDoc = new ArrayList<String>();
        contingutDoc.add("Primera linia del contingut");
        contingutDoc.add("Linia final del document");
        CtrlDocument ctrl = CtrlDocument.getInstance();
        ctrl.nouDocument(autor, titol, contingutDoc);
        ContingutDocument cdd = new ContingutDocument(titol, autor);
        ResultatContingut rc = cdd.executar();
        assertEquals(contingutDoc, rc.getResultat());
        System.out.println("------------------------------------------------------------------------------------------------------");
    }
}