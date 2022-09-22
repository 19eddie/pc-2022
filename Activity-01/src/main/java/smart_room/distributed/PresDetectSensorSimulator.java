/**
 * Simulator/mock for a presence detection sensor device.
 * 
 */
package smart_room.distributed;

import smart_room.*;

public class PresDetectSensorSimulator extends AbstractEventSource implements PresenceDetectionDevice {

	private boolean isPresenceDetected;
	private final String sensorId;

	public PresDetectSensorSimulator(String sensorId){
		this.sensorId = sensorId;
		isPresenceDetected = false;
	}
	
	public void init() {
		PresenceDetectionFrame frame = new PresenceDetectionFrame(this, sensorId);
		frame.display();
	}
	
	@Override
	public synchronized boolean presenceDetected() {
		return isPresenceDetected;
	}

	synchronized void updateValue(boolean value) {
		long ts = System.currentTimeMillis();
		this.isPresenceDetected = value;
		if (value) {
			this.notifyEvent(new PresenceDetected(ts));
		} else {
			this.notifyEvent(new PresenceNoMoreDetected(ts));
		}
	}






}
