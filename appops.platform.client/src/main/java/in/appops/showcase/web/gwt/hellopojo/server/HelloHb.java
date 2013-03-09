/**
 * 
 */
package in.appops.showcase.web.gwt.hellopojo.server;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;

/**
 * @author Debasish Padhy Created it on 27-Aug-2012
 *
 */
@Entity(name="hello")
@SuppressWarnings("serial")
public class HelloHb implements Serializable {

	private BigInteger id;
	
	/**
	 * @return the id
	 */
	@Column(name="id")
	public BigInteger getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	private String name ;

	/**
	 * @return the name
	 */
	@Column(name="name")
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	
	public void setName(String name) {
		this.name = name;
	}

	protected Integer version;

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


}
