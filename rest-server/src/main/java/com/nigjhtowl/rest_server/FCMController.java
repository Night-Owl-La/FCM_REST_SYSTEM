package com.nigjhtowl.rest_server;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.FCMUtil;
import util.myConstant.FCM_Constant;

@Controller
public class FCMController {

	@Autowired
	ServletContext application;

	@RequestMapping(value = "/fcmtest.do")
	@ResponseBody
	public String fcmtest(String title, String content) throws Exception {

		// console.firebase.google.com에서 받은 JSON파일
		String tokenPath = application.getRealPath(FCM_Constant.TOKEN_PATH);

		// Android폰에서 받는 토큰값
		String tokenId = FCM_Constant.TOKEN_ID;

		new FCMUtil().send_FCM(tokenPath, tokenId, title, content);

		return "ok";
	}

	@RequestMapping("/latest")
	public String latest() {
		return "test_form";
	}
}