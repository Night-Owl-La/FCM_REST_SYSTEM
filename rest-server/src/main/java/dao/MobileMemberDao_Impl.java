package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vo.MobileMemberVo;

@Repository("mobileMemberDao")
public class MobileMemberDao_Impl implements MobileMemberDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<MobileMemberVo> selectList() {
		return sqlSession.selectList("mobile_member.list_mobile_member");
	}

	@Override
	public MobileMemberVo selectOne(String id) {
		return sqlSession.selectOne("mobile_member.one_mobile_member_id", id);
	}

	@Override
	public MobileMemberVo selectOne(int idx) {
		return sqlSession.selectOne("mobile_member.one_mobile_member_idx", idx);
	}

	@Override
	public int insert(MobileMemberVo vo) {
		return sqlSession.insert("mobile_member.insert_mobile_member", vo);
	}

	@Override
	public int update(MobileMemberVo vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateDeviceToken(MobileMemberVo vo) {
		return sqlSession.update("mobile_member.update_mobile_member_device_token", vo);
	}

	@Override
	public int delete(int idx) {
		// TODO Auto-generated method stub
		return 0;
	}

}
