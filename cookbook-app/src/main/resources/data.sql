-- Users

insert into user (id, dtype, username, password) values
    (1, 'Cook', 'gipszjakab', 'password'),
    (2, 'Cook', 'palkata', '123456');

create table authorities (
    username varchar(256) references user(username),
    authority varchar(256)
);

insert into authorities values
    ('gipszjakab', 'USERS'),
    ('palkata', 'USERS');

-- Recipes

insert into recipe (id, name, preparation, servings, uploader_id) values (
    1,
    'Pea soup',
    'Heat the oil in a saucepan over a medium heat. Add the onion and garlic and fry for 34 minutes, until softened.
Add the frozen peas and chicken or vegetable stock and bring to the boil. Reduce the heat and simmer for ten minutes.
Add the cream and use a hand blender to liquidise the soup.
Season, to taste and serve in a warm bowl, garnished with a mint leaf.',
    2,
    1
);

insert into recipe_category (recipe_id, category_name) values
    (1, 'SALTY'),
    (1, 'SOUP');

insert into recipe_ingredients (recipe_id, amount, unit, name) values
    (1, 15.0, 'MILLILITER', 'olive'),
    (1, 1.0, 'PIECE', 'garlic'),
    (1, 0.5, 'PIECE', 'onion'),
    (1, 200.0, 'GRAM', 'peas'),
    (1, 300.0, 'GRAM', 'chicken'),
    (1, 50.0, 'MILLILITER', 'cream');

insert into recipe (id, name, preparation, servings, uploader_id) values (
    2,
    'French onion soup',
    'In a 5 to 6 quart thick-bottomed pot, heat 3 tablespoons of olive oil on medium heat. Add the onions and toss to coat with the olive oil.
Add the stock, bay leaves, and thyme. Bring to a simmer, cover the pot and lower the heat to maintain a low simmer. Cook for about 30 minutes.
Brush both sides of the French bread or baguette slices lightly with olive oil (you''ll end up using about a tablespoon and a half of olive oil for this).
To serve, ladle soup into a bowl and transfer one cheesy toast onto the top of each bowl of soup.',
    4,
    2
);

insert into recipe_category (recipe_id, category_name) values
    (2, 'SALTY'),
    (2, 'SOUP');

insert into recipe_ingredients (recipe_id, amount, unit, name) values
    (2, 30.0, 'MILLILITER', 'olive'),
    (2, 6.0, 'PIECE', 'onion'),
    (2, 100.0, 'GRAM', 'butter'),
    (2, 20.0, 'GRAM', 'sugar'),
    (2, 2.0, 'PIECE', 'bay leave'),
    (2, 8.0, 'PIECE', 'french bread slice'),
    (2, 0.5, 'KILOGRAM', 'beef');

-- Comments

insert into comment (id, recipe_id, owner_id, timestamp, description) values
    (1, 1, 2, '2021-01-08T15:30:39.881330200', 'Very yummy.');
