/** A class that represents a path via pursuit curves. */
public class Path {
	public Point curr;
	public Point next;

	

	public Path(double x, double y) {
		this.next = new Point(x, y);
		this.curr = new Point (1,2);
	}

	public void iterate(double dx, double dy){
		this.curr.x = this.next.x;
		this.curr.y = this.next.y;
		this.next.x = this.next.x + dx;
		this.next.y = this.next.y + dy;
		
		
	}

    // TODO

}
