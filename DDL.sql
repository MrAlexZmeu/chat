
CREATE TABLE students
(
	id serial NOT NULL,
	first_name varchar(100) NOT NULL,
	surname varchar(100) NOT NULL,
	CONSTRAINT students_id PRIMARY KEY (id)
);


CREATE TABLE tests
(
	id serial NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT tests_id PRIMARY KEY (id)
);


CREATE TABLE questions
(
	id serial NOT NULL,
	name varchar(1000) NOT NULL,
	test_id serial NOT NULL,
	CONSTRAINT questions_id PRIMARY KEY (id),
	CONSTRAINT questions_fk_test FOREIGN KEY (test_id) REFERENCES tests(id)

);


CREATE TABLE answers
(
	id serial NOT NULL,
	name varchar(500) NOT NULL,
	question_id serial NOT NULL,
	CONSTRAINT answers_id PRIMARY KEY (id),
	CONSTRAINT answers_fk_questions FOREIGN KEY (question_id) REFERENCES questions(id)
);


CREATE TABLE pass_test (
	id serial NOT NULL,
	test_date date NULL,
	test_id serial NOT NULL,
	student_id serial NOT NULL,
	question_id serial NOT NULL,
	answer_id serial NOT NULL,
	CONSTRAINT passing_key PRIMARY KEY (id),
	CONSTRAINT passing_fk_test_id FOREIGN KEY (test_id) REFERENCES tests(id),
	CONSTRAINT passing_fk_student_id FOREIGN KEY (student_id) REFERENCES students(id),
	CONSTRAINT passing_fk_question_id FOREIGN KEY (question_id) REFERENCES questions(id),
	CONSTRAINT passing_fk_answer_id FOREIGN KEY (answer_id) REFERENCES answers(id)

);