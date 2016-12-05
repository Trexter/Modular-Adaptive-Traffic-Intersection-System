
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

public class car {
	PApplet parent;
	
	private float startX, startY, destX, destY;
	private float X, Y; // the actual coordinates of the vehicle
	public boolean hasPath = false;
	
	private PathFinder pathFinder = new PathFinder();
	
	public Path vehiclePath;
	
	public car(PApplet _parent, float _startX, float _startY, float _destX, float _destY)
	{
		parent = _parent;
		startX = _startX;
		startY = _startY;
		destX = _destX;
		destY = _destY;
	}
	
	public void run(LayoutHandler _LH)
	{
		if(hasPath)
		{
			
		}
		else
		{
			//generate the path for the vehicle to travel on the first run of the vehicle
			vehiclePath = pathFinder.findPath(startX, startY, destX, destY, _LH);
			hasPath = true;
		}
	}
	
	public void draw()
	{
		parent.fill(255, 255, 255, 255);
		parent.rect(X - 5, Y - 5, 7, 7);
	}

}
