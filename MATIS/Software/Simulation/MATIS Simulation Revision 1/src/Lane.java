

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

public class Lane {
	PApplet parent;
	public float startX, startY, endX, endY;
	final static float laneWidth = 10;
	
	public Lane(PApplet _parent, float _startX, float _startY, float _endX, float _endY)
	{
		parent = _parent;
		startX = _startX;
		startY = _startY;
		endX = _endX;
		endY = _endY;
	}
	
	public void run()
	{
		
	}
	
	public void draw()
	{
		parent.noStroke();
		if(startX == endX)
		{
			if(startY < endY)
			{
				parent.fill(40, 40, 40);
				parent.rect(startX - (laneWidth / 2f), startY, laneWidth, parent.abs(endY - startY));
				parent.fill(255, 255, 255);
				parent.rect(startX + (laneWidth / 2f) - 1f, startY, 1f, parent.abs(endY - startY));
				parent.rect(startX - (laneWidth / 2f), startY, 1f, parent.abs(endY - startY));
			}
			else
			{
				parent.fill(40, 40, 40);
				parent.rect(endX - (laneWidth / 2f), endY, laneWidth, parent.abs(endY - startY));
				parent.fill(255, 255, 255);
				parent.rect(endX + (laneWidth / 2f) - 1f, endY, 1f, parent.abs(endY - startY));
				parent.rect(endX - (laneWidth / 2f), endY, 1f, parent.abs(endY - startY));
			}
		}
		else if(startY == endY)
		{
			if(startX < endX)
			{
				parent.fill(40, 40, 40);
				parent.rect(startX, startY - (laneWidth / 2f), parent.abs(endX - startX), laneWidth);
				parent.fill(255, 255, 255);
				parent.rect(startX, startY + (laneWidth / 2f), parent.abs(endX - startX), 1f);
				parent.rect(startX, startY - (laneWidth / 2f) - 1f, parent.abs(endX - startX), 1f);
			}
			else
			{
				parent.fill(40, 40, 40);
				parent.rect(endX, endY - (laneWidth / 2f), parent.abs(endX - startX), laneWidth);
				parent.fill(255, 255, 255);
				parent.rect(endX, endY + (laneWidth / 2f), parent.abs(endX - startX), 1f);
				parent.rect(endX, endY - (laneWidth / 2f) - 1f, parent.abs(endX - startX), 1f);
			}
		}
		else 
		{
			
		}
	}
	
}
