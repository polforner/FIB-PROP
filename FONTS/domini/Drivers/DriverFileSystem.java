package domini.Drivers;

import domini.Clases.ExprBooleana;
import domini.Clases.ResultatConsultaNoms;
import domini.Clases.ResultatConsultaPairs;
import domini.Clases.ResultatContingut;
import domini.controladors.CtrlDomini;
import domini.exceptions.ExprMalFormadaException;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.*;

public class DriverFileSystem {

    private static CtrlDomini cd_;

    static void estatinicial() throws IOException, ClassNotFoundException {
        cd_ = new CtrlDomini();
        System.out.println("Benvigut a l'entorn de manipulacio de documents del Grup 22-subgrup 6.3. \nPer coneixer" +
                " les funcionalitats del sistema, escrigui --func");
    }


    static void mostraOpcions() {
        System.out.println("Funcionalitats del sistema: \n1: Crear Document\n2: Modificar document\n" +
                "3: Esborrar document\n4: Realitzar una consulta dels documents\n5: Ordenar" +
                " les consultes realitzades\n6: Operar amb les consultes realitzades\n7: Gestionar Expressions booleanes per a consultes");
    }

    static void OrdenarConsultes() {
        Map<Integer, ResultatConsultaNoms> rcn = cd_.getResultatConsultaNoms();
        Map<Integer, ResultatConsultaPairs> rcp = cd_.getResultatConsultaPairs();
        Map<Integer, ResultatContingut> rcc = cd_.getResultatContingut();
        if (rcn.isEmpty() && rcp.isEmpty() && rcc.isEmpty()) {
            System.out.println("No s'ha realitzat cap consulta. Primer, crei una consulta per a poder ordenarla");
            return;
        }
        System.out.println("Consultes realitzades:");
        for (Map.Entry<Integer, ResultatConsultaNoms> m : rcn.entrySet()) {
            System.out.println("-Id:" + m.getKey() + "-> Tipus: " + m.getValue().getTipusConsulta() +
                    "-> Parametres: " + m.getValue().getParametres());
        }
        for (Map.Entry<Integer, ResultatConsultaPairs> n : rcp.entrySet()) {
            System.out.println("-Id:" + n.getKey() + "-> Tipus: " + n.getValue().getTipusConsulta() +
                    "-> Parametres: " + n.getValue().getParametres());
        }
        for (Map.Entry<Integer, ResultatContingut> p : rcc.entrySet()) {
            System.out.println("-Id:" + p.getKey() + "-> Tipus: " + p.getValue().getTipusConsulta() +
                    "-> Parametres: " + p.getValue().getParametres());
        }
        Scanner S = new Scanner(System.in);
        while (true) {
            String next = S.nextLine();
            while (true) {
                try {
                    int k = Integer.parseInt(next);
                } catch (NumberFormatException e) {
                    System.out.println("Entrada incorrecte. Ha d'introduir un numero valid");
                    break;
                }
                break;
            }
            if("--cancel".equalsIgnoreCase(next)) {
                System.out.println("Operacio Cancelada");
                return;
            }
            int k = Integer.parseInt(next);
            if(!rcn.containsKey(k) && !rcp.containsKey(k) && !rcc.containsKey(k)) {
                System.out.println("No existeix cap consulta amb id = " + k + "Torni a inserir un id valid o escrigui --cancel per abortar.");
            }
            else if (rcn.containsKey(k)) {
                System.out.println("Ha seleccionat la consulta -> " + "Tipus: " + rcn.get(k).getTipusConsulta() +
                        "-> Parametres: " + rcn.get(k).getParametres() +  "\n" +
                        "Si desitja ordenar la consulta alfabeticament (a-z), escrigui \"a\".\n" +
                        "Si desitja ordenarla inversament (z-a), escrigui \"i\"");
                while(true) {
                    String ordre = S.nextLine();
                    if("--cancel".equalsIgnoreCase(ordre)) {
                        System.out.println("Operacio cancelada");
                        return;
                    }
                    else if("a".equalsIgnoreCase(ordre)) {
                        System.out.println("Consulta ordenada alfabeticament! Resultat:");
                        cd_.ordenarConsulta(k, "Alfabeticament");
                        for(String st: rcn.get(k).getResultat()) {
                            System.out.println("-" + st);
                        }
                        return;
                    }
                    else if("i".equalsIgnoreCase(ordre)) {
                        System.out.println("Consulta ordenada inversament! Resultat:");
                        cd_.ordenarConsulta(k, "Inversament");
                        for(String st: rcn.get(k).getResultat()) {
                            System.out.println("-" + st);
                        }
                        return;
                    }
                    else {
                        System.out.println("Comanda incorrecte. Escrigui \"a\" o \"i\" per a ordenar alfabeticament o inversament." +
                                " Si desitja cancelar la operacio, escrigui --cancel");
                    }
                }
            }
            else if (rcp.containsKey(k)) {
                System.out.println("Ha seleccionat la consulta -> " + "Tipus: " + rcp.get(k).getTipusConsulta() +
                        "-> Parametres: " + rcp.get(k).getParametres() +  "\n" +
                        "Si desitja ordenar la consulta alfabeticament (a-z), escrigui \"a\".\n" +
                        "Si desitja ordenarla inversament (z-a), escrigui \"i\"");
                while(true) {
                    String ordre = S.nextLine();
                    if("a".equalsIgnoreCase(ordre)) {
                        System.out.println("Consulta ordenada alfabeticament! Resultat:");
                        cd_.ordenarConsulta(k, "Alfabeticament");
                        for(String st: rcp.get(k).getResultat().keySet()) {
                            for (String titol: rcp.get(k).getResultat().get(st)) {
                                System.out.println("- Autor:"+ st +", Titol:"+titol);
                            }
                        }
                        return;
                    }
                    else if("i".equalsIgnoreCase(ordre)) {
                        System.out.println("Consulta ordenada inversament! Resultat:");
                        cd_.ordenarConsulta(k, "Inversament");
                        for(String st: rcp.get(k).getResultat().keySet()) {
                            for (String titol: rcp.get(k).getResultat().get(st)) {
                                System.out.println("- Autor:"+ st +", Titol:"+titol);
                            }
                        }
                        return;
                    }

                }
            }
            else  {
                System.out.println("El resultat de la consulta contingut de document no pot ordenar");
            }

        }
    }
    static void NovaConsulta() throws FileNotFoundException, ExprMalFormadaException {
        Map<String, Map<String, Integer>> titolsAutor = cd_.getTitolsAutor();
        if (titolsAutor.size() == 0)  {
            System.out.println("No hi han Documents al sistema. Si us plau, crei un document abans de continuar");
            return;
        }
        Scanner S = new Scanner(System.in);
        System.out.println("Ha triat fer una nova consulta. Si us plau. Indiqui el numero de la consulta a realitzar:\n" +
                "1. Titols Autor: Retorna els titols del autor indicat.\n" +
                "2. Autors per Prefix: Retorna tots els autors que contenen un prefix indicat.\n" +
                "3. Contingut Document: Donats un titol i autor d'un document, mostra els seus continguts.\n" +
                "4. K Mes Semblants: donat un titol i autor d'un document, i un natural K, mostra els k documents mes semblants al introduit.\n" +
                "5. Expressio Booleana: donada una expressio Booleana, mostra tots els documents que la cumpleixen.\n" +
                "6. K mes Rellevants: donada una sequencia de paraules i un enter k, mostra els documents k mes rellevants en quant a contingut per aquesta sequencia.");
        while(true) {
            String line = S.nextLine();
            if ("1".equalsIgnoreCase(line)) {
                System.out.println("Autors: ");
                for (Map.Entry<String, Map<String, Integer>> m : titolsAutor.entrySet()) {
                    System.out.println("- " + m.getKey());
                }
                System.out.println("Indiqui el nom del autor: ");
                while (true) {
                    String autorCheck = S.nextLine();
                    if ("--cancel".equalsIgnoreCase(autorCheck)) {
                        System.out.println("Comanda Cancelada. Indiqui el nombre de consulta a realitzar o escrigui --cancel per a abortar");
                        break;
                    } else if (!titolsAutor.containsKey(autorCheck)) {
                        System.out.println("L'autor no existeix. Si us plau, indiqui un autor existent, o aborti la consulta escrivint --cancel");
                    } else {
                        Set<String> resultat = cd_.consultaTitolsAutor(autorCheck);
                        System.out.println("Titols de l'autor " + autorCheck + ":");
                        for (String st : resultat) {
                            System.out.println("- " + st);
                        }
                        return;
                    }
                }

            } else if ("2".equalsIgnoreCase(line)) {
                System.out.println("Ha escollit la consulta Autors per Prefix");
                System.out.println("Introdueixi un prefix");
                String prefix = S.nextLine();
                Set<String> resultat = cd_.consultaPrefixAutor(prefix);
                if (resultat.size() == 0) {
                    System.out.println("No existeix cap Autor amb el prefix " + prefix + ".");
                    return;
                } else {
                    System.out.println("Autors amb el prefix " + prefix + ":");
                    for (String st : resultat) {
                        System.out.println("- " + st);
                    }
                    return;
                }

            } else if ("3".equalsIgnoreCase(line)) {
                System.out.println("Ha escollit la consulta Contingut Document");
                for (Map.Entry<String, Map<String, Integer>> m : titolsAutor.entrySet()) {
                    for (String s : m.getValue().keySet()) {
                        System.out.println("- " + m.getKey() + ": " + s);
                    }
                }
                System.out.println("Introdueixi un titol i autor per a obtenir el contingut");
                while (true) {
                    String titol = S.nextLine();
                    if ("--cancel".equalsIgnoreCase(titol)) {
                        System.out.println("Consulta cancelada");
                        return;
                    }
                    String autor = S.nextLine();
                    if (!titolsAutor.containsKey(autor) || !titolsAutor.get(autor).containsKey(titol)) {
                        System.out.println("No existeix cap document amb el titol " + titol + " i autor " + autor + ". Si desitja abortar, escrigui --cancel");
                    } else {
                        List<String> resultat = cd_.consultaContingutDocument(titol, autor);
                        for (String st : resultat) {
                            System.out.println(st);
                        }
                        return;
                    }
                }
            } else if ("4".equalsIgnoreCase(line)) {
                System.out.println("Ha escollit la consulta K mes semblants");
                for (Map.Entry<String, Map<String, Integer>> m : titolsAutor.entrySet()) {
                    for (String s : m.getValue().keySet()) {
                        System.out.println("- " + m.getKey() + ": " + s);
                    }
                }
                System.out.println("Introdueixi un titol i autor i nombre en linees separades per a obtenir els documents mes semblants");
                while (true) {
                    String titol = S.nextLine();
                    if ("--cancel".equalsIgnoreCase(titol)) {
                        System.out.println("Consulta cancelada");
                        return;
                    }
                    String autor = S.nextLine();
                    if (!titolsAutor.containsKey(autor) || !titolsAutor.get(autor).containsKey(titol)) {
                        System.out.println("No existeix cap document amb el titol " + titol + " i autor " + autor + ". Si desitja abortar, escrigui --cancel");
                    }
                    String next = S.nextLine();
                    try {
                        int k = Integer.parseInt(next);
                    } catch (NumberFormatException e) {
                        System.out.println("No ha introduit un nombre valid. Torni a introduir la query i un numero valid");
                        break;
                    }
                    int k = Integer.parseInt(next);
                    if (k < 0 || k > cd_.getCtrlDoc().getIdentificadors().size()-1) {
                        System.out.println("Nombre incorrecte. El nombre ha de ser natural i mes petit que el nombre de documents total - 1. Torni a " +
                                "introduir un titol i autor i nombre valids en linees separades, o escrigui --cancelar per abortar");
                    } else {
                        Map<String, Set<String>> resultat = cd_.consultaKDocumentsSemblants(titol, autor, k);
                        if (resultat.size() == 0) {
                            System.out.println("No existeix cap document Semblant");
                            return;
                        } else {
                            System.out.println("Documents Semblants:");
                            int i = 0;
                            for (Map.Entry<String, Set<String>> m : resultat.entrySet()) {
                                for (String st : m.getValue()) {
                                    System.out.println(i + ": Autor: " + m.getKey() + ", Titol:" + st);
                                    i++;
                                }
                            }
                            return;
                        }
                    }
                }
            } else if ("5".equalsIgnoreCase(line)) {
                System.out.println("Ha escollit la consulta Booleana");
                System.out.println("Donades una expressió booleana formada pels operadors & | i ! (amb les normes de\n" +
                        "precedència habituals) i " +
                        "conjunts de paraules (delimitats per {}), sequencies de paraules (delimitades per \"\"),\n" +
                        "o paraules soltes com a operands, s'obtenen tots els documents que contenen una frase\n" +
                        "que satisfà aquesta expressió. Un exemple d’expressió seria:\n" +
                        "{p1 p2 p3} & (\"hola adéu\" | pep) & !joan\n\nInsereixi a continuacio l'expresio booleana amb els criteris indicats.");
                while (true) {
                    String consulta = S.nextLine();
                    if ("--cancel".equalsIgnoreCase(consulta)) {
                        System.out.println("Consulta Cancelada");
                        return;
                    } else {
                        while (true) {
                            try {
                                Map<String, Set<String>> resultat = cd_.consultaBooleana(consulta);
                            } catch (ExprMalFormadaException e) {
                                System.out.println(e.getMessage());
                                System.out.println("Torni a entrar una expressio valida, o escrigui --cancel per cancelar l'operacio.");
                                break;
                            }
                            Map<String, Set<String>> resultat = cd_.consultaBooleana(consulta);
                            if (resultat.isEmpty()) {
                                System.out.println("No hi ha cap document que cumpleixi amb la consulta indicada");
                                System.out.println("Vol guardar aquesta expressio ?\n Entra Si/si si vol guarda");
                                String resposta=S.nextLine();
                                if ("Si".equalsIgnoreCase(resposta) || "si".equalsIgnoreCase(resposta)) {
                                    cd_.afegirExpressioBooleana(consulta);
                                }
                                return;
                            } else {
                                System.out.println("Documents que cumpleixen l'expressio " + consulta + ":");
                                int i = 0;
                                for (Map.Entry<String, Set<String>> m : resultat.entrySet()) {
                                    for (String st : m.getValue()) {
                                        System.out.println(i + ": Autor: " + m.getKey() + ", Titol:" + st);
                                        i++;
                                    }
                                }
                                System.out.println("Vol guardar aquesta expressio ?\n Entra Si/si si vol guarda");
                                String resposta=S.nextLine();
                                if ("Si".equalsIgnoreCase(resposta) || "si".equalsIgnoreCase(resposta)) {
                                    cd_.afegirExpressioBooleana(consulta);
                                }
                                return;
                            }
                        }
                    }
                }
            }
            else if ("6".equalsIgnoreCase(line)) {
                System.out.println("Ha escollit la consulta K mes Rellevants");
                System.out.println("donades p paraules " +
                        "(denotades col·lectivament com a query), i un \nenter k, obtenir els k documents més " +
                        "rellevants (en quant a contingut) per aquesta query.\n");
                System.out.println("Ingresi les paraules seguides, separades per comes i sense signes de puntuacio, i en la seguent linea ingresi k");
                while(true) {
                    List<String> query = new ArrayList<>();
                    String paraules = S.nextLine();
                    if("--cancel".equalsIgnoreCase(paraules)) {
                        System.out.println("Operacio cancelada");
                        return;
                    }
                    else {
                        String s = "";
                        for (char c : paraules.toCharArray()) {
                            if (c == '.' || c == ':' || c == ';' || c == '-' || c == '?' || c == '/' || c == '!' || c == ' ') {
                                System.out.println("Query incorrecte. Torni a ingresar les paraules seguides, separades per comes i sense signes de puntuacio ni espais, i a la seguent " +
                                        "linea ingresi k. Per cancelar, escrigui --cancel");
                                break;
                            } else if (c == ',') {
                                if (!s.equals("")) query.add(s);
                                s = "";
                            } else {
                                s += c;
                            }
                        }
                        String next = S.nextLine();
                        try {
                            int k = Integer.parseInt(next);
                        } catch (NumberFormatException e) {
                            System.out.println("No ha introduit un nombre valid. Torni a introduir la query i un numero valid");
                            break;
                        }
                        int k = Integer.parseInt(next);
                        Map<String,Set<String>> res = cd_.consultaKDocumentsRellevants(query,k);
                        System.out.println("Documents que cumpleixen la query " + query + ":");
                        int i = 0;
                        for (Map.Entry<String, Set<String>> m : res.entrySet()) {
                            for (String st : m.getValue()) {
                                System.out.println(i + ": Autor: " + m.getKey() + ", Titol:" + st);
                                i++;
                            }
                        }
                        return;
                    }
                }
            }
            else if ("--cancel".equalsIgnoreCase(line)){
                System.out.println("Comanda cancelada");
                return;
            }
            else {
                System.out.println("nombre de consulta incorrecte. Torni a ingresar el nombre de la consulta, o escrigui " +
                        "--cancel per a cancelar la comanda");
            }
        }
    }
    static void EsborraDocument() throws Exception {
        Scanner S = new Scanner(System.in);
        System.out.println("Documents:");
        Map<String, Map<String, Integer>> titolsAutor = cd_.getTitolsAutor();
        if (titolsAutor.size() == 0)  {
            System.out.println("No hi han Documents al sistema. Si us plau, crei un document abans de continuar");
            return;
        }
        for (Map.Entry<String, Map<String, Integer>> m : titolsAutor.entrySet()) {
            for (String s : m.getValue().keySet()) {
                System.out.println("- " + m.getKey() + ": " + s);
            }
        }
        System.out.println("Indiqui titol i autor del document a eliminar, amb el format:\n\"Titol\"\n\"Autor\"");
        while (true) {
            String titol = S.nextLine();
            if("--cancel".equalsIgnoreCase(titol)) {
                System.out.println("Operacio cancelada");
                return;
            }
            String autor = S.nextLine();
            if (!titolsAutor.containsKey(autor) || !titolsAutor.get(autor).containsKey(titol)) {
                System.out.println("No existeix el document amb titol " + titol + " de l'autor " + autor + ".Torni a indicar titol i autor " +
                        "del document a eliminar, amb el format:\n\"Titol\"\n\"Autor\"\nSi desitja cancelar la " +
                        "eliminacio, escrigui --cancel");
            }
            else {
                cd_.eliminarDocument(titol,autor);
                System.out.println("Document de l'autor " + autor + " amb titol " + titol + " esborrat!");
                return;
            }
        }
    }
    static void ModificaDocument() throws Exception {
        Scanner S = new Scanner(System.in);
        System.out.println("Documents:");
        Map<String, Map<String, Integer>> titolsAutor = cd_.getTitolsAutor();
        if (titolsAutor.size() == 0)  {
            System.out.println("No hi han Documents al sistema. Si us plau, crei un document abans de continuar");
            return;
        }
        for (Map.Entry<String, Map<String, Integer>> m : titolsAutor.entrySet()) {
            for (String s : m.getValue().keySet()) {
                System.out.println("- " + m.getKey() + ": " + s);
            }
        }
        System.out.println("Indiqui titol i autor del document a modificar, amb el format:\n\"Titol\"\n\"Autor\"");
        while (true) {
            String titol = S.nextLine();
            if("--cancel".equalsIgnoreCase(titol)) {
                System.out.println("Modificacio cancelada");
                return;
            }
            String autor = S.nextLine();
            if (!titolsAutor.containsKey(autor) || !titolsAutor.get(autor).containsKey(titol)) {
                System.out.println("No existeix el document amb titol " + titol + " de l'autor " + autor + ".Torni a indicar titol i autor " +
                        "del document a modificar, amb el format:\n\"Titol\"\n\"Autor\"\nSi desitja cancelar la " +
                        "modificacio, escrigui --cancel");
            }
            else {
                System.out.println("Indiqui que desitja modificar: Titol(--t), Autor(--a) o Contingut(--c)");
                while(true) {
                    String line = S.nextLine();
                    if("--t".equalsIgnoreCase(line)) {
                        System.out.println("Ingresi el nou titol del Document");
                        String nouTitol = S.nextLine();
                        System.out.println("Desitja desar els canvis? Y/N");
                        while(true) {
                            String decisio = S.nextLine();
                            if("Y".equalsIgnoreCase(decisio) || "y".equalsIgnoreCase(decisio)) {
                                cd_.modificarTitol(nouTitol,titol,autor);
                                System.out.println("S'han desat els canvis! El titol pasa de " + titol + "->" + nouTitol);
                                return;
                            }
                            else if("N".equalsIgnoreCase(decisio) || "n".equalsIgnoreCase(decisio)) {
                                System.out.println("Modificacio cancelada. Torni a indicar que desitja modificar, o escrigui --cancel" +
                                        " per a cancelar la modificacio del document.");
                                break;
                            }
                            else {
                                System.out.println("Comanda incorrecte. Ingresi \"Y\"o \"N\".");
                            }
                        }
                    }
                    else if ("--a".equalsIgnoreCase(line)) {
                        System.out.println("Ingresi el nou autor del Document");
                        String nouAutor = S.nextLine();
                        System.out.println("Desitja desar els canvis? Y/N");
                        while(true) {
                            String decisio = S.nextLine();
                            if ("Y".equalsIgnoreCase(decisio) || "y".equalsIgnoreCase(decisio)) {
                                cd_.modificarAutor(titol, nouAutor, autor);
                                System.out.println("S'han desat els canvis! L'autor pasa de " + autor + "->" + nouAutor);
                                return;
                            } else if ("N".equalsIgnoreCase(decisio) || "n".equalsIgnoreCase(decisio)) {
                                System.out.println("Modificacio cancelada. Torni a indicar que desitja modificar, o escrigui --cancel" +
                                        " per a cancelar la modificacio del document.");
                                break;
                            } else {
                                System.out.println("Comanda incorrecte. Ingresi \"Y\"o \"N\".");
                            }
                        }
                    }
                    else if ("--c".equalsIgnoreCase(line)) {
                        System.out.println("Contingut: Seleccioni una frase a modificar, e inserti el nou " +
                                "contingut. Si desitja" +
                                "cancelar la modificacio, escrigui --cancel");
                        List<String> contingutDoc = cd_.getidsDocuments().get(titolsAutor.get(autor).get(titol)).getContingut();
                        int i = 0;
                        for (String s : contingutDoc) {
                            System.out.println("-" + i + ":" + s);
                            ++i;
                        }
                        while (true) {
                            String index = S.nextLine();
                            if ("--cancel".equalsIgnoreCase(index)) {
                                System.out.println("Modificacio cancelada");
                                return;
                            }
                            try {
                                int k = Integer.parseInt(index);
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada incorrecte. Ha d'introduir un index numeric. Torni a indicar el tipus de modificacio a fer (--t, --a o --c)");
                                break;
                            }
                            int k = Integer.parseInt(index);
                            try {
                                contingutDoc.get(k);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Entrada incorrecte. Ha d'introduir un index valid");
                                break;
                            }
                            System.out.println("Ha indicat la modificacio de la frase " + k + ":" +
                                    contingutDoc.get(k) + "\nIndiqui el nou contingut en una sola linea acabada en un punt, " +
                                    "finalitzant aquest amb un Enter i escribint \"@32@\".\n" +
                                    "Si desitja eliminar la frase, simplement entri una frase buida abans d'escriure @32@.\n" +
                                    "Per abortar la modificacio, escrigui --cancel");
                            while (true) {
                                String nextl = S.nextLine();
                                if ("--cancel".equalsIgnoreCase(nextl)) {
                                    System.out.println("Modificacio de document cancelada.");
                                    return;
                                }
                                if ("@32@".equalsIgnoreCase(nextl)) {
                                    break;
                                } else if (nextl.lastIndexOf('.') != nextl.length() - 1 && !nextl.isEmpty()) {
                                    System.out.println("Frase introduida incorrectament. Insereixi una frase correcta. Per finalitzar la modificacio" +
                                            " escrigui \"@32@\". Per cancelar la modificacio, escrigui --cancel");
                                } else {
                                    contingutDoc.set(k, nextl);
                                }
                            }
                            if (contingutDoc.get(k).isEmpty()) contingutDoc.remove(k);
                            else if (contingutDoc.get(k).indexOf('.') == -1) {
                                String afegirpunt = contingutDoc.get(k);
                                afegirpunt += '.';
                            }
                            System.out.println("Desitja desar els canvis? Y/N");
                            while (true) {
                                String decisio = S.nextLine();
                                if ("Y".equalsIgnoreCase(decisio) || "y".equalsIgnoreCase(decisio)) {
                                    cd_.modificarContingut(titol, autor, contingutDoc);
                                    System.out.println("S'han desat els canvis!");
                                    return;
                                } else if ("N".equalsIgnoreCase(decisio) || "n".equalsIgnoreCase(decisio)) {
                                    System.out.println("Modificacio cancelada.");
                                    return;
                                } else {
                                    System.out.println("Comanda incorrecte. Ingresi \"Y\"o \"N\".");
                                }
                            }
                        }
                    }
                    else if("--cancel".equalsIgnoreCase(line)) {
                        System.out.println("Modificacio cancelada");
                        return;
                    }
                    else {
                        System.out.println("Comanda incorrecte. Indiqui que desitja modificar: Titol(--t), Autor(--a) o Contingut(--c).\n" +
                                "Si desitja cancelar la modificacio, escrigui --cancel");
                    }
                }
            }
        }
    }
    static void CreacioDocument() throws Exception {
        Scanner S = new Scanner(System.in);
        System.out.println("Primer seleccioni el format del document:\nFitxer de text -> Escrigui (.txt)\nFitxer XML -> Escrigui (.xml)");
        while(true) {
            String line = S.nextLine();
            if(".txt".equalsIgnoreCase(line)) {
                System.out.println("Ha seleccionat un fitxer .txt. Introdueixi en linees consecutives el Titol, Autor, i Contingut del Document.\n" +
                        "El contingut del document ha de ser introduit frase per frase, totes acabant amb un punt.\nPer finalitzar l'introduccio del contingut, " +
                        "escrigui en una linea separada \"@32@\"");
                while(true) {
                    if("--cancel".equalsIgnoreCase(line)) {
                        System.out.println("Accio cancelada. Introdueixi les dades de nou");
                        break;
                    }
                    else {
                        String titol = S.nextLine();
                        if(titol.isEmpty())  {
                            System.out.println("El titol no pot ser buit. Torni a seleccionar el format de document a inserir");
                            break;
                        }
                        String autor = S.nextLine();
                        if(autor.isEmpty())  {
                            System.out.println("L'autor no pot ser buit. Torni a seleccionar el format de document a inserir");
                            break;
                        }
                        List <String> contingut = new ArrayList<>();
                        while(true) {
                            String nextl = S.nextLine();
                            if("--cancel".equalsIgnoreCase(nextl)) {
                                System.out.println("Creacio de document cancelada.");
                                return;
                            }
                            if("@32@".equalsIgnoreCase(nextl)) {
                                break;
                            }
                            else if (nextl.lastIndexOf('.') != nextl.length()-1) {
                                System.out.println("Frase introduida incorrectament. Insereixi una frase correcta. Per finalitzar l'insercio de contingut" +
                                        " escrigui \"@32@\". Per cancelar la creacio, escrigui --cancel");
                            }
                            else {
                                contingut.add(nextl);
                            }
                        }
                        if (contingut.isEmpty()) {
                            System.out.println("El contingut no pot ser buit. Torni a seleccionar el format de document a inserir");
                            break;
                        }
                        try {
                            cd_.crearDocument(autor, titol, contingut);
                        }catch (CloneNotSupportedException e) {
                            System.out.println("Ja existeix un document amb aquest titol i autor. Torni a seleccionar el format de document a inserir.");
                            break;
                        }
                        System.out.println("El document de l'autor " + autor + " amb titol " + titol +" s'ha creat!" );
                        //System.out.println(cd_.consultaTitolsAutor(autor));
                        //System.out.println(cd_.getCtrlDoc().getDocument(titol,autor).getContingut());
                        return;
                    }
                }
            }
            if(".xml".equalsIgnoreCase(line)) {
                System.out.println("Ha seleccionat un fitxer .xml. Introdueixi en linees consecutives el valor de les etiquetes <Titol>, <Autor>, i <Contingut> , finalitzant " +
                        "aquest amb \"@32@\"");
                while (true) {
                    if ("--cancel".equalsIgnoreCase(line)) {
                        System.out.println("Accio cancelada. Introdueixi el format del fitxer nou");
                        break;
                    } else {
                        String titol = S.nextLine();
                        if(titol.isEmpty())  {
                            System.out.println("El titol no pot ser buit. Torni a seleccionar el format de document a inserir");
                            break;
                        }
                        String autor = S.nextLine();
                        if(autor.isEmpty())  {
                            System.out.println("L'autor no pot ser buit. Torni a seleccionar el format de document a inserir");
                            break;
                        }
                        List <String> contingut = new ArrayList<>();
                        while(true) {
                            String nextl = S.nextLine();
                            if("--cancel".equalsIgnoreCase(nextl)) {
                                System.out.println("Creacio de document cancelada.");
                                return;
                            }
                            if("@32@".equalsIgnoreCase(nextl)) {
                                break;
                            }
                            else if (nextl.lastIndexOf('.') != nextl.length()-1) {
                                System.out.println("Frase introduida incorrectament. Insereixi una frase correcta. Per finalitzar l'insercio de contingut" +
                                        " escrigui \"@32\". Per cancelar la creacio, escrigui --cancel");
                            }
                            else {
                                contingut.add(nextl);
                            }
                        }
                        if (contingut.isEmpty()) {
                            System.out.println("El contingut no pot ser buit. Torni a seleccionar el format de document a inserir");
                            break;
                        }
                        try {
                            cd_.crearDocument(autor, titol, contingut);
                        }catch (CloneNotSupportedException e) {
                            System.out.println("Ja existeix un document amb aquest titol i autor. Torni a seleccionar el format de document a inserir.");
                            break;
                        }
                        System.out.println("El document amb <Autor> " + autor + " i <Titol> " + titol + " s'ha creat!");
                        return;
                    }
                }
            }
            else if ("--cancel".equalsIgnoreCase(line)) {
                System.out.println("Accio cancelada. Introdueixi una comanda o escrigui --fin per tancar l'entorn");
                return;
            } else {
                System.out.println("Comanda invalida. Introdueixi \".txt\", \".xml\" o --cancel per cancelar l'accio");
            }
        }

    }

