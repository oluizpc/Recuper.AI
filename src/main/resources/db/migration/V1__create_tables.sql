CREATE TABLE public.shops (
  idshop serial4 NOT NULL,
  shop_domain varchar NOT NULL,
  access_token text NOT NULL,
  created_at timestamp DEFAULT now() NULL,
  updated_at timestamp DEFAULT now() NULL,
  active bool DEFAULT true NULL,

  CONSTRAINT "pk_idshop" PRIMARY KEY (idshop),
  CONSTRAINT "uk_shop_domain" UNIQUE (shop_domain)
);


CREATE TABLE public.customers (
  idcustomer serial4 NOT NULL,
  idshop int4 NOT NULL,
  shopify_customer_id int8 NULL,
  name varchar NULL,
  phone varchar NULL,
  email varchar NULL,

  created_at timestamp DEFAULT now() NULL,
  updated_at timestamp DEFAULT now() NULL,
  active bool DEFAULT true NULL,

  CONSTRAINT "pk_idcustomer" PRIMARY KEY (idcustomer),

  CONSTRAINT "fk_customer_shop" FOREIGN KEY (idshop)
    REFERENCES public.shops(idshop),

  CONSTRAINT "uk_customer_email" UNIQUE (idshop, email)
);

-- Unique parcial (evita duplicar customer Shopify quando existir)
CREATE UNIQUE INDEX IF NOT EXISTS uk_customer_shopify_notnull
ON public.customers (idshop, shopify_customer_id)
WHERE shopify_customer_id IS NOT NULL;


DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'checkout_status') THEN
    CREATE TYPE public.checkout_status AS ENUM (
      'OPEN',
      'ABANDONED',
      'CONTACTED',
      'CONVERTED',
      'LOST',
      'HUMAN_NEEDED'
    );
  END IF;
END $$;


CREATE TABLE public.checkouts (
  idcheckout serial4 NOT NULL,
  idshop int4 NOT NULL,
  idshopify_checkout int8 NOT NULL,

  idcustomer int4 NULL,

  status public.checkout_status DEFAULT 'OPEN' NULL,
  total_value numeric(12,2) NULL,
  currency varchar(10) NULL,
  recovery_url text NULL,

  last_event_at timestamp DEFAULT now() NULL,
  abandoned_at timestamp NULL,

  created_at timestamp DEFAULT now() NULL,
  updated_at timestamp DEFAULT now() NULL,
  active bool DEFAULT true NULL,

  CONSTRAINT "pk_idcheckout" PRIMARY KEY (idcheckout),

  CONSTRAINT "fk_checkout_shop" FOREIGN KEY (idshop)
    REFERENCES public.shops(idshop),

  CONSTRAINT "fk_checkout_customer" FOREIGN KEY (idcustomer)
    REFERENCES public.customers(idcustomer),

  CONSTRAINT "uk_checkout_shopify" UNIQUE (idshop, idshopify_checkout)
);

CREATE INDEX IF NOT EXISTS idx_checkouts_shop_status
ON public.checkouts (idshop, status);

CREATE INDEX IF NOT EXISTS idx_checkouts_last_event
ON public.checkouts (last_event_at);


CREATE TABLE public.checkout_items (
  idcheckout_item serial4 NOT NULL,
  idcheckout int4 NOT NULL,
  product_name varchar NOT NULL,
  variant_name varchar NOT NULL,
  quantity int4 NOT NULL DEFAULT 1,
  unit_price numeric(12,2) NULL,

  created_at timestamp DEFAULT now() NULL,
  updated_at timestamp DEFAULT now() NULL,

  CONSTRAINT "pk_idcheckout_item" PRIMARY KEY (idcheckout_item),

  CONSTRAINT "fk_checkout_item_checkout" FOREIGN KEY (idcheckout)
    REFERENCES public.checkouts (idcheckout)
    ON DELETE CASCADE
);


CREATE TABLE public.messages (
  idmessage serial4 NOT NULL,
  idshop int4 NOT NULL,
  idcheckout int4 NOT NULL,

  direction varchar(10) NOT NULL,     -- IN / OUT
  channel varchar(30) DEFAULT 'WHATSAPP' NULL,
  content text NOT NULL,

  idprovider_message varchar NULL,
  send_status varchar NULL,
  created_at timestamp DEFAULT now() NULL,

  CONSTRAINT "pk_idmessage" PRIMARY KEY (idmessage),

  CONSTRAINT "fk_message_shop" FOREIGN KEY (idshop)
    REFERENCES public.shops (idshop),

  CONSTRAINT "fk_message_checkout" FOREIGN KEY (idcheckout)
    REFERENCES public.checkouts (idcheckout),

  CONSTRAINT "ck_message_direction" CHECK (direction IN ('IN', 'OUT'))
);

CREATE INDEX IF NOT EXISTS idx_messages_checkout
ON public.messages (idcheckout);


CREATE TABLE public.orders (
  idorder serial4 NOT NULL,
  idshop int4 NOT NULL,

  idshopify_order int8 NOT NULL,
  idcheckout int4 NULL,

  total_value numeric(12,2) NULL,
  currency varchar(10) NULL,
  created_at timestamp DEFAULT now() NULL,

  CONSTRAINT "pk_idorder" PRIMARY KEY (idorder),

  CONSTRAINT "fk_order_shop" FOREIGN KEY (idshop)
    REFERENCES public.shops (idshop),

  CONSTRAINT "fk_order_checkout" FOREIGN KEY (idcheckout)
    REFERENCES public.checkouts (idcheckout),

  CONSTRAINT "uk_order_shopify" UNIQUE (idshop, idshopify_order)
);

CREATE INDEX IF NOT EXISTS idx_orders_checkout
ON public.orders (idcheckout);