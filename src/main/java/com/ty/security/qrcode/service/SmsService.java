package com.ty.security.qrcode.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.ty.security.qrcode.configuration.TwilioConfig;
import com.ty.security.qrcode.dto.OtpRequest;
import com.ty.security.qrcode.dto.OtpResponseDto;
import com.ty.security.qrcode.dto.OtpStatus;
import com.ty.security.qrcode.dto.OtpValidationRequest;

@Service
public class SmsService {

	@Autowired
	private TwilioConfig twilioConfig;
	
	Map<String,String> otpMap=new HashMap<String,String>();
	
	public OtpResponseDto sendSMS(OtpRequest otpRequest) {
		OtpResponseDto otpResponseDto=null;
		try {
			PhoneNumber to =new PhoneNumber(otpRequest.getPhoneNumber());
			PhoneNumber from =new PhoneNumber(twilioConfig.getPhonenumber());
			String otp=generateOTP();
			String otpMessage="Dear UPI user A/C X9645 debited by 5000.0 on date 09MAR24 to MURAM THIRUMULESH Refno 45874740438 by this otp "+otp+". if not contact to axis bank THANK YOU!!!!!";
			Message message=Message.creator(to, from, otpMessage).create();
			otpMap.put(otpRequest.getUsername(),otp);
			otpResponseDto=new OtpResponseDto(OtpStatus.DELIVERED,otpMessage);
		}catch (Exception e) {
			otpResponseDto=new OtpResponseDto(OtpStatus.FAILED,e.getMessage());
			
		}
		
		return otpResponseDto;
	}

	private String generateOTP() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999)) ;
		
	}

	public String validateOtp(OtpValidationRequest otpValidationRequest) {
		Set<String> keys=otpMap.keySet();
		String username=null;
		for (String key : keys) {
			username=key;
		}
		if(otpValidationRequest.getUsername().equals(username)) {
			otpMap.remove(username,otpValidationRequest.getClass());
			return "otp is valid ";
		}
		
		return "otp is not valid" ;
	}

}
