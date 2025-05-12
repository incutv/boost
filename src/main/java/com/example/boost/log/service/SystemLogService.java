package com.example.boost.log.service;

import com.example.boost.log.entity.SystemLog;
import com.example.boost.log.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemLogService {

    private final SystemLogRepository systemLogRepository;

    public void saveLog(String level, String message) {
        SystemLog log = SystemLog.of(level, message);
        systemLogRepository.save(log);
    }
}

