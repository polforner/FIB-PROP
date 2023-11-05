package persistencia;

import domini.controladors.ControladorConsulta;
import java.io.*;

/**
 * Classe per guardar objectes tipus ControladorConsulta a disc
 * @author pol.forner
 */
public class GestorConsultes implements Gestor<ControladorConsulta>{
    /**
     * path del archiu que s'utilitza per guardar i carregar
     */
    private static final String path = "./FILES/consultes.ser";

    /**
     * Guarda l'objecte al archiu del path
     * @param object a guardar
     */
    public void guardar(ControladorConsulta ctrlConsulta) throws IOException, ClassNotFoundException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(ctrlConsulta);
        oos.close();
    }

    /**
     * Recupera el archiu guardat previament. En cas que no s'hagi guardat res abans, es retorna un objecte nou.
     * @return un objecte de tipus GestorConsulta
     */
    public ControladorConsulta carregar() throws IOException, ClassNotFoundException{
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            Object obj = ois.readObject();
            ois.close();
            return (ControladorConsulta) obj;
        }catch(EOFException e) {
            return new ControladorConsulta();
        }
    }

}
