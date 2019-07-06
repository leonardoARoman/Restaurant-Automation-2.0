CREATE TABLE IF NOT EXISTS menu
(
    category_number     INTEGER PRIMARY KEY,
    category_name       TEXT
);
CREATE TABLE IF NOT EXISTS appetizers
(
    number      INTEGER,
    plateNo     INTEGER,
    dish        TEXT,
    price       REAL,
    PRIMARY KEY(number,plateNo),
    FOREIGN KEY(number) REFERENCES menu(category_number)
);
CREATE TABLE IF NOT EXISTS specials
(
    number      INTEGER,
    plateNo     INTEGER,
    dish        TEXT,
    price       REAL,
    FOREIGN KEY(number) REFERENCES menu(category_number)
);
CREATE TABLE IF NOT EXISTS salads
(
    number      INTEGER,
    plateNo     INTEGER,
    salad       TEXT,
    price       REAL,
    FOREIGN KEY(number) REFERENCES menu(category_number)
);
CREATE TABLE IF NOT EXISTS desserts
(
    number      INTEGER,
    plateNo     INTEGER,
    dessert     TEXT,
    price       REAL,
    FOREIGN KEY(number) REFERENCES menu(category_number)
);
CREATE TABLE IF NOT EXISTS drinks
(
    number      INTEGER,
    drinkNo     INTEGER,
    drink       TEXT,
    price       REAL,
    FOREIGN KEY(number) REFERENCES menu(category_number)
);
INSERT INTO menu
(
    category_number,
    category_name
)
VALUES (1,"appetizers"),(2,"specials"),(3,"salads"),(4,"desserts"),(5,"drinks");
INSERT INTO appetizers
(
    number,
    plateNo,
    dish,
    price
)
VALUES (1,1,"Fried Calamari",12.99),(1,2,"French Fries",9.99),(1,3,"Curly Fries",9.99),
       (1,4,"Chicken Wings",10.99),(1,5,"Stuffed Mushrooms",12.99),(1,6,"Clam Casino",12.99);
INSERT INTO specials
(
    number,
    plateNo,
    dish,
    price
)
VALUES (2,1,"NY Steak",15.99),(2,2,"Rock Lobster Tail",19.99),(2,3,"Fancy Salmon",19.99),
       (2,4,"Ribeye Steak",17.99),(2,5,"Porter House",32.99),(2,6,"Family Pasta",22.99);
INSERT INTO salads
(
    number,
    plateNo,
    salad,
    price
)
VALUES (3,1,"Caesar Salad",12.99),(3,2,"Mexican Salad",9.99),(3,3,"Spinach Salad",9.99);
INSERT INTO desserts
(
    number,
    plateNo,
    dessert,
    price
)
VALUES (4,1,"Brownies",10.99),(4,2,"Cheese Cake",9.99),(4,3,"Tiramisu",9.99),(4,4,"Flan",10.99);
INSERT INTO drinks
(
    number,
    drinkNo,
    drink,
    price
)
VALUES (5,1,"Cooke",5.99),(5,2,"Juice",4.99),(5,3,"Beer",9.99),(5,4,"Wine",9.99);