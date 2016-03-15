/************************** SHARE - CARGA DE TABLAS ST FABRICANTE DASHBOARD SHARE **************************/

/****** ELIMINA REGISTROS EXISTENTES DE TABLA DASHBOARD ST GRUPO DE CATEGORIA**********/

DELETE FROM SHARE_ST_INFO_G_CAT_FAB_DASH ST 
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

/********************* SHARE - CALCULA LOS INDICADORES YTD,MA,AA TOTALES G_CATEGORIA ********************/

CREATE TABLE SHARE_ST_INFO_CAT_FAB_TOTAL AS
SELECT * FROM ( SELECT DISTINCT * FROM SHARE_ST_INFO_CAT
WHERE FABRICANTE LIKE '%TOTAL%');

/********** INSERTA REGISTROS VOLUMEN Y VENTA A TABLA GRUPO DE CATEGORIA ST DASHBOARD **********/

INSERT INTO SHARE_ST_INFO_G_CAT_FAB_DASH
(FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE,
INDICADOR_AA, INDICADOR_AA_TOTAL,
INDICADOR_MES, INDICADOR_MES_TOTAL,
INDICADOR_YTD_AA, INDICADOR_YTD_AA_TOTAL,
INDICADOR_YTD_MES, INDICADOR_YTD_MES_TOTAL)
SELECT FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE,
SUM(VOLUMEN_AA) AS INDICADOR_AA,
SUM(VOLUMEN_AA_TOTAL) AS INDICADOR_AA_TOTAL,
SUM(VOLUMEN_MES) AS INDICADOR_MES,
SUM(VOLUMEN_MES_TOTAL) AS INDICADOR_MES_TOTAL,
SUM(VOLUMEN_YTD_AA) AS INDICADOR_YTD_AA,
SUM(VOLUMEN_YTD_AA_TOTAL) AS INDICADOR_YTD_AA_TOTAL,
SUM(VOLUMEN_YTD) AS INDICADOR_YTD_MES,
SUM(VOLUMEN_YTD_TOTAL) AS INDICADOR_YTD_MES_TOTAL
FROM SHARE_ST_INFO_CAT
WHERE FABRICANTE != 'TOTAL' AND PAIS = 'NOMBREPAIS'
GROUP BY FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE;
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET INDICADOR = 'SOM' WHERE INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_MES_TOTAL=( 
SELECT SUM(VOLUMEN_MES)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA)
WHERE INFO.INDICADOR = 'SOM' AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_AA_TOTAL=( 
SELECT SUM(VOLUMEN_AA)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA)
WHERE INFO.INDICADOR = 'SOM' AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_YTD_MES_TOTAL=( 
SELECT SUM(VOLUMEN_YTD)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA)
WHERE INFO.INDICADOR = 'SOM' AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_YTD_AA_TOTAL=( 
SELECT SUM(VOLUMEN_YTD_AA)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA)
WHERE INFO.INDICADOR = 'SOM' AND PAIS = 'NOMBREPAIS';
COMMIT;
--------------------------------------------------
-------------- CALCULO NARTD - PAIS --------------

INSERT INTO SHARE_ST_INFO_G_CAT_FAB_DASH
(FECHA, ANIO, MES, TIEMPO, PAIS, FABRICANTE,
INDICADOR_AA, INDICADOR_AA_TOTAL,
INDICADOR_MES, INDICADOR_MES_TOTAL,
INDICADOR_YTD_AA, INDICADOR_YTD_AA_TOTAL,
INDICADOR_YTD_MES, INDICADOR_YTD_MES_TOTAL)
SELECT FECHA, ANIO, MES, TIEMPO, PAIS, FABRICANTE,
SUM(VOLUMEN_AA) AS INDICADOR_AA,
SUM(VOLUMEN_AA_TOTAL) AS INDICADOR_AA_TOTAL,
SUM(VOLUMEN_MES) AS INDICADOR_MES,
SUM(VOLUMEN_MES_TOTAL) AS INDICADOR_MES_TOTAL,
SUM(VOLUMEN_YTD_AA) AS INDICADOR_YTD_AA,
SUM(VOLUMEN_YTD_AA_TOTAL) AS INDICADOR_YTD_AA_TOTAL,
SUM(VOLUMEN_YTD) AS INDICADOR_YTD_MES,
SUM(VOLUMEN_YTD_TOTAL) AS INDICADOR_YTD_MES_TOTAL
FROM SHARE_ST_INFO_CAT
WHERE FABRICANTE != 'TOTAL' AND PAIS = 'NOMBREPAIS'
GROUP BY FECHA, ANIO, MES, TIEMPO, PAIS, FABRICANTE;
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_MES_TOTAL=( 
SELECT SUM(VOLUMEN_MES) 
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS)
WHERE INFO.INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_AA_TOTAL=( 
SELECT SUM(VOLUMEN_AA)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS)
WHERE INFO.INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_YTD_MES_TOTAL=( 
SELECT SUM(VOLUMEN_YTD) 
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS)
WHERE INFO.INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_YTD_AA_TOTAL=( 
SELECT SUM(VOLUMEN_YTD_AA)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS)
WHERE INFO.INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET INDICADOR = 'SOM' WHERE INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

