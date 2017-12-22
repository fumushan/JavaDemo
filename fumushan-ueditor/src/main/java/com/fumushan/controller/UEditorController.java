package com.fumushan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fumushan.util.ueditor.ActionEnter;

/**
 * @author FUMUSHAN
 * @datetime 2017年12月22日 上午10:48:27
 */
@Controller
@RequestMapping("/ueditor")
public class UEditorController {

	@RequestMapping(value = "exec")
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Content-Type", "text/html");
			String info = new ActionEnter(request).exec();
			PrintWriter writer = response.getWriter();
			writer.write(info);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
