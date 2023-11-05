package domini.Clases;

import java.io.Serializable;

import domini.exceptions.*;
/**
 * Classe que defineix un Node de un Trie
 * Cada un consta de un valor que es un caracter, 3 fills i pot ser final.
 * @author pol.forner
 */
public class Node implements Serializable{
//PART PUBLICA
    //Atributs publics

    //Funcions publiques
    /**
     * Constructora per defecte
     */
    public Node() {
        valor_ = '\u0000';
        esFinal_ = false;
        esquerra_ = null;
        central_ = null;
        dret_ = null;
    }

    /**
     * Retorna el valor del node
     * @return un caracter que es el valor del node
     */
    public char getValor() {
        return valor_;
    }

    /**
     * Retorna el fill esquerra
     * @return Un node que es el fill esquerra
     */
    public Node getEsquerra() {
        if (esquerra_ == null) esquerra_ = new Node();
        return esquerra_;
    }

    /**
     * Retorna el fill central
     * @return Un node que es el fill central
     */
    public Node getCentral() {
        if (central_ == null) central_ = new Node();
        return central_;
    }

    /**
     * Retorna el fill dret
     * @return Un node que es el fill dret
     */
    public Node getDret() {
        if (dret_ == null) dret_ = new Node();
        return dret_;
    }

    /**
     * Asigna el valor del node
     * @param valor un caracter
     */
    public void setValor(char valor) {
        valor_ = valor;
    }

    /**
     * Asigna el node com a final
     */
    public void setFinal() throws NodeNoFinalException{
        if (esFinal_) throw new NodeNoFinalException("El node ja era final");
        esFinal_ = true;
    }

    /**
     * Asigna el fill esquerra 
     * @param esquerra Un node inicialitat
     */
    public void setEsquerra(Node esquerra) {
        esquerra_ = esquerra;
    }

    /**
     * Asigna el fill central
     * @param central Un node inicialitzat
     */
    public void setCentral(Node central) {
        central_ = central;
    }

    /**
     * Asigna el fill dret
     * @param dret Un node inicialitzat
     */
    public void setDret(Node dret) {
        dret_ = dret;
    }

    /**
     * Retorna si el node te un valor asignat
     * @return Un Boolea
     */
    public Boolean teValor() {
        return valor_ != '\u0000';
    }

    /**
     * Retorna si el node esta asignat com a final
     * @return Un Boolea
     */
    public Boolean esFinal() {
        return esFinal_;
    }

    /**
     * Retorna si te un fill esquerra inicialitzat
     * @return Un boolea
     */
    public Boolean teEsquerra() {
        return esquerra_ != null;
    }

    /**
     * Retorna si te un fill central inicialitzat
     * @return Un boolea
     */
    public Boolean teCentral() {
        return central_ != null;
    }

    /**
     * Retorna si te un fill dret inicialitzat
     * @return Un boolea
     */
    public Boolean teDret() {
        return dret_ != null;
    }

    /**
     * Desasigna com a final el node si era final.
     * @exception Exception Si el node no es final.
     */
    public void treuFinal() throws NodeNoFinalException{
        if (!esFinal_) throw new NodeNoFinalException("El node no era final");
        esFinal_ = false;
    }

    //PART PRIVADA
    //Atributs privats
    /**
     * Caracter que indica el valor del node
     */
    private char valor_;
    /**
     * Boolea que indica si el node es final o no
     */
    private Boolean esFinal_;
    /**
     * Fill esquerra 
     */
    private Node esquerra_;
    /**
     * Fill central
     */
    private Node central_;
    /**
     * Fill dret
     */
    private Node dret_;
}

