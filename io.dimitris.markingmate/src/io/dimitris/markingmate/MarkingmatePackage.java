/**
 */
package io.dimitris.markingmate;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see io.dimitris.markingmate.MarkingmateFactory
 * @model kind="package"
 * @generated
 */
public interface MarkingmatePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "markingmate";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "markingmate";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MarkingmatePackage eINSTANCE = io.dimitris.markingmate.impl.MarkingmatePackageImpl.init();

	/**
	 * The meta object id for the '{@link io.dimitris.markingmate.impl.ExamImpl <em>Exam</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.dimitris.markingmate.impl.ExamImpl
	 * @see io.dimitris.markingmate.impl.MarkingmatePackageImpl#getExam()
	 * @generated
	 */
	int EXAM = 0;

	/**
	 * The feature id for the '<em><b>Questions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__QUESTIONS = 0;

	/**
	 * The feature id for the '<em><b>Students</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM__STUDENTS = 1;

	/**
	 * The number of structural features of the '<em>Exam</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Exam</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXAM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.dimitris.markingmate.impl.QuestionImpl <em>Question</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.dimitris.markingmate.impl.QuestionImpl
	 * @see io.dimitris.markingmate.impl.MarkingmatePackageImpl#getQuestion()
	 * @generated
	 */
	int QUESTION = 1;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__TITLE = 0;

	/**
	 * The feature id for the '<em><b>Marks</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__MARKS = 1;

	/**
	 * The feature id for the '<em><b>Answers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION__ANSWERS = 2;

	/**
	 * The number of structural features of the '<em>Question</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Question</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUESTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.dimitris.markingmate.impl.StudentImpl <em>Student</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.dimitris.markingmate.impl.StudentImpl
	 * @see io.dimitris.markingmate.impl.MarkingmatePackageImpl#getStudent()
	 * @generated
	 */
	int STUDENT = 2;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT__NUMBER = 0;

	/**
	 * The feature id for the '<em><b>Answers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT__ANSWERS = 1;

	/**
	 * The number of structural features of the '<em>Student</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Student</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STUDENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link io.dimitris.markingmate.impl.AnswerImpl <em>Answer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see io.dimitris.markingmate.impl.AnswerImpl
	 * @see io.dimitris.markingmate.impl.MarkingmatePackageImpl#getAnswer()
	 * @generated
	 */
	int ANSWER = 3;

	/**
	 * The feature id for the '<em><b>Student</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANSWER__STUDENT = 0;

	/**
	 * The feature id for the '<em><b>Question</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANSWER__QUESTION = 1;

	/**
	 * The feature id for the '<em><b>Feedback</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANSWER__FEEDBACK = 2;

	/**
	 * The feature id for the '<em><b>Marks</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANSWER__MARKS = 3;

	/**
	 * The number of structural features of the '<em>Answer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANSWER_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Answer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANSWER_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link io.dimitris.markingmate.Exam <em>Exam</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exam</em>'.
	 * @see io.dimitris.markingmate.Exam
	 * @generated
	 */
	EClass getExam();

	/**
	 * Returns the meta object for the containment reference list '{@link io.dimitris.markingmate.Exam#getQuestions <em>Questions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Questions</em>'.
	 * @see io.dimitris.markingmate.Exam#getQuestions()
	 * @see #getExam()
	 * @generated
	 */
	EReference getExam_Questions();

	/**
	 * Returns the meta object for the containment reference list '{@link io.dimitris.markingmate.Exam#getStudents <em>Students</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Students</em>'.
	 * @see io.dimitris.markingmate.Exam#getStudents()
	 * @see #getExam()
	 * @generated
	 */
	EReference getExam_Students();

	/**
	 * Returns the meta object for class '{@link io.dimitris.markingmate.Question <em>Question</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Question</em>'.
	 * @see io.dimitris.markingmate.Question
	 * @generated
	 */
	EClass getQuestion();

	/**
	 * Returns the meta object for the attribute '{@link io.dimitris.markingmate.Question#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see io.dimitris.markingmate.Question#getTitle()
	 * @see #getQuestion()
	 * @generated
	 */
	EAttribute getQuestion_Title();

	/**
	 * Returns the meta object for the attribute '{@link io.dimitris.markingmate.Question#getMarks <em>Marks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Marks</em>'.
	 * @see io.dimitris.markingmate.Question#getMarks()
	 * @see #getQuestion()
	 * @generated
	 */
	EAttribute getQuestion_Marks();

	/**
	 * Returns the meta object for the reference list '{@link io.dimitris.markingmate.Question#getAnswers <em>Answers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Answers</em>'.
	 * @see io.dimitris.markingmate.Question#getAnswers()
	 * @see #getQuestion()
	 * @generated
	 */
	EReference getQuestion_Answers();

	/**
	 * Returns the meta object for class '{@link io.dimitris.markingmate.Student <em>Student</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Student</em>'.
	 * @see io.dimitris.markingmate.Student
	 * @generated
	 */
	EClass getStudent();

	/**
	 * Returns the meta object for the attribute '{@link io.dimitris.markingmate.Student#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see io.dimitris.markingmate.Student#getNumber()
	 * @see #getStudent()
	 * @generated
	 */
	EAttribute getStudent_Number();

	/**
	 * Returns the meta object for the containment reference list '{@link io.dimitris.markingmate.Student#getAnswers <em>Answers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Answers</em>'.
	 * @see io.dimitris.markingmate.Student#getAnswers()
	 * @see #getStudent()
	 * @generated
	 */
	EReference getStudent_Answers();

	/**
	 * Returns the meta object for class '{@link io.dimitris.markingmate.Answer <em>Answer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Answer</em>'.
	 * @see io.dimitris.markingmate.Answer
	 * @generated
	 */
	EClass getAnswer();

	/**
	 * Returns the meta object for the container reference '{@link io.dimitris.markingmate.Answer#getStudent <em>Student</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Student</em>'.
	 * @see io.dimitris.markingmate.Answer#getStudent()
	 * @see #getAnswer()
	 * @generated
	 */
	EReference getAnswer_Student();

	/**
	 * Returns the meta object for the reference '{@link io.dimitris.markingmate.Answer#getQuestion <em>Question</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Question</em>'.
	 * @see io.dimitris.markingmate.Answer#getQuestion()
	 * @see #getAnswer()
	 * @generated
	 */
	EReference getAnswer_Question();

	/**
	 * Returns the meta object for the attribute '{@link io.dimitris.markingmate.Answer#getFeedback <em>Feedback</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feedback</em>'.
	 * @see io.dimitris.markingmate.Answer#getFeedback()
	 * @see #getAnswer()
	 * @generated
	 */
	EAttribute getAnswer_Feedback();

	/**
	 * Returns the meta object for the attribute '{@link io.dimitris.markingmate.Answer#getMarks <em>Marks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Marks</em>'.
	 * @see io.dimitris.markingmate.Answer#getMarks()
	 * @see #getAnswer()
	 * @generated
	 */
	EAttribute getAnswer_Marks();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MarkingmateFactory getMarkingmateFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link io.dimitris.markingmate.impl.ExamImpl <em>Exam</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.dimitris.markingmate.impl.ExamImpl
		 * @see io.dimitris.markingmate.impl.MarkingmatePackageImpl#getExam()
		 * @generated
		 */
		EClass EXAM = eINSTANCE.getExam();

		/**
		 * The meta object literal for the '<em><b>Questions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXAM__QUESTIONS = eINSTANCE.getExam_Questions();

		/**
		 * The meta object literal for the '<em><b>Students</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXAM__STUDENTS = eINSTANCE.getExam_Students();

		/**
		 * The meta object literal for the '{@link io.dimitris.markingmate.impl.QuestionImpl <em>Question</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.dimitris.markingmate.impl.QuestionImpl
		 * @see io.dimitris.markingmate.impl.MarkingmatePackageImpl#getQuestion()
		 * @generated
		 */
		EClass QUESTION = eINSTANCE.getQuestion();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION__TITLE = eINSTANCE.getQuestion_Title();

		/**
		 * The meta object literal for the '<em><b>Marks</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUESTION__MARKS = eINSTANCE.getQuestion_Marks();

		/**
		 * The meta object literal for the '<em><b>Answers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUESTION__ANSWERS = eINSTANCE.getQuestion_Answers();

		/**
		 * The meta object literal for the '{@link io.dimitris.markingmate.impl.StudentImpl <em>Student</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.dimitris.markingmate.impl.StudentImpl
		 * @see io.dimitris.markingmate.impl.MarkingmatePackageImpl#getStudent()
		 * @generated
		 */
		EClass STUDENT = eINSTANCE.getStudent();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STUDENT__NUMBER = eINSTANCE.getStudent_Number();

		/**
		 * The meta object literal for the '<em><b>Answers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STUDENT__ANSWERS = eINSTANCE.getStudent_Answers();

		/**
		 * The meta object literal for the '{@link io.dimitris.markingmate.impl.AnswerImpl <em>Answer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see io.dimitris.markingmate.impl.AnswerImpl
		 * @see io.dimitris.markingmate.impl.MarkingmatePackageImpl#getAnswer()
		 * @generated
		 */
		EClass ANSWER = eINSTANCE.getAnswer();

		/**
		 * The meta object literal for the '<em><b>Student</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANSWER__STUDENT = eINSTANCE.getAnswer_Student();

		/**
		 * The meta object literal for the '<em><b>Question</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANSWER__QUESTION = eINSTANCE.getAnswer_Question();

		/**
		 * The meta object literal for the '<em><b>Feedback</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANSWER__FEEDBACK = eINSTANCE.getAnswer_Feedback();

		/**
		 * The meta object literal for the '<em><b>Marks</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANSWER__MARKS = eINSTANCE.getAnswer_Marks();

	}

} //MarkingmatePackage
