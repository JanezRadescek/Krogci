
public class Metrika 
{
	public static int razdalja(Krogec k, int x, int y)
	{
		int c = 0;
		c += (x-k.x)*(x-k.x) + (y-k.y)*(y-k.y);
		return (int)(Math.sqrt(c)- k.r + 0.5);
	}
	
	public static int razdalja(Krogec k, Krogec l)
	{
		int c = 0;
		c += (l.x-k.x)*(l.x-k.x) + (l.y-k.y)*(l.y-k.y);
		return (int)(Math.sqrt(c)- k.r -l.r + 0.5);
	}
	
	public static int razdalja(int x1, int y1, int x2, int y2)
	{
		int c = 0;
		c += (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
		return (int)(Math.sqrt(c) + 0.5);
	}
	
	
}
