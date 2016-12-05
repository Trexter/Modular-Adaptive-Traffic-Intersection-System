import java.awt.Component;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.sun.javafx.sg.prism.NGShape.Mode;

import processing.core.*;

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
 * 
 */

public class MATIS_Simulation extends PApplet {
	
	/**
	 * The Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	
	public float t1 = 0;
	public int programMode = 0; // 0=simulation 1=layoutCreator 2=trafficProfileCreator

	TrafficHandler TF1 = new TrafficHandler(this);
	
	LayoutHandler LH1 = new LayoutHandler(this);
	
	public void setup()
	{	
		//adjust the size of the window
		size(1400, 700);
		/*
		 *ask user to select a map
		 */
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(this.sketchPath));
        int returnVal = fc.showDialog(getParent(), "Choose Infrastructure File");
        
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
        	File file = fc.getSelectedFile();
        	LH1.loadLayout(file.getAbsolutePath());
        	/*
        	 * now ask the user for a traffic profile
        	 */
        }
        
        else
        {
        	//close program
        	System.out.println("User decided to create a layout instead");
        }
	}
	
	public void draw()
	{
		//preliminary steps for simulation algorithm
		
		//wipe the window clean
		background(0,80,0);
		//run the simulation algorithms
		LH1.drawLanes();
		LH1.drawIntersections();
		TF1.runVehicles(LH1);
		TF1.drawVehicles();
		LH1.runIntersections();
		LH1.drawBuildings();
		//write the title text
		fill(255, 255, 255);
		textSize(12);
		text("MATIS (Modular Adaptive Traffic Intersection System) Simulation by Kevin Sheridan", 2, 14);
		text("World Time:", 20, 650);
		textSize(20);
		text(t1, 20, 670);
		//make a small delay so the CPU wont be fully used
		delay(100);
		t1 += 0.1; // say that 100 milliseconds has passed in world time
	}
	
}