package domini.controladors;


import domini.Clases.*;
import domini.exceptions.ExprMalFormadaException;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

/**
 * Controlador de la consulta
 * @author Zheshuo.Lin
 */
public class ControladorConsulta implements Serializable{

    Map<Integer,ResultatConsultaPairs> ResultatsConsultaPairs;
    Map<Integer,ResultatConsultaNoms> ResultatsConsultaNoms;
    Map<Integer,ResultatContingut> ResultatsContingut;
    private int id_;

    /**
     * Crea una instancia de Controlador de Consulta i Inicialtza els atributs.
     */
    public ControladorConsulta() {
        ResultatsConsultaNoms=new HashMap<>();
        ResultatsConsultaPairs=new HashMap<>();
        ResultatsContingut=new HashMap<>();
        id_=1;

    }

    /**
     * Donat un document identificat per titol i autor retornem k documents mes semblants a aquest document
     * @param titol titol del document
     * @param autor autor del document
     * @param k nombre de documents que retornem
     * @return un conjunt de pair (titol,autork) dels documents mes semblants a document identificat per titol i autor
     */
    public Map<String,Set<String>>ConsultaKdocumentSemblant(String titol,String autor,int k) throws FileNotFoundException {
        KmesSemblant consulta=new KmesSemblant(titol,autor,k);
        ResultatConsulta res=consulta.executar();
        ResultatConsultaPairs result=(ResultatConsultaPairs) res;
        this.ResultatsConsultaPairs.put(id_,result);
        ++id_;
        return result.getResultat();

    }

    /**
     * Donat una expressió booleana i retornan els titols i autors dels documents que compleixen aquesta expressió.
     * @param Expression la expressió booleana que els documents retornats han de complir.
     * @return els titols i autors dels documents que compleixen la expressió.
     */
    public Map<String,Set<String>> ConsultaExpressioBooleana(String Expression) throws FileNotFoundException,ExprMalFormadaException {
        ExprBooleana expr=new ExprBooleana(Expression);
        Booleana consulta=new Booleana(expr);
        ResultatConsulta res = consulta.executar();
        ResultatConsultaPairs result=(ResultatConsultaPairs)  res;
        this.ResultatsConsultaPairs.put(id_,result);
        ++id_;
        return result.getResultat();
    }

    /**
     * Donat el titol i autor del document, retorna el seu contingut
     * @param titol el titol del document
     * @param autor el autor del document
     * @return el contingut del document
     */

    public List<String> ConsultaContingutDocument(String titol,String autor) throws FileNotFoundException {
        ContingutDocument consulta=new ContingutDocument(titol,autor);
        ResultatConsulta res= consulta.executar();
        ResultatContingut result=(ResultatContingut) res;
        ResultatsContingut.put(id_,result);
        id_++;
        return result.getResultat();
    }

    /**
     * Donat una llista de paraules i un nombre k retorna k documents més rellevants amb llista de paraules
     * @param query llista de paraules
     * @param k  nombre de  documents que retornem
     * @return k documents més rellevants amb llista de paraules. Si la llista de paraules sigui irrellevant amb paraula
     * és possible que menys que k document.
     */

    public Map<String, Set<String>> ConsultaKdocumentRellevant(List<String> query, int k) throws FileNotFoundException {
       KmesRellevant consulta=new KmesRellevant(k,query);
       ResultatConsulta res= consulta.executar();
       ResultatConsultaPairs result=(ResultatConsultaPairs)  res;
        this.ResultatsConsultaPairs.put(id_,result);
        ++id_;
       return result.getResultat();
    }

    /**
     * Consulta els autors per un prefix
     * @param prefix: El prefix que vol totes resultats contenen
     * @return  una instancia de ResultatConsulta amb els noms dels autors que contenen prefix
     */
    public Set<String> ConsultaPrefixAutor(String prefix ) throws FileNotFoundException {
        PrefixAutor consulta=new PrefixAutor( prefix);
        ResultatConsulta res=consulta.executar();
        ResultatConsultaNoms result= (ResultatConsultaNoms) res;
        ResultatsConsultaNoms.put(id_,result);
        id_++;
        Set<String> resultat=result.getResultat();
        return resultat;
    }

    /**
     * Consulta els titols d'un autor
     * @param autor Nom de l'autor
     * @return  una instancia de ResultatConsulta amb els titols d'aquest autor.
     */
    public Set<String> Consultatitolsautor(String autor  ) throws FileNotFoundException {

        TitolsAutor consulta=new TitolsAutor(autor);
        ResultatConsulta res= consulta.executar();
        ResultatConsultaNoms result=(ResultatConsultaNoms) res;
        Set<String> resultat=result.getResultat();
        ResultatsConsultaNoms.put(id_,result);
        id_++;
        return resultat;
    }

