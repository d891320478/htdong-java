CREATE TABLE IF NOT EXISTS short_url
(
   id bigint (20) NOT NULL AUTO_INCREMENT,
   gmt_create datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   gmt_modified datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   short_path varchar (64) NOT NULL,
   real_url text NOT NULL,
   PRIMARY KEY (id),
   UNIQUE KEY uk_shortpath (short_path)
)
ENGINE= InnoDB DEFAULT CHARSET= utf8 COLLATE= utf8_bin;

CREATE TABLE IF NOT EXISTS not_start_live
(
   id bigint (20) NOT NULL AUTO_INCREMENT,
   gmt_create datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   gmt_modified datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   room_id bigint (20) NOT NULL,
   PRIMARY KEY (id)
)
ENGINE= InnoDB DEFAULT CHARSET= utf8 COLLATE= utf8_bin;