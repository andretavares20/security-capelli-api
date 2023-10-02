package com.andretavares.testesecurity.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.entities.Ordem;
import com.andretavares.testesecurity.entities.OrdemLog;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.repositories.OrdemLogRepository;

@Service
public class OrdemLogService {
    
    @Autowired
    private OrdemLogRepository ordemLogRepository;

    public final static int DRAFT=0;
    public final static int PAYMENT=10;
    public final static int PACKING=20;
    public final static int DELIVERY=30;
    public final static int FINISH=40;
    public final static int CANCELED=90;

    public void createLog(Long userId,Ordem ordem, int type,String message){
        OrdemLog ordemLog = new OrdemLog();
        ordemLog.setLogMessage(message);
        ordemLog.setLogType(type);
        ordemLog.setOrdem(ordem);
        ordemLog.setUser(new User(userId));
        ordemLog.setTime(LocalDateTime.now());
        ordemLogRepository.save(ordemLog);
    }
}

