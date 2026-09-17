CREATE TABLE auction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_id UUID NOT NULL,
    user_id UUID NOT NULL,
    current_bid_id UUID,
    current_amount NUMERIC(19, 2),
    start_date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT chk_auction_state CHECK (status IN ('PENDING', 'ACTIVE', 'CLOSED'))
);
