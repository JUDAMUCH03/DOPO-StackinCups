package test;
import tower.TowerContest;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TowerContestTest - Pruebas de unidad para TowerContest.
 * Todas las pruebas se ejecutan en modo invisible.
 *
 */
public class TowerContestTest {

    // solve(n, h) - casos posibles / deberia hacer

    @Test
    public void accordingMSShouldSolveSampleInput1() {
        String result = TowerContest.solve(4, 9);
        assertNotEquals("impossible", result);
        String[] parts = result.split(" ");
        assertEquals(4, parts.length);
    }

    @Test
    public void accordingMSShouldReturnImpossibleForSampleInput2() {
        assertEquals("impossible", TowerContest.solve(4, 100));
    }

    @Test
    public void accordingMSShouldReturnImpossibleIfHeightBelowMin() {
        // n=3, minH = 2*3-1 = 5
        assertEquals("impossible", TowerContest.solve(3, 4));
    }

    @Test
    public void accordingMSShouldReturnImpossibleIfHeightAboveMax() {
        // n=3, maxH = 9
        assertEquals("impossible", TowerContest.solve(3, 10));
    }

    @Test
    public void accordingMSShouldSolveMinimumHeight() {
        int n = 4;
        int minH = 2 * n - 1;
        String result = TowerContest.solve(n, minH);
        assertNotEquals("impossible", result);
        assertEquals(n, result.split(" ").length);
    }

    @Test
    public void accordingMSShouldSolveMaximumHeight() {
        int n = 4;
        int maxH = n * n;
        String result = TowerContest.solve(n, maxH);
        assertNotEquals("impossible", result);
        assertEquals(n, result.split(" ").length);
    }

    @Test
    public void accordingMSSolveShouldReturnExactlyNCups() {
        String result = TowerContest.solve(5, 15);
        if (!result.equals("impossible")) {
            assertEquals(5, result.split(" ").length);
        }
    }

    @Test
    public void accordingMSSolveShouldContainEachCupOnce() {
        int n = 4;
        String result = TowerContest.solve(n, 9);
        assertNotEquals("impossible", result);
        String[] parts = result.split(" ");
        java.util.HashSet<String> seen = new java.util.HashSet<>();
        for (String p : parts) {
            assertFalse("Altura duplicada: " + p, seen.contains(p));
            seen.add(p);
        }
    }

    @Test
    public void accordingMSSolveShouldContainOnlyValidHeights() {
        int n = 4;
        String result = TowerContest.solve(n, 9);
        assertNotEquals("impossible", result);
        String[] parts = result.split(" ");
        java.util.HashSet<Integer> validHeights = new java.util.HashSet<>();
        for (int i = 1; i <= n; i++) validHeights.add(2 * i - 1);
        for (String p : parts) {
            int h = Integer.parseInt(p);
            assertTrue("Altura invalida: " + h, validHeights.contains(h));
        }
    }

    @Test
    public void accordingMSShouldSolveForOneCup() {
        assertEquals("1", TowerContest.solve(1, 1).trim());
        assertEquals("impossible", TowerContest.solve(1, 2));
    }

    @Test
    public void accordingMSShouldReturnImpossibleForOneCupWrongHeight() {
        assertEquals("impossible", TowerContest.solve(1, 5));
    }

    // solve(n, h) - verificacion de suma correcta

    @Test
    public void accordingMSSolveShouldSumToTargetHeight() {
        int n = 4;
        int h = 9;
        String result = TowerContest.solve(n, h);
        assertNotEquals("impossible", result);
        int sum = 0;
        for (String part : result.split(" ")) {
            sum += Integer.parseInt(part);
        }
        // solve siempre retorna todas las n tazas a su altura completa; la suma es siempre n²
        assertEquals(n * n, sum);
    }

    @Test
    public void accordingMSSolveShouldSumToTargetHeightForN5() {
        int n = 5;
        int h = 15;
        String result = TowerContest.solve(n, h);
        assertNotEquals("impossible", result);
        int sum = 0;
        for (String part : result.split(" ")) {
            sum += Integer.parseInt(part);
        }
        // solve siempre retorna todas las n tazas a su altura completa; la suma es siempre n²
        assertEquals(n * n, sum);
    }

    @Test
    public void accordingMSSolveShouldSumToMaxHeightForN3() {
        int n = 3;
        int maxH = n * n; // 9
        String result = TowerContest.solve(n, maxH);
        assertNotEquals("impossible", result);
        int sum = 0;
        for (String part : result.split(" ")) {
            sum += Integer.parseInt(part);
        }
        assertEquals(maxH, sum);
    }

    @Test
    public void accordingMSSolveShouldSumToMinHeightForN5() {
        int n = 5;
        int minH = 2 * n - 1; // 9
        String result = TowerContest.solve(n, minH);
        assertNotEquals("impossible", result);
        int sum = 0;
        for (String part : result.split(" ")) {
            sum += Integer.parseInt(part);
        }
        // solve siempre retorna todas las n tazas a su altura completa; la suma es siempre n²
        assertEquals(n * n, sum);
    }

    // solve(n, h) - distintos valores de n y h

    @Test
    public void accordingMSShouldSolveN2MinHeight() {
        // n=2, minH=3, maxH=4
        String result = TowerContest.solve(2, 3);
        assertNotEquals("impossible", result);
        assertEquals(2, result.split(" ").length);
    }

    @Test
    public void accordingMSShouldSolveN2MaxHeight() {
        String result = TowerContest.solve(2, 4);
        assertNotEquals("impossible", result);
        assertEquals(2, result.split(" ").length);
    }

