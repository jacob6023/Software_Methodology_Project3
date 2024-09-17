package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {

	    // UPDATE THIS METHOD
		TNode wn = new TNode(0, null, null);
		TNode bn = new TNode(0, null, wn);
		trainZero = new TNode(0, null, bn);
		TNode tn = trainZero;
		int c1 = 0;
		int c2 = 0;
		for (int i = 0; i < locations.length; i++){
			wn.setNext(new TNode(locations[i], null, null));
			wn = wn.getNext();
			if (c2 < busStops.length){
				if (locations[i] == busStops[c2]){
					bn.setNext(new TNode(busStops[c2], null. wn));
					bn = bn.getNext();
					++c2;
				}
			}
			if (c1 < trainStations.length) {
				if (locations[i] == trainStations[c1]){
					tn.setNext(new TNode(trainStations[c1], null, bn));
					tn = tn.getNext();
					++c1;
				}
			}
		}
	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
	    // UPDATE THIS METHOD
		TNode trainStations = trainZero;
		while (trainStations.getNext() !=null){
			if (station == trainStations.getNext().getLocation()){
				trainStations.setNext(trainStations.getNext().getNext());
				break;
			}
		trainStations = trainStations.getNext();
		}

	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    // UPDATE THIS METHOD
		TNode bl = trainZero.getDown();
		TNode wl = trainZero.getDown().getDown();

		while(wl.getLocation() != busStop){
			wl =wl.getNext();
		}

		while (true){
			if (busStop == bl.getLocation()){
				return;
			} else if (busStop > bl.getLocation()){
				if (bl.getNext() == null){
					bl.setNext(new TNode(busStop, null, wl));
					return;
				}else if (busStop > bl.getNext().getLocation()){
					bl = bl.getNext();
				} else if ( busStop == bl.getNext().getLocation()){
					return;
				}else{
					TNode newNode = new TNode(busStop, null, wl);
					newNode.setNext(bl.getNext());
					bl.setNext(newNode);
					return;
				}
			}
		}
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		ArrayList<TNode> list = new ArrayList<>();
		TNode se = trainZero;
		list.add(se);
		while (true){
			if (se.getNext() == null){
				list.add(se.getDown());
				se = se.getDown();
			}else if (se.getNext().getLocation() > destination){
				list.add(se.getDown());
				se = se.getDown();
			} else if (se.getNext().getLocation() < destination){
				list.add(se.getNext());
				se = se.getNext();
			}else if (se.getNext().getLocation() == destination){
				if (se.getNext().getDown() != null){
					list.add(se.getNext());
					list.add(se.getNext().getDown());
					if (se.getNext().getDown().getDown() != null){
						list.add(se.getNext().getDown().getDown());
						break;
					}

				}else {
					list.add(se.getNext());
					break;
				}
			}
		}	
	    // UPDATE THIS METHOD
	    return list;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep cop`y
	 */
	public TNode duplicate() {
		TNode wn = newTNode(0, null, null);
		TNode bn = new TNode(0, null, wn);
		TNode tn = newTNode(0, null, bn);
		TNode tndr = tn;

		TNode wcn = trainZerio.getDown().getDown().getNext();
		TNode bcn = trainZero.getDown().getNext();
		TNode tcn = trainZero.getNext();

		while (wcn != null){
			wn.setNext(new TNode(walkingcoppyNode.getLocation(), null, null));
			wn = wn.getNext();
			if (bcn != null && wcn.getLocation() == bcn.getLocation()){
				bn.setNext(new TNode(bcn.getLocaation(), null, wn));
				bcn = bcn.getNext();
				bn = bn.getNext();
			}
			if (tcn != null && wcn.getLocation() == tcn.getLocation()){
				tn.setNext(new TNode(tcn.getLocation(), null, bn));
				tcn = tcn.getNext();
				tn = tn.getNext();

			}
			wcn = wcn.getNext();
		
		}
	    // UPDATE THIS METHOD
	    return tndr;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {

		TNode sn = new TNode (0, null, null);
		TNode wn = trainZero.getDown().getDown();
		TNode bn = trainZero.getDown();
		bn.setDown(sn);
		sn.setDown(wn);

		int index = 0;
		bn = bn.getNext();

		while (wn != null){
			if (index == scooterStops.length){
				return;
			}
			if (scooterStops[index] == wn.getLocation());
			sn.setNext(new TNode(scooterStops[index], null, wn));
			sn = sn.getNExt90;
				if (bn != null){
					if (scooterStops[index] == bn.getLocation()){
						bn.setDown(sn);
					}
				}
				++index;
		}
		if (wn.getLocation() == bn.getLocation() && bn.getNext() != null){
			bn = bn.getNext();
		
		}
		wn = wn.getNext();
	    // UPDATE THIS METHOD
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
