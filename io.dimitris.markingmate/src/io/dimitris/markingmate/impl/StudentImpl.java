/**
 */
package io.dimitris.markingmate.impl;

import java.math.BigDecimal;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import io.dimitris.markingmate.Answer;
import io.dimitris.markingmate.MarkingmatePackage;
import io.dimitris.markingmate.Student;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Student</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link io.dimitris.markingmate.impl.StudentImpl#getNumber <em>Number</em>}</li>
 *   <li>{@link io.dimitris.markingmate.impl.StudentImpl#getAnswers <em>Answers</em>}</li>
 *   <li>{@link io.dimitris.markingmate.impl.StudentImpl#getTotalMarks <em>Total Marks</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StudentImpl extends MinimalEObjectImpl.Container implements Student {
	/**
	 * The default value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected static final String NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
	protected String number = NUMBER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnswers() <em>Answers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnswers()
	 * @generated
	 * @ordered
	 */
	protected EList<Answer> answers;

	/**
	 * The default value of the '{@link #getTotalMarks() <em>Total Marks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalMarks()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal TOTAL_MARKS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTotalMarks() <em>Total Marks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalMarks()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal totalMarks = TOTAL_MARKS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StudentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MarkingmatePackage.Literals.STUDENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getNumber() {
		return number;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNumber(String newNumber) {
		String oldNumber = number;
		number = newNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MarkingmatePackage.STUDENT__NUMBER, oldNumber, number));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Answer> getAnswers() {
		if (answers == null) {
			answers = new EObjectContainmentWithInverseEList<Answer>(Answer.class, this, MarkingmatePackage.STUDENT__ANSWERS, MarkingmatePackage.ANSWER__STUDENT);
		}
		return answers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public BigDecimal getTotalMarks() {
		if (totalMarks == null) {
			totalMarks = BigDecimal.ZERO;
			for (Answer a : getAnswers()) {
				totalMarks = totalMarks.add(a.getMarks());
			}
			registerTotalMarksAdapter();
		}
		return totalMarks;
	}

	private void registerTotalMarksAdapter() {
		for (Adapter ea : eAdapters()) {
			if (ea instanceof TotalMarksAdapter) {
				return;
			}
		}
		this.eAdapters().add(new TotalMarksAdapter());
	}

	protected class TotalMarksAdapter extends EContentAdapter {
		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getNotifier() instanceof Answer) {
				if (notification.getFeatureID(Answer.class) == MarkingmatePackage.ANSWER__MARKS) {
					// marks changed - invalidate the total marks computed so far
					totalMarks = null;
				}
			}

			super.notifyChanged(notification);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MarkingmatePackage.STUDENT__ANSWERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAnswers()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MarkingmatePackage.STUDENT__ANSWERS:
				return ((InternalEList<?>)getAnswers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MarkingmatePackage.STUDENT__NUMBER:
				return getNumber();
			case MarkingmatePackage.STUDENT__ANSWERS:
				return getAnswers();
			case MarkingmatePackage.STUDENT__TOTAL_MARKS:
				return getTotalMarks();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MarkingmatePackage.STUDENT__NUMBER:
				setNumber((String)newValue);
				return;
			case MarkingmatePackage.STUDENT__ANSWERS:
				getAnswers().clear();
				getAnswers().addAll((Collection<? extends Answer>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MarkingmatePackage.STUDENT__NUMBER:
				setNumber(NUMBER_EDEFAULT);
				return;
			case MarkingmatePackage.STUDENT__ANSWERS:
				getAnswers().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MarkingmatePackage.STUDENT__NUMBER:
				return NUMBER_EDEFAULT == null ? number != null : !NUMBER_EDEFAULT.equals(number);
			case MarkingmatePackage.STUDENT__ANSWERS:
				return answers != null && !answers.isEmpty();
			case MarkingmatePackage.STUDENT__TOTAL_MARKS:
				return TOTAL_MARKS_EDEFAULT == null ? totalMarks != null : !TOTAL_MARKS_EDEFAULT.equals(totalMarks);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (number: ");
		result.append(number);
		result.append(", totalMarks: ");
		result.append(totalMarks);
		result.append(')');
		return result.toString();
	}

} //StudentImpl
