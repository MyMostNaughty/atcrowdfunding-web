package com.zixue.atcrowdfunding.cotroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zixue.atcrowdfunding.bean.User;
import com.zixue.atcrowdfunding.service.UserService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/queryAll")
	public Object queryAll(){
		List<User> users = userService.queryAll();
		return users;
	}
	
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@ResponseBody
	@RequestMapping("/json")
	public Object json(){
		Map map = new HashMap();
		map.put("username", "张三");
		return map;
	}
}
