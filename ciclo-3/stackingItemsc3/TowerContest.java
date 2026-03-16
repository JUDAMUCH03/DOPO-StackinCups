import java.util.ArrayList;

/**
 * TowerContest
 * Proporciona algoritmos para encontrar el orden de tazas que alcanza una altura específica
 * y permite la visualización a través del simulador Tower.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class TowerContest {

    /**
     * Resuelve el problema logístico de encontrar el orden de las tazas.
     * * @param n cantidad de tazas a utilizar
     * @param h altura total objetivo de la torre
     * @return String con las alturas de las tazas separadas por espacios, o "impossible"
     */
    public static String solve(int n, int h) {
        int minH = 2 * n - 1;
        int maxH = n * n;

        if (h < minH || h > maxH) {
            return "impossible";
        }

        ArrayList<Integer> result = new ArrayList<>();
        int currentN = n;
        int currentH = h;

        while (currentN > 0) {
            int L = 2 * currentN - 1;
            int G = currentH - L;

            boolean[] used = new boolean[currentN + 1];
            int rem = G;
            
            for (int i = currentN - 1; i >= 1; i--) {
                int cup = 2 * i - 1;
                if (cup <= rem) {
                    used[i] = true;
                    rem -= cup;
                }
            }

            if (rem == 0) {
                ArrayList<Integer> prefix = new ArrayList<>();
                for (int i = 1; i <= currentN - 1; i++) {
                    if (used[i]) prefix.add(2 * i - 1);
                }
                
                ArrayList<Integer> suffix = new ArrayList<>();
                for (int i = currentN - 1; i >= 1; i--) {
                    if (!used[i]) suffix.add(2 * i - 1);
                }
                
                result.addAll(prefix);
                result.add(L);
                result.addAll(suffix);
                break; 
            } else {
                result.add(L);
                currentH -= 1;
                currentN -= 1;
                
                if (currentN > 0) {
                    int minPossible = 2 * currentN - 1;
                    int maxPossible = currentN * currentN;
                    if (currentH < minPossible || currentH > maxPossible) {
                        return "impossible";
                    }
                }
            }
        }
        
        if (currentN == 0 && currentH != 0) return "impossible";

        StringBuilder sb = new StringBuilder();
        for (int cup : result) {
            sb.append(cup).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Ejecuta el simulador visual de la torre basado en un objetivo de altura.
     * Crea una instancia de Tower y añade las tazas secuencialmente.
     * * @param n cantidad de tazas a utilizar
     * @param h altura total objetivo de la torre
     */
    public static void simulate(int n, int h) {
        String result = solve(n, h);
        if (result.equals("impossible")) {
            System.out.println("Resultado: impossible.");
            return;
        }

        System.out.println("Orden calculado: " + result);
        
        int width = n * 2 + 2;
        int maxHeight = n * n;
        Tower tower = new Tower(width, maxHeight);
        tower.makeVisible();

        String[] heights = result.split(" ");
        for (String hStr : heights) {
            int heightCm = Integer.parseInt(hStr);
            int cupNumber = (heightCm + 1) / 2;
            try { 
                Thread.sleep(500); 
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            } 
            tower.pushCup(cupNumber);
        }
    }
}