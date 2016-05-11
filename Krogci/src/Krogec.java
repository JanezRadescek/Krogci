import java.awt.Color;


public class Krogec 
{
	int x;
	int y;
	int r;
	Color barva;
	
	Krogec(int x, int y, int r, Color barva)
	{
		this.x = x;
		this.y = y;
		this.r = r;
		this.barva = barva;
	}
	
	void nastaviKrogec(int x, int y, int r, Color barva)
	{
		this.x = x;
		this.y = y;
		this.r = r;
		this.barva = barva;
	}
}
