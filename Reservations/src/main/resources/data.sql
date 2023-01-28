-- Skripta koja se pokrece automatski pri pokretanju aplikacije
-- Gasenjem aplikacije, brisu se svi podaci

INSERT INTO uloga(id,ime)
VALUES
(0,'Admin'),
(1,'Klijent'),
(2,'VikendicaVlasnik'),
(3,'BrodVlasnik'),
(4,'Instruktor');

insert into globalne_varijable(id,ime,vrednost)
values 
(1,'procenat','0.3');

insert into korisnik(id,adresa,broj_tel,drzava,email,grad,ime,korisnicko_ime,lozinka,prezime,uloga_id,enabled,link_slike)
values 
(nextval('korisnik_id_seq'),'c2','0123456789','srb','t.stojanovic8232@gmail.com','ns','tea','tea','123','st',0,true,'/img/avatar.png'),
(nextval('korisnik_id_seq'),'bpp','0123456789','srb','igorpavlov106@gmail.com','ns','igor','igor','123','pav',0,true,'/img/avatar.png'),
(nextval('korisnik_id_seq'),'a2','0123456789','srb','teateodora2000@gmail.com','bg','pera','pera','111','per',1,true,'/img/avatar.png'),
(nextval('korisnik_id_seq'),'b3','0123456789','srb','t.stojanovic8232@gmail.com','ns','mika','mika','222','mik',2,true,'/img/korisnici/71139c1b09af4038da2ad50eb0bf2ace.jpg'),
(nextval('korisnik_id_seq'),'c5','0123456789','srb','teateodora2000@gmail.com','za','seka','seka','000','sek',3,true,'/img/korisnici/Ana24Prag.jpg'),
(nextval('korisnik_id_seq'),'d15','0123456789','srb','teateodora2000@gmail.com','nis','rajko','rajko','999','raj',4,true,'/img/avatar.png'),
(nextval('korisnik_id_seq'),'a2','0123456789','srb','teateodora2000@gmail.com','bg','ivana','ivana','iva','iva',1,true,'/img/avatar.png'),
(nextval('korisnik_id_seq'),'c2','0123456789','srb','t.stojanovic8232@gmail.com','ns','Teodora','tstojanovic','12345','S',2,true,'/img/avatar.png'),
(nextval('korisnik_id_seq'),'b3','0123456789','srb','t.stojanovic8232@gmail.com','ns','branko','perisic','222','perisic',4,true,',/img/korisnici/perisic3.jpg'),
(nextval('korisnik_id_seq'),'c5','0123456789','srb','teateodora2000@gmail.com','za','dana','dana','aaa','dan',3,true,'/img/avatar.png'),
(nextval('korisnik_id_seq'),'a15','0123456789','srb','teateodora2000@gmail.com','nis','bora','bora','987','bor',2,true,'/img/avatar.png'),
(nextval('korisnik_id_seq'),'Cerevicka 0','0987654321','Srbija','teateodora2000@gmail.com','Novi Sad','Teodora','teodora','000','Stojanovic',0,true,'/img/avatar.png'),
(nextval('korisnik_id_seq'),'Cerevicka 0','0987654321','Srbija','mrs.isa.test@gmail.com','Novi Sad','Teodora','tea123','789','Stojanovic',0,true,'/img/avatar.png');



