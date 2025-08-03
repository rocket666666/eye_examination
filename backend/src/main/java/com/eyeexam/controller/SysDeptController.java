package com.eyeexam.controller;

import com.eyeexam.common.Result;
import com.eyeexam.entity.SysDept;
import com.eyeexam.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息Controller
 */
@RestController
@RequestMapping("/api/system/dept")
public class SysDeptController {
    
    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @GetMapping("/list")
    public Result list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return Result.success(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping(value = "/{deptId}")
    public Result getInfo(@PathVariable Long deptId) {
        deptService.checkDeptDataScope(deptId);
        return Result.success(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @PostMapping
    public Result add(@RequestBody SysDept dept) {
        if (!deptService.checkDeptNameUnique(dept).equals("0")) {
            return Result.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        return Result.success(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @PutMapping
    public Result edit(@RequestBody SysDept dept) {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (!deptService.checkDeptNameUnique(dept).equals("0")) {
            return Result.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(deptId)) {
            return Result.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return Result.error("该部门包含未停用的子部门！");
        }
        return Result.success(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    public Result remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return Result.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return Result.error("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return Result.success(deptService.deleteDeptById(deptId));
    }
} 