package com.example.service.jpa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Reserve;
import com.example.repository.ReserveRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService {

    final ReserveRepository rRepository;


    //예약내역 조회
    @Override
    public List<Reserve> selectReserve(String wname) {
        try {
            
            return rRepository.findByWnameOrderByRvnoDesc(wname);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    
}
