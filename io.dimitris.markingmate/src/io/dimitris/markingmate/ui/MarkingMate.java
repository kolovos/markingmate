package io.dimitris.markingmate.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;

import io.dimitris.markingmate.Answer;
import io.dimitris.markingmate.Exam;
import io.dimitris.markingmate.MarkingmateFactory;
import io.dimitris.markingmate.MarkingmatePackage;
import io.dimitris.markingmate.Question;
import io.dimitris.markingmate.Student;
import io.dimitris.markingmate.util.Merger;

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
	protected TimerTask autosaveTask;
	
	protected void run() throws Exception {
		setupLookAndFeel();

		SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
		SpellChecker.registerDictionaries( new File("").toURI().toURL(), null );
		SpellChecker.getOptions().setIgnoreCapitalization(true);
		exam = MarkingmateFactory.eINSTANCE.createExam();
		
		feedbackPanel = new FeedbackPanel(this, null, true);
		relatedFeedbackPanel = new RelatedFeedbackPanel(this);
		
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
		questionFeedbackPanel.add(getQuestionsComboxPanel(), BorderLayout.NORTH);
		questionFeedbackPanel.add(feedbackPanel, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new OpenAction(false)).setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));;;
		fileMenu.add(new SaveAction(false)).setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		menuBar.add(fileMenu);
		JMenu toolsMenu = new JMenu("Tools");
		toolsMenu.add(new AddAction(false));
		toolsMenu.add(new RemoveAction(false));
		toolsMenu.add(new ExportAction(false));
		// toolsMenu.add(new MergeAction(false)).setIcon(null);
		JMenu themesMenu = new JMenu("Theme");
		toolsMenu.add(themesMenu);
		themesMenu.add(new ChangeLookAndFeelAction("Light", new FlatLightLaf()));
		themesMenu.add(new ChangeLookAndFeelAction("Dark", new FlatDarkLaf()));
		
		menuBar.add(toolsMenu);
		setJMenuBar(menuBar);
		
		createToolbar();		
		
		relatedFeedbackPanelScrollPane = createJScrollPane(relatedFeedbackPanel);
		JSplitPane vertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createJTabbedPane("Students", createJScrollPane(studentsTable)), createJTabbedPane("Feedback", questionFeedbackPanel));
		vertical.setDividerLocation(200);
		vertical.setBorder(new EmptyBorder(0, 0, 2, 0));
		JSplitPane horizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vertical, createJTabbedPane("Related Feedback", relatedFeedbackPanelScrollPane));
		horizontal.setDividerLocation(300);
		horizontal.setBorder(new EmptyBorder(0, 8, 8, 8));
		
		getContentPane().add(horizontal, BorderLayout.CENTER);
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
		studentsTable.requestFocus();
		
	}
	
	protected JTabbedPane createJTabbedPane(String name, JComponent component) {
		JTabbedPane tp = new JTabbedPane();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(component, BorderLayout.CENTER);
		if (name.equalsIgnoreCase("Students")) {
			
			component.setBorder(new JTextArea().getBorder());
			panel.setBorder(new EmptyBorder(8, 2, 0, 2));
		}
		else component.setBorder(new EmptyBorder(8, 0, 0, 0));
		tp.add(name, panel);
		return tp;
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

	public void addStudent(String studentNumber) {
		if (exam != null) {
			Student student = MarkingmateFactory.eINSTANCE.createStudent();
			student.setNumber(studentNumber);
			exam.getStudents().add(student);
		}
	}

	public void removeStudent(int iStudent) {
		if (exam != null) {
			exam.getStudents().remove(iStudent);
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
	
	public void autosave() {
		try {
			if (resource != null && file != null & !file.getAbsolutePath().endsWith("autosaved")) resource.save(new FileOutputStream(file.getAbsolutePath() + ".autosaved"), null);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(MarkingMate.this, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void updateUI(JComponent component) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				component.updateUI();
			}
		});
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
					if (notification.getFeature() == null) return;
					if (!(notification.getOldValue()+"").contentEquals(notification.getNewValue()+"")) {
						setDirty(true);
						updateUI(studentsTable);
					}
				}
			};
			
			exam = (Exam) resource.getContents().get(0);
			if (exam.getQuestions().size() > 0) questionsComboBox.setSelectedIndex(0);
			if (exam.getStudents().size() > 0) {
				studentsTable.setRowSelectionInterval(0, 0);
				updateUI(studentsTable);
			}
			
			updateUI(studentsTable);
			updateUI(questionsComboBox);
			resource.eAdapters().add(adapter);
			
			if (autosaveTask != null) {
				autosaveTask.cancel();
			}
			autosaveTask = new TimerTask() {
				
				@Override
				public void run() {
					autosave();
				}
			};
			new Timer().schedule(autosaveTask, 1000, 10000);
			
		}
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
	
	class OpenAction extends AbstractAction {
		
		public OpenAction(boolean icon) {
			super("Open");
			if (icon) putValue(AbstractAction.SMALL_ICON, new FlatSVGIcon("io/dimitris/markingmate/ui/resources/open.svg"));
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
		
		public SaveAction(boolean icon) {
			super("Save");
			if (icon) putValue(AbstractAction.SMALL_ICON, new FlatSVGIcon("io/dimitris/markingmate/ui/resources/save.svg"));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Saves the current MarkingMate file");
			
		}

		public void actionPerformed(ActionEvent actionevent) {
			save();
		}
		
	}

	class AddAction extends AbstractAction {
		public AddAction(boolean icon) {
			super("Add student");
			if (icon)
				putValue(AbstractAction.SMALL_ICON, new FlatSVGIcon("io/dimitris/markingmate/ui/resources/add.svg"));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Adds a student to the current MarkingMate file");
		}

		public void actionPerformed(ActionEvent actionevent) {
			if (resource != null) {
				String studentNumber = (String) JOptionPane.showInputDialog(MarkingMate.this, // Parent component
					"Please enter the student number:", // Message
					"Enter Student Number", // Title
					JOptionPane.QUESTION_MESSAGE
				);
				if (studentNumber != null) {
					addStudent(studentNumber);
					studentsTable.setRowSelectionInterval(exam.getStudents().size() - 1, exam.getStudents().size() - 1);
				}
			}
		}
	}

	class RemoveAction extends AbstractAction {
		public RemoveAction(boolean icon) {
			super("Remove student");
			if (icon)
				putValue(AbstractAction.SMALL_ICON, new FlatSVGIcon("io/dimitris/markingmate/ui/resources/remove.svg"));
			putValue(AbstractAction.SHORT_DESCRIPTION,
					"Removes the selected student from the current MarkingMate file");
		}

		public void actionPerformed(ActionEvent actionevent) {
			final int iSelected = studentsTable.getSelectionModel().getLeadSelectionIndex();
			if (exam != null && iSelected != -1) {
				Student selectedStudent = exam.getStudents().get(iSelected);

				int response = JOptionPane.showConfirmDialog(MarkingMate.this, // Parent component (null for no parent)
					String.format("Are you sure that you want to remove student #%s?", selectedStudent.getNumber()),
					"Remove Student", // Title of the dialog window
					JOptionPane.YES_NO_OPTION, // Option buttons: Yes and No
					JOptionPane.WARNING_MESSAGE // Icon type: Warning
				);

				// Return true if user clicks 'Yes', false otherwise
				if (response == JOptionPane.YES_OPTION) {
					removeStudent(iSelected);
				}
			}
		}
	}

	class ExportAction extends AbstractAction {
		
		public ExportAction(boolean icon) {
			super("Export");
			if (icon) putValue(AbstractAction.SMALL_ICON, new FlatSVGIcon("io/dimitris/markingmate/ui/resources/export.svg"));
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
					File customGenerator = null;
					if (exam.getGenerator() != null) {
						customGenerator = new File(exam.getGenerator());
					}
					
					if (customGenerator == null || !customGenerator.exists()) {
						module.parse(new File("resources/feedback.egx"));
					}
					else {
						module.parse(customGenerator);
					}
					
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
	
	class MergeAction extends AbstractAction {
		
		public MergeAction(boolean icon) {
			super("Merge");
			if (icon) putValue(AbstractAction.SMALL_ICON, new ImageIcon(new File("resources/merge2.png").getAbsolutePath()));
			putValue(AbstractAction.SHORT_DESCRIPTION, "Merge mutliple MarkingMate files");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				FileDialog openDialog = new FileDialog(MarkingMate.this, "Choose the files to merge", FileDialog.LOAD );
				openDialog.setMultipleMode(true);
				FileDialog saveDialog = new FileDialog(MarkingMate.this, "Choose the output file", FileDialog.SAVE);
				
				openDialog.setVisible(true);
				File[] inputs = openDialog.getFiles();
				
				if (inputs.length < 2) return;
				
				saveDialog.setVisible(true);
				
				File[] outputs = saveDialog.getFiles();
				if (outputs.length != 1) return;
				
				new Merger().merge(inputs, outputs[0]);
				
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(MarkingMate.this, ex.getMessage(), "Merge failed", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class ChangeLookAndFeelAction extends AbstractAction {
		
		protected LookAndFeel laf;
		
		public ChangeLookAndFeelAction(String name, LookAndFeel laf) {
			super(name);
			this.laf = laf;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				UIManager.setLookAndFeel(laf);
				SwingUtilities.updateComponentTreeUI(MarkingMate.this);
			} catch (UnsupportedLookAndFeelException ex) {
				ex.printStackTrace();
			}
		}
		
	}
		
	public static void main(String[] args) throws Exception {
		new MarkingMate().run();
	}
	
	protected void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setMargin(new Insets(3, 3, 3, 3));
		//toolbar.setRollover(true);
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.NORTH);
		toolbar.add(new OpenAction(true));
		toolbar.add(new SaveAction(true));
		toolbar.add(new AddAction(true));
		toolbar.add(new RemoveAction(true));
		// toolbar.add(new MergeAction(true));
		toolbar.add(new ExportAction(true));
	}
	
	protected void setupLookAndFeel() throws Exception {
		FlatLightLaf.setup();
		UIManager.setLookAndFeel(new FlatLightLaf());
	}
	
	protected JComponent getQuestionsComboxPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 2, 2, 2));
		panel.setLayout(new BorderLayout());
		panel.add(questionsComboBox, BorderLayout.CENTER);
		return panel;
	}
	
	public void finetuneMarksPanel(JPanel marksPanel) {
		marksPanel.setBorder(new EmptyBorder(3,0,0,0));		
	}

}
