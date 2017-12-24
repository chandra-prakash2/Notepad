import java.io.*;
import java.awt.*;
import java.awt.event.*;
class closeDialog implements WindowListener
{
	public void windowOpened(WindowEvent e)
	{
	}
	public void windowClosed(WindowEvent e)
	{
	}
	public void windowActivated(WindowEvent e)
	{
	}
	public void windowDeactivated(WindowEvent e)
	{
	}
	public void windowIconified(WindowEvent e)
	{
	}
	public void windowDeiconified(WindowEvent e)
	{
	}
	public void windowClosing(WindowEvent e)
	{
	}
}
class Notepad_2 extends closeDialog implements ActionListener,ItemListener,WindowListener,TextListener	//,TextListener
{
	private Frame f;
	private MenuBar mb;
	private Font ft;
	private Menu file,edit,option,others;
	private MenuItem neu,open,save,saveAs,quit,cut,copy,paste,find,replace,one,two;
	private CheckboxMenuItem toolbar,statusBar;
	private TextArea ta;
	private File fl=new File("");
	private String nameFile="";
	private String nameDir="";
	private String pasteWord="";
	private boolean saveStatus=true;
	private Dialog findTxt;
	private Button findBut=new Button("Find >>");
	private Dialog replaceTxt;
	private Button replaceBut=new Button("Replace >>");
	private TextField findStr,replaceWhat,replaceWith;
	private CheckboxGroup findCbg,replaceCbg;
	private Checkbox findUp,findDown,replaceUp,replaceDown;
	private int findCount=0,replaceCount=0;
	private Button yes,no,cancel;
	private Dialog saveExit=new Dialog(f,"File is not Saved",true);
	private int findState=2,replaceState=2;
	
