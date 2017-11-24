/**
 */
package io.dimitris.markingmate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Question</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link io.dimitris.markingmate.Question#getTitle <em>Title</em>}</li>
 *   <li>{@link io.dimitris.markingmate.Question#getMarks <em>Marks</em>}</li>
 *   <li>{@link io.dimitris.markingmate.Question#getAnswers <em>Answers</em>}</li>
 * </ul>
 *
 * @see io.dimitris.markingmate.MarkingmatePackage#getQuestion()
 * @model
 * @generated
 */
public interface Question extends EObject {
	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see io.dimitris.markingmate.MarkingmatePackage#getQuestion_Title()
	 * @model default=""
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link io.dimitris.markingmate.Question#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

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
	 * @see io.dimitris.markingmate.MarkingmatePackage#getQuestion_Marks()
	 * @model default="0"
	 * @generated
	 */
	int getMarks();

	/**
	 * Sets the value of the '{@link io.dimitris.markingmate.Question#getMarks <em>Marks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Marks</em>' attribute.
	 * @see #getMarks()
	 * @generated
	 */
	void setMarks(int value);

	/**
	 * Returns the value of the '<em><b>Answers</b></em>' reference list.
	 * The list contents are of type {@link io.dimitris.markingmate.Answer}.
	 * It is bidirectional and its opposite is '{@link io.dimitris.markingmate.Answer#getQuestion <em>Question</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Answers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Answers</em>' reference list.
	 * @see io.dimitris.markingmate.MarkingmatePackage#getQuestion_Answers()
	 * @see io.dimitris.markingmate.Answer#getQuestion
	 * @model opposite="question"
	 * @generated
	 */
	EList<Answer> getAnswers();

} // Question
