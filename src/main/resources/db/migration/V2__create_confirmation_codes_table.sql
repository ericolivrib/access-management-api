CREATE TABLE IF NOT EXISTS confirmation_codes (
    code_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id uuid REFERENCES users (user_id),
    expires_in timestamp,
    confirmed boolean
);