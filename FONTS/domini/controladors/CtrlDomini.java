package domini.controladors;

import domini.Clases.ExprBooleana;
import domini.Clases.ResultatConsultaNoms;
import domini.Clases.ResultatConsultaPairs;
import domini.Clases.ResultatContingut;
import domini.exceptions.ExprMalFormadaException;
import domini.Clases.Document;
import persistencia.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
/**
 * Controlador de domini
 * El controlador s'encarrega de agrupar totes les funcionalitats del programa en un sol lloc
 * per tal que la comunicació entre capes sigui millor i més facil.
 * @author gerard.garcia.velasco
 */
public class CtrlDomini {
    /**
     * Controlador de documents necessari per fer les funcionalitats
     * relacionades amb els documents
     */
    private CtrlDocument ctrlDocument_;

    /**
     * Controlador d'expressions booleanes necessari per fer les funcionalitats
     * relacionades amb les expressions booleanes
     */
    private CtrlExprBooleanes ctrlExprBooleanes_;

    /**
     * Controlador de consultes necessari per fer les funcionalitats
     * relacionades amb consultes
     */
    private ControladorConsulta ctrlConsulta_;

    private ImportarExportarTxt txtIEGestor;
    private ImportarExportarXML xmlIEGestor;
    private ImportarExportarJSON jsonIEGestor;


    /**
     * Creadora
     * @throws FileNotFoundException excepcio si no es troba el document de stop words
     */
    public CtrlDomini() throws FileNotFoundException,IOException,ClassNotFoundException {

            ctrlDocument_ = CtrlDocument.getInstance();
            ctrlExprBooleanes_ = CtrlExprBooleanes.getInstance();



            ctrlConsulta_ = new ControladorConsulta();
            txtIEGestor = new ImportarExportarTxt();
            xmlIEGestor = new ImportarExportarXML();
            jsonIEGestor = new ImportarExportarJSON();


    }

    /**
     * Per guardar dades actuals del programa
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public void guardarEstat() throws IOException, ClassNotFoundException{

        GestorDocuments gd = new GestorDocuments();
        gd.guardar(ctrlDocument_);
        GestorExprBooleanes ge = new GestorExprBooleanes();
        ge.guardar(ctrlExprBooleanes_);
    }

    /**
     * Per carregar dades guardades en fitxer determinat
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public void carregarEstat() throws IOException, ClassNotFoundException{

        GestorDocuments gd = new GestorDocuments();
        ctrlDocument_ = gd.carregar();
        GestorExprBooleanes ge = new GestorExprBooleanes();
        ctrlExprBooleanes_ = ge.carregar();
    }
    /**
     * Guarda els documents en format txt
     * @param docs conjunt de documetns a guardat
     * @param path ruta del fitxer on es volen exportar els documents
     * @throws IOException
     */

    public void guardarDocumentTxt(Map<String,Map<String,List<String>>> docs ,String path) throws IOException {
        txtIEGestor.exportar(path,docs);

    }

    /**
     * Guarda els documents en format xml
     * @param docs conjunt de documetns a guardat
     * @param path ruta del fitxer on es volen exportar els documents
     * @param junt indica guardar documents en un fitxer o fitxers separats
     * @throws IOException
     */
    public void guardarDocumentXml(Map<String,Map<String,List<String>>> docs ,String path,boolean junt) throws IOException {
        if (junt) {
            xmlIEGestor.exportar(path, docs);
        }
        else {
            xmlIEGestor.exportarSeparat(path,docs);
        }
    }
    /**
     * Guarda els documents en format json
     * @param docs conjunt de documetns a guardat
     * @param path ruta del fitxer on es volen exportar els documents
     * @throws IOException
     */
    public void guardarDocumentJson(Map<String,Map<String,List<String>>> docs ,String path) throws IOException {
            jsonIEGestor.exportar(path, docs);
    }
    /**
     * Carregar el document en format json
     * @param path ruta on esta el document
     * @throws IOException
     */
    public void carregarDocumentJson(String path) throws Exception,FileNotFoundException,RuntimeException {
        Map<String,Map<String,List<String>>> doc=jsonIEGestor.importar(path);
        for (String autor: doc.keySet()) {
            for (String titol : doc.get(autor).keySet()) {
                ctrlDocument_.nouDocument(autor,titol,doc.get(autor).get(titol));
            }
        }
    }

    /**
     * Carregar el document en format txt
     * @param path ruta on esta el document
     * @throws IOException
     */
    public void carregarDocumentTxt(String path) throws Exception,FileNotFoundException {
        Map<String, Map<String, List<String>>> doc=txtIEGestor.importar(path);
        for (String autor: doc.keySet()) {
            for (String titol : doc.get(autor).keySet()) {
                crearDocument(autor,titol,doc.get(autor).get(titol));
            }
        }
    }

