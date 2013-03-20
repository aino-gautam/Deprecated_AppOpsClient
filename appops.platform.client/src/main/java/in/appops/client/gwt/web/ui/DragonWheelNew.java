/**
 * 
 */
package in.appops.client.gwt.web.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public class DragonWheelNew extends Composite implements WheelEventHandler{
	
	private int xWheelCenter = 300;
	private int yWheelCenter = 200;
	private int zWheelcenter = 200;
	
	private int elevateAngle = 60 ;
	private int wheelSpeed = 30 ;
	private boolean elevatedView = false ;
	
	private Map<String , Cylinder> cylinderMap = new HashMap<String, Cylinder>();
			
	private DeckPanel parent ;
	
	public DragonWheelNew(){
		parent = new DeckPanel();
		DOM.setStyleAttribute(parent.getElement(), "position", "absolute");
		parent.setStylePrimaryName("dragonWheel");
		addHandler(this, WheelEvent.TYPE);
		initWidget(parent);
	}
	
	public int getElevateAngle() {
		return elevateAngle;
	}

	/**
	 * sets elevation angle
	 * @param elevateAngle
	 */
	public void setElevateAngle(int elevateAngle) {
		this.elevateAngle = elevateAngle;
	}

	public int getWheelSpeed() {
		return wheelSpeed;
	}

	/**
	 * Change speed of wheel between 1 to 100
	 * @param wheelSpeed
	 */
	public void setWheelSpeed(int wheelSpeed) {
		this.wheelSpeed = wheelSpeed;
	}

	public boolean isElevatedView() {
		return elevatedView;
	}

	/**
	 * Call this method to toggle the elevated view of the cylinder
	 * @param elevatedView true = elevated view
	 */
	public void setElevatedView(boolean elevatedView) {
		this.elevatedView = elevatedView;
	}
	
	public void addCylinder(Cylinder cylinder){
		cylinderMap.put(cylinder.getName(), cylinder);
		parent.add(cylinder);
	}

	public void removeCylinder(Cylinder cyl){
		cylinderMap.remove(cyl.getName());
	}
	
	public Cylinder getCylinder(String name){
		return cylinderMap.get(name);
	}


	/**
	 * 
	 */
	public void initWidgetPositions() {
		Cylinder topMost = getTopMost();
		parent.showWidget(topMost.getOrder());
	}
	

	public Cylinder getTopMost(){
		for (Cylinder cyl : cylinderMap.values()){
			if (cyl.getOrder() == 0)
				return cyl;
			else continue ;
		}
		return null ;
	}

	@Override
	public void onWheelEvent(WheelEvent event) {
		/*WheelWidgetProvider provider = new WheelWidgetProviderImpl();
		nextWidgetSet = provider.getNextWidgetSet(this);*/
		
		//get the next widget set.
		
	}
		 
}
