INSERT INTO student values(STUDENT_SEQ.nextval, '한국', 26, '1996/11/01', 1, 'js0989', '김지명', '수지', '010-8748-9878', '비밀~');
INSERT INTO student values(STUDENT_SEQ.nextval, '중국', 26, '1996/11/02', 0, 'jy0989', '서주연', '수지', '010-8748-9878', '비밀~');
INSERT INTO student values(STUDENT_SEQ.nextval, '일본', 26, '1996/11/03', 0, 'jees0989', '배지수', '수지', '010-8748-9878', '비밀~');
INSERT INTO student values(STUDENT_SEQ.nextval, '베트남', 27, '1995/11/04', 0, 'yj5989', '마유진', '유진', '010-9878-9878', '비밀~');

INSERT INTO TEACHER VALUES (teacher_SEQ.nextval, '주소1', '신입', sysdate, 1, 'id1', '전공1', '선생님1', '010-xxxx-xxxx', 'pw1', '학교1');
INSERT INTO TEACHER VALUES (teacher_SEQ.nextval, '주소2', '1년', sysdate, 1, 'id2', '전공2', '선생님2', '010-xxxx-xxxx', 'pw2', '학교2');
INSERT INTO TEACHER VALUES (teacher_SEQ.nextval, '주소3', '2년', sysdate, 1, 'id3', '전공3', '선생님3', '010-xxxx-xxxx', 'pw3', '학교3');

INSERT INTO COURSE values(course_seq.nextval, '2021/11/11', 10, '2021/10/11', '월8시', '진행중', '국어', '고1', '고1국어', 250000, '오프라인', 1);
INSERT INTO COURSE values(course_seq.nextval, '2021/11/12', 5, '2021/10/12', '화7시', '마감', '수학', '고2', '고2수학', 450000, '오프라인', 2);
INSERT INTO COURSE values(course_seq.nextval, '2021/11/13', 3, '2021/10/13', '토12시', '미개강', '영어', '고3', '고3영어', 350000, '온라인', 3);

INSERT INTO review VALUES(review_seq.nextval, '강의 쉽고 너무 재미있어요!', '2021/10/11', 5, 1, 1);
INSERT INTO review VALUES(review_seq.nextval, '역시 믿고 듣는 쌤 강의', '2021/10/11', 5, 1, 2);
INSERT INTO review VALUES(review_seq.nextval, '너무 어려워서 이해하기 힘드네여', '2021/10/11', 1, 2, 2);
INSERT INTO review VALUES(review_seq.nextval, '뭔소리?', '2021/10/11', 1, 2, 1);
INSERT INTO review VALUES(review_seq.nextval, 'ㄹㅇㅋㅋ', '2021/10/11', 3, 3, 3);
INSERT INTO review VALUES(review_seq.nextval, 'ㅎㅋ2', '2021/10/11', 3, 3, 1);

INSERT INTO "CATALOG" VALUES(catalog_seq.nextval, '2021/10/11', 1, 3);
INSERT INTO "CATALOG" VALUES(catalog_seq.nextval, '2021/10/12', 2, 2);
INSERT INTO "CATALOG" VALUES(catalog_seq.nextval, '2021/10/13', 3, 1);

COMMIT;
