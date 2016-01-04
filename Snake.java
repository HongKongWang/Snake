import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.TreeMap;

import javax.swing.*;

/*
	No long ago, I saw one of my friends (MarcoZhang) play Python.
	That's why I had an idea to create a language as simple as Python.
	These words were all typed by me in only two days. So they will be changed soon~
*/
public class Snake {
	static TreeMap<String,Object> obj;
	static JTextArea jta1;
	static JButton jb3;
	static JScrollPane sp;
	static boolean sleep;
	public static void main(String[] args) {
		obj=new TreeMap<String, Object>();
		JFrame jf=new JFrame("Snake");
		sleep=false;
		jf.setDefaultCloseOperation(3);
		try{UIManager.setLookAndFeel
		(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){e.printStackTrace();}
		jf.setLayout(null);
		jf.setResizable(false);
		jf.setBounds(350,74,517,609);
		jta1=new JTextArea("Snake waked up......\n");
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
				jta1.append("I:");
				str=str.replaceAll("\n","");
				String[] s=str.split(";");
				if(s[0].equals("")){
					jta1.append("nothing\n");
				}else{
					if(str.endsWith(";")){
						StringBuffer sb=new StringBuffer(str);
						sb.setLength(sb.length()-1);
						str=sb.toString();
					}
					jta1.append(str.replaceAll(";","\n  ")+"\nSnake:");
					jtaWriter jw=new jtaWriter(jta1);
					jw.read(s);
					jw=null;
					JScrollBar sb=sp.getVerticalScrollBar();
					sb.setValue(sb.getMaximum());
				}
			}
		});
	}
}
static class jtaWriter extends Thread{
	JTextArea jta;
	boolean head;
	String[] str;
	jtaWriter(JTextArea jta){
		this.jta=jta;
		this.head=true;
	}
	void say(String str){
		if(head){
			jta.append(str+"\n");
			head=false;
		}else{
			jta.append("      "+str+"\n");
		}
	}
	public void run(){
		Snake.jb3.setEnabled(false);
		for(int I=0;I<str.length;I++){
			String[] fir=str[I].split("<<");
			if(fir.length==2){
				if(fir[0].equals("out")){
					if(obj.containsKey(fir[1])){
						say(obj.get(fir[1]).toString());
					}else if(fir[1].startsWith("~")){
						say(fir[1].replaceFirst("~",""));
					}else{
						try {
							say(new Calculator().calculate(fir[1])+"");
						} catch (Exception e) {
							say("Calculate failed!");
						}
					}
				}else if(fir[0].equals("sleep")){
					Integer i=0;
						if(obj.get(fir[1])!=null){
							try{
								i=new Integer((String)obj.get(fir[1]));
								sleep(i*1000);
							} catch (InterruptedException e) {}
							catch(NumberFormatException e1){
								say("Calculate failed");
							}
						}else{
							try {
								i=new Integer((int) new Calculator().calculate(fir[1])+"");
								sleep(i*1000);
						}catch (InterruptedException e) {
							e.printStackTrace();
						}
						catch (NumberFormatException e) {
							say("Calculate failed!");
						}
					}
				}else if(fir[0].equals("ascii")){
					if(fir[1].startsWith("~")){
						char[] c=fir[1].replaceFirst("~","").toCharArray();
						for(int i=0;i<fir[1].length()-1;i++){
							Integer in=(int) c[i];
							say(in.toString());
						}
					}else if(obj.get(fir[1])!=null){
						char[] c=((String) obj.get(fir[1])).toCharArray();
						for(int i=0;i<((String) obj.get(fir[1])).length();i++){
							Integer in=(int) c[i];
							say(in.toString());
						}
					}else{
						say("Object No Found!");
					}
				}else if(noo(fir[0])){
					if(fir[1].startsWith("~")){
						obj.put(fir[0],fir[1].replaceFirst("~",""));
						say(fir[1].replaceFirst("~",""));
					}else if(obj.get(fir[1])!=null){
						obj.put(fir[0],obj.get(fir[1]));
						say(""+obj.get(fir[1]));
					}else{
						try {
							say(new Calculator().calculate(fir[1])+"");
							obj.put(fir[0],new Calculator().calculate(fir[1]));
						} catch (Exception e) {
							say("Calculate failed!");
						}
					}
				}else{
					say("Bad Object Name!");
				}
			}else{
				say("Error!");
			}
		}
		head=true;
		Snake.jb3.setEnabled(true);
	}
	void read(String[] str){
		this.str=str;
		this.start();
	}
	boolean noo(String name){
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
