package persistencia;

import domini.controladors.CtrlExprBooleanes;
import domini.exceptions.ExprMalFormadaException;

import java.io.*;

/**
 * Classe per guardar objectes tipus CtrlExprBooleanes a disc
 * @author pol.forner
 */
public class GestorExprBooleanes implements Gestor<CtrlExprBooleanes>{
    /**
     * path del archiu que s'utilitza per guardar i carregar
     */
    private static final String path = "./FILES/exprbooleanes.ser";

    /**
     * Guarda l'objecte al archiu del path
     * @param object a guardar
     */
    public void guardar(CtrlExprBooleanes ctrlExprBooleanes) throws IOException, ClassNotFoundException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(ctrlExprBooleanes);
        oos.close();
    }

    /**
     * Recupera el archiu guardat previament. En cas que no s'hagi guardat res abans, es retorna un objecte nou.
     * @return un objecte de tipus CtrlExprBooleanes
     */
    public CtrlExprBooleanes carregar() throws IOException, ClassNotFoundException{
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            Object obj = ois.readObject();
            ois.close();
            return (CtrlExprBooleanes) obj;
        }catch(EOFException e) {
            return CtrlExprBooleanes.getInstance();
        }
    }

}