package inputters;

public enum KeyState {
	RELEASED, //not pressed
	PRESSED, //pressed for >1 frame
	TAPPED //pressed for first frame
}
