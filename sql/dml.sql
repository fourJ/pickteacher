INSERT INTO student values(STUDENT_SEQ.nextval, '�ѱ�', '26', '1996/11/59', 1, 'js0989', '������', '����', '010-8748-9878', '���~');
INSERT INTO student values(STUDENT_SEQ.nextval, '�߱�', '26', '1996/11/59', 0, 'js0989', '���ֿ�', '����', '010-8748-9878', '���~');
INSERT INTO student values(STUDENT_SEQ.nextval, '�Ϻ�', '26', '1996/11/59', 0, 'js0989', '������', '����', '010-8748-9878', '���~');
INSERT INTO student values(STUDENT_SEQ.nextval, '��Ʈ��', '27', '1995/11/17', 0, 'yj5989', '������', '����', '010-9878-9878', '���~');

INSERT INTO TEACHER VALUES (teacher_SEQ.nextval, '�ּ�1', '����', sysdate, 1, 'id1', '����1', '������1', '010-xxxx-xxxx', 'pw1', '�б�1');
INSERT INTO TEACHER VALUES (teacher_SEQ.nextval, '�ּ�2', '1��', sysdate, 1, 'id2', '����2', '������2', '010-xxxx-xxxx', 'pw2', '�б�2');
INSERT INTO TEACHER VALUES (teacher_SEQ.nextval, '�ּ�3', '2��', sysdate, 1, 'id3', '����3', '������3', '010-xxxx-xxxx', 'pw3', '�б�3');

INSERT INTO COURSE values(course_seq.nextval, '2021/11/11', 10, '2021/10/11', '��8��', '������', '����', '��1', 250000, '��������', '1');
INSERT INTO COURSE values(course_seq.nextval, '2021/11/12', 5, '2021/10/12', 'ȭ7��', '����', '����', '��2', 450000, '��������', '2');
INSERT INTO COURSE values(course_seq.nextval, '2021/11/13', 3, '2021/10/13', '��12��', '�̰���', '����', '��3', 350000, '�¶���', '3');

INSERT INTO review VALUES(review_seq.nextval, '���� ���� �ʹ� ����־��!', '2021/10/11', 5, 1, 1);
INSERT INTO review VALUES(review_seq.nextval, '���� �ϰ� ��� �� ����', '2021/10/11', 5, 1, 2);
INSERT INTO review VALUES(review_seq.nextval, '�ʹ� ������� �����ϱ� ����׿�', '2021/10/11', 1, 2, 2);
INSERT INTO review VALUES(review_seq.nextval, '���Ҹ�?', '2021/10/11', 1, 2, 1);
INSERT INTO review VALUES(review_seq.nextval, '��������', '2021/10/11', 3, 3, 3);
INSERT INTO review VALUES(review_seq.nextval, '����2', '2021/10/11', 3, 3, 1);

commit;