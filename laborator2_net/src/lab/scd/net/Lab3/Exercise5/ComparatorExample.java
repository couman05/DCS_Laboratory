package lab.scd.net.Lab3.Exercise5;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import Main.FuzzyPVizualzer;
import core.TableParser;
import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.Executor.AsyncronRunnableExecutor;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import core.FuzzyPetriLogic.Tables.OneXOneTable;
import core.FuzzyPetriLogic.Tables.TwoXOneTable;

public class ComparatorExample {

	// FLRS table for T0 that implements P0-P1
	            String differentiator = "" + //
				"{[<ZR><NM><NL><NL><NL>]" + //
				" [<PM><ZR><NM><NL><NL>]" + //
				" [<PL><PM><ZR><NM><NL>]" + //
				" [<PL><PL><PM><ZR><NM>]" + //
				" [<PL><PL><PL><PM><ZR>]}";

	// FLRS table for T1 that makes the selection according to the result P0-P1 (positive or negative) 
		String separator="{[<FF,NL><FF,NL><FF,FF><PL,FF><PL,FF>]}";

		public ComparatorExample() {

	// the Petri network is being constructed
			TableParser parser = new TableParser();
			FuzzyPetriNet petriNet = new FuzzyPetriNet();

	// adding the input places
			int p0Inp = petriNet.addInputPlace();
			int p1Inp = petriNet.addInputPlace();

	// attaching to the transition t0 the corresponding FLRS table
			TwoXOneTable diffTable = parser.parseTwoXOneTable(differentiator);
			int t0 = petriNet.addTransition(0, diffTable);

	// add the arcs and the weights corresponding to the Petri Net
			petriNet.addArcFromPlaceToTransition(p0Inp, t0, 1.0);
			petriNet.addArcFromPlaceToTransition(p1Inp, t0, 1.0);

	// add the places and arc corresponding to the Petri Net
			int p2 = petriNet.addPlace();
			petriNet.addArcFromTransitionToPlace(t0, p2);

			int t1 = petriNet.addTransition(0,               
	                         parser.parseOneXTwoTable(separator));
			petriNet.addArcFromPlaceToTransition(p2, t1, 1.0);

			int p3 = petriNet.addPlace();
			petriNet.addArcFromTransitionToPlace(t1, p3);
			int p4 = petriNet.addPlace();
			petriNet.addArcFromTransitionToPlace(t1, p4);

			int t2Out =  petriNet.addOuputTransition(OneXOneTable.defaultTable());
			petriNet.addArcFromPlaceToTransition(p3, t2Out, 1.0);

	// associating an action of the output transition t2 
			petriNet.addActionForOuputTransition(t2Out, new Consumer<FuzzyToken>() {
				@Override
				public void accept(FuzzyToken t) {
					System.out.println( "Output From Transition 2: " + t.shortString());
				}
			});

			int t3Out = petriNet.addOuputTransition(OneXOneTable.defaultTable());
			petriNet.addArcFromPlaceToTransition(p4, t3Out, 1.0);

	// associating an action of the output transition t3
			petriNet.addActionForOuputTransition(t3Out, new Consumer<FuzzyToken>() {
				@Override
				public void accept(FuzzyToken t) {
					System.out.println("Output From Transition  3: " + t.shortString());
				}
			});
	// creating the date Petri Net executor and specifying the period in milliseconds 
		AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(petriNet, 20);   

	//  creating an object for visualizing the behavior of the Petri net
	  		FullRecorder recorder = new FullRecorder();
			executor.setRecorder(recorder);
			FuzzyDriver driver =   FuzzyDriver.createDriverFromMinMax(-1.0, 1.0);

	// launching the execution of the thread that contains the executor
			(new Thread(executor)).start();

			for (int i = 0; i < 3600; i++) {

	// constructing the dictionary collection (map) for inputs
				Map<Integer, FuzzyToken> inps = new HashMap<>();
				
	// placing the fuzzyficated token
				  double sineValue = Math.sin(Math.toRadians(i));
				  double cosineValue = Math.cos(Math.toRadians(i));
					inps.put(p0Inp, driver.fuzzifie(sineValue));
					inps.put(p1Inp, driver.fuzzifie(cosineValue));
				 
				

	// placing the input tokens for the executer
				executor.putTokenInInputPlace(inps);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			executor.stop();

	// visualizing the Petri Net and its behavoir.
			FuzzyPVizualzer.visualize(petriNet, recorder);
		}
		public static void main(String[] main) {
			new ComparatorExample();
			
			// the FLETPN model runs 25 times in one tic because 
			//	AsyncronRunnableExecutor executor = new AsyncronRunnableExecutor(petriNet, 20);  represents the duration of a tic in ms
			// in the for we have 100 iterations with 5ms delay so 100*5/20=25 
		}
	}