insert into usluge(id,adresa,biografija_instruktora,cena,max_osoba,naziv,opis,pecaroska_oprema,tip,instruktor_id,link_slike)
values 
(nextval('usluge_id_seq'),'d 3','Rajko zivi u Nisu i peca na Nisavi od svoje 20. godine. Sad ima 47 godina, i iskusan je pecaros.',210,5,'Kurs za pocetnike','Najosnovnije tehnike pecanja.','2 stapa za pecanje',1,5,'/img/pecanje1.jpg'),
(nextval('usluge_id_seq'),'p5','Rajko zivi u Nisu i peca na Nisavi od svoje 20. godine. Sad ima 47 godina, i iskusan je pecaros.',350,10,'Avantura na Nisavi','Uzivajte u pecanju uz najlepse vidike na Nisavi.','',0,5,'/img/pecanje2.jpg'),
(nextval('usluge_id_seq'),'p5','Kada ne predaje, Perisic uziva u pecanju na Dunavu ili Savi',500,5,'Kurs za sve na Dunavu','Naucite da pecate kao profesionalac na dunavu.','',1,8,'/img/pecanje4.jpg'),
(nextval('usluge_id_seq'),'d 3','Kada ne predaje, Perisic uziva u pecanju na Dunavu ili Savi',750,10,'Avantura na Savi','Upustite se u avanturu na Savi i pecajte sa profesionalcem.','',0,8,'/img/pecanje5.jpg');

insert into vikendica(id,adresa,broj_kreveta,broj_soba,cena,dodatne_usluge,link_interijera,link_slike,naziv,opis,pravila_ponasanja,vlasnik_id)
values 
(nextval('vikendica_id_seq'),'p3',2,2,450,'','/img/vikendice/enterijer01.jpg','/img/vikendice/vikendica1.jpg','Mikin bungalov','Bungalov na brdu nadgledajuci jezero','',3),
(nextval('vikendica_id_seq'),'d3',1,3,500,'','/img/vikendice/enterijer02.jpg','/img/vikendice/vikendica2.jpg','Vikendica - Petrovac na Mlavi','Vikendica u Petrovcu na Mlavi','',3),
(nextval('vikendica_id_seq'),'d3',1,3,500,'','/img/vikendice/enterijer03.jpg','/img/vikendice/vikendica4.jpg','Nikolina koliba','Idilicna koliba u sumi','',3),
(nextval('vikendica_id_seq'),'p3',2,2,450,'','/img/vikendice/enterijer04.jpg','/img/vikendice/vikendica5.jpg','Vikendica u Vrdniku','Vikendica u Vrdniku','',10),
(nextval('vikendica_id_seq'),'d3',1,3,500,'','/img/vikendice/enterijer05.jpg','/img/vikendice/vikendica6.jpg','Vikendica - Backi Petrovac','Vikendica u Backom Petrovcu','',3),
(nextval('vikendica_id_seq'),'adresa B33',1,2,200,'dodatne usluge','/img/vikendice/enterijer06.jpg','/img/vik2.jpg','Milanovačka koliba','Koliba u Milanovcu','pravilo 123',10);

insert into brodovi(id,adresa,broj_motora,cena,duzina,kapacitet,max_brzina,navigaciona_oprema,naziv,opis,pecaroska_oprema,snaga,tip,vlasnik_id,link_slike,link_kabine,pravila_ponasanja)
values
(nextval('brodovi_id_seq'),'t2','0',240,5,'4',20,'','SS Camcic','Mali camac kojim mozete veslati','',0,'camac',4,'/img/brod1.jpg','/img/brodovi/kabina1.jpg',''),
(nextval('brodovi_id_seq'),'l9','1',490,15,'7',50,'GPS uredjaj','SS Sekic','Recni brod za plovljenje','',90,'recni brod',9,'/img/brod2.jpg','/img/brodovi/kabina2.jpg',''),
(nextval('brodovi_id_seq'),'Nis','4',240,5,'4',20,'','BSS Dusan','Mali camac kojim mozete veslati','',0,'speed boat',4,'/img/brod3.jpg','/img/brodovi/kabina3.jpg',''),
(nextval('brodovi_id_seq'),'Becej','2',490,15,'7',50,'GPS uredjaj','OSS brod','Recni brod za plovljenje','',90,'jezerski brod',4,'/img/brod4.jpg','/img/brodovi/kabina4.jpg',''),
(nextval('brodovi_id_seq'),'Kovilj','0',240,5,'4',20,'','USS Caca','Mali camac kojim mozete veslati','',0,'camac',9,'/img/brod5.jpg','/img/brodovi/kabina5.jpg',''),
(nextval('brodovi_id_seq'),'Manjaca','1',490,15,'7',50,'GPS uredjaj','DSS Jovan','Recni brod za plovljenje','',90,'recni brod',4,'/img/brod6.jpg','/img/brodovi/kabina6.jpg','');



