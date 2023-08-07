package kr.bit.model;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {
	private static SqlSessionFactory sqlSessionFactory; // [SqlSession..] config.xml 의 정보를 읽어서 만들어 낸다.

	// 초기화 블록 - 프로그램 실행시 한번만 실행 되는 코드 영역
	static {
		try {
			String resource = "kr/bit/mybatis/config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);// 읽기
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 회원 전체 리스트 보기
	public List<MemberVO> memberList() {
		// JDBC : Connection(db연결 객체) + statement(전송 객체) => Mybatis : SQLSession
		SqlSession session = sqlSessionFactory.openSession(); // Connection 받음
		List<MemberVO> list = session.selectList("memberList"); // sql 명령문이 어디에? (Mapper 파일의 SQL 을 연결)
		session.close();// SQLSession 반납
		return list;
	}

	// 회원 가입 기능
	public int memberInsert(MemberVO vo) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.insert("memberInsert", vo);
		session.commit();
		session.close();
		return cnt;
	}

	// 회원 삭제 기능
	public int memberDelete(int num) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.delete("memberDelete", num);
		session.commit();
		session.close();
		return cnt;
	}

	// 회원 수정 기능
	public int memberUpdate(MemberVO vo) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.update("memberUpdate", vo);
		session.commit();
		session.close();
		return cnt;
	}

	// 회원 상세 보기
	public MemberVO memberContent(int num) {
		SqlSession session = sqlSessionFactory.openSession();
		MemberVO vo = session.selectOne("memberContent", num);
		session.close();
		return vo;
	}
}
