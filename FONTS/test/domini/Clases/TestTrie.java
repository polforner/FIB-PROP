package domini.Clases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domini.Clases.Trie;
import java.util.*;


public class TestTrie {

    @Test
    /**
     * Objectiu del test: Comprobar el correcte funcionament de la constructora
     */
    public void testConstructora() {
        Trie trie = new Trie();
        assertNotNull(trie);
    }
    @Test
    /**
     * Objectiu de Test: Comprobar que, al crear un Trie, aquest no te paraules
     */
    public void testInserirParaules1() {
        Trie trie = new Trie();
        Set<String> resultat= new HashSet<>();
        assertEquals(resultat, trie.buscarParaules(""));
    }
    /**
     * Objectiu del test: Comprobar que al inserir una paraula aquesta s'insereixi correctament
     */
    @Test
    public void testInserirParaules2() throws Exception{
        String paraula = "hola";
        Trie trie = new Trie();
        trie.inserirParaula(paraula);
        Set<String> resultat= new HashSet<>();
        resultat.add(paraula);
        assertEquals(resultat, trie.buscarParaules(paraula));
    }
    /**
     * Objectiu del test: Comprobar que al afegir 2 paraules amb cap prefix igual aquestes s'insreixen correctament
     */
    @Test
    public void testInserirParaules3() throws Exception{
        String paraula1 = "hola";
        String paraula2 = "adeu";
        Trie trie = new Trie();
        trie.inserirParaula(paraula1);
        trie.inserirParaula(paraula2);
        Set<String> resultat= new HashSet<>();
        resultat.add(paraula1);
        resultat.add(paraula2);
        assertEquals(resultat, trie.buscarParaules(""));
    }
    /**
     * Objectiu del test: Comprobar que al afegir 2 paraules de amb algun prefix igual aquestes s'insreixen correctament
     */
    @Test
    public void testInserirParaules4() throws Exception{
        String paraula1 = "prova1";
        String paraula2 = "prova2";
        Trie trie = new Trie();
        trie.inserirParaula(paraula1);
        trie.inserirParaula(paraula2);
        Set<String> resultat= new HashSet<>();
        resultat.add(paraula1);
        resultat.add(paraula2);
        assertEquals(resultat, trie.buscarParaules(""));
    }
    /**
     * Objectiu del test: Comprobar que al inserir 3 paraules amb diferent prefix aquestes s'insereixen correctament
     */
    @Test
    public void testInserirParaules5() throws Exception{
        String paraula1 = "gris";
        String paraula2 = "blanc";
        String paraula3 = "negre";
        Trie trie = new Trie();
        trie.inserirParaula(paraula1);
        trie.inserirParaula(paraula2);
        trie.inserirParaula(paraula3);
        Set<String> resultat= new HashSet<>();
        resultat.add(paraula1);
        resultat.add(paraula2);
        resultat.add(paraula3);
        assertEquals(resultat, trie.buscarParaules(""));
    }
    /**
     * Objectiu del test: Comprobar que al inserir 2 paraules iguals dona una excepcio
     */
    @Test(expected = Exception.class)
    public void testInserirParaules6() throws Exception{
        String paraula1 = "prova";
        String paraula2 = "prova";
        Trie trie = new Trie();
        trie.inserirParaula(paraula1);
        trie.inserirParaula(paraula2);
    }
    /**
     * Objectiu del test: Comprobar que al inserir una paraula i despres esborrarla aquesta es borra
     */
    @Test
    public void testEsborrarParaula1() throws Exception{
        String paraula = "prova";
        Trie trie = new Trie();
        trie.inserirParaula(paraula);
        Set<String> resultat = new HashSet<>();
        resultat.add(paraula);
        assertEquals(resultat, trie.buscarParaules(""));
        trie.esborrarPaula(paraula);
        resultat.remove(paraula);
        assertEquals(resultat, trie.buscarParaules(""));
        
    }
    /**
     * Objectiu del test: Comprobar que al inserir 2 paraules i borrar una d'elles l'altra no es borra (diferent prefix)
     */
    @Test
    public void testEsborrarParaula2() throws Exception{
        String paraula1 = "hola";
        String paraula2 = "adeu";
        Trie trie = new Trie();
        trie.inserirParaula(paraula1);
        trie.inserirParaula(paraula2);
        Set<String> resultat= new HashSet<>();
        resultat.add(paraula1);
        resultat.add(paraula2);
        assertEquals(resultat, trie.buscarParaules(""));
        trie.esborrarPaula(paraula2);
        resultat.remove(paraula2);
        assertEquals(resultat, trie.buscarParaules(""));
    }
    /**
     * Objectiu del test: Comprobar que al inserir 2 paraules i borrar una d'elles l'altra no es borra (mateix prefix)
     */
    @Test
    public void testEsborrarParaula3() throws Exception{
        String paraula1 = "prova1";
        String paraula2 = "prova2";
        Trie trie = new Trie();
        trie.inserirParaula(paraula1);
        trie.inserirParaula(paraula2);
        Set<String> resultat= new HashSet<>();
        resultat.add(paraula1);
        resultat.add(paraula2);
        assertEquals(resultat, trie.buscarParaules(""));
        trie.esborrarPaula(paraula2);
        resultat.remove(paraula2);
        assertEquals(resultat, trie.buscarParaules(""));
    }
    /**
     * Objectiu del test: Comprobar que al esborrar una paraula que no existeix dona una excepcio
     */
    @Test(expected = Exception.class)
    public void testEsborrarParaula4() throws Exception{
        String paraula = "prova";
        Trie trie = new Trie();
        trie.esborrarPaula(paraula);
    }
    /**
     * Objectiu del test: Comprobar que al inserir 1 paraules i borrar una diferent salta una excepcio
     */
    @Test(expected = Exception.class)
    public void testEsborrarParaula5() throws Exception{
        String paraula1 = "hola";
        String paraula2 = "adeu";
        Trie trie = new Trie();
        trie.inserirParaula(paraula1);
        Set<String> resultat= new HashSet<>();
        resultat.add(paraula1);
        assertEquals(resultat, trie.buscarParaules(""));
        trie.esborrarPaula(paraula2);
    }
    /**
     * Objectiu del test: Comprobar al buscar un prefix buit a un Trie buit no retorna res
     */
    @Test
    public void testBuscarParaules1() throws Exception{
        Trie trie = new Trie();
        Set<String> resultat= new HashSet<>();
        assertEquals(resultat, trie.buscarParaules(""));
    }
    /**
     * Objectiu del test: Comprobar al buscar un prefix  a un Trie buit no retorna res
     */
    @Test
    public void testBuscarParaules2() throws Exception{
        Trie trie = new Trie();
        Set<String> resultat= new HashSet<>();
        assertEquals(resultat, trie.buscarParaules("prova"));
    }
    /**
     * Objectiu del test: Comprobar al buscar un prefix a un Trie que no el conte no retorna res
     */
    @Test
    public void testBuscarParaules3() throws Exception{
        String paraula = "hola";
        Trie trie = new Trie();
        trie.inserirParaula(paraula);
        Set<String> resultat= new HashSet<>();
        assertEquals(resultat, trie.buscarParaules("adeu"));
    }
}
