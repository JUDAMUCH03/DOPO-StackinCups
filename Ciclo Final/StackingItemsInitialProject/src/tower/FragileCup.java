package tower;

/**
 * FragileCup - Taza fragil que se rompe si tiene 3 o mas elementos encima.
 */
public class FragileCup extends Cup {

    private static final int MAX_ELEMENTS_ABOVE = 3;

    public FragileCup(int number, int towerWidthCm) {
        super(number, towerWidthCm);
        this.color = "crimson";
    }

    @Override
    public boolean isFragile() {
        return true;
    }

    /**
     * Verifica si la taza debe romperse basándose en cuántos elementos tiene encima.
     * @param t referencia a la torre para consultar la pila y removerse si es necesario.
     */
    public void validateIntegrity(Tower t) {
        java.util.ArrayList<StackableItem> currentItems = t.getItems();
        int idx = currentItems.indexOf(this);
        
        if (idx != -1) {
            int elementsAbove = currentItems.size() - 1 - idx;
            
            if (elementsAbove >= MAX_ELEMENTS_ABOVE) {
                this.makeInvisible();
                currentItems.remove(this);
            }
        }
    }

    @Override
    public void enterTower(Tower t) {
        t.addItem(this);
    }
}