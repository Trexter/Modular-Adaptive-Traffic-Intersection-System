import java.util.ArrayList;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.math.*;
import java.sql.Array;

import com.sun.javafx.geom.Vec2f;
import com.sun.nio.file.SensitivityWatchEventModifier;
import com.sun.prism.j2d.paint.MultipleGradientPaint.CycleMethod;

import sun.util.resources.cldr.de.CurrencyNames_de_LU;

/*
 * This is the MATIS Traffic Simulation Program created by Kevin Sheridan.
 * 
 */
//=================================================================================================
//Copyright (c) 2015, Kevin Sheridan, Trexter
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
 * This is a pathfinding algorithm designed by Kevin Sheridan based off the a* algorithm. It
 * has been adapted to work in a simulation for the purpose of planning paths for simulated vehicles
 * based off intersections
 */

public class PathFinder {
	
	LayoutHandler LH;
	
	public ArrayList<Vec2f> finalIntersections = new ArrayList<Vec2f>(2); // used to store the potential final intersections
	
	public PathFinder()
	{
		
	}
	
	public Path findPath(float _startX, float _startY, float _destX, float _destY, LayoutHandler _LH)
	{
		LH = _LH;
		//these are the lists that the actual path is stored in
		ArrayList<Vec2f> coordinates = new ArrayList<Vec2f>(10);
		ArrayList<String> directions = new ArrayList<String>(10);
		
		int buildingDestinationNumber = 0; //the index of the building
		int nearestPoint1 = 0; // used for finding the closest start point
		int startIntersectionIndex = 0;
		int closestLaneNumberToDestBuilding = 0; // the name is pretty self explanatory
		
		//important algorithm (a*) variables
		ArrayList<PFNode> openList = new ArrayList<PFNode>(10); // list for a*
		ArrayList<PFNode> closedList = new ArrayList<PFNode>(10); // list for a*
		PFNode q;
		//begin the algorithm
		
		//find the nearest start coordinates based on square distance------------------------
		
		for(int i = 0; i < LH.lanes.size(); i++)
		{
			//find the distances
			double currentClosestDist = Math.abs(Math.sqrt((Math.pow(LH.lanes.get(nearestPoint1).startX, 2) - 
					Math.pow(_startX, 2)) + (Math.pow(LH.lanes.get(nearestPoint1).startY, 2) - Math.pow(_startY, 2))));
			double distToCurrentIteration = Math.abs(Math.sqrt((Math.pow(LH.lanes.get(i).startX, 2) - 
					Math.pow(_startX, 2)) + (Math.pow(LH.lanes.get(i).startY, 2) - Math.pow(_startY, 2))));
			
			System.out.print("distance:");
			System.out.println(distToCurrentIteration);
			
			if(distToCurrentIteration < currentClosestDist)
			{
				nearestPoint1 = i;
			}
		}
		
		//add the starting point to the coordinates and label it start
		coordinates.add(new Vec2f(LH.lanes.get(nearestPoint1).startX, LH.lanes.get(nearestPoint1).startY));
		directions.add("start");
		//REMEMBER THAT YOU IDIOT!!!!!!
		System.out.println("added first point 'start'");
		//*************************************************************************************************
		
		//find the building nearest to the Destination coordinates
		//ultimately sets the building destination number to the ultimate destination of the vehicle
		for(int i = 0; i < LH.buildings.size(); i++)
		{
			//calculate the distances
			double currentClosestDist = Math.abs(Math.sqrt((Math.pow(LH.buildings.get(buildingDestinationNumber).x, 2) - 
					Math.pow(_destX, 2)) + (Math.pow(LH.buildings.get(buildingDestinationNumber).y, 2) - Math.pow(_destY, 2))));
			double distToCurrentIteration = Math.abs(Math.sqrt((Math.pow(LH.buildings.get(i).x, 2) - 
					Math.pow(_destX, 2)) + (Math.pow(LH.buildings.get(i).y, 2) - Math.pow(_destY, 2))));
			
			if(distToCurrentIteration < currentClosestDist)
			{
				buildingDestinationNumber = i;
			}
		}
		System.out.println("found the closest building " + buildingDestinationNumber);
		
		//now.......find the closest lane to the destination-------------------------------
		
		for(int i = 0; i < LH.lanes.size(); i++)
		{
			float currentClosestDist = 4000f;
			float iterationDistance = 4000f;
			System.out.println("ran lane finder");
			
			currentClosestDist = (float)(Math.abs((LH.lanes.get(closestLaneNumberToDestBuilding).endY - LH.lanes.get(closestLaneNumberToDestBuilding).startY) * 
					LH.buildings.get(buildingDestinationNumber).x - 
					(LH.lanes.get(closestLaneNumberToDestBuilding).endX - LH.lanes.get(closestLaneNumberToDestBuilding).startX) *
					LH.buildings.get(buildingDestinationNumber).y + LH.lanes.get(closestLaneNumberToDestBuilding).endX * 
					LH.lanes.get(closestLaneNumberToDestBuilding).startY - 
					LH.lanes.get(closestLaneNumberToDestBuilding).endY * LH.lanes.get(closestLaneNumberToDestBuilding).startX) /
					Math.sqrt(Math.pow(LH.lanes.get(closestLaneNumberToDestBuilding).endY - LH.lanes.get(closestLaneNumberToDestBuilding).startY, 2.0) + 
					Math.pow(LH.lanes.get(closestLaneNumberToDestBuilding).endX - LH.lanes.get(closestLaneNumberToDestBuilding).startX, 2.0)));
			
			iterationDistance = (float)(Math.abs((LH.lanes.get(i).endY - LH.lanes.get(i).startY) * 
					LH.buildings.get(buildingDestinationNumber).x - 
					(LH.lanes.get(i).endX - LH.lanes.get(i).startX) *
					LH.buildings.get(buildingDestinationNumber).y + LH.lanes.get(i).endX * 
					LH.lanes.get(i).startY - 
					LH.lanes.get(i).endY * LH.lanes.get(i).startX) /
					Math.sqrt(Math.pow(LH.lanes.get(i).endY - LH.lanes.get(i).startY, 2.0) + 
					Math.pow(LH.lanes.get(i).endX - LH.lanes.get(i).startX, 2.0)));
			
			
			if(iterationDistance <= currentClosestDist)
			{
				if(iterationDistance < currentClosestDist)
				{
					closestLaneNumberToDestBuilding = i;
					System.out.println("found the closest lane " + i + " " + iterationDistance);
				}
				else
				{
					// the lines are on top of each other
					ArrayList<Float> tempVals = new ArrayList<Float>(4);
					tempVals.add(this.euclideanDistance(LH.lanes.get(i).startY, LH.buildings.get(buildingDestinationNumber).y,
							LH.lanes.get(i).startX, LH.buildings.get(buildingDestinationNumber).x));
					tempVals.add(this.euclideanDistance(LH.lanes.get(i).endY, LH.buildings.get(buildingDestinationNumber).y,
							LH.lanes.get(i).endX, LH.buildings.get(buildingDestinationNumber).x));
					tempVals.add(this.euclideanDistance(LH.lanes.get(closestLaneNumberToDestBuilding).startY, LH.buildings.get(buildingDestinationNumber).y,
							LH.lanes.get(closestLaneNumberToDestBuilding).startX, LH.buildings.get(buildingDestinationNumber).x));
					tempVals.add(this.euclideanDistance(LH.lanes.get(closestLaneNumberToDestBuilding).endY, LH.buildings.get(buildingDestinationNumber).y,
							LH.lanes.get(closestLaneNumberToDestBuilding).endX, LH.buildings.get(buildingDestinationNumber).x));
					
					int tempValShortestIndex = 0;
					for(int j = 0; j < tempVals.size(); j++)
					{
						if(tempVals.get(j).floatValue() < tempVals.get(tempValShortestIndex).floatValue())
						{
							tempValShortestIndex = j;
						}
					}
					
					switch(tempValShortestIndex)
					{
					case 0:
						closestLaneNumberToDestBuilding = i;
						break;
					case 1:
						closestLaneNumberToDestBuilding = i;
						break;
					case 2:
						closestLaneNumberToDestBuilding = closestLaneNumberToDestBuilding;
						break;
					case 3:
						closestLaneNumberToDestBuilding = closestLaneNumberToDestBuilding;
						break;
					}
					
					System.out.println("found lanes that are the same distance away but chose: " + closestLaneNumberToDestBuilding);
					
				}
			}
		}
		
		System.out.println("passed the lane finder line 109 - clostest lane is: " + closestLaneNumberToDestBuilding);
		
		//find the two adjacent intersections to the dest building's closest lane-----------------------------------------------
		//first go from beginning to end ----->------>
		boolean intersectionFound = false; // used to temporarily determine if the sub-algorithm should continue or stop
		int nextLaneIndex = closestLaneNumberToDestBuilding;
		//this vec2f is used to store the current position to search from
		Vec2f currentPosition = new Vec2f(LH.lanes.get(closestLaneNumberToDestBuilding).endX, LH.lanes.get(closestLaneNumberToDestBuilding).endY);
		System.out.println("the lane found's ending coordinates are " + currentPosition.x + " " + currentPosition.y);
		
		while(!intersectionFound)
		{
			System.out.println("searching for adjacent intersections");
			int intersectionIndex = this.getIndexOfIntersection(currentPosition.x, currentPosition.y);
			if(intersectionIndex != -1)
			{
				System.out.println("found an intersection: " + intersectionIndex);
				intersectionFound = true;
				finalIntersections.add(new Vec2f(LH.intersections.get(intersectionIndex).x, LH.intersections.get(intersectionIndex).y));
				break;
			}
			else
			{
				//not intersection get the next position
				nextLaneIndex = this.getAttachedLane(currentPosition.x, currentPosition.y, LH.lanes.get(nextLaneIndex));
				if(nextLaneIndex != -1)
				{
					//set the new current position
					currentPosition.x = LH.lanes.get(nextLaneIndex).endX;
					currentPosition.y = LH.lanes.get(nextLaneIndex).endY;
				}
				else
				{
					//there is not another lane to go to
					System.out.println("couldnt search any further down the road");
					break;
				}
			}
		}
		
		//then go from end to beginning <-------<-----
		intersectionFound = false;
		nextLaneIndex = closestLaneNumberToDestBuilding;
		currentPosition.x = LH.lanes.get(closestLaneNumberToDestBuilding).startX;
		currentPosition.y = LH.lanes.get(closestLaneNumberToDestBuilding).startY;
		
		while(!intersectionFound)
		{
			System.out.println("searching for adjacent intersections in the opposite direction");
			int intersectionIndex = this.getIndexOfIntersection(currentPosition.x, currentPosition.y);
			if(intersectionIndex != -1)
			{
				System.out.println("found an intersection: " + intersectionIndex);
				finalIntersections.add(new Vec2f(LH.intersections.get(intersectionIndex).x, LH.intersections.get(intersectionIndex).y));
				break;
			}
			else
			{
				//not intersection get the next position
				nextLaneIndex = this.getAttachedLane(currentPosition.x, currentPosition.y, LH.lanes.get(nextLaneIndex));
				System.out.println("the next lane index is : " + nextLaneIndex);
				
				if(nextLaneIndex != -1)
				{
					//set the new current position
					currentPosition.x = LH.lanes.get(nextLaneIndex).startX;
					currentPosition.y = LH.lanes.get(nextLaneIndex).startY;
				}
				else
				{
					//there is not another lane to go to
					System.out.println("cant search further down the road");
					break;
				}
			}
		}
		
		//find the first intersection index=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		
		intersectionFound = false;
		nextLaneIndex = nearestPoint1;
		currentPosition.x = LH.lanes.get(nearestPoint1).endX;
		currentPosition.y = LH.lanes.get(nearestPoint1).endY;
		while(!intersectionFound)
		{
			int intersectionIndex = this.getIndexOfIntersection(currentPosition.x, currentPosition.y);
			if(intersectionIndex != -1)
			{
				System.out.println("Found a start intersection");
				startIntersectionIndex = intersectionIndex;
				intersectionFound = true;
				break;
			}
			else
			{
				nextLaneIndex = this.getAttachedLane(currentPosition.x, currentPosition.y, LH.lanes.get(nextLaneIndex));
				System.out.println("attached lane index: " + nextLaneIndex);
				if(nextLaneIndex != -1)
				{
					currentPosition.x = LH.lanes.get(nextLaneIndex).endX;
					currentPosition.y = LH.lanes.get(nextLaneIndex).endY;
				}
				else
				{
					System.out.println("ERROR ON LINE 294 in pathfinder.java");
					break;
				}
			}
		}
		
		
		//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
		/*
		 * now that a start and end point(s) have been established, we can actually implement my version of the a* pathfinding algorithm
		 * I used a MIT tutorial for this algorithm.
		 * Their pseudo code was very helpful
		 * I present you the A* pathfinding algorithm
		 */
		//allows algorithm to know that a path has been found
		boolean pathFound = false;
		//create a temporary successor working variable
		ArrayList<PFNode> successors = new ArrayList<PFNode>(4); // used to add the successors for a*
		//add the first node and set its f to 0;
		openList.add(new PFNode(LH.intersections.get(startIntersectionIndex).x, LH.intersections.get(startIntersectionIndex).y));
		openList.get(0).f = 0;
		openList.get(0).g = 0;
		
		//the main a* loop
		while(!openList.isEmpty())
		{
			//finds the node in the open list with the least f and sets it to q
			int openListLeastFCandaditeIndex = 0;
			for(int i = 0; i < openList.size(); i++)
			{
				if(openList.get(i).f < openList.get(openListLeastFCandaditeIndex).f)
				{
					openListLeastFCandaditeIndex = i;
				}
			}
			//set it to q
			q = openList.get(openListLeastFCandaditeIndex);
			//take it off the open list
			openList.remove(openListLeastFCandaditeIndex);
			
			//now that the q has been established for this cycle we must find its successors
			//first empty the successor list
			for(int i = 0; i < successors.size(); i++)
			{
				successors.remove(i);
			}
			//then run the successor finder
			successors = this.getSuccessors(q);
			
			//now comes the main successor logic loop of the a* algorithm
			for(int i = 0; i < successors.size(); i++)
			{
				//set the h - the dist from node to goal (euclidean now)
				successors.get(i).h = this.distToClosestGoal(successors.get(i));
				//check the distance if 0 you did boy!
				if(successors.get(i).h == 0)
				{
					System.out.println("found a path good job! *********** a* rocks");
					pathFound = true;
					
					closedList.add(successors.get(i));
				}
				
				//calculate the g - dist from start to node
				successors.get(i).g = q.g + this.euclideanDistance(q.x, q.y, successors.get(i).x, successors.get(i).y);
				
				//calculate f easy peasy lemon squeezy
				successors.get(i).f = successors.get(i).g + successors.get(i).h;
				
				//now check both open and closed lists for matches with better f
				boolean sameInOpen = false;
				boolean sameInClosed = false;
				//open
				for(int j = 0; j < openList.size(); j++)
				{
					if(openList.get(j).x == successors.get(i).x && openList.get(j).y == successors.get(i).y && openList.get(j).f < successors.get(i).f)
					{
						sameInOpen = true;
					}
				}
				
				//closed
				for(int j = 0; j < closedList.size(); j++)
				{
					if(closedList.get(j).x == successors.get(i).x && closedList.get(j).y == successors.get(i).y && closedList.get(j).f < successors.get(i).f)
					{
						sameInClosed = true;
					}
				}
				
				//do the logic determining whether to add it(this successor) to the open list
				if(!sameInClosed && !sameInOpen)
				{
					openList.add(successors.get(i));
				}
			}
			
			//add q to the closed list
			System.out.println("adding node to closed list, position: " + q.x + ", " + q.y + " its h is " + q.h);
			closedList.add(q);
			
			if(pathFound)
			{
				System.out.println("A* is finished good job on debugging this! give your self a pat on the back. The closed list has " + closedList.size());
				break;
			}
		}
		
		//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
		//print out the path
		//and add the coordinates to the coordinate list
		System.out.print("The closed list path is: " + closedList.get(0).x + ", " + closedList.get(0).y + " - ");
		PFNode currentParent = closedList.get(0);
		boolean goalReached = false;
		while(!goalReached)
		{
			for(int i = 0; i < closedList.size(); i++)
			{
				if(currentParent == closedList.get(i).parent)
				{
					currentParent = closedList.get(i);
				}
			}
			
			System.out.print(currentParent.x + ", " + currentParent.y + " - ");
			//add the coordinates
			coordinates.add(new Vec2f(currentParent.x, currentParent.y));
			
			if(this.distToClosestGoal(currentParent) == 0)
			{
				goalReached = true;
				break;
			}
		}
		
		//add the dest buildings coordinates *****add the 'final' direction later
		coordinates.add(new Vec2f(LH.buildings.get(buildingDestinationNumber).x, LH.buildings.get(buildingDestinationNumber).y));
		
		//finally calculate the directions like right left etc
		/*
		 * THis is a pretty interesting bit of code it might be many lines. 
		 * I dont know because i have not written it yet
		 * I basically have to determine whether the vehicle should turn left, right or go straight at a given intersection.
		 */
		//the the first intersection
		int beginLightIndex = -1;
		int beginLightsIntersectionIndex = -1;
		Vec2f lightDestCoordinates = new Vec2f();
		boolean beginLightFound = false;
		Vec2f searchPosition = new Vec2f(LH.lanes.get(nearestPoint1).endX, LH.lanes.get(nearestPoint1).endY);
		for(int i = 0; i < LH.intersections.size(); i++)
		{
			if(LH.intersections.get(i).x == coordinates.get(1).x && LH.intersections.get(i).y == coordinates.get(1).y)
			{
				beginLightsIntersectionIndex = i;
				while(!beginLightFound)
				{
					for(int j = 0; i < LH.intersections.get(i).trafficLights.size(); i++)
					{
						if(LH.intersections.get(i).trafficLights.get(j).lightX == searchPosition.x && LH.intersections.get(i).trafficLights.get(j).lightY
								== searchPosition.y)
						{
							beginLightFound = true;
							beginLightIndex = j;
							break;
						}
					}
					
					if(beginLightIndex == -1)
					{
						int nextLaneTestIndex = this.getLaneIndexFromStartPoint(searchPosition.x, searchPosition.y);
						if(nextLaneIndex != -1)
						{
							searchPosition.x = LH.lanes.get(nextLaneIndex).endX;
							searchPosition.y = LH.lanes.get(nextLaneIndex).endY;
						}
						else
						{
							System.out.println("error could not find another lane while searching for directions");
							break;
						}
					}
				}
				break;
			}
		}
		
		//find the dest lane index of light which leads to the next intersection
		lightDestCoordinates = this.findConnectingDestCoordinatesOfIntersections(beginLightsIntersectionIndex, beginLightIndex, this.getIndexOfIntersection(coordinates.get(2).x, coordinates.get(2).y));
		
		//do the center indexes first (beginning +2 -> end -1)
		for(int i = 2; i < coordinates.size() - 1; i++)
		{
			
		}
		
		Path output = new Path(coordinates, directions);
		return(output);
	}
	
	
	//finds the destination coordinate of a given light at a given intersection which leads to
	//another given intersection
	public Vec2f findConnectingDestCoordinatesOfIntersections(int _beginIntersectionIndex, int _beginIntersectionLightIndex, int _destIntersectionIndex)
	{
		Vec2f output = new Vec2f();
		
		switch(LH.intersections.get(_destIntersectionIndex).type)
		{
		case "basic":
			for(int i = 0; i < LH.intersections.get(_destIntersectionIndex).trafficLights.size(); i++)
			{
				//search the begin coordinates and out
				boolean keepSearching = true;
				int currentLaneIndex = this.getLaneIndexFromEndPoint(LH.intersections.get(_destIntersectionIndex).trafficLights.get(i).lightX, 
						LH.intersections.get(_destIntersectionIndex).trafficLights.get(i).lightX);
				Vec2f currentPosition = new Vec2f(LH.lanes.get(currentLaneIndex).startX, LH.lanes.get(currentLaneIndex).startY);
				while(keepSearching)
				{
					//check to see if any of the destination coordinates match the current position
					if(LH.intersections.get(_beginIntersectionIndex).trafficLights.get(_beginIntersectionLightIndex).leftDestX == currentPosition.x && 
							LH.intersections.get(_beginIntersectionIndex).trafficLights.get(_beginIntersectionLightIndex).leftDestY == currentPosition.y)
					{
						output.x = currentPosition.x;
						output.y = currentPosition.y;
						return output;
					}
					if(LH.intersections.get(_beginIntersectionIndex).trafficLights.get(_beginIntersectionLightIndex).straightDestX == currentPosition.x && 
							LH.intersections.get(_beginIntersectionIndex).trafficLights.get(_beginIntersectionLightIndex).straightDestY == currentPosition.y)
					{
						output.x = currentPosition.x;
						output.y = currentPosition.y;
						return output;
					}
					if(LH.intersections.get(_beginIntersectionIndex).trafficLights.get(_beginIntersectionLightIndex).rightDestX == currentPosition.x && 
							LH.intersections.get(_beginIntersectionIndex).trafficLights.get(_beginIntersectionLightIndex).rightDestY == currentPosition.y)
					{
						output.x = currentPosition.x;
						output.y = currentPosition.y;
						return output;
					}
					
					//find the next lanes end coordinates and keep searching
					currentLaneIndex = this.getLaneIndexFromEndPoint(currentPosition.x, currentPosition.y);
					if(currentLaneIndex != -1)
					{
						currentPosition.x = LH.lanes.get(currentLaneIndex).startX;
						currentPosition.y = LH.lanes.get(currentLaneIndex).startY;
					}
					else
					{
						keepSearching = false;
					}
				}
			}
			break;
		}
		
		return null;
	}
	
