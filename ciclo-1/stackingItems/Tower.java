import java.util.ArrayList;

public class Tower {

    private int width;
    private int maxHeight;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<Integer> itemHeights; // Guarda la altura de cada elemento (taza o tapa)
    private Rectangle towerBase;

    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.rectangles = new ArrayList<>();
        this.itemHeights = new ArrayList<>();

        towerBase = new Rectangle();
        towerBase.changeSize(maxHeight * 10, width * 10);
        towerBase.changeColor("white");
        towerBase.makeVisible();
    }

    public void pushCup(int cupHeight) {
        if (!ok()) {
            return;
        }

        String[] colors = {"blue", "red", "green", "cyan", "magenta", "yellow"};
        String color = colors[(rectangles.size() / 3) % colors.length];

        int scale = 10;
        int cupWidth = cupHeight * scale;
        int baseThickness = scale;
        int sideWidth = scale;
        int sideHeight = (cupHeight - 1) * scale;

        int xOffset = (rectangles.size() / 3) * scale;
        int currentTotalHeight = height(); // Obtener altura total actual
        int baseY = 250 - (currentTotalHeight * scale);

        Rectangle leftSide = new Rectangle();
        leftSide.changeSize(sideHeight, sideWidth);
        leftSide.changeColor(color);
        leftSide.moveVertical(baseY - sideHeight - baseThickness - 15);
        leftSide.moveHorizontal(xOffset);
        leftSide.makeVisible();
        rectangles.add(leftSide);

        Rectangle base = new Rectangle();
        base.changeSize(baseThickness, cupWidth);
        base.changeColor(color);
        base.moveVertical(baseY - baseThickness - 15);
        base.moveHorizontal(xOffset);
        base.makeVisible();
        rectangles.add(base);

        Rectangle rightSide = new Rectangle();
        rightSide.changeSize(sideHeight, sideWidth);
        rightSide.changeColor(color);
        rightSide.moveVertical(baseY - sideHeight - baseThickness - 15);
        rightSide.moveHorizontal(xOffset + cupWidth - sideWidth);
        rightSide.makeVisible();
        rectangles.add(rightSide);

        itemHeights.add(cupHeight); // Guardar altura de la taza
    }

    public void popCup() {
        if (rectangles.size() < 3) {
            return;
        }

        Rectangle rightSide = rectangles.remove(rectangles.size() - 1);
        Rectangle base = rectangles.remove(rectangles.size() - 1);
        Rectangle leftSide = rectangles.remove(rectangles.size() - 1);

        leftSide.makeInvisible();
        base.makeInvisible();
        rightSide.makeInvisible();

        if (!itemHeights.isEmpty()) {
            itemHeights.remove(itemHeights.size() - 1);
        }

        ok();
    }

    public void removeCup(int index) {
        int numCups = rectangles.size() / 3;

        if (index < 0 || index >= numCups) {
            return;
        }

        int startIdx = index * 3;

        Rectangle leftSide = rectangles.get(startIdx);
        Rectangle base = rectangles.get(startIdx + 1);
        Rectangle rightSide = rectangles.get(startIdx + 2);

        leftSide.makeInvisible();
        base.makeInvisible();
        rightSide.makeInvisible();

        rectangles.remove(startIdx);
        rectangles.remove(startIdx);
        rectangles.remove(startIdx);

        if (index < itemHeights.size()) {
            itemHeights.remove(index);
        }

        for (int i = startIdx; i < rectangles.size(); i++) {
            rectangles.get(i).moveVertical(10);
        }

        ok();
    }

    public void pushLid(int cupHeight) {
        if (!ok()) {
            return;
        }

        String[] colors = {"blue", "red", "green", "cyan", "magenta", "yellow"};
        String color = colors[(rectangles.size() / 3) % colors.length];

        int scale = 10;
        int cupWidth = cupHeight * scale;
        int lidHeight = scale;

        int xOffset = (rectangles.size() / 3) * scale;
        int currentTotalHeight = height();
        int lidY = 250 - (currentTotalHeight * scale);

        Rectangle lid = new Rectangle();
        lid.changeSize(lidHeight, cupWidth);
        lid.changeColor(color);
        lid.moveVertical(lidY - lidHeight - 15);
        lid.moveHorizontal(xOffset);
        lid.makeVisible();

        rectangles.add(lid);
        itemHeights.add(1); // La tapa siempre mide 1 cm
    }

    public void popLid() {
        if (rectangles.size() < 1) {
            return;
        }

        Rectangle lid = rectangles.remove(rectangles.size() - 1);
        lid.makeInvisible();

        if (!itemHeights.isEmpty()) {
            itemHeights.remove(itemHeights.size() - 1);
        }

        ok();
    }

    public void removeLid(int index) {
        if (index < 0 || index >= rectangles.size()) {
            return;
        }

        Rectangle lid = rectangles.get(index);
        lid.makeInvisible();

        rectangles.remove(index);

        if (index < itemHeights.size()) {
            itemHeights.remove(index);
        }

        for (int i = index; i < rectangles.size(); i++) {
            rectangles.get(i).moveVertical(10);
        }

        ok();
    }

    /**
     * Calcula la altura total de la torre desde el punto más bajo hasta el más alto.
     * La primera taza contribuye con su altura completa.
     * Las tazas/tapas subsecuentes solo agregan 1 cm (su base).
     * @return altura total en cm
     */
    public int height() {
        if (itemHeights.isEmpty()) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0; i < itemHeights.size(); i++) {
            if (i == 0) {
                // La primera taza contribuye con su altura completa
                totalHeight += itemHeights.get(i);
            } else {
                // Las demás solo agregan 1 cm (su base o el grosor de la tapa)
                totalHeight += 1;
            }
        }

        return totalHeight;
    }

    public void orderTower() {
        if (rectangles.size() < 3) {
            return;
        }

        for (Rectangle rect : rectangles) {
            rect.makeInvisible();
        }

        rectangles.clear();
        itemHeights.clear();
    }

    public void reverseTower() {
        if (rectangles.size() < 6) {
            return;
        }

        ArrayList<Rectangle[]> cups = new ArrayList<>();
        ArrayList<Integer> heights = new ArrayList<>(itemHeights);

        for (int i = 0; i < rectangles.size(); i += 3) {
            if (i + 2 < rectangles.size()) {
                Rectangle[] cup = new Rectangle[3];
                cup[0] = rectangles.get(i);
                cup[1] = rectangles.get(i + 1);
                cup[2] = rectangles.get(i + 2);
                cups.add(cup);
            }
        }

        for (Rectangle rect : rectangles) {
            rect.makeInvisible();
        }

        rectangles.clear();
        itemHeights.clear();

        for (int i = cups.size() - 1; i >= 0; i--) {
            Rectangle[] cup = cups.get(i);

            int scale = 10;
            int xOffset = (rectangles.size() / 3) * scale;
            int currentTotalHeight = height();
            int baseY = 250 - (currentTotalHeight * scale);

            cup[0].moveVertical(baseY - 15);
            cup[0].moveHorizontal(xOffset);
            cup[0].makeVisible();
            rectangles.add(cup[0]);

            cup[1].moveVertical(baseY - 15);
            cup[1].moveHorizontal(xOffset);
            cup[1].makeVisible();
            rectangles.add(cup[1]);

            cup[2].moveVertical(baseY - 15);
            cup[2].moveHorizontal(xOffset);
            cup[2].makeVisible();
            rectangles.add(cup[2]);

            if (i < heights.size()) {
                itemHeights.add(heights.get(i));
            }
        }
    }

    public int[] lidedCups() {
        return new int[0];
    }

    public String[][] stackingItems() {
        return new String[0][0];
    }

    public void makeVisible() {
        if (towerBase != null) {
            towerBase.makeVisible();
        }

        for (Rectangle rect : rectangles) {
            rect.makeVisible();
        }
    }

    public void makeInvisible() {
        if (towerBase != null) {
            towerBase.makeInvisible();
        }

        for (Rectangle rect : rectangles) {
            rect.makeInvisible();
        }
    }

    public void exit() {
        makeInvisible();
        rectangles.clear();
        itemHeights.clear();
        System.out.println("Simulador terminado");
    }

    public boolean ok() {
        return true;
    }
}