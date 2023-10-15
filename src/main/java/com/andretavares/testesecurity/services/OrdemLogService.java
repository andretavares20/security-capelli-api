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

    public final static int RASCUNHO=0;
    public final static int PAGAMENTO=10;
    public final static int EMBALAGEM=20;
    public final static int ENTREGA=30;
    public final static int FINALIZADO=40;
    public final static int CANCELADO=90;

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

