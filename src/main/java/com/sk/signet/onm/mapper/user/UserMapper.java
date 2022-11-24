package com.sk.signet.onm.mapper.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.annotation.MapperScan;

import com.sk.signet.onm.web.user.domain.User;

@MapperScan
@SuppressWarnings("rawtypes")
public interface UserMapper {

	/**
	 * 사용자정보 리스트 조회
	 */
//	List<Map<String, Object>> selectUserList();
	
	/**
	 * 사용자정보 조회
	 * @param param
	 * @return
	 */
	User selectUser(Map param);
	
	/**
	 * 사용자정보 등록
	 * @param param
	 * @return
	 */
	int insertUser(Map param);
	
	/**
	 * 사용자정보 수정
	 * @param param
	 */
	int updateUser(Map param);
	
	/**
	 * 사용자 조회 
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> selectUserList(Map param);
}
