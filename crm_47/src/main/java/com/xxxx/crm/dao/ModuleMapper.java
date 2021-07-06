package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.dto.TreeDto;
import com.xxxx.crm.vo.Module;

import java.util.List;
import java.util.Map;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    /**
     * todo 查询所有模块已tree形式展示
     * @return
     */
    List<TreeDto> queryAllModules();

    List<Module> queryModules();

    Module queryModuleByGradeAndModuleName(Integer grade, String moduleName);

    Module queryModuleByGradeAndUrl(Integer grade, String url);

    Module queryModuleByOptValue(String optValue);

    List<Map<String, Object>> queryAllModulesByGrade(Integer grade);

    int countSubModuleByParentId(Integer mid);
}