-------------------------------------------------
-------------------------------------------------

INSERT INTO SHARE_ST_INFO_G_CAT_FAB_DASH
(FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE,
INDICADOR_AA, INDICADOR_AA_TOTAL,
INDICADOR_MES, INDICADOR_MES_TOTAL,
INDICADOR_YTD_AA, INDICADOR_YTD_AA_TOTAL,
INDICADOR_YTD_MES, INDICADOR_YTD_MES_TOTAL)
SELECT FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE,
SUM(VENTA_AA) AS INDICADOR_AA,
SUM(VENTA_AA_TOTAL) AS INDICADOR_AA_TOTAL,
SUM(VENTA_MES) AS INDICADOR_MES,
SUM(VENTA_MES_TOTAL) AS INDICADOR_MES_TOTAL,
SUM(VENTA_YTD_AA) AS INDICADOR_YTD_AA,
SUM(VENTA_YTD_AA_TOTAL) AS INDICADOR_YTD_AA_TOTAL,
SUM(VENTA_YTD) AS INDICADOR_YTD_MES,
SUM(VENTA_YTD_TOTAL) AS INDICADOR_YTD_MES_TOTAL
FROM SHARE_ST_INFO_CAT
WHERE FABRICANTE != 'TOTAL' AND PAIS = 'NOMBREPAIS'
GROUP BY FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE;
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET INDICADOR = 'SOS' WHERE INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_MES_TOTAL=( 
SELECT SUM(VENTA_MES)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA)
WHERE INFO.INDICADOR = 'SOS' AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_AA_TOTAL=( 
SELECT SUM(VENTA_AA)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA)
WHERE INFO.INDICADOR = 'SOS' AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_YTD_MES_TOTAL=( 
SELECT SUM(VENTA_YTD)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA)
WHERE INFO.INDICADOR = 'SOS' AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_YTD_AA_TOTAL=( 
SELECT SUM(VENTA_YTD_AA)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA)
WHERE INFO.INDICADOR = 'SOS' AND PAIS = 'NOMBREPAIS';
COMMIT;
--------------------------------------------------
-------------- CALCULO NARTD - PAIS --------------

INSERT INTO SHARE_ST_INFO_G_CAT_FAB_DASH
(FECHA, ANIO, MES, TIEMPO, PAIS, FABRICANTE,
INDICADOR_AA, INDICADOR_AA_TOTAL,
INDICADOR_MES, INDICADOR_MES_TOTAL,
INDICADOR_YTD_AA, INDICADOR_YTD_AA_TOTAL,
INDICADOR_YTD_MES, INDICADOR_YTD_MES_TOTAL)
SELECT FECHA, ANIO, MES, TIEMPO, PAIS, FABRICANTE,
SUM(VENTA_AA) AS INDICADOR_AA,
SUM(VENTA_AA_TOTAL) AS INDICADOR_AA_TOTAL,
SUM(VENTA_MES) AS INDICADOR_MES,
SUM(VENTA_MES_TOTAL) AS INDICADOR_MES_TOTAL,
SUM(VENTA_YTD_AA) AS INDICADOR_YTD_AA,
SUM(VENTA_YTD_AA_TOTAL) AS INDICADOR_YTD_AA_TOTAL,
SUM(VENTA_YTD) AS INDICADOR_YTD_MES,
SUM(VENTA_YTD_TOTAL) AS INDICADOR_YTD_MES_TOTAL
FROM SHARE_ST_INFO_CAT
WHERE FABRICANTE != 'TOTAL' AND PAIS = 'NOMBREPAIS'
GROUP BY FECHA, ANIO, MES, TIEMPO, PAIS, FABRICANTE;
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_MES_TOTAL=( 
SELECT SUM(VENTA_MES) 
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS)
WHERE INFO.INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_AA_TOTAL=( 
SELECT SUM(VENTA_AA)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS)
WHERE INFO.INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_YTD_MES_TOTAL=( 
SELECT SUM(VENTA_YTD) 
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS)
WHERE INFO.INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH INFO SET INFO.INDICADOR_YTD_AA_TOTAL=( 
SELECT SUM(VENTA_YTD_AA)
FROM SHARE_ST_INFO_CAT_FAB_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS)
WHERE INFO.INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET INDICADOR = 'SOS' WHERE INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

