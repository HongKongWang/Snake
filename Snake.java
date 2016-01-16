import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TreeMap;

import javax.swing.*;

public class Snake {
	static TreeMap<String,String> obj;
	static JTextArea jta1;
	static JButton jb3;
	static JScrollPane sp;
	static boolean sleep;
	static jtaWriter jw;
	public static void main(String[] args) {
		obj=new TreeMap<String, String>();
		JFrame jf=new JFrame("Snake");
		sleep=false;
		jf.setDefaultCloseOperation(3);
		try{UIManager.setLookAndFeel
		(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){e.printStackTrace();}
		jf.setLayout(null);
		jf.setResizable(false);
		jf.setBounds(350,74,517,609);
		jta1=new JTextArea("Snake waked up......");
		jb3=new JButton("Bite");
		jf.add(jb3);
		jb3.setBounds(33,397,442,30);
		sp=new JScrollPane(jta1);
		GridLayout gl=new GridLayout(1,1);
		JPanel jp1=new JPanel();
		jp1.setBounds(32,31,443,354);
		jp1.setLayout(gl);
		jp1.add(sp);
		jf.add(jp1);
		final JTextArea jtf2=new JTextArea();
		JPanel jp2=new JPanel();
		jp2.setBounds(34,437,442,107);
		jp2.setLayout(gl);
		JScrollPane sp1=new JScrollPane(jtf2);
		jp2.add(sp1);
		jf.add(jp2);
		jta1.setFont(new Font("DialogInput",0,13));
		jta1.setForeground(Color.DARK_GRAY);
		jf.setVisible(true);
		jta1.setEditable(false);
		jb3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String str=jtf2.getText();
				jtf2.setText(null);
				jta1.append("\nI:");
				str=str.replaceAll("\n","");
				if(str.isEmpty()){
					jta1.append("nothing");
				}else{
					if(str.endsWith(";")){
						StringBuffer sb=new StringBuffer(str);
						sb.setLength(sb.length()-1);
						str=sb.toString();
					}
					jta1.append(str.replaceAll(";","\n  "));
					jw=null;
					jw=new jtaWriter(jta1);
					jw.read(str);
					JScrollBar sb=sp.getVerticalScrollBar();
					sb.setValue(sb.getMaximum());
				}
			}
		});
		jtf2.addKeyListener(new KeyListener(){
	    	public void keyTyped(KeyEvent e){
	    		if(e.getKeyChar()==27&&jw!=null&&jw.alive){
	    			jw.kill();
	    			jw=null;
	    		}
	    	}
	    	public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
		});
	}
	
static class jtaWriter extends Thread{
	JTextArea jta;
	boolean head,alive;
	String str;
	jtaWriter(JTextArea jta){
		this.jta=jta;
		this.head=true;
		this.alive=false;
	}
	private void say(String str){
		if(head){
			jta.append("\nSnake:"+str);
			head=false;
		}else{
			jta.append("\n      "+str);
		}
	}
	private void out(String str){
		str=str.replace("\\n","\n      ");
		if(head){
			jta.append("\nSnake:"+str);
			head=false;
		}else{
			jta.append(str);
		}
	}
	public void kill(){
		if(!this.isInterrupted()){
			this.interrupt();
		}
		this.alive=false;
		say("Stopped!");
	}
	public static String[] split(String str,String spl){
		String[] s=str.split(spl);
		String[] S=s;
		for(int i=0;i<s.length-1;i++){
			if(s[i].endsWith("\\")){
				s[i]=s[i].replace("\\","")+spl;
				for(int j=i;j<s.length-1;j++){
					s[j]=s[j]+s[j+1];
				}
				S=new String[s.length-1];
				for(int i1=0;i1<S.length;i1++){
					S[i1]=s[i1];
				}
				s=S;
			}
		}
		return S;
	}
	public void run(){
		this.alive=true;
		String[] s=split(str, ";");
		for(int I=0;I<s.length;I++){
			if(!alive){
				break;
			}
			String[] fir=split(s[I],"<<");
			if(fir.length==2){
				if(fir[0].equals("out")){
					if(obj.containsKey(fir[1])){
						out(obj.get(fir[1]));
					}else{
						try {
							out(new Calculator().calculate(fir[1]));
						} catch (Exception e) {
							say("Calculate failed!");
							break;
						}
					}
				}else if(fir[0].equals("sleep")){
					Integer i=0;
						if(obj.containsKey(fir[1])){
							try{
								i=new Double((String)obj.get(fir[1])).intValue();
								Snake.jb3.setEnabled(false);
								sleep(i);
							} catch (InterruptedException e) {
								break;
							}
							catch(Exception e1){
								say("Calculate failed");
								break;
							}finally{
								Snake.jb3.setEnabled(true);
							}
						}else{
							try {
								i=new Double(new Calculator().calculate(fir[1])).intValue();
								Snake.jb3.setEnabled(false);
								sleep(i);
						}catch (InterruptedException e) {
							break;
						}
						catch (NumberFormatException e) {
							say("Calculate failed!");
							break;
						}finally{
							Snake.jb3.setEnabled(true);
						}
					}
				}else if(fir[0].equals("ascii")){
					if(obj.containsKey(fir[1])){
						char[] c=((String) obj.get(fir[1])).toCharArray();
						for(int i=0;i<((String) obj.get(fir[1])).length();i++){
							Integer in=(int) c[i];
							say(in.toString());
						}
					}else{
						try{
							char[] c=new Calculator().calculate(fir[1]).toCharArray();
							for(int i=0;i<c.length;i++){
								Integer in=(int) c[i];
								say(in.toString());
							}
						}catch(NumberFormatException e){
							say("Calculate failed!");
							break;
						}
					}
				}else if(noo(fir[0])){
					if(obj.containsKey(fir[1])){
						obj.put(fir[0],obj.get(fir[1]));
					}else{
						try {
							obj.put(fir[0],new Calculator().calculate(fir[1]));
						} catch (NumberFormatException e) {
							say("Calculate failed!");
							break;
						}
					}
				}else{
					say("Error!");
					break;
				}
			}else{
				say("Error!");
				break;
			}
		}
		alive=false;
		head=true;
	}
	public void read(String str){
		this.str=str;
		this.start();
	}
	private static boolean noo(String name){
		char[] c=name.toCharArray();
		if(!((c.length)==0)){
				for(int i=0;i<c.length;i++){
					if(!(((int)c[i]>=65&&(int)c[i]<=90)
							|((int)c[i]>=97&&(int)c[i]<=122)
							|((int)c[i]>=48&&(int)c[i]<=57)
							|(int)c[i]==95)){
						return false;
					}
				}
				return true;
		}else{
			return false;
		}
	}
}
}
