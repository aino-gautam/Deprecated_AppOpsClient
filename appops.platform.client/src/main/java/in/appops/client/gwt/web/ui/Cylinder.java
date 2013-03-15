/**
 * 
 */
package in.appops.client.gwt.web.ui;

import java.util.Map;

/**
 * @author Debasish Padhy Created it on 14-Mar-2013
 *
 */
public class Cylinder extends Row {

	private int order ;
	
	private Map<String, Row> rowMap = null ;

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

	public void addRow(Row row){
		rowMap.put(row.getName() ,  row);
	}
	
	public void removeRow(Row row){
		rowMap.remove(row.getName());
	}
	
	public Row getRow(String name){
		return rowMap.get(name);
	}

}
