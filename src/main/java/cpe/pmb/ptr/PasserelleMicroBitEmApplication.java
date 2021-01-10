package cpe.pmb.ptr;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@ComponentScan(basePackages= {"mqtt", "uart"})
public class PasserelleMicroBitEmApplication {
	
	@Autowired
	public MqttClient mqttClient;
	
	
	public static void main(String[] args) {
		SpringApplication.run(PasserelleMicroBitEmApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner websocketDemo() {
        return (args) -> {
            while (true) {
                try {
                    Thread.sleep(3*1000); // Each 3 sec. FOR TEEEEEEST.
                    //System.out.println("envoi d'un message");
                    //mqttClient.publish("lyon/mairie", new MqttMessage("22".getBytes()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
