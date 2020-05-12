CREATE EXTERNAL TABLE `product_catalog`(
  `id` int COMMENT 'from deserializer', 
  `title` string COMMENT 'from deserializer', 
  `productcategory` string COMMENT 'from deserializer', 
  `price` float COMMENT 'from deserializer', 
  `year` int COMMENT 'from deserializer')
ROW FORMAT SERDE 
  'org.openx.data.jsonserde.JsonSerDe' 
STORED AS INPUTFORMAT 
  'org.apache.hadoop.mapred.TextInputFormat' 
OUTPUTFORMAT 
  'org.apache.hadoop.hive.ql.io.IgnoreKeyTextOutputFormat'
LOCATION
  's3://product-catalog-data/athena'
TBLPROPERTIES (
  'has_encrypted_data'='false', 
  'transient_lastDdlTime'='1551226830')