DROP TABLE SHARE_ST_INFO_CAT_FAB_TOTAL;


/****************************************** CALCULOS DE VALORES ******************************************/

/****** CALCULA VALORES PARA SOM Y SOS  ******/

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET CALCULO_AA= Decode(INDICADOR_AA_TOTAL,0,0,((INDICADOR_AA)/(INDICADOR_AA_TOTAL))*100)
WHERE PAIS = 'NOMBREPAIS';
COMMIT;      
          
UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET CALCULO_MES= Decode(INDICADOR_MES_TOTAL,0,0,((INDICADOR_MES)/(INDICADOR_MES_TOTAL))*100)
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET CALCULO_YTD_AA= Decode(INDICADOR_YTD_AA_TOTAL,0,0,((INDICADOR_YTD_AA)/(INDICADOR_YTD_AA_TOTAL))*100)
WHERE PAIS = 'NOMBREPAIS';
COMMIT;      
          
UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET CALCULO_YTD_MES= Decode(INDICADOR_YTD_MES_TOTAL,0,0,((INDICADOR_YTD_MES)/(INDICADOR_YTD_MES_TOTAL))*100)
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

/****** CALCULA VARIACIONES PP ******/

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET VARIACION_PP= CALCULO_MES-CALCULO_AA
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_G_CAT_FAB_DASH SET VARIACION_YTD_PP= CALCULO_YTD_MES-CALCULO_YTD_AA
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
/********** INSERTA REGISTROS VOLUMEN Y VENTA A TABLA CATEGORIA ST DASHBOARD **********/

/****** ELIMINA REGISTROS EXISTENTES DE TABLA DASHBOARD ST GRUPO DE CATEGORIA**********/

DELETE FROM SHARE_ST_INFO_CAT_FAB_DASH ST 
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

INSERT INTO SHARE_ST_INFO_CAT_FAB_DASH
(FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, CATEGORIA, FABRICANTE,
INDICADOR_AA, INDICADOR_AA_TOTAL,
INDICADOR_MES, INDICADOR_MES_TOTAL,
INDICADOR_YTD_AA, INDICADOR_YTD_AA_TOTAL,
INDICADOR_YTD_MES, INDICADOR_YTD_MES_TOTAL)
SELECT FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, CATEGORIA, FABRICANTE,
SUM(VOLUMEN_AA) AS INDICADOR_AA,
SUM(VOLUMEN_AA_TOTAL) AS INDICADOR_AA_TOTAL,
SUM(VOLUMEN_MES) AS INDICADOR_MES,
SUM(VOLUMEN_MES_TOTAL) AS INDICADOR_MES_TOTAL,
SUM(VOLUMEN_YTD_AA) AS INDICADOR_YTD_AA,
SUM(VOLUMEN_YTD_AA_TOTAL) AS INDICADOR_YTD_AA_TOTAL,
SUM(VOLUMEN_YTD) AS INDICADOR_YTD_MES,
SUM(VOLUMEN_YTD_TOTAL) AS INDICADOR_YTD_MES_TOTAL
FROM SHARE_ST_INFO_CAT
WHERE FABRICANTE != 'TOTAL' AND PAIS = 'NOMBREPAIS'
GROUP BY FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, CATEGORIA, FABRICANTE;
COMMIT;

UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET INDICADOR = 'SOM' WHERE INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

-------------- CALCULO G_CATEGORIA - FABRICANTE --------------

INSERT INTO SHARE_ST_INFO_CAT_FAB_DASH
(FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE,
INDICADOR_AA, INDICADOR_AA_TOTAL,
INDICADOR_MES, INDICADOR_MES_TOTAL,
INDICADOR_YTD_AA, INDICADOR_YTD_AA_TOTAL,
INDICADOR_YTD_MES, INDICADOR_YTD_MES_TOTAL)
SELECT FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE,
SUM(INDICADOR_AA) AS INDICADOR_AA,
SUM(INDICADOR_AA_TOTAL) AS INDICADOR_AA_TOTAL,
SUM(INDICADOR_MES) AS INDICADOR_MES,
SUM(INDICADOR_MES_TOTAL) AS INDICADOR_MES_TOTAL,
SUM(INDICADOR_YTD_AA) AS INDICADOR_YTD_AA,
SUM(INDICADOR_YTD_AA_TOTAL) AS INDICADOR_YTD_AA_TOTAL,
SUM(INDICADOR_YTD_MES) AS INDICADOR_YTD_MES,
SUM(INDICADOR_YTD_MES_TOTAL) AS INDICADOR_YTD_MES_TOTAL
FROM SHARE_ST_INFO_G_CAT_FAB_DASH
WHERE INDICADOR = 'SOM' AND PAIS = 'NOMBREPAIS'
GROUP BY FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE;
COMMIT;

UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET INDICADOR = 'SOM' WHERE INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

