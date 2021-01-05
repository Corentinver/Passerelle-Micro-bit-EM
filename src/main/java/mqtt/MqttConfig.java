package mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig{

    @Bean
    public MqttClient mqttClientFactory() throws MqttException {
    	String publisherId = UUID.randomUUID().toString();
    	MqttClient mqttClient = new MqttClient("tcp://192.168.1.22:1883", publisherId);
    	MqttConnectOptions options = new MqttConnectOptions();
    	options.setAutomaticReconnect(true);
    	options.setCleanSession(true);
    	options.setConnectionTimeout(10);
    	mqttClient.connect(options);
    	return mqttClient;
    }

}