package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.EffectiveType;
import com.bpm.mrceprocess.common.enums.ProcessScopeEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class UpdateProcessRequestDTO {

    public record Request(Information information,
                          Diagram diagram,
                          List<TermAbbreviation> termAbbreviations,
                          RelatedDocument relatedDocument) {

        public record Information(String id,
                                  String historyId,
                                  String code,
                                  String shortDescription,
                                  String name,
                                  String objectId,
                                  ProcessScopeEnum scope,
                                  String category,
                                  EffectiveType effectiveType,
                                  LocalDateTime effectiveDate,
                                  LocalDateTime endDate,
                                  Integer duration,
                                  List<OriginalDocument> originalDocuments) {
            public record OriginalDocument(String id,
                                           String code,
                                           String subject,
                                           String status,
                                           String type,
                                           LocalDateTime issuedDate,
                                           String approvalAuthority,
                                           boolean effectivenessSame,
                                           EffectiveType effectiveType,
                                           Integer effectiveDuration,
                                           LocalDateTime effectiveDate,
                                           LocalDateTime endDate,
                                           String shareFor,
                                           String documentLink,
                                           String attachmentId,
                                           Set<String> specificUsers,
                                           Set<String> specificUnits,
                                           Set<String> relevantUnits) {
            }
        }

        public record Diagram(String diagramId,
                              List<Description> descriptions) {
            public record Description(String id,
                                      String step,
                                      String description,
                                      String responsibility,
                                      String sla,
                                      String referenceDoc) {
            }
        }

        public record TermAbbreviation(String id,
                                       String vietnamese,
                                       String english) {
        }

        public record RelatedDocument(String id,
                                      List<String> legalProvisions,
                                      List<String> internalDocuments,
                                      List<String> relatedProcesses,
                                      List<Template> templates) {
            public record Template(String id,
                                   String name,
                                   String fileId) {
            }
        }
    }

    public record Response(String generalId, String historyId) {
    }
}
