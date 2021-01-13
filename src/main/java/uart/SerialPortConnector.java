package uart;

import org.springframework.stereotype.Component;



public class SerialPortConnector extends AbstractImplSerialPort  {

	@Override
	public void processData(String line) {
		System.out.println(line);
	}

 
}