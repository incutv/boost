package com.example.boost.product.service;

import com.example.boost.product.repository.ProductRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.boost.product.entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@RequiredArgsConstructor
@Slf4j
public class FcmService {

    private final FcmAsyncService fcmAsyncService;
    private final ProductRepository productRepository;

    public void alarm() {
        List<Product> products = productRepository.findAll().subList(0,200);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);

        for (Product product : products) {
            CompletableFuture<Void> future = fcmAsyncService
                    .sendNotification("e9Ap2NGtx_Ezf0Lb7-XqOK:APA91bHfij6-wBMqDFOKD1e99IgUnds7U_z7XINBF52McNXF_CNeqMmj4YZ4GDgzdRFEHyDCg7QD9PKSeT5znyqOYTIbAD7AEi8zRfgvyqvNNmQCu7kGQAA", product.getName(), product.getDescription())
                    .thenRun(counter::incrementAndGet);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        log.info("총 sendNotification 호출 수: {}", counter.get());
    }

    public void sendNotification(String targetToken, String title, String body) {
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
            log.info("FCM 발송 성공: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("FCM 발송 실패: {}", e.getMessage());
        }
    }

    /*@Async
    public void sendMessageTo(String targetToken, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(targetToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            // sendAsync를 통해 비동기로 전송
            FirebaseMessaging.getInstance().sendAsync(message)
                    .addListener(() -> log.info("Successfully sent message to {}", targetToken), Runnable::run);

        } catch (Exception e) {
            log.error("Error sending message to {}: {}", targetToken, e.getMessage());
        }
    }*/
}
