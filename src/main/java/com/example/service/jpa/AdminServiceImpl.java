package com.example.service.jpa;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dto.MachineCount;
import com.example.dto.Reserve;
import com.example.dto.Washing;
import com.example.dto.WashingMachine;
import com.example.entity.Admin;
import com.example.entity.Customer;
import com.example.entity.WashingCheck;
import com.example.entity.Washingmachine;
import com.example.mapper.AdminMapper;
import com.example.repository.AdminRepository;
import com.example.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    
    final AdminRepository aRepository;
    final AdminMapper aMapper;


    //--------------관리자홈----------------
    @Override
    public List<Reserve> selectRvdateList() {
        try {
            return aMapper.selectRvdateList();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reserve> selectRdateList() {
        try {
            return aMapper.selectRdateList();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public int washingCount() {
        try {
            return aMapper.washingCount();
           } catch (Exception e) {
            e.printStackTrace();
            return 0;
           }
    }



    

    @Override
    public List<Washing> selectWList() {
        try {
            return aMapper.selectWList();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<WashingMachine> selectWmList(String wnumber) {
       try {
            return aMapper.selectWmList(wnumber);
            
       } catch (Exception e) {
        e.printStackTrace();
        return null;
       }
    }


    @Override
    public List<MachineCount> selectMCount(String wnumber) {
        try {
            return aMapper.selectMCount(wnumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String selectWashingNameOne(String wnumber) {
       try {
        return aMapper.selectWashingNameOne(wnumber);
       } catch (Exception e) {
        e.printStackTrace();
        return null;
       }
    }

    @Override
    public List<WashingCheck> selctChkList() {
        try {
            return aMapper.selctChkList();
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }



    @Override
    public int todayRVWashingCount(String wnumber) {
        try {
            return aMapper.todayRVWashingCount(wnumber);
           } catch (Exception e) {
            e.printStackTrace();
            return 0;
           }
    }

    @Override
    public List<Customer> selectCustomer() {
        try {
            return aMapper.selectCustomer();
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }

    }

    @Override
    public int thisMonthRVCount() {
        try {
            return aMapper.thisMonthRVCount();
           } catch (Exception e) {
            e.printStackTrace();
            return 0;
           }
    }


//---------------------------차트------------------------------


    @Override
    public List<Reserve> selectMonthBox() {
        try {
            return aMapper.selectMonthBox();
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }

    @Override
    public List<Map<String, Object>> selectMonthAllSales() {
        try {
            return aMapper.selectMonthAllSales();
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }

    @Override
    public List<Map<String, Object>> selectMonthChart(String selectdate) {
        try {
            return aMapper.selectMonthChart(selectdate);
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }

    @Override
    public int todayRVCount() {
        try {
            return aMapper.todayRVCount();
           } catch (Exception e) {
            e.printStackTrace();
            return 0;
           }
    }



    @Override
    public List<Map<String, Object>> selectMonthDateWashingChart(String wnumber) {
        try {
            return aMapper.selectMonthDateWashingChart(wnumber);
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
        }

    @Override
    public List<Map<String, Object>> selectMonthWashingChart(String wnumber) {
        try {
            return aMapper.selectMonthWashingChart(wnumber);
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
        }

        
    @Override
    public List<String> selectMonthBoxList(String wnumber) {
        try {
            return aMapper.selectMonthBoxList(wnumber);
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }

    @Override
    public List<Washing> selectAllWasingList() {
        try {
            return aMapper.selectAllWasingList();
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }

    @Override
    public int MonthRvOkState() {
        try {
            return aMapper.MonthRvOkState();
           } catch (Exception e) {
            e.printStackTrace();
            return 0;
           }
    }

    @Override
    public int MonthRvCancelState() {
        try {
            return aMapper.MonthRvCancelState();
           } catch (Exception e) {
            e.printStackTrace();
            return 0;
           }
    }

    @Override
    public int MonthUseOkState() {
        try {
            return aMapper.MonthUseOkState();
           } catch (Exception e) {
            e.printStackTrace();
            return 0;
           }
    }

    @Override
    public String Top1Washing() {
        try {
            return aMapper.Top1Washing();
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }

    @Override
    public String Top1MachineType() {
        try {
            return aMapper.Top1MachineType();
           } catch (Exception e) {
            e.printStackTrace();
            return null;
           }
    }








        
}




