package com.yygh.hospital.controller;

import com.yygh.common.result.Result;
import com.yygh.hospital.model.cmn.Dict;
import com.yygh.hospital.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: 陈玉婷
 * @create: 2021-07-26 15:07
 **/
@RestController
@RequestMapping("/admin/cmn/dict")
@Api(tags = "数据字典接口")
@CrossOrigin
public class DictController {
    @Autowired
    DictService dictService;

    // 通过id查询所属的子数据列表
    @GetMapping("/findChildData/{id}")
    @ApiOperation(value = "查询数据字典的子数据列表")
    public Result findDataById(@PathVariable Long id) {
        List<Dict> dictList = dictService.findData(id);
        return Result.ok(dictList);
    }

    // 导出数据字典的数据为excel格式
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws IOException {
        dictService.exportData(response);
    }

    // 导入excel的数据到数据库
    @PostMapping("/importData")
    public Result importData(MultipartFile file) {
        dictService.importData(file);
        return Result.ok();
    }
}
