CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    fecha_publicacion DATE NOT NULL,
    categoria VARCHAR(100),
    isbn VARCHAR(20),
    valoracion INTEGER,
    visible BOOLEAN,
    precio NUMERIC(10, 2),
    stock INTEGER NOT NULL DEFAULT 0
);

INSERT INTO books (titulo, autor, fecha_publicacion, categoria, isbn, valoracion, visible, precio, stock)
VALUES
    ('Cien años de soledad', 'Gabriel García Márquez', '1967-05-30', 'Novela', '9780307474728', 5, true, 39.99, 10),
    ('El Principito', 'Antoine de Saint-Exupéry', '1943-04-06', 'Fábula', '9780156012195', 4, true, 25.50, 8),
    ('1984', 'George Orwell', '1949-06-08', 'Distopía', '9780451524935', 5, true, 29.99, 5),
    ('Fahrenheit 451', 'Ray Bradbury', '1953-10-19', 'Ciencia Ficción', '9781451673319', 4, false, 22.00, 7),
    ('Don Quijote de la Mancha', 'Miguel de Cervantes', '1605-01-16', 'Clásico', '9788491050217', 5, true, 34.75, 12);
