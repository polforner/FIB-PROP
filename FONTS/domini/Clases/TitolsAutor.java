package domini.Clases;

import java.io.FileNotFoundException;
import java.util.*;

import domini.controladors.CtrlDocument;

/** Representa una consulta dels titols d'un autor.
 * @author Gerard Garcia Velasco
 * @version 1.0
 */
public class TitolsAutor implements Consulta {
    /**
     * Nom de l'autor de la consulta
     */
    private String autor_;
    /**
     * Instancia del ctrlDocument
     */
    private CtrlDocument ctrlDocument_;

    /**
     * Creacio d'una consulta TitolsAutor amb el nom de l'autor
     * @param autor Nom de l'autor
     */
    public TitolsAutor(String autor) throws FileNotFoundException {
        autor_ = autor;
        ctrlDocument_ = CtrlDocument.getInstance();
    }

    /** s’encarrega de realitzar la consulta, accedint al HashMap TitolsAutor de CtrlDocument per a obtenir la llista de tots els
     * títols de l’autor que s’ha passat a la creadora, i amb aquests crea una instancia de ResultatConsulta que retorna al final de la funció.
     * @return retorna la instancia de ResultatConsulta creada en executar-se la consulta
     */
    public ResultatConsulta executar() {
        Map<String,Map<String,Integer>> titolsAutor = ctrlDocument_.getTitolsAutor();
        if (titolsAutor.get(autor_) == null) throw new RuntimeException("No existeix l'autor introduit");
        else {
            Set<String> resultat = titolsAutor.get(autor_).keySet();
            ResultatConsulta rc = new ResultatConsultaNoms(resultat, "TitolsAutor", Arrays.asList(autor_));
            return rc;
        }
    }

    /**
     * getter de l'autor de la consulta
     * @return
     */
    public String getAutor() {
        return autor_;
    }
}