    static void OperarConsultaRealitzada() {
            Map<Integer, ResultatConsultaNoms> rcn = cd_.getResultatConsultaNoms();
            Map<Integer, ResultatConsultaPairs> rcp = cd_.getResultatConsultaPairs();
            Map<Integer, ResultatContingut> rcc = cd_.getResultatContingut();
            if (rcn.isEmpty() && rcp.isEmpty() && rcc.isEmpty()) {
                System.out.println("No s'ha realitzat cap consulta. Primer, crei una consulta per a poder ordenarla");
                return;
            }
            System.out.println("Consultes realitzades:");
            for (Map.Entry<Integer, ResultatConsultaNoms> m : rcn.entrySet()) {
                System.out.println("-Id:" + m.getKey() + "-> Tipus: " + m.getValue().getTipusConsulta() +
                        "-> Parametres: " + m.getValue().getParametres());
            }
            for (Map.Entry<Integer, ResultatConsultaPairs> n : rcp.entrySet()) {
                System.out.println("-Id:" + n.getKey() + "-> Tipus: " + n.getValue().getTipusConsulta() +
                        "-> Parametres: " + n.getValue().getParametres());
            }
            for (Map.Entry<Integer, ResultatContingut> p : rcc.entrySet()) {
                System.out.println("-Id:" + p.getKey() + "-> Tipus: " + p.getValue().getTipusConsulta() +
                        "-> Parametres: " + p.getValue().getParametres());
            }
            Scanner S = new Scanner(System.in);
            while (true) {
                System.out.println("Entra dos identificadors de consultes");
                String next = S.nextLine();
                String next2 = S.nextLine();
                while (true) {
                    try {
                        int k = Integer.parseInt(next);
                        int k2= Integer.parseInt(next2);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada incorrecte. Ha d'introduir un numero valid");
                        break;
                    }
                    break;
                }
                if("--cancel".equalsIgnoreCase(next)) {
                    System.out.println("Operacio Cancelada");
                    return;
                }
                int k = Integer.parseInt(next);
                int k2 = Integer.parseInt(next2);
                if(!rcn.containsKey(k) && !rcp.containsKey(k) && !rcc.containsKey(k)) {
                    System.out.println("No existeix cap consulta amb id = " + k + "Torni a inserir un id valid o escrigui --cancel per abortar.");
                }
                else if (!rcn.containsKey(k2) && !rcp.containsKey(k2) && rcp.containsKey(k2)) {
                    System.out.println("No existeix cap consulta amb id = " + k2 + "Torni a inserir un id valid o escrigui --cancel per abortar.");
                }
                else if (rcn.containsKey(k) && rcn.containsKey(k2)) {
                    System.out.println("Ha seleccionat les consultes -> " + "Tipus: " + rcn.get(k).getTipusConsulta() +
                            "-> Parametres: " + rcn.get(k).getParametres() +  "\t" + "Tipus: " + rcn.get(k2).getTipusConsulta() +
                                    "-> Parametres: " + rcn.get(k2).getParametres() +  "\n"+
                            "Si desitja fer Interseccio, escrigui \"i\".\n" +
                            "Si desitja fer unio, escrigui \"u\".\n"+
                            "Si desitja fer diferencia, escrigui \"d\".\n" +
                                    "Si desitja fer diferencia simetrica, escrigui \"s\".\n");

                    while(true) {
                        String ordre = S.nextLine();
                        if("--cancel".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio cancelada");
                            return;
                        }
                        else if("i".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio realitzada! Resultat:");
                            cd_.operarConsulta(k,k2, "Interseccio");
                            for(String st: rcn.get(cd_.getConsultaid()-1).getResultat()) {
                                System.out.println("-" + st);
                            }
                            return;
                        }
                        else if("u".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio realitzada! Resultat:");
                            cd_.operarConsulta(k,k2, "Unio");
                            for(String st: rcn.get(cd_.getConsultaid()-1).getResultat()) {
                                System.out.println("-" + st);
                            }
                            return;
                        }
                        else if("d".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio realitzada! Resultat:");
                            cd_.operarConsulta(k,k2, "Diferencia");
                            for(String st: rcn.get(cd_.getConsultaid()-1).getResultat()) {
                                System.out.println("-" + st);
                            }
                            return;
                        }
                        else if("s".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio realitzada! Resultat:");
                            cd_.operarConsulta(k,k2, "DiferenciaSimetrica");
                            for(String st: rcn.get(cd_.getConsultaid()-1).getResultat()) {
                                System.out.println("-" + st);
                            }
                            return;
                        }
                        else {
                            System.out.println("Comanda incorrecte. Escrigui \"i\"  \"u\" \"d\" o \"s\" per a fer operacio que corresponda." +
                                    " Si desitja cancelar la operacio, escrigui --cancel");
                        }
                    }
                }
                else if (rcp.containsKey(k) && rcp.containsKey(k2)) {
                    System.out.println("Ha seleccionat les consultes -> " + "Tipus: " + rcp.get(k).getTipusConsulta() +
                            "-> Parametres: " + rcp.get(k).getParametres() +  "\t" + "Tipus: " + rcp.get(k2).getTipusConsulta() +
                            "-> Parametres: " + rcp.get(k2).getParametres() +  "\n"+
                            "Si desitja fer Interseccio, escrigui \"i\".\n" +
                            "Si desitja fer unio, escrigui \"u\".\n"+
                            "Si desitja fer diferencia, escrigui \"d\".\n" +
                            "Si desitja fer diferencia simetrica, escrigui \"s\".\n");

                    while(true) {
                        String ordre = S.nextLine();
                        if("--cancel".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio cancelada");
                            return;
                        }
                        else if("i".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio realitzada! Resultat:");
                            cd_.operarConsulta(k,k2, "Interseccio");
                            for(String st: rcp.get(cd_.getConsultaid()-1).getResultat().keySet()) {
                                for (String titol: rcp.get(k).getResultat().get(st)) {
                                    System.out.println("- Autor:"+ st +", Titol:"+titol);
                                }
                            }
                            return;
                        }
                        else if("u".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio realitzada! Resultat:");
                            cd_.operarConsulta(k,k2, "Unio");
                            for(String st: rcp.get(cd_.getConsultaid()-1).getResultat().keySet()) {
                                for (String titol: rcp.get(k).getResultat().get(st)) {
                                    System.out.println("- Autor:"+ st +", Titol:"+titol);
                                }
                            }
                            return;
                        }
                        else if("d".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio realitzada! Resultat:");
                            cd_.operarConsulta(k,k2, "Diferencia");
                            for(String st: rcp.get(cd_.getConsultaid()-1).getResultat().keySet()) {
                                for (String titol: rcp.get(k).getResultat().get(st)) {
                                    System.out.println("- Autor:"+ st +", Titol:"+titol);
                                }
                            }
                            return;
                        }
                        else if("s".equalsIgnoreCase(ordre)) {
                            System.out.println("Operacio realitzada! Resultat:");
                            cd_.operarConsulta(k,k2, "DiferenciaSimetrica");
                            for(String st: rcp.get(cd_.getConsultaid()-1).getResultat().keySet()) {
                                for (String titol: rcp.get(k).getResultat().get(st)) {
                                    System.out.println("- Autor:"+ st +", Titol:"+titol);
                                }
                            }
                            return;
                        }
                        else {
                            System.out.println("Comanda incorrecte. Escrigui \"i\"  \"u\" \"d\" o \"s\" per a fer operacio que corresponda." +
                                    " Si desitja cancelar la operacio, escrigui --cancel");
                        }
                    }
                }
                else if (rcc.containsKey(k) && rcc.containsKey(k2)) {
                    System.out.println("El resultat de la consulta contingut de document no pot ordenar");
                }

            }

    }