    /**
     * Carregar el document en format xml
     * @param path ruta on esta el document
     * @throws IOException
     */
    public void carregarDocumentXml(String path) throws Exception,FileNotFoundException,RuntimeException {
        Map<String,Map<String,List<String>>> doc=xmlIEGestor.importar(path);
        for (String autor: doc.keySet()) {
            for (String titol : doc.get(autor).keySet()) {
                ctrlDocument_.nouDocument(autor,titol,doc.get(autor).get(titol));
            }
        }
    }

    /**
     * Crea un objecte document
     * @param titol nom del titol del document
     * @param autor nom del autor del document
     * @param contingut contingut del document
     * @throws Exception si ja existia un document amb mateix autor i titol
     */
    public void crearDocument(String titol,String autor, List<String> contingut) throws Exception {
        ctrlDocument_.nouDocument(autor, titol, contingut);
    }

    /**
     * Modifica el titol de un document existent
     * @param titolNou nou nom del document
     * @param titolVell antic nom del document
     * @param autor autor del document
     * @throws Exception si no existeix un document amb autor i titolVell
     */
    public void modificarTitol(String titolNou, String titolVell, String autor) throws Exception {
        ctrlDocument_.ModificarTitol(titolNou, titolVell, autor);
    }

    /**
     * Modifica l'autor d'un document existent
     * @param titol Titol del document
     * @param autorNou nou autor del Document
     * @param autorVell autor antic del Document
     * @throws Exception si no existeix el Document
     */
    public void modificarAutor(String titol, String autorNou, String autorVell) throws Exception {
        ctrlDocument_.ModificarAutor(autorNou, autorVell, titol);
    }

    /**
     * Modifica el contingut d'un document existent
     * @param titol Titol del Document
     * @param autor Autor del Document
     * @param contingut Contingut nou del Document
     * @throws Exception si no existeix el Document
     */
    public void modificarContingut(String titol, String autor, List<String> contingut) throws Exception {
        ctrlDocument_.ModificarContingut(titol,autor, contingut);
    }

    /**
     * Donats un titol i un autor, elimina del sistema el Document identificat per aquests paràmetres
     * @param titol titol del document a eliminar
     * @param autor autor del document a eliminar
     * @throws Exception
     */
    public void eliminarDocument(String titol, String autor) throws Exception {
        ctrlDocument_.eliminarDocument(titol,autor);
    }

    /**
     * Obte tots els titols de l'autor identificat pel parametre autor
     * @param autor autor que genera la consulta
     * @return Set que conte els titols
     * @throws FileNotFoundException
     */
    public Set<String> consultaTitolsAutor(String autor) throws FileNotFoundException {
        return ctrlConsulta_.Consultatitolsautor(autor);
    }

    /**
     * Obte tots els autors a partir del prefix indicat
     * @param prefix prefix que genera la consulta
     * @return set que conte els noms dels autors
     * @throws FileNotFoundException
     */
    public Set<String> consultaPrefixAutor(String prefix) throws FileNotFoundException {
        return ctrlConsulta_.ConsultaPrefixAutor(prefix);
    }

    /**
     * Obte el contingut del document identificat pels parametres titol i autor
     * @param titol titol del document
     * @param autor autor del document
     * @return retorna el contingut del document
     */
    public List<String> consultaContingutDocument(String titol, String autor) throws FileNotFoundException {
        return ctrlConsulta_.ConsultaContingutDocument(titol,autor);
    }

    /**
     * Retorna els k documents més semblants al document identificat pels paràmetres títol i autor
     * @param titol titol del document a comparar
     * @param autor autor del document a comparar
     * @param k limit per la consulta
     * @return Documents mes semblants, agrupats per autor
     * @throws FileNotFoundException
     */
    public Map<String,Set<String>> consultaKDocumentsSemblants(String titol, String autor, int k) throws FileNotFoundException {
        return ctrlConsulta_.ConsultaKdocumentSemblant(titol, autor, k);
    }

    /**
     * Obte els K documents mes rellevants en termes de contingut respecte al parametre query
     * @param query parametre per comparar amb els documents
     * @param k limit de la consulta
     * @return Llistat de documents que cumpleixen la consulta
     * @throws FileNotFoundException
     */
    public Map<String,Set<String>> consultaKDocumentsRellevants(List<String> query, int k) throws FileNotFoundException {
        return ctrlConsulta_.ConsultaKdocumentRellevant(query,k);
    }

