/************ SHARE - CALCULA LOS INDECADORES YTD,MA,AA PARA VOLUMEN Y VENTAS **************************/

CREATE TABLE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO AS
SELECT DISTINCT * FROM SHARE_TMP_CLAVECORTAPAIS_INFO;

/****************************************** CALCULOS DE VALORES MENSUALES  ******************************************/

/****** CALCULA VALOR DEL AÑO ANTERIOR VOLUMEN ******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VOLUMEN_AA=(
SELECT SUM(VOLUMEN_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.ANIO,0)= nvl(TEMPORAL.ANIO-1,0)
AND nvl(ST.MES,0)= nvl(TEMPORAL.MES,0)
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.CANAL,0)=nvl(TEMPORAL.CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0)
);
COMMIT;


/****** CALCULA VALOR DEL MES ANTERIOR VOLUMEN (SIN JANRO)******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VOLUMEN_MA=(
SELECT SUM(VOLUMEN_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.TIEMPO,0)= nvl(TEMPORAL.TIEMPO-1,0)
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.ID_CANAL,0)=nvl(TEMPORAL.ID_CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0))
WHERE MES!='JAN';
COMMIT;

/****** CALCULA VALOR DEL MES ANTERIOR VOLUMEN (SOLO JANRO)******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VOLUMEN_MA=(
SELECT SUM(VOLUMEN_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.ANIO,0)= nvl(TEMPORAL.ANIO-1,0)
AND nvl(ST.MES,0)= 'DEC'
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.ID_CANAL,0)=nvl(TEMPORAL.ID_CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0))
WHERE MES='JAN';
COMMIT;


/****** CALCULA VALOR DEL AÑO ANTERIOR VALOR ******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VENTA_AA=(
SELECT SUM(VENTA_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.ANIO,0)= nvl(TEMPORAL.ANIO-1,0)
AND nvl(ST.MES,0)= nvl(TEMPORAL.MES,0)
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.ID_CANAL,0)=nvl(TEMPORAL.ID_CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0)
);
COMMIT;

/****** CALCULA VALOR DEL MES ANTERIOR VALOR (SIN JANRO)******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VENTA_MA=(
SELECT SUM(VENTA_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.TIEMPO,0)= nvl(TEMPORAL.TIEMPO-1,0)
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.ID_CANAL,0)=nvl(TEMPORAL.ID_CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0))
WHERE MES!='JAN';
COMMIT;


/****** CALCULA VALOR DEL MES ANTERIOR VALOR (SOLO JANRO)******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VENTA_MA=(
SELECT SUM(VENTA_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.ANIO,0)= nvl(TEMPORAL.ANIO-1,0)
AND nvl(ST.MES,0)= 'DEC'
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.ID_CANAL,0)=nvl(TEMPORAL.ID_CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0))
WHERE MES='JAN';
COMMIT;

/****************************************** CALCULOS DE VALORES ACUMULADOS  ******************************************/

/****** CALCULA VALOR YTD VOLUMEN ******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VOLUMEN_YTD=(
SELECT SUM(VOLUMEN_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.ANIO,0)= nvl(TEMPORAL.ANIO,0)
AND nvl(ST.TIEMPO,0)<= nvl(TEMPORAL.TIEMPO,0)
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.ID_CANAL,0)=nvl(TEMPORAL.ID_CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0)
);
COMMIT;

/****** CALCULA VALOR YTD AA VOLUMEN ******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VOLUMEN_YTD_AA=(
SELECT SUM(VOLUMEN_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.ANIO,0)= nvl(TEMPORAL.ANIO-1,0)
AND nvl(ST.TIEMPO,0)<= nvl(TEMPORAL.TIEMPO-100,0)
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.ID_CANAL,0)=nvl(TEMPORAL.ID_CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0)
);
COMMIT;


/****** CALCULA VALOR YTD VALOR ******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VENTA_YTD=(
SELECT SUM(VENTA_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.ANIO,0)= nvl(TEMPORAL.ANIO,0)
AND nvl(ST.TIEMPO,0)<= nvl(TEMPORAL.TIEMPO,0)
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.ID_CANAL,0)=nvl(TEMPORAL.ID_CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0)
);
COMMIT;

/****** CALCULA VALOR YTD AA VALOR ******/

