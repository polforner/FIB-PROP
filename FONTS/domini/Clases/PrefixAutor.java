package domini.Clases;
import domini.controladors.CtrlDocument;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Classe que defineix la consulta que retorna els autors que els seus noms comencen per un prefix.
 * La consulta reb un prefix i retorna noms que comencen per aquest prefix
 * @author Zheshuo.Lin
 */
public class PrefixAutor implements Consulta {
    private  final String prefix;


    /**
     * Creacio d'una consulta PrefixAutor amb el prefix i CtrlDocument
     * @param prefix el prefix
     */
    public PrefixAutor( String prefix)  {
        this.prefix=prefix;

    }
    /** Crea una instancia de ResultatConsulta amb els noms dels autors que contenen prefix
     * @return retorna la instancia de ResultatConsulta creada en executar-se la consulta
     */
    public ResultatConsulta executar() throws FileNotFoundException {
        CtrlDocument cd =CtrlDocument.getInstance();
        Trie autors=cd.getPrefixosAutors();
        Set<String>  resultat= autors.buscarParaules(this.prefix);
        List<String> parametres=new ArrayList<>();
        parametres.add(prefix);
        return new ResultatConsultaNoms(resultat,"PrefixAutor",parametres);
    }
    public String getPrefix() {
        return this.prefix;
    }
}

