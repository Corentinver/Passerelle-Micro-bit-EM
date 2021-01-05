package uart;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adventurer.springserialport.connector.SpringSerialPortConnector;
import com.adventurer.springserialport.connector.properties.SerialPortProperties;

import gnu.io.NRSerialPort;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import service.PasserelleService;

@Component
public abstract class SerialPortConnector implements SpringSerialPortConnector {

    private BufferedReader input;

    private NRSerialPort serial;
    
    @Autowired
    public PasserelleService passerelleService;

    @PostConstruct
    public void connect() throws TooManyListenersException, NoSuchPortException {

        serial = new NRSerialPort("", 200);
        serial.connect();

        if (!serial.isConnected()) {
            throw new RuntimeException("Is not possible to open connection in " + "" + " port");
        }
        serial.addEventListener(this);
        serial.notifyOnDataAvailable(true);
        input = new BufferedReader(new InputStreamReader(serial.getInputStream()));
    }

    @PreDestroy
    public void disconnect() {


        if (serial != null && serial.isConnected()) {
            serial.disconnect();

        }
    }


    @Override
    public synchronized void sendMessage(String message) {
        DataOutputStream stream = new DataOutputStream(serial.getOutputStream());
        try {
			stream.write(message.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public synchronized void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                if (input.ready()) {
                    processData(input.readLine());
                }

            } catch (Exception e) {
            }
        }
    }

    /**
     * This method must implemented with logic that you want execute at the moment to read
     * information from the serial port
     *
     * @param line input data that comes from Serial port
     */
    public void processData(String line) {
    	// faire le traitement des données reçues
    	// passerelleService.newFire(....);
    }
}