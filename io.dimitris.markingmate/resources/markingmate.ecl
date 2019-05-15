rule Exam 
	match l : Left!Exam
	with r : Right!Exam {

	compare : true
}

rule Question 
	match l : Left!Question
	with r : Right!Question {

	compare : l.title = r.title
}

rule Answer 
	match l : Left!Answer
	with r : Right!Answer {

	compare : l.question.matches(r.question) 
		and l.student.matches(r.student)
}

rule Student 
	match l : Left!Student
	with r : Right!Student {

	compare : l.number = r.number
}