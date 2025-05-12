importScripts('https://www.gstatic.com/firebasejs/11.6.1/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/11.6.1/firebase-messaging-compat.js');

firebase.initializeApp({
    apiKey: "",
    authDomain: "boost-10b95.firebaseapp.com",
    projectId: "boost-10b95",
    messagingSenderId: "395235624993",
    appId: "1:395235624993:web:4e37500651417e0508bb60"
});

const messaging = firebase.messaging();

// 백그라운드 메시지 수신 핸들러
messaging.onBackgroundMessage(function(payload) {
    console.log('[firebase-messaging-sw.js] 백그라운드 메시지 수신:', payload);
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
        body: payload.notification.body,
        icon: '/icon.png' // 아이콘 이미지가 있다면 설정
    };

    self.registration.showNotification(notificationTitle, notificationOptions);
});
