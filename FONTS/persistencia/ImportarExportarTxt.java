package persistencia;


import java.io.*;
import java.util.*;

/**
 * Aquesta classe conte les funcionalitats per importar i exportar documents en format txt
 * @author pol.forner
 */
public class ImportarExportarTxt implements ImportarExportar{

    /**
     * Aquest metode exporta el conjunt de documents en format txt
     * @param path ruta del fitxer on es volen exportar els documents
     * @param documents conjunt de documetns a exportar
     * @throws IOException
     */
    public void exportar(String path, Map<String,Map<String,List<String>>> documents) throws IOException{

        for (Map.Entry<String,Map<String,List<String>>> entry : documents.entrySet()) {
            Map<String,List<String>> aux= entry.getValue();
            for (Map.Entry<String,List<String>> entry2 : aux.entrySet()) {
                String pathNow=path+"/"+entry2.getKey()+".txt";

                File file = new File(pathNow);
                int contador=0;
                while (file.exists()) {
                    pathNow=path+"/"+entry2.getKey()+"("+contador+")"+".txt";
                    file=new File(pathNow);
                }
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.println(entry.getKey());
                writer.println(entry2.getKey());
                for (String line : entry2.getValue()) {
                    writer.println(line);
                }
                writer.close();
            }
        }
    }

    /**
     * Aquest metode importa el document que es troba en la ruta path
     * @param path la ruta del document que es volen importar a l'aplicacio
     * @return retorna els camps del document importat del document passat per la ruta
     */
    public Map<String,Map<String, List<String>>> importar(String path) throws FileNotFoundException, Exception{
        File file = new File(path);
        Scanner reader = new Scanner(file);
        Map<String,Map<String,List<String>>> documents = new HashMap<>();
        while(reader.hasNextLine()) {
            String autor = reader.nextLine();
            if (reader.hasNextLine() && autor != "@@") {
                String titol = reader.nextLine();
                if (reader.hasNextLine() && titol != "@@") {
                    List<String> contingut = new ArrayList<>();
                    do {
                        String line = reader.nextLine();
                        if (line.equals("@@")) break;
                        else {
                            contingut.add(line);
                        }    
                    }while(reader.hasNextLine());
                    if (documents.containsKey(autor) && documents.get(autor).containsKey(titol)) throw new Exception("Document txt amb documents amb mateix titol i autor");
                    else {
                        if (documents.containsKey(autor)) {
                            documents.get(autor).put(titol,contingut);
                        }
                        else {
                            Map<String,List<String>> aux = new HashMap<>();
                            aux.put(titol, contingut);
                            documents.put(autor,aux);
                        }
                    }
                } else throw new Exception("Document txt amb mal format");
            } else throw new Exception("Document txt amb mal format");
        }
        reader.close();
        return documents;
    }
}