	Notepad_2()
	{
		f=new Frame();
		f.setLocation(550,100);
		f.setSize(800,600);

		mb=new MenuBar();

		file=new Menu("File");
		edit=new Menu("Edit");
		option=new Menu("Options");
		others=new Menu("Others");

		neu=new MenuItem("New");
		open=new MenuItem("Open");
		save=new MenuItem("Save");
		saveAs=new MenuItem("Save As");
		quit=new MenuItem("Quit");
		cut=new MenuItem("Cut");
		copy=new MenuItem("Copy");
		paste=new MenuItem("Paste");
		one=new MenuItem("One");
		two=new MenuItem("Two");
		find=new MenuItem("Find");
		replace=new MenuItem("Replace");

		toolbar=new CheckboxMenuItem("Toolbar");
		statusBar=new CheckboxMenuItem("Statusbar");

		ta=new TextArea();
		ta.setText("");
		ft=new Font("Arial",Font.PLAIN,15);
		ta.setFont(ft);
		ta.addTextListener(this);

		neu.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		saveAs.addActionListener(this);
		quit.addActionListener(this);

		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		find.addActionListener(this);
		replace.addActionListener(this);

		one.addActionListener(this);
		two.addActionListener(this);

		toolbar.addItemListener(this);
		statusBar.addItemListener(this);

		findBut.addActionListener(this);
		replaceBut.addActionListener(this);
		file.add(neu);
		file.add(open);
		file.addSeparator();
		file.add(save);
		file.add(saveAs);
		file.add(quit);

		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.addSeparator();
		edit.add(find);
		edit.add(find);
		edit.add(replace);

		option.add(toolbar);
		option.add(statusBar);

		others.add(one);
		others.add(two);

		option.add(others);

		mb.add(file);
		mb.add(edit);
		mb.add(option);

		findTxt=new Dialog(f,"Find");
		findTxt.setLayout(new BorderLayout());
		findTxt.setLocationRelativeTo(null);
		findTxt.setSize(300,100);
		Panel pFind=new Panel();
		pFind.setLayout(new FlowLayout());
		pFind.add(new Label("Find What:"));
		findStr=new TextField(20);
		pFind.add(findStr);
		pFind.add(findBut);
		findCbg=new CheckboxGroup();
		findUp=new Checkbox("UP",false,findCbg);
		findDown=new Checkbox("DOWN",true,findCbg);
		findUp.addItemListener(this);
		findDown.addItemListener(this);
		pFind.add(findUp);
		pFind.add(findDown);
		findTxt.add(pFind);
		
		GridBagConstraints gbc1=new GridBagConstraints();
		gbc1.insets=new Insets(5,3,5,3);
		gbc1.anchor=GridBagConstraints.WEST;
		replaceTxt=new Dialog(f,"Replace");
		replaceTxt.setLayout(new GridBagLayout());
		replaceTxt.setLocationRelativeTo(null);
		replaceTxt.setSize(450,100);
		gbc1.gridx=0;
		gbc1.gridy=0;
		replaceTxt.add(new Label("Find What:"),gbc1);
		replaceWhat=new TextField(20);
		replaceWith=new TextField(20);
		gbc1.gridx=1;
		replaceTxt.add(replaceWhat,gbc1);
		gbc1.gridx=2;
		gbc1.fill=GridBagConstraints.HORIZONTAL;
		replaceTxt.add(replaceBut,gbc1);
		gbc1.fill=GridBagConstraints.NONE;
		gbc1.gridx=0;
		gbc1.gridy=1;
		gbc1.gridwidth=2;
		replaceTxt.add(new Label("Replace With:"),gbc1);
		gbc1.gridwidth=1;
		gbc1.gridx=1;
		gbc1.gridy=1;
		replaceTxt.add(replaceWith,gbc1);
		replaceCbg=new CheckboxGroup();
		replaceUp=new Checkbox("UP",false,replaceCbg);
		replaceDown=new Checkbox("DOWN",true,replaceCbg);
		replaceUp.addItemListener(this);
		replaceDown.addItemListener(this);
		gbc1.anchor=GridBagConstraints.CENTER;
		gbc1.gridx=2;
		replaceTxt.add(replaceUp,gbc1);
		gbc1.gridx=3;
		replaceTxt.add(replaceDown,gbc1);

		f.setMenuBar(mb);
		f.add(ta);
		f.setVisible(true);

		f.addWindowListener(this);
		findTxt.addWindowListener(this);
		replaceTxt.addWindowListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println("HI");
		Object o=e.getSource();
		if(o==neu)
		{
			ta.setText("");
		}

		if(o==open)
		{
			if(saveStatus==false)
			{	
				ta.append("\n\n"+"open save:false");
				saveFun();
			}
			saveStatus=true;
			ta.setText("");
			FileDialog fd=new FileDialog(f,"Open File",FileDialog.LOAD);
			fd.setVisible(true);
			nameFile=fd.getFile();
			nameDir=fd.getDirectory();
			f.setTitle(nameFile);
			if(nameFile!=null&&nameFile.length()!=0)
			{
				ta.setText("");
				fl=new File(nameDir,nameFile);
				FileInputStream fis;
				try
				{
					fis=new FileInputStream(fl);
					int x=0;
					while((x=fis.read())!=-1)
					{
						ta.append(""+(char)x);
					}
				}
				catch(IOException io)
				{
					ta.setText(io.getMessage());
				}
			}
		}
		if(o==save)
		{
			saveFun();
		}
		if(o==saveAs)
		{
			FileDialog fd=new FileDialog(f,"Save As",FileDialog.SAVE);
			fd.setVisible(true);
			nameFile=fd.getFile();
			nameDir=fd.getDirectory();
			f.setTitle(nameFile);
			fl=new File(nameDir,nameFile);
			FileOutputStream fos;
			try
			{
				fos=new FileOutputStream(fl);
				char ch[]=ta.getText().toCharArray();
				for(char x:ch)
				{
					fos.write(x);
				}
			}
			catch(IOException io)
			{
				ta.append("\n\n"+io.getMessage());
			}
			saveStatus=true;
		}
		if(o==cut)
		{
			pasteWord=ta.getSelectedText();
			ta.replaceRange("",ta.getSelectionStart(),ta.getSelectionEnd());
		}
		if(o==copy)
		{
			pasteWord=ta.getSelectedText();
		}
		if(o==paste)
		{
			if((ta.getSelectionEnd()-ta.getSelectionStart())!=0)
			{
				ta.replaceRange("",ta.getSelectionStart(),ta.getSelectionEnd());
			}
			ta.insert(pasteWord,ta.getSelectionStart());
		}

		if(o==find)
		{
			findTxt.setVisible(true);
		}
		if(o==replace)
		{
			System.out.println("replace menu");
			replaceTxt.setVisible(true);
		}
		if(o==findBut)
		{
			if(findState==2)
			{
				// System.out.println("findBut");
				String findx=findStr.getText();
				if(findx!=null&&findx.length()!=0)
				{
					if(findCount==-1)
					{
						findTxt.add(new Label("End OF File"),"South");
						findTxt.validate();
					}
					String temp=ta.getText();
					findCount=temp.indexOf(findx,findCount);
					System.out.println(findCount);
					ta.select(findCount,findCount+findx.length());
					ta.requestFocus();
					findCount++;
				}
				else
				{
					findTxt.add(new Label("No Argument Is Supplied To Find"),"South");
					findTxt.validate();
				}
			}
			if(findState==1)
			{
				String findx=findStr.getText();
				if(findx!=null&&findx.length()!=0)
				{
					if(findCount==-1)
					{
						findTxt.add(new Label("Start OF File reached"),"South");
						findTxt.validate();
					}
					String temp=ta.getText();
					findCount=temp.lastIndexOf(findx,findCount);
					System.out.println(findCount);
					ta.select(findCount,findCount+findx.length());
					ta.requestFocus();
					findCount--;
				}
				else
				{
					findTxt.add(new Label("No Argument Is Supplied To Find"),"South");
					findTxt.validate();
				}
			}

		}
		if(o==replaceBut)
		{
			if(replaceState==2)
			{
				String replacex=replaceWhat.getText();
				// System.out.println(replacex);
				if(replacex!=null&&replacex.length()!=0)
				{
					String temp=ta.getText();
					replaceCount=temp.indexOf(replacex,replaceCount);
					if(replaceCount==-1)
					{
						replaceTxt.add(new Label("End OF File"));
						replaceTxt.validate();
					}
					else
					{
						ta.replaceRange("",replaceCount,replaceCount+replacex.length());
						ta.insert(replaceWith.getText(),replaceCount);
						replaceCount++;
					}
				}
				else
				{
					replaceTxt.add(new Label("No Argument Is Supplied To Replace"));
					replaceTxt.validate();
				}
			}
			if(replaceState==1)
			{
				System.out.println("replaceState1");
				String replacex=replaceWhat.getText();
				// System.out.println(replacex);
				if(replacex!=null&&replacex.length()!=0)
				{
					String temp=ta.getText();
					replaceCount=temp.lastIndexOf(replacex,replaceCount);
					if(replaceCount==-1)
					{
						replaceTxt.add(new Label("Start OF File reached"));
						replaceTxt.validate();
					}
					else
					{
						ta.replaceRange("",replaceCount,replaceCount+replacex.length());
						ta.insert(replaceWith.getText(),replaceCount);
						replaceCount++;
					}
				}
				else
				{
					replaceTxt.add(new Label("No Argument Is Supplied To Replace"));
					replaceTxt.validate();
				}
			}
		}
		if(o==yes || o==no)
		{
			System.out.println("Hello");
			saveExit.setVisible(false);
			saveExit.dispose();
			saveFun();
			f.setVisible(false);
			f.dispose();
			System.exit(1);
		}

	}

