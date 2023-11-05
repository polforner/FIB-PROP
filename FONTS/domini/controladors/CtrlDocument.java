package domini.controladors;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;
import domini.exceptions.*;

import domini.Clases.*;

/**
 * Controlador de la clase Document
 * El controlador s'ocupa de totes les tasques relacionades amb els documents del sistema i actualitza
 * continuament les estructures de dades del sistema necessaries perque les funcionalitats relacionades
 * amb els documents funcionin correctament
 * @author gerard.garcia.velasco
 */
public class CtrlDocument implements Serializable{
    /**
     * Estructura de dades amb els títols de cada autor, i l’identificador del seu corresponent document
     */
    private final Map<String, Map<String,Integer>> titolsAutor_;

    /**
     * Estructura de dades amb tots els prefixos dels noms dels autors.
     */
    private final Trie prefixosAutors_;

    /**
     * Estructura de dades amb els identificadors dels documents i la seva corresponent instància.
     */
    private final Map<Integer,Document> identificadors_;

    /**
     * Instància de la classe que permet l'ús del patró Singleton.
     */
    private static CtrlDocument singletonObject;

    /**
     * Index del sistema
     */
    private final Index index_;

    /**
     * Enter que serveix per l'assignació d’identificador al crear Documents.
     */
    private int id_;

    /**
     * Constructora que inicialitza totes les estructures de dades i atributs.
     */
    private CtrlDocument() throws FileNotFoundException {
        titolsAutor_ = new HashMap<>();
        prefixosAutors_ = new Trie();
        identificadors_ = new TreeMap();
        index_ = new Index();
        id_ = 1;
    }

    /**
     * Getter de la instancia de controlador de document del sistema, per a aplicar el patró Singleton
     * @return retorna el controlador de document del sistema
     * @throws FileNotFoundException si el ctrl de document no pot crear-se
     * perque no troba el fitxer de les stopwords salta excepcio
     */
    public static CtrlDocument getInstance() throws FileNotFoundException{
        if (singletonObject == null) {
            singletonObject = new CtrlDocument();
        }
        return singletonObject;
    }

    /**
     * Genera la instància de Document i li assigna un Id, actualitzant les corresponents estructures de dades de l'Índex amb la informació del nou Document,
     * i cridant a AfegirDocument per a actualitzar les estructures de dades de la classe.
     * @param autor autor del nou document
     * @param titol titol del nou document
     * @param contingut contingut del nou document
     * @throws Exception en cas que existeixi duplicat del document
     */
    public void nouDocument(String autor, String titol, List<String> contingut) throws Exception {
        if (titolsAutor_.containsKey(autor) && titolsAutor_.get(autor).containsKey(titol)){
            throw new CloneNotSupportedException("Document ja existent");
        }
        else {
            Document Doc = new Document(autor, titol, contingut, id_);
            Map<String, Integer> keyWordsDoc = new TreeMap<>();
            keyWordsDoc = index_.afegirDocument(contingut, id_);
            Doc.setKeyWords(keyWordsDoc);
            AfegirDocument(Doc);
            ++id_;
        }
    }

    /**
     * Actualitza les estructures de dades que mantenen els Documents (titolsAutor_, prefixosAutors_, identificadors_)
     * @param doc Representa el document a afegir en titolsAutor_ i prefixosAutors_
     */
    public void AfegirDocument(Document doc) throws Exception {
        String titol = doc.getTitol();
        String autor = doc.getAutor();
        int id = doc.getDocID();
        //Afegir dins de titolsAutor_
        if (!titolsAutor_.containsKey(autor)) { // si no existeix l'autor
            Map <String,Integer> titols = new HashMap<>();
            titols.put(titol, id);
            titolsAutor_.put(autor, titols);
        }
        else {
            titolsAutor_.get(autor).put(titol,id);
        }
        /*Afegir dins de prefixosAutors_, pendent d'acabar perquè no hi ha funció
        per comprovar l'existència de la paraula al trie
         */
        Set<String> existeix = prefixosAutors_.buscarParaules(autor);
        if (!existeix.contains(autor)) {
            prefixosAutors_.inserirParaula(autor);
        }

        //Afegir dins del Treemap d'identificadors
        if(!identificadors_.containsKey(id)) identificadors_.put(id,doc);
    }

