/**
 */
package io.dimitris.markingmate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Student</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.dimitris.markingmate.Student#getNumber <em>Number</em>}</li>
 *   <li>{@link io.dimitris.markingmate.Student#getAnswers <em>Answers</em>}</li>
 * </ul>
 *
 * @see io.dimitris.markingmate.MarkingmatePackage#getStudent()
 * @model
 * @generated
 */
public interface Student extends EObject {
	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(String)
	 * @see io.dimitris.markingmate.MarkingmatePackage#getStudent_Number()
	 * @model
	 * @generated
	 */
	String getNumber();

	/**
	 * Sets the value of the '{@link io.dimitris.markingmate.Student#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(String value);

	/**
	 * Returns the value of the '<em><b>Answers</b></em>' containment reference list.
	 * The list contents are of type {@link io.dimitris.markingmate.Answer}.
	 * It is bidirectional and its opposite is '{@link io.dimitris.markingmate.Answer#getStudent <em>Student</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Answers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Answers</em>' containment reference list.
	 * @see io.dimitris.markingmate.MarkingmatePackage#getStudent_Answers()
	 * @see io.dimitris.markingmate.Answer#getStudent
	 * @model opposite="student" containment="true"
	 * @generated
	 */
	EList<Answer> getAnswers();

} // Student
