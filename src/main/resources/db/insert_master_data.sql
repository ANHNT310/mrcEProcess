INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('17bf7aa1-e316-4da4-b07e-873deba28418', 'Management', 'Quản lý điều hành');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('06bc245e-bd1f-4500-8eed-c354d1599e74', 'Credit', 'Nghiệp vụ tín dụng');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('f8edd417-b1dd-4d02-98f4-a010730d5f8a', 'Investment - Capital', 'Đầu tư - Nguồn vốn');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('23e50402-beb1-4846-9304-25ee2d218fa8', 'Insurance Agent', 'Đại lý bảo hiểm');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('5b3596d2-7252-4281-8266-ab5063c699b6', 'Sale Management', 'Quản lý bán hành');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('433adc12-9b01-4c07-bec2-4e6a5ae92854', 'Internal Audit', 'Kiểm toán nội bộ');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('1f1c6586-4da9-4cee-86c6-5abe6d682d04', 'Risk Management', 'Quản trị rủi ro');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('9d649c01-6a28-4646-960f-45ac26c30588', 'Legal and Compliance', 'Pháp chế & Tuân thủ');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('940abe62-3389-4789-9458-6b1997520234', 'Accounting Finance', 'Tài chính Kế toán');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('307b2fff-e5d3-41c7-a90c-89e731a7c528', 'Human Resource Management', 'Quản trị nguồn nhân lực');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('19bdb03a-3616-42b8-bfb2-1d5d72959502', 'IT', 'Công nghệ thông tin');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('d1c985c8-961e-4470-9bee-4283a5b77dbd', 'Management Information System', 'Quản trị Thông tin');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('8da9b7aa-8c0a-40bc-993e-f3aeb05ecb3f', 'PR- Branding', 'Truyền thông - thương hiệu');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('8e9d3381-ebf3-474f-960f-43167a7db99e', 'Procurement', 'Mua sắm');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('0d7baf84-79db-4b69-92a7-bda7b6a28664', 'Clerical', 'Hành chính - Văn phòng');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('f1622f6a-cffc-45c6-ba40-8f2dbb339f07', 'Customer Service', 'Dịch vụ Khách hàng');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('b4d4b77b-b2aa-4f5f-bb92-a1ed1283d336', 'Network developing', 'Phát triển mạng lưới');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('9db04ce6-72fa-4f9a-abe4-17fdd001a8e7', 'Collections', 'Xử lý tín dụng');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('00135c15-5d2f-4f5b-8411-8587b41cfc93', 'Payment', 'Nghiệp vụ Thanh toán');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('b1dd5d76-ac4d-40c4-823a-fff0c001f2ad', 'ALCO', 'Hỗ trợ ALCO');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('1da9a75e-5f81-4f4f-8249-12cdbd7bfa91', 'Fraud Control', 'Kiểm soát gian lận');
INSERT INTO "e-process-service".mst_category (id, en_name, vie_name) VALUES ('b33a295b-6b74-4e8a-9132-27abf330505d', 'Others', 'Khác');


INSERT INTO "e-process-service".mst_authority (id, created_at, create_by, updated_at, updated_by, code, name) VALUES ('9864e11e-022e-4024-96c4-06270480c750', null, null, null, null, 'DIVISION_CENTER_DIRECTOR', 'Division/Center Director');
INSERT INTO "e-process-service".mst_authority (id, created_at, create_by, updated_at, updated_by, code, name) VALUES ('2dbf1769-f09e-4ba1-9d48-74990ae647e8', null, null, null, null, 'CEO', 'CEO');
INSERT INTO "e-process-service".mst_authority (id, created_at, create_by, updated_at, updated_by, code, name) VALUES ('e1b0be2c-71fe-45ab-80be-dfabf0b2b20d', null, null, null, null, 'CHAIRMAN', 'Chairman');
INSERT INTO "e-process-service".mst_authority (id, created_at, create_by, updated_at, updated_by, code, name) VALUES ('1aaa473c-9568-4489-a45b-aac9b49fdf97', null, null, null, null, 'BOD', 'BOD');

INSERT INTO "e-process-service".mst_document_type (id, created_at, create_by, updated_at, updated_by, code, en_name, vi_name) VALUES ('7472001b-b5fb-460a-bb1d-01cc2b365a7c', null, null, null, null, 'PROCESS', 'Process', 'Quy trình');
INSERT INTO "e-process-service".mst_document_type (id, created_at, create_by, updated_at, updated_by, code, en_name, vi_name) VALUES ('2016695f-e444-4c60-8851-b1c2505becde', null, null, null, null, 'PROFESSIONAL_GUIDANCE', 'Professional Guidance', 'Hướng dẫn nghiệp vụ');
INSERT INTO "e-process-service".mst_document_type (id, created_at, create_by, updated_at, updated_by, code, en_name, vi_name) VALUES ('148fbb1d-f86b-4a3d-8001-4b0f6f5762e4', null, null, null, null, 'OPERATIONAL_GUIDANCE', 'Operational Guidance', 'Hướng dẫn vận hành');
INSERT INTO "e-process-service".mst_document_type (id, created_at, create_by, updated_at, updated_by, code, en_name, vi_name) VALUES ('84b58d0e-ae21-4537-8447-65f121b8970c', null, null, null, null, 'MEMORANDUM', 'Memorandum', 'Biên bản ghi nhớ');
INSERT INTO "e-process-service".mst_document_type (id, created_at, create_by, updated_at, updated_by, code, en_name, vi_name) VALUES ('aecf71d2-33be-4548-9f28-837915fb9fa4', null, null, null, null, 'PROPOSAL', 'Proposal', 'Tờ trình');
INSERT INTO "e-process-service".mst_document_type (id, created_at, create_by, updated_at, updated_by, code, en_name, vi_name) VALUES ('de423469-4a61-465b-bed2-8b25b2d13ea1', null, null, null, null, 'BRD', 'BRD', 'Mô tả yêu cầu kinh doanh');
INSERT INTO "e-process-service".mst_document_type (id, created_at, create_by, updated_at, updated_by, code, en_name, vi_name) VALUES ('1697e76c-2e61-4eb5-a6fc-6d9bfcbeed76', null, null, null, null, 'OTHERS', 'Others', 'Khác');