    /**
     * Elimina el Document identificat pels paràmetres titol i autor, i elimina totes les seves dependències.
     * @param titol titol del document
     * @param autor titol del document
     */
    public void eliminarDocument(String titol, String autor) throws Exception {
        //Elimina document de TítolsAutor
        Document doc = getDocument(titol,autor);
        int id = doc.getDocID();
        if (titolsAutor_.containsKey(autor)) {
            titolsAutor_.get(autor).remove(titol);
            if (titolsAutor_.get(autor).isEmpty()) {
                titolsAutor_.remove(autor);
            }
        }
        //Elimina nom de l'autor del document de PrefixosAutors
        Set<String> existeix = prefixosAutors_.buscarParaules(autor);
        if (!existeix.isEmpty()) {
            prefixosAutors_.esborrarPaula(autor);
        }

        //Esborra les entrades del document modificat per les keyWords del index eliminades del document
        List<String> eliminades = new ArrayList<>();
        for (Map.Entry<String, Integer> paraula : doc.getKeyWords().entrySet()) {
            eliminades.add(paraula.getKey());
        }
        index_.actualitzaIndex(eliminades, doc.getDocID());

        //Eliminar el document del TreeMap d'identificadors
        if(identificadors_.containsKey(id)) identificadors_.remove(id);
    }

    /**
     * Substitueix el títol d’un document pel paràmetre titolNou, i actualitza les estructures de dades corresponents que contenien el títol antic.
     * @param titolNou representa el titol modificat del document
     * @param titolVell representa el titol antic del document
     * @param autor representa l'autor del document
     */
    public void ModificarTitol(String titolNou, String titolVell, String autor) throws Exception {
        //actualitzar el document, i fer la nova entrada amb el títol canviat en titolsAutor_
        Document docNou = getDocument(titolVell, autor);
        if (titolsAutor_.containsKey(autor)) {
            if (titolsAutor_.get(autor).containsKey(titolNou)) {
                throw new CloneNotSupportedException("Document amb titol: "+titolNou+" autor: "+autor+ "ja existit");
            }
            else {
                eliminarDocument(titolVell, autor);
                docNou.setTitol(titolNou);
                AfegirDocument(docNou);
            }
        }
        else {
            eliminarDocument(titolVell, autor);
            docNou.setTitol(titolNou);
            AfegirDocument(docNou);
        }
    }

    /**
     * Substitueix l’autor d’un document pel paràmetre autorNou, i actualitza les estructures de dades corresponents que contenien l’autor antic.
     * @param autorNou representa l'autor modificat del document
     * @param autorVell representa l'autor antic del document
     * @param titol representa el titol del document
     */
    public void ModificarAutor(String autorNou, String autorVell, String titol) throws Exception {

        Document docNou = getDocument(titol,autorVell);
        if (titolsAutor_.containsKey(autorNou)) {
            if (titolsAutor_.get(autorNou).containsKey(titol)) {
                throw new CloneNotSupportedException("Document amb titol: "+titol+" autor: "+autorNou+ "ja existit");
            }
            else {
                eliminarDocument(titol, autorVell);
                docNou.setAutor(autorNou);
                AfegirDocument(docNou);
            }
        }
        else {
            eliminarDocument(titol, autorVell);
            docNou.setAutor(autorNou);
            AfegirDocument(docNou);
        }
    }

    /**
     *  Modifica el contingut d’un document i actualitza les estructures de dades corresponents que contenien el contingut antic.
     * @param titol representa el titol del document
     * @param autor representa l'autor del document
     * @param contingutNou representa el contingut modificat del document
     */
    public void ModificarContingut(String titol, String autor, List<String> contingutNou) {
        Document docNou = getDocument(titol,autor);
        docNou.setContingut(contingutNou);
        int idDoc = docNou.getDocID();
        titolsAutor_.get(autor).replace(autor,idDoc);

        //Esborra totes les vectoritzacions de tots els documents
        for (int i : identificadors_.keySet()) {
            identificadors_.get(i).esborraVectorDocument();
        }

        //Afegeix el nou contingut de document al index i obte les keyWords actuals del sistema amb el nombre de cops que apareixen
        Map<String, Integer> currentKeyWords = index_.afegirDocument(docNou.getContingut(), idDoc);

        //Obte keyWords velles del document
        Map<String, Integer> oldKeyWords = docNou.getKeyWords();

        //Actualitza les keyWords del document
        docNou.setKeyWords(currentKeyWords);

        List<String> eliminada = new ArrayList<>();

        //Agafa les keyWords eliminades del document
        for (Map.Entry<String, Integer> paraula : oldKeyWords.entrySet()) {
            String currentWord = paraula.getKey();
            if (!currentKeyWords.containsKey(currentWord)) eliminada.add(currentWord);
        }

        //Esborra les entrades del document modificat per les keyWords del index eliminades del document
        index_.actualitzaIndex(eliminada , idDoc);
    }

