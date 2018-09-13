DESC players;
DESC positions;
DESC comments;
DROP TABLE players;
DROP TABLE positions;
DROP TABLE comments;
SELECT * FROM players;
SELECT * FROM positions;
SELECT * FROM comments;

CREATE TABLE comments (
	player_code			INT				NOT NULL,
	comment				CHAR(100)		NOT NULL,
	edit_time			DATETIME		NOT NULL,
	PRIMARY KEY (player_code, comment)
);

CREATE TABLE players (
	player_code			INT				PRIMARY KEY		NOT NULL	AUTO_INCREMENT,
	srcThumb			CHAR(100),
	playerName			CHAR(20),
	player_price_bp1	INT	,
	player_price_bp2	INT	,
	player_price_bp3	INT	,
	player_price_bp4	INT	,
	player_price_bp5	BIGINT	,
	player_price_bp6	BIGINT	,
	player_price_bp7	BIGINT	,
	player_price_bp8	BIGINT	,
	player_price_bp9	BIGINT	,
	player_price_bp10	BIGINT
);

INSERT INTO players (player_code, srcThumb, playerName, player_price_bp1, player_price_bp2, player_price_bp3,
	player_price_bp4, player_price_bp5, player_price_bp6, player_price_bp7, player_price_bp8, player_price_bp9, player_price_bp10)
	VALUES (123, 'wwnavercom', 'name', 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
	
INSERT INTO positions (player_code, position, stat)
	VALUES (298155355, 'LW', 90),
	(298155355, 'LB', 98),
	(298176403, 'ST', 90),
	(298176403, 'CB', 71),
	(298176403, 'GK', 69)
	
INSERT INTO players (player_code, playerName)
	VALUES (111, 'name')

CREATE TABLE positions (
	player_code			INT				NOT NULL,
	position			CHAR(10)		NOT NULL,
	stat				INT				NOT NULL
);

SELECT * FROM players pl INNER JOIN positions po ON pl.player_code=po.player_code ORDER BY po.stat DESC;
SELECT * FROM players INNER JOIN positions ORDER BY player_code DESC;










CREATE TABLE players (
	player_code		INT				PRIMARY KEY		NOT NULL	AUTO_INCREMENT,
	skill_level 	INT				NOT NULL,
	left_foot		INT				NOT NULL,
	right_foot		INT				NOT NULL,
	player_pay 		INT				NOT NULL,
	player_height	INT				NOT NULL,
	player_weight	INT				NOT NULL,
	name			VARCHAR(20) 	NOT NULL 
);

DROP TABLE player_price;
DESC player_price;
CREATE TABLE player_price (
	player_code			INT		PRIMARY KEY		NOT NULL	AUTO_INCREMENT,
	player_price_bp1	INT	,
	player_price_bp2	INT	,
	player_price_bp3	INT	,
	player_price_bp4	INT	,
	player_price_bp5	INT	,
	player_price_bp6	INT	,
	player_price_bp7	INT	,
	player_price_bp8	INT	,
	player_price_bp9	INT	,
	player_price_bp10	INT
) ;

INSERT INTO players (skill_level, left_foot, right_foot, player_pay, player_height, player_weight, name)
	VALUES (1, 2, 3, 4, 5, 6, 'lee');
	
	
	