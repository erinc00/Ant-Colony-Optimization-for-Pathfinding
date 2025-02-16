import java.util.ArrayList;

public class Edges {
    public ArrayList<Points> p;
    public double pheromone_intensity;
    public double distance;
    public double edge_value;

    public Edges(ArrayList<Points> p, double pheromone_intensity, double distance, double edge_value) {
        this.p = p;
        this.pheromone_intensity = pheromone_intensity;
        this.distance = distance;
        this.edge_value = edge_value;
    }

}
