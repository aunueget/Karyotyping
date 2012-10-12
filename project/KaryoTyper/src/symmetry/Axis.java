package symmetry;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


public class Axis {
	
	private LinkedList<AxisPoint> axis;
	private AxisPoint[] pointsArray;
	private short[][] imgMap;
	/** 
	 * The lower distance acceptable to reasonably acquire an approximate tangent line
	 * for a point.
	 */
	private double tanLowerPrecision;
	/** 
	 * The upper distance acceptable to reasonably acquire an approximate tangent line 
	 * for a point.
	 */
	private double tanUpperPrecision;
	
	public Axis(Collection<Point> axis, Image img, double lowerPrecision, double upperPrecision) {
		imgMap = img.getImageMap();
		createAxis(axis);
		this.tanLowerPrecision = lowerPrecision;
		this.tanUpperPrecision = upperPrecision;
	}
	
	public Axis(Collection<Point> axis, double lowerPrecision, double upperPrecision) {
		createAxis(axis);
		this.tanLowerPrecision = lowerPrecision;
		this.tanUpperPrecision = upperPrecision;
	}

	public void createAxis(Collection<Point> axis) {
		for (Point point : axis) {
			AxisPoint axisPoint = getAxisPointData(point, axis);
			this.axis.add(axisPoint);
		}
	}
	
	/**
	 * 
	 * @param p
	 * @param axis
	 * @return
	 */
	public AxisPoint getAxisPointData(Point p, Collection<Point> axis) {
		for (Point q : axis) {
			double pqDist = Point.distance(p.x, p.y, q.x, q.y);
			if (pqDist >= tanLowerPrecision && pqDist <= tanUpperPrecision) {
				double tanSlope = 0;
				try {
					tanSlope = (p.y - q.y)/(p.x - q.x);
				} catch (ArithmeticException e) {
					tanSlope = Integer.MAX_VALUE;
				}
				double tanIntercept = p.y - (tanSlope * p.x);
				AxisPoint axisPoint = new AxisPoint(p, tanSlope, tanIntercept);
				return axisPoint;
			}
		}
		return null;
	}

	private LinkedList<Point> getAxisFromImage(LinkedList<Point> pointsList, Image img) {
		LinkedList<Point> axisList = new LinkedList<Point>();
		short[][] imgMap = img.getImageMap();
		
		for (Point p : pointsList) {
			imgMap[p.x][p.y] = Short.MAX_VALUE;
		}
		
//		axisList.
		
		return null;
	}

	public int getLength() {
		return axis.size();
	}

	public LinkedList<AxisPoint> getAxis() {
		return axis;
	}
	
	public Point[] getPointsArray() {
		return pointsArray;
	}

	public Point getPrevious(Point p) {
		Point[] pointsArray = axis.toArray(new Point[axis.size()]);
		for (int i = 1; i < axis.size(); i++) {
			if (p.equals(pointsArray[i])) {
				return pointsArray[i-1];
			}
		}
		return null;
	}
	
	public Point getNext(Iterator<Point> iterator) {
		return iterator.next();
	}
	
	public Point getNext(Point p) {
		Point[] pointsArray = axis.toArray(new Point[axis.size()]);
		for (int i = 0; i < axis.size() - 1; i++) {
			if (p.equals(pointsArray[i])) {
				return pointsArray[i+1];
			}
		}
		return null;
		
		//TODO: change to look for point more than 1 pixel along
	}
	
	public String toString() {
		String axisString = "Axis string:\n";
		for (Point p : axis) {
			axisString += "(" + p.x + ", " + p.y + ")\n";
		}
		return axisString;
	}

	/**
	 * Gets a point along the medial axis 5 points away from the requested point.  It searches pixels around
	 * the requested point clockwise until another axis point is encountered.  Then does so again 4 more times.
	 * If the final point is less than 5 points away then it searches again counterclockwise.
	 * @param p
	 * @return
	 */
	public Point getNearbyPoint(Point p) {
		int numCycles = 5;
		Point nearbyPoint = p;
		for (int i = 0; i < numCycles; i++) {
			if (imgMap[p.x][p.y - 1] == Short.MAX_VALUE) {
				nearbyPoint.y--;
				continue;
			}
			if (imgMap[p.x+1][p.y-1] == Short.MAX_VALUE) {
				nearbyPoint.x++;
				nearbyPoint.y--;
				continue;
			}
			if (imgMap[p.x+1][p.y] == Short.MAX_VALUE) {
				nearbyPoint.x++;
				continue;
			}
			if (imgMap[p.x+1][p.y+1] == Short.MAX_VALUE) {
				nearbyPoint.x++;
				nearbyPoint.y++;
				continue;
			}
			if (imgMap[p.x][p.y+1] == Short.MAX_VALUE) {
				nearbyPoint.y++;
				continue;
			}
			if (imgMap[p.x-1][p.y+1] == Short.MAX_VALUE) {
				nearbyPoint.x--;
				nearbyPoint.y++;
				continue;
			}
			if (imgMap[p.x-1][p.y] == Short.MAX_VALUE) {
				nearbyPoint.x--;
				continue;
			}
			if (imgMap[p.x-1][p.y-1] == Short.MAX_VALUE) {
				nearbyPoint.x--;
				nearbyPoint.y--;
				continue;
			}
		}
		return nearbyPoint;
	}
	
}