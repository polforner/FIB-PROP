package persistencia;
import java.io.*;

/**
 * Interficie que representa un gestor de disc per guardar i carregar el estat d'un objecte generic
 * @author pol.forner
 */
public interface Gestor<T>{
    
    /**
     * Guarda l'objecte tipus T a un archiu
     * @param object a guardar
     */
    public void guardar(T object) throws IOException, ClassNotFoundException;

    /**
     * Recupera el archiu guardat previament. En cas que no s'hagi guardat res abans, es retorna un objecte nou.
     * @return un objecte de tipus T
     */
    public T carregar() throws IOException, ClassNotFoundException;
}
