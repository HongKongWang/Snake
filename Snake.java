import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.TreeMap;

import javax.swing.*;

/*
	No long ago, I saw one of my friend(MarcoZhang) play Python.
	That's why I had an idea to create a language simple as Python.
	These words were all typed by me in only one day. So they will be changed soon~
*/
public class Snake {
	static TreeMap<String,Object> obj;
	static JTextArea jta1;
	static JScrollPane sp;
	static boolean sleep;
	static slp SLP;
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
		final JTextArea jtf2=new JTextArea();
		jf.add(jtf2);
		jtf2.setBounds(34,437,442,107);
		JButton jb3=new JButton("Bit");
		jf.add(jb3);
		jb3.setBounds(33,397,442,30);
		sp=new JScrollPane(jta1);
		GridLayout gl=new GridLayout(1,1);
		JPanel jp1=new JPanel();
		jp1.setBounds(32,31,443,354);
		jp1.setLayout(gl);
		jp1.add(sp);
		jf.add(jp1);
		jf.setVisible(true);
		jta1.setEditable(false);
		jb3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String str=jtf2.getText();
				jtf2.setText(null);
				BufferedReader br=new BufferedReader(new StringReader(str));
				String s = null;
				jta1.append("I:");
				if(str.isEmpty()){
					jta1.append("nothing\n");
					say();
				}
				while(true){
					try {
						s =br.readLine();
					} catch (Exception e1) {}
					if(s!=null){
						jta1.append(s+"\n");
						read(s);
					}else{
						break;
					}
				}
			}
		});
	}
	static void say(String str){
		jta1.append("Snake:"+str+"\n");
		say();
	}
	static void say(){
		JScrollBar sb=sp.getVerticalScrollBar();
		sb.setValue(sb.getMaximum());
	}
	static void read(String str){
		if(!sleep){
			String[] fir=str.split("<<");
			if(fir.length==2){
				if(fir[0].equals("out")){
					if(obj.containsKey(fir[1])){
						say((String)obj.get(fir[1]));
					}else if(fir[1].startsWith("~")){
						say(fir[1].replaceFirst("~",""));
					}else{
						say("Object No Found!");
					}
				}else if(fir[0].equals("sleep")){
					Integer i=0;
					try{
						if(fir[1].startsWith("`")){
							i=new Integer(fir[1].replaceFirst("`",""));
							SLP=new slp(i);
							SLP.start();
						}else if(obj.get(fir[1])!=null){
							i=new Integer((String)obj.get(fir[1]));
							SLP=new slp(i);
							SLP.start();
						}else{
							say("Time No Found!");
						}
					}catch(Exception e){
						say("Time No Found!");
					}
				}else if(!fir[0].equals(null)){
					if(fir[1].startsWith("~")){
						obj.put(fir[0],fir[1].replaceFirst("~",""));
						say(fir[1].replaceFirst("~",""));
					}else if(obj.get(fir[1])!=null){
						obj.put(fir[0],obj.get(fir[1]));
						say((String) obj.get(fir[1]));
					}else{
						say("Object No Found!");
					}
				}else{
					say("No Object Name!");
				}
			}else{
				say("Error!");
			}
		}else if(str.equals("wake up")){
			SLP.interrupt();
		}
	}
}
class slp extends Thread{
	Integer i;
	slp(Integer i){
		this.i=i;
	};
	public void run() {
		try {
			Snake.sleep=true;
			sleep(i*1000);
		} catch (InterruptedException e) {
			
		}
		finally{
			Snake.sleep=false;
			Snake.jta1.append("Snake waked up......\n");

		}
	}
}
