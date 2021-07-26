package uk.co.blackwell.microservice.ebay.notification;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EbayNotificationController {
	
	private static final Logger logger = LoggerFactory.getLogger(EbayNotificationController.class);
	
	@RequestMapping(value = "notification", method = RequestMethod.GET, produces = { "application/json"})
	ResponseEntity<Map<String, Object>> notification(@RequestParam String challenge_code) throws Exception {
		
		String challengeCode = challenge_code;
		
		//TO-DO : handling them as a prop file 
		String verificationToken = "1234-1234-1234-1234-1234-1234-1234";
		String endpoint = "https://ebay-webhook-demo.herokuapp.com/notification";
		
		logger.info("challenge_code: {}", challenge_code);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(challengeCode.getBytes(StandardCharsets.UTF_8));
		digest.update(verificationToken.getBytes(StandardCharsets.UTF_8));
		byte[] bytes = digest.digest(endpoint.getBytes(StandardCharsets.UTF_8));
		
		logger.info("Returning challenge_code: {}", org.apache.commons.codec.binary.Hex.encodeHexString(bytes));
		
		map.put("challengeResponse", org.apache.commons.codec.binary.Hex.encodeHexString(bytes));
		
		
		return new ResponseEntity<Map<String, Object>>(map,  HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "notification", method = RequestMethod.POST, produces = {"application/json"})
	ResponseEntity<String> notification(HttpServletRequest request, @RequestBody Object obj) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();  
        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        
        logger.info("Request Header: {}", map.toString());
        logger.info("Request Body: {}", mapper.writeValueAsString(obj));
		return new ResponseEntity<>("Success",  HttpStatus.OK);
	}

}
