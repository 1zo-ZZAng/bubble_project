package com.example.service.jpa;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Machine;
import com.example.repository.MachineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MachineServiceImpl implements MachineService {

    final MachineRepository mRepository;

    //일괄삭제용
    // @Override
    // public Machine deletMachineAll(List<BigInteger> chk) {
    //     try {


    //         mRepository.deleteAllById(chk);

            
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
    
}
