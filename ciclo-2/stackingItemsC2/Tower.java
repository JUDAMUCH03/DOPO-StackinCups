import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Dimension;

/**
 * Tower - Simulador de apilamiento de tazas y tapas.
 * Basado en el Problem J - Stacking Cups de ICPC 2025.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class Tower {

    private static final int SCALE = 20;
    private static final int WALL = 5;
    private static final int TOWER_X = 50;
    private static final int TOWER_Y = 20;

    private int width;
    private int maxHeight;
    private ArrayList<Object> items;
    private boolean ok;
    private boolean visible;

    private Rectangle leftWall;
    private Rectangle rightWall;
    private Rectangle floor;
    private ArrayList<Rectangle> marks;

    /**
     * Crea una nueva torre con las dimensiones especificadas.
     * @param width     ancho de la torre en cm
     * @param maxHeight altura maxima de la torre en cm
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.items = new ArrayList<>();
        this.ok = true;
        this.visible = false;
        this.marks = new ArrayList<>();
    }

    /**
     * Crea una torre con tazas numeradas del 1 al numero indicado.
     * El ancho y la altura maxima se calculan automaticamente para que
     * todas las tazas quepan en el peor caso (sin nesting).
     * No incluye tapas.
     * @param cups cantidad de tazas a crear (de 1 a cups)
     */
    public Tower(int cups) {
        this.width = cups * 2 + 2;
        this.maxHeight = cups * cups;
        this.items = new ArrayList<>();
        this.ok = true;
        this.visible = false;
        this.marks = new ArrayList<>();
        for (int i = 1; i <= cups; i++) {
            items.add(new Cup(i, this.width));
        }
    }

    /**
     * Agrega una taza a la torre.
     * @param i numero de la taza
     */
    public void pushCup(int i) {
        ok = false;
        if (cupExists(i)) {
            if (visible) JOptionPane.showMessageDialog(null, "Ya existe una taza con el numero " + i);
            return;
        }
        Cup cup = new Cup(i, width);
        if (cup.isTooBig(width)) {
            if (visible) JOptionPane.showMessageDialog(null, "La taza " + i + " es mas ancha que la torre");
            return;
        }
        if (!fitsInTower(cup)) {
            if (visible) JOptionPane.showMessageDialog(null, "La taza " + i + " no cabe en la torre");
            return;
        }
        items.add(cup);
        ok = true;
        if (visible) redraw();
    }

    /**
     * Elimina la ultima taza agregada a la torre.
     * Si tiene su tapa en la torre, la elimina tambien.
     */
    public void popCup() {
        ok = false;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i) instanceof Cup) {
                Cup cup = (Cup) items.get(i);
                cup.makeInvisible();
                items.remove(i);
                removeLidIfExists(cup.getNumber());
                ok = true;
                if (visible) redraw();
                return;
            }
        }
        if (visible) JOptionPane.showMessageDialog(null, "No hay tazas en la torre");
    }

    /**
     * Elimina la taza con el numero especificado.
     * Si tiene su tapa en la torre, la elimina tambien.
     * @param i numero de la taza a eliminar
     */
    public void removeCup(int i) {
        ok = false;
        for (int j = 0; j < items.size(); j++) {
            if (items.get(j) instanceof Cup && ((Cup) items.get(j)).getNumber() == i) {
                ((Cup) items.get(j)).makeInvisible();
                items.remove(j);
                removeLidIfExists(i);
                ok = true;
                if (visible) redraw();
                return;
            }
        }
        if (visible) JOptionPane.showMessageDialog(null, "No existe una taza con el numero " + i);
    }

    /**
     * Agrega una tapa a la torre.
     * @param i numero de la tapa
     */
    public void pushLid(int i) {
        ok = false;
        if (lidExists(i)) {
            if (visible) JOptionPane.showMessageDialog(null, "Ya existe una tapa con el numero " + i);
            return;
        }
        Cup tempCup = new Cup(i, width);
        if (tempCup.isTooBig(width)) {
            if (visible) JOptionPane.showMessageDialog(null, "La tapa " + i + " es mas ancha que la torre");
            return;
        }
        Cup cup = findCup(i);
        Lid lid = (cup != null) ? new Lid(i, cup) : new Lid(i, tempCup);
        if (!fitsInTower(lid)) {
            if (visible) JOptionPane.showMessageDialog(null, "La tapa " + i + " no cabe en la torre");
            return;
        }
        items.add(lid);
        ok = true;
        if (visible) redraw();
    }

    /**
     * Elimina la tapa que esta en el tope de la torre.
     */
    public void popLid() {
        ok = false;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i) instanceof Lid) {
                Lid lid = (Lid) items.get(i);
                lid.makeInvisible();
                items.remove(i);
                ok = true;
                if (visible) redraw();
                return;
            }
        }
        if (visible) JOptionPane.showMessageDialog(null, "No hay tapas en la torre");
    }

    /**
     * Elimina la tapa con el numero especificado.
     * @param i numero de la tapa a eliminar
     */
    public void removeLid(int i) {
        ok = false;
        for (int j = 0; j < items.size(); j++) {
            if (items.get(j) instanceof Lid && ((Lid) items.get(j)).getNumber() == i) {
                ((Lid) items.get(j)).makeInvisible();
                items.remove(j);
                ok = true;
                if (visible) redraw();
                return;
            }
        }
        if (visible) JOptionPane.showMessageDialog(null, "No existe una tapa con el numero " + i);
    }

    /**
     * Mueve cada tapa inmediatamente encima de su taza correspondiente.
     * Solo afecta las tapas cuya taza este presente en la torre.
     * Las tapas sin taza permanecen en su posicion actual.
     * Las tazas tapadas lucen con su apertura cerrada visualmente.
     */
    public void cover() {
        ok = false;
        ArrayList<Object> newItems = new ArrayList<>();
        ArrayList<Lid> placedLids = new ArrayList<>();

        for (Object item : items) {
            if (item instanceof Cup) {
                Cup cup = (Cup) item;
                newItems.add(cup);
                Lid matchingLid = findLid(cup.getNumber());
                if (matchingLid != null) {
                    newItems.add(matchingLid);
                    placedLids.add(matchingLid);
                    cup.setCovered(true);
                } else {
                    cup.setCovered(false);
                }
            }
        }
        for (Object item : items) {
            if (item instanceof Lid && !placedLids.contains(item)) {
                newItems.add(item);
            }
        }

        items = filterByHeight(newItems);
        ok = true;
        if (visible) redraw();
    }

    /**
     * Intercambia la posicion de dos objetos en la torre.
     * Los objetos se identifican por tipo y numero. Ej: {"cup","4"} o {"lid","4"}.
     * @param o1 descriptor del primer objeto {tipo, numero}
     * @param o2 descriptor del segundo objeto {tipo, numero}
     */
    public void swap(String[] o1, String[] o2) {
        ok = false;
        int idx1 = findIndex(o1);
        int idx2 = findIndex(o2);
        if (idx1 == -1) {
            if (visible) JOptionPane.showMessageDialog(null, "No se encontro: " + o1[0] + " " + o1[1]);
            return;
        }
        if (idx2 == -1) {
            if (visible) JOptionPane.showMessageDialog(null, "No se encontro: " + o2[0] + " " + o2[1]);
            return;
        }
        Object temp = items.get(idx1);
        items.set(idx1, items.get(idx2));
        items.set(idx2, temp);
        ok = true;
        if (visible) redraw();
    }

    /**
     * Busca un intercambio de dos elementos que reduzca la altura de la torre.
     * Retorna los descriptores de los dos elementos a intercambiar.
     * Si no existe ningun intercambio que reduzca la altura, retorna arreglo vacio.
     * Ej: {{"cup","4"},{"lid","4"}}
     * @return par de descriptores {tipo, numero} o arreglo vacio
     */
    public String[][] swapToReduce() {
        int currentHeight = height();
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                doSwap(i, j);
                int newHeight = height();
                doSwap(i, j);
                if (newHeight < currentHeight) {
                    return new String[][] {
                        {itemType(items.get(i)), String.valueOf(itemNumber(items.get(i)))},
                        {itemType(items.get(j)), String.valueOf(itemNumber(items.get(j)))}
                    };
                }
            }
        }
        return new String[0][0];
    }

    /**
     * Ordena los elementos de la torre de mayor a menor numero.
     * Solo incluye los elementos que quepan en la torre.
     */
    public void orderTower() {
        ok = false;
        ArrayList<Object> cups = new ArrayList<>();
        ArrayList<Object> lids = new ArrayList<>();

        for (Object item : items) {
            if (item instanceof Cup) cups.add(item);
            else if (item instanceof Lid) lids.add(item);
        }

        cups.sort((a, b) -> ((Cup) b).getNumber() - ((Cup) a).getNumber());
        lids.sort((a, b) -> ((Lid) b).getNumber() - ((Lid) a).getNumber());

        ArrayList<Object> ordered = new ArrayList<>();
        for (Object cup : cups) {
            ordered.add(cup);
            int number = ((Cup) cup).getNumber();
            for (Object lid : lids) {
                if (((Lid) lid).getNumber() == number) {
                    ordered.add(lid);
                    break;
                }
            }
        }
        for (Object lid : lids) {
            int number = ((Lid) lid).getNumber();
            boolean cupFound = false;
            for (Object cup : cups) {
                if (((Cup) cup).getNumber() == number) { cupFound = true; break; }
            }
            if (!cupFound) ordered.add(lid);
        }

        items = filterByHeight(ordered);
        ok = true;
        if (visible) redraw();
    }

    /**
     * Coloca los elementos de la torre en orden inverso al actual.
     * Solo incluye los elementos que quepan en la torre.
     */
    public void reverseTower() {
        ok = false;
        ArrayList<Object> reversed = new ArrayList<>();
        for (int i = items.size() - 1; i >= 0; i--) {
            reversed.add(items.get(i));
        }
        items = filterByHeight(reversed);
        ok = true;
        if (visible) redraw();
    }

    /**
     * Retorna la altura actual de los elementos apilados en cm.
     */
    public int height() {
        int currentBottomY = maxHeight;
        int prevHeightCm = 0;
        int prevCupNumber = 0;
        boolean prevWasCup = false;
        int highestPoint = maxHeight;

        for (Object item : items) {
            if (item instanceof Cup) {
                Cup cup = (Cup) item;
                boolean nests = prevWasCup && cup.getNumber() < prevCupNumber;
                currentBottomY -= nests ? 1 : prevHeightCm;
                int top = currentBottomY - cup.getHeightCm();
                if (top < highestPoint) highestPoint = top;
                prevHeightCm = cup.getHeightCm();
                prevCupNumber = cup.getNumber();
                prevWasCup = true;
            } else if (item instanceof Lid) {
                Lid lid = (Lid) item;
                currentBottomY -= prevHeightCm;
                int top = currentBottomY - lid.getHeightCm();
                if (top < highestPoint) highestPoint = top;
                prevHeightCm = lid.getHeightCm();
                prevWasCup = false;
            }
        }
        return maxHeight - highestPoint;
    }

    /**
     * Retorna los numeros de las tazas tapadas por sus tapas, ordenados de menor a mayor.
     */
    public int[] lidedCups() {
        ArrayList<Integer> result = new ArrayList<>();
        for (Object item : items) {
            if (item instanceof Cup) {
                int number = ((Cup) item).getNumber();
                if (lidExists(number)) result.add(number);
            }
        }
        result.sort((a, b) -> a - b);
        int[] array = new int[result.size()];
        for (int i = 0; i < result.size(); i++) array[i] = result.get(i);
        return array;
    }

    /**
     * Retorna los elementos apilados ordenados de base a cima.
     * Cada elemento es un par {tipo, numero} en minusculas.
     */
    public String[][] stackingItems() {
        String[][] result = new String[items.size()][2];
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof Cup) {
                result[i][0] = "cup";
                result[i][1] = String.valueOf(((Cup) items.get(i)).getNumber());
            } else {
                result[i][0] = "lid";
                result[i][1] = String.valueOf(((Lid) items.get(i)).getNumber());
            }
        }
        return result;
    }

    /**
     * Retorna los numeros de las tazas tapadas en formato legible.
     */
    public String lidedCupsInfo() {
        int[] lided = lidedCups();
        String result = "[";
        for (int i = 0; i < lided.length; i++) {
            result += lided[i];
            if (i < lided.length - 1) result += ", ";
        }
        return result + "]";
    }

    /**
     * Retorna los elementos apilados en formato legible.
     */
    public String stackingItemsInfo() {
        String[][] stacking = stackingItems();
        String result = "{";
        for (int i = 0; i < stacking.length; i++) {
            result += "{\"" + stacking[i][0] + "\",\"" + stacking[i][1] + "\"}";
            if (i < stacking.length - 1) result += ",";
        }
        return result + "}";
    }

    /**
     * Hace visible el simulador dibujando la torre y sus elementos.
     * Redimensiona el canvas para que se ajuste exactamente a la torre.
     * Si la torre no cabe en la pantalla del sistema, no se hace visible.
     */
    public void makeVisible() {
        int towerWidthPx  = width * SCALE;
        int towerHeightPx = maxHeight * SCALE;
        int neededWidth   = TOWER_X + towerWidthPx + WALL + 20;
        int neededHeight  = TOWER_Y + towerHeightPx + WALL + 20;

        Canvas canvas = Canvas.getCanvas();
        Dimension screen = canvas.getScreenSize();

        if (neededWidth > screen.width || neededHeight > screen.height) {
            ok = false;
            return;
        }

        canvas.resize(neededWidth, neededHeight);
        visible = true;

        leftWall = new Rectangle();
        leftWall.changeSize(towerHeightPx, WALL);
        leftWall.moveHorizontal(TOWER_X - 70);
        leftWall.moveVertical(TOWER_Y - 15);
        leftWall.changeColor("black");
        leftWall.makeVisible();

        rightWall = new Rectangle();
        rightWall.changeSize(towerHeightPx, WALL);
        rightWall.moveHorizontal(TOWER_X + towerWidthPx - 70);
        rightWall.moveVertical(TOWER_Y - 15);
        rightWall.changeColor("black");
        rightWall.makeVisible();

        floor = new Rectangle();
        floor.changeSize(WALL, towerWidthPx + WALL);
        floor.moveHorizontal(TOWER_X - 70);
        floor.moveVertical(TOWER_Y + towerHeightPx - 15);
        floor.changeColor("black");
        floor.makeVisible();

        drawMarks(towerHeightPx);
        if (items.size() > 0) redraw();
    }

    /**
     * Hace invisible el simulador ocultando la torre y sus elementos.
     */
    public void makeInvisible() {
        visible = false;
        if (leftWall != null)  leftWall.makeInvisible();
        if (rightWall != null) rightWall.makeInvisible();
        if (floor != null)     floor.makeInvisible();
        for (Rectangle mark : marks) mark.makeInvisible();
        marks.clear();
        for (Object item : items) {
            if (item instanceof Cup)      ((Cup) item).makeInvisible();
            else if (item instanceof Lid) ((Lid) item).makeInvisible();
        }
    }

    /**
     * Termina el simulador.
     */
    public void exit() {
        makeInvisible();
        System.exit(0);
    }

    /**
     * Retorna si la ultima operacion fue exitosa.
     */
    public boolean ok() {
        return ok;
    }

    // -------------------------------------------------------------------------
    // Metodos privados auxiliares
    // -------------------------------------------------------------------------

    /*
     * Busca el indice de un elemento por su descriptor {tipo, numero}.
     * Retorna -1 si no se encuentra.
     */
    private int findIndex(String[] descriptor) {
        String type = descriptor[0].toLowerCase();
        int number = Integer.parseInt(descriptor[1]);
        for (int i = 0; i < items.size(); i++) {
            Object item = items.get(i);
            if (type.equals("cup") && item instanceof Cup && ((Cup) item).getNumber() == number) return i;
            if (type.equals("lid") && item instanceof Lid && ((Lid) item).getNumber() == number) return i;
        }
        return -1;
    }

    /*
     * Intercambia los elementos en las posiciones i y j de la lista.
     */
    private void doSwap(int i, int j) {
        Object temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
    }

    /*
     * Retorna el tipo del elemento como cadena ("cup" o "lid").
     */
    private String itemType(Object item) {
        return (item instanceof Cup) ? "cup" : "lid";
    }

    /*
     * Retorna el numero del elemento.
     */
    private int itemNumber(Object item) {
        if (item instanceof Cup) return ((Cup) item).getNumber();
        if (item instanceof Lid) return ((Lid) item).getNumber();
        return -1;
    }

    /*
     * Dibuja las marcaciones de centimetros a lo largo de la pared izquierda.
     */
    private void drawMarks(int towerHeightPx) {
        for (int i = 0; i <= maxHeight; i++) {
            Rectangle mark = new Rectangle();
            mark.changeSize(3, 10);
            mark.moveHorizontal(TOWER_X - 15 - 70);
            mark.moveVertical(TOWER_Y + towerHeightPx - i * SCALE - 15);
            mark.changeColor("red");
            mark.makeVisible();
            marks.add(mark);
        }
    }

    /*
     * Busca y retorna la taza con el numero dado, o null si no existe.
     */
    private Cup findCup(int number) {
        for (Object item : items) {
            if (item instanceof Cup && ((Cup) item).getNumber() == number) return (Cup) item;
        }
        return null;
    }

    /*
     * Busca y retorna la tapa con el numero dado, o null si no existe.
     */
    private Lid findLid(int number) {
        for (Object item : items) {
            if (item instanceof Lid && ((Lid) item).getNumber() == number) return (Lid) item;
        }
        return null;
    }

    /*
     * Verifica si ya existe una taza con ese numero en la torre.
     */
    private boolean cupExists(int number) {
        return findCup(number) != null;
    }

    /*
     * Verifica si ya existe una tapa con ese numero en la torre.
     */
    private boolean lidExists(int number) {
        for (Object item : items) {
            if (item instanceof Lid && ((Lid) item).getNumber() == number) return true;
        }
        return false;
    }

    /*
     * Simula agregar el elemento y verifica si cabe en la torre.
     */
    private boolean fitsInTower(Object newItem) {
        items.add(newItem);
        int newHeight = height();
        items.remove(newItem);
        return newHeight <= maxHeight;
    }

    /*
     * Elimina la tapa con el numero dado si existe en la torre.
     */
    private void removeLidIfExists(int number) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof Lid && ((Lid) items.get(i)).getNumber() == number) {
                ((Lid) items.get(i)).makeInvisible();
                items.remove(i);
                return;
            }
        }
    }

    /*
     * Filtra una lista de elementos dejando solo los que quepan en la torre.
     */
    private ArrayList<Object> filterByHeight(ArrayList<Object> newItems) {
        ArrayList<Object> result = new ArrayList<>();
        items = new ArrayList<>();
        for (Object item : newItems) {
            if (item instanceof Cup) {
                Cup cup = (Cup) item;
                if (!cup.isTooBig(width) && fitsInTower(cup)) {
                    items.add(cup);
                    result.add(cup);
                } else {
                    cup.makeInvisible();
                }
            } else if (item instanceof Lid) {
                Lid lid = (Lid) item;
                if (fitsInTower(lid)) {
                    items.add(lid);
                    result.add(lid);
                } else {
                    lid.makeInvisible();
                }
            }
        }
        return result;
    }

    /*
     * Redibuja todos los elementos de la torre en orden secuencial.
     * El anidamiento (avance de 1cm) solo ocurre cuando una taza va
     * inmediatamente despues de una taza de numero mayor.
     * Las tapas se posicionan siempre encima de su taza correspondiente
     * si esta en la torre, o en su posicion secuencial si no tiene taza.
     */
    private void redraw() {
        int floorY = TOWER_Y + maxHeight * SCALE;
        int centerX = TOWER_X + (width * SCALE) / 2;
        int currentBottomY = floorY;
        int prevHeightPx = 0;
        int prevCupNumber = 0;
        boolean prevWasCup = false;
        java.util.HashMap<Integer, Integer> cupTopYMap = new java.util.HashMap<>();

        for (Object item : items) {
            if (item instanceof Cup) {
                Cup cup = (Cup) item;
                boolean nests = prevWasCup && cup.getNumber() < prevCupNumber;
                currentBottomY -= nests ? SCALE : prevHeightPx;
                int cupTopY = currentBottomY - cup.getHeightCm() * SCALE;
                cup.drawAt(centerX - cup.getCupWidthPx() / 2, cupTopY);
                cupTopYMap.put(cup.getNumber(), cupTopY);
                prevHeightPx = cup.getHeightCm() * SCALE;
                prevCupNumber = cup.getNumber();
                prevWasCup = true;
            } else if (item instanceof Lid) {
                Lid lid = (Lid) item;
                int sequentialY = currentBottomY - prevHeightPx;
                if (cupTopYMap.containsKey(lid.getNumber())) {
                    int cupTopY = cupTopYMap.get(lid.getNumber());
                    int lidBottomY = Math.min(sequentialY, cupTopY);
                    lid.drawAt(centerX - lid.getWidthPx() / 2, lidBottomY - lid.getHeightCm() * SCALE);
                    currentBottomY = lidBottomY;
                } else {
                    lid.drawAt(centerX - lid.getWidthPx() / 2, sequentialY - lid.getHeightCm() * SCALE);
                    currentBottomY = sequentialY;
                }
                prevHeightPx = lid.getHeightCm() * SCALE;
                prevWasCup = false;
            }
        }
    }
}