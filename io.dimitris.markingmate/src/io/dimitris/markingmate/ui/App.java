package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.explodingpixels.macwidgets.HudWidgetFactory;
import com.explodingpixels.macwidgets.MacButtonFactory;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.explodingpixels.macwidgets.UnifiedToolBar;
import com.explodingpixels.macwidgets.plaf.HudComboBoxUI;

import io.dimitris.markingmate.Answer;
import io.dimitris.markingmate.Exam;
import io.dimitris.markingmate.MarkingmateFactory;
import io.dimitris.markingmate.MarkingmatePackage;
import io.dimitris.markingmate.Question;
import io.dimitris.markingmate.Student;

public class App extends JFrame {

	protected JList<Student> studentsList;
	protected AnswersPanel answersPanel;
	protected JComboBox<Question> questionsComboBox;
	protected JEditorPane feedbackEditorPane;
	protected JTextField markTextArea;
	protected Exam exam;
	protected Answer answer;
	
	public static void main(String[] args) throws Exception {
		// UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
        
		new App().run();
	}

	protected void run() throws Exception {
		setTitle("MarkingMate");
		System.setProperty("Quaqua.tabLayoutPolicy", "wrap");
		UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());
		MacUtils.makeWindowLeopardStyle(getRootPane());
		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(MarkingmatePackage.eINSTANCE.getNsURI(), MarkingmatePackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource resource = resourceSet.createResource(URI.createFileURI("/Users/dkolovos/git/markingmate/io.dimitris.markingmate/sample.model"));
		resource.load(null);
		exam = (Exam) resource.getContents().get(0);
		// exam = MarkingmateFactory.eINSTANCE.createExam();
		
		feedbackEditorPane = new JEditorPane();
		feedbackEditorPane.setMinimumSize(new Dimension(100, 0));
		feedbackEditorPane.setBorder(new EmptyBorder(7,7,7,7));
		
		answersPanel = new AnswersPanel();
		
		questionsComboBox = new JComboBox<Question>(new QuestionsComboBoxModel(this));
		//questionsComboBox.setUI(new HudComboBoxUI());
		
		questionsComboBox.setRenderer(new QuestionsComboBoxListCellRenderer());
		questionsComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				questionSelected((Question)questionsComboBox.getSelectedItem());
			}
		});
		
		if (exam.getQuestions().size() > 0) questionsComboBox.setSelectedIndex(0);
		
		studentsList = new JList<Student>(new StudentsListModel(this));
		studentsList.setMinimumSize(new Dimension(200, 0));
		//studentsList.setBorder(new EtchedBorder());
		studentsList.setCellRenderer(new StudentsListCellRenderer());
		studentsList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) return;
				studentSelected(studentsList.getSelectedValue());
			}
		});
		if (exam.getStudents().size() > 0) studentsList.setSelectedIndex(0);
		
		JPanel studentPanel= new JPanel(new BorderLayout());
		studentPanel.add(questionsComboBox, BorderLayout.NORTH);
		studentPanel.add(feedbackEditorPane, BorderLayout.CENTER);
		
		JSplitPane leftSplitPane = createSplitPane(studentsList, studentPanel);
		JSplitPane rightSplitPane = createSplitPane(leftSplitPane, createJScrollPane(answersPanel));
		rightSplitPane.setDividerLocation(600);
		
		setLayout(new BorderLayout());
		add(rightSplitPane, BorderLayout.CENTER);
		
		UnifiedToolBar toolbar = new UnifiedToolBar();
		toolbar.installWindowDraggerOnWindow(this);
		toolbar.disableBackgroundPainter();
		add(toolbar.getComponent(), BorderLayout.NORTH);
		toolbar.addComponentToLeft(getUnifiedToolBarButton(new OpenAction()));
		toolbar.addComponentToLeft(getUnifiedToolBarButton(new SaveAction()));
		getRootPane().putClientProperty("apple.awt.brushMetalLook", true);
		add(MacWidgetFactory.createComponentStatusBar().getComponent(), BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(200, 200, 800, 500);
		setVisible(true);
		
	}
	
	protected void questionSelected(Question question) {
		if (answer != null) {
			answer.setFeedback(feedbackEditorPane.getText());
		}
		Student student = getStudent();
		if (student != null) {
			setAnswer(getAnswer(student, question));
		}
	}
	
	protected void studentSelected(Student student) {
		if (answer != null) {
			answer.setFeedback(feedbackEditorPane.getText());
		}
		Question question = getQuestion();
		if (question != null) {
			setAnswer(getAnswer(student, question));
		}
	}
	
	protected Answer getAnswer(Student student, Question question) {
		for (Answer answer : student.getAnswers()) {
			if (answer.getQuestion() == question) {
				return answer;
			}
		}
		Answer answer = MarkingmateFactory.eINSTANCE.createAnswer();
		answer.setStudent(student);
		answer.setQuestion(question);
		return answer;
	}
	
	protected void setAnswer(Answer answer) {
		this.answer = answer;
		feedbackEditorPane.setText(answer.getFeedback());
		answersPanel.setAnswer(answer);
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
	
	protected JScrollPane createJScrollPane(Component component) {
		JScrollPane scrollPane = new JScrollPane(component, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		return scrollPane;
	}
	
	protected JSplitPane createSplitPane(Component left, Component right) {
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		splitPane.putClientProperty("Quaqua.SplitPane.style","bar");
		splitPane.setDividerSize(1);
		return splitPane;
	}
	
	protected AbstractButton getUnifiedToolBarButton(AbstractAction action) {
		JButton button = new JButton(action);
		button.setActionCommand("pressed");
		Dimension d = new Dimension(48,48);
		button.setPreferredSize(d);
		button.setMinimumSize(d);
		button.setMaximumSize(d);
		button.putClientProperty("JButton.buttonType", "textured");
		return MacButtonFactory.makeUnifiedToolBarButton(button);
	}
	
	class OpenAction extends AbstractAction {
		
		public OpenAction() {
			super("Open", new ImageIcon(new File("resources/open.png").getAbsolutePath()));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Opens a MarkingMate file");
		}

		public void actionPerformed(ActionEvent actionevent) {
			
		}
		
	}
	
	class SaveAction extends AbstractAction {
		
		public SaveAction() {
			super("Save", new ImageIcon(new File("resources/save.png").getAbsolutePath()));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Saves the current MarkingMate file");
		}

		public void actionPerformed(ActionEvent actionevent) {
			
		}
		
	}
}
