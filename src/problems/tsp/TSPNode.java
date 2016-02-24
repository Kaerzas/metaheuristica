package problems.tsp;

public class TSPNode {

	double x, y;
	
	public TSPNode(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public double distance(TSPNode n){
		return Math.sqrt(Math.pow(this.x - n.getX(), 2) + Math.pow((this.y - n.getY()), 2));
	}
	
}
