
create table mobile_member(
	idx int,
	name varchar2(255),
	id varchar2(255),
	pwd varchar2(255),
	device_token varchar2(255)
);

alter table mobile_member add constraint pk_mobile_member_idx primary key (idx);
alter table mobile_member add constraint uq_mobile_member_id unique (id);

insert into mobile_member values((select nvl(max(idx),0)+1 from mobile_member), 'test', 'test', 1111, null);

select * from mobile_member;