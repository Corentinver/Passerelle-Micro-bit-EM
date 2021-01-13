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
        	String port = "";
        	for(String s:NRSerialPort.getAvailableSerialPorts()){
        		System.out.println("Availible port: "+s);
        		port=s;
        	}

        	int baudRate = 115200;
        	NRSerialPort serial = new NRSerialPort(port, baudRate);
        	serial.connect();


			DataInputStream ins = new DataInputStream(serial.getInputStream());
			DataOutputStream outs = new DataOutputStream(serial.getOutputStream());
        	try{
        		while(ins.available()==0 && !Thread.interrupted());// wait for a byte
        		while(!Thread.interrupted()) {// read all bytes
        			if(ins.available()>0) {
        				char b = (char) ins.read();
        				System.out.print(b);
        			}
        	    		Thread.sleep(5);
        		}
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
        };
    }
}
