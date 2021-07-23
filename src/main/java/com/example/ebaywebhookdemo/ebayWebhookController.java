package com.example.ebaywebhookdemo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ebayWebhookController {
	
    @RequestMapping("/")
    @ResponseBody
    String home() {
      return "Hello World!";
    }
	
	@RequestMapping(value = "notification", method = RequestMethod.GET, produces = { "application/json"})
	ResponseEntity<Map<String, Object>> notification(@RequestParam String challenge_code) throws Exception {
		System.out.println("incoming...");
		
		String challengeCode = challenge_code;
		String verificationToken = "1234-1234-1234-1234-1234-1234-1234";
		String endpoint = "https://ebay-webhook-demo.herokuapp.com/notification";
		
		System.out.println("challenge_code: "+ challenge_code);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(challengeCode.getBytes(StandardCharsets.UTF_8));
		digest.update(verificationToken.getBytes(StandardCharsets.UTF_8));
		byte[] bytes = digest.digest(endpoint.getBytes(StandardCharsets.UTF_8));
		
		System.out.println("Returning challenge_code: "+ bytes.toString());
		
		map.put("challengeResponse", bytes.toString());
		
		//System.out.println(org.apache.commons.codec.binary.Hex.encodeHexString(bytes));
		
		return new ResponseEntity<Map<String, Object>>(map,  HttpStatus.OK);
	}

}