insert into rezervacije(id,cena,datum,entitet_id,izvjestaj,max_osoba,naziv_entiteta,tip,tip_entiteta,trajanje,vreme,klijent_id,akcija)
values
(nextval('rezervacije_id_seq'),200,'06/05/2022',0,'aaaaaaaaaaaaa',2,'Kurs za pocetnike',0,2,3,'17:30',2,1),
(nextval('rezervacije_id_seq'),200,'06/05/2022',0, NULL,3,'Mikin bungalov',0,0,2,'15:30',2,1),
(nextval('rezervacije_id_seq'),200,'06/25/2022',0, NULL,4,'SS Camcic',0,1,1,'12:30',2,1),
(nextval('rezervacije_id_seq'),200,'06/29/2022',1, NULL,2,'Avantura na Nisavi',1,2,2,'12:35',NULL,0.75),
(nextval('rezervacije_id_seq'),500,'06/01/2022',1,NULL,3,'Vikendica - Petrovac na Mlavi',0,0,2,'13:30',6,1);

insert into termin(id,datum_vreme_kraj,datum_vreme_pocetak,tip_entiteta,vlasnik_id,brod_id,usluga_id,vikendica_id)
values
(nextval('termin_id_seq'),'06/08/2022','06/05/2022',2,5,NULL,1,NULL),
(nextval('termin_id_seq'),'06/07/2022','06/05/2022',0,3,NULL,NULL,2),
(nextval('termin_id_seq'),'06/26/2022','06/25/2022',1,4,1,NULL,NULL),
(nextval('termin_id_seq'),'07/01/2022','06/29/2022',2,5,NULL,1,NULL),
(nextval('termin_id_seq'),'06/03/2022','06/01/2022',0,3,NULL,NULL,1),
(nextval('termin_id_seq'),'06/12/2022','06/09/2022',2,5,NULL,2,NULL),
(nextval('termin_id_seq'),'07/17/2022','07/15/2022',1,4,1,NULL,NULL);


update termin set rezervacija_id=5 where id=1;
update rezervacije set termin_id=1 where id=5;

update termin set rezervacija_id=1 where id=2;
update rezervacije set termin_id=2 where id=1;

update termin set rezervacija_id=2 where id=3;
update rezervacije set termin_id=3 where id=2;

update termin set rezervacija_id=4 where id=4;
update rezervacije set termin_id=4 where id=4;

update termin set rezervacija_id=3 where id=5;
update rezervacije set termin_id=5 where id=3;

insert into zalba(id,naziv_klijenta,zalba,rez_id,odgovor)
values
(nextval('zalba_id_seq'),'pera','bbbbbbbb',1,'aaaaaaaaaaaaaa'),
(nextval('zalba_id_seq'),'iva','aaaaaaaaa',3,NULL);


insert into pretplate_brod(brod_id,klijent_id)
values
(2,6),
(1,2);

insert into pretplate_vikendica(vikendica_id,klijent_id)
values
(3,6),
(6,2);

insert into pretplate_usluga(usluga_id,klijent_id)
values
(1,6),
(3,2);


insert into vikendica_termini_zauzetosti(vikendica_id,termini_zauzetosti_id)
values
(1,5),
(2,2);

insert into brodovi_termini_zauzetosti(brod_id,termini_zauzetosti_id)
values
(2,3),
(1,7);

insert into usluge_termini_zauzetosti(usluga_id,termini_zauzetosti_id)
values
(2,1),
(3,6),
(1,4);
