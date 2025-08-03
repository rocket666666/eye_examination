package com.eyeexam.controller;

import com.eyeexam.common.Result;
import com.eyeexam.entity.ExamItem;
import com.eyeexam.service.IExamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查项目Controller
 */
@RestController
@RequestMapping("/api/exam/item")
public class ExamItemController {
    
    @Autowired
    private IExamItemService examItemService;

    /**
     * 查询检查项目列表
     */
    @GetMapping("/list")
    public Result list(ExamItem examItem) {
        List<ExamItem> list = examItemService.selectExamItemList(examItem);
        return Result.success(list);
    }

    /**
     * 获取检查项目详细信息
     */
    @GetMapping(value = "/{itemId}")
    public Result getInfo(@PathVariable("itemId") Long itemId) {
        return Result.success(examItemService.selectExamItemById(itemId));
    }

    /**
     * 新增检查项目
     */
    @PostMapping
    public Result add(@RequestBody ExamItem examItem) {
        return Result.success(examItemService.insertExamItem(examItem));
    }

    /**
     * 修改检查项目
     */
    @PutMapping
    public Result edit(@RequestBody ExamItem examItem) {
        return Result.success(examItemService.updateExamItem(examItem));
    }

    /**
     * 删除检查项目
     */
    @DeleteMapping("/{itemIds}")
    public Result remove(@PathVariable Long[] itemIds) {
        return Result.success(examItemService.deleteExamItemByIds(itemIds));
    }
} 