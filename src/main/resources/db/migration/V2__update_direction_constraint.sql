ALTER TABLE messages DROP CONSTRAINT ck_message_direction;
ALTER TABLE messages ADD CONSTRAINT ck_message_direction 
    CHECK (direction IN ('INBOUND', 'OUTBOUND'));