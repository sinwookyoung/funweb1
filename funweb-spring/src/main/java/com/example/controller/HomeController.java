package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller // 클래스 안에서 @GetMapping 등의 애노테이션을 사용 가능
@Slf4j
public class HomeController  {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private Timer timer;

	@GetMapping("/")
	public String index() {
		log.info("index() 호출됨");
		return "index";
	}
	
	@GetMapping("/company/welcome")
	public void welcome() {
		log.info("welcome() 호출됨");
//		return "company/welcome";
		
		// 리턴타입이 void면 애노테이션 url 요청경로를
		// 실행할 jsp뷰 이름으로 사용함
	}
	
	@GetMapping("/company/history")
	public void history() {
		log.info("history() 호출됨");
	}
	
	@GetMapping("/company/chart")
	public void chart() {
		log.info("chart() 호출됨");
	}
	
	
	@GetMapping(value = "/ajax/chartDataMember", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody // 리턴 객체를 JSON 문자열로 변환해서 응답을 줌
	public Map<String, List<Object>> ajaxChartDataMember() {
		Map<String, List<Object>> map = memberService.getAjaxChartDataMember();
		return map;
	} // ajaxChartDataMember
	
	@GetMapping("/company/realtimeChart")
	public void realtimeChart() {
		log.info("realtimeChart() 호출됨");
	}
	
}

