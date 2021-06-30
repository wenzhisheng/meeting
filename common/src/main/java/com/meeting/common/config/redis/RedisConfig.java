package com.meeting.common.config.redis;

import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;

/**
 * @dateTime 2019-03-26 22:39
 * @author: dameizi
 * @description: Redis配置
 */
@Configuration
public class RedisConfig {

    /** 超时时间 */
    @Value("${cluster-servers-config.timeout}")
    private int timeout;
    /** 超时时间 */
    @Value("${cluster-servers-config.connecct-timeout}")
    private int connecctTimeout;
    /** 密码 */
    @Value("${cluster-servers-config.password}")
    private String password;
    /** 从连接池大小 */
    @Value("${cluster-servers-config.slave-connection-pool-size}")
    private int slaveConnectionPoolSize;
    /** ping连接间隔 */
    @Value("${cluster-servers-config.ping-connection-interval-size}")
    private int pingConnectionIntervalSize;
    /** 主连接池大小 */
    @Value("${cluster-servers-config.master-connection-pool-size}")
    private int masterConnectionPoolSize;
    /** 从连接池大小 */
    @Value("${cluster-servers-config.connection-minimum-idle-size}")
    private int connectionMinimumIdleSize;
    /** 连接失败重试次数 */
    @Value("${cluster-servers-config.failed-attempts}")
    private int failedAttempts;
    /** 连接失败重试次数 */
    @Value("${cluster-servers-config.connection-pool-size}")
    private int connectionPoolSize;
    /** 连接失败重试时间 */
    @Value("${cluster-servers-config.retry-interval}")
    private int retryInterval;
    /** 命令失败重试次数 */
    @Value("${cluster-servers-config.retry-attempts}")
    private int retryAttempts;
    /** 空闲连接超时时间 */
    @Value("${cluster-servers-config.idle-connection-timeout}")
    private int idleConnectionTimeout;
    /** 空闲连接超时时间 */
    @Value("${cluster-servers-config.scan-interval}")
    private int scanInterval;
    /** 哨兵模式数组 */
    @Value("${cluster-servers-config.cluster-addresses}")
    private String[] clusterAddresses;

    @Bean(value = "redissonClient")
    public RedissonClient getRedis(){
        Config config = new Config();
        // 单机版redisson
        /*config.useSingleServer()
                .setAddress("redis://61.216.31.153:7001")
                .setConnectionPoolSize(this.connectionPoolSize)
                .setConnectTimeout(this.timeout)
                .setPingConnectionInterval(this.pingConnectionIntervalSize)
                .setConnectionMinimumIdleSize(this.connectionMinimumIdleSize)
                .setPassword(this.password);*/
        // 哨兵模式自动装配redisson
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                // 添加节点（哨兵模式）
                .addNodeAddress(this.clusterAddresses)
                // ping连接间隔
                .setPingConnectionInterval(this.pingConnectionIntervalSize)
                // 从服务器重连接间隔失败
                .setFailedSlaveReconnectionInterval(this.failedAttempts)
                // 重新尝试
                .setRetryAttempts(this.retryAttempts)
                // 重试时间间隔
                .setRetryInterval(this.retryInterval)
                // 超时时间
                .setTimeout(this.timeout)
                // 连接超时
                .setConnectTimeout(this.connecctTimeout)
                // 保活机制
                .setKeepAlive(false)
                // 空闲连接超时时间
                .setIdleConnectionTimeout(this.idleConnectionTimeout)
                // 连接最小空闲大小
                .setMasterConnectionMinimumIdleSize(this.connectionMinimumIdleSize)
                .setSlaveConnectionMinimumIdleSize(this.connectionMinimumIdleSize)
                // 连接池大小
                .setMasterConnectionPoolSize(this.masterConnectionPoolSize)
                .setSlaveConnectionPoolSize(this.slaveConnectionPoolSize)
                // 集群状态扫描间隔(毫秒)
                .setScanInterval(this.scanInterval);
        if (StringUtils.isNotBlank(this.password)) {
            clusterServersConfig.setPassword(this.password);
        }
        return Redisson.create(config);
    }

}
