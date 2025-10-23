package com.darkyellowcat.weconnected.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darkyellowcat.weconnected.service.UserTeamService;
import com.darkyellowcat.weconnected.model.domain.UserTeam;
import com.darkyellowcat.weconnected.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
 * 用户队伍服务实现类
 *
 * @author darkyellowcat
 */
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
        implements UserTeamService {

}




