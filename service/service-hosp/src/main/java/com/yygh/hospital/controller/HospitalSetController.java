package com.yygh.hospital.controller;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yygh.common.config.MD5;
import com.yygh.common.result.Result;
import com.yygh.hospital.model.hosp.HospitalSet;
import com.yygh.hospital.service.HospitalSetService;
import com.yygh.hospital.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @author: 陈玉婷
 * @create: 2021-07-10 14:42
 **/
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@Api(tags = "医院设置管理")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有医院设置信息")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    @DeleteMapping("{id}")
    public Result removeById(@PathVariable long id) {
        boolean flag = hospitalSetService.removeById(id);
        return flag ? Result.ok() : Result.fail();
    }

    @ApiOperation(value = "依据条件分页医院设置信息")
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable long current, @PathVariable long limit,
                             @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> page = new Page<>(current, limit);
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }
        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())) {
            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        Page<HospitalSet> hospitalSet = hospitalSetService.page(page, wrapper);
        return Result.ok(hospitalSet);
    }

    @ApiOperation(value = "添加一条医院设置")
    @PostMapping("add")
    public Result addOneHospitalSet(@RequestBody HospitalSet hospitalSet) {
        // 设置医院设置的状态为1(可用)
        hospitalSet.setStatus(1);
        // 生成签名密钥
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + new Random(1000).nextInt()));
        boolean flag = hospitalSetService.save(hospitalSet);
        return flag ? Result.ok() : Result.fail();

    }
    @GetMapping("findById/{id}")
    public Result findById(@PathVariable long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }
    @PostMapping("update")
    public Result update(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        return flag ? Result.ok() : Result.fail();
    }
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Integer> idList) {
        boolean flag = hospitalSetService.removeByIds(idList);
        return flag ? Result.ok() : Result.fail();
    }
}
