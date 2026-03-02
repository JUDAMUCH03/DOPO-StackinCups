/**
 * Cup - Representa una taza en el simulador de apilamiento.
 * La taza numero i tiene altura 2i-1 cm y se dibuja como una forma de U.
 * Cuando esta tapada, su apertura superior se muestra cerrada con un color oscuro.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class Cup {
    private static final int SCALE = 20;
    private static final int WALL_THICK = 8;
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta", "orange"};

    private int number;
    private int heightCm;
    private int cupWidthPx;
    private String color;
    private boolean visible;
    private boolean covered;

    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle base;

    /**
     * Crea una taza con su numero y el ancho de la torre.
     * @param number       numero de la taza (i)
     * @param towerWidthCm ancho de la torre en cm
     */
    public Cup(int number, int towerWidthCm) {
        this.number = number;
        this.heightCm = 2 * number - 1;
        this.color = COLORS[(number - 1) % COLORS.length];
        this.cupWidthPx = calculateWidth(number, towerWidthCm);
        this.visible = false;
        this.covered = false;
        leftWall  = new Rectangle();
        rightWall = new Rectangle();
        base      = new Rectangle();
    }

    /*
     * Calcula el ancho de la taza en pixeles sin exceder el ancho de la torre.
     */
    private int calculateWidth(int number, int towerWidthCm) {
        int desired    = number * SCALE * 2;
        int maxAllowed = towerWidthCm * SCALE - WALL_THICK * 2;
        return Math.min(desired, maxAllowed);
    }

    /**
     * Dibuja la taza en la posicion indicada.
     * Si esta tapada, cierra la apertura superior con un rectangulo negro.
     * @param xPx posicion x absoluta en pixeles (esquina superior izquierda)
     * @param yPx posicion y absoluta en pixeles (esquina superior izquierda)
     */
    public void drawAt(int xPx, int yPx) {
        makeInvisible();
        leftWall  = new Rectangle();
        rightWall = new Rectangle();
        base      = new Rectangle();

        int wallHeight = (heightCm - 1) * SCALE;

        leftWall.changeSize(wallHeight, WALL_THICK);
        leftWall.moveHorizontal(xPx - 70);
        leftWall.moveVertical(yPx - 15);
        leftWall.changeColor(color);
        leftWall.makeVisible();

        rightWall.changeSize(wallHeight, WALL_THICK);
        rightWall.moveHorizontal(xPx + cupWidthPx - WALL_THICK - 70);
        rightWall.moveVertical(yPx - 15);
        rightWall.changeColor(color);
        rightWall.makeVisible();

        base.changeSize(SCALE, cupWidthPx);
        base.moveHorizontal(xPx - 70);
        base.moveVertical(yPx + wallHeight - 15);
        base.changeColor(color);
        base.makeVisible();

        visible = true;
    }

    /**
     * Hace invisible la taza.
     */
    public void makeInvisible() {
        leftWall.makeInvisible();
        rightWall.makeInvisible();
        base.makeInvisible();
        visible = false;
    }

    /**
     * Marca la taza como tapada o destapada.
     * Cambia su apariencia visual en el proximo redibujado.
     * @param covered true si la taza esta tapada
     */
    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    /**
     * Retorna si la taza esta actualmente tapada.
     */
    public boolean isCovered() {
        return covered;
    }

    /**
     * Retorna el numero de la taza.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Retorna la altura de la taza en cm.
     */
    public int getHeightCm() {
        return heightCm;
    }

    /**
     * Retorna el ancho de la taza en pixeles.
     */
    public int getCupWidthPx() {
        return cupWidthPx;
    }

    /**
     * Retorna el color de la taza.
     */
    public String getColor() {
        return color;
    }

    /**
     * Indica si la taza es mas ancha que la torre.
     * @param towerWidthCm ancho de la torre en cm
     */
    public boolean isTooBig(int towerWidthCm) {
        return number * SCALE * 2 > towerWidthCm * SCALE - WALL_THICK * 2;
    }
}