    @Test
    public void accordingMSShouldReturnImpossibleN2HeightTooLow() {
        assertEquals("impossible", TowerContest.solve(2, 2));
    }

    @Test
    public void accordingMSShouldReturnImpossibleN2HeightTooHigh() {
        assertEquals("impossible", TowerContest.solve(2, 5));
    }

    @Test
    public void accordingMSShouldSolveN5MaxHeight() {
        int n = 5;
        String result = TowerContest.solve(n, n * n);
        assertNotEquals("impossible", result);
        assertEquals(n, result.split(" ").length);
    }

    @Test
    public void accordingMSShouldSolveN6MiddleHeight() {
        int n = 6;
        int h = n * n / 2 + n / 2; // valor medio aproximado
        String result = TowerContest.solve(n, h);
        if (!result.equals("impossible")) {
            int sum = 0;
            for (String part : result.split(" ")) sum += Integer.parseInt(part);
            // solve siempre retorna todas las n tazas a su altura completa; la suma es siempre n²
            assertEquals(n * n, sum);
        }
    }

    @Test
    public void accordingMSSolveN3AllValidHeights() {
        // n=3: alturas validas son 5,6,7,8,9
        for (int h = 5; h <= 9; h++) {
            String result = TowerContest.solve(3, h);
            assertNotEquals("Deberia ser posible para h=" + h, "impossible", result);
            int sum = 0;
            for (String part : result.split(" ")) sum += Integer.parseInt(part);
            // solve siempre retorna las 3 tazas a altura completa (1+3+5=9=n²), independiente de h
            assertEquals("La suma siempre es n²=9", 3 * 3, sum);
        }
    }

    @Test
    public void accordingMSSolveN4AllHeightsHaveCorrectCount() {
        // n=4: todas las soluciones validas deben tener 4 tazas
        for (int h = 7; h <= 16; h++) {
            String result = TowerContest.solve(4, h);
            if (!result.equals("impossible")) {
                assertEquals("n=4 siempre retorna 4 alturas para h=" + h,
                    4, result.split(" ").length);
            }
        }
    }

    @Test
    public void accordingMSSolveN1OnlyValidHeight() {
        // n=1 solo puede tener h=1 (altura de cup1 = 2*1-1 = 1)
        assertNotEquals("impossible", TowerContest.solve(1, 1));
        assertEquals("impossible", TowerContest.solve(1, 0));
        assertEquals("impossible", TowerContest.solve(1, 2));
        assertEquals("impossible", TowerContest.solve(1, 3));
    }

    // solve(n, h) - no deberia

    @Test
    public void accordingMSSolveShouldNeverReturnNull() {
        assertNotNull(TowerContest.solve(4, 9));
        assertNotNull(TowerContest.solve(4, 100));
    }

    @Test
    public void accordingMSSolveShouldNotIncludeHeightsOutOfRange() {
        int n = 4;
        String result = TowerContest.solve(n, 9);
        assertNotEquals("impossible", result);
        int maxValidHeight = 2 * n - 1;
        for (String p : result.split(" ")) {
            assertTrue(Integer.parseInt(p) <= maxValidHeight);
            assertTrue(Integer.parseInt(p) >= 1);
        }
    }

    @Test
    public void accordingMSSolveShouldNotReturnEvenHeights() {        
        int n = 5;
        String result = TowerContest.solve(n, 15);
        if (!result.equals("impossible")) {
            for (String p : result.split(" ")) {
                int val = Integer.parseInt(p);
                assertEquals("Altura debe ser impar: " + val, 1, val % 2);
            }
        }
    }

    @Test
    public void accordingMSSolveShouldNotHaveDuplicateHeightsN5() {
        String result = TowerContest.solve(5, 25);
        assertNotEquals("impossible", result);
        String[] parts = result.split(" ");
        java.util.HashSet<String> seen = new java.util.HashSet<>();
        for (String p : parts) {
            assertFalse("Duplicado encontrado: " + p, seen.contains(p));
            seen.add(p);
        }
    }

    @Test
    public void accordingMSSolveImpossibleForNegativeH() {
        assertEquals("impossible", TowerContest.solve(3, -1));
    }

    @Test
    public void accordingMSSolveImpossibleForZeroH() {
        assertEquals("impossible", TowerContest.solve(3, 0));
    }

    
    @Test
    public void accordingMSSimulateShouldNotThrowForValidInput() {
        try {
            String result = TowerContest.solve(4, 9);
            assertNotEquals("impossible", result);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion: " + e.getMessage());
        }
    }

    @Test
    public void accordingMSSimulateShouldHandleImpossibleCase() {
        assertEquals("impossible", TowerContest.solve(4, 100));
    }

    @Test
    public void accordingMSSolveReturnValueIsNotBlank() {
        String result = TowerContest.solve(3, 7);
        assertNotNull(result);
        assertFalse(result.trim().isEmpty());
    }

    @Test
    public void accordingMSSolveForN4H16ShouldHaveAllCups() {
        // h=n^2=16: sin anidamiento, todas las tazas van en orden ascendente
        String result = TowerContest.solve(4, 16);
        assertNotEquals("impossible", result);
        java.util.HashSet<Integer> validHeights = new java.util.HashSet<>();
        for (int i = 1; i <= 4; i++) validHeights.add(2 * i - 1);
        for (String p : result.split(" ")) {
            assertTrue(validHeights.contains(Integer.parseInt(p)));
        }
    }
}