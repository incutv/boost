package com.example.boost.log.service;

import com.example.boost.log.entity.SystemLog;
import com.example.boost.log.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncSystemLogService {

    private final SystemLogRepository systemLogRepository;

    @Async("asyncExecutor")
    public void saveLogAsync(String level, String message) {
        SystemLog log = SystemLog.of(level, message);
        systemLogRepository.save(log);
    }
}

