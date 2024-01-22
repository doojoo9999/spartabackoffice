# API 명세서

## 멤버 관련 API

### 회원가입
- **Method**: `POST`
- **URI**: `/api/v1/signup`
- **Auth**: 없음
- **Request**: 
  ```json
  {
    "email": "String",
    "password": "String",
    "name": "String"
  }
  ```
- **Response**: 회원가입 성공 여부 반환
- **Status**: `204 No Content`

### 로그인
- **Method**: `POST`
- **URI**: `/api/v1/login`
- **Auth**: 없음
- **Request**:
  ```json
  {
    "email": "String",
    "password": "String"
  }
  ```
- **Response**: 로그인 성공 여부 및 정보 반환
- **Status**: `200 OK`

### 회원정보 조회
- **Method**: `GET`
- **URI**: `/api/v1/members/{memberId}`
- **Auth**: student, tutor, admin
- **Request**: 없음
- **Response**:
  ```json
  {
    "userId": "1",
    "email": "email",
    "name": "name"
  }
  ```
- **Status**: `200 OK`

### 회원정보 수정
- **Method**: `PUT`
- **URI**: `/api/v1/members/{memberId}`
- **Auth**: student, tutor, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "email": "String",
    "password": "String"
  }
  ```
- **Response**:
  ```json
  {
    "userId": "1",
    "email": "email"
  }
  ```
- **Status**: `200 OK`

### 회원 탈퇴
- **Method**: `DELETE`
- **URI**: `/api/v1/members/{memberId}/withdraw`
- **Auth**: student, tutor
- **Request**: 없음
- **Response**: 없음
- **Status**: `204 No Content`

## 게시글 관련 API

### 게시글 작성
- **Method**: `POST`
- **URI**: `/api/v1/posts`
- **Auth**: student, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "title": "String",
    "content": "String",
    "private": "Boolean"
  }
  ```
- **Response**:
  ```json
  {
    "userId": "1",
    "postId": "1",
    "title": "title",
    "content": "content",
    "private": "false"
  }
  ```
- **Status**: `201 Created`

### 게시글 수정
- **Method**: `PUT`
- **URI**: `/api/v1/posts/{postId}`
- **Auth**: student, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "title": "String",
    "content": "String",
    "private": "Boolean"
  }
  ```
- **Response**:
  ```json
  {
    "userId": "1",
    "postId": "1",
    "title": "title",
    "content": "content",
    "private": "false"
  }
  ```
- **Status**: `200 OK`

### 게시글 삭제
- **Method**: `DELETE`
- **URI**: `/api/v1/posts/{postId}`
- **Auth**: student, admin
- **Request**:
  ```json
  {
    "userId": "Long"
  }
  ```
- **Response**: 없음
- **Status**: `204 No Content`

### 게시글 해결 완료
- **Method**: `POST`
- **URI**: `/api/v1/posts/{postId}/complete`
- **Auth**: student, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "postId": "Long",
    "complete": "String"
  }
  ```
- **Response**:
  ```json
  {
    "userId": "1",
    "postId": "1",
    "complete": "true"
  }
  ```
- **Status**: `201 Created`

### 전체 글 조회
- **Method**: `GET`
- **URI**: `/api/v1/posts`
- **Auth**: 없음
- **Request**: 없음
- **Response**: 없음
- **Status**: `200 OK`

## 답글 관련 API

### 답글 작성
- **Method**: `POST`
- **URI**: `/api/v1/posts/{postId}/replies`
- **Auth**: tutor, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "postId": "Long",
    "complete": "String",
    "title": "String",
    "content": "String",
    "private": "Boolean"
  }
  ```
- **Response**:
  ```json
  {
    "postId": "2",
    "parentpostId": "1",
    "title": "title",
    "content": "content",
    "private": "false"
  }
  ```
- **Status**: `201 Created`

### 답글 수정
- **Method**: `PUT`
- **URI**: `/api/v1/posts/{postId}/replies/{repliesId}`
- **Auth**: tutor, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "title": "String",
    "content": "String"
  }
  ```
- **Response**:
  ```json
  {
    "userId": "1",
    "postId": "2",
    "parentpostId": "1",
    "title": "title",
    "content": "content",
    "private": "false"
  }
  ```
- **Status**: `200 OK`

