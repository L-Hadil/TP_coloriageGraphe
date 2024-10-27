import java.util.List;
import java.util.Scanner;

public class RegisterAllocationMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueExecution = true;

        while (continueExecution) {
            System.out.println("\nChoisissez parmi :");
            System.out.println("1. Graphe diamant (X, Y, Z, T)");
            System.out.println("2. Graphe du cours (X, Y, Z, T, U, V)");
            System.out.println("3. Quitter");

            int choice = scanner.nextInt();

            if (choice == 3) {
                continueExecution = false;
                System.out.println("Programme terminé.");
                break;
            }

            System.out.println("Entrez le nombre de registres (K) : ");
            int k = scanner.nextInt();

            Graph graph = new Graph();

            if (choice == 1) {
                graph.addInterference("x", "y");
                graph.addInterference("y", "t");
                graph.addInterference("t", "z");
                graph.addInterference("z", "x");
            } else if (choice == 2) {
                graph.addInterference("x", "y");
                graph.addInterference("x", "v");
                graph.addInterference("x", "u");
                graph.addInterference("z", "v");
                graph.addInterference("u", "y");
                graph.addInterference("y", "t");
                graph.addInterference("t", "v");
                graph.addPreference("u", "t");
            } else {
                System.out.println("Choix invalide, veuillez réessayer.");
                continue;
            }

            // Affichage du graphe
            graph.displayEdges();

            // Exécuter l'algorithme de Chaitin
            List<Node> spilledNodes = graph.applyChaitinAlgorithm(k);

            // Vérifier si le coalescing est nécessaire
            if (!spilledNodes.isEmpty()) {
                graph.optimizeWithCoalescing(spilledNodes, k);
            } else {
                System.out.println("\nTous les nœuds ont été coloriés sans spillage.");
                graph.displayResults(spilledNodes); // Affiche le résultat final même sans spillage
            }
        }
        scanner.close();
    }
}
