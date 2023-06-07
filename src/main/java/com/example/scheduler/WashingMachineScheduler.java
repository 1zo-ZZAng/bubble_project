package com.example.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

// 고객이 세탁기 예약했던 시간이 지나면 자동 '이용 완료' 처리해줌
public class WashingMachineScheduler {
    
    final SchedulerMyBatisServie sMyBatisServie;

    @Scheduled(cron = "0 * * * * *") // => 이게 있어야 스케쥴링이 제대로 동작됨 (1분마다)
    public void printDate() throws ParseException{

        //서버시간
        Date now = new Date(); 

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //포맷용 -> 나오는 시간에 T가 섞여 나와서 포맷필요

        // 1. reserve 테이블 RVDATE, RVTIME 가져오기
        List<Reserve> list = sMyBatisServie.selectReserveListSch();

        //List로 나와서 for문 돌렸음
        for(Reserve obj : list ){

            String result = obj.getRvdate() + " " + obj.getRvtime(); //이용일 + 예약시간 => ex) 2023-06-07 10:30

            Date date = format.parse(result);   //string을 date타입으로
            // log.info("시작시간 => {}", format.format(date)); // 세탁기 이용 시작시간 확인용


            Calendar cal = Calendar.getInstance(); //시간 더하려고 불러온 클래스
            cal.setTime(date); // date에는 이용일 + 예약시간이 담겨있음
            cal.add(Calendar.MINUTE, Integer.parseInt(String.valueOf(obj.getMtime()))); // result + 기기 종류에 따른 기기 시간 (바로 +40, +80이 안되니깐 Calender.MINUTE사용해서 시간을 더 해줌)
            // log.info("종료시간=>{}", format.format(cal.getTime()));


            // log.info("포맷된 서버시간 => {}", format.format(now));  //시간 잘 나오는지 보려고 확인용
            // log.info(format.format(cal.getTime()));  // 기기별로 시간이 잘 더해졌는지 테스트


            // 2. if(RVDATE = 찍은거랑 같아(서버시간) && RVTIME = 찍은거랑 같아)
            if(format.format(cal.getTime()).equals(format.format(now))){

                // log.info("성공 => {}", 1);

                // 3. 상태 업데이트 끝 (1분마다 업데이트 됨)
                sMyBatisServie.updateReserveState(obj.getRvno());

            }

        }
    }
}


    

