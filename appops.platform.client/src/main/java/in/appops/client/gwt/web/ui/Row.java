/**
 * 
 */
package in.appops.client.gwt.web.ui;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public class Row {

	private int rowHeight ; 
	private int rowWidth ;
	private int widgetSpacing ;
	private int numWidgetsVisible ;
	private boolean independent = false ;
	private int xLeft = 0 ; 
	private int yTop = 0 ;
	private String name ;
	private String displayName ;
	private int rowPosition ;
	private Cylinder parentCylinder = null ;
	
	public int getRowPosition() {
		return rowPosition;
	}

	public void setRowPosition(int rowPosition) {
		this.rowPosition = rowPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	

	public int getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public int getRowWidth() {
		return rowWidth;
	}

	public void setRowWidth(int rowWidth) {
		this.rowWidth = rowWidth;
	}

	public int getWidgetSpacing() {
		return widgetSpacing;
	}

	public void setWidgetSpacing(int widgetSpacing) {
		this.widgetSpacing = widgetSpacing;
	}

	public int getNumWidgetsVisible() {
		return numWidgetsVisible;
	}

	public void setNumWidgetsVisible(int numWidgetsVisible) {
		this.numWidgetsVisible = numWidgetsVisible;
	}

	public boolean isIndependent() {
		return independent;
	}

	public void setIndependent(boolean independent) {
		this.independent = independent;
	}

	public int getxLeft() {
		return xLeft;
	}

	public void setxLeft(int xLeft) {
		this.xLeft = xLeft;
	}

	public int getyTop() {
		return yTop;
	}

	public void setyTop(int yTop) {
		this.yTop = yTop;
	}

	public Cylinder getParentCylinder() {
		return parentCylinder;
	}

	public void setParentCylinder(Cylinder parentCylinder) {
		this.parentCylinder = parentCylinder;
	}
	
	public void spinRow(){
		
	}
}
