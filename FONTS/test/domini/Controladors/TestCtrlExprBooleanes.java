package domini.Controladors;

import domini.Clases.ExprBooleana;
import domini.controladors.CtrlExprBooleanes;
import java.util.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class TestCtrlExprBooleanes {
    @Test
    /**
     * Objectiu del test: Comprobar que la classe es singleton
     */
    public void testSingleton() {
        CtrlExprBooleanes ctrl1  = CtrlExprBooleanes.getInstance();
        CtrlExprBooleanes ctrl2  = CtrlExprBooleanes.getInstance();
        assertEquals(ctrl1, ctrl2);
    }
    @Test
    /**
     * Objectiu del test: Comprobar que es guarden correctament les ExprBooleanes
     */
    public void testSetExprBooleana1() {
        String expr = "prova";
        CtrlExprBooleanes ctrl  = CtrlExprBooleanes.getInstance();
        int i = ctrl.setExprBooleana(expr);
        assertEquals("prova", ctrl.getExprBooleana(i).getExpresio());
    }
    @Test
    /**
     * Objectiu del test: Comprobar que es guarden be diferents expressions booleanes
     */
    public void testSetExprBooleana2() {
        String expr1 = "prova1";
        String expr2 = "prova2";
        CtrlExprBooleanes ctrl  = CtrlExprBooleanes.getInstance();
        ctrl.setExprBooleana(expr1);
        ctrl.setExprBooleana(expr2);
        Map<Integer,ExprBooleana> result = new HashMap<>();
        result.put(0, new ExprBooleana(expr1));
        result.put(1, new ExprBooleana(expr2));
        assertEquals(result, ctrl.getExpressions());
    }
    @Test (expected = IllegalArgumentException.class)
    /**
     * Objectiu del test: Comprobar que es guarden be diferents expressions booleanes
     */
    public void testEsborrarExprBooleana1() {
        CtrlExprBooleanes ctrl  = CtrlExprBooleanes.getInstance();
        ctrl.esborraExprBooleana(0);
    }
    @Test
    /**
     * Objectiu del test: Comprobar que es guarden correctament les ExprBooleanes
     */
    public void testEsborrarExprBooleana2() {
        String expr = "prova";
        CtrlExprBooleanes ctrl  = CtrlExprBooleanes.getInstance();
        int i = ctrl.setExprBooleana(expr);
        ctrl.esborraExprBooleana(i);
        Map<Integer,ExprBooleana> result = new HashMap<>();
        assertEquals(result, ctrl.getExpressions());
    }
}
