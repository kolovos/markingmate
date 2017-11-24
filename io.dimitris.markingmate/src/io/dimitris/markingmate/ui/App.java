package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import io.dimitris.markingmate.Answer;
import io.dimitris.markingmate.Exam;
import io.dimitris.markingmate.MarkingmatePackage;
import io.dimitris.markingmate.Question;
import io.dimitris.markingmate.Student;

public class App extends JFrame {

	protected JList<Student> studentsList;
	protected JComboBox<Question> questionsComboBox;
	protected JEditorPane feedbackEditorPane;
	protected JTextField markTextArea;
	protected Exam exam;
	protected Answer answer;
	
	public static void main(String[] args) throws Exception {
		new App().run();
	}

	protected void run() throws Exception {
		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(MarkingmatePackage.eINSTANCE.getNsURI(), MarkingmatePackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource resource = resourceSet.createResource(URI.createFileURI("/Users/dkolovos/git/markingmate/io.dimitris.markingmate/sample.model"));
		resource.load(null);
		exam = (Exam) resource.getContents().get(0);
		
		feedbackEditorPane = new JEditorPane();
		feedbackEditorPane.setBorder(new EtchedBorder());
		
		questionsComboBox = new JComboBox<Question>(new QuestionsComboBoxModel(this));
		questionsComboBox.setRenderer(new QuestionsComboBoxListCellRenderer());
		questionsComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				questionSelected((Question)questionsComboBox.getSelectedItem());
			}
		});
		
		if (exam.getQuestions().size() > 0) questionsComboBox.setSelectedIndex(0);
		
		studentsList = new JList<Student>(new StudentsListModel(this));
		studentsList.setCellRenderer(new StudentsListCellRenderer());
		studentsList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) return;
				studentSelected(studentsList.getSelectedValue());
			}
		});
		if (exam.getStudents().size() > 0) studentsList.setSelectedIndex(0);
		
		JPanel otherAnswersPanel = new JPanel(new GridLayout(0, 1));
		
		otherAnswersPanel.add(new JLabel("TBD"));
		
		JPanel studentPanel= new JPanel(new BorderLayout());	
		studentPanel.add(questionsComboBox, BorderLayout.NORTH);
		
		studentPanel.add(feedbackEditorPane, BorderLayout.CENTER);
		
		getRootPane().setLayout(new BorderLayout());
		getRootPane().add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(studentsList),
				new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, studentPanel, otherAnswersPanel)), BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(200, 200, 400, 400);
		setVisible(true);
		
	}
	
	protected void questionSelected(Question question) {
		if (answer != null) {
			answer.setFeedback(feedbackEditorPane.getText());
		}
		Student student = getStudent();
		if (student != null) {
			for (Answer candidate : question.getAnswers()) {
				if (candidate.getStudent() == student) {
					answer = candidate;
					feedbackEditorPane.setText(answer.getFeedback());
					break;
				}
			}
		}
	}
	
	protected void studentSelected(Student student) {
		if (answer != null) {
			answer.setFeedback(feedbackEditorPane.getText());
		}
		Question question = getQuestion();
		if (question != null) {
			for (Answer candidate : student.getAnswers()) {
				if (candidate.getQuestion() == question) {
					answer = candidate;
					feedbackEditorPane.setText(answer.getFeedback());
					break;
				}
			}
		}
	}
	
	public Student getStudent() {
		if (studentsList == null) return null;
		return studentsList.getSelectedValue();
	}
	
	public Question getQuestion() {
		if (questionsComboBox == null) return null;
		return (Question) questionsComboBox.getSelectedItem();
	}
	
	public Exam getExam() {
		return exam;
	}
	
}
