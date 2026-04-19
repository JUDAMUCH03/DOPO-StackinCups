package test;
import tower.Tower;
import tower.TowerContest;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TowerC4Test - Pruebas de unidad para los tipos nuevos del ciclo 4.
 * Todas las pruebas se ejecutan en modo invisible.
 *
 */
public class TowerC4Test {
    

    @Test
    public void accordingMSShouldNotPushFearfulLidWithoutItsCup() {
        Tower t = new Tower(10, 20);
        t.pushLid("fearful", 2);
        assertFalse(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldPushFearfulLidWhenItsCupIsPresent() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.pushLid("fearful", 2);
        assertTrue(t.ok());
        assertEquals(2, t.stackingItems().length);
        assertEquals("lid", t.stackingItems()[1][0]);
        assertEquals("2", t.stackingItems()[1][1]);
    }

    @Test
    public void accordingMSShouldNotRemoveFearfulLidWhileCupIsInTower() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.pushLid("fearful", 2);
        t.removeLid(2);
        assertFalse(t.ok());
        assertEquals(2, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldNotPopFearfulLidWhileCupIsInTower() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.pushLid("fearful", 2);
        t.popLid();
        assertFalse(t.ok());
        assertEquals(2, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldRemoveFearfulLidAfterItsCupIsRemoved() {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        t.pushLid("fearful", 2);
        t.removeCup(2);
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void accordingMSFearfulLidColorShouldBePurple() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("fearful", 1);
        
        String[][] stacking = t.stackingItems();
        assertEquals("lid", stacking[1][0]);
        assertEquals("1", stacking[1][1]);
    }

    @Test
    public void accordingMSFearfulLidCanEnterWithDifferentCupNumbers() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(3);
        t.pushLid("fearful", 1);
        t.pushLid("fearful", 3);
        assertTrue(t.ok());
        assertEquals(4, t.stackingItems().length);
    }

    @Test
    public void accordingMSFearfulLidShouldNotEnterIfOnlyCupWithDifferentNumberExists() {
        Tower t = new Tower(10, 20);
        t.pushCup(1); 
        t.pushLid("fearful", 2);
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSFearfulLidRemainsAfterOtherCupsRemoved() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid("fearful", 1);
        t.removeCup(2); 
        assertTrue(t.ok());
        assertEquals(2, t.stackingItems().length);
    }

    @Test
    public void accordingMSFearfulLidCanBeRemovedAfterPopCupRemovesItsCup() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("fearful", 1);
        // popCup elimina cup1 y su lid
        t.popCup();
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    

    @Test
    public void accordingMSShouldPushCrazyLidAtBottomOfTower() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid("crazy", 3);
        assertTrue(t.ok());
        String[][] stacking = t.stackingItems();
        assertEquals("lid", stacking[0][0]);
        assertEquals("3", stacking[0][1]);
    }

    @Test
    public void accordingMSShouldPushCrazyLidAtBottomEvenWithEmptyTower() {
        Tower t = new Tower(10, 20);
        t.pushLid("crazy", 1);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
        assertEquals("lid", t.stackingItems()[0][0]);
    }

    @Test
    public void accordingMSCrazyLidShouldKeepOtherElementsAbove() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid("crazy", 3);
        String[][] stacking = t.stackingItems();
        assertEquals(3, stacking.length);
        assertEquals("cup", stacking[1][0]);
        assertEquals("cup", stacking[2][0]);
    }

