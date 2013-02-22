package inputters;

public class KeyInformation{
	
	private int key;
	private KeyState state;
	
	public KeyInformation(int _key, KeyState _state){
		key = _key;
		state = _state;
	}
	
	public int getKey(){
		return key;
	}
	
	public KeyState getState(){
		return state;
	}
	
	/*
	 public boolean equals(KeyInformation k) {
		 return key == k.getKey() && state == k.getState();
	 }
	 */
	
	public boolean equals(Object o) {
		KeyInformation k = (KeyInformation)o;
		return key == k.getKey() && state == k.getState();
	}
	
	public int hashCode() {
		int num_val = KeyState.values().length;
		int hash_code = key+1; //this way we won't get isues is key is 0
		hash_code *= 10;
		while (num_val >= 10){
			hash_code *= 10;
		}
		hash_code += state.ordinal();
		return hash_code;
	}
	
}
