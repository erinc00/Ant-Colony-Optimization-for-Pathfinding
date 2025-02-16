import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * author: Ethem Erinç Cengiz
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //assigning constant variables
        int N=100;//iteration count
        int M=50;//ant count per iteration
        double DEGRETION_FACTOR=0.9;//degration factor
        double ALPHA=0.97;//default -> 0.8
        double BETA=2.8;//default -> 1.5
        double INITIAL_PHEROMONE_INTENSITY=0.1;
        double Q=0.0001;
        //inputfile
        File input_file=new File(args[0]);
        Scanner scanner=new Scanner(input_file);
        //choosing calculating and drawing methods
        int methods=2;//1 -> Brute Force, 2 -> Ant Colony
        int drawing_methods=1;//1 -> Optimal Path, 2 -> Pheromone intensity Map
        //assigning each stop as an obj
        ArrayList<Points> points=new ArrayList<>();
        int index=0;
        while(scanner.hasNextLine()){
            String[] coordinates_array_string=scanner.nextLine().split(",");
            double x_coordinate=Double.parseDouble(coordinates_array_string[0]);
            double y_coordinate=Double.parseDouble(coordinates_array_string[1]);
            Points point=new Points(index,x_coordinate,y_coordinate);
            points.add(point);
            index++;
        }
        ArrayList<Double> distances = new ArrayList<>();//contains 5000 possible total distances
        ArrayList<ArrayList<Edges>> paths = new ArrayList<>();//contains possible paths
        ArrayList<ArrayList<Points>> path_index = new ArrayList<>();//contains possible paths
        ArrayList<Edges> optimum_path = new ArrayList<>();
        ArrayList<Points> optimum_path_index = null;
        ArrayList<Edges> allEdges = new ArrayList<>();//all possible edges between 2 different points
        // Generating all possible edges between points
        for (int j = 0; j < points.size(); j++) {
            for (int k = j + 1; k < points.size(); k++) {
                ArrayList<Points> p = new ArrayList<>();//contains 2 points which are corner points of edge
                p.add(points.get(j));//one of the corner points
                p.add(points.get(k));//another corner point
                double distance = Math.pow(Math.pow((points.get(j).y - points.get(k).y), 2) + Math.pow((points.get(j).x - points.get(k).x), 2), 0.5);//calculating edge distance between corner points
                Edges edge = new Edges(p, INITIAL_PHEROMONE_INTENSITY, distance, 0);//initializing edges(initial pheromone intensity decreases each iteration)
                allEdges.add(edge);//adding edges to allEdges list
            }
        }
        double time=System.currentTimeMillis();
        // Method selection
        if(methods==1) {
            // Brute Force Method
            // Find the shortest path using brute force
            int[] arr =new int[points.size()];
            double[] min_dist=new double[1];
            min_dist[0]=Double.MAX_VALUE;
            for(int i=0;i<points.size() ;i++){
                arr[i]=i+1;
            }
            int[] bestpath=new int[points.size()];
            int[] a=(permute(arr , 1, min_dist,allEdges,bestpath));
            ArrayList<Integer> alist=new ArrayList<>();
            for(Integer i:a){
                alist.add(i);
            }
            alist.add(1);
            for(Edges e:allEdges){
                for(int i=0;i<alist.size()-1;i++){
                    if((((e.p.get(0).index)+1==(alist.get(i)))||((e.p.get(1).index)+1==(alist.get(i))))&&(((e.p.get(0).index)+1==alist.get(i+1))||((e.p.get(1).index)+1==alist.get(i+1)))){
                        optimum_path.add(e);
                    }
                }
            }
            System.out.println("Method: Brute Force Method");
            System.out.println("Shortest Distance: "+min_dist[0]);
            System.out.println("Shortest Path: "+alist);
            System.out.println("Time: "+(System.currentTimeMillis()-time)/1000+" seconds");
        }
        else if(methods==2) {
            //calculating distance using ant colony method
            for (int i = 0; i < N; i++) {//iterations
                for (int j = 0; j < M; j++) {//ants
                    for (Edges e : allEdges) {
                        e.edge_value = (Math.pow(e.pheromone_intensity, ALPHA)) / (Math.pow(e.distance, BETA));//calculating edge value of all edges
                    }
                    double final_distance = 0;//initializing total distance
                    //to determine random starting point
                    Points starting_point = null;
                    int starting_point_index = (int) (Math.random() * points.size());
                    for (Points point : points) {
                        if (point.index == starting_point_index) {
                            starting_point = point;
                        }
                    }
                    ArrayList<Points> visited_points = new ArrayList<>();
                    ArrayList<Edges> visited_edges = new ArrayList<>();
                    boolean boolean0 = true;
                    while (boolean0) {//finding an ant's possible path
                        //neighboring points
                        ArrayList<Points> other_points = new ArrayList<>();
                        for (Points point : points) {
                            if ((point.index != starting_point.index) && !(visited_points.contains(point))) {
                                other_points.add(point);
                            }
                        }
                        //connected edges to starting point
                        ArrayList<Edges> connected_edges = new ArrayList<>();
                        for (Edges allEdge : allEdges) {
                            for (Points otherPoint : other_points)
                                if ((allEdge.p.contains(starting_point)) && (allEdge.p.contains(otherPoint))) {
                                    connected_edges.add(allEdge);
                                }
                        }
                        //determining next starting point
                        if (other_points.isEmpty()) {//if there is no neighboring point of current starting point, we add last edge to return to initial position
                            visited_points.add(starting_point);
                            for (Edges e : allEdges) {
                                if (e.p.contains(starting_point) && ((e.p.get(0).index == starting_point_index) || (e.p.get(1).index == starting_point_index))) {
                                    visited_edges.add(e);
                                    final_distance += e.distance;
                                }
                            }
                            boolean0 = false;//quiting while loop
                        }
                        else {
                            Edges next_edge = null;
                            //choosing new starting point using possibilities
                            double edge_values_sum = 0;
                            for (Edges connectedEdge : connected_edges) {
                                edge_values_sum += connectedEdge.edge_value;
                            }
                            double random_possibility = Math.random() * edge_values_sum;
                            double sum_edge = 0;
                            for (Edges connectedEdge : connected_edges) {
                                sum_edge += connectedEdge.edge_value;
                                if (sum_edge > random_possibility) {//calculating possibility
                                    next_edge = connectedEdge;
                                    break;
                                }
                            }
                            visited_points.add(starting_point);
                            visited_edges.add(next_edge);
                            if (starting_point.equals(next_edge.p.get(0))) {
                                starting_point = next_edge.p.get(1);//assigning starting point to another corner point
                            }
                            else {
                                starting_point = next_edge.p.get(0);//assigning starting point to another corner point
                            }
                            final_distance += next_edge.distance;//summation of each cycle
                        }
                    }
                    for (Edges e : visited_edges) {
                        e.pheromone_intensity += Q / final_distance;//updating pheromone intensity of used edges
                    }
                    path_index.add(visited_points);
                    paths.add(visited_edges);
                    distances.add(final_distance);//collecting 5000 possible total distance
                }
                for(Edges e:allEdges){
                    e.pheromone_intensity*=DEGRETION_FACTOR;
                }
            }
            //sorting distances to find the shortest distance
            double control_distance=Integer.MAX_VALUE;
            int shortest_index=0;//determining objects' index of the shortest path list
            for(int i=0;i<distances.size();i++){
                if (distances.get(i)<control_distance){
                    control_distance=distances.get(i);
                    shortest_index=i;
                }
            }
            optimum_path=paths.get(shortest_index);
            optimum_path_index=path_index.get(shortest_index);
            int index_of_zero=0;
            //reorder to start at 0
            while(optimum_path_index.getFirst().index>0){
                optimum_path_index.add(optimum_path_index.getFirst());
                optimum_path_index.remove(0);
            }
            //finding 0's index in path list
            for(int i=0;i<optimum_path_index.size();i++){
                if(0==optimum_path_index.get(i).index){
                    index_of_zero=i;
                    break;
                }
            }
            optimum_path_index.add(optimum_path_index.get(index_of_zero));//adding end point
            System.out.println("Method: Ant Colony Method");
            System.out.println("Shortest Distance: "+distances.get(shortest_index));
            System.out.println("Shortest Path: "+optimum_path_index);
            System.out.println("Time: "+(System.currentTimeMillis()-time)/1000+" seconds");
        }
        StdDraw.setCanvasSize(700,700);
        StdDraw.setXscale(0,1);
        StdDraw.setYscale(0,1);
        StdDraw.enableDoubleBuffering();
        //Drawing method selection
        if(drawing_methods==1){
            //Drawing the shortest path
            for(Edges e:optimum_path){
                StdDraw.setPenRadius(0.004);
                StdDraw.line(e.p.get(0).x,e.p.get(0).y,e.p.get(1).x,e.p.get(1).y);
            }
        }
        else if(drawing_methods==2){
            for(Edges e: allEdges){
                StdDraw.setPenRadius(e.pheromone_intensity*0.75);
                StdDraw.line(e.p.get(0).x,e.p.get(0).y,e.p.get(1).x,e.p.get(1).y);
            }
        }
        for(Points p:points){
            if(p.index==0&&drawing_methods==1){
                StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
            }
            else{
                StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            }
            StdDraw.filledCircle(p.x,p.y,0.02);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(p.x,p.y,Integer.toString(p.index+1));
        }
        StdDraw.show();
    }

    /**
     *
     * @param arr This parameter represents the array of integers that we want to permute
     * @param k It indicates the current index during the permutation process. Initially, it's set to 1.
     * @param min_dist This is an array of doubles used to store the minimum distance found during permutation.
     *                 It's passed as an array to allow modification within the method and retain the updated value.
     * @param edges This parameter is an ArrayList of Edges objects. It allows access to the list of edges in the main method.
     * @param bestpath It's an array of integers that holds the indices of the shortest path's elements. It's also modified within the method to update the best path found.
     * @return
     */
    public static int[] permute(int[] arr, int k, double[] min_dist, ArrayList<Edges> edges,int[] bestpath) {

        if (k == arr.length) {
            double dist=0;

            for (int i=0;i<arr.length-1;i++){
                for (Edges f:edges){
                    if ((f.p.get(0).index+1==arr[i]||f.p.get(1).index+1==arr[i])&&(f.p.get(0).index+1==arr[i+1]||f.p.get(1).index+1==arr[i+1])){
                        dist+=f.distance;
                    }
                }
            }
            for (Edges edge:edges){
                if((edge.p.get(0).index+1==arr[0]||edge.p.get(1).index+1==arr[0])&&(edge.p.get(0).index+1==arr[arr.length-1]||edge.p.get(1).index+1==arr[arr.length-1])){
                    dist+=edge.distance;

                }
            }

            if (dist<min_dist[0]){
                min_dist[0]=dist;
                int le=0;
                for(int s :arr){
                    bestpath[le]=s;
                    le++;
                }
            }

        } else {
            for (int i = k; i < arr.length; i++) {
                int temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
                permute(arr, k + 1 , min_dist,edges,bestpath);
                temp = arr[k];
                arr[k] = arr[i];
                arr[i] = temp;
            }
        }
        return bestpath;
    }
}
