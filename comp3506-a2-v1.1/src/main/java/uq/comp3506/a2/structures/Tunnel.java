// @edu:student-assignment

package uq.comp3506.a2.structures;

/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 */
public class Tunnel {

    /**
     * The unique tunnel identifier.
     */
    private int tunnelId; 

    /**
     * The x coordinate of the tunnel, a positive integer
     */
    private int xCoord;

    /**
     * The x coordinate of the tunnel, a positive integer
     */
    private int yCoord;

    /**
     * The drain flow radius
     */
    private double radius;


    /** Simple constructor taking a tunnel id, x, y coords, and radius */
    public Tunnel(int t, int x, int y, double r) {
        this.tunnelId = t;
        this.xCoord = x;
        this.yCoord = y;
        this.radius = r;
    }

    /** returns the tunnel id*/
    public int getId() {
        return this.tunnelId;
    }

    /** returns the x coord*/
    public int getX() {
        return this.xCoord;
    }

    /** returns the y coord*/
    public int getY() {
        return this.yCoord;
    }

    /**returns the radius*/
    public double getRadius() {
        return this.radius;
    }

    // You may add more helper functions here, if you wish
}
