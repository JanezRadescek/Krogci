
public class AlgManjsanje 
{
	OrgSlika orgSlika;
	NovaSlika novaSlika;
	int zacetniR;
	int visina;
	int sirina;
	
	public AlgManjsanje(String path, int R) 
	{
		orgSlika = new OrgSlika(path);
		novaSlika = new NovaSlika();
		zacetniR = R;
		visina = orgSlika.image.getHeight();
		sirina = orgSlika.image.getWidth();
	}
	
	
	
}
