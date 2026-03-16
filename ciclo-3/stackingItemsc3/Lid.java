/**
 * Lid - Representa una tapa en el simulador de apilamiento.
 * La tapa mide siempre 1 cm de alto y tiene el mismo color que su taza.
 * Conoce y almacena su propia posicion en el canvas.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class Lid extends StackableItem {
    private static final int SCALE = 20;
    private static final int HEIGHT_CM = 1;

    private int number;
    private String color;
    private int cupWidthPx;
    private boolean visible;
    private int xPx;
    private int yPx;

    private Rectangle body;

    /**
     * Crea una tapa asociada a la taza del numero dado.
     * @param number numero de la tapa (mismo que su taza)
     * @param cup    la taza a la que pertenece esta tapa
     */
    public Lid(int number, Cup cup) {
        this.number = number;
        this.color = cup.getColor();
        this.cupWidthPx = cup.getCupWidthPx();
        this.visible = false;
        this.xPx = 0;
        this.yPx = 0;
        this.body = new Rectangle();
    }

    /**
     * Dibuja la tapa en la posicion indicada y almacena su posicion.
     * @param xPx posicion x absoluta en pixeles
     * @param yPx posicion y absoluta en pixeles
     */
    @Override
    public void drawAt(int xPx, int yPx) {
        makeInvisible();
        this.xPx = xPx;
        this.yPx = yPx;
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
    @Override
    public void makeInvisible() {
        body.makeInvisible();
        visible = false;
    }

    /**
     * Retorna el tipo del elemento.
     */
    @Override
    public String getType() {
        return "lid";
    }

    /**
     * Retorna el numero de la tapa.
     */
    @Override
    public int getNumber() {
        return number;
    }

    /**
     * Retorna la altura de la tapa en cm.
     */
    @Override
    public int getHeightCm() {
        return HEIGHT_CM;
    }

    /**
     * Retorna el color de la tapa.
     */
    @Override
    public String getColor() {
        return color;
    }

    /**
     * Retorna el tipo del elemento.
     */
    public String getTypeName() {
        return "lid";
    }

    /**
     * Retorna el ancho de la tapa en pixeles.
     */
    public int getWidthPx() {
        return cupWidthPx;
    }

    /**
     * Retorna la posicion x actual de la tapa en pixeles.
     */
    public int getXPx() {
        return xPx;
    }

    /**
     * Retorna la posicion y actual de la tapa en pixeles.
     */
    public int getYPx() {
        return yPx;
    }
}