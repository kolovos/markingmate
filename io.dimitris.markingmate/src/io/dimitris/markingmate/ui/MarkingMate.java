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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.execute.context.Variable;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;

import io.dimitris.markingmate.Answer;
import io.dimitris.markingmate.Exam;
import io.dimitris.markingmate.MarkingmateFactory;
import io.dimitris.markingmate.MarkingmatePackage;
import io.dimitris.markingmate.Question;
import io.dimitris.markingmate.Student;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.View;
import net.infonode.docking.theme.GradientDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.PropertiesUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.gui.componentpainter.SolidColorComponentPainter;
import net.infonode.util.Direction;

public class MarkingMate extends JFrame {

	protected JTable studentsTable;
	protected RelatedFeedbackPanel relatedFeedbackPanel;
	protected JComboBox<Question> questionsComboBox;
	protected FeedbackPanel feedbackPanel;
	protected JScrollPane relatedFeedbackPanelScrollPane;
	protected Resource resource;
	protected Exam exam;
	protected Answer answer;
	protected File file;
	protected boolean dirty = false;
	protected EContentAdapter adapter;
	
	/*
	public static void main(String[] args) throws Exception {
		new MarkingMate().run();
	}*/
	
	protected void setupLookAndFeel() throws Exception {}
	
	protected void createToolbarAndMenus() {
		JToolBar toolbar = new JToolBar();
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.NORTH);
		toolbar.add(new OpenAction());
		toolbar.add(new SaveAction());
		toolbar.add(new ExportAction());
	}
	
	protected void run() throws Exception {
		setupLookAndFeel();

		SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
		SpellChecker.registerDictionaries( new File("").toURI().toURL(), null );
		SpellChecker.getOptions().setIgnoreCapitalization(true);
		exam = MarkingmateFactory.eINSTANCE.createExam();
		
		feedbackPanel = new FeedbackPanel(null);
		relatedFeedbackPanel = new RelatedFeedbackPanel();
		
		questionsComboBox = new JComboBox<Question>(new QuestionsComboBoxModel(this));
		questionsComboBox.setMinimumSize(new Dimension(0, 0));
	
		//questionsComboBox.setUI((ComboBoxUI) Class.forName("com.apple.laf.AquaComboBoxUI").newInstance());
		
		questionsComboBox.setRenderer(new QuestionsComboBoxListCellRenderer());
		questionsComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				questionSelected((Question)questionsComboBox.getSelectedItem());
			}
		});
		
		studentsTable = new JTable(new StudentsTableModel(this));
		studentsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studentsTable.getTableHeader().setOpaque(false);
		//studentsList.setCellRenderer(new StudentsListCellRenderer());
		studentsTable.setPreferredScrollableViewportSize(new Dimension(450,63));
		studentsTable.setFillsViewportHeight(true);
		studentsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//if (e.getValueIsAdjusting()) return;
				studentSelected(exam.getStudents().get(studentsTable.getSelectionModel().getLeadSelectionIndex()));
			}
		});
		
		JPanel questionFeedbackPanel= new JPanel(new BorderLayout());
		questionFeedbackPanel.setOpaque(false);
		questionFeedbackPanel.add(questionsComboBox, BorderLayout.NORTH);
		questionFeedbackPanel.add(feedbackPanel, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		createToolbarAndMenus();
		
		ViewMap viewMap = new ViewMap();
		JScrollPane p = createJScrollPane(studentsTable);
		p.setOpaque(true);
		p.setBackground(new Color(238, 238, 238));
		p.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2, 2, 1, 1), new EtchedBorder()));
		View studentsView = createView("Students", p, viewMap);
		View feedbackView = createView("Feedback", questionFeedbackPanel, viewMap);
		relatedFeedbackPanelScrollPane = createJScrollPane(relatedFeedbackPanel);
		View relatedFeedbackView = createView("Related Feedback", relatedFeedbackPanelScrollPane, viewMap);
		RootWindow rootWindow = DockingUtil.createRootWindow(viewMap, true);
		
		GradientDockingTheme theme = new GradientDockingTheme(false, false, false, false, new Color(150, 150, 150)); //new ShapedGradientDockingTheme(); //new GradientDockingTheme(false, false, false, false);
		rootWindow.getRootWindowProperties().addSuperObject(theme.getRootWindowProperties());
		// new Color(223, 228, 234) <- Light blue
		
		Color rootWindowBackgroundColor = new Color(245, 244, 245);
		rootWindow.getRootWindowProperties().getWindowAreaShapedPanelProperties().setComponentPainter(new SolidColorComponentPainter(new FixedColorProvider(rootWindowBackgroundColor)));
		rootWindow.getRootWindowProperties().getWindowAreaProperties().setInsets(new Insets(0, 0, 0, 0)).setBorder(new EmptyBorder(5,5,5,5));
		rootWindow.getRootWindowProperties().getTabWindowProperties().getTabbedPanelProperties().setTabAreaOrientation(Direction.DOWN);
		rootWindow.getRootWindowProperties().addSuperObject(PropertiesUtil.createTitleBarStyleRootWindowProperties());
		rootWindow.setWindow(new SplitWindow(false, 0.7f, new SplitWindow(true, 0.3f, studentsView, feedbackView), relatedFeedbackView));
		
		getContentPane().add(rootWindow, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (handleDirty()) System.exit(0);
			}
		});
		setBounds(200, 200, 800, 500);
		setDirty(false);
		setVisible(true);
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
		String title = "MarkingMate";
		if (dirty) title += " *";
		setTitle(title);
	}
	
	public void save() {
		try {
			if (resource != null) resource.save(null);
			setDirty(false);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(MarkingMate.this, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean handleDirty() {
		if (isDirty()) {
			int result = JOptionPane.showOptionDialog(this, "File has been modified. Save changes?", "File modified", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, JOptionPane.CANCEL_OPTION);
			if (result == JOptionPane.CANCEL_OPTION) {
				return false;
			}
			else if (result == JOptionPane.NO_OPTION) {
				return true;
			}
			else {
				save();
				return true;
			}
		}
		return true;
	}
	
	public void open() throws Exception {
		
		if (!handleDirty()) return;
		
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(MarkingmatePackage.eINSTANCE.getNsURI(), MarkingmatePackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		
		FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
		fd.setVisible(true);
		if (fd.getFile() != null) {
			file = new File(fd.getDirectory(), fd.getFile());
			resource = resourceSet.createResource(URI.createFileURI(file.getAbsolutePath()));
			resource.load(null);
			
			adapter = new EContentAdapter() {
				@Override
				public void notifyChanged(Notification notification) {
					
					if (!(notification.getOldValue()+"").contentEquals(notification.getNewValue()+"")) {
						setDirty(true);
						studentsTable.updateUI();
					}
				}
			};
			
			exam = (Exam) resource.getContents().get(0);
			if (exam.getQuestions().size() > 0) questionsComboBox.setSelectedIndex(0);
			if (exam.getStudents().size() > 0) {
				studentsTable.setRowSelectionInterval(0, 0);
				studentsTable.updateUI();
			}
			
			studentsTable.updateUI();
			questionsComboBox.updateUI();
			resource.eAdapters().add(adapter);
		}
	}
	
	public View createView(String title, Component component, ViewMap viewMap) {
		final View view = new View(title, null, component);
		viewMap.addView(viewMap.getViewCount(), view);
		view.getWindowProperties().setCloseEnabled(false);
		view.getWindowProperties().setMaximizeEnabled(false);
		view.getWindowProperties().setUndockEnabled(false);
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
		System.out.println(adapter == null);
		answer.eAdapters().add(adapter);
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
		if (studentsTable == null || studentsTable.getSelectionModel().getLeadSelectionIndex() == -1) return null;
		return exam.getStudents().get(studentsTable.getSelectionModel().getLeadSelectionIndex());
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
	
	class OpenAction extends AbstractAction {
		
		public OpenAction() {
			super("Open", new ImageIcon(new File("resources/open.png").getAbsolutePath()));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Opens a MarkingMate file");
		}

		public void actionPerformed(ActionEvent actionevent) {
			try {
				open();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(MarkingMate.this, e.getMessage());
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
	
	class ExportAction extends AbstractAction {
		
		public ExportAction() {
			super("Export", new ImageIcon(new File("resources/export.png").getAbsolutePath()));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Exports marks and feedback");
		}

		public void actionPerformed(ActionEvent actionevent) {
			
			if (file == null) return;
			
			FileDialog fd = new FileDialog(MarkingMate.this, "Choose a directory", FileDialog.SAVE);
			fd.setFile("marks.csv");
			fd.setVisible(true);
			if (fd.getFile() != null) {
				try {
					EgxModule module = new EgxModule(new EglFileGeneratingTemplateFactory());
					module.parse(new File("resources/feedback.egx"));
					((EglFileGeneratingTemplateFactory) module.getTemplateFactory()).setOutputRoot(fd.getDirectory());
					module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("csv", fd.getFile()));
					module.getContext().getModelRepository().addModel(new InMemoryEmfModel(exam.eResource()));
					module.execute();
					module.getContext().dispose();
					JOptionPane.showMessageDialog(MarkingMate.this, "Marks and feedback exported", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(MarkingMate.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		
	}
}
