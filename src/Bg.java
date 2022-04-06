import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Bg {
	static int score_sum=0;
	static int potion_num=3;
	static boolean potion_use=false;
	static int level=1;//关卡数
	int aim_score=level*15;
	int potion_price=(int)(Math.random()*10);
	boolean shop =false;
	
	long start_time;
	long end_time;
	
	Image bg=Toolkit.getDefaultToolkit().getImage("imgs/bg.jpg");
	Image bg1=Toolkit.getDefaultToolkit().getImage("imgs/bg1.jpg");
	Image peo=Toolkit.getDefaultToolkit().getImage("imgs/peo.png");
	Image potion=Toolkit.getDefaultToolkit().getImage("imgs/water.png");
	
	void paintself(Graphics g)//画笔
	{
		g.drawImage(bg, 0, 200,null);
		g.drawImage(bg1, 0, 0,null);
		g.drawImage(peo, 310, 50,null);
		
		switch(Gamewin.state)
		{
			case 0:
				draw_word(g,80,Color.green,"Ready to start",130,400);
				break;
			case 1:
				//药水
				g.drawImage(potion,450, 40, null);
				draw_word(g,30,Color.black,"*"+potion_num,510,70);
				
				//分数
				draw_word(g,30,Color.black,"Score:"+score_sum,30,150);
				
				//关卡&目标积分
				draw_word(g,20,Color.black,"Level:"+level,30,60);
				draw_word(g,30,Color.black,"Goal:"+aim_score,30,110);
				
				end_time=System.currentTimeMillis();
				long tim=20-(end_time-start_time)/1000;
				draw_word(g,30,Color.black,"Time:"+tim+"s",520,150);
				break;
			case 2:
				g.drawImage(potion,300, 400, null);
				draw_word(g,30,Color.black,"Price:"+potion_price,300,500);
				draw_word(g,30,Color.black,"Whether to buy?",300,550);
				if(shop)
				{
					score_sum-=potion_price;
					potion_num++;
					shop=false;
					Gamewin.state=1;
					start_time=System.currentTimeMillis();
				}
				break;
			case 3:
				draw_word(g,80,Color.black,"Failed",250,350);
				draw_word(g,80,Color.black,"Score:"+score_sum,200,450);
				break;
		}
		
		
	}
	
	//倒计时是否完成
	public boolean check_time()
	{
		end_time=System.currentTimeMillis();
		long tim=20-(end_time-start_time)/1000;
		if(tim<=0)
			return true;
		return false;
	}
	
	public void draw_word(Graphics g,int size,Color col,String str,int x,int y)
	{
		g.setColor(col);
		g.setFont(new Font("Arial",Font.BOLD,size));
		g.drawString(str,x,y);
	}
	
	void regame()
	{
		score_sum=0;
		potion_num=3;
		potion_use=false;
		level=1;//关卡数
	}
}
