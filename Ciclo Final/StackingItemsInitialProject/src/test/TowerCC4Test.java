package test;
import tower.Tower;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TowerCC4Test - Casos para la prueba colectiva del ciclo 4.
 * Escenarios combinados que involucran multiples tipos interactuando.
 * Todas las pruebas se ejecutan en modo invisible.
 *
 */
public class TowerCC4Test {

    // Escenario 1: OpenerCup elimina FearfulLids al entrar

    @Test
    public void accordingMSOpenerShouldRemoveFearfulLidsOnEntry() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("fearful", 1);
        t.pushCup(2);
        t.pushLid("fearful", 2);
        t.pushCup("opener", 3);
        assertTrue(t.ok());
        int lidCount = 0;
        for (String[] item : t.stackingItems()) {
            if (item[0].equals("lid")) lidCount++;
        }
        assertEquals(0, lidCount);
    }

    // Escenario 2: CrazyLid queda debajo de HierarchicalCup

    @Test
    public void accordingMSCrazyLidGoesToBottomBelowHierarchical() {
        Tower t = new Tower(10, 20);
        t.pushCup("hierarchical", 3);
        t.pushLid("crazy", 1);
        String[][] stacking = t.stackingItems();
        assertEquals("lid", stacking[0][0]);
        assertEquals("cup", stacking[1][0]);
    }

    // Escenario 3: HierarchicalCup no desplaza tazas mayores

    @Test
    public void accordingMSHierarchicalShouldNotDisplaceLargerCups() {
        Tower t = new Tower(10, 20);
        t.pushCup(4);
        t.pushCup("hierarchical", 2);
        String[][] stacking = t.stackingItems();
        assertEquals("4", stacking[0][1]);
        assertEquals("2", stacking[1][1]);
    }

    // Escenario 4: FearfulLid no entra si su taza no esta en la torre

    @Test
    public void accordingMSFearfulLidCannotEnterWithoutItsCup() {
        Tower t = new Tower(10, 20);
        t.pushCup("opener", 2);
        t.pushLid("fearful", 1);
        assertFalse(t.ok());
    }

    // Escenario 5: torre con todos los tipos mezclados cuenta elementos correctamente

    @Test
    public void accordingMSMixedTowerShouldCountElementsCorrectly() {
        Tower t = new Tower(12, 30);
        t.pushCup(1);
        t.pushCup("hierarchical", 3);
        t.pushLid("crazy", 2);
        t.pushCup(4);
        t.pushLid("fearful", 1);
        assertTrue(t.ok());
        assertEquals(5, t.stackingItems().length);
    }

    // Escenario 6: HierarchicalCup bloqueada no impide remover otras tazas

    @Test
    public void accordingMSBlockedHierarchicalShouldNotBlockOtherCups() {
        Tower t = new Tower(10, 20);
        t.pushCup("hierarchical", 3);
        t.pushCup(1);
        t.popCup();
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
        assertEquals("3", t.stackingItems()[0][1]);
    }

    // Escenario 7: OpenerCup entra a torre vacia sin errores

    @Test
    public void accordingMSOpenerCupShouldEnterEmptyTowerWithoutError() {
        Tower t = new Tower(10, 20);
        t.pushCup("opener", 1);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    // Escenario 8: orderTower con tipos nuevos no rompe la torre

    @Test
    public void accordingMSOrderTowerShouldWorkWithNewTypes() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup("opener", 3);
        t.pushCup(2);
        t.orderTower();
        assertTrue(t.ok());
        assertEquals("3", t.stackingItems()[0][1]);
    }

    // Escenario 9: CrazyLid persiste despues de orderTower

    @Test
    public void accordingMSCrazyLidShouldPersistAfterOrderTower() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid("crazy", 3);
        t.orderTower();
        assertTrue(t.ok());
        boolean crazyPresent = false;
        for (String[] item : t.stackingItems()) {
            if (item[0].equals("lid") && item[1].equals("3")) crazyPresent = true;
        }
        assertTrue(crazyPresent);
    }

    // Escenario 10: FearfulLid bloqueada no se quita con popLid en secuencia

    @Test
    public void accordingMSFearfulLidCannotBeRemovedByPopLidWithCupPresent() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("fearful", 1);
        t.popLid();
        assertFalse(t.ok());
        // lid y cup siguen presentes
        assertEquals(2, t.stackingItems().length);
    }

    // Escenario 11: OpenerCup elimina CrazyLid al entrar

    @Test
    public void accordingMSOpenerCupShouldRemoveCrazyLidOnEntry() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("crazy", 2);
        t.pushCup("opener", 3);
        assertTrue(t.ok());
        // Solo deben quedar tazas
        for (String[] item : t.stackingItems()) {
            assertEquals("cup", item[0]);
        }
    }

    // Escenario 12: HierarchicalCup en fondo + CrazyLid en base

    @Test
    public void accordingMSCrazyLidShouldGoBeforeHierarchicalAtIndex0() {
        Tower t = new Tower(10, 20);
        t.pushCup("hierarchical", 4);
        t.pushCup(1);
        t.pushLid("crazy", 2);
        // CrazyLid se inserta en indice 0
        assertEquals("lid", t.stackingItems()[0][0]);
    }

    // Escenario 13: reverseTower con tipos nuevos

    @Test
    public void accordingMSReverseTowerShouldWorkWithOpenerCup() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup("opener", 3);
        t.reverseTower();
        assertTrue(t.ok());
        // Simplemente verificamos que la operacion no rompe la torre
        assertTrue(t.stackingItems().length > 0);
    }

    // Escenario 14: HierarchicalCup desplaza solo menores, respeta iguales

    @Test
    public void accordingMSHierarchicalShouldNotDisplaceCupWithSameNumber() {
        // La misma taza no puede existir dos veces
        Tower t = new Tower(10, 20);
        t.pushCup(3);
        t.pushCup("hierarchical", 3); // duplicado, debe fallar
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    // Escenario 15: FearfulLid desaparece con el popCup de su taza

    @Test
    public void accordingMSPopCupShouldAlsoRemoveFearfulLid() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.pushLid("fearful", 2);
        // removeCup elimina cup2 -> fearfulLid2 puede salir (cup ya no existe)
        t.removeCup(2);
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    // Escenario 16: Torre con todos los tipos + orderTower

    @Test
    public void accordingMSOrderTowerWithAllTypesShouldNotFail() {
        Tower t = new Tower(12, 30);
        t.pushCup(1);
        t.pushCup("hierarchical", 4);
        t.pushCup(2);
        t.pushLid("crazy", 3);
        t.pushCup("opener", 5);
        t.orderTower();
        assertTrue(t.ok());
        // Opener elimino las tapas, orderTower ordena lo que queda
        for (String[] item : t.stackingItems()) {
            assertNotNull(item[0]);
            assertNotNull(item[1]);
        }
    }

    // Escenario 17: FearfulLid entra, cup sale, FearfulLid puede salir

    @Test
    public void accordingMSFearfulLidExitFlowWithCupRemoval() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid("fearful", 2);
        // Cup2 y lid fearful2 en torre. Eliminar cup2 debe permitir lid2 salir sola.
        t.removeCup(2);
        // removeCup elimina cup2 y su tapa asociada
        assertTrue(t.ok());
        // Solo queda cup1
        assertEquals(1, t.stackingItems().length);
        assertEquals("1", t.stackingItems()[0][1]);
    }

    // Escenario 18: Multiples CrazyLids acumuladas en el fondo

    @Test
    public void accordingMSMultipleCrazyLidsShouldAllBePresent() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("crazy", 2);
        t.pushLid("crazy", 3);
        assertTrue(t.ok());
        int lidCount = 0;
        for (String[] item : t.stackingItems()) {
            if (item[0].equals("lid")) lidCount++;
        }
        assertEquals(2, lidCount);
    }

    // Escenario 19: lidedCups refleja FearfulLid cubriendo su taza

    @Test
    public void accordingMSFearfulLidShouldCountInLidedCups() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.pushLid("fearful", 2);
        int[] lided = t.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(2, lided[0]);
    }

    // Escenario 20: CrazyLid no cuenta en lidedCups si no tiene su taza

    @Test
    public void accordingMSCrazyLidWithoutCupShouldNotCountInLidedCups() {
        Tower t = new Tower(10, 20);
        t.pushLid("crazy", 5); // no hay cup5
        assertEquals(0, t.lidedCups().length);
    }
}
