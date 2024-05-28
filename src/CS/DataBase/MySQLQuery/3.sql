
Select1 에서 사용한 querys

select name, position from employee where id = 9;

select * from employee;

select * from project;

insert into project values(1, 'web', 3, '2024-05-27', '2024-05-28');
insert into project values(2002, 'web_security', 14, '2024-05-27', '2024-05-28');

select employee.id, employee.name, position
from project, employee
where project.id = 2002 and project.leader_id = employee.id;

select e.id, e.name, e.position
from project AS p, employee AS e
where p.id = 2002 and p.leader_id = e.id;

select e.id as leader_id, e.name as leader_name , e.position as leader_position
from project AS p, employee AS e
where p.id = 2002 and p.leader_id = e.id;


insert into project values(2010, 'web dsgn', 2, '2024-05-27', '2024-05-28');
insert into project values(2011, 'web dsgn2', 7, '2024-05-27', '2024-05-28');

insert into works_on values(2, 2010);
insert into works_on values(7, 2011);
