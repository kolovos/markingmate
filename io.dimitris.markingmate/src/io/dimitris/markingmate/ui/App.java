package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.explodingpixels.macwidgets.MacButtonFactory;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.UnifiedToolBar;

import io.dimitris.markingmate.Answer;
import io.dimitris.markingmate.Exam;
import io.dimitris.markingmate.MarkingmateFactory;
import io.dimitris.markingmate.MarkingmatePackage;
import io.dimitris.markingmate.Question;
import io.dimitris.markingmate.Student;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.View;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.GradientDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.gui.componentpainter.SolidColorComponentPainter;

public class App extends JFrame {

	protected JList<Student> studentsList;
	protected RelatedFeedbackPanel relatedFeedbackPanel;
	protected JComboBox<Question> questionsComboBox;
	protected FeedbackPanel feedbackPanel;
	protected JScrollPane relatedFeedbackPanelScrollPane;
	protected Resource resource;
	protected Exam exam;
	protected Answer answer;
	protected File file;
	
	public static void main(String[] args) throws Exception {
		new App().run();
	}

	protected void run() throws Exception {
		setTitle("MarkingMate");
		System.setProperty("Quaqua.tabLayoutPolicy", "wrap");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MacUtils.makeWindowLeopardStyle(getRootPane());
		
		exam = MarkingmateFactory.eINSTANCE.createExam();
		
		feedbackPanel = new FeedbackPanel(null);
		relatedFeedbackPanel = new RelatedFeedbackPanel();
		
		questionsComboBox = new JComboBox<Question>(new QuestionsComboBoxModel(this));
		questionsComboBox.setMinimumSize(new Dimension(0, 0));
	
		questionsComboBox.setUI((ComboBoxUI) Class.forName("com.apple.laf.AquaComboBoxUI").newInstance());
		
		questionsComboBox.setRenderer(new QuestionsComboBoxListCellRenderer());
		questionsComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				questionSelected((Question)questionsComboBox.getSelectedItem());
			}
		});
		
		
		studentsList = new JList<Student>(new StudentsListModel(this));
		studentsList.setBorder(new EtchedBorder());
		studentsList.setCellRenderer(new StudentsListCellRenderer());
		studentsList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) return;
				studentSelected(studentsList.getSelectedValue());
			}
		});
		
		JPanel questionFeedbackPanel= new JPanel(new BorderLayout());
		questionFeedbackPanel.setOpaque(false);
		questionFeedbackPanel.add(questionsComboBox, BorderLayout.NORTH);
		questionFeedbackPanel.add(feedbackPanel, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		
		UnifiedToolBar toolbar = new UnifiedToolBar();
		toolbar.installWindowDraggerOnWindow(this);
		toolbar.disableBackgroundPainter();
		add(toolbar.getComponent(), BorderLayout.NORTH);
		toolbar.addComponentToLeft(getUnifiedToolBarButton(new OpenAction()));
		toolbar.addComponentToLeft(getUnifiedToolBarButton(new SaveAction()));
		getRootPane().putClientProperty("apple.awt.brushMetalLook", true);
		
		ViewMap viewMap = new ViewMap();
		View studentsView = createView("Students", createJScrollPane(studentsList), viewMap);
		View feedbackView = createView("Feedback", questionFeedbackPanel, viewMap);
		relatedFeedbackPanelScrollPane = createJScrollPane(relatedFeedbackPanel);
		View relatedFeedbackView = createView("Related Feedback", relatedFeedbackPanelScrollPane, viewMap);
		RootWindow rootWindow = DockingUtil.createRootWindow(viewMap, true);

		DockingWindowsTheme theme = new GradientDockingTheme(false, false, false, false, new Color(165, 165, 165));
		rootWindow.getRootWindowProperties().addSuperObject(theme.getRootWindowProperties());
		rootWindow.getRootWindowProperties().getWindowAreaShapedPanelProperties().setComponentPainter(new SolidColorComponentPainter(new FixedColorProvider(new Color(223, 228, 234))));
		rootWindow.getRootWindowProperties().getWindowAreaProperties().setInsets(new Insets(0, 0, 0, 0))
		.setBorder(new EmptyBorder(10, 10, 10, 10));
		rootWindow.setWindow(new SplitWindow(true, 0.3f, studentsView, new SplitWindow(true, 0.5f, feedbackView, relatedFeedbackView)));
		
		getContentPane().add(rootWindow, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
			
		});
		setBounds(200, 200, 800, 500);
		setVisible(true);
		
	}
	
	public void save() {
		try {
			if (resource != null) resource.save(null);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(App.this, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void open() throws Exception {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(MarkingmatePackage.eINSTANCE.getNsURI(), MarkingmatePackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		
		FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
		fd.setVisible(true);
		if (fd.getFile() != null) {
			file = new File(fd.getDirectory(), fd.getFile());
			resource = resourceSet.createResource(URI.createFileURI(file.getAbsolutePath()));
			resource.load(null);
			exam = (Exam) resource.getContents().get(0);
			if (exam.getQuestions().size() > 0) questionsComboBox.setSelectedIndex(0);
			if (exam.getStudents().size() > 0) studentsList.setSelectedIndex(0);
			
			studentsList.updateUI();
			questionsComboBox.updateUI();
		}
	}
	
	public View createView(String title, Component component, ViewMap viewMap) {
		final View view = new View(title, null, component);
		viewMap.addView(viewMap.getViewCount(), view);
		view.getWindowProperties().setCloseEnabled(false);
		return view;
	}
	
	protected void questionSelected(Question question) {
		Student student = getStudent();
		if (student != null) {
			setAnswer(getAnswer(student, question));
		}
	}
	
	protected void studentSelected(Student student) {
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
		feedbackPanel.setAnswer(answer);
		relatedFeedbackPanel.setAnswer(answer);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				relatedFeedbackPanelScrollPane.getVerticalScrollBar().setValue(0);
			}
		});
		
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
		JScrollPane scrollPane = new JScrollPane(component, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		return scrollPane;
	}
	
	protected JSplitPane createSplitPane(Component left, Component right) {
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		splitPane.putClientProperty("Quaqua.SplitPane.style","bar");
		splitPane.setDividerSize(2);
		BasicSplitPaneDivider divider = (BasicSplitPaneDivider) splitPane.getComponent(2);
		divider.setBackground(new Color(165, 165, 165));
		divider.setBorder(new LineBorder(new Color(165, 165, 165), 0));
		splitPane.setBorder(new EmptyBorder(0,0,0,0));
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
			try {
				open();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(App.this, e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	class SaveAction extends AbstractAction {
		
		public SaveAction() {
			super("Save", new ImageIcon(new File("resources/save.png").getAbsolutePath()));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Saves the current MarkingMate file");
		}

		public void actionPerformed(ActionEvent actionevent) {
			save();
		}
		
	}
}
