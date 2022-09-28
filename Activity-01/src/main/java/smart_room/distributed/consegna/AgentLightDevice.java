package smart_room.distributed.consegna;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttClient;
import smart_room.distributed.LightDeviceSimulator;

public class AgentLightDevice extends AbstractVerticle {

    String presence = "";
    Double luminosity = 0.0;
    LightDeviceSimulator ld = new LightDeviceSimulator("MyLight");
    AgentLightDevice(){
        ld.init();
    }

    public static void main(String[] args) throws Exception {
        Vertx vertx = Vertx.vertx();
        AgentLightDevice agent = new AgentLightDevice();
        vertx.deployVerticle(agent);
    }

    @Override
    public void start() {
        MqttClient client = MqttClient.create(vertx);

        client.connect(1883, "broker.mqtt-dashboard.com", c -> {
            log("connected");
            log("subscribing...");
            client.publishHandler(s -> {
                        System.out.println("There are new message in topic: " + s.topicName());
                        System.out.println("Content(as string) of the message: " + s.payload().toString());
                        System.out.println("QoS: " + s.qosLevel());
                        presence = s.payload().toString();
                        updateLightStatus();
                    })
                    .subscribe("esiot-2122_presence", 2);

            client.publishHandler(f -> {
                        System.out.println("There are new message in topic: " + f.topicName());
                        System.out.println("Content(as string) of the message: " + f.payload().toString());
                        System.out.println("QoS: " + f.qosLevel());
                        luminosity = Double.valueOf(f.payload().toString());
                        updateLightStatus();
                    })
                    .subscribe("esiot-2122_luminosity", 2);
        });
    }

    private void updateLightStatus(){
        if(luminosity < 0.50 && presence.equals("presenceDetected")){
            ld.on();
            log("luce on");
        } else {
            ld.off();
            log("luce off");
        }
    }
    private void log(String msg) {
        System.out.println("[MQTT AGENT LightDevice] "+msg);
    }
}
