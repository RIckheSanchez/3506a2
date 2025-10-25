// @edu:student-assignment

package uq.comp3506.a2;

// You may wish to import more/other structures too
import uq.comp3506.a2.structures.Edge;
import uq.comp3506.a2.structures.Vertex;
import uq.comp3506.a2.structures.Entry;
import uq.comp3506.a2.structures.TopologyType;
import uq.comp3506.a2.structures.Tunnel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

// This is part of COMP3506 Assignment 2. Students must implement their own solutions.

/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 * No bounds are provided. You should maximize efficiency where possible.
 * Below we use `S` and `U` to represent the generic data types that a Vertex
 * and an Edge can have, respectively, to avoid confusion between V and E in
 * typical graph nomenclature. That is, Vertex objects store data of type `S`
 * and Edge objects store data of type `U`.
 */
public class Problems {

    /**
     * Return a double representing the minimum radius of illumination required
     * to light the entire tunnel. Your answer will be accepted if
     * |your_ans - true_ans| is less than or equal to 0.000001
     * @param tunnelLength The length of the tunnel in question
     * @param lightIntervals The list of light intervals in [0, tunnelLength];
     * that is, all light interval values are >= 0 and <= tunnelLength
     * @return The minimum radius value required to illuminate the tunnel
     * or -1 if no light fittings are provided
     * Note: We promise that the input List will be an ArrayList.
     */
    public static double tunnelLighting(int tunnelLength, List<Integer> lightIntervals) {
        if (lightIntervals == null || lightIntervals.isEmpty()) {
            return -1;
        }
        
        ArrayList<Integer> positions = new ArrayList<>(lightIntervals);
        positions.sort(Integer::compareTo);
        
        double left = 0.0;
        double right = (double) tunnelLength;
        double epsilon = 0.000001;
        
        while (right - left > epsilon) {
            double mid = (left + right) / 2;
            if (canCover(tunnelLength, positions, mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        
        return right;
    }
    
    private static boolean canCover(int tunnelLength, List<Integer> positions, double radius) {
        double covered = 0.0;
        int i = 0;
        
        while (covered < tunnelLength && i < positions.size()) {
            if (positions.get(i) - radius > covered) {
                return false;
            }
            
            double maxReach = covered;
            while (i < positions.size() && positions.get(i) - radius <= covered) {
                maxReach = Math.max(maxReach, positions.get(i) + radius);
                i++;
            }
            
            covered = maxReach;
        }
        
        return covered >= tunnelLength;
    }

    /**
     * Compute the TopologyType of the graph as represented by the given edgeList.
     * @param edgeList The list of edges making up the graph G; each is of type
     *              Edge, which stores two vertices and a value. Vertex identifiers
     *              are NOT GUARANTEED to be contiguous or in a given range.
     * @return The corresponding TopologyType.
     * Note: We promise not to provide any self loops, double edges, or isolated
     * vertices.
     */
    public static <S, U> TopologyType topologyDetection(List<Edge<S, U>> edgeList) {
        if (edgeList == null || edgeList.isEmpty()) {
            return TopologyType.UNKNOWN;
        }
        
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        HashSet<Integer> vertices = new HashSet<>();
        
        for (Edge<S, U> edge : edgeList) {
            int v1 = edge.getVertex1().getId();
            int v2 = edge.getVertex2().getId();
            vertices.add(v1);
            vertices.add(v2);
            
            graph.putIfAbsent(v1, new ArrayList<>());
            graph.putIfAbsent(v2, new ArrayList<>());
            graph.get(v1).add(v2);
            graph.get(v2).add(v1);
        }
        
        HashSet<Integer> visited = new HashSet<>();
        ArrayList<HashSet<Integer>> components = new ArrayList<>();
        
        for (Integer vertex : vertices) {
            if (!visited.contains(vertex)) {
                HashSet<Integer> component = new HashSet<>();
                dfsExplore(vertex, graph, visited, component);
                components.add(component);
            }
        }
        
        boolean isConnected = components.size() == 1;
        
        if (isConnected) {
            boolean hasCycle = detectCycle(graph, vertices.iterator().next());
            return hasCycle ? TopologyType.CONNECTED_GRAPH : TopologyType.CONNECTED_TREE;
        } else {
            boolean hasTree = false;
            boolean hasGraph = false;
            
            for (HashSet<Integer> component : components) {
                Integer start = component.iterator().next();
                if (detectCycleInComponent(graph, start, component)) {
                    hasGraph = true;
                } else {
                    hasTree = true;
                }
            }
            
            if (hasTree && hasGraph) {
                return TopologyType.HYBRID;
            } else if (hasTree) {
                return TopologyType.FOREST;
            } else {
                return TopologyType.DISCONNECTED_GRAPH;
            }
        }
    }
    
    private static void dfsExplore(Integer vertex, HashMap<Integer, ArrayList<Integer>> graph, 
                                   HashSet<Integer> visited, HashSet<Integer> component) {
        visited.add(vertex);
        component.add(vertex);
        
        if (graph.containsKey(vertex)) {
            for (Integer neighbor : graph.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    dfsExplore(neighbor, graph, visited, component);
                }
            }
        }
    }
    
    private static boolean detectCycle(HashMap<Integer, ArrayList<Integer>> graph, Integer start) {
        HashSet<Integer> visited = new HashSet<>();
        return dfsCheckCycle(start, null, graph, visited);
    }
    
    private static boolean dfsCheckCycle(Integer vertex, Integer parent, 
                                         HashMap<Integer, ArrayList<Integer>> graph, 
                                         HashSet<Integer> visited) {
        visited.add(vertex);
        
        if (graph.containsKey(vertex)) {
            for (Integer neighbor : graph.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    if (dfsCheckCycle(neighbor, vertex, graph, visited)) {
                        return true;
                    }
                } else if (parent == null || !neighbor.equals(parent)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private static boolean detectCycleInComponent(HashMap<Integer, ArrayList<Integer>> graph, 
                                                  Integer start, HashSet<Integer> component) {
        HashSet<Integer> visited = new HashSet<>();
        return dfsCheckCycleInComponent(start, null, graph, visited, component);
    }
    
    private static boolean dfsCheckCycleInComponent(Integer vertex, Integer parent, 
                                                    HashMap<Integer, ArrayList<Integer>> graph, 
                                                    HashSet<Integer> visited, 
                                                    HashSet<Integer> component) {
        visited.add(vertex);
        
        if (graph.containsKey(vertex)) {
            for (Integer neighbor : graph.get(vertex)) {
                if (!component.contains(neighbor)) {
                    continue;
                }
                if (!visited.contains(neighbor)) {
                    if (dfsCheckCycleInComponent(neighbor, vertex, graph, visited, component)) {
                        return true;
                    }
                } else if (parent == null || !neighbor.equals(parent)) {
                    return true;
                }
            }
        }
        
        return false;
    }
 
    /**
     * Compute the list of reachable destinations and their minimum costs.
     * @param edgeList The list of edges making up the graph G; each is of type
     *              Edge, which stores two vertices and a value. Vertex identifiers
     *              are NOT GUARANTEED to be contiguous or in a given range.
     * @param origin The origin vertex object.
     * @param threshold The total time the driver can drive before a break is required.
     * @return an ArrayList of Entry types, where the first element is the identifier
     *         of a reachable station (within the time threshold), and the second
     *         element is the minimum cost of reaching that given station. The
     *         order of the list is not important.
     * Note: We promise that S will be of Integer type.
     * Note: You should return the origin in your result with a cost of zero.
     */
    public static <S, U> List<Entry<Integer, Integer>> routeManagement(List<Edge<S, U>> edgeList,
                                                          Vertex<S> origin, int threshold) {
        ArrayList<Entry<Integer, Integer>> answers = new ArrayList<>();
        
        if (edgeList == null || origin == null) {
            return answers;
        }
        
        int originId = origin.getId();
        
        HashMap<Integer, ArrayList<EdgeInfo>> graph = new HashMap<>();
        
        for (Edge<S, U> edge : edgeList) {
            int v1 = edge.getVertex1().getId();
            int v2 = edge.getVertex2().getId();
            int weight = (Integer) edge.getData();
            
            graph.putIfAbsent(v1, new ArrayList<>());
            graph.putIfAbsent(v2, new ArrayList<>());
            
            graph.get(v1).add(new EdgeInfo(v2, weight));
            graph.get(v2).add(new EdgeInfo(v1, weight));
        }
        
        HashMap<Integer, Integer> distances = new HashMap<>();
        distances.put(originId, 0);
        
        PriorityQueue<NodeDist> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));
        pq.offer(new NodeDist(originId, 0));
        
        HashSet<Integer> visited = new HashSet<>();
        
        while (!pq.isEmpty()) {
            NodeDist current = pq.poll();
            int nodeId = current.nodeId;
            int dist = current.dist;
            
            if (visited.contains(nodeId)) {
                continue;
            }
            
            visited.add(nodeId);
            
            if (dist > threshold) {
                continue;
            }
            
            if (!graph.containsKey(nodeId)) {
                continue;
            }
            
            for (EdgeInfo neighbor : graph.get(nodeId)) {
                int neighborId = neighbor.targetId;
                int edgeWeight = neighbor.weight;
                int newDist = dist + edgeWeight;
                
                if (newDist <= threshold) {
                    if (!distances.containsKey(neighborId) || newDist < distances.get(neighborId)) {
                        distances.put(neighborId, newDist);
                        pq.offer(new NodeDist(neighborId, newDist));
                    }
                }
            }
        }
        
        for (HashMap.Entry<Integer, Integer> entry : distances.entrySet()) {
            answers.add(new Entry<>(entry.getKey(), entry.getValue()));
        }
        
        return answers;
    }
    
    private static class NodeDist {
        int nodeId;
        int dist;
        
        NodeDist(int nodeId, int dist) {
            this.nodeId = nodeId;
            this.dist = dist;
        }
    }
    
    private static class EdgeInfo {
        int targetId;
        int weight;
        
        EdgeInfo(int targetId, int weight) {
            this.targetId = targetId;
            this.weight = weight;
        }
    }

    /**
     * Compute the tunnel that if flooded will cause the maximal flooding of 
     * the network
     * @param tunnels A list of the tunnels to consider; see Tunnel.java
     * @return The identifier of the Tunnel that would cause maximal flooding.
     * Note that for Tunnel A to drain into some other tunnel B, the distance
     * from A to B must be strictly less than the radius of A plus an epsilon
     * allowance of 0.000001. 
     * Note also that all identifiers in tunnels are GUARANTEED to be in the
     * range [0, n-1] for n unique tunnels.
     */
    public static int totallyFlooded(List<Tunnel> tunnels) {
        if (tunnels == null || tunnels.isEmpty()) {
            return -1;
        }
        
        int n = tunnels.size();
        double epsilon = 0.000001;
        
        int maxFlooded = -1;
        int resultId = -1;
        
        for (int i = 0; i < n; i++) {
            Tunnel source = tunnels.get(i);
            int floodCount = 0;
            
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                
                Tunnel target = tunnels.get(j);
                
                double dx = source.getX() - target.getX();
                double dy = source.getY() - target.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                
                if (distance < source.getRadius() + epsilon) {
                    floodCount++;
                }
            }
            
            if (floodCount > maxFlooded) {
                maxFlooded = floodCount;
                resultId = source.getId();
            } else if (floodCount == maxFlooded && (resultId == -1 || source.getId() < resultId)) {
                resultId = source.getId();
            }
        }
        
        return resultId;
    }

    /**
     * Compute the number of sites that cannot be infiltrated from the given starting sites.
     * @param sites The list of unique site identifiers. A site identifier is GUARANTEED to be
     *              non-negative, starting from 0 and counting upwards to n-1.
     * @param rules The infiltration rule. The right-hand side of a rule is represented by a list
     *             of lists of site identifiers (as is done in the assignment specification). The
     *             left-hand side of a rule is given by the rule's index in the parameter `rules`
     *             (i.e. the rule whose left-hand side is 4 will be at index 4 in the parameter
     *              `rules` and can be accessed with `rules.get(4)`).
     * @param startingSites The list of site identifiers to begin your infiltration from.
     * @return The number of sites which cannot be infiltrated.
     */
    public static int susDomination(List<Integer> sites, List<List<List<Integer>>> rules,
                                     List<Integer> startingSites) {
        if (sites == null || sites.isEmpty()) {
            return 0;
        }
        
        HashSet<Integer> infiltrated = new HashSet<>();
        
        if (startingSites != null) {
            infiltrated.addAll(startingSites);
        }
        
        for (Integer site : sites) {
            if (infiltrated.contains(site)) {
                continue;
            }
            
            if (site >= rules.size() || rules.get(site) == null) {
                continue;
            }
            
            List<List<Integer>> siteRules = rules.get(site);
            
            for (List<Integer> rule : siteRules) {
                boolean canInfiltrate = true;
                
                for (Integer required : rule) {
                    if (!infiltrated.contains(required)) {
                        canInfiltrate = false;
                        break;
                    }
                }
                
                if (canInfiltrate) {
                    infiltrated.add(site);
                    break;
                }
            }
        }
        
        return sites.size() - infiltrated.size();
    }
}