    /**
     * Funcio que obte un document donats el seu titol i autor
     * @param titol representa el titol del document
     * @param autor representa l'autor del document
     * @return retorna l'objecte Document
     */
    public Document getDocument(String titol, String autor) {
        if (!titolsAutor_.containsKey(autor) || !titolsAutor_.get(autor).containsKey(titol)) throw new RuntimeException("el Document identificat per Autor: " + autor + " i Titol: " + titol + " no existeix");
        else {
            int docId = titolsAutor_.get(autor).get(titol);
            return identificadors_.get(docId);
        }
    }

    /**
     * Getter del document identificat per docID
     * @param docID identificador numeric del document
     * @return document identificat per docID
     */
    public Document getDocPerIndex(Integer docID) {
        if (!identificadors_.containsKey(docID)) throw new RuntimeException("el Document identificat per identificador: "+ docID + " no existeix"); 
        return identificadors_.get(docID);
    }

    /**
     * Getter de titolsAutor_
     * @return retorna el Map titolsAutor_
     */
    public Map<String, Map<String,Integer>> getTitolsAutor() {
        return titolsAutor_;
    }

    /**
     * Getter del enter per mantenir els identificadors de documents
     * @return retorna l'enter per manternir els identificadors de documents
     */
    public int getId() {
        return id_;
    }

    /**
     * Getter de prefixosAutors_
     * @return retorna el Trie prefixosAutors_
     */
    public Trie getPrefixosAutors() {
        return prefixosAutors_;
    }

    /**
     * Getter de identificadors_
     * @return retorna el Map identificadors_
     */
    public Map<Integer,Document> getIdentificadors() {
        return identificadors_;
    }

    /**
     * Getter dels vectors dels documents del sistema
     * @return llista de vectors dels documents del sistema
     */
    public Map<Integer, Map<String, Double>> getVectorizacions() {
        Map<Integer, Map<String, Double>> vectors = new TreeMap<>();
        for (Map.Entry<Integer, Document> doc : identificadors_.entrySet()) {
            DocumentVectoritzat vec = doc.getValue().vectoritzarDocument();
            vec.calcularVector(doc.getValue().getKeyWords(), index_.getNdocsKeyWords(doc.getValue().getKeyWords()), index_.getParaulesRestants(doc.getValue().getDocID()), identificadors_.size(), index_.nombreParaulesDocument(doc.getValue().getContingut()));
            vectors.put(doc.getKey(),vec.getVectorDocument());
        }
        return vectors;
    }

    /**
     * Getter de les entrades de l'index corresponent a la keyword paraula
     * @param paraula keyword sobre la que es vol fer la consulta
     * @return entrades del index per la keyword paraula
     */
    public Map<Integer, Set<Integer>> getDocsFrasesParaula(String paraula) {
        return index_.getDocsFrasesParaula(paraula);
    }

    /**
     * Getter de les entrades de l'index corresponents a les keywords que conte frase
     * @param frase frase sobre la que es vol fer la consulta
     * @return llista de les keywords contingudes en frase amb les seves corresponents entrades del index
     */
    public Map<String, Map<Integer, Set<Integer>>> getDocsFrasesFrase(String frase) {
        return index_.getDocsFrasesFrase(frase);
    }

    /**
     * Getter dels identificadors dels documents del sistema que contenen la keyword paraula
     * @param paraula keyword sobre la que es vol fer la consulta
     * @return llista d'identificadors de documents que contenen la keyword paraula
     */
    public Set<Integer> getDocsParaula(String paraula) {
        return index_.getDocsParaula(paraula);
    }

    public  Map<Integer, Set<Integer>> getComplement(Map<Integer, Set<Integer>> autorsTitols) {
        Map<Integer, Set<Integer>> resultat = new HashMap<>();
        for(Map.Entry<Integer, Set<Integer>> entry : autorsTitols.entrySet()) {
            Document doc = identificadors_.get(entry.getKey());
            Set<Integer> resultatAux = new HashSet<>();
            for (int i = 0; i < doc.getContingut().size(); ++i) {
                if (!autorsTitols.get(entry.getKey()).contains(i)) resultatAux.add(i);
            }
            if (!resultatAux.isEmpty()) resultat.put(entry.getKey(),resultatAux);
        }
        return resultat;
    }
}

