CREATE TABLE voters (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    has_voted BOOLEAN NOT NULL DEFAULT false,
    CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

CREATE TABLE candidates (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
    party VARCHAR(150),
    votes INT NOT NULL DEFAULT 0,
	CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

CREATE TABLE vote (
    id SERIAL PRIMARY KEY,
    voter_id INT NOT NULL,
    candidate_id INT NOT NULL,
    CONSTRAINT fk_voter FOREIGN KEY (voter_id) REFERENCES voters(id),
    CONSTRAINT fk_candidate FOREIGN KEY (candidate_id) REFERENCES candidates(id),
    CONSTRAINT unique_voter_vote UNIQUE (voter_id)
);

INSERT INTO voters (name, email) VALUES
('Carlos Mendoza', 'carlos.mendoza@example.com'),
('Lucía Ramírez', 'lucia.ramirez@example.com'),
('Pedro Torres', 'pedro.torres@example.com'),
('Ana Salas', 'ana.salas@example.com');

INSERT INTO candidates (name, email, party) VALUES
('Laura López', 'laura.lopez@futuroverde.org', 'Futuro Verde'),
('Daniel Ortega', 'daniel.ortega@avanceciudadano.org', 'Avance Ciudadano'),
('Sofía Herrera', 'sofia.herrera@unionlibre.org', 'Unión Libre');

INSERT INTO vote (voter_id, candidate_id) VALUES (1, 1);
UPDATE voters SET has_voted = true WHERE id = 1;
UPDATE candidates SET votes = votes + 1 WHERE id = 1;

INSERT INTO vote (voter_id, candidate_id) VALUES (2, 2);
UPDATE voters SET has_voted = true WHERE id = 2;
UPDATE candidates SET votes = votes + 1 WHERE id = 2;

INSERT INTO vote (voter_id, candidate_id) VALUES (3, 1);
UPDATE voters SET has_voted = true WHERE id = 3;
UPDATE candidates SET votes = votes + 1 WHERE id = 1;
