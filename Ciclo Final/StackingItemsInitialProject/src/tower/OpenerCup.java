package tower;

/**
 * OpenerCup - Taza que elimina todas las tapas que le impiden el paso al entrar.
 */
public class OpenerCup extends Cup {
    /**
     * Crea una taza de tipo Opener.
     * @param number numero de la taza
     * @param towerWidthCm ancho de la torre
     */
    public OpenerCup(int number, int towerWidthCm) {
        super(number, towerWidthCm);
        this.color = "cyan";
    }

    /**
     * Elimina todas las tapas de la torre y luego se agrega al final.
     * Usa los metodos controlados de Tower para evitar acceso directo a la lista interna.
     * @param t Referencia a la torre
     */
    @Override
    public void enterTower(Tower t) {
        t.removeAllLids();
        t.addItem(this);
    }
}