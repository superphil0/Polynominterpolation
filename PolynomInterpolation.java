package com.spreenix.Helpers;

/**
 * A class for static polynominterpolation for the slingshot movement
 * @author Philipp
 *
 */
public class PolynomInterpolation {
	
	/**
	 * @param vertices n+1 vertices lead to n coefficients
	 * @return coefficients a(i) for the polynom of a form a(i) * x(i)  i = 0...n
	 */
	public static float[] NewtonPolynom(Vector2[] vertices)
	{
		float[] x,y;
		x = new float[vertices.length];
		y = new float[vertices.length];
		for(int i = 0; i < vertices.length; i++)
		{
			x[i] = vertices[i].X;
			y[i] = vertices[i].Y;
		}
		float[] tmp = new float[y.length];
		tmp[0] = y[0];
		y = pyramideScheme(x,y,1); // from xvalues
		for(int i = 0; i< y.length; i++)
		{
			tmp[i+1] = y[i];
		}
		y = tmp;          

		return calculateNiceCoefficients(x,y);		
	}
	
	/** input = the coefficients for the polynoms
	 * now we want to compute all the coefficients a(i) for the i-th components of a form a(i)*x^i
	 * @param x
	 * @param y
	 * @return
	 */
	private static float[] calculateNiceCoefficients(float[] x,float[] y)
	{
		float[] coefficients = new float[x.length];
		for(int i = 0; i <coefficients.length; i++)
		{
			coefficients[i] = 0;
		}
		for(int i = 0; i< x.length; i++)
		{
			for(int j = 0; j <i+1; j++)
			{
				float coef = getFactor(x, i,j);
				coefficients[j] += y[i] * coef;
			}
		}
		return coefficients;
	}
	/**
	 * @param x
	 * @param potenzInsgesamt
	 * @param potenz
	 * @return the x^potenz component of the polynom a*(x+k1)*(x+k2)...(x+ki) = all combinations
	 */
	private static float getFactor(float[] x,  int potenzInsgesamt, int potenz)
	{
		if(potenzInsgesamt == potenz) return 1;
		long factorCount = binCoeff(potenzInsgesamt, potenz);
		float result = 0;
		int[] counter = new int[potenzInsgesamt-potenz];
		for(int i = 0; i< counter.length;i++)
		{
			counter[i] = i;
		}
		float pick;
		// pick k out of n and add them
		// one pick is multiplied
		// a*(x+b)*(x+c)*(x+d) ==> a*x^3 + a*x^2*(b+c+d) + a*x(bc+bd+cd) +a*b*c*d
		// so b*c*d would be one pick b*c and b*d too
		for(int i = 0; i < factorCount; i++)
		{			
			pick = 1;
			for(int j = 0; j < potenzInsgesamt -potenz ; j++) // how many to pick from potenzInsgesamt
			{
				pick *= x[counter[j]]*-1;
			}
			result += pick; 
			if(i+1 == factorCount)return result;
			boolean carryFlag = false;
			for(int j = counter.length-1; j>= 0; j--)
			{
				if(counter[j] >= potenzInsgesamt-(counter.length-j) && !carryFlag)
				{
					carryFlag = true; // greift auf variable zu die als nächste verändert wird

				}
				else if (carryFlag)
				{
					if(counter[j] >= potenzInsgesamt-(counter.length-j) )
					{
						carryFlag = true;
					}
					else
					{
						counter[j]++;
						for(int k = 1; k <= counter.length-j-1; k++)
						{
							counter[j+k] = counter[j+k-1]+1;
						}
						carryFlag = false;
						break;
					}
				}
				else
				{					
					counter[j]++;
					break;
				}
			}
		}		
		return result;
	}

	/**
	 * @param n
	 * @param k
	 * @return return n over k ; the binomeal coefficient
	 */
	private static long binCoeff(int n, int k)
	{
	    if ((n < 0) || (k < 0) || (k > n))
	        throw new IllegalArgumentException(n + ", " + k);
	    if (k > n/2) k = n - k;
	 
	    long result = 1;
	 
	    for (int i = n - k + 1; i <= n; i++)
	        result = result*i;
	    for (int i = 2; i <= k; i++)
	        result = result /i;
	 
	    return result;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param plain how many recursion steps
	 * @return coefficients
	 */
	private static float[] pyramideScheme(float[] x, float[] y, int plain)
	{
		float[] coefficients = new float[y.length-1];
		float[] solutions = new float[y.length-1]; 
		for(int i = 0; i< y.length-1; i++)
		{
			solutions[i] = differenceQuotient(new Vector2(x[i],y[i]),new Vector2(x[i+plain],y[i+1]));			
		}
		if(solutions.length > 1)
		{
			float[] temp = pyramideScheme(x,solutions,plain+1);
			coefficients[0] = solutions[0];
			for(int i = 0; i < temp.length; i++ )
			{
				coefficients[i+1] = temp[i];
			}			
			
		}
		else
		{
			coefficients[0] =solutions[0];
		}
		return coefficients; //coefficients;
	}
	
	
	/**
	 * @param vec1
	 * @param vec2
	 * @return the difference quotient of two vectors
	 */
	private static float differenceQuotient(Vector2 vec1, Vector2 vec2)
	{
		return  (vec2.Y-vec1.Y) / (vec2.X - vec1.X);
	}
}
