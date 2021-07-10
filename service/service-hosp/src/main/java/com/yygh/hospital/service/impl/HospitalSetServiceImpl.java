package com.yygh.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yygh.hospital.mapper.HospitalSetMapper;
import com.yygh.hospital.model.hosp.HospitalSet;
import com.yygh.hospital.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 陈玉婷
 * @create: 2021-07-10 14:39
 **/

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {
    @Autowired
    private HospitalSetMapper hospitalSetMapper;
}
