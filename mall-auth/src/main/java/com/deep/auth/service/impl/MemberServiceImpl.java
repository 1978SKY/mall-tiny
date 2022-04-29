package com.deep.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.auth.dao.MemberDao;
import com.deep.auth.model.entity.MemberEntity;
import com.deep.auth.model.params.PasswordParam;
import com.deep.auth.service.MemberService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 会员
 *
 * @author Deep
 * @date 2022/4/1
 */
@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public boolean updatePassword(PasswordParam param) {
        MemberEntity member = getById(param.getMemberId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (member.getPassword() != null && !encoder.matches(param.getOldPassword(), member.getPassword())) {
            return false;
        }
        member.setPassword(encoder.encode(param.getNewPassword()));
        this.updateById(member);
        return true;
    }
}
