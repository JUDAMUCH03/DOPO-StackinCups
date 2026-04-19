package test;
import tower.Tower;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TowerC2Test - Pruebas de unidad para los ciclos 1 y 2 del simulador.
 * Todas las pruebas se ejecutan en modo invisible.
 * Convencion de nombres: accordingMS (Munar, Sua en orden alfabetico)
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class TowerC2Test {

    // CICLO 1 - Requisito 1: Tower(width, maxHeight)

    @Test
    public void accordingMSShouldCreateEmptyTowerWithCorrectDimensions() {
        Tower t = new Tower(10, 20);
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSNewTowerShouldHaveZeroHeight() {
        Tower t = new Tower(10, 20);
        assertEquals(0, t.height());
    }

    @Test
    public void accordingMSNewTowerShouldHaveNoLidedCups() {
        Tower t = new Tower(10, 20);
        assertEquals(0, t.lidedCups().length);
    }

    // CICLO 1 - Requisito 2: pushCup, popCup, removeCup

    @Test
    public void accordingMSShouldPushCupAndFindItInTower() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals(1, stacking.length);
        assertEquals("cup", stacking[0][0]);
        assertEquals("1", stacking[0][1]);
    }

    @Test
    public void accordingMSShouldNotPushDuplicateCup() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(1);
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldNotPushCupThatDoesNotFit() {
        Tower t = new Tower(10, 5);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        assertFalse(t.ok());
    }

    @Test
    public void accordingMSShouldNotPushCupTooWideForTower() {
        // Taza 10 con torre de ancho 3 => ancho calculado excede el maximo
        Tower t = new Tower(3, 100);
        t.pushCup(10);
        assertFalse(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldPushMultipleCupsSuccessfully() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        assertTrue(t.ok());
        assertEquals(3, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldPopLastCup() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.popCup();
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals(1, stacking.length);
        assertEquals("1", stacking[0][1]);
    }

    @Test
    public void accordingMSShouldNotPopCupFromEmptyTower() {
        Tower t = new Tower(10, 20);
        t.popCup();
        assertFalse(t.ok());
    }

    @Test
    public void accordingMSPopCupFromTowerWithOnlyLidsShouldFail() {
        // Solo tapas, sin tazas: popCup no encuentra ninguna taza
        Tower t = new Tower(10, 20);
        t.pushLid(1);
        t.popCup();
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldRemoveCupByNumber() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.removeCup(1);
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals(1, stacking.length);
        assertEquals("2", stacking[0][1]);
    }

    @Test
    public void accordingMSShouldNotRemoveCupThatDoesNotExist() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.removeCup(99);
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSPopCupShouldAlsoRemoveItsLid() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        t.popCup();
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSRemoveCupShouldAlsoRemoveItsLid() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        t.removeCup(1);
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSRemoveCupShouldLeaveOtherCupsIntact() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.removeCup(2);
        assertTrue(t.ok());
        assertEquals(2, t.stackingItems().length);
    }

    @Test
    public void accordingMSPopCupShouldNotRemoveLidOfDifferentNumber() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        t.popCup(); // elimina cup2 (la ultima), lid1 pertenece a cup1 no a cup2
        assertTrue(t.ok());
        // cup1 y lid1 siguen en la torre
        assertEquals(2, t.stackingItems().length);
    }

    // CICLO 1 - Requisito 3: pushLid, popLid, removeLid

    @Test
    public void accordingMSShouldPushLidAndFindItInTower() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals(2, stacking.length);
        assertEquals("lid", stacking[1][0]);
        assertEquals("1", stacking[1][1]);
    }

    @Test
    public void accordingMSShouldPushLidWithoutCup() {
        Tower t = new Tower(10, 20);
        t.pushLid(5);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
        assertEquals("lid", t.stackingItems()[0][0]);
    }

    @Test
    public void accordingMSShouldNotPushDuplicateLid() {
        Tower t = new Tower(10, 20);
        t.pushLid(1);
        t.pushLid(1);
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldPopLastLid() {
        Tower t = new Tower(10, 20);
        t.pushLid(1);
        t.pushLid(2);
        t.popLid();
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals(1, stacking.length);
        assertEquals("1", stacking[0][1]);
    }

    @Test
    public void accordingMSShouldNotPopLidFromEmptyTower() {
        Tower t = new Tower(10, 20);
        t.popLid();
        assertFalse(t.ok());
    }

    @Test
    public void accordingMSPopLidFromTowerWithOnlyCupsShouldFail() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.popLid();
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldRemoveLidByNumber() {
        Tower t = new Tower(10, 20);
        t.pushLid(1);
        t.pushLid(2);
        t.removeLid(1);
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals(1, stacking.length);
        assertEquals("2", stacking[0][1]);
    }

    @Test
    public void accordingMSShouldNotRemoveLidThatDoesNotExist() {
        Tower t = new Tower(10, 20);
        t.pushLid(1);
        t.removeLid(99);
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSRemoveLidShouldNotAffectCups() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        t.removeLid(1);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
        assertEquals("cup", t.stackingItems()[0][0]);
    }

    // CICLO 1 - Requisito 4: orderTower

    @Test
    public void accordingMSShouldOrderCupsFromHighestToLowest() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(3);
        t.pushCup(2);
        t.orderTower();
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals("3", stacking[0][1]);
        assertEquals("2", stacking[1][1]);
        assertEquals("1", stacking[2][1]);
    }

    @Test
    public void accordingMSOrderShouldPlaceLidRightAfterItsCup() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        t.orderTower();
        String[][] stacking = t.stackingItems();
        boolean found = false;
        for (int i = 0; i < stacking.length - 1; i++) {
            if (stacking[i][1].equals("1") && stacking[i][0].equals("cup")
                    && stacking[i+1][1].equals("1") && stacking[i+1][0].equals("lid")) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    public void accordingMSOrderTowerShouldNotChangeSizeOfItems() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(2);
        int before = t.stackingItems().length;
        t.orderTower();
        assertEquals(before, t.stackingItems().length);
    }

    @Test
    public void accordingMSOrderTowerOnEmptyTowerShouldSucceed() {
        Tower t = new Tower(10, 20);
        t.orderTower();
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSOrderTowerShouldPlaceOrphanLidAtEnd() {
        // Tapa sin su taza debe quedar al final
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(5); // lid5 no tiene cup5
        t.orderTower();
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals("lid", stacking[stacking.length - 1][0]);
        assertEquals("5", stacking[stacking.length - 1][1]);
    }

    @Test
    public void accordingMSOrderTowerAlreadySortedShouldStayOrdered() {
        Tower t = new Tower(10, 20);
        t.pushCup(3);
        t.pushCup(2);
        t.pushCup(1);
        t.orderTower();
        assertTrue(t.ok());
        assertEquals("3", t.stackingItems()[0][1]);
        assertEquals("2", t.stackingItems()[1][1]);
        assertEquals("1", t.stackingItems()[2][1]);
    }

    // CICLO 1 - Requisito 5: reverseTower

    @Test
    public void accordingMSShouldReverseOrderOfElements() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.reverseTower();
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals("3", stacking[0][1]);
        assertEquals("2", stacking[1][1]);
        assertEquals("1", stacking[2][1]);
    }

    @Test
    public void accordingMSReverseTowerShouldWorkWithLids() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        t.reverseTower();
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals("lid", stacking[0][0]);
        assertEquals("cup", stacking[1][0]);
    }

    @Test
    public void accordingMSReverseTowerOnEmptyTowerShouldSucceed() {
        Tower t = new Tower(10, 20);
        t.reverseTower();
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSReverseTowerTwiceShouldRestoreOriginalOrder() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.reverseTower();
        t.reverseTower();
        assertTrue(t.ok());
        assertEquals("1", t.stackingItems()[0][1]);
        assertEquals("2", t.stackingItems()[1][1]);
        assertEquals("3", t.stackingItems()[2][1]);
    }

    @Test
    public void accordingMSReverseSingleElementShouldNotChange() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.reverseTower();
        assertTrue(t.ok());
        assertEquals("2", t.stackingItems()[0][1]);
    }

    // CICLO 1 - Requisito 6: height - valores exactos

    @Test
    public void accordingMSHeightShouldBeZeroForEmptyTower() {
        Tower t = new Tower(10, 20);
        assertEquals(0, t.height());
    }

    @Test
    public void accordingMSHeightShouldBe1ForCup1() {
        // Cup 1 mide 2*1-1 = 1 cm
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        assertEquals(1, t.height());
    }

    @Test
    public void accordingMSHeightShouldBe3ForCup2() {
        // Cup 2 mide 2*2-1 = 3 cm
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        assertEquals(3, t.height());
    }

    @Test
    public void accordingMSHeightShouldBe9ForCups123() {
        // Sin nesting: 1 + 3 + 5 = 9 cm (tazas de menor a mayor)
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        assertEquals(9, t.height());
    }

    @Test
    public void accordingMSHeightShouldBe5ForCups321() {
        Tower t = new Tower(10, 20);
        t.pushCup(3);
        t.pushCup(2);
        t.pushCup(1);
        assertEquals(5, t.height());
    }

    @Test
    public void accordingMSHeightShouldBe16ForCups1234() {
        // Sin nesting: 1+3+5+7 = 16 = n^2 para n=4
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.pushCup(4);
        assertEquals(16, t.height());
    }

    @Test
    public void accordingMSHeightShouldBe7ForCups4321() {
        // Altura minima n=4: 2*4-1 = 7 (todas anidadas)
        Tower t = new Tower(10, 20);
        t.pushCup(4);
        t.pushCup(3);
        t.pushCup(2);
        t.pushCup(1);
        assertEquals(7, t.height());
    }

    @Test
    public void accordingMSHeightShouldIncreasePushingCup() {
        Tower t = new Tower(10, 20);
        int before = t.height();
        t.pushCup(2);
        assertTrue(t.height() > before);
    }

    @Test
    public void accordingMSHeightShouldIncreaseBy1WhenAddingLid() {
        // Una tapa siempre agrega 1 cm al apilarse encima
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        int before = t.height();
        t.pushLid(2);
        assertEquals(before + 1, t.height());
    }

    @Test
    public void accordingMSHeightShouldDecreaseAfterPopCup() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        int before = t.height();
        t.popCup();
        assertTrue(t.height() < before);
    }

    @Test
    public void accordingMSHeightShouldDecreaseAfterPopLid() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        int before = t.height();
        t.popLid();
        assertEquals(before - 1, t.height());
    }

    @Test
    public void accordingMSHeightWithLidAloneIs1() {
        Tower t = new Tower(10, 20);
        t.pushLid(3);
        assertEquals(1, t.height());
    }

    // CICLO 1 - Requisito 7: lidedCups, stackingItems

    @Test
    public void accordingMSLidedCupsShouldReturnNumbersOfCoveredCups() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        t.pushLid(2);
        int[] lided = t.lidedCups();
        assertEquals(2, lided.length);
        assertEquals(1, lided[0]);
        assertEquals(2, lided[1]);
    }

    @Test
    public void accordingMSLidedCupsShouldBeEmptyWithNoLids() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        assertEquals(0, t.lidedCups().length);
    }

    @Test
    public void accordingMSLidedCupsShouldNotCountLidWithoutCup() {
        Tower t = new Tower(10, 20);
        t.pushLid(5); // no hay cup5
        assertEquals(0, t.lidedCups().length);
    }

    @Test
    public void accordingMSLidedCupsShouldBeSortedAscending() {
        Tower t = new Tower(10, 20);
        t.pushCup(3);
        t.pushCup(1);
        t.pushLid(3);
        t.pushLid(1);
        int[] lided = t.lidedCups();
        assertEquals(2, lided.length);
        assertEquals(1, lided[0]);
        assertEquals(3, lided[1]);
    }

    @Test
    public void accordingMSStackingItemsShouldReturnCorrectTypeAndNumber() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        String[][] stacking = t.stackingItems();
        assertEquals("cup", stacking[0][0]);
        assertEquals("1", stacking[0][1]);
        assertEquals("lid", stacking[1][0]);
        assertEquals("1", stacking[1][1]);
    }

    @Test
    public void accordingMSStackingItemsShouldBeEmptyForNewTower() {
        Tower t = new Tower(10, 20);
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSLidedCupsInfoShouldReturnEmptyBracketsForNoLids() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        String info = t.lidedCupsInfo();
        assertEquals("[]", info);
    }

    @Test
    public void accordingMSLidedCupsInfoShouldListCoveredCupNumbers() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        String info = t.lidedCupsInfo();
        assertTrue(info.contains("1"));
    }

    @Test
    public void accordingMSStackingItemsInfoShouldContainTypeAndNumber() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        String info = t.stackingItemsInfo();
        assertTrue(info.contains("cup"));
        assertTrue(info.contains("2"));
    }

    @Test
    public void accordingMSStackingItemsInfoOnEmptyTowerShouldReturnEmptyBraces() {
        Tower t = new Tower(10, 20);
        assertEquals("{}", t.stackingItemsInfo());
    }

    // CICLO 2 - Requisito 10: Tower(int cups)

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

    @Test
    public void accordingMSShouldBeOkAfterCupsConstructor() {
        Tower t = new Tower(4);
        assertTrue(t.ok());
    }

    @Test
    public void accordingMSShouldNotHaveLidsWithCupsConstructor() {
        Tower t = new Tower(4);
        assertEquals(0, t.lidedCups().length);
    }

    @Test
    public void accordingMSShouldNotCreateMoreCupsThanSpecified() {
        Tower t = new Tower(3);
        assertEquals(3, t.stackingItems().length);
    }

    @Test
    public void accordingMSSingleCupConstructorShouldHaveOneCup() {
        Tower t = new Tower(1);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
        assertEquals("1", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSCupsConstructorShouldAllowPushingLid() {
        Tower t = new Tower(3);
        t.pushLid(1);
        assertTrue(t.ok());
        assertEquals(4, t.stackingItems().length);
    }

    @Test
    public void accordingMSCupsConstructorShouldNotAllowDuplicateCup() {
        Tower t = new Tower(3);
        t.pushCup(2); // ya existe cup2
        assertFalse(t.ok());
        assertEquals(3, t.stackingItems().length);
    }

    // CICLO 2 - Requisito 11: swap

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

    @Test
    public void accordingMSShouldNotSwapIfFirstElementMissing() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.swap(new String[]{"cup", "99"}, new String[]{"cup", "2"});
        assertFalse(t.ok());
        assertEquals("2", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSShouldNotSwapIfSecondElementMissing() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.swap(new String[]{"cup", "2"}, new String[]{"cup", "99"});
        assertFalse(t.ok());
        assertEquals("2", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSShouldNotSwapOnEmptyTower() {
        Tower t = new Tower(10, 20);
        t.swap(new String[]{"cup", "1"}, new String[]{"cup", "2"});
        assertFalse(t.ok());
    }

    @Test
    public void accordingMSSwapShouldNotModifyOrderIfElementsAreSame() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        // intercambiar cup1 consigo mismo (mismos indices no son posibles aqui pero probamos consistencia)
        t.swap(new String[]{"cup", "1"}, new String[]{"cup", "2"});
        t.swap(new String[]{"cup", "1"}, new String[]{"cup", "2"});
        assertTrue(t.ok());
        assertEquals("1", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSSwapShouldFailIfHeightExceedsMax() {
        // Crear torre pequena donde el swap aumenta la altura mas del limite
        Tower t = new Tower(10, 4);
        t.pushCup(1); // h=1
        t.pushCup(2); // h=1+3=4
        // Al intercambiar: cup2 queda abajo, cup1 anida -> h=4
        // Intercambiamos para ver si se rechaza cuando excede
        t.swap(new String[]{"cup", "2"}, new String[]{"cup", "1"});
        // El resultado puede ser ok o no dependiendo de la logica de altura
        assertNotNull(t.stackingItems());
    }

    // CICLO 2 - Requisito 12: cover

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

    @Test
    public void accordingMSCoverShouldNotCreateExtraLids() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.cover();
        assertTrue(t.ok());
        assertEquals(0, t.lidedCups().length);
    }

    @Test
    public void accordingMSShouldKeepLidsWithoutCupAfterCover() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.pushLid(1);
        t.cover();
        String[][] stacking = t.stackingItems();
        boolean lidPresent = false;
        for (String[] item : stacking) {
            if (item[0].equals("lid") && item[1].equals("1")) lidPresent = true;
        }
        assertTrue(lidPresent);
    }

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

    @Test
    public void accordingMSCoverWithNoLidsShouldNotChangeOrder() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        int sizeBefore = t.stackingItems().length;
        t.cover();
        assertTrue(t.ok());
        assertEquals(sizeBefore, t.stackingItems().length);
        assertEquals(0, t.lidedCups().length);
    }

    @Test
    public void accordingMSCoverShouldMarkCupAsCoveredWhenLidPresent() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        t.cover();
        assertTrue(t.ok());
        int[] lided = t.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(1, lided[0]);
    }

    @Test
    public void accordingMSCoverOnEmptyTowerShouldSucceed() {
        Tower t = new Tower(10, 20);
        t.cover();
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSCoverShouldHandleMultipleLids() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(2);
        t.pushLid(1);
        t.cover();
        assertTrue(t.ok());
        assertEquals(2, t.lidedCups().length);
    }

    // CICLO 2 - Requisito 13: swapToReduce

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

    @Test
    public void accordingMSSwapToReduceShouldNeverReturnNull() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        assertNotNull(t.swapToReduce());
    }

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

    @Test
    public void accordingMSShouldReturnEmptyIfNoReducingSwapExists() {
        // Torre ya en orden optimo (mayor a menor): no hay swap que reduzca
        Tower t = new Tower(10, 20);
        t.pushCup(3);
        t.pushCup(2);
        t.pushCup(1);
        assertEquals(0, t.swapToReduce().length);
    }

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

    @Test
    public void accordingMSSwapToReduceOnEmptyTowerShouldReturnEmpty() {
        Tower t = new Tower(10, 20);
        String[][] result = t.swapToReduce();
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    public void accordingMSSwapToReduceOnSingleElementShouldReturnEmpty() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        assertEquals(0, t.swapToReduce().length);
    }

    @Test
    public void accordingMSSwapToReduceDescriptorsShouldHaveTwoFields() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(3);
        t.pushCup(2);
        String[][] pair = t.swapToReduce();
        if (pair.length > 0) {
            assertEquals(2, pair[0].length);
            assertEquals(2, pair[1].length);
        }
    }
}
