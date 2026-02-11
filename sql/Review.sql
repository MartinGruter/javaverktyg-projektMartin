CREATE TABLE review (
    id BIGSERIAL PRIMARY KEY,
    reviewerName VARCHAR(50) NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(80) NOT NULL,
    comment VARCHAR(500) NOT NULL,
    createdDate DATE NOT NULL

);