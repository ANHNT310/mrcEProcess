create view "e-process-service".process_detail_information_view
            (general_id, general_code, general_scope, general_name, general_short_desc, history_id, business_code,
             diagram_id, object_id, duration, effective_date, effective_type, end_date, stage, version, created_at,
             created_by)
as
SELECT t1.id                AS general_id,
       t1.code              AS general_code,
       t1.scope             AS general_scope,
       t1.name              AS general_name,
       t1.short_description AS general_short_desc,
       t2.id                AS history_id,
       t2.business_code,
       t2.diagram_id,
       t2.object_id,
       t2.duration,
       t2.effective_date,
       t2.effective_type,
       t2.end_date,
       t2.stage,
       t2.version,
       t2.created_at,
       t2.create_by         AS created_by
FROM "e-process-service".general_information t1,
     "e-process-service".general_information_hist t2
WHERE t2.general_information_id::text = t1.id::text;

alter table "e-process-service".process_detail_information_view
    owner to postgres;

