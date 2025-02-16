# Delivery Optimization Using Ant Colony Optimization & Brute Force 🚚🐜

## **Project Overview**

This project implements a **delivery route optimization system** using two different approaches:

1. **Ant Colony Optimization (ACO)** — A bio-inspired metaheuristic algorithm that mimics the foraging behavior of ants.
2. **Brute Force Algorithm** — A traditional approach that examines all possible routes to find the optimal solution.

The goal is to **minimize delivery time and distance** by finding the shortest path through multiple delivery points. The system compares the efficiency of **ACO vs. Brute Force**, analyzing computation time, solution accuracy, and scalability.

This project can be applied to **logistics, delivery services, and smart transportation systems** to improve efficiency in large-scale delivery networks.

---

## **Features**

🔹 **Graph-Based Route Planning** — The delivery map is modeled as a weighted graph.\
🔹 **Ant Colony Optimization (ACO)** — Uses **pheromone-based reinforcement learning** for shortest pathfinding.\
🔹 **Brute Force Approach** — Computes the optimal path by evaluating all possible routes.\
🔹 **Dynamic Visualization** — Outputs both **optimized and brute force paths** for comparison.\
🔹 **Hyperparameter Tuning** — Adjustable parameters like **alpha, beta, pheromone intensity, and degradation factor** for optimization.\
🔹 **Performance Benchmarking** — Compares solution accuracy and runtime of ACO vs. Brute Force.

---

## **Technologies & Algorithms**

🖥 **Programming Language:** Java\
🔄 **Optimization Algorithms:**

- **Ant Colony Optimization (ACO)**
- **Brute Force Algorithm**\
  📈 **Data Structures:**
- **Graph (Adjacency List)** — Represents delivery points and connections.
- **ArrayLists** — Stores possible routes and distances.

---

## **File Structure**

📂 **Main.java** → Reads input, initializes optimization methods, and runs simulations.\
📂 **Edges.java** → Represents edges (delivery routes) with distance and pheromone values.\
📂 **Points.java** → Defines delivery points (nodes) with coordinates.

---

## **How It Works?**

### **1. Graph Initialization**

- The delivery network is modeled as a **graph with weighted edges**, where nodes represent delivery locations and edges represent distances.

### **2. Pathfinding Algorithms**

#### **Brute Force Algorithm**

- Evaluates **all possible routes** to determine the absolute shortest path.
- Computationally expensive but guarantees optimal results.

#### **Ant Colony Optimization (ACO)**

- Uses **pheromone-based reinforcement** to guide virtual ants towards optimal paths.
- **Alpha (α) & Beta (β) parameters** balance exploitation and exploration.
- Over time, **shorter paths accumulate stronger pheromone trails**, reinforcing optimal routes.

### **3. Algorithm Performance Comparison**

- Compares ACO with Brute Force in terms of **execution time, accuracy, and scalability**.
- Outputs the **computed shortest path, total distance, and runtime** for each method.

---

## **Installation & Usage**

### **Prerequisites**

📌 **Java 8+** must be installed.

### **Compiling the Project**

```sh
javac *.java  
```

### **Running the Project**

```sh
java Main <input_file>  
```

Example:

```sh
java Main input01.txt  
```

---

## **Example Output**

```
Method: Ant Colony Method  
Shortest Distance: 14.37  
Shortest Path: [0, 2, 4, 1, 3, 0]  
Time: 2.4 seconds  

Method: Brute Force Method  
Shortest Distance: 14.37  
Shortest Path: [0, 2, 4, 1, 3, 0]  
Time: 10.2 seconds  
```

---

## **Best ACO Hyperparameters**

```sh
N = 100      # Number of iterations  
M = 50       # Ant count per iteration  
Degradation Factor = 0.9  
Alpha (α) = 0.97  
Beta (β) = 2.8  
Initial Pheromone Intensity = 0.1  
Q = 0.0001  
```

---


## **Author**

👤 **Ethem Erinc Cengiz**

📧 [erinccengiz@gmail.com](mailto\:erinccengiz@gmail.com)

🔗 [GitHub](https://github.com/erinc00)

