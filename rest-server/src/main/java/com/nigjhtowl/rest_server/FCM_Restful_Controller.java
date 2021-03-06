package com.nigjhtowl.rest_server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.MobileMemberDao;
import util.FCMUtil;
import util.myConstant.FCM_Constant;
import vo.MobileMemberVo;

@Controller
public class FCM_Restful_Controller {
	
	@Autowired
	ServletContext application;

	@Autowired
	MobileMemberDao mobileMemberDao;

	@RequestMapping("/mobile_member/list.do")
	public String selectList(Model model) {
		List<MobileMemberVo> list = mobileMemberDao.selectList();
		model.addAttribute("list", list);
		return "mobile_member/mobile_member_list";
	}
	
	@RequestMapping("/mobile_member/send_message.do")
	public String Send_Message(String title, String content, @RequestParam("idx") int[] idx_array) {
		String tokenPath = application.getRealPath(FCM_Constant.TOKEN_PATH);
		List<String> tokenList = new ArrayList<String>();
		
		for (int idx : idx_array) {
			MobileMemberVo vo = mobileMemberDao.selectOne(idx);
			if(!(vo.getDevice_token()==null)) 
				tokenList.add(vo.getDevice_token());
		}
		
		FCMUtil.send_FCM_All(tokenPath, tokenList, title, content);
		
		return "redirect:list.do";
	}

	@RequestMapping(value = "/mobile_members", method = RequestMethod.GET)
	@ResponseBody
	public Map selectList() {
		List<MobileMemberVo> list = mobileMemberDao.selectList();

		Map map = new HashMap();
		map.put("list", list);
		return map;
	}

	@RequestMapping(value = "/mobile_member/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public MobileMemberVo selectOne(@PathVariable("id") String id) {
		MobileMemberVo vo = mobileMemberDao.selectOne(id);
		
		return (vo == null) ? new MobileMemberVo() : vo;
	}

	@RequestMapping(value = "/mobile_member/idx/{idx}", method = RequestMethod.GET)
	@ResponseBody
	public MobileMemberVo selectOne(@PathVariable("idx") int idx) {
		MobileMemberVo vo = mobileMemberDao.selectOne(idx);
		return (vo == null) ? vo = new MobileMemberVo() : vo;
	}

	@RequestMapping(value = "/mobile_member", method = RequestMethod.POST)
	@ResponseBody
	public Map insert(@RequestBody MobileMemberVo vo) {
		int res = 0;
		
		try {
			res = mobileMemberDao.insert(vo);
		}catch(Exception e){
			res = 0;
		}

		Map map = new HashMap();
		map.put("result", res);
		return map;
	}
	
	@RequestMapping(value = "/mobile_member/device_token", method = RequestMethod.PUT)
	@ResponseBody
	public Map updateDeviceToken(@RequestBody MobileMemberVo vo) {
		int res = 0;
		
		try {
			res = mobileMemberDao.updateDeviceToken(vo);
		}catch(Exception e){
			res = 0;
		}

		Map map = new HashMap();
		map.put("result", res);
		return map;
	}

	@RequestMapping(value = "/mobile_member/{idx}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map delete(@PathVariable("idx") int idx) {

		int res = mobileMemberDao.delete(idx);

		Map map = new HashMap();
		map.put("result", res);
		return map;
	}

}
