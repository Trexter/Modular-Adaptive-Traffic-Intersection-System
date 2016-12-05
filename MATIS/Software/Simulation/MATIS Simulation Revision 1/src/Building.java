import processing.core.PApplet;

public class Building
{
	PApplet parent;
	public float x, y, width, height;
	
	public Building(PApplet _parent, float _x, float _y, float _width, float _height)
	{
		parent = _parent;
		x =_x;
		y= _y;
		width = _width;
		height = _height;
	}
	
	public void draw()
	{
		parent.fill(127);
		parent.rect(x - (width / 2f), y - (height / 2f), width, height);
	}
}
