package domini.Clases;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.*;
import domini.exceptions.*;
import java.io.*;

/**
 * Classe que defineix una expressio booleana
 * L'expressio booleana esta formada per operadors logics ('&', '|', '!'), paraules i frases (denotades entre "")
 * Tambe poden tenir conjunt de paraules, demilitades per '{', '}' i poden contindre parentesis
 * @author pol.forner
 */
public class ExprBooleana extends Object implements Serializable{ 
//PART PUBLICA
    //Atributs publics
    //Funcions publiques
    /**
     * Construtora amb parametre
     * Crea una instancia de ExprBooleana amb expressio
     * @param expressio Expressio booleana
     * @exception ExprMalFormadaException si el parametre expressio no conte una expressio boolena ben formada
     */
    public ExprBooleana(String expressio) throws ExprMalFormadaException{
        expressio = treureEspaisExtra(expressio);
        if(!benFormat(expressio)) throw new ExprMalFormadaException("L'expressió " + expressio + " està mal formada");
        expressio_ = expressio;
    }

    /**
     * 
     * @return
     */
    public String getExpresio() {
        return expressio_;
    }
    /**
     * Crea un arbre d'expressio a partir de la expressio boolena. Retorna un BinTree que conte l'expressio booleana
     * en forma de arbre binari. Aquest arbre esta format per nodes que, o bé son operadors logics (& i |) on els seus fills
     * son la part esquerra i la dreta del operant a l'expressió, o bé son atoms (paraulaes o frases sense operadors logics).
     * @return Retorna una instancia de BinTree format per la expressió booleana de this
     */
    public BinTree getArbreExpressio() {
        BinTree resultat = new BinTree();
        String expressio = treureQueries(expressio_); 
        resultat = convertirEnTree(expressio);
        return resultat;
    }

//PART PRIVADA
    //Atributs privats
    /**
     * Expressio booleana.
     */
    private String expressio_;
    //Funcions privades
    /**
     * Retorna un String que es una copia de expressio amb 
     * els substrings de la forma {a1 a2 .. an} transformats en (a1 & a2 & ..& an).
     * @param expressio Expressio booleana
     * @return Retorna l'expressio modificada
     */
    private static String treureQueries(String expressio) {
        for (int i = 0; i < expressio.length(); ++i) {
            if (expressio.charAt(i) == '{') {
                int j = i + 1;
                while (j < expressio.length() && expressio.charAt(j) != '}') ++j;

                String replace = "(" + expressio.substring(i + 1, j) + ")";
                replace = replace.replaceAll(" ", " & ");
                String ini;
                if (i>= 0) ini = expressio.substring(0, i);
                else ini = "";
                String end;
                if (j + 1 < expressio.length()) end = expressio.substring(j + 1, expressio.length());
                else end = "";
                expressio = ini + replace + end;
                i = ini.length() + replace.length();
            }
        }
        return expressio;
    }

    /**
     * Subtitueix els espais de tamany major que 1 per un sol espai
     * @param expressio expressio booleana
     * @return un String que es el parametre sense espais majors que 1
     */
    private static String treureEspaisExtra(String expressio) {
        return  expressio.trim().replaceAll(" +", " ");
    }

