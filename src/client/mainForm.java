package client;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import static com.wagnerandade.coollection.Coollection.*;

import com.toedter.calendar.JCalendar;

import dao.DAOFactory;
import dao.IConfigDAO;
import dao.IExerciseDAO;
import dao.IResultDAO;
import dao.ITrainingPlanDAO;
import entities.Exercise;
import entities.Result;
import entities.Task;
import entities.Training;
import entities.TrainingPlan;
import transfer.Exel;
import transfer.Transfer;

//import src.gui.Throwable;

import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;

import javax.swing.JMenuBar;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;

import java.awt.SystemColor;
import java.awt.SystemTray;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.StyledEditorKit.FontFamilyAction;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;
import java.awt.GridBagLayout;

public class mainForm extends JFrame {

	public static mainForm frame;
	private JPanel contentPane;
	public static JTable tableSchedule;
	
	static DAOFactory daoFactory;
	
	static IExerciseDAO exerciseDAO;
	static IResultDAO resultDAO;
	static ITrainingPlanDAO trainingPlanDAO;
	static IConfigDAO configDAO;
	
	static JButton addJButtonExercisesTab = null;
	static JButton updateJButtonExercisesTab = null;
	static JButton deleteJButtonExercisesTab = null;
	static JTextArea exercisesTabTextArea = null;
	
	static JCalendar calendar = new JCalendar();
	private JMenuItem importMenuItem_1;
	private JMenuItem exportMenuItem_1;
	private JMenuItem exportXLSMenuItem;
	
	////!!!Всю работу вести с этими коллекциями
	public static ArrayList<Exercise> exercises;
	static ArrayList<Result> results;
	public static ArrayList<TrainingPlan> trainingPlans;
	
	static Exercise currentExercise;
	static Result currentResult;
	static TrainingPlan currentTrainingPlan;
	
	static JLabelWithId currentExerciseJLabelWithId;
	static JLabelWithId currentTrainingPlanJLabelWithId;
	static Training currentTraining;
	static Task currentTask;
	
	//Объекты вкладки планов
	public static JPanel panelPlanList = new JPanel();
	private JComboBox exerciseСomboBox_1;
	public static MyDragGestureListener dlistener = new MyDragGestureListener();
	
	
	
	public static JPanel exercisesTabExercisesPanel = new JPanel();
	public static JPanel trainingPlansTabExercisesPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		daoFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
		//daoFactory.createDataSource();
		
		exerciseDAO = daoFactory.getExerciseDAO();
		resultDAO = daoFactory.getResultDAO();
		trainingPlanDAO = daoFactory.getTrainingPlanDAO();
		configDAO = daoFactory.getConfigDAO();
		
		exercises = (ArrayList<Exercise>) exerciseDAO.findAll();
		results = (ArrayList<Result>) resultDAO.findAll();
		trainingPlans = (ArrayList<TrainingPlan>) trainingPlanDAO.findAll();
		
