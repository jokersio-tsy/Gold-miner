import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;//图形化编程的包

public class Gamewin extends JFrame
{
	static int state=0;
	//0准备 1运行中 2商店 3失败
	ArrayList<Object> objlist=new ArrayList<>();//存放金块石头
	Bg bg=new Bg();
	Line line=new Line(this);
	Image OffScreenImage;
	Gold gold_tmp;
	
	//添加object
	{
		//添加金块
		for(int i=1;i<=8;i++)
		{
			boolean is_place=true;
			double p=Math.random();
			if(p<0.3)
				gold_tmp=new GoldMini();
			else if(p<0.7)
				gold_tmp=new Gold();
			else
				gold_tmp=new GoldPlus();
			
			for(Object obj:objlist)
			{
				if(gold_tmp.getrec().intersects(obj.getrec()))//两个矩形的碰撞检测
				{
					is_place=false;
					i--;
					break;
				}
			}
			
			if(is_place==true)
				objlist.add(gold_tmp);
		}
			
		//添加石块
		for(int i=1;i<=5;i++)
		{
			boolean is_place=true;
			Rock rock_tmp=new Rock();
			for(Object obj:objlist)
			{
				if(rock_tmp.getrec().intersects(obj.getrec()))//两个矩形的碰撞检测
				{
					is_place=false;
					i--;
					break;
				}
			}
			
			if(is_place==true)
				objlist.add(rock_tmp);
		}
	}
	
	public void next_leval()
	{
		if(state==1&&bg.check_time())
		{
			if(bg.score_sum>=bg.aim_score)
			{
				state=2;
				Bg.level++;
				bg.start_time=System.currentTimeMillis();
			}
			else {
				state=3;
			}
			dispose();
			Gamewin new_gamewin=new Gamewin();
			new_gamewin.launch();
		}
	}
	
	void launch()//初始化
	{
		this.setVisible(true);
		this.setSize(768,824);
		this.setLocationRelativeTo(null);//使当前窗口居中
		this.setTitle("Gold Miner");
		setDefaultCloseOperation(EXIT_ON_CLOSE);//单击右上角也可以关闭程序
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				switch(state)
				{
					case 0:
						if(e.getButton()==3)
						{
							state=1;
							bg.start_time=System.currentTimeMillis();
						}
							
						break;
					case 1:
						if(e.getButton()==1&&line.state==0)//左键是1，滚轮是2，右键是3
							line.state=1;
						if(e.getButton()==3&&line.state==3)
							if(Bg.potion_num>0)
							{
								Bg.potion_num--;
								Bg.potion_use=true;
							}
						break;
					case 2:
						if(e.getButton()==1)
							bg.shop=true;
						else 
						{
							state=1;
							bg.start_time=System.currentTimeMillis();
						}
						break;
					case 3:
						if(e.getButton()==1)
						{
							state=0;
							bg.regame();
							line.regame();
						}
						break;
				}
				
			}
		});
		
		while(true)
		{
			repaint();
			next_leval();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void paint(Graphics g)
	{
		OffScreenImage=this.createImage(768,1000);
		Graphics gimage=OffScreenImage.getGraphics();
		bg.paintself(gimage);
		if(state==1)
		{
			for(Object obj:objlist)
				obj.paintself(gimage);
			line.paintself(gimage);
		}
		
		
		g.drawImage(OffScreenImage,0,0,null);
	}
	
	public static void main(String args[])
	{
		Gamewin gamewin=new Gamewin();
		gamewin.launch();
	}
}
