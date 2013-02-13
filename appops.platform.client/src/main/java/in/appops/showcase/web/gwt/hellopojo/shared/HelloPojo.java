/**
 * 
 */
package in.appops.showcase.web.gwt.hellopojo.shared;

import java.math.BigInteger;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;

/**
 * @author Debasish Padhy Created it on 27-Aug-2012
 * 
 * We can do away with this pojo by simply defining a type using a string and relying on default entity instance
 */

@SuppressWarnings("serial")
final public class HelloPojo extends Entity{

	public HelloPojo(){
		
	}
	/**
	 * @param name
	 */
	public HelloPojo(String str) {
		setName("Hello" + str);
	}

	public HelloPojo(String name, String msg) {
		setName("Hello "+ name + msg);
	}
	public Key<BigInteger> getKey(){
		return getPropertyByName("id");
	}
		
	public String getName(){
		return getPropertyByName("name");
	}
	
	/**
	 * @param string
	 * @return
	 */

	public void setName(String nm){
		setPropertyByName("name", nm);
	}
	
}
