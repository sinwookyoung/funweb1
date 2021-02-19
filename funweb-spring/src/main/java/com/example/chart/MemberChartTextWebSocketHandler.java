package com.example.chart;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Getter
@Slf4j
public class MemberChartTextWebSocketHandler extends TextWebSocketHandler {
	
	private final Set<WebSocketSession> sessions = new HashSet<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("=== MemberChartTextWebSocketHandler 와 소켓 연결됨 ===");
		sessions.add(session);
	}
	
	public void broadcast(String message) throws Exception {
		log.info("=== MemberChartTextWebSocketHandler broadcasting ===");
		// 브로드캐스팅 하기
		for (WebSocketSession sess : sessions) {
			sess.sendMessage(new TextMessage(message));
		} // for
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("=== MemberChartTextWebSocketHandler 와 소켓 연결이 해제됨 ===");
		sessions.remove(session);
	}
}
