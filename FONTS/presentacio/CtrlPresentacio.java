package presentacio;
import domini.Clases.*;
import domini.controladors.CtrlDomini;
import domini.exceptions.ExprMalFormadaException;
import persistencia.ImportarExportarTxt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *  El controlador de presentació s’encarrega de fer de comunicador entre vistes de la capa de presentació.
 *  També és el que transmet a la capa de presentació les dades de les capes inferiors
 * @author Zheshuo Lin
 */
public class CtrlPresentacio {
    /**
     * Instància del controlador de domini.
     */
    private static final CtrlDomini cd;

    static {
        try {
            cd = new CtrlDomini();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * mostrar la finestra de menú principal
     */
    public static void iniPresentacio() {
        vistaMenuPrincipal menu= new vistaMenuPrincipal();
    }

    /**
     * mostrar la finestra de menú del gestor de documents
     */
    public static void vistaGestorDocument () {
        vistaGestorDocument vGD=new vistaGestorDocument();
    }
    /**
     * mostrar la finestra de menú del gestor de expressions booleana
     */
    public static void vistaGestorExpr() {
        vistaGestorExpr vGE=new vistaGestorExpr();
    }
    /**
     * mostrar la finestra de menú del gestor de consultes
     */
    public static void vistaGestorConsulta() {
        vistaGestorConsulta vGE=new vistaGestorConsulta();
    }

    /**
     * mostrar la finestra per  crear document
     */
    public static void vistaNouDoc () {
        vistaNouDoc vND=new vistaNouDoc();
    }

    /**
     * mostrar la finestra per  modificar document
     */

    public static void vistaModificarDoc() {
        vistaModificarDoc vMD=new vistaModificarDoc();
    }

    /**
     * mostrar la finestra per  eliminar document
     */

    public static void vistaEliminarDoc() {
        vistaEliminarDoc vED=new vistaEliminarDoc();
    }
    /**
     * mostrar la finestra per  guardar document
     */

    public static void vistaGuardarDoc() {
        vistaGuardarDoc vED=new vistaGuardarDoc();
    }

    /**
     *  mostrar la finestra per carregar document
     */
    public static void vistaCarregarDoc() {
        vistaCarregarDoc vED=new vistaCarregarDoc();
    }

    /**
     * mostrar la finestra per crear expressio booleana
     */
    public static void vistaAltaExpressio() {
        vistaAltaExpressio vAE=new vistaAltaExpressio();
    }

    /**
     * mostrar la finestra per eliminar expressio booleana
     */

    public static void vistaEliminarExpr() {
        vistaEliminarExpr vEE=new vistaEliminarExpr();
    }

    /**
     * mostrar la finestra per modificar expressio booleana
     */
    public static void vistaModificarExpr() {
        vistaModificarExpr vME=new vistaModificarExpr();
    }

    /**
     * mostrar la finestra per realitzar una consulta
     */

    public static void vistaNovaConsulta() {
        vistaNovaConsulta vNC=new vistaNovaConsulta();
    }

    /**
     * mostrar la finestra per ordenar una consulta
     */


    public static void vistaOrdenarConsulta() {
        vistaOrdenarConsulta vOC=new vistaOrdenarConsulta();
    }
    /**
     * mostrar la finestra per realitzar una operacio sobre consultes
     */


    public static void vistaOperarConsulta() {
        vistaOperarConsulta vOpC = new vistaOperarConsulta();
    }

    /**
     * Getter de titols i autors dels documents
     * @return titols i autors dels documents
     */
    public static Map<String, Map<String,Integer>> getTitolsAutors() {
        return cd.getTitolsAutor();
    }
    /**
     * Getter de identificadors interns dels documents
     * @return identificadors interns dels documents
     */
    
    public static Map<Integer, Document> getIdentificadors() {
        return cd.getidsDocuments();
    }

    /**
     * Getter de les expressions booleanes
     * @return  les expressions booleanes
     */

    public static Map<Integer, ExprBooleana> getExpressions() {
        return cd.getExpressions();
    }

    /**
     * Getter per obtenir tots ResultatConsultaPairs
     * @return tots ResultatConsultaPairs
     */
    public static Map<Integer, ResultatConsultaPairs> getResultatPair() {
        return cd.getResultatConsultaPairs();
    }

    /**
     * Getter per obtenir tots ResultatConsultaNoms
     * @return tots ResultatConsultaNoms
     */
    public static Map<Integer, ResultatConsultaNoms> getResultatNoms() {
        return cd.getResultatConsultaNoms();
    }
    /**
     * Getter per obtenir tots ResultatContingut
     * @return tots ResultatContingut
     */

    public static Map<Integer, ResultatContingut> getResultatContingut() {
        return cd.getResultatContingut();
    }

    /**
     * Crida creaDocument del controlador domini
     * @param titol titol del document
     * @param autor autor del document
     * @param contingut contingut del document
     * @throws Exception document ja existit
     */
    public static void creaDocument(String titol, String autor, List<String> contingut) throws Exception {
        cd.crearDocument(titol,autor,contingut);

    }


    /**
     * Crida modificarContingut del controlador domini
     * @param autor autor del document
     * @param titol titol del document
     * @param nouContingut nou contingut
     */
    public static void modificaContingut(String autor,String titol, List<String> nouContingut) throws Exception {
        cd.modificarContingut(titol,autor,nouContingut);
    }

    /**
     * Crida modificarAutor del controlador domini
     * @param autor autor original del document
     * @param titol titol del document
     * @param nouAutor nou autor

     */
    public static void modificaAutor(String autor,String titol, String nouAutor) throws Exception {
        cd.modificarAutor(titol,nouAutor,autor);
    }

    /**
     * Crida modificarTitol del controlador domini
     * @param autor autor del document
     * @param titol titol original del document
     * @param nouTitol nou titol
     */
    public static void modificaTitol(String autor,String titol, String nouTitol) throws Exception {
        cd.modificarTitol(nouTitol,titol,autor);
    }

    /**
     * Crida eliminarDocument del controlador domini
     * @param titol titol del document
     * @param autor autor del document
     */
    public  static void eliminarDocument(String titol, String autor) throws Exception {
        cd.eliminarDocument(titol,autor);
    }



    /**
     * Crida carregarDocumentTxt del controlador domini
     * @param path path del document que vol carregar
     */
    public static void carregarDocumentTxt(String path) throws Exception,FileNotFoundException {
        cd.carregarDocumentTxt(path);
    }
    /**
     * Crida carregarDocumentXml del controlador domini
     * @param path path del document que vol carregar
     */
    public static void carregarDocumentXml(String path) throws Exception,RuntimeException,FileNotFoundException {
        cd.carregarDocumentXml(path);
    }



    /**
     * Crida carregarDocumentJson del controlador domini
     * @param path path del fitxer que vol carregar
     */

    public static void carregarDocumentJson(String path) throws Exception,FileNotFoundException,RuntimeException {
        cd.carregarDocumentJson(path);
    }

    /**
     * Crida guardarDocumentsTxt del controlador domini
     * @param docs documents que vol guardar
     * @param path el path on guarda el documents
     */

    public static void guardarDocumentsTxt(Map<String,Map<String, List<String>>>  docs , String path) throws IOException {
        cd.guardarDocumentTxt(docs,path);

    }

    /**
     * Crida guardarDocumentsXml del controlador domini
     *
     * @param docs documents que vol guardar
     * @param path el path on guarda el documents
     * @param junt per indicar guarda documents junt en un fitxer o separats
     */

    public static void guardarDocumentXml(Map<String, Map<String, List<String>>> docs , String path, boolean junt) throws IOException {
        cd.guardarDocumentXml(docs,path,junt);
    }
    /**
     * Crida guardarDocumentsJson del controlador domini
     *
     * @param docs documents que vol guardar
     * @param path el path on guarda el documents
     */
    public static void guardarDocumentJson(Map<String, Map<String, List<String>>> docs , String path) throws IOException {
        cd.guardarDocumentJson(docs,path);
    }
    /**
     * Crida afegirExpressioBooleana del controlador domini
     * @param expr la expressio booleana que vol crear
     * @throws ExprMalFormadaException expressio booleana mal formada
     */
    public static void altaExpressioBooleana(String expr) throws ExprMalFormadaException {
        cd.afegirExpressioBooleana(expr);
    }

    /**
     * Crida borrarExpressioBooleana del controlador domini
     * @param identificador identificador de la expressio booleana que vol eliminar
     */
    public static void eliminarExpressioBooleana(int identificador) {
        cd.borrarExpressioBooleana(identificador);
    }

    /**
     * Crida modificarExprBooleana del controlador domini
     * @param identificador identificador de la expressio booleana que vol modificar
     * @param novaExpr nova expressio
     */

    public static void modificarExpressioBooleana(int identificador, String novaExpr) throws ExprMalFormadaException {
        cd.modificarExprBooleana(identificador,novaExpr);
    }

    /**
     * Crida consultaTitolsAutor del controlador domini
     * @param autor autor que genera la consulta
     * @return Set que conte els titols
     */
    public static Set<String> consultaTitolsAutor(String autor) throws FileNotFoundException {
        return cd.consultaTitolsAutor(autor);
    }

    /**
     * Crida consultaPrefixAutor del controlador domini
     * @param prefix prefix que genera la consulta
     * @return set que conte els noms dels autors
     */

    public static Set<String> consultaPrefixAutors(String prefix) throws FileNotFoundException {
        return cd.consultaPrefixAutor(prefix);
    }

    /**
     * Crida consultaContingutDocument del controlador domini
     * @param titol titol del document
     * @param autor autor del document
     * @return retorna el contingut del document
     */
    public static List<String> consultaContingut(String autor,String titol) throws FileNotFoundException {
        return cd.consultaContingutDocument(titol,autor);
    }

    /**
     * Crida consultaBooleana del controlador domini
     * @param expr expressio a comprovar
     * @return Documents que cumpleixen l'expressio
     */

    public static Map<String, Set<String>> consultaBooleana(String expr) throws ExprMalFormadaException, FileNotFoundException {
        return cd.consultaBooleana(expr);
    }

    /**
     * Crida consultaKmesSemblant del controlador domini
     * @param titol titol del document a comparar
     * @param autor autor del document a comparar
     * @param k limit per la consulta
     * @return Documents mes semblants, agrupats per autor
     */
    public static Map<String,Set<String>> consultaKmesSemblant(String autor,String titol, int k) throws FileNotFoundException,RuntimeException {
        return cd.consultaKDocumentsSemblants(titol,autor,k);
    }

    /**
     * Criada consultaKDocumentsRellevants del controlador domini
     * @param query parametre per comparar amb els documents
     * @param k limit de la consulta
     * @return Llistat de documents que cumpleixen la consulta
     */
    public static Map<String,Set<String>> consultaKmesRellevant(List<String> query, int k) throws FileNotFoundException,RuntimeException {
        return cd.consultaKDocumentsRellevants(query,k);
    }


    /**
     * Crida operarConsulta del controlador domini
     * @param id1 identificador de consulta 1 a operar
     * @param id2 identificador de consulta 2 a operar
     * @param op criteri d'operacio
     */
    public static Integer OperarConsulta(int id1, int id2, String op) {
        return cd.operarConsulta(id1,id2,op);
    }

    /**
     * Crida ordenarConsulta del controlador domini
     * @param id identificador de la consulta a ordenar
     * @param criteri criteri d'ordenacio
     */
    public static void ordenarConsulta(int id,String criteri) {
        cd.ordenarConsulta(id,criteri);
    }

    /**
     * Crida el getter de ResultatConsultaNoms del controlador de domini
     * @return el mapa amb el id de la consulta i la instància del resultat
     */
    public static Map<Integer,ResultatConsultaNoms> getResultatConsultaNoms() {
        return cd.getResultatConsultaNoms();
    }

    /**
     * Crida el getter de ResultatConsultaPairs del controlador de domini
     * @return el mapa amb el id de la consulta i la instància del resultat
     */
    public static Map<Integer,ResultatConsultaPairs> getResultatConsultaPairs() {
        return cd.getResultatConsultaPairs();
    }
    /**
     * Crida guardarConfig del controlador domini
     */
    public static void guardarConfig() throws IOException, ClassNotFoundException {
        cd.guardarEstat();;
    }

    /**
     * Crida carregarConfig del controlador domini
     */

    public static void carregarConfig() throws IOException, ClassNotFoundException {
        cd.carregarEstat();
    }



}
