package engine;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	public void tick() {
		
		// Obtaining Iterator
		//Note -- there are issues with thread safety which prolly have to be addressed
	    Iterator<GameObject> objectsIterator = objects.iterator();
	 
	    while(objectsIterator.hasNext())
	       objectsIterator.next().tick();
	    
	}

	public void render(Graphics g) {
		
		// Obtaining Iterator
	    Iterator<GameObject> objectsIterator = objects.iterator();
	 
	    while(objectsIterator.hasNext())
	       objectsIterator.next().render(g);
	    
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
	}
	
	public void removeObject(GameObject object) {
		objects.remove(object);
	}
	
}