    /**
     * ordena el resultat segons criteri donat per usuari i mostra per la pantalla el resultat ordenat
     * @param criteri: criteri donat per usuari
     * @param id: identificador del resultat
     */

    public void ordenarConsulta(int id,String criteri) {
        switch (criteri) {
            case "Alfabeticament":

                if (ResultatsConsultaNoms.containsKey(id)) {
                    ResultatsConsultaNoms.get(id).ordenarAlfabeticament();
                }
                else if (ResultatsConsultaPairs.containsKey(id)) {
                    ResultatsConsultaPairs.get(id).ordenarAlfabeticament();
                }
                break;
            case "Inversament":
                if (ResultatsConsultaNoms.containsKey(id)) {
                    ResultatsConsultaNoms.get(id).ordenarInversament();
                }
                else if (ResultatsConsultaPairs.containsKey(id)) {
                    ResultatsConsultaPairs.get(id).ordenarInversament();
                }

                break;

        }
    }

    /**
     * Donat dos ids dels resultats i una operacio, fara operacio indicada.
     * @param id1 identificador de primer resultat
     * @param id2 identificador de segon resultat
     * @param op la operacio que hauria de fer
     */
    public Integer OperarResultat(int id1, int id2, String op) {
        switch (op) {
            case "Interseccio":

                if (ResultatsConsultaNoms.containsKey(id1) && ResultatsConsultaNoms.containsKey(id2)) {
                    Set<String> resultatOperacio=new HashSet<>();
                    OperacionResultatNoms opera=new OperacionResultatNoms(ResultatsConsultaNoms.get(id1).getResultat(),ResultatsConsultaNoms.get(id2).getResultat());
                    resultatOperacio =opera.interseccio();
                    List<String> parametre=Arrays.asList(String.valueOf(id1),String.valueOf(id2));
                    if (ResultatsConsultaNoms.get(id1).getTipusConsulta() == "TitolsAutor") {
                        ResultatConsultaNoms res=new ResultatConsultaNoms(resultatOperacio,"Interseccio-TitolsAutor",parametre);
                        ResultatsConsultaNoms.put(id_,res);
                    }
                    else {
                        ResultatConsultaNoms res= new ResultatConsultaNoms(resultatOperacio,"Interseccio-PrefixosAutor",parametre);
                        ResultatsConsultaNoms.put(id_,res);
                    }
                    id_++;
                }

                else if (ResultatsConsultaPairs.containsKey(id1) && ResultatsConsultaPairs.containsKey(id2)) {
                    Map<String,Set<String>> resultatOperacio=new HashMap<>();
                    OperacionResultatPair opera=new OperacionResultatPair(ResultatsConsultaPairs.get(id1).getResultat(),ResultatsConsultaPairs.get(id2).getResultat());
                    resultatOperacio =opera.interseccio();
                    List<String> parametre=Arrays.asList(String.valueOf(id1),String.valueOf(id2));
                    ResultatConsultaPairs res=new ResultatConsultaPairs(resultatOperacio,"Interseccio",parametre);
                    ResultatsConsultaPairs.put(id_,res);
                    id_++;
                }
                break;
            case "Unio":


               if (ResultatsConsultaNoms.containsKey(id1) && ResultatsConsultaNoms.containsKey(id2)) {
                    Set<String> resultatOperacio=new HashSet<>();
                    OperacionResultatNoms opera=new OperacionResultatNoms(ResultatsConsultaNoms.get(id1).getResultat(),ResultatsConsultaNoms.get(id2).getResultat());
                    resultatOperacio =opera.unio();
                    List<String> parametre=Arrays.asList(String.valueOf(id1),String.valueOf(id2));
                   if (ResultatsConsultaNoms.get(id1).getTipusConsulta() == "TitolsAutor") {
                       ResultatConsultaNoms res = new ResultatConsultaNoms(resultatOperacio, "Unio-TitolsAutor", parametre);
                       ResultatsConsultaNoms.put(id_, res);
                   }
                   else {
                       ResultatConsultaNoms res = new ResultatConsultaNoms(resultatOperacio, "Unio-PrefixosAutor", parametre);
                       ResultatsConsultaNoms.put(id_, res);
                   }
                    id_++;
                }

                else if (ResultatsConsultaPairs.containsKey(id1) && ResultatsConsultaPairs.containsKey(id2)) {
                    Map<String,Set<String>> resultatOperacio=new HashMap<>();
                    OperacionResultatPair opera=new OperacionResultatPair(ResultatsConsultaPairs.get(id1).getResultat(),ResultatsConsultaPairs.get(id2).getResultat());
                    resultatOperacio =opera.unio();
                    List<String> parametre=Arrays.asList(String.valueOf(id1),String.valueOf(id2));
                    ResultatConsultaPairs res=new ResultatConsultaPairs(resultatOperacio,"Unio",parametre);
                    ResultatsConsultaPairs.put(id_,res);
                    id_++;
                }

                break;
            case "Diferencia":


                if (ResultatsConsultaNoms.containsKey(id1) && ResultatsConsultaNoms.containsKey(id2)) {
                    Set<String> resultatOperacio=new HashSet<>();
                    OperacionResultatNoms opera=new OperacionResultatNoms(ResultatsConsultaNoms.get(id1).getResultat(),ResultatsConsultaNoms.get(id2).getResultat());
                    resultatOperacio =opera.diferencia();
                    List<String> parametre=Arrays.asList(String.valueOf(id1),String.valueOf(id2));
                    if (ResultatsConsultaNoms.get(id1).getTipusConsulta() == "TitolsAutor") {
                        ResultatConsultaNoms res = new ResultatConsultaNoms(resultatOperacio, "Diferencia-TitolsAutor", parametre);
                        ResultatsConsultaNoms.put(id_, res);
                    }
                    else {
                        ResultatConsultaNoms res = new ResultatConsultaNoms(resultatOperacio, "Diferencia-PrefixosAutor", parametre);
                        ResultatsConsultaNoms.put(id_, res);
                    }
                    id_++;
                }

                else if (ResultatsConsultaPairs.containsKey(id1) && ResultatsConsultaPairs.containsKey(id2)) {
                    Map<String,Set<String>> resultatOperacio=new HashMap<>();
                    OperacionResultatPair opera=new OperacionResultatPair(ResultatsConsultaPairs.get(id1).getResultat(),ResultatsConsultaPairs.get(id2).getResultat());
                    resultatOperacio =opera.diferencia();
                    List<String> parametre=Arrays.asList(String.valueOf(id1),String.valueOf(id2));
                    ResultatConsultaPairs res=new ResultatConsultaPairs(resultatOperacio,"Diferencia",parametre);
                    ResultatsConsultaPairs.put(id_,res);
                    id_++;
                }
                else {
                }
                break;
            case "DiferenciaSimetrica":
                if (ResultatsContingut.containsKey(id1) || ResultatsContingut.containsKey(id2)) {
                }
                else if ((ResultatsConsultaNoms.containsKey(id1) && ResultatsConsultaPairs.containsKey(id2)) ||(ResultatsConsultaNoms.containsKey(id2) && ResultatsConsultaPairs.containsKey(id1))){
                }
                else if (ResultatsConsultaNoms.containsKey(id1) && ResultatsConsultaNoms.containsKey(id2)) {
                    Set<String> resultatOperacio=new HashSet<>();
                    OperacionResultatNoms opera=new OperacionResultatNoms(ResultatsConsultaNoms.get(id1).getResultat(),ResultatsConsultaNoms.get(id2).getResultat());
                    resultatOperacio =opera.diferenciaSimetrica();
                    List<String> parametre=Arrays.asList(String.valueOf(id1),String.valueOf(id2));
                    if (ResultatsConsultaNoms.get(id1).getTipusConsulta() == "TitolsAutor") {
                        ResultatConsultaNoms res = new ResultatConsultaNoms(resultatOperacio, "DiferenciaSimetrica-TitolsAutor", parametre);
                        ResultatsConsultaNoms.put(id_, res);
                    }
                    else {
                        ResultatConsultaNoms res = new ResultatConsultaNoms(resultatOperacio, "DiferenciaSimetrica-PrefixosAutor", parametre);
                        ResultatsConsultaNoms.put(id_, res);
                    }
                    id_++;
                }

                else if (ResultatsConsultaPairs.containsKey(id1) && ResultatsConsultaPairs.containsKey(id2)) {
                    Map<String,Set<String>> resultatOperacio=new HashMap<>();
                    OperacionResultatPair opera=new OperacionResultatPair(ResultatsConsultaPairs.get(id1).getResultat(),ResultatsConsultaPairs.get(id2).getResultat());
                    resultatOperacio =opera.diferenciaSimetrica();
                    List<String> parametre=Arrays.asList(String.valueOf(id1),String.valueOf(id2));
                    ResultatConsultaPairs res=new ResultatConsultaPairs(resultatOperacio,"DiferenciaSimetrica",parametre);

                    ResultatsConsultaPairs.put(id_,res);
                    id_++;
                }
                else {
                }
                break;

        }
        return id_ - 1;
    }





    public int getId() {
        return id_;
    }

    public Map<Integer, ResultatConsultaNoms> getResultatsConsultaNoms() {
        return ResultatsConsultaNoms;
    }

    public Map<Integer,ResultatConsultaPairs> getResultatsConsultaPairs() {
        return ResultatsConsultaPairs;
    }

    public Map<Integer,ResultatContingut> getResultatsConsultaContingut() {
        return ResultatsContingut;
    }


}
