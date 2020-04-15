Napomene:
Prilikom unosenja vrijednosti atributa amount(kada se mijenja ili kreira nova transakcija) nije dozvoljeno unositi negativne brojeve.
Znak vrijednosti amounta se odredjuje na osnovu tipa transakcije(za tipove koji su payment i purchase vrijednost se oduzima od ukupnog budzeta,
a za tipove income se dodaje)
Prilikom dodavanja nove transakcije, nakon klika na dugme Add transaction, otvara se forma na kojoj su neki podaci po defaultu popunjeni i trebaju
se promijeniti.

Druga spirala:
Umjesto swipe akcije su koristena dugmad. Grafici su napravljeni, ali iz nekog razloga nakon otvaranja GraphsFragment ne prikazu se odmah, nego se 
mora prvo scrollati misem. Mjesecni grafovi prikazuju mjesece tekuce godine. Sedmicni grafovi prikazuju sedmice tekuceg mjeseca. Racunaju se samo
cijele sedmice u mjesecu, tacnije datumi od 1. do 28. Dnevni grafovi prikazuju dane u tekucoj sedmici. Tekuca sedmica se pronalazi tako sto se krene
od 1. u tekucem mjesecu i trazi se sedmica u kojoj se nalazi trenutni datum. Datumi nakon 28. u mjesecu se prikazuju zajedno u jednom grafu(dakle,
ukupno je 5 mogucih grafika za sedmice u mjesecu). 