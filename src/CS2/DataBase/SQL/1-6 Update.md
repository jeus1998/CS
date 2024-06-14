# Update

### UPDATE statement

```sql
UPDATE [TABLE 이름] SET 컬럼 = value;
WHERE condition;
```

### 예제 - 모든 튜플 이름에 '님' 붙이기

```sql
UPDATE CUSTOMER 
SET customerName = CONCAT(customerName , '님');
```
- CONCAT 함수를 사용해서 문자열 연결
- MySQL에서는 '+' 연산자는 숫자만 가능하다 

```sql
UPDATE CUSTOMER SET customerName = CONCAT(customerName , '님', '님');
```
- 파라미터 N개 가능 