package domini.Clases;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestOperacionResultatNoms {
    /**
     * Objecte de la prova: Test de la funcio interseccio de la classe OperacionResultatNoms
     * Fitxers de dades necessaris: dades introduiides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte OperacionResultatNoms.
     * Operativa: En aquest test comprovem que la funcio interserccio funciona bé.
     */
    @Test
    public void testInterseccio() {
        Set<String> resultat1=Set.of("a","b","c","d","e");
        Set<String> resultat2=new HashSet<>();
        Set<String> resultaOperacio= new HashSet<>();
        OperacionResultatNoms op1=new OperacionResultatNoms(resultat1,resultat2);
        assertTrue(op1.interseccio().isEmpty());

        resultat2=new HashSet<>(Set.of("a","c"));
        op1=new OperacionResultatNoms(resultat1,resultat2);
        resultaOperacio=op1.interseccio();
        assertEquals(resultat2,resultaOperacio);
        resultat2.add("f");
        op1=new OperacionResultatNoms(resultat1,resultat2);
        resultaOperacio=op1.interseccio();
        assertEquals(Set.of("a","c"),resultaOperacio);
    }
    /**
     * Objecte de la prova: Test de la funcio unio de la classe OperacionResultatNoms
     * Fitxers de dades necessaris: dades introduiides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte OperacionResultatNoms.
     * Operativa: En aquest test comprovem que la funcio interserccio funciona bé.
     */
    @Test
    public void TestUnio() {
        Set<String> resultat1=Set.of("a","b","c","d","e");
        Set<String> resultat2=new HashSet<>();
        Set<String> resultaOperacio= new HashSet<>();
        OperacionResultatNoms op1=new OperacionResultatNoms(resultat1,resultat2);
        assertEquals(resultat1,op1.unio());

        resultat2=new HashSet<>(Set.of("a","c"));
        op1=new OperacionResultatNoms(resultat1,resultat2);
        resultaOperacio=op1.unio();
        assertEquals(resultat1,resultaOperacio);
        resultat2.add("f");
        op1=new OperacionResultatNoms(resultat1,resultat2);
        resultaOperacio=op1.unio();
        assertEquals(Set.of("a","c","b","d","f","e"),resultaOperacio);
    }
    /**
     * Objecte de la prova: Test de la funcio diferencia de la classe OperacionResultatNoms
     * Fitxers de dades necessaris: dades introduiides manualment.
     * Valors estudiats: Estrategia caixa gris. Es crea un objecte OperacionResultatNoms.
     * Operativa: En aquest test comprovem que la funcio interserccio funciona bé.
     */
    @Test
    public void TestDiferencia() {
        Set<String> resultat1=Set.of("a","b","c","d","e");
        Set<String> resultat2=new HashSet<>();
        Set<String> resultaOperacio= new HashSet<>();
        OperacionResultatNoms op1=new OperacionResultatNoms(resultat1,resultat2);
        assertEquals(resultat1,op1.diferencia());

        resultat2=new HashSet<>(Set.of("a","c"));
        op1=new OperacionResultatNoms(resultat1,resultat2);
        resultaOperacio=op1.diferencia();
        assertEquals(Set.of("b","d","e"),resultaOperacio);
        resultat2.add("f");
        op1=new OperacionResultatNoms(resultat1,resultat2);
        resultaOperacio=op1.diferencia();
        assertEquals(Set.of("b","d","e"),resultaOperacio);
    }

    @Test
    public void TestDiferenciaSimetirica() {
        Set<String> resultat1=Set.of("a","b","c","d","e");
        Set<String> resultat2=new HashSet<>();
        Set<String> resultaOperacio= new HashSet<>();
        OperacionResultatNoms op1=new OperacionResultatNoms(resultat1,resultat2);
        assertEquals(resultat1,op1.diferenciaSimetrica());

        resultat2=new HashSet<>(Set.of("a","c"));
        op1=new OperacionResultatNoms(resultat1,resultat2);
        resultaOperacio=op1.diferenciaSimetrica();
        assertEquals(Set.of("b","d","e"),resultaOperacio);
        resultat2.add("f");
        op1=new OperacionResultatNoms(resultat1,resultat2);
        resultaOperacio=op1.diferenciaSimetrica();
        assertEquals(Set.of("b","d","e","f"),resultaOperacio);
    }
}
