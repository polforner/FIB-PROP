package persistencia;

import domini.controladors.CtrlDocument;
import java.io.*;

/**
 * Classe per guardar objectes tipus CtrlDocument a disc
 * @author pol.forner
 */
public class GestorDocuments implements Gestor<CtrlDocument>{
    /**
     * path del archiu que s'utilitza per guardar i carregar
     */
    private static final String path = "./FILES/documents.ser";

    /**
     * Guarda l'objecte al archiu del path
     * @param object a guardar
     */
    public void guardar(CtrlDocument ctrlDocument) throws IOException, ClassNotFoundException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(ctrlDocument);
        oos.close();
    }

    /**
     * Recupera el archiu guardat previament. En cas que no s'hagi guardat res abans, es retorna un objecte nou.
     * @return un objecte de tipus CtrlDocument
     */
    public CtrlDocument carregar() throws IOException, ClassNotFoundException{
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            Object obj = ois.readObject();
            ois.close();
            return (CtrlDocument) obj;
        }catch(EOFException e) {
            return CtrlDocument.getInstance();
        }
    }
}
