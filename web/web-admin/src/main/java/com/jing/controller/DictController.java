package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jing.entity.Dict;
import com.jing.result.Result;
import com.jing.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController {
    @Reference
    private DictService dictService;

    @GetMapping("/findZnodes")
    @ResponseBody
    public Result findByParentId(@RequestParam(value = "id",defaultValue = "0") Long id){ //第一次请求时候没有id值 默认为0,获取根节点

        List<Map<String, Object>> zNodes = dictService.findZnodes(id);

        return Result.ok(zNodes);
    }


    /**
     * 根据字典编码获取该字典编码下所有子节点的数据
     * @param dictCode
     * @return
     */
    @GetMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode){
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }

    @GetMapping("/findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId){
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }
}
