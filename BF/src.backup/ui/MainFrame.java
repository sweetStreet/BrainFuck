package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteServer;
import java.rmi.server.RemoteStub;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;

import rmi.RemoteHelper;
import service.IOService;


public class MainFrame extends JFrame {
	private JTextArea codeTextArea;
	private JTextArea inputTextArea;
	private JLabel resultLabel;
	private JFrame frame;
	private JLabel labelUser;
	private JMenuItem redoMenuItem;
	private JMenuItem undoMenuItem;
	boolean isNewSuccess=false;
	String presentFile="";
	/*ArrayList<String> undo=new ArrayList<String>();
	int indexUndo=0;//撤销
	ArrayList<String> redo=new ArrayList<String>();
	int indexRedo=-1;//重做
	*/
	public MainFrame() {
		 // 设置风格
		try {
			String lookandfeel="javax.swing.plaf.nimbus.NimbusLookAndFeel";
			UIManager.setLookAndFeel(lookandfeel);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		//设置字体
			//Font font=new Font("Monospaced",Font.BOLD,19);
		    Font font=new Font("Lovely",Font.BOLD,19);
			Font fontMenu=new Font("Cute",Font.BOLD,15);
			UIManager.put("TextArea.font",font);
				
		// 创建窗体
		frame = new JFrame("BF Client");
		frame.setLayout(null);
		Container contentpane=frame.getContentPane();
		
		//设置图标
		ImageIcon icon=new ImageIcon("Image\\icon.png");
		frame.setIconImage(icon.getImage());
		
		//file菜单，其中包括new，open，save，exit
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.setFont(fontMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		newMenuItem.setFont(fontMenu);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		openMenuItem.setFont(fontMenu);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		saveMenuItem.setFont(fontMenu);
		//JMenuItem exitMenuItem = new JMenuItem("Exit");
		//fileMenu.add(exitMenuItem);
		frame.setJMenuBar(menuBar);
		//frame.setUndecorated(true);
		//frame.setLocationRelativeTo(null);
		
		//run菜单，其中包括execute
		JMenu runMenu=new JMenu("Run");
		menuBar.add(runMenu);
		runMenu.setFont(fontMenu);
		JMenuItem executeMenuItem=new JMenuItem("Execute");
		executeMenuItem.setFont(fontMenu);
		runMenu.add(executeMenuItem);
		
		//version菜单，其中包括各版本
		JMenu versionMenu=new JMenu("History");
		versionMenu.setFont(fontMenu);
		menuBar.add(versionMenu);
		JMenuItem versionMenuItem=new JMenuItem("Version");
		versionMenuItem.setFont(fontMenu);
		versionMenu.add(versionMenuItem);
		
		//可以实现撤销和重做功�?
		JMenu editMenu=new JMenu("Edit");
		editMenu.setFont(fontMenu);
		menuBar.add(editMenu);
		undoMenuItem=new JMenuItem("Undo");
		undoMenuItem.setFont(fontMenu);
		editMenu.add(undoMenuItem);
		redoMenuItem=new JMenuItem("Redo");
		redoMenuItem.setFont(fontMenu);
		editMenu.add(redoMenuItem);
		redoMenuItem.setEnabled(false);
		undoMenuItem.setEnabled(false);
		
		//用户菜单，其中包括注�?,登录和�??�?
		JMenu userMenu=new JMenu("User");
		userMenu.setFont(fontMenu);
		menuBar.add(userMenu);
		JMenuItem signupMenuItem=new JMenuItem("Sign Up");
		userMenu.add(signupMenuItem);
		signupMenuItem.setFont(fontMenu);
		JMenuItem loginMenuItem=new JMenuItem("Log in");
		userMenu.add(loginMenuItem);
		loginMenuItem.setFont(fontMenu);
		JMenuItem logoutMenuItem=new JMenuItem("Log out");
		logoutMenuItem.setFont(fontMenu);
		userMenu.add(logoutMenuItem);
		
		//右上角用于显示登录用户用户名的label
		Icon loginImage=new ImageIcon("Image\\login.png");
		labelUser=new JLabel(loginImage);
		menuBar.add(labelUser);
		
		//代码�?
		codeTextArea = new JTextArea();
		codeTextArea.setLineWrap(true);
		TitledBorder t1=new TitledBorder("Code Section");
		//t1.setTitleColor(Color.white);
		t1.setTitleFont(font);
		codeTextArea.setBorder(t1);
		codeTextArea.setBounds(0, 0, 895, 275);
		//codeTextArea.setMargin(new Insets(10, 10, 10, 10));
		codeTextArea.setBackground(Color.white);
		contentpane.add(codeTextArea);
		
		//输入�?
		inputTextArea=new JTextArea();
		inputTextArea.setLineWrap(true);
		TitledBorder t2=new TitledBorder("Input Section");
		t2.setTitleFont(font);
		inputTextArea.setBorder(t2);
		//inputTextArea.setMargin(new Insets(10,10,10,10));
		inputTextArea.setBackground(Color.WHITE);
		inputTextArea.setBounds(0, 270, 450, 280);
		contentpane.add(inputTextArea);
		
		// 输出区显示结�?
		resultLabel = new JLabel();
		resultLabel.setFont(font);
		resultLabel.setBackground(Color.WHITE);
		resultLabel.setOpaque(true) ;
		resultLabel.setHorizontalAlignment(JLabel.LEFT);
		resultLabel.setVerticalAlignment(JLabel.TOP);
		TitledBorder t3=new TitledBorder("Result");
		//t3.setTitleColor(Color.white);
		t3.setTitleFont(font);
		resultLabel.setBorder(t3);
		resultLabel.setBounds(445, 270, 450, 280);
		contentpane.add(resultLabel);
		
		//添加监听
		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		//exitMenuItem.addActionListener(new MenuItemActionListener());
		executeMenuItem.addActionListener(new ExecuteActionListener());
		signupMenuItem.addActionListener(new SignupActionListener());
		loginMenuItem.addActionListener(new LoginActionListener());
		logoutMenuItem.addActionListener(new LogoutActionListener());
		versionMenuItem.addActionListener(new VersionActionListener());
		redoMenuItem.addActionListener(new RedoActionListener());
		undoMenuItem.addActionListener(new UndoActionListener());

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 600);
		frame.setLocation(210, 50);
		frame.setVisible(true);
	}

	//新建文件
	class NewActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(labelUser.getText()==null){
				JOptionPane.showMessageDialog(frame,"请先登录","",JOptionPane.ERROR_MESSAGE);
			}
			else{
				codeTextArea.setText("");
				inputTextArea.setText("");
				resultLabel.setText("");
				Object filenameObject=JOptionPane.showInputDialog(frame, "请输入文件名（不加扩展名�?",""
						,JOptionPane.INFORMATION_MESSAGE);
				String filename=String.valueOf(filenameObject);
				if(filename.equals("")){
					JOptionPane.showMessageDialog(frame, "文件名不能为�?","",JOptionPane.WARNING_MESSAGE);
				}else if(filenameObject==null){
				}else{
					presentFile=filename.replace(".txt", "");
					/*try {
						isNewSuccess=RemoteHelper.getInstance().getIOService().writeFile(";;", labelUser.getText(), filename);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}*/
					JOptionPane.showMessageDialog(frame, "新建成功","",JOptionPane.INFORMATION_MESSAGE);
				}
			}
	}
}
	
	//打开文件
	class OpenActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(labelUser.getText()==null){
				JOptionPane.showMessageDialog(frame,"请先登录","",JOptionPane.ERROR_MESSAGE);
			}else{
				ArrayList<String> list=new ArrayList<String>();
				try {
					list=RemoteHelper.getInstance().getIOService().readFileList(labelUser.getText());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				Object[] listObject=new Object[list.size()];
				for(int i=0;i<list.size();i++){
					listObject[i]=list.get(i);
				}
				Object filenameObject=JOptionPane.showInputDialog(frame, "请�?�择你要打开的文�?",""
						,JOptionPane.INFORMATION_MESSAGE,null,listObject,null);
				String filename=String.valueOf(filenameObject);
				presentFile=filename.replace(".txt", "");
				String result="";
				try {
					result=RemoteHelper.getInstance().getIOService().readFile(labelUser.getText(), filename);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				String[] temp=result.split(";",-1);
				codeTextArea.setText(temp[0]);
				inputTextArea.setText(temp[1]);
				resultLabel.setText(temp[2]);
			}
	}
	}
	
	//保存文件
	class SaveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(labelUser.getText()==null){
				JOptionPane.showMessageDialog(frame,"请先登录","",JOptionPane.ERROR_MESSAGE);
			}else{
				/*if(presentFile.equals("")){
					Object filenameObject=JOptionPane.showInputDialog(null, "请输入文件名(不加扩展�?)","",JOptionPane.INFORMATION_MESSAGE);
					String filename=String.valueOf(filenameObject);
					if(filenameObject==null){
					}else {
						if(filename.equals("")){
							JOptionPane.showMessageDialog(null, "文件名不能为�?","",JOptionPane.WARNING_MESSAGE);
						}else{
							String code = codeTextArea.getText();
							String data=inputTextArea.getText();
							String result=resultLabel.getText();
							String file=code+";"+data+";"+result;
							String userId=labelUser.getText();
							try {
								boolean isSuccess=RemoteHelper.getInstance().getIOService().writeFile(file, userId, filename);
								if(isSuccess){
									JOptionPane.showMessageDialog(null, "保存成功", "",JOptionPane.INFORMATION_MESSAGE);
								}else{
									JOptionPane.showMessageDialog(null, "保存失败");
								}
							} catch (RemoteException e1) {
								JOptionPane.showMessageDialog(null, "保存失败");
								e1.printStackTrace();
							}
						}
					}*/
				if(presentFile.equals("")){	
					JOptionPane.showMessageDialog(frame, "请先新建�?个文�?","",JOptionPane.ERROR_MESSAGE);
				}else{
					String code = codeTextArea.getText();
					String data=inputTextArea.getText();
					String result=resultLabel.getText();
					String file=code+";"+data+";"+result;
					String userId=labelUser.getText();
					try {
						boolean isSuccess=RemoteHelper.getInstance().getIOService().writeFile(file, userId, presentFile);
						if(isSuccess){
							JOptionPane.showMessageDialog(frame, "保存成功", "",JOptionPane.INFORMATION_MESSAGE);
						//	presentFile="";
						}else{
							JOptionPane.showMessageDialog(frame, "保存失败");
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	//logout
	public class LogoutActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean canLogout = false;
			try {
				canLogout = RemoteHelper.getInstance().getUserService().logout(labelUser.getText());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
				if(canLogout==true){
					int choose=JOptionPane.showConfirmDialog(frame, "确定�?出吗�?","",JOptionPane.YES_NO_OPTION);
						if(choose==JOptionPane.YES_OPTION){
						try {
							RemoteHelper.getInstance().getUserService().dologout(labelUser.getText());
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						labelUser.setText("");
						}
				}else{
					JOptionPane.showMessageDialog(frame, "请先登录","",JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	//login，弹出登录窗口，结果有登录成功和用户名或密码错误
	public class LoginActionListener implements ActionListener {
		JFrame frame1;
		JButton confirm;
		JButton cancle;
		JLabel usernameLabel;
		JLabel passwordLabel;
		JLabel title;
		JTextField usernameText;
		JPasswordField passwordText;
		@Override
		public void actionPerformed(ActionEvent e) {
			
				frame1=new JFrame();
				frame1.setLayout(null);
				confirm=new JButton("确定");
				cancle=new JButton("取消");
				confirm.addActionListener(new ButtonActionListener());
				cancle.addActionListener(new ButtonActionListener());
				usernameLabel=new JLabel("用户�?");
				passwordLabel=new JLabel("密码");
				title=new JLabel("登录");
				usernameText=new JTextField();
				passwordText=new JPasswordField();
				
				usernameLabel.setBounds(20,30,100,40);
				usernameText.setBounds(80,30,200,40);
				passwordLabel.setBounds(20,90,100,40);
				passwordText.setBounds(80,90,200,40);
				confirm.setBounds(60,150,80,40);
				cancle.setBounds(180,150,80,40);
				title.setBounds(5, 0, 40, 20);
				Container c=frame1.getContentPane();
				c.add(usernameLabel);
				c.add(usernameText);
				c.add(passwordLabel);
				c.add(passwordText);
				c.add(confirm);
				c.add(cancle);
				c.add(title);
				
				frame1.setBounds(500, 250, 300, 200);
				frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame1.dispose();
				frame1.setUndecorated(true);
				frame1.setVisible(true);	
		}
		
		public class ButtonActionListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				String instruction=e.getActionCommand();
				if(instruction.equals("确定")){
					String username=usernameText.getText();
					String password=String.valueOf(passwordText.getPassword());
					try {
						if(RemoteHelper.getInstance().getUserService().login(username,password)==true){
							frame1.dispose();
							labelUser.setText(username);
							JOptionPane.showMessageDialog(frame,"登录成功","",JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							JOptionPane.showMessageDialog(frame,"用户名或密码错误","",JOptionPane.ERROR_MESSAGE);
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				if(instruction.equals("取消")){
					frame1.dispose();
				}
			}
		}
	}
	
	//对�?�sign up”的监听，弹出注册界面，结果有注册成功和用户名已存在
	public class SignupActionListener implements ActionListener {
		JFrame frame1;
		JButton confirm;
		JButton cancle;
		JLabel usernameLabel;
		JLabel passwordLabel;
		JLabel title;
		JTextField usernameText;
		JPasswordField passwordText;
		@Override
		public void actionPerformed(ActionEvent e) {
				frame1=new JFrame();
				frame1.setLayout(null);
				confirm=new JButton("确定");
				cancle=new JButton("取消");
				confirm.addActionListener(new ButtonActionListener());
				cancle.addActionListener(new ButtonActionListener());
				usernameLabel=new JLabel("用户�?");
				passwordLabel=new JLabel("密码");
				title=new JLabel("注册");
				usernameText=new JTextField();
				passwordText=new JPasswordField();
				
				usernameLabel.setBounds(20,30,100,40);
				usernameText.setBounds(80,30,200,40);
				passwordLabel.setBounds(20,90,100,40);
				passwordText.setBounds(80,90,200,40);
				confirm.setBounds(60,150,80,40);
				cancle.setBounds(180,150,80,40);
				title.setBounds(5, 0, 40, 20);
				Container c=frame1.getContentPane();
				c.add(usernameLabel);
				c.add(usernameText);
				c.add(passwordLabel);
				c.add(passwordText);
				c.add(confirm);
				c.add(cancle);
				c.add(title);
				
				frame1.setBounds(500, 250, 300, 200);
				frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame1.dispose();
				frame1.setUndecorated(true);
				frame1.setVisible(true);
				
		}
		public class ButtonActionListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				String instruction=e.getActionCommand();
				if(instruction.equals("确定")){
					String username=usernameText.getText();
					String password=String.valueOf(passwordText.getPassword());
					try {
						if(RemoteHelper.getInstance().getUserService().signup(username,password)==false){
							frame1.dispose();
							JOptionPane.showMessageDialog(frame, "用户名已存在","",JOptionPane.ERROR_MESSAGE);
						}
						else{
							frame1.dispose();
							JOptionPane.showMessageDialog(frame, "注册成功","",JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				if(instruction.equals("取消")){
					frame1.dispose();
				}
			}
		}
	}
	//进行版本选择
	public class VersionActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(labelUser.getText()==null){
				JOptionPane.showMessageDialog(frame,"请先登录","",JOptionPane.ERROR_MESSAGE);
			}else{
				ArrayList<String> list=new ArrayList<String>();
				try {
					list=RemoteHelper.getInstance().getIOService().readVersionFileList(labelUser.getText(),presentFile);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				/*for(int i=0;i<list.size();i++){
					if(!list.get(i).contains(presentFile+".txt")){
						list.remove(i);
					}
				}*/
				if(list==null){
					JOptionPane.showMessageDialog(frame,"没有历史版本","",JOptionPane.ERROR_MESSAGE);
				}else{
					Object[] listObject=new Object[list.size()];
					for(int i=0;i<list.size();i++){
						listObject[i]=list.get(i);
					}
					Object filenameObject=JOptionPane.showInputDialog(frame, "请�?�择你要打开的文�?",""
						,JOptionPane.INFORMATION_MESSAGE,null,listObject,null);
					String filename=String.valueOf(filenameObject);
					String result="";
					try {
						result=RemoteHelper.getInstance().getIOService().readVersionFile(labelUser.getText(),presentFile, filename);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				String[] temp=result.split(";",-1);
				codeTextArea.setText(temp[0]);
				inputTextArea.setText(temp[1]);
				resultLabel.setText(temp[2]);
			}
			}
			}
		}

	
	public class UndoActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
				
		}
	}
	
	public class RedoActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
		}
		
	}
	
	//将代码区和数据区的内容传给remotehelper
	public class ExecuteActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			String code=codeTextArea.getText();
			String param=inputTextArea.getText();
			try {
				resultLabel.setText(RemoteHelper.getInstance().getExecuteService().execute(code, param));
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
}
