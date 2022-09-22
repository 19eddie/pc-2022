package smart_room.distributed.consegna;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import smart_room.Event;
import smart_room.distributed.PresDetectSensorSimulator;

public class AgentPresDetectDevice extends AbstractVerticle {

    public AgentPresDetectDevice() {
    }

    public static void main(String[] args) throws Exception {
        Vertx vertx = Vertx.vertx();
        AgentPresDetectDevice agent = new AgentPresDetectDevice();
        vertx.deployVerticle(agent);
    }

    @Override
    public void start() {
        MqttClient client = MqttClient.create(vertx);

        PresDetectSensorSimulator pd = new PresDetectSensorSimulator("MyPIR");
        pd.init();

        client.connect(1883, "broker.mqtt-dashboard.com", c -> {
            log("connected");
            pd.register((Event ev) -> {
                String presence = pd.presenceDetected() ? "presenceDetected" : "presenceNotDetected";

                log("publishing msg " + presence);
                client.publish("esiot-2122_presence",
                        Buffer.buffer(presence),
                        MqttQoS.AT_LEAST_ONCE,
                        false,
                        false);
            });
        });
    }

    private void log(String msg) {
        System.out.println("[MQTT AGENT PresDetectDevice] "+msg);
    }
}