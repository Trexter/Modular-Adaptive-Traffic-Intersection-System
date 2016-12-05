import java.util.ArrayList;

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

public class LayoutHandler {
	
	PApplet parent;
	
	public ArrayList<Lane> lanes = new ArrayList<Lane>(10);
	public ArrayList<Building> buildings = new ArrayList<Building>(10);
	public ArrayList<Intersection> intersections =  new ArrayList<Intersection>(10);
	
	public LayoutHandler(PApplet _parent)
	{
		parent = _parent;
	}
	
	public void drawLanes()
	{
		for(int i = 0; i < lanes.size(); i++)
		{
			lanes.get(i).draw();
		}
	}
	
	public void drawBuildings()
	{
		for(int i = 0; i < buildings.size(); i++)
		{
			buildings.get(i).draw();
		}
	}
	
	public void updateWorldTime(float _time)
	{
		
	}
	
	public void runIntersections()
	{
		for(int i = 0; i < intersections.size(); i++)
		{
			intersections.get(i).run();
		}
	}
	
	public void drawIntersections()
	{
		for(int i = 0; i < intersections.size(); i++)
		{
			intersections.get(i).draw();
		}
	}
	
	/*
	 * loads the map for the specified simulation using a file 
	 */
	public void loadLayout(String path)
	{
		//loads the parts of the map itself
		String[] layoutFile = parent.loadStrings(path);
		
		//goes through and actually adds the parts to there corresponding arraylist
		for(int i = 0; i < layoutFile.length; i++)
		{
			String[] splitString = layoutFile[i].split(",");
			switch(splitString[0])
			{
			//if it is a lane
			case "lane":
				lanes.add(new Lane(parent, parent.parseFloat(splitString[1]), parent.parseFloat(splitString[2]), parent.parseFloat(splitString[3]), parent.parseFloat(splitString[4])));
				break;
				
			case "building":
				buildings.add(new Building(parent, parent.parseFloat(splitString[1]), parent.parseFloat(splitString[2]), parent.parseFloat(splitString[3]), parent.parseFloat(splitString[4])));
				break;
			
			case "intersection":
				intersections.add(new Intersection(parent, parent.parseFloat(splitString[1]), parent.parseFloat(splitString[2]), splitString[3]));
				break;
			}
		}
	}
	
}
