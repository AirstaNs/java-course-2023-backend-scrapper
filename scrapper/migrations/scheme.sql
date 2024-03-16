--liquibase formatted sql

--changeset AirstaNs:1
CREATE TABLE IF NOT EXISTS chat
(
    chat_id BIGINT GENERATED ALWAYS AS IDENTITY,
    PRIMARY KEY (chat_id)
);

CREATE TABLE IF NOT EXISTS link
(
    link_id         BIGINT GENERATED ALWAYS AS IDENTITY,
    url             TEXT UNIQUE NOT NULL,
    description     TEXT,
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_checked_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (link_id)
);

CREATE TABLE IF NOT EXISTS chat_link
(
    chat_id BIGINT,
    link_id BIGINT,
    PRIMARY KEY (chat_id, link_id),
    FOREIGN KEY (chat_id) REFERENCES chat (chat_id),
    FOREIGN KEY (link_id) REFERENCES link (link_id)
);

--rollback drop table chat, link, chat_link;
