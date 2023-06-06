package com.example.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.dto.Reserve;
import com.example.service.mybatis.SchedulerMyBatisServie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component //기능적으로 특별한게 없으면 component넣을 것
@Slf4j
@RequiredArgsConstructor

public class WashingMachineScheduler {
    
    final SchedulerMyBatisServie sMyBatisServie;

    @Scheduled(cron = "*/5 * * * * *") // =>이게 있어야 스케쥴링이 제대로 동작됨
    public void printDate(){


        LocalDateTime now = LocalDateTime.now();

        
        

        String year = now.toString().substring(0, 10);
        String time = now.toString().substring(11, 16);

        log.info("{}" , year); //연도
        log.info("{}" , time); // 시분


        // 1. reserve 테이블의 RVDATE, RVTIME 이걸 가져와
        List<Reserve> list = sMyBatisServie.selectReserveListSch();

        //for문쓰자
        for(Reserve obj : list ){

            // 2. if(RVDATE = 찍은거랑 같아 && RVTIME = 찍은거랑 같아)

            if(obj.getRvdate().equals(year) && obj.getRvtime().equals(time)){

                log.info("떠라! => {}", 1);

                // 3. 상태 업데이트 끝

                

            }

        }





        
        







        
        
    }


    
}
