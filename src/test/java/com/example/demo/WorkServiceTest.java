package com.example.demo;

import com.example.demo.redistest.WorkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class WorkServiceTest {
    @Autowired
    private WorkService workService;
    @Test
    void test() {
    }
    @Test
    void work() throws Exception {
        CountDownLatch downLatch = new CountDownLatch(10);
        LinkedList<Thread> threads = new LinkedList<>();
        for (int i = 0; i < 10; ++i) {
            Thread thread = new Thread(() -> {
                // 加锁测试
//                workService.work();
                workService.workb();
                // 未加锁测试
//                 workService.notLockWork();
                downLatch.countDown();
            });
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        downLatch.await();
        System.out.println("count = " + WorkService.count);
    }
}
