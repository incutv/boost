package com.example.boost.product.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class FcmAsyncService {

    @Async("asyncExecutor")
    public CompletableFuture<Void> sendNotification(String targetToken, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(notification)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM 비동기 발송 성공: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("FCM 비동기 발송 실패: {}", e.getMessage(), e);
        }

        return CompletableFuture.completedFuture(null);
    }
}

