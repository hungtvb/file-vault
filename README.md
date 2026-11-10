# FileVault

Side project học Spring Boot: ứng dụng upload/quản lý file.

## Chạy Sprint 1

```bash
docker compose up -d                 # PostgreSQL
cd backend
export JWT_SECRET="doi-thanh-chuoi-ngau-nhien-it-nhat-32-ky-tu"   # tuỳ chọn khi dev
mvn test
mvn spring-boot:run
```

## Thử API

```bash
# Đăng ký
curl -i -X POST localhost:8080/api/v1/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"email":"a@example.com","password":"matkhau123"}'

# Đăng nhập
curl -s -X POST localhost:8080/api/v1/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"a@example.com","password":"matkhau123"}'

# Gọi API cần đăng nhập
curl -i localhost:8080/api/v1/me -H "Authorization: Bearer <accessToken>"
# Không có token -> 401
curl -i localhost:8080/api/v1/me
```

## Quy ước

- Mọi endpoint nằm dưới `/api/v1` (trừ `/actuator/health`).
- Lombok chỉ dùng `@Getter`, `@Setter`, `@NoArgsConstructor` trên entity.
  Không dùng `@Data`, `@EqualsAndHashCode`, `@ToString` tự sinh trên entity
  (id đổi khi lưu làm hỏng hashCode, và toString có thể kéo quan hệ lazy).
- IntelliJ: bật Settings → Build → Compiler → Annotation Processors → Enable annotation processing.
