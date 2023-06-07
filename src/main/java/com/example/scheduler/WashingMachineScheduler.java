package com.example.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
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

    @Scheduled(cron = "0 * * * * *") // => 이게 있어야 스케쥴링이 제대로 동작됨 (1분마다)
    public void printDate() throws ParseException{

        //서버시간
        Date now = new Date(); 

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //포맷용

        // 1. reserve 테이블의 RVDATE, RVTIME 이걸 가져와
        List<Reserve> list = sMyBatisServie.selectReserveListSch();

        //List로 나와서 for문 돌렸음
        for(Reserve obj : list ){

            String result = obj.getRvdate() + " " + obj.getRvtime(); //이용일 + 예약시간 

            Date date = format.parse(result);   //string을 date타입으로 
            // log.info("시작시간 => {}", format.format(date));


            Calendar cal = Calendar.getInstance(); //시간 더하려고 불러온 class
            cal.setTime(date); //
            cal.add(Calendar.MINUTE, Integer.parseInt(String.valueOf(obj.getMtime()))); //위의 result에 기기 종류에 따른 시간 추가
            // log.info("종료시간=>{}", format.format(cal.getTime()));


            // log.info("에헤이 => {}", format.format(now));    //시간 잘 나오는지 보려고 확인용
            // log.info(format.format(cal.getTime()));


            // 2. if(RVDATE = 찍은거랑 같아(서버시간) && RVTIME = 찍은거랑 같아)
            if(format.format(cal.getTime()).equals(format.format(now))){

                // log.info("성공 => {}", 1);

                // 3. 상태 업데이트 끝 (1분마다 업데이트 됨)
                sMyBatisServie.updateReserveState(obj.getRvno());

            }

        }
    }
}


    

