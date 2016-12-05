import processing.core.PApplet;

/*
 * This is the MATIS Traffic Simulation Program created by Kevin Sheridan.
 * 
 */
//=================================================================================================
//Copyright (c) 2011, Kevin Sheridan, Trexter
//All rights reserved.

//Redistribution and use in source and binary forms, with or without
//modification, are permitted provided that the following conditions are met:
//  * Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
//  * Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
//  * Neither the name of the Simulation, Trexter nor the names of its contributors may be used to
//    endorse or promote products derived from this software without
//    specific prior written permission.

//THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
//WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
//DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
//DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
//(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
//LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
//ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
//(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
//SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//=================================================================================================
/*Description:
 * 
 */

public class TrafficLight {
	
	PApplet parent;
	
	final static int GREEN = 1;
	final static int YELLOW = 2;
	final static int RED = 3;
	
	private int currentColor = 3;
	
	//all of the possible coordinates necessary for a vehicle to navigate an intersection
	public float lightX, lightY, straightDestX, straightDestY, leftDestX, leftDestY, rightDestX, rightDestY;
	
	public boolean allowStraight = false;
	public boolean allowRight = false;
	public boolean allowLeft = false;
	
	public TrafficLight(PApplet _parent, float _lightX, float _lightY)
	{
		parent = _parent;
		lightX = _lightX;
		lightY = _lightY;
	}
	
	public void setTravelRules(boolean straight, boolean right, boolean left)
	{
		allowStraight = straight;
		allowRight = right;
		allowLeft = left;
	}
	
	//sets the destination coordinates of the traffic lights for each direction. if the light doesn't allow a right left or straight just set any value
	public void setDestinationCoordinates(float _straightDestX, float _straightDestY, float _leftDestX, float _leftDestY, float _rightDestX, float _rightDestY)
	{
		straightDestX = _straightDestX;
		straightDestY = _straightDestY;
		leftDestX = _leftDestX;
		leftDestY = _leftDestY;
		rightDestX = _rightDestX;
		rightDestY = _rightDestY;
	}
	
	public void setColor(int _inputVal)
	{
		currentColor = _inputVal;
	}
	
	public int getColor()
	{
		return currentColor;
	}
	
	public void draw()
	{
		switch(currentColor)
		{
		case 1:
			parent.fill(0,255,0,100);
			break;
			
		case 2:
			parent.fill(255,255,0,100);
			break;
			
		case 3:
			parent.fill(255,0,0,100);
			break;
			
		default:
			parent.fill(255,0,0,100);
			break;
		}
		
		//actually draw the rectangle 
		
		parent.ellipse(lightX, lightY, 10, 10);
	}
	
}
