create table route_type(
  id serial primary key,
  route_type varchar(100) unique not null
);

create table street_type(
  id serial primary key,
  type varchar(50) unique not null
);

create table transit_route(
  id serial primary key,
  route_type integer references route_type (id) on delete cascade,
  street_type integer references street_type (id) on delete cascade,
  number integer not null,
  con_level decimal(5, 2) not null
);

create table transit_agent(
  id varchar(20) primary key,
  full_name varchar(100) not null,
  experience_year decimal (5, 2),
  transit_code varchar,
  transit_route integer references transit_route (id) on delete set null
);

alter table transit_agent
add constraint unique_route_transit unique(transit_route); 