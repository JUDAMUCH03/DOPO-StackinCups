package tower;

import java.util.Scanner;

/**
 * Main - Menu interactivo para probar Tower y TowerContest.
 * Permite crear una torre, agregar/quitar tazas y tapas de distintos tipos,
 * ejecutar operaciones de reordenamiento y resolver el problema de la maraton.
 *
 */
public class Main {

    private static Tower tower = null;
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Simulador de Tazas - StackingItems");


        boolean running = true;
        while (running) {
            printMenu();
            int option = readInt("Opcion: ");
            System.out.println();
            switch (option) {
                // ── Crear torre ─────────────────────────────────────────
                case 1:  crearTorreManual();      break;
                case 2:  crearTorreCups();         break;

                // ── Tazas ────────────────────────────────────────────────
                case 3:  pushCup();                break;
                case 4:  popCup();                 break;
                case 5:  removeCup();              break;

                // ── Tapas ────────────────────────────────────────────────
                case 6:  pushLid();                break;
                case 7:  popLid();                 break;
                case 8:  removeLid();              break;

                // ── Operaciones ──────────────────────────────────────────
                case 9:  cover();                  break;
                case 10: swap();                   break;
                case 11: swapToReduce();           break;
                case 12: orderTower();             break;
                case 13: reverseTower();           break;

                // ── Consultas ────────────────────────────────────────────
                case 14: consultar();              break;

                // ── Visualizacion ────────────────────────────────────────
                case 15: makeVisible();            break;
                case 16: makeInvisible();          break;

                // ── TowerContest ─────────────────────────────────────────
                case 17: contestSolve();           break;
                case 18: contestSimulate();        break;

                // ── Demo automatica ──────────────────────────────────────
                case 19: demoAutomatica();         break;

                case 0:
                    System.out.println("Hasta luego!");
                    running = false;
                    break;

                default:
                    System.out.println("[!] Opcion invalida.");
            }
        }
        SCANNER.close();
    }


    // Menu


    private static void printMenu() {
        System.out.println();
        System.out.println("──────────────────────────────────────────");
        estadoActual();
        System.out.println("──────────────────────────────────────────");
        System.out.println("  CREAR TORRE");
        System.out.println("  [1] Tower(width, maxHeight)");
        System.out.println("  [2] Tower(cups)  — crea n tazas normales");
        System.out.println("  TAZAS");
        System.out.println("  [3] pushCup(type, i)");
        System.out.println("  [4] popCup()");
        System.out.println("  [5] removeCup(i)");
        System.out.println("  TAPAS");
        System.out.println("  [6] pushLid(type, i)");
        System.out.println("  [7] popLid()");
        System.out.println("  [8] removeLid(i)");
        System.out.println("  OPERACIONES");
        System.out.println("  [9] cover()");
        System.out.println("  [10] swap(o1, o2)");
        System.out.println("  [11] swapToReduce()");
        System.out.println("  [12] orderTower()");
        System.out.println("  [13] reverseTower()");
        System.out.println("  CONSULTAS");
        System.out.println("  [14] height / lidedCups / stackingItems");
        System.out.println("  VISUALIZACION");
        System.out.println("  [15] makeVisible()");
        System.out.println("  [16] makeInvisible()");
        System.out.println("  TOWER CONTEST");
        System.out.println("  [17] solve(n, h)");
        System.out.println("  [18] simulate(n, h)");
        System.out.println("  DEMO");
        System.out.println("  [19] Demo automatica de todos los tipos");
        System.out.println("  [0]  Salir");
        System.out.println("──────────────────────────────────────────");
    }

    private static void estadoActual() {
        if (tower == null) {
            System.out.println("  Torre: (ninguna creada)");
        } else {
            System.out.println("  Torre activa | ok=" + tower.ok()
                    + " | altura=" + tower.height()
                    + " | items=" + tower.stackingItems().length);
            System.out.println("  Items: " + tower.stackingItemsInfo());
            System.out.println("  Tapadas: " + tower.lidedCupsInfo());
        }
    }


    // Crear torre

    private static void crearTorreManual() {
        int w = readInt("  Ancho de la torre (cm): ");
        int h = readInt("  Altura maxima (cm): ");
        tower = new Tower(w, h);
        System.out.println("  Torre creada. ok=" + tower.ok());
    }

    private static void crearTorreCups() {
        int n = readInt("  Cantidad de tazas: ");
        tower = new Tower(n);
        System.out.println("  Torre creada con " + n + " tazas. ok=" + tower.ok());
    }


    // Tazas
    private static void pushCup() {
        if (!checkTower()) return;
        System.out.println("  Tipos: normal | opener | hierarchical | fragile");
        String type = readStr("  Tipo de taza: ");
        int i = readInt("  Numero de taza: ");
        tower.pushCup(type, i);
        System.out.println("  pushCup(\"" + type + "\", " + i + ") -> ok=" + tower.ok());
    }

    private static void popCup() {
        if (!checkTower()) return;
        tower.popCup();
        System.out.println("  popCup() -> ok=" + tower.ok());
    }

    private static void removeCup() {
        if (!checkTower()) return;
        int i = readInt("  Numero de taza a eliminar: ");
        tower.removeCup(i);
        System.out.println("  removeCup(" + i + ") -> ok=" + tower.ok());
    }


    // Tapas

    private static void pushLid() {
        if (!checkTower()) return;
        System.out.println("  Tipos: normal | fearful | crazy");
        String type = readStr("  Tipo de tapa: ");
        int i = readInt("  Numero de tapa: ");
        tower.pushLid(type, i);
        System.out.println("  pushLid(\"" + type + "\", " + i + ") -> ok=" + tower.ok());
    }

    private static void popLid() {
        if (!checkTower()) return;
        tower.popLid();
        System.out.println("  popLid() -> ok=" + tower.ok());
    }

    private static void removeLid() {
        if (!checkTower()) return;
        int i = readInt("  Numero de tapa a eliminar: ");
        tower.removeLid(i);
        System.out.println("  removeLid(" + i + ") -> ok=" + tower.ok());
    }


    // Operaciones


    private static void cover() {
        if (!checkTower()) return;
        tower.cover();
        System.out.println("  cover() -> ok=" + tower.ok());
    }

    private static void swap() {
        if (!checkTower()) return;
        System.out.println("  Elemento 1:");
        String type1 = readStr("    Tipo (cup/lid): ");
        int num1     = readInt("    Numero: ");
        System.out.println("  Elemento 2:");
        String type2 = readStr("    Tipo (cup/lid): ");
        int num2     = readInt("    Numero: ");
        tower.swap(new String[]{type1, String.valueOf(num1)},
                   new String[]{type2, String.valueOf(num2)});
        System.out.println("  swap -> ok=" + tower.ok());
    }

    private static void swapToReduce() {
        if (!checkTower()) return;
        int alturaAntes = tower.height();
        String[][] par = tower.swapToReduce();
        if (par.length == 0) {
            System.out.println("  swapToReduce: no hay intercambio que reduzca la altura ("
                    + alturaAntes + " cm).");
        } else {
            System.out.println("  swapToReduce: intercambiar ["
                    + par[0][0] + " " + par[0][1] + "] con ["
                    + par[1][0] + " " + par[1][1] + "]");
            System.out.println("  Aplicando intercambio...");
            tower.swap(par[0], par[1]);
            System.out.println("  Altura antes=" + alturaAntes + " | ahora=" + tower.height()
                    + " | ok=" + tower.ok());
        }
    }

    private static void orderTower() {
        if (!checkTower()) return;
        tower.orderTower();
        System.out.println("  orderTower() -> ok=" + tower.ok());
    }

    private static void reverseTower() {
        if (!checkTower()) return;
        tower.reverseTower();
        System.out.println("  reverseTower() -> ok=" + tower.ok());
    }


    // Consultas

    private static void consultar() {
        if (!checkTower()) return;
        System.out.println();
        System.out.println("  ─ Altura actual          : " + tower.height() + " cm");
        System.out.println("  ─ ok()                   : " + tower.ok());
        System.out.println("  ─ stackingItems()        : " + tower.stackingItemsInfo());
        System.out.println("  ─ lidedCups()            : " + tower.lidedCupsInfo());
        System.out.println();
        System.out.println("  Desglose de items (base → cima):");
        String[][] items = tower.stackingItems();
        for (int i = 0; i < items.length; i++) {
            System.out.printf("    [%2d] %-5s #%s%n", i, items[i][0], items[i][1]);
        }
    }


    // Visualizacion


    private static void makeVisible() {
        if (!checkTower()) return;
        tower.makeVisible();
        System.out.println("  makeVisible() -> ok=" + tower.ok());
        if (!tower.ok()) {
            System.out.println("  [!] La torre no cabe en la pantalla.");
        }
    }

    private static void makeInvisible() {
        if (!checkTower()) return;
        tower.makeInvisible();
        System.out.println("  makeInvisible() ejecutado.");
    }


    // TowerContest


    private static void contestSolve() {
        int n = readInt("  n (cantidad de tazas): ");
        int h = readInt("  h (altura objetivo): ");
        String result = TowerContest.solve(n, h);
        System.out.println();
        System.out.println("  TowerContest.solve(" + n + ", " + h + ") = \"" + result + "\"");
        if (!result.equals("impossible")) {
            String[] parts = result.split(" ");
            System.out.println("  Tazas en orden: " + parts.length);
            int suma = 0;
            System.out.print("  Heights: ");
            for (String p : parts) {
                System.out.print(p + " ");
                suma += Integer.parseInt(p);
            }
            System.out.println();
            System.out.println("  Suma verificada: " + suma + " == " + h + " ? " + (suma == h));
        }
    }

    private static void contestSimulate() {
        int n = readInt("  n (cantidad de tazas): ");
        int h = readInt("  h (altura objetivo): ");
        System.out.println("  Ejecutando TowerContest.simulate(" + n + ", " + h + ")...");
        System.out.println("  (La animacion se mostrara en la ventana grafica)");
        TowerContest.simulate(n, h);
    }


    // Demo automatica


    private static void demoAutomatica() {
        System.out.println();

        System.out.println("  DEMO AUTOMATICA — Todos los tipos");


        tower = new Tower(14, 50);
        System.out.println("\n[1] Tower(14, 50) creada.");
        printEstado();

        tower.pushCup("normal", 1);
        tower.pushCup("normal", 2);
        tower.pushCup("normal", 3);
        System.out.println("\n[2] pushCup normal: 1, 2, 3");
        printEstado();

        tower.pushLid("normal", 1);
        tower.pushLid("normal", 2);
        System.out.println("\n[3] pushLid normal: 1, 2");
        printEstado();

        tower.cover();
        System.out.println("\n[4] cover() — reordena tapas sobre sus tazas");
        printEstado();

        tower.pushLid("fearful", 3);
        System.out.println("\n[5] pushLid(fearful, 3) — entra porque cup3 existe");
        printEstado();
        tower.pushLid("fearful", 9);
        System.out.println("\n[6] pushLid(fearful, 9) — FALLA porque cup9 NO existe. ok=" + tower.ok());

        tower.pushLid("crazy", 7);
        System.out.println("\n[7] pushLid(crazy, 7) — se inserta en el FONDO");
        printEstado();

        tower.pushCup("hierarchical", 5);
        System.out.println("\n[8] pushCup(hierarchical, 5) — desplaza elementos menores");
        printEstado();

        tower.pushCup("fragile", 4);
        System.out.println("\n[9] pushCup(fragile, 4) — taza fragil (rumpe con >= 3 encima)");
        printEstado();

        System.out.println("\n[10] pushCup(opener, 6) — elimina TODAS las tapas al entrar");
        tower.pushCup("opener", 6);
        printEstado();

        System.out.println("\n[11] orderTower() — ordena de mayor a menor");
        tower.orderTower();
        printEstado();

        System.out.println("\n[12] swapToReduce()");
        int altAntes = tower.height();
        String[][] par = tower.swapToReduce();
        if (par.length > 0) {
            System.out.println("     Intercambia [" + par[0][0] + " " + par[0][1]
                    + "] con [" + par[1][0] + " " + par[1][1] + "]");
            tower.swap(par[0], par[1]);
            System.out.println("     Altura: " + altAntes + " -> " + tower.height());
        } else {
            System.out.println("     No hay intercambio que reduzca la altura.");
        }

        System.out.println("\n[13] reverseTower()");
        tower.reverseTower();
        printEstado();

        System.out.println("\n[14] TowerContest.solve(4, 9) = \"" + TowerContest.solve(4, 9) + "\"");
        System.out.println("     TowerContest.solve(4, 7) = \"" + TowerContest.solve(4, 7) + "\"");
        System.out.println("     TowerContest.solve(4,100) = \"" + TowerContest.solve(4, 100) + "\"");
        System.out.println("     TowerContest.solve(3, 5) = \"" + TowerContest.solve(3, 5) + "\"");
        System.out.println("     TowerContest.solve(3, 9) = \"" + TowerContest.solve(3, 9) + "\"");


        System.out.println("  Demo finalizada.");

    }


    // Utilidades


    private static void printEstado() {
        System.out.println("     Items   : " + tower.stackingItemsInfo());
        System.out.println("     Altura  : " + tower.height() + " cm");
        System.out.println("     Tapadas : " + tower.lidedCupsInfo());
        System.out.println("     ok      : " + tower.ok());
    }

    private static boolean checkTower() {
        if (tower == null) {
            System.out.println("  [!] Primero crea una torre (opciones 1 o 2).");
            return false;
        }
        return true;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Ingresa un numero entero valido.");
            }
        }
    }

    private static String readStr(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }
}