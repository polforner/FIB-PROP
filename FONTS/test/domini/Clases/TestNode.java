package domini.Clases;

import domini.Clases.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

public class TestNode {
    /**
     * Objecte de la prova: Test constructora de la classe Node
     * Fitxers de dades necessaris: dades introduiides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte Node.
     * Operativa: En aquest test comprovem que la constructora s'inicialita adecuadament
     */
    @Test
    public void testConstructora1() {
        Node node = new Node();
        assertTrue(!node.teValor());
        assertTrue(!node.esFinal());
        assertTrue(!node.teEsquerra());
        assertTrue(!node.teDret());
        assertTrue(!node.teCentral());
    }
    /**
     * Objecte de la prova: Test del valor de la classe Node
     * Fitxers de dades necessaris: dades introduiides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte Node.
     * Operativa: En aquest test comprovem que el setter i el getter de valor funciona adecuadament
     */
    @Test
    public void testValor1() {
        Node node = new Node();
        char valor = 'a';
        node.setValor(valor);
        assertEquals(valor,node.getValor());
    }
}
