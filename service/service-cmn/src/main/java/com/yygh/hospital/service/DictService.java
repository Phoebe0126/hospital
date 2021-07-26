package com.yygh.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yygh.hospital.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface DictService extends IService<Dict> {
    // 通过id查询所属的子数据列表
    List<Dict> findData(Long id);

    // 导出数据
    void exportData(HttpServletResponse response) throws IOException;

    // 导入数据
    void importData(MultipartFile file);
}
