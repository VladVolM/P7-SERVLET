--Tabla A
drop table A cascade;
create table A(
	ID 		varchar(20),
	Nombre 		varchar(40),
	Codigo 		varchar(20),
	constraint PK_ID primary key (ID)
	);
insert into A values('AB1234BA', 'Yura', 'V');
insert into A values('AB3456BA', 'Max', 'So');
insert into A values('AB5678BA', 'Andy', 'Co');
insert into A values('AB9012BA', 'Paul', 'Mo');
insert into A values('AB0001BA', 'Norman', 'Po');
	
--Tabla C
drop table C cascade;
create table raton(
	Nombre 		varchar(30),
	Precio 		integer,
	Imagen 		varchar(30),
	constraint FK_RAT primary key (Nombre)
	);
insert into C values('Coche', 32000,'img1');
insert into C values('Avion', 120000,'img2');
insert into C values('Barco', 50000,'img3');
insert into C values('Traje', 1234567,'img4');
insert into C values('Gafas', 4000,'img5');

--Tabla D
drop table D cascade;
create table D(
	Numero 		integer,
	Nombre		varchar(30),
	Lugar		varchar(30),
	Valor		integer,
	constraint FK_TEC primary key (Numero)
	);
insert into D values(1, 'A.B. Fundation', 'USA', 6);
insert into D values(2, 'A.B. Corporation', 'England', 9);
insert into D values(3, 'A.B. Global', 'Japan', 7);
insert into D values(4, 'A.B. Development', 'Italy', 5);

--Tabla B
drop table B cascade;
create table B(
	NumeroT 	integer,
	Cliente		varchar(20),
	Producto 	varchar(30),
	Lugar 		integer,
	Cantidad 	integer,
	Fecha 		date,
	Estado		boolean,
	constraint PK_T primary key (NumeroT),
	constraint FK_C foreign  key (Cliente) references A,
	constraint FK_P foreign  key (Cliente) references C,
	constraint FK_L foreign  key (Cliente) references D
	);
insert into B values(1, 'AB1234BA', 'Traje',1,1, '5/23/2018',false);
insert into B values(2, 'AB1234BA', 'Gafas',3,2, '11/18/2015',true);
insert into B values(3, 'AB1234BA', 'Coche',4,1, '6/1/2019',false);
insert into B values(4, 'AB3456BA', 'Gafas',1,4, '5/10/2018',false);
insert into B values(5, 'AB5678BA', 'Traje',2,4, '3/12/2019',false);
insert into B values(6, 'AB5678BA', 'Coche',3,2, '7/9/2017',true);
insert into B values(7, 'AB9012BA', 'Gafas',4,1, '1/29/2016',true);
insert into B values(8, 'AB9012BA', 'Traje',3,7, '11/2/2017',true);
insert into B values(9, 'AB0001BA', 'Avion',4,1, '6/15/2010',true);