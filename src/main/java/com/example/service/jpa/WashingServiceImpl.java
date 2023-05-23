package com.example.service.jpa;

import org.springframework.stereotype.Service;

import com.example.entity.Washing;
import com.example.repository.WashingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WashingServiceImpl implements WashingService {
    
    final WashingRepository wRepository;


    //아이디 중복 확인
    @Override
    public int washingIDCheck(String id) {
        try {

            int ret = wRepository.countById(id);

            if(ret == 0){
                return 0;
            }else{
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }



    //비밀번호 찾기
    @Override
    public Washing findWashingPw(Washing washing) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findWashingPw'");
    }


    //
    @Override
    public Washing findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }


    //회원가입
    @Override
    public int joinWashing(Washing obj) {
        try {

            wRepository.save(obj);
            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //로그인
    @Override
    public int loginWashing(Washing obj) {
        try {

            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //탈퇴
    @Override
    public int deleteWashing(Washing obj) {
        try {

            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //정보수정
    @Override
    public int updateWashing(Washing obj) {
        try {

            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    //비밀번호 수정
    @Override
    public int pwupdateWashing(Washing obj) {
        try {

            return 1;
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }









    
    
    
}
