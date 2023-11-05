package domini.controladors;

import domini.Clases.ExprBooleana;
import domini.exceptions.ExprMalFormadaException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class CtrlExprBooleanes implements Serializable{
/**
 * Controlador de la classe expressio booleana
 * Classe que s'ocupa de gestionar les expressions booleanes
 * @author pol.forner
 */
//PART PUBLICA
    //Funcions publiques
    /**
     * Constructora per defecte
     */
    public CtrlExprBooleanes() {
        expressions_ = new HashMap<>();
        seguentIdentificador = 0;
    }

    /**
     * Retorna la instancia singleton de la classe CtrlExprBooleanes
     * @return Un punter a la instancia singleton de CtrlExprBooleanes
     */
    public static CtrlExprBooleanes getInstance() {
        if (singletonObject == null) singletonObject = new CtrlExprBooleanes();
        return singletonObject;
    }

    /**
     * Afegeix una expressio booleana
     * @param expressio una expressio booleana
     */
    public int addExprBooleana(String expressio) throws ExprMalFormadaException{
        ExprBooleana exprBoolena = new ExprBooleana(expressio);
        expressions_.put(seguentIdentificador, exprBoolena);
        ++seguentIdentificador;
        return seguentIdentificador - 1;
    }

    /**
     * Modificar una expressio booleana
     * @param id identificador de la expressio booleana a modificar
     * @param expressio una nova expressio booleana
     */

    public void modificarExprBooleana(String expressio, int id) throws ExprMalFormadaException {
        ExprBooleana exprBoolena = new ExprBooleana(expressio);
        expressions_.replace(id, exprBoolena);
    }


    /**
     * Retorna la ExprBooleana amb identicador
     * @param identificador un enter
     * @return una ExprBooleana amb identificador
     */
    public ExprBooleana getExprBooleana(int identificador) {
        if (!expressions_.containsKey(identificador)) {
            throw new IllegalArgumentException("L'expressió amb identificador " + identificador + "no existeix");
        }
        ExprBooleana resultat = expressions_.get(identificador);
        return resultat;
    }

    /**
     * Retorna el conjunt de expressions booleanes
     * @return Un Map<Integer,ExprBooleana> amb ExprBooleanes amb identificador
     */
    public Map<Integer,ExprBooleana> getExpressions() {
        return expressions_;
    }
    /**
     * Esborra l'expressio boolena amb identificador
     * @param identificador un enter
     */
    public void esborraExprBooleana(int identificador) {
        if (!expressions_.containsKey(identificador)) {
            throw new IllegalArgumentException("L'expressió amb identificador " + identificador + " no existeix");
        }
        expressions_.remove(identificador);
    }

//PART PRIVADA
    //Atributs privats
    /**
     * Singleton CtrlExprBooleanes
     */
    private static CtrlExprBooleanes singletonObject;
    /**
     * Conjunt de ExprBoolenes
     */
    private Map<Integer,ExprBooleana> expressions_;
    /**
     * Seguent identificador usable
     */
    private int seguentIdentificador;
}