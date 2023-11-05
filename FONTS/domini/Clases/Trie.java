package domini.Clases;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import domini.exceptions.*;

/**
 * Classe que defineix un Trie
 * El Trie consta de un Node arrel on a partir dell i unin els caracters que recorem quan pasem per un node central, podem formar paraules
 * @author pol.forner
 */
public class Trie implements Serializable{
//PART PUBLICA
    //Funcions publiques
    /**
     * Constructora per defecte
     */
    public Trie() {
        root_ = new Node();
    }

    /**
     * Retorna totes les paraules que tenen coma a prefix el parametre
     * @param prefix un String
     * @return un Set<String> que conte totes les paraules amb el prefix
     */
    public Set<String> buscarParaules(String prefix) {
        Node nodePrefix = root_;
        int i = 0;
        while (nodePrefix != null && i < prefix.length()) {
            nodePrefix = buscarPrefix(nodePrefix, prefix.charAt(i));
            ++i;
        }
        Set<String> result = new HashSet<>();
        if (nodePrefix != null) {
            result = buscarSufixos(nodePrefix);
            afegeixPrefix(result, prefix);
        }
        return result;
    }
 
    /**
     * Insereix el parametre al arbre
     * @param paraula un String
     */
    public void inserirParaula(String paraula) throws AutorNoExisteixException{
        int i = 0;
        Node node = root_;
        while (i < paraula.length()) {
            if (node.teValor()) {
                if (paraula.charAt(i) == node.getValor()) {
                    ++i;
                    node = node.getCentral();
                }
                else if (paraula.charAt(i) < node.getValor()) {
                    node = node.getEsquerra();
                }
                else {
                    node = node.getDret();
                }
            } else {
                node.setValor(paraula.charAt(i));
                ++i;
                node = node.getCentral();
            }
        }
        
        try {
            node.setFinal();
        }catch(NodeNoFinalException e){
            throw new AutorNoExisteixException("S'intenta inserir la paraula: " + paraula + " que ja esta inserida");
        }
    }

    /**
     * Esborra el parametre al arbre
     * @param paraula
     */
    public void esborrarPaula(String paraula) throws AutorNoExisteixException{
        Node nodePrefix = root_;
        int i = 0;
        while (nodePrefix != null && i < paraula.length()) {
            nodePrefix = buscarPrefix(nodePrefix, paraula.charAt(i));
            ++i;
        }
        if (nodePrefix != null) {
            try{
                nodePrefix.treuFinal();
            }
            catch(NodeNoFinalException e) {
                throw new AutorNoExisteixException("S'intenta esborrar la paraula: " + paraula + " que no existeix");
            }
        }
        else throw new AutorNoExisteixException("S'intenta esborrar la paraula: " + paraula + " que no existeix");
    }

    //PART PRIVADA

    //Atributs privats
    /** 
     * Es l'arrel del arbre
     */
    private final Node root_;

    //Funcions privades
    /**
     * Busca apartir del node 'ini' les paraules que comencen pel caracter 'lletra'
     * @param ini Un node no null
     * @param lletra qualsevol caracter
     * @return Retorna un Node que es algun descendent de 'ini' el qual conte el caracter 'lletra' 
     */
    private Node buscarPrefix(Node ini, char lletra) {
        Node result = null;
        if(ini.teCentral()) {
            if (lletra == ini.getValor()) result = ini.getCentral();
            else if (ini.teEsquerra() && lletra < ini.getValor()) result =  buscarPrefix(ini.getEsquerra(), lletra);
            else if (ini.teDret() && lletra > ini.getValor())result = buscarPrefix(ini.getDret(), lletra);
        }
        return result;
    }

    /**
     * A partir del node del parametre busca tots els posibles sufixos que es poden formar
     * @param node Un node no null
     * @return Retorna un Set<String> que son tots els possibles sufixos que es formen desde el parametre
     */
    private Set<String> buscarSufixos(Node node) {
        Set<String> resultat = new HashSet<>();
        if (node.esFinal()) resultat.add("");
        if (node.teValor()) {
            resultat.addAll(buscarSufixos(node.getEsquerra()));
            resultat.addAll(buscarSufixos(node.getDret()));
            Set<String> sufixosCentral = buscarSufixos(node.getCentral());
            afegeixPrefix(sufixosCentral, String.valueOf(node.getValor()));
            resultat.addAll(sufixosCentral);
        }
        return resultat;
    }

    /**
     * Afegeix a cada element de la llista de 'sufixos' el 'prefix'
     * @param sufixos Una llista de strings que pot ser buida
     * @param prefix Un String que pot ser buit
     */
    private void afegeixPrefix(Set<String> sufixos, String prefix) {
        Set<String> aux = new HashSet<>();
        for (String sufix : sufixos) {
            aux.add(prefix + sufix);
        }
        sufixos.clear();
        sufixos.addAll(aux);
    }
}
