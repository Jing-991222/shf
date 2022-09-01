package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jing.base.BaseController;
import com.jing.entity.Dict;
import com.jing.result.Result;
import com.jing.service.DictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {
    @Reference
    private DictService dictService;


    /**
     * 通过父id获取所有的子集元素  二级标签联动  东城区下的所有地方等
     * @param parentId
     * @return
     */
    @GetMapping("/findListByParentId/{parentId}")
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId){
        List<Dict> dictList = dictService.findListByParentId(parentId);
        return Result.ok(dictList);
    }

    /**
     * 根据字典编码获取所有的字典信息  户型  楼层 建筑结构等
     * @param dictCode
     * @return
     */
    @GetMapping("/findListByDictCode/{dictCode}")
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode){
        List<Dict> dictList = dictService.findListByDictCode(dictCode);
        return Result.ok(dictList);
    }

}