    @Test
    public void accordingMSCrazyLidCanBeRemovedNormally() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("crazy", 2);
        t.removeLid(2);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSCrazyLidCanBePopped() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid("crazy", 3); 
        t.removeLid(3);
        assertTrue(t.ok());
        assertEquals(2, t.stackingItems().length);
    }

    @Test
    public void accordingMSMultipleCrazyLidsGoToBottom() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("crazy", 2);
        t.pushLid("crazy", 3);
        
        String[][] stacking = t.stackingItems();
        assertEquals("lid", stacking[0][0]);
    }

    @Test
    public void accordingMSCrazyLidShouldNotAffectCupCount() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid("crazy", 3);
        long cupCount = 0;
        for (String[] item : t.stackingItems()) {
            if (item[0].equals("cup")) cupCount++;
        }
        assertEquals(2, cupCount);
    }

   

    @Test
    public void accordingMSOpenerCupShouldRemoveAllLidsOnEntry() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        t.pushLid(2);
        t.pushCup("opener", 3);
        assertTrue(t.ok());
        for (String[] item : t.stackingItems()) {
            assertEquals("cup", item[0]);
        }
    }

    @Test
    public void accordingMSOpenerCupShouldEnterEvenWithNoLids() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup("opener", 3);
        assertTrue(t.ok());
        assertEquals(3, t.stackingItems().length);
    }

    @Test
    public void accordingMSOpenerCupShouldBeLastAfterEntry() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        t.pushCup("opener", 2);
        String[][] stacking = t.stackingItems();
        assertEquals("2", stacking[stacking.length - 1][1]);
    }

    @Test
    public void accordingMSOpenerCupShouldLeaveOnlyCupsAfterEntry() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        t.pushLid(2);
        t.pushLid(3);
        t.pushCup("opener", 4);
        assertEquals(2, t.stackingItems().length);
    }

    @Test
    public void accordingMSOpenerCupShouldEnterEmptyTower() {
        Tower t = new Tower(10, 20);
        t.pushCup("opener", 1);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSOpenerCupColorShouldBeCyan() {
        Tower t = new Tower(10, 20);
        t.pushCup("opener", 1);
        assertTrue(t.ok());        
        assertEquals("cup", t.stackingItems()[0][0]);
        assertEquals("1", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSOpenerCupShouldRemoveOnlyLidsNotCups() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        t.pushCup("opener", 3);        
        int cupCount = 0;
        for (String[] item : t.stackingItems()) {
            if (item[0].equals("cup")) cupCount++;
        }
        assertEquals(3, cupCount);
    }

    @Test
    public void accordingMSOpenerCupShouldNotBeDuplicated() {
        Tower t = new Tower(10, 20);
        t.pushCup("opener", 2);
        t.pushCup("opener", 2);
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    

    @Test
    public void accordingMSHierarchicalCupShouldDisplaceSmallerCups() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup("hierarchical", 3);
        String[][] stacking = t.stackingItems();
        int idx3 = -1, idx1 = -1;
        for (int i = 0; i < stacking.length; i++) {
            if (stacking[i][1].equals("3")) idx3 = i;
            if (stacking[i][1].equals("1")) idx1 = i;
        }
        assertTrue(idx3 < idx1);
    }

    @Test
    public void accordingMSHierarchicalCupAtBottomCannotBeRemoved() {
        Tower t = new Tower(10, 20);
        t.pushCup("hierarchical", 3);
        t.removeCup(3);
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSHierarchicalCupNotAtBottomCanBeRemoved() {
        Tower t = new Tower(10, 20);
        t.pushCup(4);
        t.pushCup("hierarchical", 2);
        t.removeCup(2);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSHierarchicalCupAtBottomCannotBePopped() {
        Tower t = new Tower(10, 20);
        t.pushCup("hierarchical", 3);
        t.popCup();
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSHierarchicalCupShouldNotDisplaceLargerCups() {
        Tower t = new Tower(10, 20);
        t.pushCup(4);
        t.pushCup("hierarchical", 2);        
        assertEquals("4", t.stackingItems()[0][1]);
        assertEquals("2", t.stackingItems()[1][1]);
    }

    @Test
    public void accordingMSHierarchicalCupAloneGoesToBottom() {
        Tower t = new Tower(10, 20);
        t.pushCup("hierarchical", 3);
        assertTrue(t.ok());
        assertEquals("3", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSHierarchicalCupCanBeRemovedWhenNotAtBottom() {
        Tower t = new Tower(12, 20);
        t.pushCup(5);
        t.pushCup("hierarchical", 2);
        assertEquals("2", t.stackingItems()[1][1]);        
        t.removeCup(2);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
        assertEquals("5", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSHierarchicalCupShouldDisplaceAllSmallerCups() {
        Tower t = new Tower(12, 30);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.pushCup("hierarchical", 4);        
        assertEquals("4", t.stackingItems()[0][1]);
    }

    

    @Test
    public void accordingMSPushCupWithDefaultTypeShouldWorkLikeNormal() {
        Tower t = new Tower(10, 20);
        t.pushCup("cup", 2);
        assertTrue(t.ok());
        assertEquals("2", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSPushLidWithDefaultTypeShouldWorkLikeNormal() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("lid", 1);
        assertTrue(t.ok());
        assertEquals(2, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldNotPushDuplicateCupWithType() {
        Tower t = new Tower(10, 20);
        t.pushCup("opener", 2);
        t.pushCup("opener", 2);
        assertFalse(t.ok());
        assertEquals(1, t.stackingItems().length);
    }

    @Test
    public void accordingMSShouldNotPushDuplicateLidWithType() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("fearful", 1);
        t.pushLid("fearful", 1);
        assertFalse(t.ok());
        assertEquals(2, t.stackingItems().length);
    }

    @Test
    public void accordingMSPushCupUnknownTypeShouldDefaultToNormal() {
        Tower t = new Tower(10, 20);
        t.pushCup("unknown", 3);
        assertTrue(t.ok());
        assertEquals("cup", t.stackingItems()[0][0]);
        assertEquals("3", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSPushLidUnknownTypeShouldDefaultToNormal() {
        Tower t = new Tower(10, 20);
        t.pushLid("unknown", 2);
        assertTrue(t.ok());
        assertEquals("lid", t.stackingItems()[0][0]);
        assertEquals("2", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSPushCupTypeIsCaseInsensitive() {
        Tower t = new Tower(10, 20);
        t.pushCup("OPENER", 1);
        assertTrue(t.ok());
        assertEquals("1", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSPushLidTypeIsCaseInsensitive() {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid("FEARFUL", 1);
        assertTrue(t.ok());
        assertEquals(2, t.stackingItems().length);
    }
    
    //----------------
    @Test
    public void accordingMSMakeVisibleShouldActivateGraphicsAndMaintainOk() {
        Tower t = new Tower(10, 10);
        t.makeVisible();
        assertTrue(t.ok());
        t.pushCup("normal", 1);
        t.pushLid("normal", 1);
        t.makeInvisible();
        assertTrue(t.ok());
    }

    @Test
    public void accordingMSRedrawShouldExecuteSuccessfullyAfterCover() {
        Tower t = new Tower(20, 50);
        t.makeVisible();
        t.pushCup("normal", 1);
        t.pushLid("normal", 1);
        t.cover();
        assertTrue("La ejecución gráfica o el redibujado falló", t.ok());
        t.makeInvisible();
        assertTrue(t.ok());
    }
    
    @Test
    public void accordingMSTowerContestSimulateShouldExecuteFlow() {
        try {
            TowerContest.simulate(4, 9);
            assertTrue(true);
        } catch (Exception e) {
            fail("La simulación lanzó una excepción inesperada: " + e.getMessage());
        }
    }

    // ---- FragileCup - tipo nuevo propuesto (Ciclo 4) ----

    @Test
    public void accordingMSFragileCupEntersTowerNormally() {
        Tower t = new Tower(10, 20);
        t.pushCup("fragile", 1);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
        assertEquals("cup", t.stackingItems()[0][0]);
        assertEquals("1", t.stackingItems()[0][1]);
    }

    @Test
    public void accordingMSFragileCupSurvivesWithFewerThanThreeItemsAbove() {
        Tower t = new Tower(12, 30);
        t.pushCup("fragile", 1);
        t.pushCup(2);
        t.pushCup(3);
        // 2 elementos encima: no debe romperse
        assertTrue(t.ok());
        assertEquals(3, t.stackingItems().length);
    }

    @Test
    public void accordingMSFragileCupBreaksWithThreeItemsAbove() {
        Tower t = new Tower(14, 40);
        t.pushCup("fragile", 1);
        t.pushCup(2);
        t.pushCup(3);
        t.pushCup(4);
        // 3 elementos encima: la taza fragil debe haberse eliminado automaticamente
        assertEquals(3, t.stackingItems().length);
        // La taza fragil (numero 1) ya no debe estar en la torre
        for (String[] item : t.stackingItems()) {
            assertFalse("FragileCup debe haber sido eliminada", item[1].equals("1"));
        }
    }

    @Test
    public void accordingMSShouldNotBreakFragileCupWithTwoItemsAbove() {
        Tower t = new Tower(12, 30);
        t.pushCup("fragile", 1);
        t.pushCup(2);
        t.pushLid(2);
        // 2 elementos encima (taza2 y tapa2): no debe romperse
        assertEquals(3, t.stackingItems().length);
        boolean fragilePresent = false;
        for (String[] item : t.stackingItems()) {
            if (item[1].equals("1")) fragilePresent = true;
        }
        assertTrue("FragileCup no deberia romperse con solo 2 elementos encima", fragilePresent);
    }
}
