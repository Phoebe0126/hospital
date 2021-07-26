package com.yygh.hospital.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yygh.hospital.mapper.DictMapper;
import com.yygh.hospital.model.cmn.Dict;
import com.yygh.hospital.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * @author: 陈玉婷
 * @create: 2021-07-26 20:47
 **/
public class DictListener extends AnalysisEventListener<DictEeVo> {

    DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        // 一次处理一行
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
