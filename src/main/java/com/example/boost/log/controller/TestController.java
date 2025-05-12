package com.example.boost.log.controller;

import com.example.boost.log.service.AsyncSystemLogService;
import com.example.boost.log.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final SystemLogService syncLogService;
    private final AsyncSystemLogService asyncLogService;

    @GetMapping("/log/sync")
    public String syncLog() {
        syncLogService.saveLog("INFO", "동기 로그입니다.");
        return "Sync Log Saved";
    }

    @GetMapping("/log/async")
    public String asyncLog() {
        asyncLogService.saveLogAsync("INFO", "비동기 로그입니다.");
        return "Async Log Queued";
    }
}

