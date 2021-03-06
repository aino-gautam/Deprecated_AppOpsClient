/**
 * 
 */
package in.appops.client.gwt.web.ui;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public class Cylinder extends Row {

	private int order ;
	private HashMap<String, Row> rowMap = null ;
	private String displayName;
	private int height;
	private double radius ;
	private int xLeft=0;
	private int yTop=0;
	private double elevation_angle =0; 
	private double speed =0	; // 
	private boolean isSkewMode;
	
	public Cylinder() {
		
	}
	
	 public Cylinder(int order){
		this.order = order;
		
	 }

	public void addRow(Row row){
		if(rowMap==null)
			rowMap = new HashMap<String, Row>();
		
		setDefaultPropertiesToRow(row);
	}
	
	private void setDefaultPropertiesToRow(Row row) {
		row.setxLeft(xLeft);
		row.setSkewMode(isSkewMode);
		row.setParentCylinder(this);
				
		yTop+=120;
		row.setyTop(yTop);
		rowMap.put(row.getName(),row);
	}

	public double getElevation_angle() {
		return elevation_angle;
	}

	public void setElevation_angle(double elevation_angle) {
		this.elevation_angle = elevation_angle;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void removeRow(Row row){
		rowMap.remove(row.getName());
	}
	
	public Row getRow(String name){
		return rowMap.get(name);
	}

	/**
	 * When non-independent row will be rotated the whole cylinder will rotate. 
	 * @param direction
	 */
	
	public void rotate(int direction){
		Map<String, Row> rowmap = getRowMap();
		for ( String rowName : rowmap.keySet()){
			Row row =rowmap.get(rowName);
			Set<Widget> widgetSetForRow = row.getWidgetSetForRow();
			double currentAngle = 0.0;
			int indexOfWidget = 0;
			
			if (direction>0) {
				currentAngle = row.getCurrentAngle() - (Math.PI / speed);
				
			} else {
				// mouse wheel is moving south
				currentAngle = row.getCurrentAngle() + (Math.PI / speed);
			}
			row.setCurrentAngle(currentAngle);
			
			for (Widget widget : widgetSetForRow) {
				
				int newXpos = (int) Math.round(Math.cos(row.getCurrentAngle() + indexOfWidget* row.getWidgetSpacing()+speed )* radius + row.getxLeft());
				int newYPos = (int) Math.round(-Math.sin(row.getCurrentAngle() + indexOfWidget* row.getWidgetSpacing()+speed)* elevation_angle + row.getyTop());

				double scale = row.getScalingConstant()/ (row.getScalingConstant() + Math.sin(row.getCurrentAngle() + indexOfWidget * row.getWidgetSpacing()+speed )* radius + row.getZcenter());
				
				widget = scaleExistingWheelWidget(widget, scale,indexOfWidget,0);
												
				row.add(widget, newXpos, newYPos);
				
				indexOfWidget++;
			}
		}
		
	}
	
	public Map<String, Row> getRowMap(){
		return rowMap;
	}
	
	public int getOrder() {
		return order;
	}

	/**
	 * Changes the order of the Cylinder i.e. frontmost is 0 and number increases as you towards the center.
	 * @param order
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void initWidgetPositions(MediaViewer mediaViewer) {
		
		Map<Row, LinkedHashSet<Widget>> widgetSetForCylinder = mediaViewer.getNextWidgetSet(this);
		
		for (Row row : widgetSetForCylinder.keySet()){
			row.initWidgetPositions(mediaViewer,widgetSetForCylinder.get(row));
			add(row);
		}
		
	}

	public void setRowMap(HashMap<String, Row> rowMap) {
		this.rowMap = rowMap;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setCoordinates(int xLeft, int yTop) {
		this.xLeft  =xLeft;
		this.yTop = yTop;
		
	}

	public boolean isSkewMode() {
		return isSkewMode;
	}

	public void setSkewMode(boolean isSkewMode) {
		this.isSkewMode = isSkewMode;
	}
	
	
}