package com.ty.security.qrcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twilio.Twilio;
import com.ty.security.qrcode.configuration.TwilioConfig;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class DemoSmsSenderPhoneApplication {

	@Autowired
	private TwilioConfig twilioConfig;
	
	@PostConstruct
	public void setup() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoSmsSenderPhoneApplication.class, args);
	}

}
