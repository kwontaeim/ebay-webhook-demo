package uk.co.blackwell.microservice.ebay.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:ebay-notification.properties")
public class EbayNotificationProperties {
	
	@Value("${verification.token}")
	private String verificationToken;
	@Value("${endpoint}")
	private String endpoint;
	
	
	public String getVerificationToken() {
		return verificationToken;
	}
	public String getEndoint() {
		return endpoint;
	}
	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}
