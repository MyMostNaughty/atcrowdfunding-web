package com.zixue.atcrowdfunding.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zixue.atcrowdfunding.bean.Permission;
import com.zixue.atcrowdfunding.service.PermissionService;

// 适配器
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private PermissionService permissionService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取用户的请求地址
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		// 判断当前路径是否需要进行权限验证。
		// 查询所有需要验证的路径集合
		List<Permission> permissions = permissionService.queryAll();
		Set<String> uriSet = new HashSet<>();
		for (Permission permission : permissions) {
			if(permission.getUrl() != null && permission.getUrl() != ""){
				uriSet.add(session.getServletContext().getContextPath() + permission.getUrl());
			}
		}

		if(uriSet.contains(uri)){
			// 权限验证
			// 判断当前的用户是否拥有对应的权限
			Set<String> authUriSet = (Set<String>) session.getAttribute("authUriSet");
			if(authUriSet.contains(uri)){
				return true;
			}else{
				String path = session.getServletContext().getContextPath();
				response.sendRedirect(path+"/error");
				return false;
			}
		}else{
			return true;
		}
		
	}


}