### 답글 삭제
- **Method**: `DELETE`
- **URI**: `/api/v1/posts/{postId}/replies/{repliesId}`
- **Auth**: tutor, admin
- **Request**:
  ```json
  {
    "userId": "Long"
  }
  ```
- **Response**: 없음
- **Status**: `204 No Content`

## 과제 관련 API

### 과제 제출
- **Method**: `POST`
- **URI**: `/api/v1/homework`
- **Auth**: student, admin
- **Request**: 
  ```json
  {
    "userId": "Long",
    "title": "String",
    "content": "String"
  }
  ```
- **Response**:
  ```json
  {
    "userId": "1",
    "homeworkId": "1",
    "title": "title",
    "content": "content"
  }
  ```
- **Status**: `201 Created`

### 과제 완료 처리
- **Method**: `POST`
- **URI**: `/api/v1/homework/success`
- **Auth**: tutor, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "homeworkId": "Long",
    "complete": "String"
  }
  ```
- **Response**:
  ```json
  {
    "userId": "1",
    "homeworkId": "1",
    "complete": "complete"
  }
  ```
- **Status**: `201 Created`

### 과제 점수 등록
- **Method**: `POST`
- **URI**: `/api/v1/homework/grade`
- **Auth**: tutor, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "homeworkId": "Long",
    "complete": "String",
    "grade": "String"
  }
  ```
- **Response**:
  ```json
  {
    "userId": "1",
    "homeworkId": "1",
    "complete": "complete",
    "grade": "A"
  }
  ```
- **Status**: `201 Created`

## 댓글 관련 API

### 댓글 작성
- **Method**: `POST`
- **URI**: `/api/v1/comments/{memberId}/{postId}`
- **Auth**: student, tutor, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "postId": "Long",
    "content": "String"
  }
  ```
- **Response**:
  ```json
  {
    "userId": 1,
    "postId": 1,
    "content": "content"
  }
  ```
- **Status**: `201 Created`

### 댓글 수정
- **Method**: `PUT`
- **URI**: `/api/v1/comments/{commentId}`
- **Auth**: student, tutor, admin
- **Request**:
  ```json
  {
    "userId": "Long",
    "content": "String"
  }
  ```
- **Response**:
  ```json
  {
    "userId": 1,
    "content": "updated content"
  }
  ```
- **Status**: `200 OK`

### 댓글 삭제
- **Method**: `DELETE`
- **URI**: `/api/v1/comments/{commentId}`
- **Auth**: student, tutor, admin
- **Request**:
  ```json
  {
    "userId": "Long"
  }
  ```
- **Response**: 댓글 삭제 성공 여부 반환
- **Status**: `204 No Content`

## 조회 관련 API

### 회원 별 게시글 조회
- **Method**: `GET`
- **URI**: `/api/v1/admin/posts/{userId}`
- **Auth**: admin
- **Request**: 없음
- **Response**: 없음
- **Status**: `200 OK`

### 회원 별 댓글 조회
- **Method**: `GET`
- **URI**: `/api/v1/admin/comments/{userId}`
- **Auth**: admin
- **Request**: 없음
- **Response**: 없음
- **Status**: `200 OK`

### 전체 과제 조회
- **Method**: `GET`
- **URI**: `/api/v1/tutor/homework`
- **Auth**: tutor, admin
- **Request**: 없음
- **Response**: 없음
- **Status**: `200 OK`

### 회원 별 과제 조회
- **Method**: `GET`
- **URI**: `/api/v1/tutor/homework/{userId}`
- **Auth**: tutor, admin
- **Request**: 없음
- **Response**: 없음
- **Status**: `200 OK`

### 과제 단건 조회
- **Method**: `GET`
- **URI**: `/api/v1/tutor/homework/{homeworkId}`
- **Auth**: tutor, admin
- **Request**: 없음
- **Response**: 없음
- **Status**: `200 OK`

### 완료된 과제 조회
- **Method**: `GET`
- **URI**: `/api/v1/tutor/homework/complete`
- **Auth**: tutor, admin
- **Request**: 없음
- **Response**: 없음
- **Status**: `200 OK`

### 미해결 게시글 조회
- **Method**: `GET`
- **URI**: `/api/v1/tutor/post/complete`
- **Auth**: tutor, admin
- **Request**: 없음
- **Response**: 없음
- **Status**: `200 OK`
