package com.meeting.common.areacascade.service.impl;

import com.meeting.common.areacascade.dao.IAreaCascadeDao;
import com.meeting.common.areacascade.service.IAreaCascadeService;
import com.meeting.common.pojo.areacascade.AreaCascadeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dameizi
 * @description 地区级联
 * @dateTime 2019-07-11 12:25
 * @className com.meeting.common.areacascade.service.impl.AreaCascadeServiceImpl
 */
@Service
public class AreaCascadeServiceImpl implements IAreaCascadeService {

    @Autowired
    private IAreaCascadeDao iAreaCascadeDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-07-11 12:29
     * @description: 级联列表
     * @param: []
     * @return: java.lang.Object
     */
    @Override
    public Object cascadeList() {
        AreaCascadeVO areaCascadeVO = new AreaCascadeVO();
        areaCascadeVO.setLevel(1);
        areaCascadeVO.setParentId(-1);
        List<AreaCascadeVO> areaCascadeList = iAreaCascadeDao.cascadeList(areaCascadeVO);
        for (AreaCascadeVO areaCascade: areaCascadeList) {
            areaCascadeVO = new AreaCascadeVO();
            areaCascadeVO.setParentId(areaCascade.getAreaId());
            List<AreaCascadeVO> areaCascadeList1 = iAreaCascadeDao.cascadeList(areaCascadeVO);
            areaCascade.setDistricts(areaCascadeList1);
            for (AreaCascadeVO areaCascade1: areaCascadeList1) {
                areaCascadeVO = new AreaCascadeVO();
                areaCascadeVO.setParentId(areaCascade1.getAreaId());
                List<AreaCascadeVO> areaCascadeList2 = iAreaCascadeDao.cascadeList(areaCascadeVO);
                areaCascade1.setDistricts(areaCascadeList2);
            }
        }
        return areaCascadeList;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-11 16:13
     * @description: 地区省列表
     * @param: []
     * @return: java.lang.Object
     */
    @Override
    public Object provinceList() {
        AreaCascadeVO areaCascadeVO = new AreaCascadeVO();
        areaCascadeVO.setParentId(-1);
        return iAreaCascadeDao.cascadeList(areaCascadeVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-07-11 16:13
     * @description: 地区市县查询
     * @param: [areaCascadeVO]
     * @return: java.lang.Object
     */
    @Override
    public Object cityList(AreaCascadeVO areaCascadeVO) {
        return iAreaCascadeDao.cascadeList(areaCascadeVO);
    }

}
