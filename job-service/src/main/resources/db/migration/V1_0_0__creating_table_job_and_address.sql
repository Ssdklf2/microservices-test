CREATE TABLE address
(
    address_id   UUID PRIMARY KEY NOT NULL,
    country      VARCHAR(50)      NOT NULL,
    city         VARCHAR(255)     NOT NULL,
    street_name  VARCHAR(255)     NOT NULL,
    house_number NUMERIC          NOT NULL
);

CREATE TABLE job
(
    job_id      UUID PRIMARY KEY NOT NULL,
    title       VARCHAR(50)      NOT NULL,
    description VARCHAR(255)     NOT NULL,
    salary      INTEGER          NOT NULL CHECK ( salary > 0 ),
    address_id  UUID REFERENCES address
);