		try {
			ServerSocket ss = new java.net.ServerSocket(1488);

			} catch (java.net.BindException ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Дневник тренировок уже запущен", "", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Program already running");
			System.exit(1);
			} catch (java.io.IOException ex) {
			ex.printStackTrace();
			System.exit(1);
			}
		
		
		//currentTrainingPlan
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new mainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainForm() {
		setMinimumSize(new Dimension(709, 523));
	
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("\u0414\u043D\u0435\u0432\u043D\u0438\u043A \u0442\u0440\u0435\u043D\u0438\u0440\u043E\u0432\u043E\u043A");
		setIconImage(Toolkit.getDefaultToolkit().getImage(mainForm.class.getResource("/resources/gym-icon.png")));
		setBounds(100, 100, 709, 523);
		
		/*
		 *Tray objects
		 */
		PopupMenu trayMenu           = null;
		MenuItem taryMenuItem        = null;
		MenuItem defaultTrayManuItem = null;
		java.awt.Image icon = null;
		SystemTray tray = null;
		TrayIcon trayIcon = null;
		
		/*
		 *File menu objects
		 */
		JMenuBar menuBar = null;
		JMenu fileMenu = null;
		JMenuItem importMenuItem = null;
		JMenuItem exportMenuItem = null;
		JSeparator fileMenuSeparator = null;
		JMenuItem exitMenuItem = null;
		JMenu settingsMenu = null;
		JMenuItem fontItem = null;
		JMenuItem altStyleMenuItem = null;
		JMenu helpMenu = null;
		JMenuItem aboutMenuItem = null;
		
		/*
		 *TabPane objects
		 */
		JPanel panelSchedule = null;
		
		
		/*
		 *TrainingPlansTab objects
		 */
		trainingPlansTabExercisesPanel = new JPanel();
		JButton btnCreatePlan = new JButton("\u0421\u043E\u0437\u0434\u0430\u0442\u044C \u043F\u043B\u0430\u043D");
		JButton btnDeletePlan = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u043F\u043B\u0430\u043D");
		JScrollPane scrollPane = new JScrollPane();
		JPanel panelPlan = new JPanel();
		JScrollPane scrollPanePlanList = new JScrollPane();
		
		JPanel dropPanel = new JPanel();
		
		/*
		 *ChartTabPanel objects
		 */
		JPanel chartTabPanel = new JPanel();
		final JComboBox timeСomboBox;// = new JComboBox(timeItems);
		ArrayList<String> exercisesNamesArrayListt = new ArrayList<String>();
		JComboBox exerciseСomboBox = null;
        ArrayList<String> exercisesNamesArrayList = new ArrayList<String>();
        
		/*
		 *Tray methods
		 */
		if(! SystemTray.isSupported() ) {
	        return;
	      }

	      trayMenu = new PopupMenu();
	      taryMenuItem = new MenuItem("Выход");
	      taryMenuItem.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	System.exit(0);
	        }
	      });
	      trayMenu.add(taryMenuItem);
	      
	      defaultTrayManuItem = new MenuItem("Открыть");
	      defaultTrayManuItem.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    setVisible(true);
	                    setExtendedState(JFrame.NORMAL);
	                    
	                }
	            });
	            trayMenu.add(defaultTrayManuItem);

	      icon = Toolkit.getDefaultToolkit().getImage(mainForm.class.getResource("/resources/gym-icon.png"));
	      trayIcon = new TrayIcon(icon, "Дневник", trayMenu);
	      trayIcon.setImageAutoSize(true);

	      tray = SystemTray.getSystemTray();
	      try {
	        tray.add(trayIcon);
	      } catch (AWTException e) {
	        e.printStackTrace();
	      }
	      
	      trayIcon.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    setVisible(true);
	                    setExtendedState(JFrame.NORMAL);
	                    
	                }
	            });
	      

		
		/*
		 *File menu methods
		 */
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			fileMenu = new JMenu("Файл");
			menuBar.add(fileMenu);
			
			importMenuItem_1 = new JMenuItem("\u0418\u043C\u043F\u043E\u0440\u0442");
			importMenuItem_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser dialog = new JFileChooser();
					//dialog.se
					FileNameExtensionFilter filt = new FileNameExtensionFilter("Import xml file", "xml");
					dialog.setFileFilter(filt);
					int result = dialog.showOpenDialog(frame);
			        if (result == JFileChooser.APPROVE_OPTION) {
				        String pathString = dialog.getSelectedFile().getAbsolutePath();
				        if (pathString.substring(pathString.lastIndexOf('.'), pathString.length()).equalsIgnoreCase(".xml")) {
				        	Transfer.importPlan(dialog.getSelectedFile(), exerciseDAO, trainingPlanDAO); 
				        } else {
				        	JOptionPane.showMessageDialog(new JFrame(), "Выбран неверный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
				        }
			        }

				}
			});
			fileMenu.add(importMenuItem_1);
			
			exportMenuItem_1 = new JMenuItem("\u042D\u043A\u0441\u043F\u043E\u0440\u0442");
			exportMenuItem_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (currentTrainingPlan != null) {
						JFileChooser dialog = new JFileChooser();
						FileNameExtensionFilter filt = new FileNameExtensionFilter("Export xml file", "xml");
						dialog.setFileFilter(filt);
						int result = dialog.showSaveDialog(frame);
				        setVisible(true);
				        //Transfer.importPlan(dialog.getSelectedFile(), exerciseDAO, trainingPlanDAO); 
				        if (result == JFileChooser.APPROVE_OPTION) {
					        String pathString = dialog.getSelectedFile().getAbsolutePath();
					        try {
						        if (pathString.substring(pathString.lastIndexOf('.'), pathString.length()).equalsIgnoreCase(".xml")) {
						        	Transfer.exportPlan(trainingPlanDAO.findOne(currentTrainingPlan.getPlanId()), dialog.getSelectedFile());
						        } else {
						        	
						        	JOptionPane.showMessageDialog(new JFrame(), "Выбран неверный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
						        }
					        } catch (Exception ee) {
					        	System.out.println("Нужно добавить xml");
					        	File f = new File(pathString + ".xml");
					        	Transfer.exportPlan(trainingPlanDAO.findOne(currentTrainingPlan.getPlanId()), f);
					        }
				        }
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "Выберите план тренировок", "Ошибка", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			fileMenu.add(exportMenuItem_1);
			
			exportXLSMenuItem = new JMenuItem("Экспорт в xls");
			exportXLSMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (currentTrainingPlan != null) {
						JFileChooser dialog = new JFileChooser();
						FileNameExtensionFilter filt = new FileNameExtensionFilter("Рабочая книга Exel", "xls");
						dialog.setFileFilter(filt);
						int result = dialog.showSaveDialog(frame);
						if (result == JFileChooser.APPROVE_OPTION) {
					        String pathString = dialog.getSelectedFile().getAbsolutePath();
					        try {
						        if (pathString.substring(pathString.lastIndexOf('.'), pathString.length()).equalsIgnoreCase(".xls")) {
						        	Exel.createXLSPlan(trainingPlanDAO.findOne(currentTrainingPlan.getPlanId()), dialog.getSelectedFile());
						        } else {
						        	JOptionPane.showMessageDialog(new JFrame(), "Выбран неверный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
						        }
					        } catch (Exception e) {
					        	File f = new File(pathString + ".xls");
					        	Exel.createXLSPlan(trainingPlanDAO.findOne(currentTrainingPlan.getPlanId()), f);
					        }
						}
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "Выберите план тренировок", "Ошибка", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			fileMenu.add(exportXLSMenuItem);
			
			fileMenuSeparator = new JSeparator();
			fileMenu.add(fileMenuSeparator);
			
			exitMenuItem = new JMenuItem("Выход");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//daoFactory.closeDataSource();
					System.exit(0);
				}
			});
			exitMenuItem.setIcon(new ImageIcon(mainForm.class.getResource("/resources/exit-icon.png")));
			fileMenu.add(exitMenuItem);
			
			settingsMenu_1 = new JMenu("Настройки");
			menuBar.add(settingsMenu_1);
			fontItem = new JMenuItem("Шрифт");
			settingsMenu_1.add(fontItem);
			
			JMenu menu = new JMenu("\u0421\u0442\u0438\u043B\u044C");
			settingsMenu_1.add(menu);
			
			JMenuItem mntmNewMenuItem = new JMenuItem("\u041E\u0431\u044A\u0435\u043C\u043D\u044B\u0439");
			menu.add(mntmNewMenuItem);
			
			altStyleMenuItem_1 = new JMenuItem("\u0410\u043B\u044C\u0442\u0435\u0440\u043D\u0430\u0442\u0438\u0432\u043D\u044B\u0439");
			menu.add(altStyleMenuItem_1);
			
			JMenuItem ClassicStyleMenuItem = new JMenuItem("\u041A\u043B\u0430\u0441\u0441\u0438\u0447\u0435\u0441\u043A\u0438\u0439");
			menu.add(ClassicStyleMenuItem);
			ClassicStyleMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						SwingUtilities.updateComponentTreeUI(contentPane.getParent());
						exercisesTabTextArea.setFont(getFont());
						configDAO.setCurrentStyle(0);

					} catch(Exception ex) {
						System.out.println("Unable to set LookAndFeel");
					}
				}
			});
			altStyleMenuItem_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
						configDAO.setCurrentStyle(1);
						exercisesTabTextArea.setFont(getFont());

					} catch (Throwable ex) {
						ex.printStackTrace();
					}
					SwingUtilities.updateComponentTreeUI(contentPane.getParent());
				}
			});
			mntmNewMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
						SwingUtilities.updateComponentTreeUI(contentPane.getParent());
						exercisesTabTextArea.setFont(getFont());
						configDAO.setCurrentStyle(2);
					} catch (Throwable ex) {
						ex.printStackTrace();
					}					
				}
			});
			
			helpMenu = new JMenu("Справка");
			menuBar.add(helpMenu);
			
			aboutMenuItem = new JMenuItem("О программе");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(new JFrame(), "Версия 0.2", "О программе", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			aboutMenuItem.setIcon(new ImageIcon(mainForm.class.getResource("/resources/info-icon.png")));
			helpMenu.add(aboutMenuItem);
			
			
			
		/*
		 *TabPane methods
		 */	
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			GroupLayout gl_contentPane = new GroupLayout(contentPane);
			gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
			);
			panelSchedule = new JPanel();
			tabbedPane.addTab("Расписание", null, panelSchedule, null);

		
		/*
		 *TrainingPlansTab methods
		 */


		calendar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (currentTrainingPlan != null) {
					currentTraining = findTraining(currentTrainingPlan.getTrainings(), calendar.getDate());
					repaintExerciseTableInTrainingPlansTab();
					
				}
			}
		});
		Integer curPlan = configDAO.getCurrentTrainingPlan();
		if (curPlan != null) {
			currentTrainingPlan = findTrainingPlan(curPlan);
			currentTraining = findTraining(currentTrainingPlan.getTrainings(), calendar.getDate());
			
		}
				if (currentTraining != null) {
		        Date curDate = new Date();
		        
		      //  for (Training tr : currentTrainingPlan.getTrainings()) {
		         System.out.println("Тренировка " + currentTraining.getDate().toLocaleString());
		         if (curDate.getYear() == currentTraining.getDate().getYear()
		           && curDate.getDate() == currentTraining.getDate().getDate()
		           && curDate.getMonth() == currentTraining.getDate().getMonth()) {
		          trayIcon.displayMessage("Напоминание", "У вас сегодня тренировка", TrayIcon.MessageType.INFO);
		          System.out.println(curDate.toLocaleString() + " = " + currentTraining.getDate().toLocaleString());
		         }
				}
		        //}

		
		Integer curStyle = configDAO.getCurrentStyle();
		if (curStyle != null) {
			if (curStyle == 0) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SwingUtilities.updateComponentTreeUI(contentPane.getParent());

				} catch(Exception ex) {
					System.out.println("Unable to set LookAndFeel");
				}
			} else if (curStyle == 1) {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					SwingUtilities.updateComponentTreeUI(contentPane.getParent());
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			} else if (curStyle == 2) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					SwingUtilities.updateComponentTreeUI(contentPane.getParent());
					exercisesTabTextArea.setFont(getFont());
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
			
		}
		tableSchedule = new JTable() {
			@Override 
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 					try {
						UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					} catch (Throwable ex) {
						ex.printStackTrace();
					}
					SwingUtilities.updateComponentTreeUI(contentPane.getParent());
				}
			});
			settingsMenu.add(altStyleMenuItem);
			
			JMenuItem ClassicStyleMenuItem = new JMenuItem("Классический стиль");
			ClassicStyleMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						SwingUtilities.updateComponentTreeUI(contentPane.getParent());

					} catch(Exception ex) {
						System.out.println("Unable to set LookAndFeel");
					}
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		repaintPlansPanel();
		repaintExerciseTrainingPlanTab(exercises);
		
		panelPlan.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "План на день", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panelScrollPlanList = new JPanel();
		panelScrollPlanList.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Список планов", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout gl_panelSchedule = new GroupLayout(panelSchedule);
		gl_panelSchedule.setHorizontalGroup(
			gl_panelSchedule.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSchedule.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSchedule.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
						.addGroup(gl_panelSchedule.createSequentialGroup()
							.addComponent(calendar, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelPlan, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panelScrollPlanList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelSchedule.setVerticalGroup(
			gl_panelSchedule.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelSchedule.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSchedule.createParallelGroup(Alignment.LEADING)
						.addComponent(panelPlan, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
						.addComponent(panelScrollPlanList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(calendar, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JButton delTaskButton = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0443\u043F\u0440\u0430\u0436\u043D\u0435\u043D\u0438\u0435");
	

		GroupLayout gl_panelScrollPlanList = new GroupLayout(panelScrollPlanList);
		gl_panelScrollPlanList.setHorizontalGroup(
			gl_panelScrollPlanList.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPanePlanList, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
				.addComponent(btnCreatePlan, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
				.addComponent(btnDeletePlan, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
				.addComponent(delTaskButton, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
		);
		gl_panelScrollPlanList.setVerticalGroup(
			gl_panelScrollPlanList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelScrollPlanList.createSequentialGroup()
					.addComponent(scrollPanePlanList, GroupLayout.PREFERRED_SIZE, 157, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCreatePlan)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDeletePlan)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(delTaskButton))
		);



		panelPlanList.setBackground(new Color(255,255,255));
		panelPlanList.setLayout(new GridLayout(0, 1));
		scrollPanePlanList.setViewportView(panelPlanList);
		panelScrollPlanList.setLayout(gl_panelScrollPlanList);
		
		JScrollPane scrollPanePlan = new JScrollPane();
		GroupLayout gl_panelPlan = new GroupLayout(panelPlan);
		gl_panelPlan.setHorizontalGroup(
			gl_panelPlan.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPanePlan, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
		);
		gl_panelPlan.setVerticalGroup(
			gl_panelPlan.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPanePlan, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
		);


		dropPanel.setToolTipText("");
		dropPanel.setBackground(SystemColor.window);
		scrollPanePlan.setViewportView(dropPanel);
		
		
		
		//Клик на строке таблицы
		/*tableSchedule.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				

				//currentTraining.getTasks().remove(row);
				//trainingPlanDAO.deleteTraining(currentTrainingPlan.planId, currentTraining.getDate());
				//Изменение тренировки
			}
		});*/
		
		//Удаление таска
		delTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//printTrainingPlans();
				if (currentTrainingPlan != null) {
					if (currentTraining != null) {

						ArrayList<Task> tsks = currentTraining.getTasks();
						tsks.remove(tableSchedule.getSelectedRow());
						//tableSchedule.remove(tableSchedule.getSelectedRow());
						repaintExerciseTableInTrainingPlansTab();
						if (tsks.size() > 0) {
							currentTraining.setTasks(tsks);
							trainingPlanDAO.deleteTraining(currentTrainingPlan.getPlanId(), currentTraining.getDate());
							trainingPlanDAO.addTraining(currentTrainingPlan.getPlanId(), currentTraining);
						} else {
							trainingPlanDAO.deleteTraining(currentTrainingPlan.getPlanId(), currentTraining.getDate());
							currentTraining = null;
						}
					} else {
						System.out.println("currentTraining == null");
					}
				} else {
					System.out.println("currentTrainingPlan == null");
				}				
			}
		});
		
		tableSchedule.setToolTipText("Перетащите сюда упражнения");
		tableSchedule.setDragEnabled(true);
		tableSchedule.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Exercise", "ExerciseQuantity", "Repeats", "Wheight"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableSchedule.getColumnModel().getColumn(0).setPreferredWidth(130);
		tableSchedule.getColumnModel().getColumn(1).setPreferredWidth(20);
		tableSchedule.getColumnModel().getColumn(1).setMaxWidth(100);
		tableSchedule.getColumnModel().getColumn(2).setPreferredWidth(20);
		tableSchedule.getColumnModel().getColumn(2).setMaxWidth(100);
		tableSchedule.getColumnModel().getColumn(3).setPreferredWidth(20);
		tableSchedule.getColumnModel().getColumn(3).setMaxWidth(100);
	
        TransferHandler dnd = new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                if (!support.isDrop()) {
                    return false;
                }
                //only Strings
                if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    return false;
                }
                return true;
            }
        };
		
        tableSchedule.setTransferHandler(dnd);
        new MyDropTargetListener(tableSchedule);
		
		GroupLayout gl_dropPanel = new GroupLayout(dropPanel);
		gl_dropPanel.setHorizontalGroup(
			gl_dropPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tableSchedule, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
		);
		gl_dropPanel.setVerticalGroup(
			gl_dropPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tableSchedule, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
		);
		dropPanel.setLayout(gl_dropPanel);
		panelPlan.setLayout(gl_panelPlan);
		
		scrollPane.setViewportView(trainingPlansTabExercisesPanel);
		trainingPlansTabExercisesPanel.setLayout(new GridLayout(0, 5));
    	
		btnDeletePlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(new JFrame(), "Версия 0.2", "О программе", JOptionPane.con);
				
				//if (currentTrainingPlanJLabelWithId != null) {
					if (currentTrainingPlan != null) {
						if (JOptionPane.showConfirmDialog(new JFrame(), 
								"Вы уверены, что хотите удалить выбранный план тренировок?", 
								"Подтверждение удаления", 
								JOptionPane.YES_NO_OPTION) == 0) {
							panelPlanList.remove(currentTrainingPlanJLabelWithId);
							trainingPlanDAO.delete(currentTrainingPlan.getPlanId());
							trainingPlans.remove(currentTrainingPlan);
							currentTraining = null;
							currentTrainingPlan = null;
							configDAO.removeCurrentTrainingPlan();
							panelPlanList.revalidate();
							panelPlanList.repaint();
							repaintExerciseTableInTrainingPlansTab();
						}
					} else {
						System.out.println("currentTrainingPlan == null");
					}
				/*} else {
					System.out.println("currentTrainingPlanJLabelWithId == null)");
				}*/
			}
		});
		
		btnCreatePlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String planName = JOptionPane.showInputDialog("Введите название плана");
				if (planName != null) {
					if (!planName.trim().equals("")) {
						TrainingPlan trainingPlan = (new TrainingPlan(planName));
						trainingPlan.setPlanId(trainingPlanDAO.create(trainingPlan));
						JLabelWithId label = new JLabelWithId(planName, trainingPlan.getPlanId());
						label.setHorizontalAlignment(SwingConstants.CENTER);
						label.setHorizontalTextPosition(SwingConstants.CENTER);
						label.setVerticalTextPosition(SwingConstants.BOTTOM);
						trainingPlans.add(trainingPlan);
						panelPlanList.add(label);
					
						label.addMouseListener(trainingPlanItemMouseListener);
	
						panelPlanList.revalidate();
						panelPlanList.repaint();
						//printTrainingPlans();
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "Некорректное название плана", "Ошибка", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		repaintExerciseTableInTrainingPlansTab();
		panelSchedule.setLayout(gl_panelSchedule);
				
		repaintExerciseExercisesTab(exercises);
		

		MouseListener exercisesAddLableMouseListener = new MouseAdapter() {			
            public void mouseClicked(MouseEvent e) {
            /*	String exName = JOptionPane.showInputDialog("Введите название упражнения");
            	Exercise ex = new Exercise(exName);
            	//exerciseDAO.create(ex);
				JLabelWithId lblNewLabel_6 = new JLabelWithId(ex.getName(), exerciseDAO.create(ex));
				lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel_6.setHorizontalTextPosition(SwingConstants.CENTER);
				lblNewLabel_6.setIcon(new ImageIcon(mainForm.class.getResource("/resources/placeholder.png")));
				lblNewLabel_6.setVerticalTextPosition(SwingConstants.BOTTOM);
				exercisesTabExercisesPanel.add(lblNewLabel_6);
				lblNewLabel_6.addMouseListener(exercisesMouseListener);
				labelExercises.add(lblNewLabel_6);
				/////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				JLabelWithId newLabel = new JLabelWithId(ex.getName(), lblNewLabel_6.id);
				newLabel.setHorizontalAlignment(SwingConstants.CENTER);
				newLabel.setHorizontalTextPosition(SwingConstants.CENTER);
				newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
				newLabel.addMouseListener(trainingsMouseListener);
				trainingPlansTabExercisesPanel.add(newLabel);
				///Запилить другую проверку
				File newFile = new File("img/" + String.valueOf(ex.getId() + ".png"));
				if (newFile.exists()) {
					newLabel.setIcon(new ImageIcon(newFile.getPath()));
				} else {
					newLabel.setIcon(new ImageIcon(mainForm.class.getResource("/resources/placeholder.png")));
				}
		        DragSource ds0 = new DragSource();
		        ds0.createDefaultDragGestureRecognizer(newLabel, DnDConstants.ACTION_COPY, dlistener);*/
		        
            	/*for (ClientExercise labelExercise : labelExercises) {
            		labelExercise.jlabel.setBorder(BorderFactory.createEmptyBorder());
            	}
            	//lblNewLabel_12.setBorder(BorderFactory.createEmptyBorder());
            	exercisesTabTextArea.setVisible(true);
            	//exercisesTabTextArea.setEditable(false);
            	updateJButtonExercisesTab.setVisible(true);
            	deleteJButtonExercisesTab.setVisible(true);
                JComponent c = (JComponent)e.getSource();
             //   c.setBorder(BorderFactory.createEtchedBorder());
                
            	for (ClientExercise labelExercise : labelExercises) {
            		if (c == labelExercise.jlabel) {
            			exercisesTabTextArea.setText(exerciseDAO.findOne(labelExercise.exerciseId).getDescription());
            			System.out.println(exerciseDAO.findOne(labelExercise.exerciseId).getDescription());
            			labelExercise.jlabel.setBorder(BorderFactory.createEtchedBorder());
            			currentLabelExercise = labelExercise;
            		}
            	}*/
            }
        };
        
        fontItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontChooseDialog.getInstance().setVisible(true);
			}
		});
        
        
        
		/*
		 *ChartTabPanel methods
		 */
        
        JPanel exercisePanel = new JPanel();
        tabbedPane.addTab("\u0423\u043F\u0440\u0430\u0436\u043D\u0435\u043D\u0438\u044F", null, exercisePanel, null);
        
        JScrollPane exercisesScrollPane = new JScrollPane();
        
        exercisesTabTextArea = new JTextArea();
        exercisesTabTextArea.setLineWrap(true);
        exercisesTabTextArea.setEditable(false);
        exercisesTabTextArea.setBorder(UIManager.getBorder("ComboBox.border"));
        exercisesTabTextArea.setFont(getFont());
        
        addJButtonExercisesTab = new JButton("");
        addJButtonExercisesTab.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		CreateExerciseDialog.getInstance().setVisible(true);
        	}
        });
        addJButtonExercisesTab.setIcon(new ImageIcon(mainForm.class.getResource("/resources/add3-icon.png")));
        addJButtonExercisesTab.setPreferredSize(new Dimension(50, 50));
        addJButtonExercisesTab.setMinimumSize(new Dimension(50, 50));
        addJButtonExercisesTab.setMaximumSize(new Dimension(50, 50));
        
        updateJButtonExercisesTab = new JButton("");
        updateJButtonExercisesTab.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		EditExerciseDialog.getInstance().setVisible(true);
        	}
        });
        updateJButtonExercisesTab.setIcon(new ImageIcon(mainForm.class.getResource("/resources/edit.png")));
        updateJButtonExercisesTab.setMaximumSize(new Dimension(50, 50));
        updateJButtonExercisesTab.setMinimumSize(new Dimension(50, 50));
        updateJButtonExercisesTab.setPreferredSize(new Dimension(50, 50));
        
        deleteJButtonExercisesTab = new JButton("");
        deleteJButtonExercisesTab.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (currentExercise != null) {
				boolean canDelete = true;
				for (TrainingPlan pl : trainingPlans) {
					for (Training tr : pl.getTrainings()) {
						for (Task t : tr.getTasks()) {

							//System.out.println("Cant delete: " + t.getExecrise().getId());
							if (t.getExecrise().getId() == currentExercise.getId()) {
								canDelete = false;
								/*exercisesTabExercisesPanel.remove(currentExerciseJLabelWithId);
								exerciseDAO.delete(currentExerciseJLabelWithId.id);
								exercisesTabExercisesPanel.revalidate();
								exercisesTabExercisesPanel.repaint();*/
							}
						}
					}
				}
				if (canDelete) {
					exercisesTabExercisesPanel.remove(currentExerciseJLabelWithId);
					exerciseDAO.delete(currentExercise.getId());
					exercisesTabTextArea.setText("");
					System.out.println("Can delete");
					exercisesTabExercisesPanel.revalidate();
					exercisesTabExercisesPanel.repaint();
				} else {

					JOptionPane.showMessageDialog(new JFrame(), "Это упражнение используется в планах", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
        	} else {
        		JOptionPane.showMessageDialog(new JFrame(), "Не выбрано упражнение", "Ошибка", JOptionPane.ERROR_MESSAGE);
        	}
        	}
        });
        deleteJButtonExercisesTab.setIcon(new ImageIcon(mainForm.class.getResource("/resources/delete-icon.png")));
        deleteJButtonExercisesTab.setPreferredSize(new Dimension(50, 50));
        deleteJButtonExercisesTab.setMinimumSize(new Dimension(50, 50));
        deleteJButtonExercisesTab.setMaximumSize(new Dimension(50, 50));
        GroupLayout gl_exercisePanel = new GroupLayout(exercisePanel);
        gl_exercisePanel.setHorizontalGroup(
        	gl_exercisePanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_exercisePanel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(exercisesScrollPane, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_exercisePanel.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_exercisePanel.createSequentialGroup()
        					.addComponent(deleteJButtonExercisesTab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(updateJButtonExercisesTab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(addJButtonExercisesTab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(exercisesTabTextArea, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_exercisePanel.setVerticalGroup(
        	gl_exercisePanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_exercisePanel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_exercisePanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_exercisePanel.createSequentialGroup()
        					.addComponent(exercisesScrollPane, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        					.addContainerGap())
        				.addGroup(Alignment.TRAILING, gl_exercisePanel.createSequentialGroup()
        					.addComponent(exercisesTabTextArea, GroupLayout.PREFERRED_SIZE, 351, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_exercisePanel.createParallelGroup(Alignment.LEADING)
        						.addComponent(deleteJButtonExercisesTab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(updateJButtonExercisesTab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(addJButtonExercisesTab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        					.addContainerGap(17, 17))))
        );
        
        
        exercisesScrollPane.setViewportView(exercisesTabExercisesPanel);
        exercisesTabExercisesPanel.setLayout(new GridLayout(0, 1));
        exercisePanel.setLayout(gl_exercisePanel);
        
        
        tabbedPane.addTab("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043A\u0430", null, chartTabPanel, null);
        
        String[] exercisesNamesArray = new String[exercisesNamesArrayList.size()];
        for (int i = 0; i < exercisesNamesArrayList.size(); i++) {
        	exercisesNamesArray[i] = exercisesNamesArrayList.get(i);
        }
        String[] exercisesNames = new String[exercises.size()];
        for (int i = 0; i < exercises.size(); i++) {
        	exercisesNames[i] = exercises.get(i).getName();
        }
        
        //
        ArrayList<String> categoriesArrayList = new ArrayList<String>();
        for (Exercise exercise : exercises) {
        	if (!categoriesArrayList.contains(exercise.getCategory())) {
        		categoriesArrayList.add(exercise.getCategory());
        	}
        }
        String[] categories = new String[categoriesArrayList.size()];
        categories = categoriesArrayList.toArray(categories);
        /*for (Exercise ex : exerciseDAO.findAll()) {
        	exercisesNamesArrayListt.add(ex.getName());
        }
        String[] exercisesNamesArrayy = new String[exercisesNamesArrayListt.size()];
        for (int i = 0; i < exercisesNamesArrayListt.size(); i++) {
        	exercisesNamesArrayy[i] = exercisesNamesArrayListt.get(i);
        }*/
        
        JComboBox timeComboBox = new JComboBox();
        
        String[] timeItems = {
          "Неделя",
          "Месяц",
          "Год"
        };
        
        JPanel statisticPanel = new JPanel();
        statisticPanel.setBorder(new TitledBorder(null, "\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043A\u0430", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0420\u0435\u0437\u0443\u043B\u044C\u0442\u0430\u0442", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

        GroupLayout gl_chartTabPanel = new GroupLayout(chartTabPanel);
        gl_chartTabPanel.setHorizontalGroup(
        	gl_chartTabPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_chartTabPanel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(statisticPanel, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_chartTabPanel.setVerticalGroup(
        	gl_chartTabPanel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_chartTabPanel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_chartTabPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(statisticPanel, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
        				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE))
        			.addContainerGap())
        );
        
        JLabel lblNewLabel_3 = new JLabel("\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u0443\u043F\u0440\u0430\u0436\u043D\u0435\u043D\u0438\u0435 *");
        
        JLabel lblNewLabel_4 = new JLabel("\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u0434\u0430\u0442\u0443 *");
        
        JLabel lblNewLabel_5 = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u043F\u043E\u0434\u0445\u043E\u0434\u043E\u0432");
        
        JLabel lblNewLabel_6 = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u043F\u043E\u0432\u0442\u043E\u0440\u0435\u043D\u0438\u0439");
        
        JLabel lblNewLabel_7 = new JLabel("\u0412\u0435\u0441");
        

        JComboBox resultExerciseComboBox = new JComboBox(exercisesNames);
        
        JDateChooser dateChooser = new JDateChooser();
        
        scTextField = new JTextField();
        scTextField.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		scTextField.setText("");
        	}
        });
        scTextField.setText("1");
        scTextField.setColumns(10);
        
        rcTextField = new JTextField();
        rcTextField.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		rcTextField.setText("");
        	}
        });
        rcTextField.setText("1");
        rcTextField.setColumns(10);
        
        wTextField = new JTextField();
        wTextField.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		wTextField.setText("");
        	}
        });
        wTextField.setText("0");
        wTextField.setColumns(10);
        
        JButton addResultButton = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0440\u0435\u0437\u0443\u043B\u044C\u0442\u0430\u0442");
        addResultButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		boolean canAdd = false;
        		if (dateChooser.getDate() != null) {
        			if (rcTextField.getText() != "" && wTextField.getText() != "" && scTextField.getText() != "") {
        				int rc;
        				int sc;
        				int w;
        				try {
        					rc = Integer.valueOf(rcTextField.getText());
        					sc = Integer.valueOf(scTextField.getText());
        					w = Integer.valueOf(wTextField.getText());
        					if (rc > 0 && sc > 0 && w >= 0) {
        						canAdd = true;
        					} else {
        						JOptionPane.showMessageDialog(new JFrame(), "Количество повторений и подходов должно быть больше нуля, вес не может быть отрицательным", "Ошибка", JOptionPane.ERROR_MESSAGE);
        					}
        				} catch (Exception ex) {
        					JOptionPane.showMessageDialog(new JFrame(), "Введите корректные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
        				}
        			} else {
        				JOptionPane.showMessageDialog(new JFrame(), "Заполните все поля", "Напоминание", JOptionPane.INFORMATION_MESSAGE);
        			}
        		} else {
        			JOptionPane.showMessageDialog(new JFrame(), "Выберите дату", "Напоминание", JOptionPane.INFORMATION_MESSAGE);
        		}
        		if (canAdd == true) {
        			Result result = new Result(dateChooser.getDate(), 
        					findExercise(resultExerciseComboBox.getSelectedIndex()), 
        					Integer.valueOf(rcTextField.getText()), 
        					Integer.valueOf(scTextField.getText()),
        					Integer.valueOf(wTextField.getText()));
        	        		resultDAO.create(result);
        	        		results.add(result);
        	        		JOptionPane.showMessageDialog(new JFrame(), "Результат успешно сохранен", "Уведомление", JOptionPane.INFORMATION_MESSAGE);
        		}
        		
        	}
        });
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNewLabel_3)
        				.addComponent(lblNewLabel_4)
        				.addComponent(lblNewLabel_5)
        				.addComponent(lblNewLabel_6)
        				.addComponent(lblNewLabel_7))
        			.addGap(18)
        			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        				.addComponent(addResultButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        				.addComponent(wTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        				.addComponent(dateChooser, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        				.addComponent(scTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        				.addComponent(rcTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        				.addComponent(resultExerciseComboBox, Alignment.LEADING, 0, 196, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_3)
        				.addComponent(resultExerciseComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblNewLabel_4)
        				.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_5)
        				.addComponent(scTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_6)
        				.addComponent(rcTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_7)
        				.addComponent(wTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addComponent(addResultButton)
        			.addContainerGap(79, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        
                exerciseСomboBox_1 = new JComboBox(exercisesNames);
        timeСomboBox = new JComboBox(timeItems);
        
        JButton chartButton = new JButton("\u041E\u0442\u043E\u0431\u0440\u0430\u0437\u0438\u0442\u044C \u0433\u0440\u0430\u0444\u0438\u043A");
        chartButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		
        		exerciseСomboBox_1.getSelectedIndex();
        		timeСomboBox.getSelectedIndex();
        		drawChart(timeСomboBox.getSelectedIndex(), exerciseСomboBox_1.getSelectedIndex());
        	}
        });
                
        GroupLayout gl_statisticPanel = new GroupLayout(statisticPanel);
        gl_statisticPanel.setHorizontalGroup(
        	gl_statisticPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_statisticPanel.createSequentialGroup()
        			.addGap(42)
        			.addGroup(gl_statisticPanel.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(timeСomboBox, 0, 184, Short.MAX_VALUE)
        				.addComponent(exerciseСomboBox_1, 0, 184, Short.MAX_VALUE)
        				.addComponent(chartButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addContainerGap(44, Short.MAX_VALUE))
        );
        gl_statisticPanel.setVerticalGroup(
        	gl_statisticPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_statisticPanel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(exerciseСomboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(timeСomboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(chartButton)
        			.addContainerGap(244, Short.MAX_VALUE))
        );
        statisticPanel.setLayout(gl_statisticPanel);

        chartTabPanel.setLayout(gl_chartTabPanel);
        

        
        for (Exercise ex : exerciseDAO.findAll()) {
        	exercisesNamesArrayList.add(ex.getName());
        }
		contentPane.setLayout(gl_contentPane);
	}
	
	public void repaintPlansPanel() {
		for (Component component : panelPlanList.getComponents()) {
    		JLabelWithId jLabelWithId = (JLabelWithId) component;
    		jLabelWithId.setBorder(BorderFactory.createEmptyBorder());
    	}
		for (TrainingPlan trainingPlan : trainingPlans) {
		
			JLabelWithId newLabel = new JLabelWithId(trainingPlan.getName(), trainingPlan.getPlanId());
			newLabel.setHorizontalAlignment(SwingConstants.CENTER);
			newLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
			
			
			newLabel.addMouseListener(trainingPlanItemMouseListener);
			if (currentTrainingPlan != null) {
				if (trainingPlan.getPlanId() == currentTrainingPlan.getPlanId()) {
					newLabel.setBorder(BorderFactory.createEtchedBorder());
					currentTrainingPlanJLabelWithId = newLabel;
				}
			}
			panelPlanList.add(newLabel);
		}
      	
    	
    	//JComponent c = (JComponent)e.getSource();
      //  c.setBorder(BorderFactory.createEtchedBorder());
        //JLabelWithId jLabelWithId =  (JLabelWithId) c;
       // currentTrainingPlan = findTrainingPlan(jLabelWithId.id);
       // configDAO.setCurrentTrainingPlan(currentTrainingPlan.getPlanId());
      //  currentTraining = findTraining(currentTrainingPlan.getTrainings(), calendar.getDate());
       // repaintExerciseTableInTrainingPlansTab();
	}
	
	public void repaintExerciseExercisesTab(ArrayList<Exercise> exs) {
		for (Exercise exercise : exs) {
			JLabelWithId lblNewLabel = new JLabelWithId(exercise.getName(), exercise.getId());
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			lblNewLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
			///Запилить другую проверку
			File newFile = new File("img/" + String.valueOf(exercise.getId() + ".png"));
			if (newFile.exists()) {
				lblNewLabel.setIcon(new ImageIcon(newFile.getPath()));
			} else {
				lblNewLabel.setIcon(new ImageIcon(mainForm.class.getResource("/resources/placeholder.png")));
			}
			exercisesTabExercisesPanel.add(lblNewLabel);
			lblNewLabel.addMouseListener(exercisesMouseListener);
		}
	}
	
	public void repaintExerciseTrainingPlanTab(ArrayList<Exercise> exs) {
		trainingPlansTabExercisesPanel.removeAll();
		for (Exercise exercise : exs) {
			JLabelWithId lblNewLabel = new JLabelWithId(exercise.getName(), exercise.getId());
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			lblNewLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
			///Запилить другую проверку
			File newFile = new File("img/" + String.valueOf(exercise.getId() + ".png"));
			if (newFile.exists()) {
				lblNewLabel.setIcon(new ImageIcon(newFile.getPath()));
			} else {
				lblNewLabel.setIcon(new ImageIcon(mainForm.class.getResource("/resources/placeholder.png")));
			}
	        DragSource ds0 = new DragSource();
	        ds0.createDefaultDragGestureRecognizer(lblNewLabel, DnDConstants.ACTION_COPY, dlistener);
	        trainingPlansTabExercisesPanel.add(lblNewLabel);
		}
	}
	
	public static void repaintExerciseTableInTrainingPlansTab() {
		for(int i = tableSchedule.getRowCount() - 1; i >=0; i--)
		{
			((DefaultTableModel)tableSchedule.getModel()).removeRow(i);
		}
		/*if (currentTrainingPlan != null) {
			for (Training training : currentTrainingPlan.getTrainings()) {
				System.out.println("ololo");
				if ((training.getDate().getDate() == calendar.getDate().getDate())
						&& (training.getDate().getMonth() == calendar.getDate().getMonth())
						&& (training.getDate().getYear() == calendar.getDate().getYear())) {
					for (Task task : training.getTasks()) {
						Vector<String> newRow = new Vector<String>();
		                newRow.add(task.getExecrise().getName());
		                DefaultTableModel data = (DefaultTableModel) tableSchedule.getModel();
		                data.addRow(newRow);
		                tableSchedule.setModel(data);
					}
				}
			}*/
		if (currentTrainingPlan != null) {
				if (currentTraining != null) {
					for (Task task : currentTraining.getTasks()) {
						Vector<String> newRow = new Vector<String>();
		                newRow.add(task.getExecrise().getName());
		                DefaultTableModel data = (DefaultTableModel) tableSchedule.getModel();
	                    newRow.add(String.valueOf(task.getSetsCount()));
	                    newRow.add(String.valueOf(task.getRepetitionsCount()));
	                    newRow.add(String.valueOf(task.getWeight()));
		                data.addRow(newRow);
		                tableSchedule.setModel(data);
					}

				}
		} else {
			System.out.println("currentTrainingPlan == null");
		}
		/*or(int i = tableSchedule.getRowCount() - 1; i >=0; i--)
		{
			((DefaultTableModel)tableSchedule.getModel()).removeRow(i);
		}
		if (currentTrainingPlan != null) {
			TrainingPlan trainingPlan = trainingPlanDAO.findOne(currentTrainingPlan.id);
			if (trainingPlan != null) {
				ArrayList<Training> trainings = trainingPlan.getTrainings();
				for (Training training : trainings) {
					if ((training.getDate().getDate() == calendar.getDate().getDate())
							&& (training.getDate().getMonth() == calendar.getDate().getMonth())
							&& (training.getDate().getYear() == calendar.getDate().getYear())) {
						
						currentTraining = training;
						for (Task task : currentTraining.getTasks()) {
			                Vector<String> newRow = new Vector<String>();
			                newRow.add(task.getExecrise().getName());
			                DefaultTableModel data = (DefaultTableModel) tableSchedule.getModel();
			                data.addRow(newRow);
			                tableSchedule.setModel(data);
						}

					}
				}
			}
		}*/
		/*for (TrainingPlan trainingPlan : trainingPlans) {

			JLabelWithId newLabel = new JLabelWithId(trainingPlan.getName(), trainingPlan.getPlanId());
			newLabel.setHorizontalAlignment(SwingConstants.CENTER);
			newLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			newLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
			panelPlanList.add(newLabel);
			
			newLabel.addMouseListener(trainingPlanItemMouseListener);*/
			/*clientTrainingPlans.add(newLabel);
			if (curPlanId == trainingPlan.getPlanId()) {
				currentTrainingPlan = newLabel;
				configDAO.setCurrentTrainingPlan(curPlanId);
				newLabel.setBorder(BorderFactory.createEtchedBorder());
			}*/
		//}
	}
	
    public void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
	public static MouseListener exercisesMouseListener = new MouseAdapter() {			
        public void mouseClicked(MouseEvent e) {
        	System.out.println("Click on ex");
        	/*for (JLabelWithId labelExercise : labelExercises) {
        		labelExercise.setBorder(BorderFactory.createEmptyBorder());
        	}*/
        	for (Component component : exercisesTabExercisesPanel.getComponents()) {
        		try {
        		JLabelWithId jLabelWithId = (JLabelWithId) component;
        		jLabelWithId.setBorder(BorderFactory.createEmptyBorder());
        		} catch (Exception ex) {
        			
        		}
        	}
        	//addExerciseLabelExercisesTab.setBorder(BorderFactory.createEmptyBorder());
        	//exercisesTabTextArea.setVisible(true);
        	//exercisesTabTextArea.setEditable(false);
//        	updateJButtonExercisesTab.setVisible(true);
//        	deleteJButtonExercisesTab.setVisible(true);
        	
            JComponent c = (JComponent)e.getSource();
        	//hsrth
            c.setBorder(BorderFactory.createEtchedBorder());
            JLabelWithId jLabelWithId = (JLabelWithId) c;
            jLabelWithId.setBorder(BorderFactory.createEtchedBorder());
        	currentExercise = findExercise(jLabelWithId.id);
        	currentExerciseJLabelWithId = jLabelWithId;
        	exercisesTabTextArea.setText(currentExercise.getDescription());
        /*	for (JLabelWithId labelExercise : labelExercises) {
        		if ((JComponent)e.getSource() == labelExercise) {
        			
        			//////Поменять?
        			exercisesTabTextArea.setText(exerciseDAO.findOne(labelExercise.id).getDescription());
        			//System.out.println(exerciseDAO.findOne(labelExercise.id).getDescription());
        			labelExercise.setBorder(BorderFactory.createEtchedBorder());
        			currentLabelExercise = labelExercise;
        		}
        	}*/
        }
    };
    //Обработчик клика на лейбла плана в списке
    public static MouseListener trainingPlanItemMouseListener = new MouseAdapter() {			
        public void mouseClicked(MouseEvent e) {
        	
        	for (Component component : panelPlanList.getComponents()) {
        		JLabelWithId jLabelWithId = (JLabelWithId) component;
        		jLabelWithId.setBorder(BorderFactory.createEmptyBorder());
        	}
        	
        	JComponent c = (JComponent)e.getSource();
            c.setBorder(BorderFactory.createEtchedBorder());
            JLabelWithId jLabelWithId =  (JLabelWithId) c;
            currentTrainingPlan = findTrainingPlan(jLabelWithId.id);
            currentTrainingPlanJLabelWithId = jLabelWithId;
            configDAO.setCurrentTrainingPlan(currentTrainingPlan.getPlanId());
            currentTraining = findTraining(currentTrainingPlan.getTrainings(), calendar.getDate());
            repaintExerciseTableInTrainingPlansTab();
            
            
            
        	/*currentTraining = null;
        	for(int i = tableSchedule.getRowCount() - 1; i >=0; i--)
            {
                ((DefaultTableModel)tableSchedule.getModel()).removeRow(i);
            }
        	for (JLabelWithId labelExercise : clientTrainingPlans) {
        		labelExercise.setBorder(BorderFactory.createEmptyBorder());
        	}
            JComponent c = (JComponent)e.getSource();
           // c.setBorder(BorderFactory.createEtchedBorder());
            
        	for (JLabelWithId labelExercise : clientTrainingPlans) {
        		if (c == labelExercise) {
        			labelExercise.setBorder(BorderFactory.createEtchedBorder());
        			currentTrainingPlan = labelExercise;
        			configDAO.setCurrentTrainingPlan(labelExercise.id);
        		}
        	}
        	for(int i = tableSchedule.getRowCount() - 1; i >=0; i--)
			{
				((DefaultTableModel)tableSchedule.getModel()).removeRow(i);
			}
			if (currentTrainingPlan != null) {
				TrainingPlan trainingPlan = trainingPlanDAO.findOne(currentTrainingPlan.id);
				if (trainingPlan != null) {
					ArrayList<Training> trainings = trainingPlan.getTrainings();
					for (Training training : trainings) {
						if ((training.getDate().getDate() == calendar.getDate().getDate())
								&& (training.getDate().getMonth() == calendar.getDate().getMonth())
								&& (training.getDate().getYear() == calendar.getDate().getYear())) {
							currentTraining = training;
							for (Task task : currentTraining.getTasks()) {
				                Vector<String> newRow = new Vector<String>();
				                newRow.add(task.getExecrise().getName());
				                DefaultTableModel data = (DefaultTableModel) tableSchedule.getModel();
				                data.addRow(newRow);
				                tableSchedule.setModel(data);
							}

						}
					}
				}
			}*/
        }
    };
    private JTextField scTextField;
    private JTextField rcTextField;
    private JTextField wTextField;
    private JMenu settingsMenu_1;
    private JMenuItem altStyleMenuItem_1;
    
    public static TrainingPlan findTrainingPlan(int id) {
    	TrainingPlan result = null;
    	for (TrainingPlan trainingPlan : trainingPlans) {
    		if (trainingPlan.getPlanId() == id) {
    			result = trainingPlan;
    		}
    	}
    	return result;
    }
    
    public static Exercise findExercise(int id) {
    	Exercise result = null;
    	for (Exercise exercise : exercises) {
    		if (exercise.getId() == id) {
    			result = exercise;
    		}
    	}
    	return result;
    }
    
    public static Training findTraining(ArrayList<Training> array, Date date) {
    	Training result = null;
    	for (Training training : array) {
    		if (training.getDate().getDate() == date.getDate()
    				&& training.getDate().getMonth() == date.getMonth()
    				&& training.getDate().getYear() == date.getYear()) {
    			result = training;
    		}
    	}
    	return result;
    }
    
    public static void printTrainingPlans() {
    	System.out.println("");
    	for (TrainingPlan trainingPlan : trainingPlans) {
    		System.out.println("Training plan: " + "id = " + trainingPlan.getPlanId() + ", name = " + trainingPlan.getName() + ";");
    		for (Training training : trainingPlan.getTrainings()) {
    			System.out.println("    Training: " + "date = " + training.getDate().toGMTString() + ";");
    			for (Task task : training.getTasks()) {
    				System.out.println("        Task: " 
    									+ "id = " 
    									+ task.getId() 
    									+ ", exercise name = " 
    									+ task.getExecrise().getName()
    									+ ", exercise id = "
    									+ task.getExecrise().getId()
    									+ ", rc = "
    									+ task.getRepetitionsCount()
    									+ ", sc = "
    									+ task.getSetsCount()
    									+ ", w = "
    									+ task.getWeight()
    									+ ";");
    			}
    		}
    	}
    }
   /* public DefaultCategoryDataset Days(int days, DefaultCategoryDataset dataset) { 
    	Date today = new Date();
    	Date dt = new Date(today.getYear(), today.getMonth(), today.getDate() + days);
    	System.out.println(dt.toString());
        	for(Result result : results) {
        		
        		//if (входит в интервал)
        		dataset.setValue(result.getWeight(), "Вес", result.getDate().toString().substring(0, 10));
        	}

     return dataset;
     }*/
       
       public void drawChart(int days, int exId){
    	   DefaultCategoryDataset dataset = new DefaultCategoryDataset();
       	Date today = new Date();
       	Date dt = null;
       	switch (days) {
       	case 0:

       		dt = new Date(today.getYear(), today.getMonth(), today.getDate() - 7);
       		break;
       	case 1:
       		dt = new Date(today.getYear(), today.getMonth() - 1, today.getDate());
       		break;
       	case 2:
       		dt = new Date(today.getYear() - 1, today.getMonth(), today.getDate());
       		break;
       	}
       	System.out.println(dt.toString());
       	Date a = new Date();
       	Collections.sort(results, new Comparator<Result>() {
			@Override
			public int compare(Result arg0, Result arg1) {
				return arg0.getDate().compareTo(arg1.getDate());
			}
       	});
        for(int i = 0; i < results.size(); i++) {
        	//System.out.println(results.get(i).getExercise());
        	if (exercises.get(exId).getId() == results.get(i).getExercise().getId()) {
	        	System.out.println(results.get(i).getDate().toLocaleString().substring(0, 10));
	        	if (results.get(i).getDate().after(dt) && results.get(i).getDate().before(a)) {
	        		dataset.setValue(results.get(i).getWeight(), "Вес", results.get(i).getDate().toLocaleString().substring(0, 10));
	        	} 
	        	
	        	System.out.println(String.valueOf(results.get(i).getDate().getYear()) + "  " + String.valueOf(dt.getYear()));
	        	System.out.println(String.valueOf(results.get(i).getDate().getMonth()) + "  " + String.valueOf(dt.getMonth()));
	        	System.out.println(String.valueOf(results.get(i).getDate().getDate()) + "  " + String.valueOf(dt.getDate()));
        	}
        }
    	   
        JFreeChart timechart = ChartFactory.createBarChart(  
                "Статистика", // Title  
                "Время",         // X-axis Label  
                "Вес",       // Y-axis Label  
                dataset,        // Collection
                PlotOrientation.VERTICAL,      //Plot orientation  
                false,          // Show legend  
                true,          // Use teletypes  
                false          // Generate URLs  
            );
        
        final CategoryPlot plot = timechart.getCategoryPlot();
           plot.setRangeGridlinePaint(Color.red);
        timechart.setBackgroundPaint(Color.white);     
        JFrame frame = new JFrame("Chart");
        frame.getContentPane().add(new ChartPanel(timechart));
        frame.setSize(500,400);
        frame.show();
       /* chart_panel.add(new ChartPanel(timechart));
        chart_panel.setSize(400,300);  
        
        chart_panel.removeAll();
        chart_panel.revalidate();
        chart_panel.add(new ChartPanel(timechart));
        chart_panel.setSize(400,300);  */
       }
}
