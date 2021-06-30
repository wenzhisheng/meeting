package com.meeting.member;

import com.corundumstudio.socketio.SocketIOClient;
import com.meeting.common.config.socketio.MessageEventHandler;
import com.meeting.common.constant.RedisKeyConst;
import com.meeting.common.dialog.service.IDialogService;
import com.meeting.common.pojo.dialog.DialogVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.text.MessageFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MerchantApplicationTests {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IDialogService iDialogService;

    @Test
    public void contextLoads() {
        /*DialogVO dialogVO = new DialogVO();
        dialogVO.setSendId(1);
        dialogVO.setTargetId(2);
        dialogVO.setLastContent("123");
        dialogVO.setLastMsgid("124");
        dialogVO.setChatType("single");
        iDialogService.save(dialogVO);*/

        /*for (int i=0; i<10000; i++){
            String onlineRedisKey = MessageFormat.format(RedisKeyConst.MEETING_ONLINE_STATUS, "admin");
            RBucket<Object> bucket = redissonClient.getBucket(onlineRedisKey);
            System.out.println(i+"-----------"+bucket.get());
        }*/

        /*SocketIOClient client = mapUS.remove("user");
        // 精准服务端发送消息
        client.sendEvent("pushPoint", new Point(8, 9));
        // 广播消息
        //socketIOServer.getBroadcastOperations().sendEvent("broadcast","Are you pig?");*/
    }

}
