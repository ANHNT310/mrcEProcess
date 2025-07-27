package com.bpm.mrceprocess.external;

import com.bpm.mrceprocess.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@FeignClient(name = "document-service", url = "${app-feign.services.document}", path = "/document",
        configuration = FeignClientConfig.class)
public interface DocumentService {

    @PostMapping(value = "/parser/docx-table", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<Map<String, String>> docxTableParser(@RequestPart("file") MultipartFile file);
}
