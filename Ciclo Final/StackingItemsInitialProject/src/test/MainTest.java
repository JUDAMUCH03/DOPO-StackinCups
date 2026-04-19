package test;

import tower.Tower;
import tower.TowerContest;
import tower.Main;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * MainTest - Pruebas de unidad para la clase Main.
 * 
 */
public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    private void setMainTower(Tower t) throws Exception {
        Field field = Main.class.getDeclaredField("tower");
        field.setAccessible(true);
        field.set(null, t);
    }

    private Tower getMainTower() throws Exception {
        Field field = Main.class.getDeclaredField("tower");
        field.setAccessible(true);
        return (Tower) field.get(null);
    }

    private Object invokeStatic(String methodName) throws Exception {
        Method m = Main.class.getDeclaredMethod(methodName);
        m.setAccessible(true);
        return m.invoke(null);
    }

    // checkTower devuelve false si la torre es null
    @Test
    public void accordingMSCheckTowerReturnsFalseWhenTowerIsNull() throws Exception {
        setMainTower(null);
        Method m = Main.class.getDeclaredMethod("checkTower");
        m.setAccessible(true);
        boolean result = (boolean) m.invoke(null);
        assertFalse(result);
    }

    // checkTower devuelve true si hay una torre activa
    @Test
    public void accordingMSCheckTowerReturnsTrueWhenTowerIsSet() throws Exception {
        setMainTower(new Tower(10, 20));
        Method m = Main.class.getDeclaredMethod("checkTower");
        m.setAccessible(true);
        boolean result = (boolean) m.invoke(null);
        assertTrue(result);
    }

    // checkTower imprime aviso cuando la torre es null
    @Test
    public void accordingMSCheckTowerPrintsMessageWhenNull() throws Exception {
        setMainTower(null);
        invokeStatic("checkTower");
        assertTrue(outContent.toString().contains("Primero crea una torre"));
    }

    // estadoActual imprime "ninguna creada" si no hay torre
    @Test
    public void accordingMSEstadoActualPrintsNingunaCreadaWhenNull() throws Exception {
        setMainTower(null);
        invokeStatic("estadoActual");
        assertTrue(outContent.toString().contains("ninguna creada"));
    }

    // estadoActual imprime datos de la torre cuando existe
    @Test
    public void accordingMSEstadoActualPrintsTowerInfoWhenSet() throws Exception {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        setMainTower(t);
        invokeStatic("estadoActual");
        String output = outContent.toString();
        assertTrue(output.contains("Torre activa"));
        assertTrue(output.contains("altura"));
        assertTrue(output.contains("items"));
    }

    // estadoActual muestra 0 items en torre vacia
    @Test
    public void accordingMSEstadoActualWithEmptyTowerShowsZeroItems() throws Exception {
        setMainTower(new Tower(10, 20));
        invokeStatic("estadoActual");
        assertTrue(outContent.toString().contains("items=0"));
    }

    // estadoActual muestra el conteo correcto de items
    @Test
    public void accordingMSEstadoActualWithCupsShowsCorrectItemCount() throws Exception {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        setMainTower(t);
        invokeStatic("estadoActual");
        assertTrue(outContent.toString().contains("items=2"));
    }

    // printMenu contiene las secciones principales del menu
    @Test
    public void accordingMSPrintMenuContainsMainOptions() throws Exception {
        setMainTower(null);
        invokeStatic("printMenu");
        String output = outContent.toString();
        assertTrue(output.contains("CREAR TORRE"));
        assertTrue(output.contains("TAZAS"));
        assertTrue(output.contains("TAPAS"));
        assertTrue(output.contains("OPERACIONES"));
        assertTrue(output.contains("CONSULTAS"));
        assertTrue(output.contains("VISUALIZACION"));
    }

    // printMenu contiene la seccion TowerContest y Demo
    @Test
    public void accordingMSPrintMenuContainsTowerContestSection() throws Exception {
        setMainTower(null);
        invokeStatic("printMenu");
        String output = outContent.toString();
        assertTrue(output.contains("TOWER CONTEST"));
        assertTrue(output.contains("DEMO"));
        assertTrue(output.contains("Salir"));
    }

    // printEstado imprime items, altura y ok
    @Test
    public void accordingMSPrintEstadoPrintsItemsAlturaOk() throws Exception {
        Tower t = new Tower(10, 20);
        t.pushCup(2);
        setMainTower(t);
        invokeStatic("printEstado");
        String output = outContent.toString();
        assertTrue(output.contains("Items"));
        assertTrue(output.contains("Altura"));
        assertTrue(output.contains("ok"));
    }

    // printEstado imprime la informacion de tapas
    @Test
    public void accordingMSPrintEstadoPrintsTapadas() throws Exception {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushLid(1);
        setMainTower(t);
        invokeStatic("printEstado");
        assertTrue(outContent.toString().contains("Tapadas"));
    }

    // demoAutomatica se ejecuta sin lanzar excepcion
    @Test
    public void accordingMSDemoAutomaticaRunsWithoutException() throws Exception {
        try {
            invokeStatic("demoAutomatica");
        } catch (Exception e) {
            fail("demoAutomatica no debe lanzar excepcion: " + e.getMessage());
        }
    }

    // demoAutomatica imprime el encabezado de la demo
    @Test
    public void accordingMSDemoAutomaticaPrintsDemoHeader() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("DEMO AUTOMATICA"));
    }

    // demoAutomatica indica que crea la torre con ancho 14
    @Test
    public void accordingMSDemoAutomaticaCreatesTowerWith14Width() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("Tower(14, 50)"));
    }

    // demoAutomatica agrega tazas normales
    @Test
    public void accordingMSDemoAutomaticaPushesNormalCups() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("pushCup normal"));
    }

    // demoAutomatica agrega tapas normales
    @Test
    public void accordingMSDemoAutomaticaPushesNormalLids() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("pushLid normal"));
    }

    // demoAutomatica ejecuta cover()
    @Test
    public void accordingMSDemoAutomaticaTestsCover() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("cover()"));
    }

    // demoAutomatica prueba FearfulLid
    @Test
    public void accordingMSDemoAutomaticaTestsFearfulLid() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("fearful"));
    }

    // demoAutomatica prueba CrazyLid
    @Test
    public void accordingMSDemoAutomaticaTestsCrazyLid() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("crazy"));
    }

    // demoAutomatica prueba HierarchicalCup
    @Test
    public void accordingMSDemoAutomaticaTestsHierarchicalCup() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("hierarchical"));
    }

    // demoAutomatica prueba FragileCup
    @Test
    public void accordingMSDemoAutomaticaTestsFragileCup() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("fragile"));
    }

    // demoAutomatica prueba OpenerCup
    @Test
    public void accordingMSDemoAutomaticaTestsOpenerCup() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("opener"));
    }

    // demoAutomatica ejecuta orderTower()
    @Test
    public void accordingMSDemoAutomaticaTestsOrderTower() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("orderTower()"));
    }

    // demoAutomatica ejecuta swapToReduce()
    @Test
    public void accordingMSDemoAutomaticaTestsSwapToReduce() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("swapToReduce()"));
    }

    // demoAutomatica ejecuta reverseTower()
    @Test
    public void accordingMSDemoAutomaticaTestsReverseTower() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("reverseTower()"));
    }

    // demoAutomatica invoca TowerContest.solve
    @Test
    public void accordingMSDemoAutomaticaTestsTowerContest() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("TowerContest.solve"));
    }

    // demoAutomatica imprime el mensaje de finalizacion
    @Test
    public void accordingMSDemoAutomaticaPrintsFinalizedMessage() throws Exception {
        invokeStatic("demoAutomatica");
        assertTrue(outContent.toString().contains("Demo finalizada"));
    }

    // demoAutomatica deja una torre activa en el campo estatico
    @Test
    public void accordingMSDemoAutomaticaSetsTowerAfterExecution() throws Exception {
        setMainTower(null);
        invokeStatic("demoAutomatica");
        assertNotNull(getMainTower());
    }

    // la torre creada por demoAutomatica tiene un arreglo de items valido
    @Test
    public void accordingMSDemoAutomaticaTowerHasItemsAfterExecution() throws Exception {
        setMainTower(null);
        invokeStatic("demoAutomatica");
        assertNotNull(getMainTower().stackingItems());
    }

    // los resultados de TowerContest usados en la demo son correctos
    @Test
    public void accordingMSDemoAutomaticaTowerContestSolveIsCorrect() {
        String r1 = TowerContest.solve(4, 9);
        String r2 = TowerContest.solve(4, 7);
        String r3 = TowerContest.solve(4, 100);
        String r4 = TowerContest.solve(3, 5);
        String r5 = TowerContest.solve(3, 9);
        assertNotEquals("impossible", r1);
        assertNotEquals("impossible", r2);
        assertEquals("impossible", r3);
        assertNotEquals("impossible", r4);
        assertNotEquals("impossible", r5);
    }

    // checkTower devuelve true con una torre recien creada y vacia
    @Test
    public void accordingMSCheckTowerWithEmptyTowerReturnsTrue() throws Exception {
        setMainTower(new Tower(10, 20));
        Method m = Main.class.getDeclaredMethod("checkTower");
        m.setAccessible(true);
        assertTrue((boolean) m.invoke(null));
    }

    // checkTower devuelve true cuando la torre tiene elementos
    @Test
    public void accordingMSCheckTowerWithPopulatedTowerReturnsTrue() throws Exception {
        Tower t = new Tower(10, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        setMainTower(t);
        Method m = Main.class.getDeclaredMethod("checkTower");
        m.setAccessible(true);
        assertTrue((boolean) m.invoke(null));
    }
}
