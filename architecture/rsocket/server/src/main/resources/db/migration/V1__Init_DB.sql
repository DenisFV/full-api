CREATE TABLE public.test_table
(
  test_id BIGINT NOT NULL,
  value   TEXT,
  PRIMARY KEY (test_id)
);

CREATE TABLE public.test_second_table
(
  test_second_id TEXT NOT NULL,
  value          TEXT,
  PRIMARY KEY (test_second_id)
);

Create sequence if not exists public.test_id_seq START 1;
Alter table public.test_table
  Alter column test_id set default nextval('public.test_id_seq');
