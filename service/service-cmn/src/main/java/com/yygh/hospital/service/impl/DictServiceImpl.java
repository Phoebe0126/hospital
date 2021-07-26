package com.yygh.hospital.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yygh.hospital.listener.DictListener;
import com.yygh.hospital.mapper.DictMapper;
import com.yygh.hospital.model.cmn.Dict;
import com.yygh.hospital.service.DictService;
import com.yygh.hospital.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 陈玉婷
 * @create: 2021-07-26 15:05
 **/
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Autowired
    DictMapper dictMapper;

    @Override
    // 通过id查询所属的子数据列表
    public List<Dict> findData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Dict> dicts = dictMapper.selectList(queryWrapper);
        for (Dict dict : dicts) {
            boolean flag = hasChilden(dict.getId());
            dict.setHasChildren(flag);
        }
        return dicts;
    }

    @Override
    public void exportData(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("数据字典", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

        // 查询数据库的数据字典数据，需要做映射
        List<Dict> dicts = dictMapper.selectList(null);
        List<DictEeVo> dictEeVoList = dicts.stream().map(dict -> {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict, dictEeVo);
            return dictEeVo;
        }).collect(Collectors.toList());

        // 写入到excel中
        EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典")
        .doWrite(dictEeVoList);
    }

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(dictMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 判断某个接口是否存在子数据
    private boolean hasChilden(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Integer count = dictMapper.selectCount(queryWrapper);
        return count > 0;
    }
}
