INSERT singer(date_of_birth, first_name, last_name, nickname)
VALUES ('1994-01-15', 'Justin', 'Bieber', null),
       ('1984-10-18', 'Curtis', 'Jackson', '50 Cent'),
       ('1992-02-19', 'Filip', 'Szcześniak', 'Taco'),
       ('1991-06-12', 'Jakub', 'Grabowski', 'Quebonafide');

INSERT INTO song(name, release_date)
VALUES ('Bubbletea', '2020-07-21'),
       ('Tamagotchi', '2018-05-11'),
       ('Lollipop ', '2000-01-12'),
       ('Baby', '2014-02-02'),
       ('Kryptowaluty', '2018-05-15'),
       ('6 zer', '2015-09-12');

INSERT INTO song_singers(id_song, id_singer)
VALUES (1, 4),
       (2, 3),
       (2, 4),
       (3, 2),
       (4, 1),
       (5, 3),
       (5, 4),
       (6, 3);
