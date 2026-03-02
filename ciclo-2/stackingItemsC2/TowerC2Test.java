import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TowerC2Test - Pruebas de unidad para el ciclo 2 del simulador.
 * Todas las pruebas se ejecutan en modo invisible.
 * Convencion de nombres: accordingMS (Munar, Sua en orden alfabetico)
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class TowerC2Test {

    // =========================================================================
    // Requisito 10: Tower(int cups)
    // =========================================================================

    /**
     * Deberia: crear la torre con tazas del 1 al numero indicado.
     */
    @Test
    public void accordingMSShouldCreateCupsFrom1ToN() {
        Tower t = new Tower(4);
        String[][] stacking = t.stackingItems();
        assertEquals(4, stacking.length);
        assertEquals("cup", stacking[0][0]); assertEquals("1", stacking[0][1]);
        assertEquals("cup", stacking[1][0]); assertEquals("2", stacking[1][1]);
        assertEquals("cup", stacking[2][0]); assertEquals("3", stacking[2][1]);
        assertEquals("cup", stacking[3][0]); assertEquals("4", stacking[3][1]);
    }

    /**
     * Deberia: quedar ok despues de crear la torre con cups.
     */
    @Test
    public void accordingMSShouldBeOkAfterCupsConstructor() {
        Tower t = new Tower(4);
        assertTrue(t.ok());
    }

    /**
     * Deberia: crear torre con 1 sola taza correctamente.
     */
    @Test
    public void accordingMSShouldCreateTowerWithOneCup() {
        Tower t = new Tower(1);
        String[][] stacking = t.stackingItems();
        assertEquals(1, stacking.length);
        assertEquals("cup", stacking[0][0]);
        assertEquals("1", stacking[0][1]);
    }

    /**
     * No deberia: incluir tapas al usar el constructor Tower(cups).
     */
    @Test
    public void accordingMSShouldNotHaveLidsWithCupsConstructor() {
        Tower t = new Tower(4);
        assertEquals(0, t.lidedCups().length);
    }

    /**
     * No deberia: crear tazas de mas al usar Tower(cups).
     */
    @Test
    public void accordingMSShouldNotCreateMoreCupsThanSpecified() {
        Tower t = new Tower(3);
        assertEquals(3, t.stackingItems().length);
    }

    // =========================================================================
    // Requisito 11: swap(String[] o1, String[] o2)
    // =========================================================================

    /**
     * Deberia: intercambiar las posiciones de dos tazas.
     */
    @Test
    public void accordingMSShouldSwapTwoCups() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.swap(new String[]{"cup", "1"}, new String[]{"cup", "2"});
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals("2", stacking[0][1]);
        assertEquals("1", stacking[1][1]);
    }

    /**
     * Deberia: intercambiar una taza y una tapa.
     */
    @Test
    public void accordingMSShouldSwapCupAndLid() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        t.swap(new String[]{"cup", "1"}, new String[]{"lid", "1"});
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals("lid", stacking[0][0]);
        assertEquals("cup", stacking[1][0]);
    }

    /**
     * Deberia: intercambiar dos tapas.
     */
    @Test
    public void accordingMSShouldSwapTwoLids() {
        Tower t = new Tower(10, 20);
        t.pushLid(1);
        t.pushLid(2);
        t.swap(new String[]{"lid", "1"}, new String[]{"lid", "2"});
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals("2", stacking[0][1]);
        assertEquals("1", stacking[1][1]);
    }

    /**
     * No deberia: hacer swap si el primer elemento no existe.
     */
    @Test
    public void accordingMSShouldNotSwapIfFirstElementMissing() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.swap(new String[]{"cup", "99"}, new String[]{"cup", "2"});
        assertFalse(t.ok());
    }

    /**
     * No deberia: hacer swap si el segundo elemento no existe.
     */
    @Test
    public void accordingMSShouldNotSwapIfSecondElementMissing() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.swap(new String[]{"cup", "2"}, new String[]{"cup", "99"});
        assertFalse(t.ok());
    }

    /**
     * No deberia: hacer swap si la torre esta vacia.
     */
    @Test
    public void accordingMSShouldNotSwapOnEmptyTower() {
        Tower t = new Tower(10, 20);
        t.swap(new String[]{"cup", "1"}, new String[]{"cup", "2"});
        assertFalse(t.ok());
    }

    // =========================================================================
    // Requisito 12: cover()
    // =========================================================================

    /**
     * Deberia: mover la tapa inmediatamente encima de su taza.
     */
    @Test
    public void accordingMSShouldMoveLidOnTopOfItsCup() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        t.cover();
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        boolean found = false;
        for (int i = 0; i < stacking.length - 1; i++) {
            if (stacking[i][0].equals("cup") && stacking[i][1].equals("1")
                    && stacking[i+1][0].equals("lid") && stacking[i+1][1].equals("1")) {
                found = true;
            }
        }
        assertTrue(found);
    }

    /**
     * Deberia: quedar ok despues de cover aunque no haya parejas.
     */
    @Test
    public void accordingMSShouldBeOkAfterCoverWithNoPairs() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.cover();
        assertTrue(t.ok());
    }

    /**
     * Deberia: mantener las tapas sin taza en la torre despues de cover.
     */
    @Test
    public void accordingMSShouldKeepLidsWithoutCupAfterCover() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.pushLid(1);
        t.cover();
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        boolean lidPresent = false;
        for (String[] item : stacking) {
            if (item[0].equals("lid") && item[1].equals("1")) lidPresent = true;
        }
        assertTrue(lidPresent);
    }

    /**
     * No deberia: cover mover una tapa si su taza no esta en la torre.
     */
    @Test
    public void accordingMSShouldNotCoverCupIfNoCupInTower() {
        Tower t = new Tower(10, 20);
        t.pushLid(1);
        int sizeBefore = t.stackingItems().length;
        t.cover();
        assertEquals(sizeBefore, t.stackingItems().length);
    }

    /**
     * No deberia: cover alterar el numero total de elementos de la torre.
     */
    @Test
    public void accordingMSShouldNotChangeSizeAfterCover() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        int sizeBefore = t.stackingItems().length;
        t.cover();
        assertEquals(sizeBefore, t.stackingItems().length);
    }

    // =========================================================================
    // Requisito 13: swapToReduce()
    // =========================================================================

    /**
     * Deberia: retornar un par que al aplicarlo reduce la altura.
     */
    @Test
    public void accordingMSShouldReturnPairThatReducesHeight() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(3);
        t.pushCup(2);
        int heightBefore = t.height();
        String[][] pair = t.swapToReduce();
        if (pair.length > 0) {
            t.swap(pair[0], pair[1]);
            assertTrue(t.height() < heightBefore);
        }
    }

    /**
     * Deberia: retornar arreglo no nulo siempre.
     */
    @Test
    public void accordingMSSwapToReduceShouldNeverReturnNull() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        assertNotNull(t.swapToReduce());
    }

    /**
     * Deberia: retornar par con descriptores de tipo y numero validos.
     */
    @Test
    public void accordingMSSwapToReduceShouldReturnValidDescriptors() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(3);
        t.pushCup(2);
        String[][] pair = t.swapToReduce();
        if (pair.length > 0) {
            assertEquals(2, pair.length);
            assertTrue(pair[0][0].equals("cup") || pair[0][0].equals("lid"));
            assertTrue(pair[1][0].equals("cup") || pair[1][0].equals("lid"));
        }
    }

    /**
     * No deberia: retornar par si la torre ya esta en orden optimo.
     */
    @Test
    public void accordingMSShouldReturnEmptyIfNoReducingSwapExists() {
        Tower t = new Tower(10, 20);
        t.pushCup(3);
        t.pushCup(2);
        t.pushCup(1);
        String[][] pair = t.swapToReduce();
        assertEquals(0, pair.length);
    }

    /**
     * No deberia: modificar la torre al llamar swapToReduce.
     */
    @Test
    public void accordingMSSwapToReduceShouldNotModifyTower() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(3);
        t.pushCup(2);
        String[][] before = t.stackingItems();
        t.swapToReduce();
        String[][] after = t.stackingItems();
        assertEquals(before.length, after.length);
        for (int i = 0; i < before.length; i++) {
            assertEquals(before[i][0], after[i][0]);
            assertEquals(before[i][1], after[i][1]);
        }
    }
}