package com.bpm.mrceprocess.external;

import com.bpm.mrceprocess.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import feign.Response;
import com.bpm.mrceprocess.common.dtos.TermAbbreviationDTO;





import java.util.List;
import java.util.Map;

@FeignClient(name = "document-service", url = "${app-feign.services.document}", path = "/document",
        configuration = FeignClientConfig.class)
public interface DocumentService {

    @PostMapping(value = "/parser/docx-table", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<Map<String, String>> docxTableParser(@RequestPart("file") MultipartFile file);

    @PostMapping(
            value = "/file/export/{templateCode}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    Response exportExcel(
            @RequestBody List<TermAbbreviationDTO> request,
            @PathVariable("templateCode") String templateCode
    );
}
