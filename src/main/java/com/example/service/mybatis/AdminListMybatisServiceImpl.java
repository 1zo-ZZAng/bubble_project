package com.example.service.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.AdminChkList;
import com.example.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminListMybatisServiceImpl implements AdminListMybatisService {
    final AdminMapper aMapper;

    @Override
    public List<String> selectBoxList() {
       try {
        return aMapper.selectBoxList();
       } catch (Exception e) {
        e.printStackTrace();
        return null;
       }
    }

    @Override
    public List<AdminChkList> selectWlistUnchecked(String chk) {
        try {
            return aMapper.selectWlistUnchecked(chk);
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
        }

    @Override
    public int updateChk(String wnumber) {
        try {
            return aMapper.updateChk(wnumber);
           } catch (Exception e) {
            e.printStackTrace();
            return 0;
           }
    }

    
    
}
