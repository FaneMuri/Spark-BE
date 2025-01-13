-- !important do not change the version file once it is created
-- !important name : V1 __ use 2 * __
-- ! if you want to alter a table, please create a new file.sql where you alter it
-- ! try to be consistent with the version number :) V uppercase , number

-- Create table for users
CREATE TABLE Userr (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        role VARCHAR(255) NOT NULL,
                        username VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        fullname VARCHAR(255) NOT NULL
);

-- Create table for tokens
CREATE TABLE Token (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        token_type VARCHAR(255) NOT NULL,
                        revoked BOOLEAN NOT NULL,
                        expired BOOLEAN NOT NULL,
                        user_id BIGINT NOT NULL REFERENCES Userr(id) ON DELETE CASCADE
);

-- Create table for events
CREATE TABLE Event (
                       id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       description VARCHAR(255) NOT NULL,
                       organizerid BIGINT NOT NULL REFERENCES Userr(id) ON DELETE CASCADE,
                       date TIMESTAMP NOT NULL,
                       participantcount BIGINT,
                       location VARCHAR(255) NOT NULL,
                       image BYTEA
);

-- Create table for posts
CREATE TABLE Post (
                      id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                      name VARCHAR(255) NOT NULL,
                      description VARCHAR(255) NOT NULL,
                      eventid BIGINT NOT NULL REFERENCES Event(id) ON DELETE CASCADE,
                      image BYTEA

);

-- Create table for comments
CREATE TABLE Comment (
                         id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         userid BIGINT NOT NULL REFERENCES Userr(id) ON DELETE CASCADE,
                         postid BIGINT NOT NULL REFERENCES Post(id) ON DELETE CASCADE,
                         message VARCHAR(255) NOT NULL
);
