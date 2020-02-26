--Tabla A
drop table a cascade;
create table a (
	id 			varchar(20),
	nombre 		varchar(40),
	codigo 		varchar(20),
	constraint PK_ID primary key (id)
	);
insert into a values('AB1234BA', 'Yura', 'V');
insert into a values('AB3456BA', 'Max', 'So');
insert into a values('AB5678BA', 'Andy', 'Co');
insert into a values('AB9012BA', 'Paul', 'Mo');
insert into a values('AB0001BA', 'Norman', 'Po');
	
--Tabla C
drop table c cascade;
create table c (
	nombre 		varchar(30),
	precio 		integer,
	imagen 		varchar(30),
	constraint FK_NOM primary key (nombre)
	);
insert into c values('Coche', 32000,'img1');
insert into c values('Avion', 120000,'img2');
insert into c values('Barco', 50000,'img3');
insert into c values('Traje', 1234567,'img4');
insert into c values('Gafas', 4000,'img5');

--Tabla D
drop table d cascade;
create table d (
	numero 		serial,
	nombre		varchar(30),
	lugar		varchar(30),
	valor		integer,
	constraint FK_NUM primary key (numero)
	);
insert into d (nombre,lugar,valor) values( 'A.B. Fundation', 'USA', 6);
insert into d (nombre,lugar,valor) values( 'A.B. Corporation', 'England', 9);
insert into d (nombre,lugar,valor) values( 'A.B. Global', 'Japan', 7);
insert into d (nombre,lugar,valor) values( 'A.B. Development', 'Italy', 5);

--Tabla B
drop table b cascade;
create table b (
	numerot 	serial,
	cliente		varchar(20),
	producto 	varchar(30),
	lugar 		integer,
	cantidad 	integer,
	fecha 		date,
	estado		boolean,
	constraint PK_T primary key (numerot),
	constraint FK_C foreign  key (cliente) references a,
	constraint FK_P foreign  key (producto) references c,
	constraint FK_L foreign  key (lugar) references d
	);
insert into b (cliente,producto,lugar,cantidad,fecha,estado) values( 'AB1234BA', 'Traje',1,1, '5/23/2018',false);
insert into b (cliente,producto,lugar,cantidad,fecha,estado) values( 'AB1234BA', 'Gafas',3,2, '11/18/2015',true);
insert into b (cliente,producto,lugar,cantidad,fecha,estado) values( 'AB1234BA', 'Coche',4,1, '6/1/2019',false);
insert into b (cliente,producto,lugar,cantidad,fecha,estado) values( 'AB3456BA', 'Gafas',1,4, '5/10/2018',false);
insert into b (cliente,producto,lugar,cantidad,fecha,estado) values( 'AB5678BA', 'Traje',2,4, '3/12/2019',false);
insert into b (cliente,producto,lugar,cantidad,fecha,estado) values( 'AB5678BA', 'Coche',3,2, '7/9/2017',true);
insert into b (cliente,producto,lugar,cantidad,fecha,estado) values( 'AB9012BA', 'Gafas',4,1, '1/29/2016',true);
insert into b (cliente,producto,lugar,cantidad,fecha,estado) values( 'AB9012BA', 'Traje',3,7, '11/2/2017',true);
insert into b (cliente,producto,lugar,cantidad,fecha,estado) values( 'AB0001BA', 'Avion',4,1, '6/15/2010',true);
