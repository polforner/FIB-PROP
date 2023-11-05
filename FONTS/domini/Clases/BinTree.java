package domini.Clases;
/**
 * Classe que defineix un arbre binari
 * Cada node d'aquest te un valor i pot estar o no negat. 
 * @author pol.forner
 */
public class BinTree {
//PART PUBLICA
    //Funcions publiques
    /**
     * Constructora per defecte
     */
    public BinTree() {
        valor_ = null;
        esquerra_ = null;
        dret_ = null;
        esNegat_ = false;
    }

    /**
     * Constructora amb parametre
     * @param valor String amb el valor del node
     */
    public BinTree(String valor) {
        valor_ = valor;
        esquerra_ = null;
        dret_ = null;
        esNegat_ = false;
    }
    
    /**
     * Retorna si l'arbre es negat
     * @return un boolea que es true sii l'arrel del abre esta negada
     */
    public Boolean esNegat() {
        return esNegat_;
    }

    /**
     * Dona valor al fill esquerra amb el valor del parametre
     * @param esquerra es un BinTree inicialitzat
     */
    public void setEsquerra(BinTree esquerra) {
        esquerra_ = esquerra;
    }

    /**
     * Dona valor al fill dret amb el valor del parametre
     * @param dret es un BinTree inicialitzat
     */
    public void setDret(BinTree dret) {
        dret_ = dret;
    }

    /**
     * Nega el arbre, es a dir, l'arrel del arbre
     */
    public void setNegat() {
        esNegat_ = true;
    }

    /**
     * Retorna el valor de l'arrel del arbre
     * @return un String amb el valor
     */
    public String getValor() {
        return valor_;
    }

    /**
     * Retorna un arbre binari que es el fill esquerra
     * @return BinTree que conte el fill esquerra del arbre
     */
    public BinTree getEsquerra() {
        return esquerra_;
    }

    /**
     * Retorna un arbre binari que es el fill dret
     * @return BinTree que conte el fill esquerra del arbre
     */
    public BinTree getDret() {
        return dret_;
    }
/*
//Operacion per facilitar el debbuging
    public void print() {
        if (esNegat_) System.out.print('!');
        System.out.print('{');
        if (esquerra_ != null) esquerra_.print();
        System.out.print(valor_);
        if (dret_ != null) dret_.print();
        System.out.print('}');
    }
*/
//PART PRIVADA
    //Atributs privats
    /**
     * Es el valor de l'arrel de arbre.
     */
    private String valor_;

    /**
     * Es el fill esquerra del arbre
     */
    private BinTree esquerra_;

    /** 
     * Es el fill dret del arbre
     */
    private BinTree dret_;

    /**
     * Indica si l'arbre esta negat
     */
    private Boolean esNegat_;
}
