/********************* SHARE - CARGA DE DIMENSIONES **************************/

/****** CARGA DE CATALOGO PAIS **********/

MERGE INTO SHARE_DIM_PAIS D
USING (SELECT DISTINCT PAIS FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT PAIS IS NULL)TMP 
ON (D.GV_PAIS=TMP.PAIS)
WHEN NOT MATCHED THEN
INSERT(PK_PAIS, GV_PAIS)
VALUES(SHARE_SEQ_PK_PAIS.NEXTVAL, TMP.PAIS);
COMMIT;

/****** CARGA DE CATALOGO UNIDAD DE NEGOCIO **********/

MERGE INTO SHARE_DIM_UNIDAD_NEGOCIO D
USING (SELECT DISTINCT UNIDAD_NEGOCIO FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT UNIDAD_NEGOCIO IS NULL) TMP
ON (D.GV_UNIDAD_NEGOCIO=TMP.UNIDAD_NEGOCIO)
WHEN NOT MATCHED THEN
INSERT(PK_UNIDAD_NEGOCIO, GV_UNIDAD_NEGOCIO)
VALUES(SHARE_SEQ_PK_UNIDAD_NEGOCIO.NEXTVAL, TMP.UNIDAD_NEGOCIO);
COMMIT;

/****** CARGA DE CATALOGO CANAL **********/

MERGE INTO SHARE_DIM_CANAL D
USING (SELECT DISTINCT CANAL FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT CANAL IS NULL)TMP 
ON (D.GV_CANAL=TMP.CANAL)
WHEN NOT MATCHED THEN
INSERT(PK_CANAL, GV_CANAL)
VALUES(SHARE_SEQ_PK_CANAL.NEXTVAL, TMP.CANAL);
COMMIT;

/****** CARGA DE CATALOGO SUBCANAL **********/

MERGE INTO SHARE_DIM_SUBCANAL D
USING (SELECT DISTINCT SUBCANAL FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT SUBCANAL IS NULL)TMP 
ON (D.GV_SUBCANAL=TMP.SUBCANAL)
WHEN NOT MATCHED THEN
INSERT(PK_SUBCANAL, GV_SUBCANAL)
VALUES(SHARE_SEQ_PK_SUBCANAL.NEXTVAL, TMP.SUBCANAL);
COMMIT;

/****** CARGA DE CATALOGO UNIDAD OPERATIVA **********/

MERGE INTO SHARE_DIM_UNIDAD_OPERATIVA D
USING (SELECT DISTINCT UNIDAD_OPERATIVA FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT UNIDAD_OPERATIVA IS NULL) TMP
ON (D.GV_UNIDAD_OPERATIVA=TMP.UNIDAD_OPERATIVA)
WHEN NOT MATCHED THEN
INSERT(PK_UNIDAD_OPERATIVA, GV_UNIDAD_OPERATIVA)
VALUES(SHARE_SEQ_PK_UNIDAD_OPERATIVA.NEXTVAL, TMP.UNIDAD_OPERATIVA);
COMMIT;

/****** CARGA DE CATALOGO REGION **********/

MERGE INTO SHARE_DIM_REGION D
USING (SELECT DISTINCT REGION FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT REGION IS NULL) TMP
ON (D.GV_REGION=TMP.REGION)
WHEN NOT MATCHED THEN
INSERT(PK_REGION, GV_REGION)
VALUES(SHARE_SEQ_PK_REGION.NEXTVAL, TMP.REGION);
COMMIT;

/****** CARGA DE CATALOGO ZONA **********/

MERGE INTO SHARE_DIM_ZONA D
USING (SELECT DISTINCT ZONA FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT ZONA IS NULL) TMP 
ON (D.GV_ZONA=TMP.ZONA)
WHEN NOT MATCHED THEN
INSERT(PK_ZONA, GV_ZONA)
VALUES(SHARE_SEQ_PK_ZONA.NEXTVAL, TMP.ZONA);
COMMIT;

/****** CARGA DE CATALOGO GRUPO DE CATEGORIA **********/