    /**
     * Retorna un arbre binari que representa el parametre
     * @param expressio una expressio booleana ben formada
     * @return Un BinTree inicialitzat amb el parametre
     */
    private static BinTree convertirEnTree(String expressio) {
        if (esAtom(expressio)) {
            Boolean esNegat = false;
            if (expressio.charAt(0) == '!') esNegat = true;
            if (esNegat) expressio = expressio.substring(1,expressio.length());
            BinTree atom = new BinTree(expressio);
            if (esNegat) atom.setNegat();
            return atom;
        }
        Boolean esNegat = false;
        Map<Integer,Character> operands = new TreeMap<Integer, Character>();
        Boolean trobat = false;
        int indexOperant = 0;
        while (!trobat) {
            int i = 0;
            int contador = 0;
            while (i < expressio.length()) {
                char c = expressio.charAt(i);
                if (c == '(') ++contador;
                else if (c == ')') --contador;
                else if (contador == 0 && (c == '&' | c == '|')) operands.put(i,c);
                ++i;
            }
            for (Map.Entry<Integer, Character> entry : operands.entrySet()) {
                if(entry.getValue() == '&') {
                    trobat = true;
                    indexOperant = entry.getKey();
                }
                if(trobat) break;
            }
            if (!trobat) {
                for (Map.Entry<Integer, Character> entry : operands.entrySet()) {
                    if(entry.getValue() == '|') {
                        trobat = true;
                        indexOperant = entry.getKey();
                    }
                    if(trobat) break;
                }
            }
            if(!trobat) {
                if (expressio.charAt(0) == '!') {
                    esNegat = !esNegat;
                    expressio = expressio.substring(1,expressio.length());
                }
                expressio = expressio.substring(1,expressio.length() - 1);

            }
        }
        String esquerra = expressio.substring(0, indexOperant - 1);
        String dret = expressio.substring(indexOperant + 2, expressio.length());
        String op =  expressio.substring(indexOperant, indexOperant + 1);
        BinTree node = new BinTree(op);
        node.setEsquerra(convertirEnTree(esquerra));
        node.setDret(convertirEnTree(dret));
        if (esNegat) node.setNegat();
        return node;
    }

    /**
     * Retorna si el parametre es un atom, no conte cap operant.
     * @param expressio Una expressio booleanaa.
     * @return Si el parametre es un atom.
     */
    private static Boolean esAtom(String expressio) {
        char[] aux = expressio.toCharArray();
        for (char c: aux) if (c == '&' | c == '|') return false;
        return true;
    }

    /**
     * Retorna si el parametre esta correctament format. Es comproba que els parentesis, claudators i dobles cometes.
     * estiguin correctament. També comproba si les negacions estan ben colocades. Finalment revisa si els operants estan correctament
     * colocats.
     * @param expressio
     * @return si el parametre es una expressio booleana ben formada.
     */
    private static Boolean benFormat(String expressio) {
        char[] aux = expressio.toCharArray();
        int contadorCla = 0; //Contador per els claudators '{' '}'
        int contadorPar = 0; //Contador per els parentesis '(' ')'
        int contadorCom = 0; //Contador per les dobles cometes '"'
        //Comprobem que els claudators, parentesis i dobles cometes estan correctes
        for (char c: aux) {
            if (c == '(') ++contadorPar;
            else if (c == ')') {
                --contadorPar;
                if (contadorPar < 0) return false;
            }
            else if (c == '{') ++contadorCla;
            else if (c == '}') {
                --contadorCla;
                if (contadorCla < 0) return false;
            }
            else if (c == '"') ++contadorCom;
        }
        if((contadorPar != 0) | (contadorCla != 0) | (contadorCom % 2 != 0)) return false;

        //intercanviem totes les "frases" per una sola paraula i comprobrem que estiguen be
        for (int i = 0; i < expressio.length(); ++i) {
            int j = expressio.indexOf("\"", i);
            int k = 0;
            if (j != -1) {
                k = expressio.indexOf("\"", j + 1);
                expressio = expressio.substring(0, j) + "a" + expressio.substring(k + 1, expressio.length());
            }
            else break;
            i = j + 1;
        }
        //revisem que despres de una '!'' no hi ha ni '&', ni '|' ni ' ' ni ')'
        for (int i = 0; i < expressio.length() - 1; ++i) {
            char actual = expressio.charAt(i);
            char seguent = expressio.charAt(i + 1); 
            if (actual == '!' &&(seguent == '&' | seguent == '|' | seguent == ' '| seguent == ')')) return false;
        }
        //treiem tots els parentesis i negacions
        expressio = treureQueries(expressio);
        expressio = expressio.replaceAll("\\(", "");
        expressio = expressio.replaceAll("\\)", "");
        expressio = expressio.replaceAll("!", "");
        //ara ho separem per espais i comprobem que tigui una forma correcta
        ArrayList<String> split = new ArrayList<String>(Arrays.asList(expressio.split(" ")));
        for (int i = 0; i < split.size(); ++i) {
            if ((i % 2 == 0) && (split.get(i).equals("&") | split.get(i).equals("|") )) return false;
            if ((i % 2 != 0) && !(split.get(i).equals("&")  | split.get(i).equals("|"))) return false;
        }
        return true;
    }
}