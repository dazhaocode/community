# community project by alan

## 来源学习B站

```sql
create table user
(
	id int auto_increment,
	account_id varchar(100),
	name varchar(50),
	token char(36),
	gmt_create bigint,
	gmt_modified bigint,
	constraint user_pk
		primary key (id)
);
```
