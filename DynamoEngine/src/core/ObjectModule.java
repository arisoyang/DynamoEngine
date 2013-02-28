package core;

import java.util.ArrayDeque;
import java.util.ArrayList;

import Objects.GameObject;

public class ObjectModule {
public ArrayList<GameObject> objects = new ArrayList<GameObject>();
public ArrayDeque<GameObject> changeque = new ArrayDeque<GameObject>();
	
	public ObjectModule(){
		
		
		
	}
	public void addObject(GameObject obj){
		
		objects.add(obj);
	}
	
	public void updateObjectStates(){
		//iterate through all objects in the "change queue"
		//apply any state changes to them
		while(changeque.size() != 0){
			changeque.pop().update();
		}
	}
}
