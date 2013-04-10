package in.appops.client.gwt.web.ui.messaging.atomosphereutil;

import in.appops.platform.core.entity.broadcast.RealTimeSyncEvent;
import org.atmosphere.gwt.client.AtmosphereGWTSerializer;
import org.atmosphere.gwt.client.SerialTypes;


/** 
 * @author nitish@ensarm.com on 29-Nov-2012 at 12:13:06 PM
 *
 */
@SerialTypes(value = { RealTimeSyncEvent.class })
public abstract class RealTimeSyncEventSerializer extends AtmosphereGWTSerializer {
	
}
