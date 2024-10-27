
//  LADJ HADIL GL

import java.util.ArrayList;
public class Node {
    String name;
    ArrayList<Node> interferences = new ArrayList<>();
    ArrayList<Node> preferences = new ArrayList<>();
    Integer color = null;

    public Node(String name) {
        this.name = name;
    }

    public void addInterference(Node node) {
        if (!interferences.contains(node)) {
            interferences.add(node);
            node.interferences.add(this);
        }
    }

    public void addPreference(Node node) {
        if (!preferences.contains(node)) {
            preferences.add(node);
            node.preferences.add(this);
        }
    }

    public boolean isTriviallyColorable(int k) {
        return interferences.size() < k;
    }

    @Override
    public String toString() {
        return name + (color == null ? ": Spilled" : ": Registre " + color);
    }
}
