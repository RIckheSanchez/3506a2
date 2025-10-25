/**
* Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
*/

import uq.comp3506.a2.Problems;
import uq.comp3506.a2.structures.Heap;
import uq.comp3506.a2.structures.OrderedMap;
import uq.comp3506.a2.structures.UnorderedMap;
import uq.comp3506.a2.structures.Vertex;
import uq.comp3506.a2.structures.Edge;
import uq.comp3506.a2.structures.Tunnel;
import uq.comp3506.a2.structures.Entry;
import uq.comp3506.a2.structures.TopologyType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

11111
public class TestProblems {

    // The series of tests that need to be implemented... We have provided
    // some for you, but you need to make/work on your own as well.
    public static void testTunnelLighting() {
        System.out.println("Testing 'Tunnel Lighting'");

        // Here's the test from the specsheet
        assert Math.abs(Problems.tunnelLighting(12,
                        new ArrayList<>(Arrays.asList(4, 7, 8, 1))) - 4) <= 0.000001;

        // you can use this one if you prefer jUnit testing instead
        // assertEquals(4, Problems.tunnelLighting(12,
        //              new ArrayList<>(Arrays.asList(4, 7, 8, 1))), 0.000001);
    }

    public static void testTopologyDetection() {
        System.out.println("Testing 'Topology Detection'");

        // this is a subgraph of the first specsheet graph
        List<Vertex<String>> vertices = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            vertices.add(new Vertex<>(i, "dr. a waz here"));
        }
        ArrayList<Edge<String, Character>> connectedGraph = new ArrayList<>(Arrays.asList(
            new Edge<>(vertices.get(0), vertices.get(1), 'G'),
            new Edge<>(vertices.get(0), vertices.get(2), 'U'),
            new Edge<>(vertices.get(1), vertices.get(4), 'R'),
            new Edge<>(vertices.get(1), vertices.get(2), 'K'),
            new Edge<>(vertices.get(1), vertices.get(3), 'E'),
            new Edge<>(vertices.get(2), vertices.get(5), 'Y'),
            new Edge<>(vertices.get(2), vertices.get(6), 'T'),
            new Edge<>(vertices.get(3), vertices.get(5), 'I'),
            new Edge<>(vertices.get(3), vertices.get(6), 'M'),
            new Edge<>(vertices.get(5), vertices.get(6), 'E'))
        );
        assert Problems.topologyDetection(connectedGraph) == TopologyType.CONNECTED_GRAPH;


        // this is the last of the four specsheet examples
        ArrayList<Edge<String, Character>> disconnectedTree = new ArrayList<>(Arrays.asList(
            new Edge<>(vertices.get(0), vertices.get(1), 'B'),
            new Edge<>(vertices.get(0), vertices.get(2), 'A'),
            new Edge<>(vertices.get(1), vertices.get(4), 'R'),
            new Edge<>(vertices.get(3), vertices.get(6), 'R'),
            new Edge<>(vertices.get(5), vertices.get(6), 'Y'))
        );
        assert Problems.topologyDetection(disconnectedTree) == TopologyType.FOREST;