    /**
     * Obte els documents que cumpleixen l'expressio booleana indicada pel parametre expressio
     * @param expressio expressio a comprovar
     * @return Documents que cumpleixen l'expressio
     */
    public Map<String,Set<String>> consultaBooleana(String expressio) throws FileNotFoundException, ExprMalFormadaException {
        return ctrlConsulta_.ConsultaExpressioBooleana(expressio);
    }

    /**
     *  Executa l’ordenacio de la consulta identificada pel parametre idConsulta,
     *  pel criteri identificat pel parametre criteri, que pot ser “Alfabeticament” o “Inversament”.
     * @param idconsulta identificador de la consulta a ordenar
     * @param criteri criteri d'ordenacio
     */
    public void ordenarConsulta(int idconsulta,String criteri) {
        ctrlConsulta_.ordenarConsulta(idconsulta, criteri);
    }

    /**
     * Executa l’operacio entre dues consultes, identificades per id1 i id2,
     * pel criteri identificat pel parametre op, que pot ser Unio, Interseccio, Diferencia i Diferencia Simetrica.
     * @param id1 identificador de consulta 1 a operar
     * @param id2 identificador de consulta 2 a operar
     * @param op criteri d'operacio
     */
    public Integer operarConsulta(int id1, int id2, String op) {
        return ctrlConsulta_.OperarResultat(id1, id2, op);
    }

    /**
     *  Executa de l’alta d’expressions booleanes al sistema.
     * @param expressio expressio a donar d'alta
     * @throws ExprMalFormadaException si l'expressio esta mal formada
     */
    public void afegirExpressioBooleana(String expressio) throws ExprMalFormadaException{
        ctrlExprBooleanes_.addExprBooleana(expressio);
    }

    /**
     * Executa la baixa del sistema de l’expressio booleana identificada pel parametre id.
     * @param id identificador de l'expressio
     */
    public void borrarExpressioBooleana(int id) {
        ctrlExprBooleanes_.esborraExprBooleana(id);
    }

    /**
     * executa la modificacio de l’expressio booleana identificada pel parametre id.
     * @param id identificador de l'expressio
     * @param NovaExpressio expressio que subtitueix l'antiga
     * @throws ExprMalFormadaException si NovaExpressio esta mal formada
     */
    public void modificarExprBooleana(int id, String NovaExpressio) throws ExprMalFormadaException{
        //ctrlExprBooleanes_.esborraExprBooleana(id);
        ctrlExprBooleanes_.modificarExprBooleana(NovaExpressio,id);
    }

    /**
     * getter de TitolsAutor
     * @return TitolsAutor
     */
    public Map<String, Map<String, Integer>> getTitolsAutor() {
        return ctrlDocument_.getTitolsAutor();
    }

    /**
     * getter de l'estructura de resultats de consultes del tipus ResultatConsultaNoms
     * @return estructura de resultats de consultes del tipus ResultatConsultaNoms
     */
    public Map<Integer, ResultatConsultaNoms> getResultatConsultaNoms() {
        return ctrlConsulta_.getResultatsConsultaNoms();
    }
    /**
     * getter de l'estructura de resultats de consultes del tipus ResultatConsultaPairs
     * @return estructura de resultats de consultes del tipus ResultatConsultaPairs
     */
    public Map<Integer, ResultatConsultaPairs> getResultatConsultaPairs() {
        return ctrlConsulta_.getResultatsConsultaPairs();
    }
    /**
     * getter de l'estructura de resultats de consultes del tipus ResultatConsultaContingut
     * @return estructura de resultats de consultes del tipus ResultatConsultaContingut
     */
    public Map<Integer, ResultatContingut> getResultatContingut() {
        return ctrlConsulta_.getResultatsConsultaContingut();
    }

    /**
     * Getter dels identificadors dels documents, amb la seva instancia
     * @return Map amb els identificadors dels documents i la seva instancia
     */
    public Map<Integer, Document> getidsDocuments() {
        return ctrlDocument_.getIdentificadors();
    }

    /**
     * getter del id d'una consulta
     * @return idconsulta
     */
    public int getConsultaid() {
        return ctrlConsulta_.getId();
    }

    /**
     * Getter del controlador de documents
     * @return El controlador de Documents
     */
    public CtrlDocument getCtrlDoc() {
        return ctrlDocument_;
    }

    /**
     * Getter de totes les expresions booleanes
     * @return Mapa d'identificadors i Expresions Booleanes
     */
    public Map<Integer, ExprBooleana> getExpressions(){
        return ctrlExprBooleanes_.getExpressions();
    }
}
