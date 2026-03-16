/**
 * StackableItem - Clase abstracta que representa un elemento apilable en la torre.
 * Garantiza que solo objetos de tipo Cup o Lid puedan ser agregados a la torre,
 * evitando que elementos de otros tipos sean insertados en la lista de items.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public abstract class StackableItem {

    /**
     * Retorna el numero identificador del elemento.
     */
    public abstract int getNumber();

    /**
     * Retorna la altura del elemento en cm.
     */
    public abstract int getHeightCm();

    /**
     * Retorna el color del elemento.
     */
    public abstract String getColor();

    /**
     * Retorna el tipo del elemento en minusculas ("cup" o "lid").
     */
    public abstract String getType();

    /**
     * Dibuja el elemento en la posicion indicada.
     * @param xPx posicion x absoluta en pixeles
     * @param yPx posicion y absoluta en pixeles
     */
    public abstract void drawAt(int xPx, int yPx);

    /**
     * Hace invisible el elemento.
     */
    public abstract void makeInvisible();
}