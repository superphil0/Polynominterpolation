package com.spreenix.Helpers;

/**
 * A 2-dimensional vector
 * @author Philipp
 *
 */
public class Vector2 {

	/**
	 * The x and y values
	 */
	public float X,Y;
	
	/**
	 * Creates a new Vector2 with (X,Y) = (0,0)
	 */
	public Vector2()
	{
		X=Y=0;
	}
	
	
	/**
	 * takes float as parameter
	 * @param x 
	 * @param y
	 */
	public Vector2(float x, float y)
	{
		X = x;
		Y = y;
	}
	
	/**
	 * takes int as parameter
	 * @param x
	 * @param y
	 */
	public Vector2(int x, int y)
	{
		X = x;
		Y = y;
	}
	
	/**
	 * copy Constructor
	 * @param vec
	 */
	public Vector2(Vector2 vec)
	{
		this(vec.X,vec.Y);
	}
	
	public String toString()
	{
		return "X: " + X + " Y:" + Y;
	}
	
	/**
	 * object will not be affected
	 * @param vector2
	 * @return the result
	 */
	public Vector2 subtract(Vector2 vector2)
	{
		return new Vector2(this.X-vector2.X, this.Y -vector2.Y);
	}
	/**
	 * object will not be affected
	 * @param vector2
	 * @return the result
	 */
	public Vector2 add(Vector2 vector2)
	{
		return new Vector2(this.X+vector2.X, this.Y +vector2.Y);
	}
	
	
	/** 
	 * Calculates the Euclidian distance
	 * object will not be affected
	 * @param vector2
	 * @return distance from object to vector2
	 */
	public float getDistance(Vector2 vector2)
	{
		Vector2 helpV = subtract(vector2);
		float result = (float) Math.sqrt(helpV.X*helpV.X + helpV.Y * helpV.Y);
		return result;
	}
	
	
	/**
	 * calculate the unit vector of the vector
	 * @return unit Vector of the object
	 */
	public Vector2 getUnitVector()
	{
		float distance = this.getDistance(new Vector2());
		Vector2 unitVec = new Vector2(this.X/distance, this.Y/ distance);
		return unitVec;
	}
	
	
	/**
	 * Multiply two vectory x*x and y*y
	 * @param multiplier
	 * @return vectors multiplied
	 */
	public Vector2 multiply(float multiplier)
	{
		return new Vector2(this.X * multiplier, this.Y * multiplier);
	}
}
