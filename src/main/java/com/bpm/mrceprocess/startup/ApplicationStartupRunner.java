package com.bpm.mrceprocess.startup;

import com.bpm.mrceprocess.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationStartupRunner implements ApplicationRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("========================================================");
        log.info("mrcEProcess service is starting up...");
        log.info("Executing startup logic...");

        // TODO: Thêm logic khởi tạo của bạn ở đây.
        // Ví dụ: Tải dữ liệu cấu hình, khởi tạo cache, kiểm tra kết nối,...

        // Ví dụ: Kiểm tra và log số lượng category có trong database
        long categoryCount = categoryRepository.count();
        log.info("Successfully connected to the database. Found {} categories.", categoryCount);

        log.info("Startup logic finished.");
        log.info("========================================================");
    }
}