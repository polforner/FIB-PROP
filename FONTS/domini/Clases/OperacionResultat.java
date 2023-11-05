package domini.Clases;

/**
 * Interficie per operar amb resultats de consultes
 */
public interface OperacionResultat<T> {
    /**
     * Operacio per intersecar 2 resultats
     * @return
     */
    public T interseccio();
    /**
     * Operacio per unir 2 resultats
     * @return
     */
    public T unio();
    /**
     * Operacio per fer la diferencia entre el primer i el segon resultat
     * @return
     */
    public T diferencia();
    /**
     * Operacio per fer la diferencia simetrica entre 2 resultats
     * @return
     */
    public T diferenciaSimetrica();
}