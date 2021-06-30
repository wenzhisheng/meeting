package com.meeting.common.util;

import com.meeting.common.pojo.base.PageResult;
import com.meeting.common.pojo.base.PageVO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;
import java.util.List;

/**
 * @author dameizi
 * @description mongo工具
 * @dateTime 2019-07-13 18:53
 * @className com.meeting.common.util.MongoDBUtil
 */
public class MongoDBUtil {

    /**
     * @author: dameizi
     * @dateTime: 2019-07-13 18:53
     * @description: mongo分页处理
     * @param: [mongoTemplate, query, pageVO, classz]
     * @return: com.meeting.common.pojo.base.PageResult<?>
     */
    public static PageResult<?> mongoPageResult(MongoTemplate mongoTemplate, Query query, PageVO pageVO, Class classz) {
        int totalRecord = (int)mongoTemplate.count(query,classz);
        // 分页组装
        PageResult<?> pageResult = new PageResult<>();
        // 总条数
        pageResult.setTotalRecord(totalRecord);
        // 总共多少页
        pageResult.setTotalPage(totalRecord % pageVO.getPageSize() == 0 ?
                totalRecord / pageVO.getPageSize() : totalRecord / pageVO.getPageSize() + 1);
        pageResult.setPageSize(pageVO.getPageSize());
        pageResult.setPageNo(pageVO.getPageNo());
        List<?> list = mongoTemplate.find(query.skip((pageVO.getPageNo() - 1) *
                pageVO.getPageSize()).limit(pageVO.getPageSize()),classz);
        pageResult.setResult(list);
        if (list == null || list.size() == 0) {
            PageResult<?> result = new PageResult<>();
            result.setResult(Collections.emptyList());
            return result;
        }
        return pageResult;
    }

}
