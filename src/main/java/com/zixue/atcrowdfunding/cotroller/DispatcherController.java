package com.zixue.atcrowdfunding.cotroller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zixue.atcrowdfunding.bean.AjaxResult;
import com.zixue.atcrowdfunding.bean.User;
import com.zixue.atcrowdfunding.service.UserService;

@Controller
public class DispatcherController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/main")
	public String main (){
		return "main";
	}
	
	@RequestMapping("/logout")
	public String logout (HttpSession session){
//		session.removeAttribute("loginUser");
		session.invalidate();//session失效
		return "redirect:/login";
	}
	
	@ResponseBody
	@RequestMapping("/doAjaxLogin")
	public Object doAjaxLogin(User user,HttpSession session) throws UnsupportedEncodingException{
		AjaxResult ajaxResult = new AjaxResult();
		User dbuser = userService.queryLogin(user);
		if(dbuser != null){
			ajaxResult.setSuccess(true);
			session.setAttribute("loginUser", dbuser);
		}else{
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}
	/**
	 * @Description 执行登录
	 * @author hp
	 * @date 2020年4月21日  下午11:37:50
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/dologin")
	public String dologin(User user, Model model) throws UnsupportedEncodingException{
		String loginacct = user.getLoginacct();
		// 将乱码字符串按照错误的编码方式转换为原始的字节码序列(前天编码设置了UTF-8,服务器默认编码格式是ISO8859-1)
		//byte[] bytes = loginacct.getBytes("ISO8859-1");
		// 将原始的字节码序列按照正确的编码转换为正确的文字即可
		//loginacct = new String(bytes,"UTF-8");
		// 最优的解决方法使用spring提供的过滤器
		
		
		// 1.获取表单属性
		// 1-1.HttpServletRequest
		// 1-2.在方法参数列表中添加表单对应的参数，名称相同
		// 1-3.就是将表单数据封装为实体类对象
		
		// 2.查询用户信息
		User dbuser = userService.queryLogin(user);
		if(dbuser != null){
			// 登录成功，跳转主页面
			return "main";
		}else{
			// 登录失败，跳转回到登录页面，并提示错误信息
			String errorMsg = "登录账号或密码不正确，请重新输入！";
			model.addAttribute("errorMsg", errorMsg);
			return "redirect:/login";
		}
	}
}
