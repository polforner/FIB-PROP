package persistencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Aquesta classe conte les funcionalitats per importar i exportar documents en format XML
 * @author bernat.homs
 */
public class ImportarExportarXML implements ImportarExportar {
    /**
     * Aquest metode importa tots els documents que contingui el fitxer que es troba en la ruta path
     * @param path del fitxer on es troben els documents que es volen importar a l'aplicacio
     * @return retorna els camps dels documents importats del fitxer passat per la ruta
     */
    public Map<String, Map<String, List<String>>> importar(String path) throws FileNotFoundException,RuntimeException {
        Map<String, Map<String, List<String>>> documents = new HashMap<>();
        File file = new File(path);
        Scanner lector = new Scanner(file);
        String line=lector.nextLine();

        if (line.contains("<documents>")) {
            while (!line.contains("</documents>") && lector.hasNextLine()) {

                line =lector.nextLine();
                if (line.contains("<document>")) {
                    readDoc(documents,lector,line);
                }
            }
        }
        else if (line.contains("document")) {
            readDoc(documents,lector,line);
        }

        lector.close();
        if (documents.isEmpty()) {
            throw new RuntimeException("xml mal Format");
        }
        return documents;
    }

    /**
     * Per llegir document
     * @param documents mapa per guardar document llegit
     * @param lector lector per llegir document
     * @param line linia actual del lector
     */
    private void readDoc(Map<String,Map<String,List<String>>> documents,Scanner lector,String line) {
        String autor="";
        String titol="";
        List<String> contingut=new ArrayList<>();
        while (!line.contains("</document>") && lector.hasNextLine()) {
            line=lector.nextLine();
            if (line.contains("<autor>")) {
                autor= line.replaceAll("<autor>","").replaceAll("</autor>","");
                autor=autor.strip();
            }
            if (line.contains("<titol>")) {
                titol= line.replaceAll("<titol>","").replaceAll("</titol>","");
                titol=titol.strip();
            }
            if (line.contains("<contingut>") && lector.hasNextLine()) {
                if (line.contains("</contingut>")) {
                    String c=line.replaceAll("<contingut>","").replaceAll("</contingut>","");

                    String[] cs=c.split(" ");
                    for (String s : cs) {
                        s=s.strip();
                        if (!s.isEmpty()) {
                            contingut.add(s);
                        }
                    }
                }
                line=lector.nextLine();
                if (line.contains("</contingut>")) {
                    String c=line.replaceAll("</contingut>","");
                    String[] cs=c.split(" ");
                    for (String s : cs) {
                        s=s.strip();
                        if (!s.isEmpty()) {
                            contingut.add(s);
                        }
                    }
                }
                else if (!line.contains("</document>")) {
                    while (!line.contains("</contingut>") && lector.hasNextLine()) {
                        line = lector.nextLine();
                        if (!line.contains("</contingut>")) {
                            String c = line.strip();
                            contingut.add(c);
                        }
                    }
                }
            }

        }
        if (autor=="" || titol=="" || contingut.isEmpty()) {
            throw new RuntimeException("xml Mal format");
        }
        else {
            Map<String,List<String>> titoldoc=new HashMap<>();
            titoldoc.put(titol,contingut);
            documents.put(autor,titoldoc);

        }
    }

    /**
     * Aquest metode exporta el conjunt de documents que se li passen a un fitxer en format XML
     * @param path ruta del fitxer on es volen exportar els documents
     * @param documents conjunt de documetns a exportar
     * @throws IOException
     */
    public void exportar(String path, Map<String, Map<String, List<String>>> documents) throws IOException {
        File fitxer = new File(path);
        FileWriter escriptor = new FileWriter(fitxer);
        escriptor.write("<documents>\n");
        for (Map.Entry<String, Map<String, List<String>>> documentsAutor : documents.entrySet()) {
            for (Map.Entry<String, List<String>> titolDocument : documentsAutor.getValue().entrySet()) {
                escriptor.write("<document>\n");
                escriptor.write("   <autor>" + documentsAutor.getKey() + "</autor> \n");
                escriptor.write("   <titol>" + titolDocument.getKey() + "</titol> \n");
                escriptor.write("   <contingut>\n");
                for (int i = 0; i < titolDocument.getValue().size(); ++i) {
                    escriptor.write("       " + titolDocument.getValue().get(i) + '\n');
                }
                escriptor.write("   </contingut>\n");
                escriptor.write("</document>\n\n");
            }
        }
        escriptor.write("</documents>\n");
        escriptor.close();
    }

    /**
     * Aquest metode exporta el conjunt de documents en format XML
     * @param path ruta del fitxer on es volen exportar els documents
     * @param documents conjunt de documetns a exportar
     * @throws IOException
     */

    public void exportarSeparat(String path, Map<String, Map<String, List<String>>> documents) throws IOException {

        for (Map.Entry<String, Map<String, List<String>>> documentsAutor : documents.entrySet()) {
            for (Map.Entry<String, List<String>> titolDocument : documentsAutor.getValue().entrySet()) {
                String pathNow= path+"/"+titolDocument.getKey()+".xml";
                File fitxer = new File(pathNow);
                int contador=0;
                while (fitxer.exists()) {
                    pathNow=path+"/"+titolDocument.getKey()+"("+contador+")"+".txt";
                    fitxer=new File(pathNow);
                }
                FileWriter escriptor = new FileWriter(fitxer);
                escriptor.write("<document>\n");
                escriptor.write("   <autor>" + documentsAutor.getKey() + "</autor> \n");
                escriptor.write("   <titol>" + titolDocument.getKey() + "</titol> \n");
                escriptor.write("   <contingut>\n");
                for (int i = 0; i < titolDocument.getValue().size(); ++i) {
                    escriptor.write("       " + titolDocument.getValue().get(i) + '\n');
                }
                escriptor.write("   </contingut>\n");
                escriptor.write("</document>\n\n");
                escriptor.close();
            }
        }

    }
}