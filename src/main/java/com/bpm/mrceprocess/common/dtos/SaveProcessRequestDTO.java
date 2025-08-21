package com.bpm.mrceprocess.common.dtos;

import com.bpm.mrceprocess.common.enums.EffectiveType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class SaveProcessRequestDTO {

    @Schema(name = "SaveProcessRequest", description = "Payload to save a process, either as a new draft or updating an existing one.")
    public record Request(
            @Schema(description = "General information about the process type.")
            General general,
            @Schema(description = "Detailed information about this specific version of the process.")
            Information information,
            @Schema(description = "BPMN diagram details.")
            Diagram diagram,
            @Schema(description = "List of terms and abbreviations used in the process.")
            List<TermAbbreviation> termAbbreviations,
            @Schema(description = "Links to related documents and templates.")
            RelatedDocument relatedDocument) {

        @Schema(description = "General information about the process type.")
        public record General(
                @Schema(description = "The unique code for the general process.", example = "PROC-001")
                String code) {
        }

        @Schema(description = "Detailed information about this specific version of the process.")
        public record Information(
                @Schema(description = "The ID of the GeneralInformationHistory to update. Leave null for new drafts.", example = "c7a7a7e7-7e7e-3f3f-7e7e-7e7e7e7e7e7e")
                String id,
                @Schema(description = "A specific code for this version.", example = "PROC-001-V1")
                String code,
                @Schema(description = "A short summary of the process.", example = "Handles new employee onboarding.")
                String shortDescription,
                @Schema(description = "The full name of the process.", example = "New Employee Onboarding Process")
                String name,
                @Schema(description = "ID of the object this process applies to.", example = "OBJ-HR")
                String objectId,
                @Schema(description = "The category of the process (ID of a Category).", example = "CAT-HR")
                String category,
                @Schema(description = "The workflow of the process (ID of a workflow Config).", example = "CAT-HR")
                String workflow,
                @Schema(description = "The rule for when the process becomes effective.")
                EffectiveType effectiveType,
                @Schema(description = "The specific date the process becomes effective (if effectiveType is SPECIFIC).", example = "2025-01-01T10:00:00")
                LocalDateTime effectiveDate,
                @Schema(description = "The date the process is no longer effective.", example = "2026-01-01T10:00:00")
                LocalDateTime endDate,
                @Schema(description = "The duration in months/days for the process effectiveness.", example = "12")
                Integer duration,
                @Schema(description = "List of original legal or source documents.")
                List<OriginalDocument> originalDocuments) {
            @Schema(description = "Represents an original source document related to the process.")
            public record OriginalDocument(
                    @Schema(description = "ID of the document to update. Null for new.", example = "doc-001")
                    String id,
                    @Schema(description = "Document code.", example = "LAW-2024-12")
                    String code,
                    @Schema(description = "The subject or title of the document.", example = "Labor Law 2024")
                    String subject,
                    @Schema(description = "Current status of the document.", example = "ACTIVE")
                    String status,
                    @Schema(description = "Type of the document.", example = "LEGAL")
                    String type,
                    @Schema(description = "Date the document was issued.", example = "2024-06-15T00:00:00")
                    LocalDateTime issuedDate,
                    @Schema(description = "The authority that approved the document.", example = "National Assembly")
                    String approvalAuthority,
                    @Schema(description = "Whether the document's effectiveness is the same as the process'.")
                    boolean effectivenessSame,
                    @Schema(description = "The rule for when the document becomes effective.")
                    EffectiveType effectiveType,
                    @Schema(description = "Duration of effectiveness.", example = "24")
                    Integer duration,
                    @Schema(description = "Specific effective date.", example = "2024-07-01T00:00:00")
                    LocalDateTime effectiveDate,
                    @Schema(description = "Specific end date.", example = "2026-07-01T00:00:00")
                    LocalDateTime endDate,
                    @Schema(description = "Who this document is shared with.", example = "ALL_EMPLOYEES")
                    String shareFor,
                    @Schema(description = "A direct link to the document.", example = "https://example.com/docs/law-2024-12.pdf")
                    String documentLink,
                    @Schema(description = "ID of the attachment in a file storage system.", example = "file-xyz-123")
                    String attachmentId,
                    @Schema(description = "Set of specific user IDs who can access this.", example = "[\"user1\", \"user2\"]")
                    Set<String> specificUsers,
                    @Schema(description = "Set of specific unit/department IDs that can access this.", example = "[\"HR_DEPT\", \"LEGAL_DEPT\"]")
                    Set<String> specificUnits,
                    @Schema(description = "Set of relevant unit/department IDs.", example = "[\"FINANCE_DEPT\"]")
                    Set<String> relevantUnits) {
            }
        }

        @Schema(description = "BPMN diagram details.")
        public record Diagram(
                @Schema(description = "The ID of the diagram in the BPMN engine (e.g., Flowable).", example = "proc-def-id:1:12345")
                String diagramId,
                @Schema(description = "List of descriptions for each step in the diagram.")
                List<Description> descriptions) {

            @Schema(description = "Description for a specific step in the BPMN diagram.")
            public record Description(
                    @Schema(description = "ID of the description to update. Null for new.", example = "desc-001")
                    String id,
                    @Schema(description = "The step number or ID.", example = "1.1")
                    String step,
                    @Schema(description = "Detailed description of the step's activities.")
                    String description,
                    @Schema(description = "The person or role responsible for this step.", example = "HR Manager")
                    String responsibility,
                    @Schema(description = "Service Level Agreement for this step.", example = "24 hours")
                    String sla,
                    @Schema(description = "Reference to another document or step.", example = "See section 2.3 of HR-Policy")
                    String referenceDoc) {
            }
        }

        @Schema(description = "A term or abbreviation definition.")
        public record TermAbbreviation(
                @Schema(description = "ID of the term to update. Null for new.", example = "term-001")
                String id,
                @Schema(description = "The definition in Vietnamese.", example = "Nhân viên")
                String vietnamese,
                @Schema(description = "The definition in English.", example = "Employee")
                String english) {
        }

        @Schema(description = "Links to related documents and templates.")
        public record RelatedDocument(
                @Schema(description = "List of related legal provisions.", example = "[\"Law-123\", \"Decree-456\"]")
                List<String> legalProvisions,
                @Schema(description = "List of related internal company documents.", example = "[\"HR-Policy-01\", \"IT-Security-Guide\"]")
                List<String> internalDocuments,
                @Schema(description = "List of other related processes.", example = "[\"PROC-002\", \"PROC-003\"]")
                List<String> relatedProcesses,
                @Schema(description = "List of templates used in this process.")
                List<Template> templates) {
            @Schema(description = "A template file used in the process.")
            public record Template(
                    @Schema(description = "ID of the template to update. Null for new.", example = "tpl-001")
                    String id,
                    @Schema(description = "Name of the template.", example = "Offer Letter Template")
                    String name,
                    @Schema(description = "ID of the template file in a file storage system.", example = "file-abc-456")
                    String fileId) {
            }
        }
    }

    @Schema(name = "SaveProcessResponse", description = "Response containing the IDs of the created/updated process entities.")
    public record Response(
            @Schema(description = "The ID of the parent GeneralInformation entity.", example = "d8b8a8e8-8e8e-4f4f-8e8e-8e8e8e8e8e8e")
            String generalId,
            @Schema(description = "The ID of the specific GeneralInformationHistory version that was saved.", example = "c7a7a7e7-7e7e-3f3f-7e7e-7e7e7e7e7e7e")
            String historyId) {
    }
}
