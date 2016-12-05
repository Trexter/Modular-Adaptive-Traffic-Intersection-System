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

public class Intersection {

	PApplet parent;
	public String type;
	public float x, y;
	public float sideLength = 0;
	
	public int lightLastMillis = 0;
	
	public ArrayList<TrafficLight> trafficLights = new ArrayList<TrafficLight>(12);
	
	public Intersection(PApplet _parent, float _x, float _y, String _type)
	{
		parent = _parent;
		x = _x;
		y = _y;
		type = _type;
		this.setIntersectionType(_type);
	}
	
	public void run()
	{
		this.runLightTimer();
	}
	
	public void draw()
	{
		parent.fill(40, 40, 40);
		parent.rect(x - (sideLength / 2), y - (sideLength / 2), sideLength, sideLength);
		
		for(int i = 0; i < trafficLights.size(); i++)
		{
			trafficLights.get(i).draw();
		}
	}
	
	public void setIntersectionType(String _type)
	{
		//clear out all traffic lights already added just in case...
		trafficLights.clear();
		
		switch(_type)
		{
		case "basic":
			sideLength = 50;
			float halfSideLength = sideLength / 2;
			
			//create the traffic lights and their destinations
			//Light A
			trafficLights.add(new TrafficLight(parent, x - halfSideLength, y + 15));
			trafficLights.get(trafficLights.size() - 1).setTravelRules(true, true, true);
			trafficLights.get(trafficLights.size() - 1).setDestinationCoordinates( x + halfSideLength, y + 15, x + 15, y - halfSideLength, x - 15, y + halfSideLength);
			//Light B
			trafficLights.add(new TrafficLight(parent, x + 15, y + halfSideLength));
			trafficLights.get(trafficLights.size() - 1).setTravelRules(true, true, true);
			trafficLights.get(trafficLights.size() - 1).setDestinationCoordinates(x + 15, y - halfSideLength, x - halfSideLength, y - 15, x + halfSideLength, y + 15);
			//Light C
			trafficLights.add(new TrafficLight(parent, x + halfSideLength, y - 15));
			trafficLights.get(trafficLights.size() - 1).setTravelRules(true, true, true);
			trafficLights.get(trafficLights.size() - 1).setDestinationCoordinates(x - halfSideLength, y - 15, x - 15, y + halfSideLength, x + 15, y - halfSideLength);
			//Light D
			trafficLights.add(new TrafficLight(parent, x - 15, y - halfSideLength));
			trafficLights.get(trafficLights.size() - 1).setTravelRules(true, true, true);
			trafficLights.get(trafficLights.size() - 1).setDestinationCoordinates(x - 15, y + halfSideLength, x + halfSideLength, y + 15, x - halfSideLength, y - 15);
			
			//test d1 = x - halfSideLength, y - 15
			//d2 = x - 15, y + halfSideLength
			//d3 = x + halfSideLength, y + 15
			//d4 = x + 15, y - halfSideLength
			break;
		}
	}
	
	public void runLightTimer()
	{
		switch(type)
		{
		case "basic":
			int dTime = parent.millis() - lightLastMillis;
			if(dTime < 10000)
			{
				trafficLights.get(0).setColor(TrafficLight.GREEN);
				trafficLights.get(2).setColor(TrafficLight.GREEN);
				trafficLights.get(1).setColor(TrafficLight.RED);
				trafficLights.get(3).setColor(TrafficLight.RED);
			}
			else if(dTime >= 10000 && dTime < 14000)
			{
				trafficLights.get(0).setColor(TrafficLight.YELLOW);
				trafficLights.get(2).setColor(TrafficLight.YELLOW);
				trafficLights.get(1).setColor(TrafficLight.RED);
				trafficLights.get(3).setColor(TrafficLight.RED);
			}
			else if(dTime >= 14000 && dTime < 16000)
			{
				trafficLights.get(0).setColor(TrafficLight.RED);
				trafficLights.get(2).setColor(TrafficLight.RED);
				trafficLights.get(1).setColor(TrafficLight.RED);
				trafficLights.get(3).setColor(TrafficLight.RED);
			}
			else if(dTime >= 16000 && dTime < 26000)
			{
				trafficLights.get(0).setColor(TrafficLight.RED);
				trafficLights.get(2).setColor(TrafficLight.RED);
				trafficLights.get(1).setColor(TrafficLight.GREEN);
				trafficLights.get(3).setColor(TrafficLight.GREEN);
			}
			else if(dTime >= 26000 && dTime < 30000)
			{
				trafficLights.get(0).setColor(TrafficLight.RED);
				trafficLights.get(2).setColor(TrafficLight.RED);
				trafficLights.get(1).setColor(TrafficLight.YELLOW);
				trafficLights.get(3).setColor(TrafficLight.YELLOW);
			}
			else if(dTime >= 30000 && dTime < 32000)
			{
				trafficLights.get(0).setColor(TrafficLight.RED);
				trafficLights.get(2).setColor(TrafficLight.RED);
				trafficLights.get(1).setColor(TrafficLight.RED);
				trafficLights.get(3).setColor(TrafficLight.RED);
			}
			else
			{
				lightLastMillis = parent.millis();
			}
			break;
		}
	}
	
}
