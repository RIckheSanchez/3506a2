// @edu:student-assignment

package uq.comp3506.a2.structures;

/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 */
public enum TopologyType {
    CONNECTED_TREE,      // One component, no cycles
    CONNECTED_GRAPH,     // One component, cycles
    DISCONNECTED_GRAPH,  // Two or more components, all with cycles 
    FOREST,              // Two or more components, all trees
    HYBRID,              // Two or more components, some graphs and some trees
    UNKNOWN              // Degenerate cases; an empty graph for example
}
