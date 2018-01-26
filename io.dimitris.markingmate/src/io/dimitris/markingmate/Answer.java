/**
 */
package io.dimitris.markingmate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Answer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.dimitris.markingmate.Answer#getStudent <em>Student</em>}</li>
 *   <li>{@link io.dimitris.markingmate.Answer#getQuestion <em>Question</em>}</li>
 *   <li>{@link io.dimitris.markingmate.Answer#getFeedback <em>Feedback</em>}</li>
 *   <li>{@link io.dimitris.markingmate.Answer#getMarks <em>Marks</em>}</li>
 * </ul>
 *
 * @see io.dimitris.markingmate.MarkingmatePackage#getAnswer()
 * @model
 * @generated
 */
public interface Answer extends EObject {
	/**
	 * Returns the value of the '<em><b>Student</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link io.dimitris.markingmate.Student#getAnswers <em>Answers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Student</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Student</em>' container reference.
	 * @see #setStudent(Student)
	 * @see io.dimitris.markingmate.MarkingmatePackage#getAnswer_Student()
	 * @see io.dimitris.markingmate.Student#getAnswers
	 * @model opposite="answers"
	 * @generated
	 */
	Student getStudent();

	/**
	 * Sets the value of the '{@link io.dimitris.markingmate.Answer#getStudent <em>Student</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Student</em>' container reference.
	 * @see #getStudent()
	 * @generated
	 */
	void setStudent(Student value);

	/**
	 * Returns the value of the '<em><b>Question</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link io.dimitris.markingmate.Question#getAnswers <em>Answers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Question</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Question</em>' reference.
	 * @see #setQuestion(Question)
	 * @see io.dimitris.markingmate.MarkingmatePackage#getAnswer_Question()
	 * @see io.dimitris.markingmate.Question#getAnswers
	 * @model opposite="answers" resolveProxies="false"
	 * @generated
	 */
	Question getQuestion();

	/**
	 * Sets the value of the '{@link io.dimitris.markingmate.Answer#getQuestion <em>Question</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Question</em>' reference.
	 * @see #getQuestion()
	 * @generated
	 */
	void setQuestion(Question value);

	/**
	 * Returns the value of the '<em><b>Feedback</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feedback</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feedback</em>' attribute.
	 * @see #setFeedback(String)
	 * @see io.dimitris.markingmate.MarkingmatePackage#getAnswer_Feedback()
	 * @model default=""
	 * @generated
	 */
	String getFeedback();

	/**
	 * Sets the value of the '{@link io.dimitris.markingmate.Answer#getFeedback <em>Feedback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feedback</em>' attribute.
	 * @see #getFeedback()
	 * @generated
	 */
	void setFeedback(String value);

	/**
	 * Returns the value of the '<em><b>Marks</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Marks</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Marks</em>' attribute.
	 * @see #setMarks(int)
	 * @see io.dimitris.markingmate.MarkingmatePackage#getAnswer_Marks()
	 * @model default="0"
	 * @generated
	 */
	int getMarks();

	/**
	 * Sets the value of the '{@link io.dimitris.markingmate.Answer#getMarks <em>Marks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Marks</em>' attribute.
	 * @see #getMarks()
	 * @generated
	 */
	void setMarks(int value);

} // Answer
