package com.ty.security.qrcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ty.security.qrcode.dto.OtpRequest;
import com.ty.security.qrcode.dto.OtpResponseDto;
import com.ty.security.qrcode.dto.OtpValidationRequest;
import com.ty.security.qrcode.service.SmsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/otp")
@Slf4j
public class OtpController {

	@Autowired
	private SmsService smsService;

	@GetMapping("/process")
	public String processSMS() {
		return "sms sent";

	}
	@PostMapping("/sendotp")
	public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
		log.info("inside otp ::" +otpRequest.getUsername());
		return smsService.sendSMS(otpRequest);
	}
	
	@PostMapping("/validateotp")
	public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
		log.info("inside sent otp ::" +otpValidationRequest.getUsername()+" "+otpValidationRequest);
		return smsService.validateOtp(otpValidationRequest);
	}

	
}
