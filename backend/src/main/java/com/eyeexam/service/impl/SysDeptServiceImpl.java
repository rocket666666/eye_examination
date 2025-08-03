package com.eyeexam.service.impl;

import com.eyeexam.entity.SysDept;
import com.eyeexam.mapper.SysDeptMapper;
import com.eyeexam.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门管理Service业务层处理
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> deptTrees = new java.util.ArrayList<>();
        for (SysDept dept : depts) {
            if (dept.getParentId() == 0) {
                recursionFn(depts, dept);
                deptTrees.add(dept);
            }
        }
        if (deptTrees.isEmpty()) {
            deptTrees = depts;
        }
        return deptTrees;
    }

    @Override
    public List<SysDept> buildDeptTreeSelect(List<SysDept> depts) {
        return buildDeptTree(depts);
    }

    @Override
    public SysDept selectDeptById(Long deptId) {
        return deptMapper.selectById(deptId);
    }

    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0;
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    @Override
    public String checkDeptNameUnique(SysDept dept) {
        Long deptId = dept.getDeptId() == null ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (info != null && info.getDeptId().longValue() != deptId.longValue()) {
            return "1";
        }
        return "0";
    }

    @Override
    public void checkDeptDataScope(Long deptId) {
        // 数据权限校验逻辑，暂时为空
    }

    @Override
    public int insertDept(SysDept dept) {
        if (dept.getParentId() != null && dept.getParentId() != 0) {
            SysDept info = deptMapper.selectById(dept.getParentId());
            if (info != null) {
                dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
            }
        } else {
            dept.setAncestors("0");
        }
        return deptMapper.insert(dept);
    }

    @Override
    public int updateDept(SysDept dept) {
        return deptMapper.updateById(dept);
    }

    @Override
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteById(deptId);
    }

    /**
     * 递归列表
     */
    public void recursionFn(List<SysDept> list, SysDept t) {
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        return list.stream()
                .filter(n -> n.getParentId().longValue() == t.getDeptId().longValue())
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t) {
        return getChildList(list, t).size() > 0;
    }
} 