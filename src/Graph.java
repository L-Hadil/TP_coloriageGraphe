import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Node> nodes = new ArrayList<>();
    List<Node> allNodes = new ArrayList<>();

    public Node getNode(String name) {
        for (Node node : nodes) {
            if (node.name.equals(name)) {
                return node;
            }
        }
        Node newNode = new Node(name);
        nodes.add(newNode);
        allNodes.add(newNode);
        return newNode;
    }

    public void addInterference(String name1, String name2) {
        Node n1 = getNode(name1);
        Node n2 = getNode(name2);
        n1.addInterference(n2);
    }

    public void addPreference(String name1, String name2) {
        Node n1 = getNode(name1);
        Node n2 = getNode(name2);
        n1.addPreference(n2);
    }

    public List<Node> applyChaitinAlgorithm(int k) {
        List<Node> stack = new ArrayList<>();
        List<Node> spilledNodes = new ArrayList<>();

        // Phase de simplification
        while (!nodes.isEmpty()) {
            Node triviallyColorable = null;
            for (Node node : nodes) {
                if (node.isTriviallyColorable(k)) {
                    triviallyColorable = node;
                    break;
                }
            }

            if (triviallyColorable != null) {
                stack.add(triviallyColorable);
                nodes.remove(triviallyColorable);
                for (Node neighbor : triviallyColorable.interferences) {
                    neighbor.interferences.remove(triviallyColorable);
                }
            } else {
                Node nodeToSpill = selectNodeToSpill();
                spilledNodes.add(nodeToSpill);
                nodes.remove(nodeToSpill);
                for (Node neighbor : nodeToSpill.interferences) {
                    neighbor.interferences.remove(nodeToSpill);
                }
            }
        }

        // Phase de coloriage
        while (!stack.isEmpty()) {
            Node node = stack.remove(stack.size() - 1);
            List<Integer> forbiddenColors = new ArrayList<>();
            for (Node neighbor : node.interferences) {
                if (neighbor.color != null && !forbiddenColors.contains(neighbor.color)) {
                    forbiddenColors.add(neighbor.color);
                }
            }

            int color = 0;
            while (forbiddenColors.contains(color) && color < k) {
                color++;
            }

            if (color < k) {
                node.color = color;
            } else {
                spilledNodes.add(node);
            }

            assignPreferenceColors(node, color);
        }

        return spilledNodes;
    }

    private Node selectNodeToSpill() {
        Node nodeToSpill = null;
        int maxDegree = -1;
        for (Node node : nodes) {
            if (node.interferences.size() > maxDegree) {
                maxDegree = node.interferences.size();
                nodeToSpill = node;
            }
        }
        return nodeToSpill;
    }

    private void assignPreferenceColors(Node node, int color) {
        for (Node preference : node.preferences) {
            if (preference.color == null || preference.color == color) {
                preference.color = color;
            }
        }
    }

    public void optimizeWithCoalescing(List<Node> spilledNodes, int k) {
        boolean coalescingApplied = false;
        for (int i = 0; i < spilledNodes.size(); i++) {
            Node node = spilledNodes.get(i);
            List<Integer> forbiddenColors = new ArrayList<>();
            for (Node neighbor : node.interferences) {
                if (neighbor.color != null && !forbiddenColors.contains(neighbor.color)) {
                    forbiddenColors.add(neighbor.color);
                }
            }

            int color = 0;
            while (forbiddenColors.contains(color) && color < k) {
                color++;
            }

            if (color < k) {
                node.color = color;
                spilledNodes.remove(node);
                coalescingApplied = true;
                i--; // Ajuster l'index après suppression
            }
        }

        if (coalescingApplied) {
            System.out.println("\nCoalescing appliqué après l'algorithme de Chaitin pour réduire les spillages et optimiser la coloration.");
        }

        displayResults(spilledNodes);
    }

    public void displayResults(List<Node> spilledNodes) {
        System.out.println("\nColoration du graphe :");
        for (Node node : allNodes) {
            if (node.color != null) {
                System.out.println("Variable " + node.name + " -> " + node.name + ": Registre " + node.color);
            } else {
                System.out.println("Variable " + node.name + " -> " + node.name + ": Spilled");
            }
        }
        if (!spilledNodes.isEmpty()) {
            System.out.print("\nVariables spillées : ");
            List<String> spilledNames = new ArrayList<>();
            for (Node spilled : spilledNodes) {
                spilledNames.add(spilled.name);
            }
            System.out.println(spilledNames);
        }
    }
    public void displayEdges() {
        System.out.println("Arêtes d'interférence :");
        for (Node node : allNodes) { // Remplacez `nodes` par `allNodes` si vous voulez toutes les connexions d'origine
            for (Node neighbor : node.interferences) {
                if (node.name.compareTo(neighbor.name) < 0) { // Eviter les doublons
                    System.out.println(node.name + " -- " + neighbor.name);
                }
            }
        }

        System.out.print("\nArêtes de préférence : ");
        boolean hasPreference = false;
        for (Node node : allNodes) {
            if (!node.preferences.isEmpty()) {
                hasPreference = true;
                break;
            }
        }

        if (!hasPreference) {
            System.out.println("aucune");
        } else {
            System.out.println();
            for (Node node : allNodes) {
                for (Node preference : node.preferences) {
                    if (node.name.compareTo(preference.name) < 0) { // Eviter les doublons
                        System.out.println(node.name + " -- " + preference.name);
                    }
                }
            }
        }
    }

}
