package uart;

import org.springframework.stereotype.Component;


@Component
public class SerialPortConnector extends AbstractImplSerialPort  {

	@Override
	public void processData(String line) {
		System.out.println(line);
	}

 
}