------------------------------------------------------------------
------------------------------------------------------------------

INSERT INTO SHARE_ST_INFO_CAT_FAB_DASH
(FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, CATEGORIA, FABRICANTE,
INDICADOR_AA, INDICADOR_AA_TOTAL,
INDICADOR_MES, INDICADOR_MES_TOTAL,
INDICADOR_YTD_AA, INDICADOR_YTD_AA_TOTAL,
INDICADOR_YTD_MES, INDICADOR_YTD_MES_TOTAL)
SELECT FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, CATEGORIA, FABRICANTE,
SUM(VENTA_AA) AS INDICADOR_AA,
SUM(VENTA_AA_TOTAL) AS INDICADOR_AA_TOTAL,
SUM(VENTA_MES) AS INDICADOR_MES,
SUM(VENTA_MES_TOTAL) AS INDICADOR_MES_TOTAL,
SUM(VENTA_YTD_AA) AS INDICADOR_YTD_AA,
SUM(VENTA_YTD_AA_TOTAL) AS INDICADOR_YTD_AA_TOTAL,
SUM(VENTA_YTD) AS INDICADOR_YTD_MES,
SUM(VENTA_YTD_TOTAL) AS INDICADOR_YTD_MES_TOTAL
FROM SHARE_ST_INFO_CAT
WHERE FABRICANTE != 'TOTAL' AND PAIS = 'NOMBREPAIS'
GROUP BY FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, CATEGORIA, FABRICANTE;
COMMIT;

UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET INDICADOR = 'SOS' WHERE INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

-------------------------------------------------------------
-------------- CALCULO G_CATEGORIA - FABRICANTE --------------

INSERT INTO SHARE_ST_INFO_CAT_FAB_DASH
(FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE,
INDICADOR_AA, INDICADOR_AA_TOTAL,
INDICADOR_MES, INDICADOR_MES_TOTAL,
INDICADOR_YTD_AA, INDICADOR_YTD_AA_TOTAL,
INDICADOR_YTD_MES, INDICADOR_YTD_MES_TOTAL)
SELECT FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE,
SUM(INDICADOR_AA) AS INDICADOR_AA,
SUM(INDICADOR_AA_TOTAL) AS INDICADOR_AA_TOTAL,
SUM(INDICADOR_MES) AS INDICADOR_MES,
SUM(INDICADOR_MES_TOTAL) AS INDICADOR_MES_TOTAL,
SUM(INDICADOR_YTD_AA) AS INDICADOR_YTD_AA,
SUM(INDICADOR_YTD_AA_TOTAL) AS INDICADOR_YTD_AA_TOTAL,
SUM(INDICADOR_YTD_MES) AS INDICADOR_YTD_MES,
SUM(INDICADOR_YTD_MES_TOTAL) AS INDICADOR_YTD_MES_TOTAL
FROM SHARE_ST_INFO_G_CAT_FAB_DASH
WHERE INDICADOR = 'SOS' AND PAIS = 'NOMBREPAIS'
GROUP BY FECHA, ANIO, MES, TIEMPO, PAIS, GRUPO_CATEGORIA, FABRICANTE;
COMMIT;

UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET INDICADOR = 'SOS' WHERE INDICADOR IS NULL AND PAIS = 'NOMBREPAIS';
COMMIT;

------------------------------------------------------------------
------------------------------------------------------------------
/****** CALCULA VALORES PARA SOM Y SOS  ******/

UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET CALCULO_AA= Decode(INDICADOR_AA_TOTAL,0,0,((INDICADOR_AA)/(INDICADOR_AA_TOTAL))*100)
WHERE PAIS = 'NOMBREPAIS';
COMMIT;      
          
UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET CALCULO_MES= Decode(INDICADOR_MES_TOTAL,0,0,((INDICADOR_MES)/(INDICADOR_MES_TOTAL))*100)
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET CALCULO_YTD_AA= Decode(INDICADOR_YTD_AA_TOTAL,0,0,((INDICADOR_YTD_AA)/(INDICADOR_YTD_AA_TOTAL))*100)
WHERE PAIS = 'NOMBREPAIS';
COMMIT;      
          
UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET CALCULO_YTD_MES= Decode(INDICADOR_YTD_MES_TOTAL,0,0,((INDICADOR_YTD_MES)/(INDICADOR_YTD_MES_TOTAL))*100)
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

/****** CALCULA VARIACIONES PP ******/

UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET VARIACION_PP= CALCULO_MES-CALCULO_AA
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

UPDATE SHARE_ST_INFO_CAT_FAB_DASH SET VARIACION_YTD_PP= CALCULO_YTD_MES-CALCULO_YTD_AA
WHERE PAIS = 'NOMBREPAIS';
COMMIT;

EXIT;