        // you can use these ones if you prefer jUnit testing instead
        // assertEquals(TopologyType.CONNECTED_GRAPH, Problems.topologyDetection(connectedGraph));
        // assertEquals(TopologyType.FOREST, Problems.topologyDetection(disconnectedTree));
    }

    public static void testRouteManagement() {
        System.out.println("Testing 'Route Management'");

        // Here's the test from the specsheet
        List<Vertex<Integer>> vertices = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            vertices.add(new Vertex<>(i, i));
        }

        List<Edge<Integer, Integer>> edgeList = new ArrayList<>(Arrays.asList(
                new Edge<>(vertices.get(0), vertices.get(1), 7),
                new Edge<>(vertices.get(0), vertices.get(2), 2),
                new Edge<>(vertices.get(1), vertices.get(2), 1),
                new Edge<>(vertices.get(1), vertices.get(3), 9),
                new Edge<>(vertices.get(1), vertices.get(4), 5),
                new Edge<>(vertices.get(2), vertices.get(6), 2),
                new Edge<>(vertices.get(3), vertices.get(5), 4),
                new Edge<>(vertices.get(4), vertices.get(6), 2),
                new Edge<>(vertices.get(5), vertices.get(6), 2),
                new Edge<>(vertices.get(5), vertices.get(7), 7),
                new Edge<>(vertices.get(6), vertices.get(7), 5),
                new Edge<>(vertices.get(8), vertices.get(9), 100),
                new Edge<>(vertices.get(9), vertices.get(10), 100)
        ));

        Vertex<Integer> origin = vertices.get(5); // vertex F
        int threshold = 6;

        List<Entry<Integer, Integer>> expected = new ArrayList<>(Arrays.asList(
                new Entry<>(0, 6),
                new Entry<>(1, 5),
                new Entry<>(2, 4),
                new Entry<>(3, 4),
                new Entry<>(4, 4),
                new Entry<>(5, 0),
                new Entry<>(6, 2)
        ));

        List<Entry<Integer, Integer>> actual = Problems.routeManagement(edgeList, origin, threshold);

        assert expected.containsAll(actual) && actual.containsAll(expected);
        // you can use this one if you prefer jUnit testing instead
        // assertEquals(expected, actual);
    }

    public static void testTotallyFlooded() {
        System.out.println("Testing 'Totally Flooded'");

        // Here's the test from the spec sheet
        List<Tunnel> tunnels = new ArrayList<>();
        tunnels.add(new Tunnel(0, 1, 0, 1.6));
        tunnels.add(new Tunnel(1, 0,1,0.6));
        tunnels.add(new Tunnel(2, 3,1,2.6));
        tunnels.add(new Tunnel(3, 4,3,1.2));
        tunnels.add(new Tunnel(4,1,3,1.6));
        tunnels.add(new Tunnel(5, 4,4,1.2));
        
         assert Problems.totallyFlooded(tunnels) == 2;
        // you can use this one if you prefer jUnit testing instead
        // assertEquals(2, Problems.totallyFlooded(tunnels));
    }

    public static void testSusDomination() {
        System.out.println("Testing 'BRCC Domination'");

        // Here's the specsheet example
        List<Integer> sites = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        List<List<List<Integer>>> rules = List.of(
                List.of(List.of(2, 1)),
                List.of(List.of(2, 3), List.of(0)),
                List.of(List.of(0, 1)),
                List.of(List.of(0, 1), List.of(0, 1, 2)),
                List.of(List.of(2, 5), List.of(0, 3)),
                List.of(List.of(0, 1, 3, 4))
        );
        List<Integer> startingSites = List.of(1, 2);

        assert Problems.susDomination(sites, rules, startingSites) == 0;

        // you can use this one if you prefer jUnit testing instead
        // assertEquals(0L, Problems.susDomination(sites, rules, startingSites));
    }


    // Try to call the given test based on the input
    public static void dispatch(String str) {
        switch (str.toLowerCase()) {
            case "lights":
                testTunnelLighting();
                return;
            case "topo":
                testTopologyDetection();
                return;
            case "routes":
                testRouteManagement();
                return;
            case "flood":
                testTotallyFlooded();
                return;
            case "dominate":
                testSusDomination();
                return;
            default:
                throw new IllegalArgumentException("Unknown command: " + str);
        }
    }

    // Does what it says on the tin
    private static void usage() {
        System.out.println("Usage: java TestProblems <commands>");
        System.out.println("Commands:");
        System.out.println("  lights");
        System.out.println("  topo");
        System.out.println("  routes");
        System.out.println("  flood");
        System.out.println("  dominate");
    }

    public static void main(String[] args) {

        // Basic checking - make sure a command is provided
        if (args.length == 0) {
            usage();
            return;
        }

        // Walk the commands and try to dispatch them
        for (int i = 0; i < args.length; ++i) {
            dispatch(args[i]);
        }

        // profit??
    }

}
