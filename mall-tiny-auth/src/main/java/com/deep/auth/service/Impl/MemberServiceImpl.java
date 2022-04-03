package com.deep.auth.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.auth.dao.MemberDao;
import com.deep.auth.model.entity.MemberEntity;
import com.deep.auth.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * 会员
 *
 * @author Deep
 * @date 2022/4/1
 */
@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
}
