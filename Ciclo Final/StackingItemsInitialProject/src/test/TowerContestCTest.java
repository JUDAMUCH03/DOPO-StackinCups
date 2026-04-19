package test;
import tower.TowerContest;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * TowerContestCTest - Pruebas colectivas de unidad para la clase TowerContest.
 * Complementa TowerContestTest con casos de borde y verificaciones de contrato.
 * Convencion de nombres: accordingMS (Munar, Sua en orden alfabetico)
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class TowerContestCTest {

    // ---- Verificacion de rango valido ----

    @Test
    public void accordingMSSolveShouldReturnImpossibleBelowMinForN3() {
        // n=3: minH=5, cualquier h<5 es imposible
        assertEquals("impossible", TowerContest.solve(3, 4));
        assertEquals("impossible", TowerContest.solve(3, 0));
        assertEquals("impossible", TowerContest.solve(3, -1));
    }

    @Test
    public void accordingMSSolveShouldReturnImpossibleAboveMaxForN3() {
        // n=3: maxH=9, cualquier h>9 es imposible
        assertEquals("impossible", TowerContest.solve(3, 10));
        assertEquals("impossible", TowerContest.solve(3, 100));
    }

    @Test
    public void accordingMSSolveShouldHandleAllValidHeightsForN3() {
        // n=3: alturas validas 5..9, todas deben tener solucion con 3 alturas
        for (int h = 5; h <= 9; h++) {
            String result = TowerContest.solve(3, h);
            assertNotEquals("n=3 h=" + h + " debe tener solucion", "impossible", result);
            String[] parts = result.split(" ");
            assertEquals("n=3 siempre retorna 3 alturas, h=" + h, 3, parts.length);
        }
    }

    @Test
    public void accordingMSSolveShouldHandleAllValidHeightsForN4() {
        // n=4: alturas validas 7..16, todas deben tener solucion con 4 alturas
        for (int h = 7; h <= 16; h++) {
            String result = TowerContest.solve(4, h);
            assertNotEquals("n=4 h=" + h + " debe tener solucion", "impossible", result);
            String[] parts = result.split(" ");
            assertEquals("n=4 siempre retorna 4 alturas, h=" + h, 4, parts.length);
        }
    }

    // ---- Verificacion del caso especial n^2 - 2 ----

    @Test
    public void accordingMSSolveShouldHandleGapCaseN3H7() {
        // Caso borde critico: n=3, h=7 = 3^2 - 2
        String result = TowerContest.solve(3, 7);
        assertNotEquals("n=3 h=7 debe ser posible", "impossible", result);
        assertEquals("debe retornar 3 alturas", 3, result.split(" ").length);
    }

    @Test
    public void accordingMSSolveShouldHandleGapCaseN4H14() {
        // Caso borde critico: n=4, h=14 = 4^2 - 2
        String result = TowerContest.solve(4, 14);
        assertNotEquals("n=4 h=14 debe ser posible", "impossible", result);
        assertEquals("debe retornar 4 alturas", 4, result.split(" ").length);
    }

    @Test
    public void accordingMSSolveShouldHandleGapCaseN5H23() {
        // Caso borde critico: n=5, h=23 = 5^2 - 2
        String result = TowerContest.solve(5, 23);
        assertNotEquals("n=5 h=23 debe ser posible", "impossible", result);
        assertEquals("debe retornar 5 alturas", 5, result.split(" ").length);
    }

    // ---- Verificacion de contenido de la solucion ----

    @Test
    public void accordingMSSolveShouldUseEachCupOnce() {
        // Las alturas retornadas deben ser distintas (cada taza usada exactamente una vez)
        int n = 5;
        for (int h = 2 * n - 1; h <= n * n; h++) {
            String result = TowerContest.solve(n, h);
            if (!result.equals("impossible")) {
                java.util.HashSet<String> seen = new java.util.HashSet<>();
                for (String part : result.split(" ")) {
                    assertFalse("Altura duplicada en h=" + h + ": " + part, seen.contains(part));
                    seen.add(part);
                }
            }
        }
    }

    @Test
    public void accordingMSSolveShouldReturnOnlyValidCupHeights() {
        // Cada altura retornada debe ser de la forma 2i-1 con 1<=i<=n
        int n = 4;
        java.util.HashSet<Integer> valid = new java.util.HashSet<>();
        for (int i = 1; i <= n; i++) valid.add(2 * i - 1);

        for (int h = 7; h <= 16; h++) {
            String result = TowerContest.solve(n, h);
            assertNotEquals("impossible", result);
            for (String part : result.split(" ")) {
                int val = Integer.parseInt(part);
                assertTrue("Altura invalida " + val + " para n=" + n, valid.contains(val));
            }
        }
    }

    @Test
    public void accordingMSSolveForN1OnlyH1IsValid() {
        assertNotEquals("impossible", TowerContest.solve(1, 1));
        assertEquals("impossible", TowerContest.solve(1, 0));
        assertEquals("impossible", TowerContest.solve(1, 2));
    }

    @Test
    public void accordingMSSolveNeverReturnsNull() {
        assertNotNull(TowerContest.solve(1, 1));
        assertNotNull(TowerContest.solve(3, 7));
        assertNotNull(TowerContest.solve(4, 14));
        assertNotNull(TowerContest.solve(4, 100));
    }

    @Test
    public void accordingMSSolveReturnValueIsNotBlankForValidInput() {
        String result = TowerContest.solve(4, 9);
        assertNotNull(result);
        assertFalse("El resultado no debe ser vacio para entrada valida",
                result.trim().isEmpty());
    }

    // ---- Verificacion de n=2 ----

    @Test
    public void accordingMSSolveN2CoversFullRange() {
        // n=2: rango [3,4]
        assertNotEquals("impossible", TowerContest.solve(2, 3));
        assertNotEquals("impossible", TowerContest.solve(2, 4));
        assertEquals("impossible", TowerContest.solve(2, 2));
        assertEquals("impossible", TowerContest.solve(2, 5));
    }
}
