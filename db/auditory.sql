create table agent_auditory(
  id serial primary key,
  agent varchar(20) references transit_agent (id) on delete set null,
  route integer id references transit_route (id) on delete set null,
  start_date datetime not null,
  end_date datetime not null
);

create or replace function trigger_audict_agent()
returns trigger
language plpgsql
as
$$
  declare
    agent_id transit_agent.id%type;
    route_id transit_route.id%type;
    auditory_id agent_auditory.id%type;
  begin
    if tg_op = 'INSERT' or tg_op = 'UPDATE' then
      select a.id
      into agent_id
      from transit_agent a
      where a.id = new.id;

      select r.id
      into route_id
      from transit_route r
      where r.id = new.transit_route;
    end if;

    if tg_op = 'INSERT' then 
      insert into agent_auditory (agent, route, start_date)
      values (agent_id, route_id, now());
      return new;
    end if;

    if tg_op = 'UPDATE' then

      select aa.id
      into auditory_id
      from agent_auditory aa
      where aa.agent = new.id and aa.route = new.transit_route;

      insert into agent_auditory (agent, route, start_date, end_date)
      values (new.id, new.transit_route)