	public void itemStateChanged(ItemEvent ie)
	{
		Object o=ie.getSource();
		if(o==findUp)
		{
			findState=1;
			findCount=ta.getText().length();
		}
		
		if(o==findDown)
			findState=2;
		if(o==replaceUp)
		{
			replaceState=1;
			if(replaceCount==0)
			{
				replaceCount=ta.getText().length();
			}
		}
		if(o==replaceDown)
		{
			System.out.println("replaveDown");
			replaceState=2;
		}
	}
	public void textValueChanged(TextEvent e)
	{
		if(e.getSource()==ta)
		{
			saveStatus=false;
		}
	}
	public static void main(String args[])
	{
		new Notepad_2();

	}
	public void windowClosing(WindowEvent e)
	{
		Object o=e.getSource();
		if(o==f)
		{
			Window w=e.getWindow();
			if(saveStatus==false)
			{

				saveExit.add(new Label("Do you want to Save it"));
				saveExit.setLocation(300,300);
				saveExit.setSize(200,100);
				saveExit.setLayout(new FlowLayout());
				yes=new Button("Save");
				no=new Button("Don't Save");
				cancel=new Button("Cancel");
				saveExit.add(yes);
				saveExit.add(no);
				saveExit.add(cancel);
				yes.addActionListener(this);
				no.addActionListener(this);
				cancel.addActionListener(this);
				saveExit.setVisible(true);
				saveExit.validate();
			}
			saveExit.setVisible(false);
			saveExit.dispose();
			f.setVisible(false);
			f.dispose();
			System.exit(1);
		}
		else
		{
			Window w=e.getWindow();
			w.setVisible(false);
			findCount=0;
		}

	}
	private void saveFun()
	{
		// ta.append("\n\nSave:\t"+fl);
		if(fl.exists()==false)
		{
			ta.append("\n\n"+"fl.exists()==false");
			FileDialog fd=new FileDialog(f,"Save File",FileDialog.SAVE);
			fd.setVisible(true);
			nameFile=fd.getFile();
			nameDir=fd.getDirectory();
			System.out.println(nameDir+"\n"+nameFile);
			if(nameFile==null||nameFile.length()==0&&nameDir==null||nameDir.length()==0)
			{
				return;
			}
			f.setTitle(nameFile);
		}
		FileOutputStream fos;
		try
		{
			// ta.append("\n\n"+nameFile+"\t"+nameDir);
			fl=new File(nameDir,nameFile);
			fos=new FileOutputStream(fl);
			char ch[]=ta.getText().toCharArray();
			for(char x:ch)
			{
				fos.write(x);
			}
		}
		catch(IOException io)
		{
			ta.append("\n\n"+io.getMessage());
		}
		saveStatus=true;
	}
}
