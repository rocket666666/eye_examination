package com.eyeexam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eyeexam.entity.SysConfig;

import java.util.List;

/**
 * 参数配置Service接口
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    SysConfig selectConfigById(Integer configId);
    
    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    String selectConfigByKey(String configKey);
    
    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    List<SysConfig> selectConfigList(SysConfig config);
    
    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    int insertConfig(SysConfig config);
    
    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    int updateConfig(SysConfig config);
    
    /**
     * 批量删除参数信息
     * 
     * @param configIds 需要删除的参数ID
     */
    void deleteConfigByIds(Integer[] configIds);
    
    /**
     * 加载参数缓存数据
     */
    void loadingConfigCache();
    
    /**
     * 清空参数缓存数据
     */
    void clearConfigCache();
    
    /**
     * 重置参数缓存数据
     */
    void resetConfigCache();
    
    /**
     * 校验参数键名是否唯一
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    String checkConfigKeyUnique(SysConfig config);
} 