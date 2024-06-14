# Delete

### DELETE statement

```sql
DELETE [TABLE 이름]
WHERE condition;
```

- OR 사용해서 삭제 
```sql
delete from customer
where CustomerId = 10 OR CustomerId = 9
```

- BETWEEN AND 사용해서 삭제
```sql
delete from customer
where CustomerId BETWEEN 8 AND 10;
```