    static void gestionaExpressionBooleana() throws ExprMalFormadaException {
        Map<Integer, ExprBooleana> exprBooleanaMap=cd_.getExpressions();
        for (int id : exprBooleanaMap.keySet()) {
            System.out.println("identificador: "+ id +" Expressio: "+exprBooleanaMap.get(id).getExpresio());
        }
        Scanner S = new Scanner(System.in);
        System.out.println("Entra m si vol modificar, e si vol esborrar");
        String op= S.nextLine();
        System.out.println("Entra un identificador de Expressio");
        String next=S.nextLine();
        while (true) {
            try {
                int k = Integer.parseInt(next);
            } catch (NumberFormatException e) {
                System.out.println("Entrada incorrecte. Ha d'introduir un numero valid");
                break;
            }
            break;

        }
        int id=Integer.parseInt(next);
        if (!exprBooleanaMap.containsKey(id)) {
            System.out.println("Identificador invalid");
        }
        if ("m".equalsIgnoreCase(op)) {
            System.out.println("Entra nova Expression");
            String nova=S.nextLine();
            try {
                cd_.modificarExprBooleana(id,nova);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Expressio invalid.");
            }

        }
        else if ("e".equalsIgnoreCase(op)) {
            cd_.borrarExpressioBooleana(id);
        }

    }
    static void esperarComanda() throws Exception {
        Scanner S = new Scanner(System.in);
        while (true) {
            String line = S.nextLine();
            if ("--func".equalsIgnoreCase(line)) {
                mostraOpcions();
            }
            else if("1".equalsIgnoreCase(line)) {
                CreacioDocument();
            }
            else if("2".equalsIgnoreCase(line)) {
                ModificaDocument();
            }
            else if("3".equalsIgnoreCase(line)) {
                EsborraDocument();
            }
            else if("4".equalsIgnoreCase(line)) {
                NovaConsulta();
            }
            else if("5".equalsIgnoreCase(line)) {
                OrdenarConsultes();
            }
            else if("6".equalsIgnoreCase(line)) {
                OperarConsultaRealitzada();
            }
            else if("7".equalsIgnoreCase(line)) {
                gestionaExpressionBooleana();
            }
            else if("--fin".equalsIgnoreCase(line)) {
                break;
            }
            else {
                System.out.println("Entri una comanda valida. Per consultar les funcionalitats disponibles, introdueixi --func.\nSi desitja tancar l'entorn, Escrigui --fin");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        estatinicial();
        String s;
        Scanner S = new Scanner(System.in);
        while(true) {
            esperarComanda();
            break;
        }
    }
}


