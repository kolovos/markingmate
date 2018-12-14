/**
 */
package io.dimitris.markingmate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exam</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.dimitris.markingmate.Exam#getGenerator <em>Generator</em>}</li>
 *   <li>{@link io.dimitris.markingmate.Exam#getQuestions <em>Questions</em>}</li>
 *   <li>{@link io.dimitris.markingmate.Exam#getStudents <em>Students</em>}</li>
 * </ul>
 *
 * @see io.dimitris.markingmate.MarkingmatePackage#getExam()
 * @model
 * @generated
 */
public interface Exam extends EObject {
	/**
	 * Returns the value of the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generator</em>' attribute.
	 * @see #setGenerator(String)
	 * @see io.dimitris.markingmate.MarkingmatePackage#getExam_Generator()
	 * @model
	 * @generated
	 */
	String getGenerator();

	/**
	 * Sets the value of the '{@link io.dimitris.markingmate.Exam#getGenerator <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generator</em>' attribute.
	 * @see #getGenerator()
	 * @generated
	 */
	void setGenerator(String value);

	/**
	 * Returns the value of the '<em><b>Questions</b></em>' containment reference list.
	 * The list contents are of type {@link io.dimitris.markingmate.Question}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Questions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Questions</em>' containment reference list.
	 * @see io.dimitris.markingmate.MarkingmatePackage#getExam_Questions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Question> getQuestions();

	/**
	 * Returns the value of the '<em><b>Students</b></em>' containment reference list.
	 * The list contents are of type {@link io.dimitris.markingmate.Student}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Students</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Students</em>' containment reference list.
	 * @see io.dimitris.markingmate.MarkingmatePackage#getExam_Students()
	 * @model containment="true"
	 * @generated
	 */
	EList<Student> getStudents();

} // Exam