	//finds the distance to the closest final intersection
	//and reports it
	public float distToClosestGoal(PFNode _currentNode)
	{
		float currentClosestDist = 4000;
		for(int i = 0; i < finalIntersections.size(); i++)
		{
			float thisDist = this.euclideanDistance(_currentNode.x, _currentNode.y, finalIntersections.get(i).x, finalIntersections.get(i).y);
			if(thisDist < currentClosestDist)
			{
				currentClosestDist = thisDist;
			}
		}
		return currentClosestDist;
	}
	
	//gets the coordinates of an intersection when given a point
	//the point can be the coordinate of any light at the interesection
	//returns -1 if not intersection
	public int getIndexOfIntersection(float _x, float _y)
	{
		System.out.println("checking coordinates for intersection " + _x + " " + _y);
		for(int i = 0; i < LH.intersections.size(); i++)
		{
			//cycle through the intersections' traffic lights
			for(int j = 0; j < LH.intersections.get(i).trafficLights.size(); j++)
			{
				//if the current position is equal to a lights position
				//then add the intersections coordinates to the final intersections coordinates
				if(LH.intersections.get(i).trafficLights.get(j).lightX == _x && LH.intersections.get(i).trafficLights.get(j).lightY == _y)
				{
					//an intersection is found
					//return its index
					return i;
				}
			}
		}
		//no intersections found sorry
		return -1;
	}
	
