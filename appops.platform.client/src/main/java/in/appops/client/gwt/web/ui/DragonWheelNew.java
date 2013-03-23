/**
 * 
 */
package in.appops.client.gwt.web.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public class DragonWheelNew extends Composite {
	
	private int elevateAngle = 5 ;
	private int wheelSpeed = 30 ;
	private boolean elevatedView = false ;
	
	private Map<String , Cylinder> cylinderMap = new HashMap<String, Cylinder>();
			
	private DeckPanel parent ;
	
	public DragonWheelNew(){
		parent = new DeckPanel();
		DOM.setStyleAttribute(parent.getElement(), "position", "absolute");
		parent.setStylePrimaryName("dragonWheel");
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
		cylinder.setElevation_angle(elevateAngle);
		
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
	public void initWidgetPositions(MediaViewer mediaViewer) {
		Cylinder topMost = getTopMost();
		topMost.initWidgetPositions(mediaViewer);
		parent.showWidget(0);
	}
	

	public Cylinder getTopMost(){
		for (Cylinder cyl : cylinderMap.values()){
			if (cyl.getOrder() == 0)
				return cyl;
			else continue ;
		}
		return null ;
	}
	
	/**
	 * Method used to spin the cylinder.
	 */
	public void spin(){
		if(elevateAngle == 60)
			elevateAngle = 80;
		else
			elevateAngle = 60;
		
		for ( String cylinderName : cylinderMap.keySet()){
			Cylinder cylinder = cylinderMap.get(cylinderName);
		
			Map<String, Row> rowmap = cylinder.getRowMap();
			for ( String rowName : rowmap.keySet()){
				Row row =rowmap.get(rowName);
				Set<Widget> widgetSetForRow = row.getWidgetSetForRow();
			
				int indexOfWidget = 0;
				row.setElevation_angle(elevateAngle);	
			
				for (Widget widget : widgetSetForRow) {
				
					int newXpos = (int) Math.round(Math.cos(row.getCurrentAngle() + indexOfWidget* row.getWidgetSpacing()+row.getSpeed() )* row.getRadius() + row.getxLeft());
					int newYPos = (int) Math.round(-Math.sin(row.getCurrentAngle() + indexOfWidget* row.getWidgetSpacing()+row.getSpeed())* row.getElevation_angle() + row.getyTop());
				
					double scale = row.getScalingConstant()/ (row.getScalingConstant() + Math.sin(row.getCurrentAngle() + indexOfWidget * row.getWidgetSpacing()+row.getSpeed() )* row.getRadius() + row.getZcenter());
					
					
					double angleInRadian = 0;
					widget = row.scaleWheelWidget(widget, scale,angleInRadian,indexOfWidget);
								
					row.add(widget, newXpos, newYPos);
				
					indexOfWidget++;
				}
			}
			}
		}
			 
}
