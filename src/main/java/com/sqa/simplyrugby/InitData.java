package com.sqa.simplyrugby;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // 暂时空着，不执行任何代码
        System.out.println("✅ 初始化数据已跳过");
    }
}