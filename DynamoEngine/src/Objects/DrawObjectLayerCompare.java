package Objects;
import java.util.Comparator;


public class DrawObjectLayerCompare implements Comparator<DrawObject>
{
    public int compare(DrawObject x, DrawObject y)
    {
    	if (x.getLayer() < y.getLayer()){
    		return -1;
    	}
    	if (x.getLayer() > y.getLayer()){
    		return 1;
    	}
    	if (x.getPos().x < y.getPos().x){
    		return -1;
    	}
    	if (x.getPos().x > y.getPos().x){
    		return 1;
    	}
    	if (x.getPos().y < y.getPos().y){
    		return -1;
    	}
    	if (x.getPos().y > y.getPos().y){
    		return 1;
    	}
        return 0;
    }

}