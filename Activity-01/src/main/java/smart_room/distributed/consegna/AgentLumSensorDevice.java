package smart_room.distributed.consegna;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import smart_room.Event;
import smart_room.distributed.LuminositySensorSimulator;

public class AgentLumSensorDevice extends AbstractVerticle {

    public AgentLumSensorDevice() {
    }

    public static void main(String[] args) throws Exception {
        Vertx vertx = Vertx.vertx();
        AgentLumSensorDevice agent = new AgentLumSensorDevice();
        vertx.deployVerticle(agent);
    }

    @Override
    public void start() {
        MqttClient client = MqttClient.create(vertx);

        LuminositySensorSimulator ls = new LuminositySensorSimulator("MyLightSensor");
        ls.init();

        client.connect(1883, "broker.mqtt-dashboard.com", c -> {
            log("connected");
            ls.register((Event ev) -> {
                String luminosity = String.valueOf(ls.getLuminosity());

                log("publishing msg " + luminosity);
                client.publish("esiot-2122_luminosity",
                        Buffer.buffer(luminosity),
                        MqttQoS.AT_LEAST_ONCE,
                        false,
                        false);
            });
        });
    }

    private void log(String msg) {
        System.out.println("[MQTT AGENT LumSensorDevice] "+msg);
    }
}