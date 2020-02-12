package com.nigjhtowl.rest_server;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.FCMUtil;

@Controller
public class FCMController {

	@Autowired
	ServletContext application;

	@RequestMapping(value = "/fcmtest.do")
	@ResponseBody
	public String fcmtest(String title, String content) throws Exception {

		// console.firebase.google.com에서 받은 JSON파일
		String tokenPath = application.getRealPath("resources/google/fcm-test-b4e16-firebase-adminsdk-etdt4-16f9949f01.json");

		// Android폰에서 받는 토큰값
		String tokenId = "cIHMCj4PfDM:APA91bGhfwbtR71qxogm7FuNMp0Hi5q7XKlNBZJ2EKx7qn6ZfX_nSg3sb9LtyWq4S3CaRet5QGurSRH_aLPOOaZZc330KvJHZep6wm0uFFGnohPZAiXJWCciI4ebp-M_B5o4sUvx558y";

		new FCMUtil().send_FCM(tokenPath, tokenId, title, content);

		return "ok";
	}

	@RequestMapping("/latest")
	public String latest() {
		return "test_form";
	}
}