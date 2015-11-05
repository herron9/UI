package test;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Dimension;


import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import client.CircleClient;
import communication.Message;

import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChattingPanel extends JPanel {

	//public JTextArea ChatArea = new JTextArea(16,40);
	public JPanel Inner = new JPanel();
	public JScrollPane Scroller = new JScrollPane(Inner);//add chatarea to scrollarea
	public JTextField MsgField = new JTextField(20);
	public JButton SendMsgBtn = new JButton("Send");
	public JButton ImgBtn = new JButton("Image");
	public JLabel Plus;
	public ImageIcon PlusIcon = new ImageIcon("bin/plus.png");
	
	JPanel South = new JPanel();
	JPanel SubBar = new JPanel();
	String DesID = "null";
	boolean subbar=false;
	
	
	public ChattingPanel( CircleClient client) {
		
		setBackground(UIManager.getColor("CheckBox.background"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		Scroller.setPreferredSize(new Dimension(600, 370));
		Scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		DefaultCaret caret = (DefaultCaret)ChatArea.getCaret();
//		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
//		Scroller.setViewportView(ChatArea);//set sroller to the component u want to scroll
		
//		JScrollBar Bar = Scroller.getVerticalScrollBar();
//		Bar.setValue( Bar.getMaximum() );
//		DefaultCaret caret = (DefaultCaret)Inner.getCaret();
//		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		Scroller.setViewportView(Inner);
		add(Scroller);//add scroller to panel, not the textarea
		MsgField.setHorizontalAlignment(SwingConstants.LEFT);
		MsgField.setBackground(UIManager.getColor("Button.highlight"));
		
		Plus = new JLabel(ClientFunction.resizeIcon(PlusIcon,24));
		Plus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("click");
//				if (subbar==false) {
//					add(SubBar);
//					revalidate();
//					repaint();
//					subbar=true;
//					return;	
//				}
//				if (subbar==true) {
//					remove(SubBar);
//					revalidate();
//					repaint();
//					subbar=false;
//					return;
//				}
			}
		});
		
//		SendTextButtonHandler sendTextButtonHandler = new SendTextButtonHandler(Inner,MsgField,client,null);
		
		FlowLayout flowLayout = (FlowLayout) South.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		South.add(MsgField);
		South.add(Plus);
		South.add(ImgBtn);
		South.add(SendMsgBtn);
//		ImgBtn.addActionListener(new SendTextButtonHandler(Inner,MsgField,client,null));

		SendMsgBtn.addActionListener(new SendTextButtonHandler(Inner,MsgField,client,null));

		Inner.setPreferredSize(new Dimension(600, 370));
		Inner.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		SendMsgBtn.setPreferredSize(new Dimension(100, 32));
		ImgBtn.setPreferredSize(new Dimension(100, 32));

		add(South);
//		add(SubBar);
		SubBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
//		ImgBtn.setPreferredSize(new Dimension(100, 32));
//		SubBar.add(ImgBtn);
		South.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{MsgField, SendMsgBtn}));
		
		//{
//            public void  mouseClicked(MouseEvent e) {
//            	s3Repository s3= new s3Repository();
//        		String key = ""+UUID.randomUUID()+".jpg";
//        		String filePath =SwingFileChooserDemo.chooseAFileFromCurrentMachine();
//        		s3.uploadFile(key,filePath);
//        		String fileurl="https://s3.amazonaws.com/circleuserfiles/"+key;
//        		System.out.println(fileurl);
//        		BufferedImage img = null;
//        		try {
//        			URL myURL = new URL(fileurl);
//        			img = ImageIO.read(myURL);
//        		} catch (IOException f) {
//        		}
//        		System.out.println(img.getWidth());
//        		ArrayList<String> des = new ArrayList<>();
//        	    des.add(FriendPanel.friendname);
//        	    Message message = new Message();
//        	    message.setMessageType(Message.LINK);
//        	    message.setMessageSrcID(LoginPanel.circleAccessToken);
//        	    message.setMessageDesIDList(des);
//        	    message.setMessageContent(fileurl);
//        		System.out.println(client);
//        	    try {
//					client.sendTextMessage(message);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//
//             }
//             public void  mouseExited(MouseEvent e) {
//             }
//             public void  mouseEntered(MouseEvent e) {            	
//             }
//             public void  mouseReleased(MouseEvent e) {            	 
//             }
//             public void  mousePressed(MouseEvent e) { 
//             }
//         });
	}


	private ActionListener SendTextButtonHandler(JPanel inner2, JTextField msgField2, CircleClient client,
			Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}

