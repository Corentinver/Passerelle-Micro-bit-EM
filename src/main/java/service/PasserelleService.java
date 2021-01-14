package service;

import java.math.BigInteger;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.FireDTO;

@Service
public class PasserelleService {

	@Autowired
	public RestTemplate restTemplate;
	
	@Autowired
	public ObjectMapper objectMapper;
	
	@Autowired
	public MqttClient mqttClient;
	
	public void newFire(FireDTO fire) {
		System.out.println("new");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    ObjectMapper objectMapper = new ObjectMapper();
		HttpEntity<String> request;
		try {
			request = new HttpEntity<String>(objectMapper.writeValueAsString(fire), headers);
			restTemplate.postForEntity("http://localhost:8082/newFirePasserelle", request, FireDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void updateFire(FireDTO fire) {
		System.out.println("update");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    ObjectMapper objectMapper = new ObjectMapper();
		HttpEntity<String> request;
		try {
			request = new HttpEntity<String>(objectMapper.writeValueAsString(fire), headers);
			restTemplate.postForEntity("http://localhost:8082/updateFirePasserelle", request, FireDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	
	public void buildObjectFire(String fire) throws JsonMappingException, JsonProcessingException, MqttPersistenceException, MqttException {
		System.out.println(fire);
		String jsonFire = fire.substring(fire.lastIndexOf("$") + 1, fire.lastIndexOf("%"));
		System.out.println(jsonFire);
		FireDTO fireDTO = objectMapper.readValue(jsonFire, FireDTO.class);	

		if (fire.startsWith("b'new$")) {
			this.newFire(fireDTO);
			return;
		} else if (fire.startsWith("b'update$")) {
			this.updateFire(fireDTO);
			return;
		}
		MqttMessage message = new MqttMessage();
		message.setPayload(BigInteger.valueOf(fireDTO.getIntensity()).toByteArray());
		mqttClient.publish(fireDTO.getLocation().getLatitude() + ";" + fireDTO.getLocation().getLongitude(), message);
	}

}