UPDATE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO TEMPORAL SET TEMPORAL.VENTA_YTD_AA=(
SELECT SUM(VENTA_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_INFO ST
WHERE nvl(ST.ANIO,0)= nvl(TEMPORAL.ANIO-1,0)
AND nvl(ST.TIEMPO,0)<= nvl(TEMPORAL.TIEMPO-100,0)
AND nvl(ST.ID_PAIS,0)=nvl(TEMPORAL.ID_PAIS,0)
AND nvl(ST.ID_CANAL,0)=nvl(TEMPORAL.ID_CANAL,0)
AND nvl(ST.ID_GRUPO_CATEGORIA,0)=nvl(TEMPORAL.ID_GRUPO_CATEGORIA,0)
AND nvl(ST.ID_CATEGORIA,0)=nvl(TEMPORAL.ID_CATEGORIA,0)
AND nvl(ST.ID_FABRICANTE,0)=nvl(TEMPORAL.ID_FABRICANTE,0)
);
COMMIT;

/****** INSERTA REGISTROS A TABLA TMP **********/

TRUNCATE TABLE SHARE_TMP_CLAVECORTAPAIS_INFO;

INSERT INTO SHARE_TMP_CLAVECORTAPAIS_INFO
SELECT * FROM SHARE_TEMPORAL_CLAVECORTAPAIS_INFO;
COMMIT;

/****** BORRA TABLA TEMPORAL SHARE **********/

DROP TABLE SHARE_TEMPORAL_CLAVECORTAPAIS_INFO;


/********************* SHARE - CALCULA LOS INDECADORES YTD,MA,AA PARA VOLUMEN Y VENTAS KOF **************************/

CREATE TABLE SHARE_TMP_CLAVECORTAPAIS_KOF AS
SELECT DISTINCT * FROM SHARE_TMP_CLAVECORTAPAIS_INFO
WHERE FABRICANTE LIKE '%KOF%';

/****************************************** CALCULOS DE VALORES ******************************************/

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_AA_KOF=(
SELECT VOLUMEN_AA
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_MA_KOF=(
SELECT VOLUMEN_MA
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_MES_KOF=(
SELECT VOLUMEN_MES
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_AA_KOF=(
SELECT VENTA_AA
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_MA_KOF=(
SELECT VENTA_MA
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_MES_KOF=(
SELECT VENTA_MES
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_YTD_KOF=(
SELECT VOLUMEN_YTD
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_YTD_AA_KOF=(
SELECT VOLUMEN_YTD_AA
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_YTD_KOF=(
SELECT VENTA_YTD
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_YTD_AA_KOF=(
SELECT VENTA_YTD_AA
FROM SHARE_TMP_CLAVECORTAPAIS_KOF TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;



/**** ELIMINAR TABLA TEMPORAL CALCULOS KOF ****/
DROP TABLE SHARE_TMP_CLAVECORTAPAIS_KOF;

/********************* SHARE - CALCULA LOS INDECADORES YTD,MA,AA TOTALES **************************/
CREATE TABLE SHARE_TMP_CLAVECORTAPAIS_TOTAL AS
SELECT * FROM ( SELECT DISTINCT * FROM SHARE_TMP_CLAVECORTAPAIS_INFO
WHERE FABRICANTE LIKE '%TOTAL%');

/****************************************** CALCULOS DE VALORES ******************************************/

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_AA_TOTAL=(
SELECT SUM(VOLUMEN_AA)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_MA_TOTAL=(
SELECT SUM(VOLUMEN_MA)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_MES_TOTAL=(
SELECT SUM(VOLUMEN_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_AA_TOTAL=(
SELECT SUM(VENTA_AA)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_MA_TOTAL=(
SELECT SUM(VENTA_MA)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_MES_TOTAL=(
SELECT SUM(VENTA_MES)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_YTD_TOTAL=(
SELECT SUM(VOLUMEN_YTD)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VOLUMEN_YTD_AA_TOTAL=(
SELECT SUM(VOLUMEN_YTD_AA)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_YTD_TOTAL=(
SELECT SUM(VENTA_YTD)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

UPDATE SHARE_TMP_CLAVECORTAPAIS_INFO INFO SET INFO.VENTA_YTD_AA_TOTAL=(
SELECT SUM(VENTA_YTD_AA)
FROM SHARE_TMP_CLAVECORTAPAIS_TOTAL TMP
WHERE TMP.FECHA = INFO.FECHA
AND TMP.PAIS = INFO.PAIS
AND TMP.GRUPO_CATEGORIA = INFO.GRUPO_CATEGORIA
AND TMP.CATEGORIA = INFO.CATEGORIA
AND TMP.CANAL = INFO.CANAL);
COMMIT;

/************ ELIMINAR TABLA TEMPORAL CALCULOS TOTALES ************/
DROP TABLE SHARE_TMP_CLAVECORTAPAIS_TOTAL;
COMMIT;