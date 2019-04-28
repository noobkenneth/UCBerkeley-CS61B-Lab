public class Planet{
	double xxPos;
	double yyPos;
	double xxVel;
	double yyVel;
	double mass;
	String imgFileName;
	private double G = 6.67 * Math.pow(10, -11);

	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}

	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;

	}

	public double calcDistance(Planet p) {
		double dx = this.xxPos - p.xxPos;
		double dy = this.yyPos - p.yyPos;
		double r = Math.sqrt(dx*dx + dy*dy);
		return r;
	}

	public double calcForceExertedBy(Planet p) {
		double r = calcDistance(p);
		return (G * this.mass * p.mass) / Math.pow(r, 2); 
	}

	public double calcForceExertedByX(Planet p) {
		double force = calcForceExertedBy(p);
		double dx = p.xxPos - this.xxPos;
		double r = calcDistance(p);
		return (force * dx) / r;
	}

	public double calcForceExertedByY(Planet p) {
		double force = calcForceExertedBy(p);
		double dy = p.yyPos - this.yyPos;
		double r = calcDistance(p);
		return (force * dy) / r;
	}

	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double netforceX = 0;
		 for (int i=0; i<allPlanets.length; i++) {
			if (allPlanets[i] != this) {
				netforceX += calcForceExertedByX(allPlanets[i]);
			}

		 }
		 return netforceX;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double netforceY = 0;
		 for (int i=0; i<allPlanets.length; i++) {
			if (allPlanets[i] != this) {
				netforceY += calcForceExertedByY(allPlanets[i]);
			}

		 }
		 return netforceY;
	}

	public void update(double dt, double xforce, double yforce) {
		double xacc = xforce / this.mass;
		double yacc = yforce / this.mass; 
		double newxvelocity = this.xxVel + dt * xacc;
		double newyvelocity = this.yyVel + dt * yacc;
		double newxposition = this.xxPos + dt * newxvelocity;
		double newyposition = this.yyPos + dt * newyvelocity;
		this.xxVel = newxvelocity;
		this.yyVel = newyvelocity; 
		this.xxPos = newxposition;
		this.yyPos = newyposition;
	}

	public void draw() {
		String filename = "./images/" + this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, filename);

	}

}













