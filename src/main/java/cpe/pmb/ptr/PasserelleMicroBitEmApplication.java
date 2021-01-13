package cpe.pmb.ptr;


import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.MessageChannel;

import gnu.io.NRSerialPort;
import service.PasserelleService;

@SpringBootApplication
@ComponentScan(basePackages= {"mqtt", "uart", "service", "config"})
public class PasserelleMicroBitEmApplication {
	
	@Autowired
	public MqttClient mqttClient;
	
	@Autowired
	public PasserelleService passerelleService;
	
	public static void main(String[] args) {
		SpringApplication.run(PasserelleMicroBitEmApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner websocketDemo() {
        return (args) -> {
        	NRSerialPort serial = new NRSerialPort("COM6", 115200);
        	serial.connect();


			DataInputStream ins = new DataInputStream(serial.getInputStream());
			DataOutputStream outs = new DataOutputStream(serial.getOutputStream());
        	try{
        		while(!Thread.interrupted()) {
        			
        			String allBytes = "";
        			if(ins.available()>0) {
        				while (true) {
            				char b = (char) ins.read();
            				System.out.println(b);
            				String bString = String.valueOf(b);
            				allBytes = allBytes + bString;
            				if (bString.equals("%")) {
            					System.out.println("aaaaa");
            					ins.read();
            					break;
            				}
        				}
        				passerelleService.buildObjectFire(allBytes);
        			}
        			
        			
        			
        	    	Thread.sleep(5);
        		}
        	} catch(Exception ex){
        		ex.printStackTrace();
        	}
        };
    }
}
