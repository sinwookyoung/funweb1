package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.MemberVo;
import com.example.mapper.MemberMapper;

import lombok.extern.java.Log;

@Service
public class MemberService {

	// 스프링 빈으로 등록된 객체들 중에서
	// 타입으로 객체의 참조를 가져와서 참조변수에 저장해줌
	private MemberMapper memberMapper;
	
	@Autowired
	public void setMemberMapper(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}

	public MemberVo getMemberById(String id) {
		MemberVo memberVo = memberMapper.getMemberById(id);
		return memberVo;
	}
	
	public void addMember(MemberVo memberVo) {
		memberMapper.addMember(memberVo);
	}
	
	public List<MemberVo> getAllMembers() {
		List<MemberVo> list = memberMapper.getAllMembers();
		return list;
	}
	
	
	public int userCheck(String id, String passwd) {
		int check = -1;
		
		String dbPasswd = memberMapper.userCheck(id);
		
		if (dbPasswd != null) {
			if (BCrypt.checkpw(passwd, dbPasswd)) { // passwd.equals(dbPasswd)
				check = 1;
			} else {
				check = 0;
			}
		} else { // dbPasswd == null
			check = -1;
		}
		return check;
	}
	
	public int getCountById(String id) {
		int count = memberMapper.getCountById(id);
		return count;
	}
	
	public void update(MemberVo memberVo) {
		memberMapper.update(memberVo);
	}
	
	public void deleteById(String id) {
		memberMapper.deleteById(id);
	}
	
	public void deleteAll() {
		memberMapper.deleteAll();
	}
	
	public List<Map<String, Object>> getGenderPerCount() {
		List<Map<String, Object>> list = memberMapper.getGenderPerCount();
		return list;
	}
	
	public List<Map<String, Object>> getAgeRangePerCount() {
		List<Map<String, Object>> list = memberMapper.getAgeRangePerCount();
		return list;
	}
	
	// 차트 그리기 위한 JSON 데이터 구하기 용도
	public Map<String, List<Object>> getAjaxChartDataMember() {
		Map<String, List<Object>> allDataMap = new HashMap<>();
		
		//============== 성별 회원수 데이터 구하기 ==============
		List<Object> genderPerCountList = new ArrayList<>();
		
		List<String> titleList1 = new ArrayList<>();
		titleList1.add("성별");
		titleList1.add("회원수");
		
		genderPerCountList.add(titleList1);
		
		List<Map<String, Object>> dbList1 = memberMapper.getGenderPerCount();
		
		for (Map<String, Object> rowMap : dbList1) {
			String gender = (String) rowMap.get("gender");
			long cnt = (long) rowMap.get("cnt");
			
			List<Object> rowList = new ArrayList<>();
			rowList.add(gender);
			rowList.add(cnt);
			
			genderPerCountList.add(rowList);
		} // for
		
		allDataMap.put("genderPerCountList", genderPerCountList);
		
		//============ 나이대별 회원수 데이터 구하기 ============
		List<Object> agePerCountList = new ArrayList<>();
		
		List<String> titleList2 = new ArrayList<>();
		titleList2.add("나이대");
		titleList2.add("회원수");
		
		agePerCountList.add(titleList2);
		
		List<Map<String, Object>> dbList2 = memberMapper.getAgeRangePerCount();
		
		for (Map<String, Object> map : dbList2) {
			String ageRange = (String) map.get("age_range");
			long cnt = (long) map.get("cnt");
			
			List<Object> rowList = new ArrayList<>();
			rowList.add(ageRange);
			rowList.add(cnt);
			
			agePerCountList.add(rowList);
		} // for
		
		allDataMap.put("agePerCountList", agePerCountList);
		return allDataMap;
	} // getAjaxChartDataMember
	
}