MERGE INTO SHARE_DIM_GRUPO_CATEGORIA D
USING (SELECT DISTINCT GRUPO_CATEGORIA FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT GRUPO_CATEGORIA IS NULL)TMP 
ON (D.GV_GRUPO_CATEGORIA=TMP.GRUPO_CATEGORIA)
WHEN NOT MATCHED THEN
INSERT(PK_GRUPO_CATEGORIA, GV_GRUPO_CATEGORIA)
VALUES(SHARE_SEQ_PK_G_CATEGORIA.NEXTVAL, TMP.GRUPO_CATEGORIA);
COMMIT;

/****** CARGA DE CATALOGO CATEGORIA **********/

MERGE INTO SHARE_DIM_CATEGORIA D
USING (SELECT DISTINCT CATEGORIA FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT CATEGORIA IS NULL)TMP 
ON (D.GV_CATEGORIA=TMP.CATEGORIA)
WHEN NOT MATCHED THEN
INSERT(PK_CATEGORIA, GV_CATEGORIA)
VALUES(SHARE_SEQ_PK_CATEGORIA.NEXTVAL, TMP.CATEGORIA);
COMMIT;

/****** CARGA DE CATALOGO FABRICANTE **********/

MERGE INTO SHARE_DIM_FABRICANTE D
USING (SELECT DISTINCT FABRICANTE FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT FABRICANTE IS NULL)TMP 
ON (D.GV_FABRICANTE=TMP.FABRICANTE)
WHEN NOT MATCHED THEN
INSERT(PK_FABRICANTE, GV_FABRICANTE)
VALUES(SHARE_SEQ_PK_FABRICANTE.NEXTVAL, TMP.FABRICANTE);
COMMIT;


/****** CARGA DE CATALOGO MARCA **********/

MERGE INTO SHARE_DIM_MARCA D
USING (SELECT DISTINCT MARCA FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT MARCA IS NULL)TMP 
ON (D.GV_MARCA=TMP.MARCA)
WHEN NOT MATCHED THEN
INSERT(PK_MARCA, GV_MARCA)
VALUES(SHARE_SEQ_PK_MARCA.NEXTVAL, TMP.MARCA);
COMMIT;


/****** CARGA DE CATALOGO SABOR **********/

MERGE INTO SHARE_DIM_SABOR D
USING (SELECT DISTINCT SABOR FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT SABOR IS NULL)TMP 
ON (D.GV_SABOR=TMP.SABOR)
WHEN NOT MATCHED THEN
INSERT(PK_SABOR, GV_SABOR)
VALUES(SHARE_SEQ_PK_SABOR.NEXTVAL, TMP.SABOR);
COMMIT;


/****** CARGA DE CATALOGO TAMANO **********/

MERGE INTO SHARE_DIM_TAMANO D
USING (SELECT DISTINCT TAMANO FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT TAMANO IS NULL)TMP 
ON (D.GV_TAMANO=TMP.TAMANO)
WHEN NOT MATCHED THEN
INSERT(PK_TAMANO, GV_TAMANO)
VALUES(SHARE_SEQ_PK_TAMANO.NEXTVAL, TMP.TAMANO);
COMMIT;

/****** CARGA DE CATALOGO EMPAQUE **********/

MERGE INTO SHARE_DIM_EMPAQUE D
USING (SELECT DISTINCT EMPAQUE FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT EMPAQUE IS NULL)TMP 
ON (D.GV_EMPAQUE=TMP.EMPAQUE)
WHEN NOT MATCHED THEN
INSERT(PK_EMPAQUE, GV_EMPAQUE)
VALUES(SHARE_SEQ_PK_EMPAQUE.NEXTVAL, TMP.EMPAQUE);
COMMIT;

/****** CARGA DE CATALOGO RETORNABILIDAD **********/

MERGE INTO SHARE_DIM_RETORNABILIDAD D
USING (SELECT DISTINCT RETORNABILIDAD FROM SHARE_TMP_COL_INFO_CARGA WHERE NOT RETORNABILIDAD IS NULL)TMP 
ON (D.GV_RETORNABILIDAD=TMP.RETORNABILIDAD)
WHEN NOT MATCHED THEN
INSERT(PK_RETORNABILIDAD, GV_RETORNABILIDAD)
VALUES(SHARE_SEQ_PK_RETORNABILIDAD.NEXTVAL, TMP.RETORNABILIDAD);
COMMIT;