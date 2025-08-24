package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.EffectiveType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SaveProcessRequest", description = "Payload to save a process, either as a new draft or updating an existing one.")
public class SaveProcessRequestDTO {

    @Schema(description = "General information about the process type.")
    private General general;
    @Schema(description = "Detailed information about this specific version of the process.")
    private Information information;
    @Schema(description = "BPMN diagram details.")
    private Diagram diagram;
    @Schema(description = "List of terms and abbreviations used in the process.")
    private List<TermAbbreviation> termAbbreviations;
    @Schema(description = "Links to related documents and templates.")
    private RelatedDocument relatedDocument;

    @Schema(description = "General information about the process type.")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class General {
        @Schema(description = "The unique code for the general process.", example = "PROC-001")
        private String code;
    }

    @Schema(description = "Detailed information about this specific version of the process.")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Information {
        @Schema(description = "The ID of the GeneralInformationHistory to update. Leave null for new drafts.", example = "c7a7a7e7-7e7e-3f3f-7e7e-7e7e7e7e7e7e")
        private String id;
        @Schema(description = "A specific code for this version.", example = "PROC-001-V1")
        private String code;
        @Schema(description = "A short summary of the process.", example = "Handles new employee onboarding.")
        private String shortDescription;
        @Schema(description = "The full name of the process.", example = "New Employee Onboarding Process")
        private String name;
        @Schema(description = "ID of the object this process applies to.", example = "OBJ-HR")
        private String objectId;
        @Schema(description = "The category of the process (ID of a Category).", example = "CAT-HR")
        private String category;
        @Schema(description = "The workflow of the process (ID of a workflow Config).", example = "CAT-HR")
        private String workflow;
        @Schema(description = "The rule for when the process becomes effective.")
        private EffectiveType effectiveType;
        @Schema(description = "The specific date the process becomes effective (if effectiveType is SPECIFIC).", example = "2025-01-01T10:00:00")
        private LocalDateTime effectiveDate;
        @Schema(description = "The date the process is no longer effective.", example = "2026-01-01T10:00:00")
        private LocalDateTime endDate;
        @Schema(description = "The duration in months/days for the process effectiveness.", example = "12")
        private Integer duration;
        @Schema(description = "List of original legal or source documents.")
        private List<OriginalDocument> originalDocuments;

        @Schema(description = "Represents an original source document related to the process.")
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OriginalDocument {
            @Schema(description = "ID of the document to update. Null for new.", example = "doc-001")
            private String id;
            @Schema(description = "Document code.", example = "LAW-2024-12")
            private String code;
            @Schema(description = "The subject or title of the document.", example = "Labor Law 2024")
            private String subject;
            @Schema(description = "Current status of the document.", example = "ACTIVE")
            private String status;
            @Schema(description = "Type of the document.", example = "LEGAL")
            private String type;
            @Schema(description = "Date the document was issued.", example = "2024-06-15T00:00:00")
            private LocalDateTime issuedDate;
            @Schema(description = "The authority that approved the document.", example = "National Assembly")
            private String approvalAuthority;
            @Schema(description = "Whether the document's effectiveness is the same as the process'.")
            private boolean effectivenessSame;
            @Schema(description = "The rule for when the document becomes effective.")
            private EffectiveType effectiveType;
            @Schema(description = "Duration of effectiveness.", example = "24")
            private Integer duration;
            @Schema(description = "Specific effective date.", example = "2024-07-01T00:00:00")
            private LocalDateTime effectiveDate;
            @Schema(description = "Specific end date.", example = "2026-07-01T00:00:00")
            private LocalDateTime endDate;
            @Schema(description = "Who this document is shared with.", example = "ALL_EMPLOYEES")
            private String shareFor;
            @Schema(description = "A direct link to the document.", example = "https://example.com/docs/law-2024-12.pdf")
            private String documentLink;
            @Schema(description = "ID of the attachment in a file storage system.", example = "file-xyz-123")
            private String attachmentId;
            @Schema(description = "Set of specific user IDs who can access this.", example = "[\"user1\", \"user2\"]")
            private Set<String> specificUsers;
            @Schema(description = "Set of specific unit/department IDs that can access this.", example = "[\"HR_DEPT\", \"LEGAL_DEPT\"]")
            private Set<String> specificUnits;
            @Schema(description = "Set of relevant unit/department IDs.", example = "[\"FINANCE_DEPT\"]")
            private Set<String> relevantUnits;
        }
    }

    @Schema(description = "BPMN diagram details.")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Diagram {
        @Schema(description = "The ID of the diagram in the BPMN engine (e.g., Flowable).", example = "proc-def-id:1:12345")
        private String diagramId;
        @Schema(description = "List of descriptions for each step in the diagram.")
        private List<Description> descriptions;

        @Schema(description = "Description for a specific step in the BPMN diagram.")
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Description {
            @Schema(description = "ID of the description to update. Null for new.", example = "desc-001")
            private String id;
            @Schema(description = "The step number or ID.", example = "1.1")
            private String step;
            @Schema(description = "Detailed description of the step's activities.")
            private String description;
            @Schema(description = "The person or role responsible for this step.", example = "HR Manager")
            private String responsibility;
            @Schema(description = "Service Level Agreement for this step.", example = "24 hours")
            private String sla;
            @Schema(description = "Reference to another document or step.", example = "See section 2.3 of HR-Policy")
            private String referenceDoc;
        }
    }

    @Schema(description = "A term or abbreviation definition.")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TermAbbreviation {
        @Schema(description = "ID of the term to update. Null for new.", example = "term-001")
        private String id;
        @Schema(description = "The definition in Vietnamese.", example = "Nhân viên")
        private String vietnamese;
        @Schema(description = "The definition in English.", example = "Employee")
        private String english;
    }

    @Schema(description = "Links to related documents and templates.")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RelatedDocument {
        @Schema(description = "List of related legal provisions.", example = "[\"Law-123\", \"Decree-456\"]")
        private List<String> legalProvisions;
        @Schema(description = "List of related internal company documents.", example = "[\"HR-Policy-01\", \"IT-Security-Guide\"]")
        private List<String> internalDocuments;
        @Schema(description = "List of other related processes.", example = "[\"PROC-002\", \"PROC-003\"]")
        private List<String> relatedProcesses;
        @Schema(description = "List of templates used in this process.")
        private List<Template> templates;

        @Schema(description = "A template file used in the process.")
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Template {
            @Schema(description = "ID of the template to update. Null for new.", example = "tpl-001")
            private String id;
            @Schema(description = "Name of the template.", example = "Offer Letter Template")
            private String name;
            @Schema(description = "ID of the template file in a file storage system.", example = "file-abc-456")
            private String fileId;
        }
    }
}
