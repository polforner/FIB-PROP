package persistencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * Aquesta classe conte les funcionalitats per importar i exportar documents en format JSON
 * @author bernat.homs
 */
public class ImportarExportarJSON implements ImportarExportar {
    /**
     * Aquest metode importa tots els documents que contingui el fitxer que es troba en la ruta path
     * @param path del fitxer on es troben els documents que es volen importar a l'aplicacio
     * @return retorna els camps dels documents importats del fitxer passat per la ruta
     */
    public Map<String, Map<String, List<String>>> importar(String path) throws FileNotFoundException {
        Map<String, Map<String, List<String>>> documents = new HashMap<>();
        File file = new File(path);
        Scanner lector = new Scanner(file);

        while (lector.hasNextLine()) {
            String line=lector.nextLine();
            if (line.contains("   \"document\": {"))  {
                String autor="";
                String titol="";
                List<String> contingut=new ArrayList<>();
                while (lector.hasNextLine() && !line.contains("   }")) {
                    line=lector.nextLine();
                    if (line.contains("\"autor\": ")) {
                        autor=line.replaceAll("\"autor\": ","").replaceAll(",","");
                        autor=autor.strip();
                    }
                    if (line.contains("\"titol\": ")) {
                        titol=line.replaceAll("\"titol\": ","").replaceAll(",","");
                        titol=titol.strip();
                    }

                    if (line.contains("\"contingut\": [") && lector.hasNextLine()) {
                        line=lector.nextLine();
                        while (lector.hasNextLine() && !line.contains("     ]")) {
                            String c=line.replaceAll("\"","");
                            c=c.strip();
                            contingut.add(c);
                            line=lector.nextLine();
                        }
                    }
                }
                if (autor=="" || titol=="" || contingut.isEmpty()) {
                    throw new RuntimeException("json mal format");
                }

                else {
                    Map<String,List<String>> titolContingut=new HashMap<>();
                    titolContingut.put(titol,contingut);
                    documents.put(autor,titolContingut);
                }
            }
        }


        lector.close();
        return documents;
    }

  /*  /**
     * Aquest metode exporta el conjunt de documents que se li passen a un fitxer en format JSON
     * @param path ruta del fitxer on es volen exportar els documents
     * @param documents conjunt de documetns a exportar
     * @throws IOException
     */
    /*
    public void exportar(String path, Map<String, Map<String, List<String>>> documents) throws IOException {
        File fitxer = new File(path);
        FileWriter escriptor = new FileWriter(fitxer);
        escriptor.write("{\n");
        escriptor.write("   \"documents\": [\n");
        int counter = 0;
        int counter2 = documents.size();
        for (Map.Entry<String, Map<String, List<String>>> documentsAutor : documents.entrySet()) {
            --counter2;
            counter += documentsAutor.getValue().size();
            for (Map.Entry<String, List<String>> titolDocument : documentsAutor.getValue().entrySet()) {
                escriptor.write("       {\n");
                escriptor.write("           \"autor\": " + documentsAutor.getKey()+"," + '\n');
                escriptor.write("           \"titol\": " + titolDocument.getKey() +"," + '\n');
                escriptor.write("           \"contingut\": [\n");
                for (int i = 0; i < titolDocument.getValue().size(); ++i) {
                    escriptor.write("               " + '\"' + titolDocument.getValue().get(i) + '\"');
                    if (i < titolDocument.getValue().size() - 1) escriptor.append(",\n");
                }
                escriptor.write("\n           ]\n");
                escriptor.write("       }");
                System.out.println(counter);
                --counter;
                if ((counter >0) ||(counter == 0 && counter2>0))
                    escriptor.append(",\n\n");

            }

        }
        escriptor.write("\n ]\n");
        escriptor.write("}\n");
        escriptor.close();
    }
*/
    public void exportar(String path, Map<String, Map<String, List<String>>> documents) throws IOException {

        for (Map.Entry<String, Map<String, List<String>>> documentsAutor : documents.entrySet()) {
            for (Map.Entry<String, List<String>> titolDocument : documentsAutor.getValue().entrySet()) {
                String pathNow= path+"/"+titolDocument.getKey()+".json";
                File fitxer = new File(pathNow);
                int contador=0;
                while (fitxer.exists()) {
                    pathNow=path+"/"+titolDocument.getKey()+"("+contador+")"+".txt";
                    fitxer=new File(pathNow);
                }
                FileWriter escriptor = new FileWriter(fitxer);
                escriptor.write("{\n");
                escriptor.write("   \"document\": ");
                escriptor.write("{\n");
                escriptor.write("       \"autor\": " + documentsAutor.getKey()+"," + '\n');
                escriptor.write("       \"titol\": " + titolDocument.getKey() +"," + '\n');
                escriptor.write("       \"contingut\": [\n");
                for (int i = 0; i < titolDocument.getValue().size(); ++i) {
                    escriptor.write("           " + '\"' + titolDocument.getValue().get(i) + '\"');
                    if (i < titolDocument.getValue().size() - 1) escriptor.append(",\n");
                }
                escriptor.write("\n     ]\n");
                escriptor.write("   }\n");
                escriptor.write("}\n");
                escriptor.close();
            }

        }

    }
}