	//takes in the endings or beginning coordinates of a lane
	//returns the index of the attached lane, -1 if no lane attached
	public int getAttachedLane(float _lanex, float _laney, Lane _lane)
	{
		for(int i = 0; i < LH.lanes.size(); i++)
		{
			if(_lane != LH.lanes.get(i))
			{
				if(_lanex == LH.lanes.get(i).endX && _laney == LH.lanes.get(i).endY)
				{
					//return the found lane's index
					return i;
				}
				if(_lanex == LH.lanes.get(i).startX && _laney == LH.lanes.get(i).startY)
				{
					//return the found lane's index
					return i;
				}
			}
		}
		// sorry no lane found this was not supposed to happen... or was it?
		return -1;
	}
	
	public float euclideanDistance(float _x1, float _y1, float _x2, float _y2)
	{
		return (float)Math.sqrt((Math.pow(_y2, 2) - Math.pow(_y1, 2)) + (Math.pow(_x2, 2) - Math.pow(_x1, 2)));
	}
	
	public ArrayList<PFNode> getSuccessors(PFNode _parent)
	{
		ArrayList<PFNode> returnSuccessors = new ArrayList<PFNode>(4);
		
		//find the intersection type
		//first find the intersection index to find the type
		int qIntersectionIndex = 0;
		for(int i = 0; i < LH.intersections.size(); i++)
		{
			if(LH.intersections.get(i).x == _parent.x && LH.intersections.get(i).y == _parent.y)
			{
				qIntersectionIndex = i;
				break;
			}
		}
		
		//now switch on the intersections type
		switch(LH.intersections.get(qIntersectionIndex).type)
		{
		case "basic":
			int theIntersection = this.findIntersectionIndexDownRoad(LH.intersections.get(qIntersectionIndex).trafficLights.get(0).straightDestX,
					LH.intersections.get(qIntersectionIndex).trafficLights.get(0).straightDestY);
			if(theIntersection != -1)
			{
				returnSuccessors.add(new PFNode(LH.intersections.get(theIntersection).x, LH.intersections.get(theIntersection).y));
			}
			theIntersection = this.findIntersectionIndexDownRoad(LH.intersections.get(qIntersectionIndex).trafficLights.get(1).straightDestX,
					LH.intersections.get(qIntersectionIndex).trafficLights.get(1).straightDestY);
			if(theIntersection != -1)
			{
				returnSuccessors.add(new PFNode(LH.intersections.get(theIntersection).x, LH.intersections.get(theIntersection).y));
			}
			theIntersection = this.findIntersectionIndexDownRoad(LH.intersections.get(qIntersectionIndex).trafficLights.get(2).straightDestX,
					LH.intersections.get(qIntersectionIndex).trafficLights.get(2).straightDestY);
			if(theIntersection != -1)
			{
				returnSuccessors.add(new PFNode(LH.intersections.get(theIntersection).x, LH.intersections.get(theIntersection).y));
			}
			theIntersection = this.findIntersectionIndexDownRoad(LH.intersections.get(qIntersectionIndex).trafficLights.get(3).straightDestX,
					LH.intersections.get(qIntersectionIndex).trafficLights.get(3).straightDestY);
			if(theIntersection != -1)
			{
				returnSuccessors.add(new PFNode(LH.intersections.get(theIntersection).x, LH.intersections.get(theIntersection).y));
			}
			break;
		}
		
		System.out.println("Found " + returnSuccessors.size() + " Successors to the intersection at " + _parent.x + ", " + _parent.y);
		
		//set the successors parent
		for(int i = 0; i < returnSuccessors.size(); i++)
		{
			returnSuccessors.get(i).parent = _parent;
		}
		
		return returnSuccessors;
	}
	
	
	//finds the intersections down the road from a selected point
	//once an intersection is found its index is output
	//this takes in a begin point of a lane
	//if no intersections found
	public int findIntersectionIndexDownRoad(float _startX, float _startY)
	{
		System.out.println("Beginning the search for intersections on at " + _startX + ", " + _startY);
		int startLaneIndex = this.getLaneIndexFromStartPoint(_startX, _startY);
		System.out.println("found the starting lane index as " + startLaneIndex);
		if(startLaneIndex != -1)
		{
			boolean intersectionFound = false;
			//set the start position for the algorithm
			Vec2f currentPosition = new Vec2f(LH.lanes.get(startLaneIndex).endX, LH.lanes.get(startLaneIndex).endY);
			
			while (!intersectionFound) 
			{
				//search current point for an intersection
				int intersectionIndex = this.getIndexOfIntersection(currentPosition.x, currentPosition.y);
				if(intersectionIndex != -1)
				{
					//found an intersection 
					return intersectionIndex;
				}
				else
				{
					//find the next lane if there is one 
					startLaneIndex = this.getAttachedLane(currentPosition.x, currentPosition.y, LH.lanes.get(startLaneIndex));
					if(startLaneIndex != -1)
					{
						currentPosition.x = LH.lanes.get(startLaneIndex).endX;
						currentPosition.y = LH.lanes.get(startLaneIndex).endY;
						System.out.println("found another lane (" + startLaneIndex + ") with the end coordinates of: " + currentPosition.x + ", " + currentPosition.y);
					}
					else
					{
						System.out.println("could not find another lane");
						return -1;
					}
				}
			}
		}
		//no intersections found
		return -1;
	}
	
	//gets a lane index from its start point
	//you actually give it the end points of the current lane
	public int getLaneIndexFromStartPoint(float _startX, float _startY)
	{
		for(int i = 0; i < LH.lanes.size(); i++)
		{
			if(LH.lanes.get(i).startX == _startX && LH.lanes.get(i).startY == _startY)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	//gets a lane index from its end point
	//you actually give it the start points of the current lane
	public int getLaneIndexFromEndPoint(float _endX, float _endY)
	{
		for(int i = 0; i < LH.lanes.size(); i++)
		{
			if(LH.lanes.get(i).endX == _endX && LH.lanes.get(i).endY == _endY)
			{
				return i;
			}
		}

		return -1;
	}
}


