import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class Line {
	int sx=380,sy=180;//线的起点坐标
	int dx,dy;//线的终点坐标
	double length=100,angle_n=0;
	//注意计算机中，x的正方向是向下的，要注意一下
	int dir=1;//方向控制
	int state=0;//0摇摆 1抓取 2返回 3抓取返回
	Image hook_image=Toolkit.getDefaultToolkit().getImage("imgs/hook.png");
	
	Gamewin frame;
	Line(Gamewin fframe)
	{
		this.frame=fframe;
	}
	
	void jud_collision()
	{
//		System.out.println(dx+"  "+dy);
		for(Object obj:this.frame.objlist)
		{
			if(dx>obj.x&&dx<obj.x+obj.width)
				if(dy>obj.y&&dy<obj.y+obj.height)
				{
					state=3;
					obj.is_catch=true;
				}
		}
			
	}
	
	void draw(Graphics g)
	{
		dx=(int)(sx+length*Math.cos(angle_n*Math.PI));
		//java里double不能直接赋值给int,需要强制转换
		dy=(int)(sy+length*Math.sin(angle_n*Math.PI));
		g.setColor(Color.red);
		
		//三条平行线使红线加粗
		g.drawLine(sx-1, sy, dx-1, dy);
		g.drawLine(sx, sy, dx, dy);
		g.drawLine(sx+1, sy, dx+1, dy);
		g.drawImage(hook_image, dx-36, dy, null);
	}
	void paintself(Graphics g)
	{
		jud_collision();
		if(state==0)
		{
			if(angle_n<0.1)
				dir=1;
			else if(angle_n>0.9)
				dir=-1;
			angle_n+=dir*0.005;
			draw(g);
		}
		else if(state==1)
		{
			if(length<650)
			{
				length+=10;
				draw(g);
			}
			else state=2;
		}
		else if(state==2)
		{
			if(length>100)
			{
				length-=10;
				draw(g);
			}
			else state=0;
		}
		else if(state==3)
		{
			int speed_change=0;
			if(length>100)
			{
				length-=10;
				draw(g);
				for(Object obj:this.frame.objlist)
				{
					if(obj.is_catch==true)
					{
						speed_change=obj.m;
						obj.x=dx-(int)(0.5*obj.width);
						obj.y=dy;
						if(length<=100)
						{
							state=0;
							obj.x=-150;
							obj.y=-150;
							obj.is_catch=false;
							Bg.score_sum+=obj.score;
							Bg.potion_use=false;
							//移出屏幕外
						}
						if(Bg.potion_use==true)
						{
							if(obj.type==1)//金块
							{
								speed_change=1;
							}
							else 
							{
								state=2;
								obj.x=-150;
								obj.y=-150;
								obj.is_catch=false;
								Bg.potion_use=false;
							}
						}
						
					}
					
				}
			}
			try {
				Thread.sleep(speed_change);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	void regame()
	{
		angle_n=0;
		length=100;
	}
}
