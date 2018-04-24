package Main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//程序聊天窗口
/**
 * 
 * @author Young
 *
 */
public class ChatWindow extends JFrame{
	private JButton sendButton;//发送按钮
	private JButton cancelButton;//取消按钮
	private JButton receiveButton;//接收按钮
	private JTextArea receiveText;//接收栏
	private JTextField sendText;//发送栏
	private JLabel localPort;//当前 用户的端口号
	private JTextField remoteAddress;// 远程IP地址号
	private JTextField remoteport;//远程端口号
	private SendThread sendThread;
	private ReceiveThread receiveThread;
	
	private void GUIini() {//GUI设置
		// TODO Auto-generated method stub
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
        setSize(400,500);
//         第一个Panel
        JPanel panel1 = new JPanel(new GridLayout(4,2));
        panel1.setSize(400, 75);
        panel1.add(new JLabel("当前机器的IP地址是："));
        try {
			panel1.add(new JLabel(InetAddress.getLocalHost().getHostAddress()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			panel1.add(new JLabel("当前机器的IP地址是："+"Unknown"));
		}
        panel1.add(new JLabel("当前对话的端口是："));
        panel1.add(localPort = new JLabel(" "+0));
        panel1.add(new JLabel("远程主机的IP地址是："));
        remoteAddress = new JTextField();
        remoteAddress.setColumns(0);
        panel1.add(remoteAddress);
        panel1.add(new JLabel("远程主机的端口号是："));
        remoteport = new JTextField();
        remoteport.setColumns(0);
        panel1.add(remoteport);
        c.add(panel1,BorderLayout.NORTH);
        
//        第二个panel
        JPanel panel2 = new JPanel();
        panel2.setSize(400, 200);
        panel2.add(new JLabel("收到的内容"));
        receiveText = new JTextArea(15, 30);
        receiveText.setEditable(false);
        receiveText.setAutoscrolls(true);
        JScrollPane jsp = new JScrollPane(receiveText);
        panel2.add(jsp);
        
//        第三个panel
        JPanel panel3 = new JPanel();
        panel2.add(new JLabel("请输入发送的内容:"));
        sendText = new JTextField(30);
        sendText.setAutoscrolls(true);
        panel2.add(sendText);
        c.add(panel2,BorderLayout.CENTER);
        c.add(panel3);
        
//        第四个panel
        JPanel panel4 = new JPanel(new GridLayout(1,0));
        panel4.setSize(400, 200);
        receiveButton = new JButton("开始接收数据");
        sendButton = new JButton("发送");
        cancelButton = new JButton("取消");
        panel4.add(receiveButton);
        panel4.add(cancelButton);
        panel4.add(sendButton);
        c.add(panel4, BorderLayout.SOUTH);
//        以上四个面板设置完毕
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private void ActionIni(){
//		键盘动作
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER){ 
					//发送文本
					sendThread.sendMessage(remoteAddress.getText(), Integer.parseInt(remoteport.getText()), sendText.getText());
					receiveText.setText(receiveText.getText()+"\n"+"发送："+sendText.getText());
					
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
//		取消按钮的动作
		cancelButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				sendText.setText("");
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		sendButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				sendThread.sendMessage(remoteAddress.getText(),Integer.parseInt(remoteport.getText()), sendText.getText());
				receiveText.setText(receiveText.getText() + "发送：" + sendText.getText() + '\n');
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void ThreadIni() {
		// TODO Auto-generated method stub
		sendThread = new SendThread(0, this);
		receiveThread = new ReceiveThread(this);
	}
	public ChatWindow(){ 
		GUIini();
		ActionIni();
		ThreadIni();
	}
    public void printError(String err) {
		// TODO Auto-generated method stub
    	System.out.println("Error occur:"+err);
	}

//    回调函数，用于数据从线程中返回的数据
    public void setReceive(String receive){ 
    	receiveText.setText(receiveText.getText()+"收到："+receive+'\n');
    }
    
//    当接收数据的线程开始工作之后，调用该回调函数，设置当前窗口使用的端口是你哪个
    public void setLocalPort(int localPortText){ 
    	localPort.setText(" "+localPortText);
    }
    
//    整个程序的起点
    public static void main(String[] args) {
		new ChatWindow();
	}
}
