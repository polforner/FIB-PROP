package persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interficie que representa un gestor per importar i exportar archius entre el dispositiu i el programa
 */
public interface ImportarExportar {
    /**
     * Aquest metode exporta els documents a la ruta del path
     * @param path el fitxer on s'exportaran els documents
     * @param documents a exportar
     */
    public void exportar(String path, Map<String,Map<String,List<String>>> documents) throws Exception;

    /**
     * Aquest metode importa tots els documents que contingui el fitxer que es troba en la ruta path
     * @param path del fitxer on es troben els documents que es volen importar a l'aplicacio
     * @return retorna els docuemnts en format <autor,titol,contingut>
     */
    public Map<String,Map<String, List<String>>> importar(String path) throws Exception;
}
