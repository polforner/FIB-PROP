package domini.Clases;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestExprBooleana {
    @Test
    /**
     * Objectiu del Test: Comprobar que la constructora funciona correctament per expressions ben formades sense parentiesis, ni frases ni claudators
     */
    public void TestConstructora1() {
        //Test 1
        String expressio = "prova";
        ExprBooleana exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 2
        expressio = "prova1 & prova2";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 3
        expressio = "prova1 | prova2";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 4
        expressio = "prova1 & prova2 & prova3";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 5
        expressio = "prova1 | prova2 & prova3";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());  
    }
    @Test
    /**
     * Objectiu del Test: Comprobar que la constructora funciona correctament per expressions ben formades amb parentiesis o frases o claudators
     */
    public void TestConstructora2() {
        //Test 1
        String expressio = "(prova)";
        ExprBooleana exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 2
        expressio = "(prova1 & prova2)";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 3
        expressio = "{prova1}";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 4
        expressio = "{prova1 prova2}";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 5
        expressio = "\"prova\"";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 6
        expressio = "\"prova1 prova2\"";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());    
        //Test 7 expressio complexa
        expressio = "(((prova1 & prova2) | prova3 | {prova1 prova2}) | \"prova1\")";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());      
    }
    @Test
    /**
     * Objectiu del Test: Comprobar que la constructora funciona correctament per expressions ben formades amb negacions
     */
    public void TestConstructora3() {
        //Test 1
        String expressio = "!prova";
        ExprBooleana exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 2
        expressio = "!prova1 & !prova2";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 3
        expressio = "!!prova";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 4
        expressio = "!(prova)";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());
        //Test 5
        expressio = "!\"prova\"";
        exprBooleana = new ExprBooleana(expressio);
        assertEquals(expressio, exprBooleana.getExpresio());  
    }
    @Test(expected = IllegalArgumentException.class)
    /**
     * Objectiu del Test: Comprobar que la constructora funciona correctament per expressions mal formades
     */
    public void TestExprMalFormada1() {
        String expressio = "(";
        new ExprBooleana(expressio);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestExprMalFormada2() {
        String expressio = ")";
        new ExprBooleana(expressio);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestExprMalFormada3() {
        String expressio = "())";
        new ExprBooleana(expressio);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestExprMalFormada4() {
        String expressio = "({)}";
        new ExprBooleana(expressio);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestExprMalFormada5() {
        String expressio = "\"";
        new ExprBooleana(expressio);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestExprMalFormada6() {
        String expressio = "{prova1} (prova2)";
        new ExprBooleana(expressio);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestExprMalFormada7() {
        String expressio = "{prova1 & prova2}";
        new ExprBooleana(expressio);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestExprMalFormada8() {
        String expressio = "prova1 prova2";
        new ExprBooleana(expressio);
    }
    @Test(expected = IllegalArgumentException.class)
    public void TestExprMalFormada9() {
        String expressio = "prova1 !& prova2";
        new ExprBooleana(expressio);
    }
}
