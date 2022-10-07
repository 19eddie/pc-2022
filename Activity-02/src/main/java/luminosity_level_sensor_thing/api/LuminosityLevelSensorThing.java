package luminosity_level_sensor_thing.api;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;


public interface LuminosityLevelSensorThing {
	
	/**
	 * Get the TD
	 */
	Future<JsonObject> getTD();
	
	/* properties */

	/**
	 *
	 * get luminosity level
	 *
	 * @return
	 */
	Future<Double> getLuminosity();

	/* actions */

	/**
	 * Subscribe to events generated by the luminosity level sensor device
	 * 
	 * @param handler
	 * @return
	 */
	Future<Void> subscribe(Handler<JsonObject> handler);
}
