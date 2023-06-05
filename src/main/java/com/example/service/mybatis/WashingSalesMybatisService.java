package com.example.service.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.Reserve;



@Service
public interface WashingSalesMybatisService {

    //해당업체의 일매출
    public List<Map<String, Object>> selectDaySales(String wid);

    //해당업체의 월매출
    public List<Map<String, Object>> selectMonthSales(String wid);

    //해당업체의 연매출
    public List<Map<String, Object>> selectYearSales(String wid);

    //월별 사용자 수
    public List<Map<String, Object>> selectUserCnt(String wid);

    //주별 사용자 수
    public List<Map<String, Object>> selectWeekUserCnt(String wid);


        /* == 예약내역 조회부분 == */


    //전체조회
    public List<Reserve> selectReserveAllList(String wid);

    //이용완료
    public List<Reserve> selectReserveStateUseComplete( String wid);

    //예약 완료
    public List<Reserve> selectReserveStateRevComplete(String wid);

    //예약 취소
    public List<Reserve> selectReserveStateRevCancle(String wid);


        /* == 기기 사용 률 == */
        

    public List<Map<String, Object>> selectMachineUseRate(String wid);

}
