import java.util.Collection;
import java.util.Scanner;

import ufrgs.maslab.abstractsimulator.algorithms.model.Field;
import ufrgs.maslab.abstractsimulator.algorithms.model.Point;
import ufrgs.maslab.abstractsimulator.algorithms.Algorithm;
import ufrgs.maslab.abstractsimulator.algorithms.AllNoise;
import ufrgs.maslab.abstractsimulator.algorithms.Optics;

public class MainOptics {
	
	Scanner sc;
    Algorithm algo;

    
    public MainOptics(Algorithm algo)
    {
        sc = new Scanner(System.in);

        this.algo = algo;
    }
    
    void run(boolean reachability)
    {
        // read 'find $C_i$ to $C_j$ clusters'
        // read '$n$ points'
        // read '$x$ $y$' for each point
        sc.next(); // find
        int ci = sc.nextInt();
        int cj = ci;
        if (sc.next().equals("to")) { // otherwise, it is clusters
            cj = sc.nextInt();
            sc.next(); // clusters
        }

        int n = sc.nextInt();

        sc.next(); // points

        Field f = read(n);

        algo.setField(f);

        algo.findParameters(ci, cj, n, f.getWidth(), f.getHeight(), f.getMinX(), f.getMinY());

        algo.run();

        if (reachability) {
            algo.printReachability();
        } else {
            print(f, cj);
        }
    }
    
    /**
     * Read the points
     *
     * @param n Number of points to read
     */
    Field read(int n)
    {
        Field f = new Field();

        for (int i = 0; i < n; i++) {
            f.addPoint(new Point(sc.nextInt(), sc.nextInt(), 0));
        }

        return f;
    }
    
    /**
     * Print the field.
     */
    void print(Field field, int cj)
    {
        Collection<Point> points = field.getAllPoints();

        for (Point p : points) {
            // the algorithm should take care that the cluster number has been
            // assigned correctly (as in, lower or equal to cj)
            // but if this hasn't happened, we want to prevent that peach gives
            // errors about this
            int cluster = p.getCluster();
            if (cluster > cj) {
                cluster = cluster % cj;
                if (cluster == 0) {
                    cluster = 1;
                }
            }
            System.out.println(p.getX() + " " + p.getY() + " " + cluster);
        }
    }
    
	public static void main(String[] args) {
		boolean reach = false;
        Algorithm algo = new Optics();

        for (String arg : args) {
            if ("reach".equals(arg)) {
                reach = true;
            } else if ("noise".equals(arg) || "allnoise".equals(arg)) {
                algo = new AllNoise();
            }
        }

        new MainOptics(algo).run(reach);
		
	}

}
