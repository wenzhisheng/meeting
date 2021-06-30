package com.meeting.common.application.service.impl;

import com.meeting.common.application.dao.IApplicationDao;
import com.meeting.common.application.service.IApplicationService;
import com.meeting.common.navigation.dao.INavigationDao;
import com.meeting.common.navigation.service.INavigationService;
import com.meeting.common.pojo.application.ApplicationVO;
import com.meeting.common.pojo.navigation.NavigationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dameizi
 * @description 应用业务层
 * @dateTime 2019-06-15 12:47
 * @className com.meeting.common.navigation.service.impl.ApplicationServiceImpl
 */
@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private IApplicationDao iApplicationDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-07-10 22:02
     * @description: 应用列表
     * @param: [applicationVO]
     * @return: java.util.List<com.meeting.common.pojo.application.ApplicationVO>
     */
    @Override
    public List<ApplicationVO> list(ApplicationVO applicationVO) {
        return iApplicationDao.list(applicationVO);
    }
}
