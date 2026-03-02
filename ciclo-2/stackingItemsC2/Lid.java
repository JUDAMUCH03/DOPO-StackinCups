/**
 * Lid - Representa una tapa en el simulador de apilamiento.
 * La tapa mide siempre 1 cm de alto y tiene el mismo color que su taza.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class Lid {
    private static final int SCALE = 20;
    private static final int HEIGHT_CM = 1;

    private int number;
    private String color;
    private int cupWidthPx;
    private boolean visible;

    private Rectangle body;

    /**
     * Crea una tapa asociada a la taza del numero dado.
     * @param number numero de la tapa (mismo que su taza)
     * @param cup la taza a la que pertenece esta tapa
     */
    public Lid(int number, Cup cup) {
        this.number = number;
        this.color = cup.getColor();
        this.cupWidthPx = cup.getCupWidthPx();
        this.visible = false;
        this.body = new Rectangle();
    }

    /**
     * Dibuja la tapa en la posicion indicada.
     * @param xPx posicion x absoluta en pixeles
     * @param yPx posicion y absoluta en pixeles
     */
    public void drawAt(int xPx, int yPx) {
        makeInvisible();
        body = new Rectangle();

        body.changeSize(HEIGHT_CM * SCALE, cupWidthPx);
        body.moveHorizontal(xPx - 70);
        body.moveVertical(yPx - 15);
        body.changeColor(color);
        body.makeVisible();

        visible = true;
    }

    /**
     * Hace invisible la tapa.
     */
    public void makeInvisible() {
        body.makeInvisible();
        visible = false;
    }

    /**
     * Retorna el numero de la tapa.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Retorna la altura de la tapa en cm.
     */
    public int getHeightCm() {
        return HEIGHT_CM;
    }

    /**
     * Retorna el color de la tapa.
     */
    public String getColor() {
        return color;
    }

    /**
     * Retorna el ancho de la tapa en pixeles.
     */
    public int getWidthPx() {
        return cupWidthPx;
    }
}