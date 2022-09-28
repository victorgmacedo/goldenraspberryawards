CREATE TABLE producer (
	id bigint auto_increment not null,
	"year" int not null,
	title varchar(150) not null,
	studio varchar(150) not null,
	producer varchar(150) not null,
    winner boolean not null
);